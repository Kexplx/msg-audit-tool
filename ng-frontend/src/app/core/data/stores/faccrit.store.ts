import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { FacCrit } from '../models/faccrit.model';
import { FacCritService } from '../http/facCrit.service';

@Injectable({
  providedIn: 'root',
})
export class FacCritStore {
  private _facCrits$ = new BehaviorSubject<FacCrit[]>(null);

  public get facCrits$(): Observable<FacCrit[]> {
    return this._facCrits$.asObservable();
  }

  constructor(private facCritService: FacCritService) {}

  loadFacCrits(): void {
    this.facCritService.getFacCrits().subscribe(facCrits => {
      this._facCrits$.next(facCrits);
    });
  }

  /**
   * Returns logically grouped facCrits.
   *
   * @example
   * ['1 Factor', '2 Factor', '1.1 Criteria']
   *
   * // Will return
   * ['1 Factor', '1.1 Criteria', '2 Factor']
   */
  getGroupedFacCrits(facCritIds: number[]): FacCrit[] {
    const facCrits = this._facCrits$.value.filter(fc => facCritIds.includes(fc.id));
    return facCrits.sort((a, b) => this.getNumberFromFacCrit(a) - this.getNumberFromFacCrit(b));
  }

  private getNumberFromFacCrit(facCrit: FacCrit): number {
    const numberRegex = /(\d)(.(\d))?/;
    const execArray = numberRegex.exec(facCrit.name);

    return Number(execArray[1] + String(execArray[3] ? execArray[3] : '0'));
  }
}
