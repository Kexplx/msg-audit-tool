import { Directive, OnInit } from '@angular/core';
import { StoreActionService, StoreAction, StoreActionType } from './store-action.service';
import { NbToastrService } from '@nebular/theme';

@Directive({
  selector: '[appStoreActionSubscriber]',
})
export class StoreActionSubscriberDirective implements OnInit {
  constructor(
    private storeActionService: StoreActionService,
    private toastService: NbToastrService,
  ) {}

  ngOnInit(): void {
    this.storeActionService.storeAction$.subscribe(storeAction => this.showToast(storeAction));
  }

  private showToast({ message, type }: StoreAction): void {
    let icon: string;
    switch (type) {
      case StoreActionType.Load:
        icon = 'download-outline';
        break;
      case StoreActionType.Edit:
        icon = 'edit-outline';
        break;
      case StoreActionType.Add:
        icon = 'file-add-outline';
        break;
      default:
        icon = 'question-mark-outline';
    }

    this.toastService.show(message, 'Erfolg', { icon, position: 'bottom-end' as any });
  }
}
