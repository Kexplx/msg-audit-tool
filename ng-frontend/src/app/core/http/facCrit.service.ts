import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FacCrit } from '../data/models/faccrit.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class FacCritService {
  constructor(private http: HttpClient) {}

  /**
   * Builds an observable for making a GET request to get all facCrits.
   *
   * @returns An Observable of facCrits.
   */
  getFacCrits(): Observable<FacCrit[]> {
    return this.http.get<FacCrit[]>(environment.baseUrl + 'faccrits');
  }

  /**
   * Builds an observable for making a GET request to get facCrits that belong to an interview.
   *
   * @param id The interview's id.
   * @returns An Observable of facCrits.
   */
  getFacCritsByInterviewId(id: number): Observable<FacCrit[]> {
    return this.http.get<FacCrit[]>(environment.baseUrl + 'faccrits/interview/' + id);
  }
}
