import { Component, Input } from '@angular/core';
import { Interview, InterviewStatus } from 'src/app/core/data/models/interview.model';

@Component({
  selector: 'app-interview-card',
  templateUrl: './interview-card.component.html',
  styleUrls: ['./interview-card.component.scss'],
})
export class InterviewCardComponent {
  @Input() interview: Interview;
  @Input() facCritId: string;

  interviewStatuses = InterviewStatus;
}
