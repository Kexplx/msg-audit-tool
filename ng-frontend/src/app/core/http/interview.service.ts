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
    this.testGetInterviews();
    return this.http.get<InterviewDto[]>(compileTimeSwitchedString + 'interviews').pipe(
      map<InterviewDto[], Interview[]>(interviews => {
        return interviews.map(interviewDto => {
          const { endDate, startDate } = interviewDto;
          console.log(interviewDto.interviewedPeople);
          return {
            ...interviewDto,
            contactPeople: interviewDto.interviewedPeople,
            endDate: new Date(endDate).getTime(),
            startDate: new Date(startDate).getTime(),
          };
        });
      }),
    );
  }

  testGetInterviews() {
    this.http.get(compileTimeSwitchedString + 'interviews').subscribe(x => console.log(x));
  }

  /**
   * Sends a POST to ../interviews and returns an observable
   *
   * POST ../interviews returns a list of all interviews
   * @param interview The interview to add
   * @param interviewScope The selected scope of facCrits for that interview
   */
  postInterview(
    { contactPeople, startDate, endDate, auditId }: Interview,
    interviewScope: FacCrit[],
  ) {
    const interviewedPeopleDto = {};

    for (const iterator of contactPeople ?? []) {
      interviewedPeopleDto[String(iterator.id)] = 'Default Role';
    }

    const interviewScopeDto = interviewScope?.map(facCrit => facCrit.id);

    const postInterviewDto: PostInterviewDto = {
      auditId,
      startDate: parseTimestamp(startDate),
      goal: 'Default Goal',
      endDate: parseTimestamp(new Date(2050, 5, 5).getTime()),
      interviewScope: interviewScopeDto,
      interviewedPeople: interviewedPeopleDto,
    };

    return this.http
      .post<InterviewDto>(compileTimeSwitchedString + 'interviews', postInterviewDto)
      .pipe(
        map<InterviewDto, Interview>(interviewDto => {
          const { endDate, startDate } = interviewDto;

          return {
            ...interviewDto,
            contactPeople: interviewDto.interviewedPeople,
            endDate: new Date(endDate).getTime(),
            startDate: new Date(startDate).getTime(),
          };
        }),
      );
  }
}
