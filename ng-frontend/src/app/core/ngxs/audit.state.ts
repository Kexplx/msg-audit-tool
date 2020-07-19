import { Audit, AuditStatus } from '../data/models/audit.model';
import { State, Selector, Action, StateContext, createSelector, NgxsOnInit } from '@ngxs/store';
import { patch, updateItem, append } from '@ngxs/store/operators';
import { Injectable } from '@angular/core';
import { AddAudit, UpdateAudit } from './actions/audit.actions';
import { FacCrit } from '../data/models/faccrit.model';
import { AuditService } from '../http/audit.service';
import { FacCritService } from '../http/facCrit.service';

export interface AuditStateModel {
  audits: Audit[];
  facCrits: FacCrit[];
}

/**
 * State for managing the audits and facCrits of the application.
 *
 * Has: Action handlers to read, write, update and delete an audit.
 * Static and dynamic selectors to select audits and facCrits.
 */
@State<AuditStateModel>({
  name: 'audit',
})
@Injectable()
export class AuditState implements NgxsOnInit {
  constructor(private auditService: AuditService, private facCritService: FacCritService) {}

  ngxsOnInit({ patchState }: StateContext<AuditStateModel>) {
    this.facCritService.getFacCrits().subscribe(facCrits => {
      patchState({ facCrits });
    });

    this.auditService.getAudits().subscribe(audits => {
      patchState({ audits });
    });
  }

  @Selector()
  static audits(state: AuditStateModel) {
    return state.audits;
  }

  @Selector()
  static facCrits(state: AuditStateModel) {
    return state.facCrits;
  }

  static audit(id: number) {
    return createSelector([AuditState], (state: AuditStateModel) => {
      return state.audits.find(x => x.id === id);
    });
  }

  static auditByStatus(...statuses: AuditStatus[]) {
    return createSelector([AuditState], (state: AuditStateModel) => {
      return state.audits.filter(x => statuses.includes(x.status));
    });
  }

  static facCrit(id: number) {
    return createSelector([AuditState], (state: AuditStateModel) => {
      return state.facCrits.find(x => x.id === id);
    });
  }

  static facCritByReferenceId(id: number) {
    return createSelector([AuditState], (state: AuditStateModel) => {
      return state.facCrits.filter(x => x.referenceId === id);
    });
  }

  @Action(AddAudit)
  addAudit({ setState }: StateContext<AuditStateModel>, { audit }: AddAudit) {
    this.auditService.postAudit(audit).subscribe(audit => {
      setState(
        patch({
          audits: append<Audit>([audit]),
        }),
      );
    });
  }

  @Action(UpdateAudit)
  updateAudit(ctx: StateContext<AuditStateModel>, { id, audit }: UpdateAudit) {
    const oldAudit = ctx.getState().audits.find(audit => audit.id === id);

    this.auditService.putAudit(oldAudit, { ...audit, id }).subscribe(() => {
      const a = ctx.getState().audits.find(x => x.id === id);

      ctx.setState(
        patch({
          audits: updateItem<Audit>(x => x.id === id, { ...a, ...audit }),
        }),
      );
    });
  }
}
