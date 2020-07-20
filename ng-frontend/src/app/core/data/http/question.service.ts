import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Question } from '../models/question.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class QuestionService {
  constructor(private http: HttpClient) {}

  /**
   * Builds an observable for making a GET request to get a question.
   *
   * @param id The question's id.
   * @returns An Observable of the question.
   */
  getQuestion(id: number): Observable<Question> {
    const url = environment.baseUrl + 'questions/' + id;

    return this.http.get<Question>(url);
  }
}
