package com.amos2020.javabackend.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class AnswerPK implements Serializable {
    private int answerFaccritId;
    private int answerQuestionId;
    private int answerInterviewId;

    @Column(name = "answer_faccrit_id")
    @Id
    public int getAnswerFaccritId() {
        return answerFaccritId;
    }

    public void setAnswerFaccritId(int answerFaccritId) {
        this.answerFaccritId = answerFaccritId;
    }

    @Column(name = "answer_question_id")
    @Id
    public int getAnswerQuestionId() {
        return answerQuestionId;
    }

    public void setAnswerQuestionId(int answerQuestionId) {
        this.answerQuestionId = answerQuestionId;
    }

    @Column(name = "answer_interview_id")
    @Id
    public int getAnswerInterviewId() {
        return answerInterviewId;
    }

    public void setAnswerInterviewId(int answerInterviewId) {
        this.answerInterviewId = answerInterviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerPK answerPK = (AnswerPK) o;

        if (answerFaccritId != answerPK.answerFaccritId) return false;
        if (answerQuestionId != answerPK.answerQuestionId) return false;
        if (answerInterviewId != answerPK.answerInterviewId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = answerFaccritId;
        result = 31 * result + answerQuestionId;
        result = 31 * result + answerInterviewId;
        return result;
    }
}
