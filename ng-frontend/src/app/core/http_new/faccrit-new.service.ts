import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { FacCrit } from '../data/models/faccrit.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class FacCritNewService {
  private _facCrits$ = new BehaviorSubject<FacCrit[]>(null);

  public get facCrits$(): Observable<FacCrit[]> {
    return this._facCrits$.asObservable();
  }

  constructor(private http: HttpClient) {}

  getFacCrits(): void {
    this.http.get<FacCrit[]>(environment.baseUrl + 'faccrits').subscribe(facCrits => {
      this._facCrits$.next(facCrits);
    });
  }

  getFacCritsByInterviewId(id: number): Observable<FacCrit[]> {
    return this.http.get<FacCrit[]>(environment.baseUrl + 'faccrits/interview/' + id);
  }
}
