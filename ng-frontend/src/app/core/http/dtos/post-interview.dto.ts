export interface PostInterviewDto {
  auditId: number;
  startDate: string;
  note: string;
  interviewedContactPersons: { id: number; role: string }[];
  interviewScope: number[];
}
