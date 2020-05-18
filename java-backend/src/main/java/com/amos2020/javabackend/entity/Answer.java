package com.amos2020.javabackend.entity;

import javax.persistence.*;

@Entity
@IdClass(AnswerPK.class)
public class Answer {
    private int answerFaccritId;
    private int answerQuestionId;
    private int answerInterviewId;
    private Boolean answerResult;
    private Boolean answerResponsible;
    private Boolean answerDocumentation;
    private Boolean answerProcedure;
    private String answerReason;
    private String answerProof;
    private FacCrit facCritByAnswerFaccritId;
    private Question questionByAnswerQuestionId;
    private Interview interviewByAnswerInterviewId;

    @Id
    @Column(name = "answer_faccrit_id")
    public int getAnswerFaccritId() {
        return answerFaccritId;
    }

    public void setAnswerFaccritId(int answerFaccritId) {
        this.answerFaccritId = answerFaccritId;
    }

    @Id
    @Column(name = "answer_question_id")
    public int getAnswerQuestionId() {
        return answerQuestionId;
    }

    public void setAnswerQuestionId(int answerQuestionId) {
        this.answerQuestionId = answerQuestionId;
    }

    @Id
    @Column(name = "answer_interview_id")
    public int getAnswerInterviewId() {
        return answerInterviewId;
    }

    public void setAnswerInterviewId(int answerInterviewId) {
        this.answerInterviewId = answerInterviewId;
    }

    @Basic
    @Column(name = "answer_result")
    public Boolean getAnswerResult() {
        return answerResult;
    }

    public void setAnswerResult(Boolean answerResult) {
        this.answerResult = answerResult;
    }

    @Basic
    @Column(name = "answer_responsible")
    public Boolean getAnswerResponsible() {
        return answerResponsible;
    }

    public void setAnswerResponsible(Boolean answerResponsible) {
        this.answerResponsible = answerResponsible;
    }

    @Basic
    @Column(name = "answer_documentation")
    public Boolean getAnswerDocumentation() {
        return answerDocumentation;
    }

    public void setAnswerDocumentation(Boolean answerDocumentation) {
        this.answerDocumentation = answerDocumentation;
    }

    @Basic
    @Column(name = "answer_procedure")
    public Boolean getAnswerProcedure() {
        return answerProcedure;
    }

    public void setAnswerProcedure(Boolean answerProcedure) {
        this.answerProcedure = answerProcedure;
    }

    @Basic
    @Column(name = "answer_reason")
    public String getAnswerReason() {
        return answerReason;
    }

    public void setAnswerReason(String answerReason) {
        this.answerReason = answerReason;
    }

    @Basic
    @Column(name = "answer_proof")
    public String getAnswerProof() {
        return answerProof;
    }

    public void setAnswerProof(String answerProof) {
        this.answerProof = answerProof;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (answerFaccritId != answer.answerFaccritId) return false;
        if (answerQuestionId != answer.answerQuestionId) return false;
        if (answerInterviewId != answer.answerInterviewId) return false;
        if (answerResult != null ? !answerResult.equals(answer.answerResult) : answer.answerResult != null)
            return false;
        if (answerResponsible != null ? !answerResponsible.equals(answer.answerResponsible) : answer.answerResponsible != null)
            return false;
        if (answerDocumentation != null ? !answerDocumentation.equals(answer.answerDocumentation) : answer.answerDocumentation != null)
            return false;
        if (answerProcedure != null ? !answerProcedure.equals(answer.answerProcedure) : answer.answerProcedure != null)
            return false;
        if (answerReason != null ? !answerReason.equals(answer.answerReason) : answer.answerReason != null)
            return false;
        if (answerProof != null ? !answerProof.equals(answer.answerProof) : answer.answerProof != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = answerFaccritId;
        result = 31 * result + answerQuestionId;
        result = 31 * result + answerInterviewId;
        result = 31 * result + (answerResult != null ? answerResult.hashCode() : 0);
        result = 31 * result + (answerResponsible != null ? answerResponsible.hashCode() : 0);
        result = 31 * result + (answerDocumentation != null ? answerDocumentation.hashCode() : 0);
        result = 31 * result + (answerProcedure != null ? answerProcedure.hashCode() : 0);
        result = 31 * result + (answerReason != null ? answerReason.hashCode() : 0);
        result = 31 * result + (answerProof != null ? answerProof.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "answer_faccrit_id", referencedColumnName = "faccrit_id", nullable = false)
    public FacCrit getFacCritByAnswerFaccritId() {
        return facCritByAnswerFaccritId;
    }

    public void setFacCritByAnswerFaccritId(FacCrit facCritByAnswerFaccritId) {
        this.facCritByAnswerFaccritId = facCritByAnswerFaccritId;
    }

    @ManyToOne
    @JoinColumn(name = "answer_question_id", referencedColumnName = "question_id", nullable = false)
    public Question getQuestionByAnswerQuestionId() {
        return questionByAnswerQuestionId;
    }

    public void setQuestionByAnswerQuestionId(Question questionByAnswerQuestionId) {
        this.questionByAnswerQuestionId = questionByAnswerQuestionId;
    }

    @ManyToOne
    @JoinColumn(name = "answer_interview_id", referencedColumnName = "interview_id", nullable = false)
    public Interview getInterviewByAnswerInterviewId() {
        return interviewByAnswerInterviewId;
    }

    public void setInterviewByAnswerInterviewId(Interview interviewByAnswerInterviewId) {
        this.interviewByAnswerInterviewId = interviewByAnswerInterviewId;
    }
}
