package com.amos2020.javabackend.entity;

import com.amos2020.javabackend.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Question {
    private int id;
    private int faccritId;
    private String textDe;

    private Collection<Answer> answersById;
    private FacCrit facCritByFaccritId;

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
    @Column(name = "faccrit_id")
    public int getFaccritId() {
        return faccritId;
    }

    public void setFaccritId(int faccritId) {
        this.faccritId = faccritId;
    }

    @Basic
    @NotNull
    @Column(name = "text_de", length = Constants.NAME_LENGTH)
    public String getTextDe() {
        return textDe;
    }

    public void setTextDe(String textDe) {
        this.textDe = textDe;
    }

    @OneToMany(mappedBy = "questionByQuestionId")
    public Collection<Answer> getAnswersById() {
        return answersById;
    }

    public void setAnswersById(Collection<Answer> answersById) {
        this.answersById = answersById;
    }

    @ManyToOne
    @JoinColumn(name = "faccrit_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public FacCrit getFacCritByFaccritId() {
        return facCritByFaccritId;
    }

    public void setFacCritByFaccritId(FacCrit facCritByFaccritId) {
        this.facCritByFaccritId = facCritByFaccritId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (id != question.id) return false;
        if (faccritId != question.faccritId) return false;
        return Objects.equals(textDe, question.textDe);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + faccritId;
        result = 31 * result + (textDe != null ? textDe.hashCode() : 0);
        return result;
    }
}
