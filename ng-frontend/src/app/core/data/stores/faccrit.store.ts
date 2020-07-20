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

  groupedFacCrits(facCritIds: number[]) {
    const facCrits: FacCrit[] = this._facCrits$.value.filter(fc => facCritIds.includes(fc.id));
    const result: FacCrit[] = [];

    for (const factor of facCrits.filter(fc => !fc.referenceId)) {
      const criteriaOfFactor = facCrits
        .filter(fc => fc.referenceId === factor.id)
        .sort((a, b) => a.id - b.id);
      result.push(factor, ...criteriaOfFactor);
    }

    for (const criteria of facCrits.filter(fc => fc.referenceId)) {
      if (!result.find(fc => fc === criteria)) {
        result.push(criteria);
      }
    }

    result.sort((a, b) => {
      if (a.referenceId && b.referenceId) return a.referenceId - b.referenceId;
      if (!a.referenceId && b.referenceId) return a.id - b.referenceId;
      if (a.referenceId && !b.referenceId) return a.referenceId - b.id;
      if (!a.referenceId && !b.referenceId) return a.id - b.id;
    });

    return this.distinctFacCrits(result);
  }

  private distinctFacCrits(facCrits: FacCrit[]) {
    const distinctFacCrits: FacCrit[] = [];

    for (const facCrit of facCrits) {
      if (!distinctFacCrits.find(fc => fc.id === facCrit.id)) {
        distinctFacCrits.push(facCrit);
      }
    }

    return distinctFacCrits;
  }
}
