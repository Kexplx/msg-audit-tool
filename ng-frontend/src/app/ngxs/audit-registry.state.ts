import { Audit } from '../data/models/audit.model';
import { State, Selector, Action, StateContext } from '@ngxs/store';
import { Injectable } from '@angular/core';
import { AddAudit, DeleteAudit } from './audit.actions';

export interface AuditRegistryStateModel {
  audits: Audit[];
}

@State<AuditRegistryStateModel>({
  name: 'auditRegistry',
  defaults: {
    audits: [],
  },
})
@Injectable()
export class AuditRegistryState {
  @Selector()
  static audits(state: AuditRegistryStateModel) {
    return state.audits;
  }

  @Action(AddAudit)
  addAudit(context: StateContext<AuditRegistryStateModel>, { audit }: AddAudit) {
    const state = context.getState();

    context.setState({
      ...state,
      audits: [...state.audits, audit],
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
}
