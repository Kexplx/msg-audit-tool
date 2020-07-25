import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Answer } from '../models/answer.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AnswerService {
  constructor(private http: HttpClient) {}

  getAnswers(): Observable<Answer[]> {
    const url = environment.baseUrl + 'answers';
    return this.http.get<Answer[]>(url);
  }

  getAnswersByInterviewId(interviewId: number): Observable<Answer[]> {
    const url = environment.baseUrl + 'answers/interview/' + interviewId;
    return this.http.get<Answer[]>(url);
  }

  putAnswer(answer: Answer): Observable<Answer> {
    console.log(answer);
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
