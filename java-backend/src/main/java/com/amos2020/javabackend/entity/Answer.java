package com.amos2020.javabackend.entity;

import com.amos2020.javabackend.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@IdClass(AnswerPK.class)
public class Answer {
    private int questionId;
    private int interviewId;
    private int faccritId;
    private Boolean result;
    private Boolean responsible;
    private Boolean documentation;
    private Boolean procedure;
    private String reason;
    private String proof;
    private String annotation;

    @JsonIgnore
    private FacCrit facCritByFaccritId;
    @JsonIgnore
    private Question questionByQuestionId;
    @JsonIgnore
    private Interview interviewByInterviewId;

    @Id
    @Column(name = "question_id")
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Id
    @Column(name = "interview_id")
    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    @Basic
    @NotNull
    @Column(name = "faccrit_id")
    public int getFaccritId() {
        return faccritId;
    }

    public void setFaccritId(int faccritId) {
        this.faccritId = faccritId;
    }

    @Basic
    @Column(name = "result")
    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    @Basic
    @Column(name = "responsible")
    public Boolean getResponsible() {
        return responsible;
    }

    public void setResponsible(Boolean responsible) {
        this.responsible = responsible;
    }

    @Basic
    @Column(name = "documentation")
    public Boolean getDocumentation() {
        return documentation;
    }

    public void setDocumentation(Boolean documentation) {
        this.documentation = documentation;
    }

    @Basic
    @Column(name = "\"procedure\"")
    public Boolean getProcedure() {
        return procedure;
    }

    public void setProcedure(Boolean procedure) {
        this.procedure = procedure;
    }

    @Basic
    @Column(name = "reason", length = Constants.NOTE_LENGTH)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Basic
    @Column(name = "proof", length = Constants.NOTE_LENGTH)
    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    @Basic
    @Column(name = "annotation", length = Constants.NOTE_LENGTH)
    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    @ManyToOne
    @JoinColumn(name = "faccrit_id", referencedColumnName = "id", insertable = false, updatable = false)
    public FacCrit getFacCritByFaccritId() {
        return facCritByFaccritId;
    }

    public void setFacCritByFaccritId(FacCrit facCritByFaccritId) {
        this.facCritByFaccritId = facCritByFaccritId;
    }

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id", insertable = false, updatable = false)
    public Question getQuestionByQuestionId() {
        return questionByQuestionId;
    }

    public void setQuestionByQuestionId(Question questionByQuestionId) {
        this.questionByQuestionId = questionByQuestionId;
    }

    @ManyToOne
    @JoinColumn(name = "interview_id", referencedColumnName = "id", insertable = false, updatable = false)
    public Interview getInterviewByInterviewId() {
        return interviewByInterviewId;
    }

    public void setInterviewByInterviewId(Interview interviewByInterviewId) {
        this.interviewByInterviewId = interviewByInterviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (questionId != answer.questionId) return false;
        if (interviewId != answer.interviewId) return false;
        if (faccritId != answer.faccritId) return false;
        if (!Objects.equals(result, answer.result)) return false;
        if (!Objects.equals(responsible, answer.responsible)) return false;
        if (!Objects.equals(documentation, answer.documentation))
            return false;
        if (!Objects.equals(procedure, answer.procedure)) return false;
        if (!Objects.equals(reason, answer.reason)) return false;
        if (!Objects.equals(proof, answer.proof)) return false;
        return Objects.equals(annotation, answer.annotation);
    }

    @Override
    public int hashCode() {
        int result1 = questionId;
        result1 = 31 * result1 + interviewId;
        result1 = 31 * result1 + faccritId;
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (responsible != null ? responsible.hashCode() : 0);
        result1 = 31 * result1 + (documentation != null ? documentation.hashCode() : 0);
        result1 = 31 * result1 + (procedure != null ? procedure.hashCode() : 0);
        result1 = 31 * result1 + (reason != null ? reason.hashCode() : 0);
        result1 = 31 * result1 + (proof != null ? proof.hashCode() : 0);
        result1 = 31 * result1 + (annotation != null ? annotation.hashCode() : 0);
        return result1;
    }
}
