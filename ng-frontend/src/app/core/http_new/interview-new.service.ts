import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Interview } from '../data/models/interview.model';
import { FacCrit } from '../data/models/faccrit.model';
import { parseTimestamp } from 'src/app/core/data/helpers/date-helpers';
import { Observable, BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { InterviewDto } from '../http/dtos/interview.dto';
import { PostInterviewDto } from '../http/dtos/post-interview.dto';
import { PutInterviewDto } from '../http/dtos/put-interview.dto';

@Injectable({
  providedIn: 'root',
})
export class InterviewNewService {
  private _interviews$ = new BehaviorSubject<Interview[]>(null);

  get interviews$() {
    return this._interviews$.asObservable();
  }

  constructor(private http: HttpClient) {}

  getInterviews(): void {
    const url = environment.baseUrl + 'interviews';

    this.http
      .get<InterviewDto[]>(url)
      .pipe(
        map<InterviewDto[], Interview[]>(interviewDtos => {
          return interviewDtos.map(interviewDto => {
            const { endDate, startDate } = interviewDto;
            return {
              ...interviewDto,
              contactPersons: interviewDto.interviewedContactPersons,
              endDate: new Date(endDate).getTime(),
              startDate: new Date(startDate).getTime(),
            };
          });
        }),
      )
      .subscribe(interviews => {
        this._interviews$.next(interviews);
      });
  }

  getInterviewsByAuditId(auditId: number): Observable<Interview[]> {
    const url = environment.baseUrl + 'audits/' + auditId + '/interviews';

    return this.http.get<InterviewDto[]>(url).pipe(
      map<InterviewDto[], Interview[]>(interviewDtos => {
        return interviewDtos.map(interviewDto => {
          const { endDate, startDate } = interviewDto;
          return {
            ...interviewDto,
            contactPersons: interviewDto.interviewedContactPersons,
            endDate: new Date(endDate).getTime(),
            startDate: new Date(startDate).getTime(),
          };
        });
      }),
    );
  }

  getInterview(id: number): Observable<Interview> {
    const url = environment.baseUrl + 'interviews/' + id;

    return this.http.get<InterviewDto>(url).pipe(
      map<InterviewDto, Interview>(interviewDto => {
        const { endDate, startDate } = interviewDto;
        return {
          ...interviewDto,
          contactPersons: interviewDto.interviewedContactPersons,
          endDate: new Date(endDate).getTime(),
          startDate: new Date(startDate).getTime(),
        };
      }),
    );
  }

  postInterview(
    { contactPersons, startDate, auditId }: Interview,
    interviewScope: FacCrit[],
  ): void {
    const url = environment.baseUrl + 'interviews';
    const interviewedContactPersonsDto: { id: number; role: string }[] = [];

    for (const iterator of contactPersons ?? []) {
      interviewedContactPersonsDto.push({ id: iterator.id, role: 'Default Role' });
    }

    const interviewScopeDto = interviewScope?.map(facCrit => facCrit.id);

    const postInterviewDto: PostInterviewDto = {
      auditId,
      note: '',
      startDate: parseTimestamp(startDate),
      interviewScope: interviewScopeDto,
      interviewedContactPersons: interviewedContactPersonsDto,
    };

    this.http
      .post<InterviewDto>(url, postInterviewDto)
      .pipe(
        map<InterviewDto, Interview>(interviewDto => {
          const { endDate, startDate } = interviewDto;

          return {
            ...interviewDto,
            contactPersons: interviewDto.interviewedContactPersons,
            endDate: new Date(endDate).getTime(),
            startDate: new Date(startDate).getTime(),
          };
        }),
      )
      .subscribe(interview => {
        const interviews = this._interviews$.value;
        this._interviews$.next([...interviews, interview]);
      });
  }

  putInterview({ startDate, endDate, status, note, id }: Interview): void {
    const url = environment.baseUrl + 'interviews/' + id;

    const putInterviewDto: PutInterviewDto = {
      endDate: parseTimestamp(endDate),
      startDate: parseTimestamp(startDate),
      note,
      status,
    };

    this.http
      .put<InterviewDto>(url, putInterviewDto)
      .pipe(
        map<InterviewDto, Interview>(interviewDto => {
          const { endDate, startDate } = interviewDto;

          return {
            ...interviewDto,
            contactPersons: interviewDto.interviewedContactPersons,
            endDate: new Date(endDate).getTime(),
            startDate: new Date(startDate).getTime(),
          };
        }),
      )
      .subscribe(interview => {
        const interviews = this._interviews$.value;
        const indexOfUpdatedInterview = interviews.findIndex(i => i.id === interview.id);

        this._interviews$.next([
          ...interviews.slice(0, indexOfUpdatedInterview),
          interview,
          ...interviews.slice(indexOfUpdatedInterview + 1),
        ]);
      });
  }
}
