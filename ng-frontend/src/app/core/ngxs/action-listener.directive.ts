import { Directive, OnInit } from '@angular/core';
import { Actions } from '@ngxs/store';
import { NbToastrService } from '@nebular/theme';
import { AddAudit, UpdateAudit } from 'src/app/core/ngxs/actions/audit.actions';
import {
  UpdateContactPerson,
  AddContactPerson,
} from 'src/app/core/ngxs/actions/contact-person.action';
import { filter, map, debounceTime } from 'rxjs/operators';
import {
  AddInterview,
  UpdateAnswer,
  UpdateInterview,
} from 'src/app/core/ngxs/actions/inteview.actions';
@Directive({
  selector: '[appActionListener]',
})
export class ActionListenerDirective implements OnInit {
  constructor(private actions$: Actions, private toastrService: NbToastrService) {}

  /**
   * Subscribes to successfull NGXS Actions and shows a Toast at the bottom right.
   */
  ngOnInit() {
    this.actions$
      .pipe(
        filter((x: { action: Object; status: string }) => x.status === 'SUCCESSFUL'),
        debounceTime(250), // debounce time to only show "Antworten aktualisiert" once.
        map(x => x.action),
      )
      .subscribe((action: any) => {
        switch (true) {
          case action instanceof AddAudit:
            this.showToast(
              `Neuen Audit: ${action.audit?.name} erstellt`,
              'checkmark-circle-2-outline',
            );
            break;
          case action instanceof UpdateAudit:
            this.showToast(`Audit: ${action.audit?.name} bearbeitet`, 'edit-outline');
            break;
          case action instanceof AddInterview:
            this.showToast(`Neues Interview erstellt`, 'checkmark-circle-2-outline');
            break;
          case action instanceof UpdateInterview:
            this.showToast(`Interview wurde aktualisiert`, 'edit-outline');
            break;
          case action instanceof AddContactPerson:
            this.showToast(
              `Kontaktperson ${action.contactPerson.forename} ${action.contactPerson.surname} erstellt`,
              'checkmark-circle-2-outline',
            );
            break;
          case action instanceof UpdateContactPerson:
            this.showToast(
              `Kontaktperson ${action.contactPerson.forename} ${action.contactPerson.surname} bearbeitet`,
              'edit-outline',
            );
            break;
          case action instanceof UpdateAnswer:
            this.showToast(`Antworten wurden aktualisiert`, 'edit-outline');
            break;
        }
      });
  }

  showToast(text: string, icon: string) {
    const position: any = 'bottom-end';
    this.toastrService.show(text, 'Erfolg', { position, icon, status: 'basic' });
  }
}
