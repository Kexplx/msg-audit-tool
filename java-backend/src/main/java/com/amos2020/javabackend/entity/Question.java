package com.amos2020.javabackend.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Question {
    private int questionId;
    private String questionTextDe;
    private Collection<Answer> answersByQuestionId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "question_id")
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Basic
    @Column(name = "question_text_de")
    public String getQuestionTextDe() {
        return questionTextDe;
    }

    public void setQuestionTextDe(String questionTextDe) {
        this.questionTextDe = questionTextDe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (questionId != question.questionId) return false;
        return Objects.equals(questionTextDe, question.questionTextDe);
    }

    @Override
    public int hashCode() {
        int result = questionId;
        result = 31 * result + (questionTextDe != null ? questionTextDe.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "questionByAnswerQuestionId")
    public Collection<Answer> getAnswersByQuestionId() {
        return answersByQuestionId;
    }

    public void setAnswersByQuestionId(Collection<Answer> answersByQuestionId) {
        this.answersByQuestionId = answersByQuestionId;
    }
}