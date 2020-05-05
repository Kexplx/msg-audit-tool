package com.amos2020.javabackend.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class AnswerEntityPK implements Serializable {
    private int auditId;
    private int questionId;

    @Column(name = "audit_id")
    @Id
    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    @Column(name = "question_id")
    @Id
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerEntityPK that = (AnswerEntityPK) o;

        if (auditId != that.auditId) return false;
        return questionId == that.questionId;
    }

    @Override
    public int hashCode() {
        int result = auditId;
        result = 31 * result + questionId;
        return result;
    }
}
