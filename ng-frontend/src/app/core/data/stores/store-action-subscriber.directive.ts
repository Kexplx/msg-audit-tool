import { Directive, OnInit } from '@angular/core';
import {
  StoreActionService,
  StoreAction,
  StoreActionType,
} from './store-action.service';
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

  private showToast(storeAction: StoreAction) {
    const { message, type } = storeAction;

    const icon =
      type === StoreActionType.Load
        ? 'download-outline'
        : type === StoreActionType.Edit
        ? 'edit-outline'
        : 'file-add-outline';

    this.toastService.show(message, 'Erfolg', { icon, position: 'bottom-end' as any });
  }
}
