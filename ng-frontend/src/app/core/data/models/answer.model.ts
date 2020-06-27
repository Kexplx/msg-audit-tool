export interface Answer {
  interviewId: string;
  facCritId: string;
  questionId: string;

  result?: boolean;
  responsible?: boolean;
  documentation?: boolean;
  procedure?: boolean;

  reason?: string;
  proof?: string;
  annotation?: string;
}
