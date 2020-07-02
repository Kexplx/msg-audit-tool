export interface PostInterviewDto {
  auditId: number;
  startDate: string;
  interviewedContactPersons: { id: number; role: string }[];
  interviewScope: number[];
}
