export interface PostInterviewDto {
  auditId: number;
  startDate: string;
  goal: string;
  interviewedContactPersons: { id: number; role: string }[];
  interviewScope: number[];
}
