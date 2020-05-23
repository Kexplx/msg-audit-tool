package com.amos2020.javabackend.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class AnswerPK implements Serializable {
    private int questionId;
    private int interviewId;

    @Column(name = "question_id")
    @Id
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Column(name = "interview_id")
    @Id
    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerPK answerPK = (AnswerPK) o;

        if (questionId != answerPK.questionId) return false;
        return interviewId == answerPK.interviewId;
    }

    @Override
    public int hashCode() {
        int result = questionId;
        result = 31 * result + interviewId;
        return result;
    }
}
