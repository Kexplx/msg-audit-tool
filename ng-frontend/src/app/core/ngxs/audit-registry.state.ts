import { Audit, AuditStatus } from '../data/models/audit.model';
import { State, Selector, Action, StateContext, createSelector, Select } from '@ngxs/store';
import { patch, updateItem, removeItem, append } from '@ngxs/store/operators';
import { Injectable } from '@angular/core';
import {
  AddAudit,
  DeleteAudit,
  UpdateAudit,
  AddInterview,
  AddContactPerson,
  DeleteContactPerson,
  UpdateContactPerson,
} from './actions/audit.actions';
import * as shortid from 'shortid';
import { ContactPerson } from '../data/models/contact-person.model';
import { CONTACT_PEOPLE } from '../data/examples/contact-people';
import { FacCrit } from '../data/models/faccrit.model';
import { FACCRITS } from '../data/examples/fac-crits';
import { AUDITS } from '../data/examples/audits';

export interface AuditRegistryStateModel {
  audits: Audit[];
  facCrits: FacCrit[];
}

@State<AuditRegistryStateModel>({
  name: 'auditRegistry',
  defaults: {
    audits: AUDITS,
    facCrits: FACCRITS,
  },
})
@Injectable()
export class AuditRegistryState {
  @Selector()
  static audits(state: AuditRegistryStateModel) {
    return state.audits;
  }

  @Selector()
  static facCrits(state: AuditRegistryStateModel) {
    return state.facCrits;
  }

  static facCrit(id: string) {
    return createSelector([AuditRegistryState], (state: AuditRegistryStateModel) => {
      return state.facCrits.find(x => x.id === id);
    });
  }

  static criteriaByFactorId(id: string) {
    return createSelector([AuditRegistryState], (state: AuditRegistryStateModel) => {
      return state.facCrits.filter(x => x.referenceId === id);
    });
  }

  static auditByStatus(...statuses: AuditStatus[]) {
    return createSelector([AuditRegistryState], (state: AuditRegistryStateModel) => {
      return state.audits.filter(x => statuses.includes(x.status));
    });
  }

  static audit(id: string) {
    return createSelector([AuditRegistryState], (state: AuditRegistryStateModel) => {
      return state.audits.find(x => x.id === id);
    });
  }

  @Action(AddAudit)
  addAudit({ setState }: StateContext<AuditRegistryStateModel>, { audit }: AddAudit) {
    setState(
      patch({
        audits: append<Audit>([{ ...audit, id: shortid.generate() }]),
      }),
    );
  }

  @Action(DeleteAudit)
  deleteAudit({ setState }: StateContext<AuditRegistryStateModel>, { audit }: DeleteAudit) {
    setState(
      patch({
        audits: removeItem<Audit>(x => x === audit),
      }),
    );
  }

  @Action(UpdateAudit)
  updateAudit(ctx: StateContext<AuditRegistryStateModel>, { id, audit }: UpdateAudit) {
    const a = ctx.getState().audits.find(x => x.id == id);
    ctx.setState(
      patch({
        audits: updateItem<Audit>(x => x.id === id, { ...a, ...audit }),
      }),
    );
  }

  @Action(AddInterview)
  addInterview(context: StateContext<AuditRegistryStateModel>, { audit, interview }: AddInterview) {
    interview = { ...interview, id: shortid.generate() };
    context.setState(
      patch({
        audits: updateItem<Audit>(x => x === audit, {
          ...audit,
          interviews: [...(audit.interviews ?? []), interview],
        }),
      }),
    );
  }
}
