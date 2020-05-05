package com.amos2020.javabackend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "answer", schema = "msg_audit_database")
@IdClass(AnswerEntityPK.class)
public class AnswerEntity {
    private int auditId;
    private int questionId;
    private Boolean answer;
    private String proof;
    private AuditProjectEntity auditProjectByAuditId;
    private QuestionEntity questionByQuestionId;

    @Id
    @Column(name = "audit_id")
    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    @Id
    @Column(name = "question_id")
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Basic
    @NotNull
    @Column(name = "answer")
    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    @Basic
    @Column(name = "proof")
    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerEntity that = (AnswerEntity) o;

        if (auditId != that.auditId) return false;
        if (questionId != that.questionId) return false;
        if (!Objects.equals(answer, that.answer)) return false;
        return Objects.equals(proof, that.proof);
    }

    @Override
    public int hashCode() {
        int result = auditId;
        result = 31 * result + questionId;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (proof != null ? proof.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "audit_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public AuditProjectEntity getAuditProjectByAuditId() {
        return auditProjectByAuditId;
    }

    public void setAuditProjectByAuditId(AuditProjectEntity auditProjectByAuditId) {
        this.auditProjectByAuditId = auditProjectByAuditId;
    }

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public QuestionEntity getQuestionByQuestionId() {
        return questionByQuestionId;
    }

    public void setQuestionByQuestionId(QuestionEntity questionByQuestionId) {
        this.questionByQuestionId = questionByQuestionId;
    }
}
