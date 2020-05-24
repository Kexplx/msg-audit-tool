import { Directive, OnInit } from '@angular/core';
import { Actions, ofActionCompleted } from '@ngxs/store';
import { NbToastrService } from '@nebular/theme';
import { AddAudit, DeleteAudit, UpdateAudit } from 'src/app/core/ngxs/audit.actions';

@Directive({
  selector: '[appActionListener]',
})
export class ActionListenerDirective implements OnInit {
  constructor(private actions$: Actions, private toastrService: NbToastrService) {}

  /**
   * Listens to completed NGXS Actions and shows a Toast at the bottom right
   */
  ngOnInit() {
    this.actions$.pipe(ofActionCompleted(AddAudit)).subscribe(x => {
      this.showToast(`Neuen Audit: ${x.action.audit.name} erstellt`, 'checkmark-circle-2-outline');
    });

    this.actions$.pipe(ofActionCompleted(DeleteAudit)).subscribe(x => {
      this.showToast(`Audit: ${x.action.audit.name} gelöscht`, 'trash-outline');
    });

    this.actions$.pipe(ofActionCompleted(UpdateAudit)).subscribe(x => {
      this.showToast(`Audit: ${x.action.audit.name} bearbeitet`, 'edit-outline');
    });
  }

  showToast(text: string, icon: string) {
    const position: any = 'bottom-end';
    this.toastrService.show(text, 'Erfolg', { position, icon });
  }
}
