package com.amos2020.javabackend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "question", schema = "msg_audit_database")
public class QuestionEntity {
    private int id;
    private String question;
    private CriteriaEntity criteriaByCriteriaId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @NotNull
    @Column(name = "question")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionEntity that = (QuestionEntity) o;

        if (id != that.id) return false;
        return Objects.equals(question, that.question);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (question != null ? question.hashCode() : 0);
        return result;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "criteria_id", referencedColumnName = "id", nullable = false)
    public CriteriaEntity getCriteriaByCriteriaId() {
        return criteriaByCriteriaId;
    }

    public void setCriteriaByCriteriaId(CriteriaEntity criteriaByCriteriaId) {
        this.criteriaByCriteriaId = criteriaByCriteriaId;
    }
}
