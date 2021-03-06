package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.FacCrit;
import com.amos2020.javabackend.entity.Question;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuestionRepositoryTest {

    private static String TOO_LONG = new String(new char[20000]).replace('\0', 'H');
    @Autowired
    FacCritRepository facCritRepository;
    @Autowired
    private QuestionRepository repository;
    private Question question;
    private FacCrit facCrit;

    @Before
    public void setup() {
        facCrit = new FacCrit();
        facCrit.setName("TestFaccrit");
        facCrit.setGoal("TestGoal");
        facCritRepository.save(facCrit);
    }

    @Test
    public void insertValid() {
        question = new Question();
        question.setTextDe("TestFrage?");
        question.setFaccritId(facCrit.getId());
        repository.save(question);
        Assert.assertTrue(repository.exists(Example.of(question)));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertTextTooLong() {
        question = new Question();
        question.setTextDe(TOO_LONG);
        question.setFaccritId(facCrit.getId());
        repository.save(question);
    }

    @Test(expected = TransactionSystemException.class)
    public void insertTextNull() {
        question = new Question();
        question.setTextDe(null);
        question.setFaccritId(facCrit.getId());
        repository.save(question);
        Assert.assertTrue(repository.exists(Example.of(question)));
    }

    @Test
    public void changeTextValid() {
        question = new Question();
        question.setTextDe("TestFrage?");
        question.setFaccritId(facCrit.getId());
        Question tmp = repository.save(question);
        Assert.assertTrue(repository.exists(Example.of(question)));
        tmp.setTextDe("NeueTestFrage?");
        repository.save(tmp);
        Assert.assertTrue(repository.exists(Example.of(tmp)));
        Assert.assertEquals(tmp.getTextDe(), question.getTextDe());
    }

    @Test(expected = TransactionSystemException.class)
    public void changeTextNull() {
        question = new Question();
        question.setTextDe("TestFrage?");
        question.setFaccritId(facCrit.getId());
        Question tmp = repository.save(question);
        Assert.assertTrue(repository.exists(Example.of(question)));
        tmp.setTextDe(null);
        repository.save(tmp);
        Assert.assertTrue(repository.exists(Example.of(tmp)));
        Assert.assertNull(question.getTextDe());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void changeTextTooLong() {
        question = new Question();
        question.setTextDe("TestFrage?");
        question.setFaccritId(facCrit.getId());
        Question tmp = repository.save(question);
        Assert.assertTrue(repository.exists(Example.of(question)));
        tmp.setTextDe(TOO_LONG);
        repository.save(tmp);
    }
}
