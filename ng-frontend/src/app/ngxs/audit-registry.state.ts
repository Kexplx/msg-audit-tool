import { Audit } from '../data/models/audit.model';
import { State, Selector, Action, StateContext, createSelector } from '@ngxs/store';
import { patch, updateItem } from '@ngxs/store/operators';
import { Injectable } from '@angular/core';
import { AddAudit, DeleteAudit, UpdateAudit, GetAuditById } from './audit.actions';
import * as shortid from 'shortid';

export interface AuditRegistryStateModel {
  audits: Audit[];
}

@State<AuditRegistryStateModel>({
  name: 'auditRegistry',
  defaults: {
    audits: [
      {
        name: 'Oscar',
        contactPerson: {
          firstName: 'Oscar',
          lastName: 'Rosner',
          information: '01230',
          title: 'Herr',
        },
        customerData: { department: 'asdd', name: 'dasds', sector: 'asds' },
        id: shortid.generate(),
      },
    ],
  },
})
@Injectable()
export class AuditRegistryState {
  @Selector()
  static audits(state: AuditRegistryStateModel) {
    return state.audits;
  }

  static audit(id: string) {
    return createSelector([AuditRegistryState], (state: AuditRegistryStateModel) => {
      return state.audits.find(x => x.id === id);
    });
  }

  @Action(AddAudit)
  addAudit(context: StateContext<AuditRegistryStateModel>, { audit }: AddAudit) {
    const state = context.getState();

    context.setState({
      ...state,
      audits: [...state.audits, { ...audit, id: shortid.generate() }],
    });
  }

  @Action(DeleteAudit)
  deleteAudit(context: StateContext<AuditRegistryStateModel>, { audit }: DeleteAudit) {
    const state = context.getState();
    const indexOfAudit = state.audits.indexOf(audit);

    if (indexOfAudit != -1) {
      context.setState({
        ...state,
        audits: [...state.audits.slice(0, indexOfAudit), ...state.audits.slice(indexOfAudit + 1)],
      });
    }
  }

  @Action(UpdateAudit)
  updateAudit({ setState }: StateContext<AuditRegistryStateModel>, { id, audit }: UpdateAudit) {
    setState(
      patch({
        audits: updateItem<Audit>(x => x.id === id, { ...audit, id: id }),
      }),
    );
  }
}
