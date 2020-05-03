import { Audit } from '../data/models/audit.model';
import { State, Selector, Action, StateContext } from '@ngxs/store';
import { Injectable } from '@angular/core';
import { AddAudit } from './audit.actions';

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
  addStudent(context: StateContext<AuditRegistryStateModel>, { audit }: AddAudit) {
    const state = context.getState();

    context.setState({
      ...state,
      audits: [...state.audits, audit],
    });
  }
}
