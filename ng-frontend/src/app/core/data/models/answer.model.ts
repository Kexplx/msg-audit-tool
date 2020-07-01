export interface Answer {
  interviewId: number;
  facCritId: number;
  questionId: number;

  result?: boolean;
  responsible?: boolean;
  documentation?: boolean;
  procedure?: boolean;

  reason?: string;
  proof?: string;
  annotation?: string;
}
