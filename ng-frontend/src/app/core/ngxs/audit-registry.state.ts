import { Audit, AuditStatus } from '../data/models/audit.model';
import { State, Selector, Action, StateContext, createSelector } from '@ngxs/store';
import { patch, updateItem, removeItem, append } from '@ngxs/store/operators';
import { Injectable } from '@angular/core';
import {
  AddAudit,
  DeleteAudit,
  UpdateAudit,
  AddInterview,
  AddContactPerson,
} from './audit.actions';
import * as shortid from 'shortid';
import { ContactPerson } from '../data/models/contact-person.model';

export interface AuditRegistryStateModel {
  audits: Audit[];
  contactPeople: ContactPerson[];
}

@State<AuditRegistryStateModel>({
  name: 'auditRegistry',
})
@Injectable()
export class AuditRegistryState {
  @Selector()
  static audits(state: AuditRegistryStateModel) {
    return state.audits;
  }

  @Selector()
  static contactPeople(state: AuditRegistryStateModel) {
    return state.contactPeople;
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
        audits: updateItem<Audit>(x => x.id === id, { ...a, ...audit, id: id }),
      }),
    );
  }

  @Action(AddInterview)
  addInterview(context: StateContext<AuditRegistryStateModel>, { audit, interview }: AddInterview) {
    context.setState(
      patch({
        audits: updateItem<Audit>(x => x === audit, {
          ...audit,
          interviews: [...(audit.interviews ?? []), interview],
        }),
      }),
    );
  }

  @Action(AddContactPerson)
  addContactPerson(
    { setState }: StateContext<AuditRegistryStateModel>,
    { contactPerson }: AddContactPerson,
  ) {
    setState(
      patch({
        contactPeople: append<ContactPerson>([{ ...contactPerson, id: shortid.generate() }]),
      }),
    );
  }
}
