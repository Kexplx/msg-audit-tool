import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

export enum StoreActionType {
  Add,
  Edit,
  Load,
}

export interface StoreAction {
  message: string;
  type: StoreActionType;
}

@Injectable({
  providedIn: 'root',
})
export class StoreActionService {
  private readonly _storeAction$ = new Subject<StoreAction>();

  get storeAction$() {
    return this._storeAction$.asObservable();
  }

  notify(action: StoreAction) {
    this._storeAction$.next(action);
  }
}
