import { Directive, OnInit } from '@angular/core';
import { Actions, ofActionCompleted } from '@ngxs/store';
import { NbToastrService } from '@nebular/theme';
import { AddAudit, DeleteAudit } from '../ngxs/audit.actions';

@Directive({
  selector: '[appActionListener]',
})
export class ActionListenerDirective implements OnInit {
  constructor(private actions$: Actions, private toastrService: NbToastrService) {}

  ngOnInit() {
    this.actions$.pipe(ofActionCompleted(AddAudit)).subscribe(x => {
      const status = `Neuen Audit "${x.action.audit.name}" erstellt`;
      const position: any = 'bottom-end';
      this.toastrService.show(status, 'Erfolg', { position, icon: 'checkmark-circle-2-outline' });
    });

    this.actions$.pipe(ofActionCompleted(DeleteAudit)).subscribe(x => {
      const status = `Audit "${x.action.audit.name}" gelöscht`;
      const position: any = 'bottom-end';
      this.toastrService.show(status, 'Erfolg', { position, icon: 'trash-outline' });
    });
  }
}
