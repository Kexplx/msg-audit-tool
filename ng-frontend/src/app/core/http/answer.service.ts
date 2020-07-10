import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Answer } from '../data/models/answer.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AnswerService {
  constructor(private http: HttpClient) {}

  /**
   * Builds an observable for making a GET request to get answers by their interview id.
   *
   * @param id The interviews's id.
   * @returns An Observable of the answers.
   */
  getAnswersByInterviewId(interviewId: number): Observable<Answer[]> {
    const url = environment.baseUrl + 'answers/' + 'interview/' + interviewId;
    return this.http.get<Answer[]>(url);
  }

  /**
   * Builds an observable for making a POST request to update an answer.
   *
   * @param answer The updated answer.
   * @returns An Observable of the answers.
   */
  putAnswer(answer: Answer): Observable<Answer> {
    const url =
      environment.baseUrl +
      'answers/' +
      'interview/' +
      answer.interviewId +
      '/question/' +
      answer.questionId;

    return this.http.put<Answer>(url, answer);
  }
}
