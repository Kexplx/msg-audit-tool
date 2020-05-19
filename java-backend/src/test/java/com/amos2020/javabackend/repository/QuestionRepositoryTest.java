package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.Question;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository repository;

    private Question question;

    @Test
    public void insertValid() {
        question = new Question();
        question.setQuestionTextDe("TestFrage?");
        repository.save(question);
        Assert.assertTrue(repository.exists(Example.of(question)));
    }

    @Test
    public void insertTextNull() {
        question = new Question();
        question.setQuestionTextDe(null);
        repository.save(question);
        Assert.assertTrue(repository.exists(Example.of(question)));
    }

    @Test
    public void changeTextValid() {
        question = new Question();
        question.setQuestionTextDe("TestFrage?");
        Question tmp = repository.save(question);
        Assert.assertTrue(repository.exists(Example.of(question)));
        tmp.setQuestionTextDe("NeueTestFrage?");
        repository.save(tmp);
        Assert.assertTrue(repository.exists(Example.of(tmp)));
        Assert.assertEquals(tmp.getQuestionTextDe(),question.getQuestionTextDe());
    }

    @Test
    public void changeTextNull() {
        question = new Question();
        question.setQuestionTextDe("TestFrage?");
        Question tmp = repository.save(question);
        Assert.assertTrue(repository.exists(Example.of(question)));
        tmp.setQuestionTextDe(null);
        repository.save(tmp);
        Assert.assertTrue(repository.exists(Example.of(tmp)));
        Assert.assertNull(question.getQuestionTextDe());
    }
}
