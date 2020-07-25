import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FacCrit } from '../models/faccrit.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class FacCritService {
  constructor(private http: HttpClient) {}

  getFacCrits(): Observable<FacCrit[]> {
    return this.http.get<FacCrit[]>(environment.baseUrl + 'faccrits');
  }

  getFacCritsByInterviewId(id: number): Observable<FacCrit[]> {
    return this.http.get<FacCrit[]>(environment.baseUrl + 'faccrits/interview/' + id);
  }
}
