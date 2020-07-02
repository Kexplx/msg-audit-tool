import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { compileTimeSwitchedString } from './connectionStrings';
import { InterviewDto } from './dtos/interview.dto';
import { map } from 'rxjs/operators';
import { Interview } from '../data/models/interview.model';
import { PostInterviewDto } from './dtos/post-interview.dto';
import { FacCrit } from '../data/models/faccrit.model';
import { parseTimestamp } from './helpers';

@Injectable({
  providedIn: 'root',
})
export class InterviewService {
  constructor(private http: HttpClient) {}

  /**
   * Sends a GET to ../interviews and returns an observable
   *
   * ../interviews returns a list of all interviews
   */
  getInterviews() {
    return this.http.get<InterviewDto[]>(compileTimeSwitchedString + 'interviews').pipe(
      map<InterviewDto[], Interview[]>(interviews => {
        return interviews.map(interviewDto => {
          const { endDate, startDate } = interviewDto;
          console.log(interviewDto.interviewedContactPersons);
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

  /**
   * Sends a POST to ../interviews and returns an observable
   *
   * POST ../interviews returns a list of all interviews
   * @param interview The interview to add
   * @param interviewScope The selected scope of facCrits for that interview
   */
  postInterview({ contactPersons, startDate, auditId }: Interview, interviewScope: FacCrit[]) {
    const interviewedContactPersonsDto: { id: number; role: string }[] = [];

    for (const iterator of contactPersons ?? []) {
      interviewedContactPersonsDto.push({ id: iterator.id, role: 'Default Role' });
    }

    const interviewScopeDto = interviewScope?.map(facCrit => facCrit.id);

    const postInterviewDto: PostInterviewDto = {
      auditId,
      startDate: parseTimestamp(startDate),
      interviewScope: interviewScopeDto,
      interviewedContactPersons: interviewedContactPersonsDto,
    };

    return this.http
      .post<InterviewDto>(compileTimeSwitchedString + 'interviews', postInterviewDto)
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
      );
  }
}
