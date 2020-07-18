package com.amos2020.javabackend.integration_tests;

import com.amos2020.javabackend.JavaBackendApplication;
import com.amos2020.javabackend.entity.*;
import com.amos2020.javabackend.repository.ContactPersonRepository;
import com.amos2020.javabackend.repository.FacCritRepository;
import com.amos2020.javabackend.repository.QuestionRepository;
import com.amos2020.javabackend.rest_service.request.audit.CreateAuditRequest;
import com.amos2020.javabackend.rest_service.request.audit.DeleteAuditRequest;
import com.amos2020.javabackend.rest_service.request.audit.UpdateAuditRequest;
import com.amos2020.javabackend.rest_service.request.audit.UpdateAuditScopeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = JavaBackendApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class AuditIntegrationTest {

    private static final String VALID_NAME = "TestAuditName";
    private static final String NULL_STRING = null;
    private static final String BLANK_STRING = "   ";
    private static final String STRING_257 = StringUtils.repeat("*", 257);
    private static final String STRING_1025 = StringUtils.repeat("*", 1025);
    private static final String STRING_8097 = StringUtils.repeat("*", 8097);
    private static final String STRING_256 = StringUtils.repeat("*", 256);
    private static final String STRING_1024 = StringUtils.repeat("*", 1024);
    private static final String STRING_8096 = StringUtils.repeat("*", 8096);
    private static final Date VALID_START_DATE = Date.valueOf("2020-06-22");
    private static final Date VALID_END_DATE = Date.valueOf("2020-06-22");
    private static final Date INVALID_END_DATE = Date.valueOf("2020-06-21");
    private static final Date NULL_DATE = null;
    private static final String UPDATED_NAME = "Update Name";
    private static final Date UPDATED_START_DATE = Date.valueOf("2000-01-01");
    private static final Date UPDATED_END_DATE = Date.valueOf("2000-02-02");
    private static final AuditStatus UPDATED_AUDIT_STATUS = AuditStatus.ACTIVE;
    private static final Salutation TEST_SALUTATION = Salutation.HERR;
    private static final String TEST_TITLE = "TestTitle";
    private static final String TEST_INFORMATION = "0123456789, valid@email.com";
    private static final String TEST_FORENAME = "Jon";
    private static final String TEST_SURNAME = "Doe";
    private static final String TEST_COMPANY = "testCompany";
    private static final String TEST_DEPARTMENT = "testDepartment";
    private static final String TEST_SECTOR = "testSector";
    private static final String TEST_CORPORATE_DIVISION = "testDivision";
    List<Integer> facCritList = new ArrayList<>();
    List<FacCrit> facCrits = new ArrayList<>();
    List<Integer> contacts = new ArrayList<>();
    List<ContactPerson> contactPersons = new ArrayList<>();
    @Autowired
    private MockMvc mvc;
    @Autowired
    private FacCritRepository facCritRepository;
    @Autowired
    private ContactPersonRepository contactPersonRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Before
    public void setup() {
        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setSalutation(TEST_SALUTATION);
        contactPerson.setTitle(TEST_TITLE);
        contactPerson.setContactInformation(TEST_INFORMATION);
        contactPerson.setForename(TEST_FORENAME);
        contactPerson.setSurname(TEST_SURNAME);
        contactPerson.setCompanyName(TEST_COMPANY);
        contactPerson.setDepartment(TEST_DEPARTMENT);
        contactPerson.setSector(TEST_SECTOR);
        contactPerson.setCorporateDivision(TEST_CORPORATE_DIVISION);
        contactPerson = contactPersonRepository.save(contactPerson);

        contacts.add(contactPerson.getId());
        contactPersons.add(contactPerson);

        FacCrit facCrit = new FacCrit();
        facCrit.setName("Effektivität");
        facCrit = facCritRepository.save(facCrit);

        facCritList.add(facCrit.getId());
        facCrits.add(facCrit);

        Question question = new Question();
        question.setTextDe("Frage 1");
        question.setFaccritId(facCrit.getId());
        questionRepository.save(question);
    }


    @Test
    public void createAuditWithValidRequest_returnsOk() throws Exception {
        CreateAuditRequest request = getCreateAuditRequest(VALID_NAME, VALID_START_DATE, VALID_END_DATE, facCritList, contacts);
        String requestAsJson = buildJson(request);

        mvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(VALID_NAME))
                .andExpect(jsonPath("$.startDate").value(VALID_START_DATE.toString()))
                .andExpect(jsonPath("$.endDate").value(VALID_END_DATE.toString()))
                .andExpect(jsonPath("$.status").value(AuditStatus.OPEN.toString()))
                .andExpect(jsonPath("$.scope", hasSize(1)))
                .andExpect(jsonPath("$.scope[*].id", containsInAnyOrder(facCrits.get(0).getId())))
                .andExpect(jsonPath("$.scope[*].referenceId", containsInAnyOrder(facCrits.get(0).getReferenceId())))
                .andExpect(jsonPath("$.scope[*].name", containsInAnyOrder(facCrits.get(0).getName())))
                .andExpect(jsonPath("$.contactPersons", hasSize(1)))
                .andExpect(jsonPath("$.contactPersons[*].id", containsInAnyOrder(contactPersons.get(0).getId())))
                .andExpect(jsonPath("$.contactPersons[*].salutation", containsInAnyOrder(contactPersons.get(0).getSalutation().toString())))
                .andExpect(jsonPath("$.contactPersons[*].title", containsInAnyOrder(contactPersons.get(0).getTitle())))
                .andExpect(jsonPath("$.contactPersons[*].forename", containsInAnyOrder(contactPersons.get(0).getForename())))
                .andExpect(jsonPath("$.contactPersons[*].surname", containsInAnyOrder(contactPersons.get(0).getSurname())))
                .andExpect(jsonPath("$.contactPersons[*].contactInformation", containsInAnyOrder(contactPersons.get(0).getContactInformation())))
                .andExpect(jsonPath("$.contactPersons[*].companyName", containsInAnyOrder(contactPersons.get(0).getCompanyName())))
                .andExpect(jsonPath("$.contactPersons[*].department", containsInAnyOrder(contactPersons.get(0).getDepartment())))
                .andExpect(jsonPath("$.contactPersons[*].sector", containsInAnyOrder(contactPersons.get(0).getSector())))
                .andExpect(jsonPath("$.contactPersons[*].corporateDivision", containsInAnyOrder(contactPersons.get(0).getCorporateDivision())))
                .andReturn();
    }

    @Test
    public void createAuditWithAuditNameNull_returns400() throws Exception {
        CreateAuditRequest request = getCreateAuditRequest(NULL_STRING, VALID_START_DATE, VALID_END_DATE, facCritList, contacts);
        String requestAsJson = buildJson(request);

        mvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuditWithAuditNameBlank_returns400() throws Exception {
        CreateAuditRequest request = getCreateAuditRequest(BLANK_STRING, VALID_START_DATE, VALID_END_DATE, facCritList, contacts);
        String requestAsJson = buildJson(request);

        mvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuditWithAuditNameTooLong_returns400() throws Exception {
        CreateAuditRequest request = getCreateAuditRequest(STRING_257, VALID_START_DATE, VALID_END_DATE, facCritList, contacts);
        String requestAsJson = buildJson(request);

        mvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuditWithAuditNameIsMaximum_returns200() throws Exception {
        CreateAuditRequest request = getCreateAuditRequest(STRING_256, VALID_START_DATE, VALID_END_DATE, facCritList, contacts);
        String requestAsJson = buildJson(request);

        mvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void createAuditWithStartDateNull_returns400() throws Exception {
        CreateAuditRequest request = getCreateAuditRequest(VALID_NAME, NULL_DATE, VALID_END_DATE, facCritList, contacts);
        String requestAsJson = buildJson(request);

        mvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuditWithEndDateNull_returnsOk() throws Exception {
        CreateAuditRequest request = getCreateAuditRequest(VALID_NAME, VALID_START_DATE, NULL_DATE, facCritList, contacts);
        String requestAsJson = buildJson(request);

        mvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void createAuditWithInvalidDates_returns400() throws Exception {
        CreateAuditRequest request = getCreateAuditRequest(VALID_NAME, VALID_START_DATE, INVALID_END_DATE, facCritList, contacts);
        String requestAsJson = buildJson(request);

        mvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuditWithScopesNull_returnsOk() throws Exception {
        CreateAuditRequest request = getCreateAuditRequest(VALID_NAME, VALID_START_DATE, VALID_END_DATE, null, contacts);
        String requestAsJson = buildJson(request);

        mvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void createAuditWithScopesInvalid_returns400() throws Exception {
        facCritList.add(0);
        CreateAuditRequest request = getCreateAuditRequest(VALID_NAME, VALID_START_DATE, VALID_END_DATE, facCritList, contacts);
        String requestAsJson = buildJson(request);

        mvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuditWithContactsInvalid_returns400() throws Exception {
        contacts.add(0);
        CreateAuditRequest request = getCreateAuditRequest(VALID_NAME, VALID_START_DATE, VALID_END_DATE, facCritList, contacts);
        String requestAsJson = buildJson(request);

        mvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAuditWithContactsNull_returnsOk() throws Exception {
        CreateAuditRequest request = getCreateAuditRequest(VALID_NAME, VALID_START_DATE, VALID_END_DATE, facCritList, null);
        String requestAsJson = buildJson(request);

        mvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }

    @Test
    public void getAuditByIdWithValidId_returnsOk() throws Exception {
        String auditId = setupNewAudit();

        mvc.perform(get("/audits/" + auditId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(VALID_NAME))
                .andExpect(jsonPath("$.startDate").value(VALID_START_DATE.toString()))
                .andExpect(jsonPath("$.endDate").value(VALID_END_DATE.toString()))
                .andExpect(jsonPath("$.status").value(AuditStatus.OPEN.toString()))
                .andExpect(jsonPath("$.scope", hasSize(1)))
                .andExpect(jsonPath("$.scope[*].id", containsInAnyOrder(facCrits.get(0).getId())))
                .andExpect(jsonPath("$.scope[*].referenceId", containsInAnyOrder(facCrits.get(0).getReferenceId())))
                .andExpect(jsonPath("$.scope[*].name", containsInAnyOrder(facCrits.get(0).getName())))
                .andExpect(jsonPath("$.contactPersons", hasSize(1)))
                .andExpect(jsonPath("$.contactPersons[*].id", containsInAnyOrder(contactPersons.get(0).getId())))
                .andExpect(jsonPath("$.contactPersons[*].salutation", containsInAnyOrder(contactPersons.get(0).getSalutation().toString())))
                .andExpect(jsonPath("$.contactPersons[*].title", containsInAnyOrder(contactPersons.get(0).getTitle())))
                .andExpect(jsonPath("$.contactPersons[*].forename", containsInAnyOrder(contactPersons.get(0).getForename())))
                .andExpect(jsonPath("$.contactPersons[*].surname", containsInAnyOrder(contactPersons.get(0).getSurname())))
                .andExpect(jsonPath("$.contactPersons[*].contactInformation", containsInAnyOrder(contactPersons.get(0).getContactInformation())))
                .andExpect(jsonPath("$.contactPersons[*].companyName", containsInAnyOrder(contactPersons.get(0).getCompanyName())))
                .andExpect(jsonPath("$.contactPersons[*].department", containsInAnyOrder(contactPersons.get(0).getDepartment())))
                .andExpect(jsonPath("$.contactPersons[*].sector", containsInAnyOrder(contactPersons.get(0).getSector())))
                .andExpect(jsonPath("$.contactPersons[*].corporateDivision", containsInAnyOrder(contactPersons.get(0).getCorporateDivision())))
                .andReturn();
    }

    @Test
    public void getAuditByIdWithInvalidId_returnsNotFound() throws Exception {
        mvc.perform(get("/audits/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAuditByIdWithNotExistingId_returnsNotFound() throws Exception {
        mvc.perform(get("/audits/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuditByIdWithValidRequest_returnsOk() throws Exception {
        String auditId = setupNewAudit();

        UpdateAuditRequest request = getUpdateAuditRequest(UPDATED_NAME, UPDATED_START_DATE, UPDATED_END_DATE, UPDATED_AUDIT_STATUS);
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(UPDATED_NAME))
                .andExpect(jsonPath("$.startDate").value(UPDATED_START_DATE.toString()))
                .andExpect(jsonPath("$.endDate").value(UPDATED_END_DATE.toString()))
                .andExpect(jsonPath("$.status").value(UPDATED_AUDIT_STATUS.toString()))
                .andExpect(jsonPath("$.scope", hasSize(1)))
                .andExpect(jsonPath("$.scope[*].id", containsInAnyOrder(facCrits.get(0).getId())))
                .andExpect(jsonPath("$.scope[*].referenceId", containsInAnyOrder(facCrits.get(0).getReferenceId())))
                .andExpect(jsonPath("$.scope[*].name", containsInAnyOrder(facCrits.get(0).getName())))
                .andExpect(jsonPath("$.contactPersons", hasSize(1)))
                .andExpect(jsonPath("$.contactPersons[*].id", containsInAnyOrder(contactPersons.get(0).getId())))
                .andExpect(jsonPath("$.contactPersons[*].salutation", containsInAnyOrder(contactPersons.get(0).getSalutation().toString())))
                .andExpect(jsonPath("$.contactPersons[*].title", containsInAnyOrder(contactPersons.get(0).getTitle())))
                .andExpect(jsonPath("$.contactPersons[*].forename", containsInAnyOrder(contactPersons.get(0).getForename())))
                .andExpect(jsonPath("$.contactPersons[*].surname", containsInAnyOrder(contactPersons.get(0).getSurname())))
                .andExpect(jsonPath("$.contactPersons[*].contactInformation", containsInAnyOrder(contactPersons.get(0).getContactInformation())))
                .andExpect(jsonPath("$.contactPersons[*].companyName", containsInAnyOrder(contactPersons.get(0).getCompanyName())))
                .andExpect(jsonPath("$.contactPersons[*].department", containsInAnyOrder(contactPersons.get(0).getDepartment())))
                .andExpect(jsonPath("$.contactPersons[*].sector", containsInAnyOrder(contactPersons.get(0).getSector())))
                .andExpect(jsonPath("$.contactPersons[*].corporateDivision", containsInAnyOrder(contactPersons.get(0).getCorporateDivision())))
                .andReturn();
    }

    @Test
    public void updateAuditByIdWithAuditNotExisting_returnsNotFound() throws Exception {
        UpdateAuditRequest request = getUpdateAuditRequest(UPDATED_NAME, UPDATED_START_DATE, UPDATED_END_DATE, UPDATED_AUDIT_STATUS);
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuditByIdWithNameIsNull_returns400() throws Exception {
        String auditId = setupNewAudit();

        UpdateAuditRequest request = getUpdateAuditRequest(NULL_STRING, UPDATED_START_DATE, UPDATED_END_DATE, UPDATED_AUDIT_STATUS);
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithNameIsBlank_returns400() throws Exception {
        String auditId = setupNewAudit();

        UpdateAuditRequest request = getUpdateAuditRequest(BLANK_STRING, UPDATED_START_DATE, UPDATED_END_DATE, UPDATED_AUDIT_STATUS);
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void updateAuditByIdWithNameIsMaximum_returnsIsOk() throws Exception {
        String auditId = setupNewAudit();

        UpdateAuditRequest request = getUpdateAuditRequest(STRING_256, UPDATED_START_DATE, UPDATED_END_DATE, UPDATED_AUDIT_STATUS);
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());

    }

    @Test
    public void updateAuditByIdWithNameIsTooLong_returns400() throws Exception {
        String auditId = setupNewAudit();

        UpdateAuditRequest request = getUpdateAuditRequest(STRING_257, UPDATED_START_DATE, UPDATED_END_DATE, UPDATED_AUDIT_STATUS);
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithNameIsMinimum_returnsIsOk() throws Exception {
        String auditId = setupNewAudit();

        UpdateAuditRequest request = getUpdateAuditRequest("*", UPDATED_START_DATE, UPDATED_END_DATE, UPDATED_AUDIT_STATUS);
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk());
    }


    @Test
    public void updateAuditByIdWithStartDateIsNull_returns400() throws Exception {
        String auditId = setupNewAudit();

        UpdateAuditRequest request = getUpdateAuditRequest(UPDATED_NAME, NULL_DATE, UPDATED_END_DATE, UPDATED_AUDIT_STATUS);
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithEndDateIsBeforeStartDate_returns400() throws Exception {
        String auditId = setupNewAudit();

        UpdateAuditRequest request = getUpdateAuditRequest(UPDATED_NAME, VALID_START_DATE, INVALID_END_DATE, UPDATED_AUDIT_STATUS);
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditByIdWithAuditStatusIsNull_returns400() throws Exception {
        String auditId = setupNewAudit();

        UpdateAuditRequest request = getUpdateAuditRequest(UPDATED_NAME, UPDATED_START_DATE, UPDATED_END_DATE, null);
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void addContactPersonToAuditWithValidIds_returns200() throws Exception {
        String auditId = setupNewAudit();
        String contactPersonId = setupNewContactPerson();
        mvc.perform(put("/audits/" + auditId + "/contactpersons/" + contactPersonId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(VALID_NAME))
                .andExpect(jsonPath("$.startDate").value(VALID_START_DATE.toString()))
                .andExpect(jsonPath("$.endDate").value(VALID_END_DATE.toString()))
                .andExpect(jsonPath("$.status").value(AuditStatus.OPEN.toString()))
                .andExpect(jsonPath("$.scope", hasSize(1)))
                .andExpect(jsonPath("$.scope[*].id", containsInAnyOrder(facCrits.get(0).getId())))
                .andExpect(jsonPath("$.scope[*].referenceId", containsInAnyOrder(facCrits.get(0).getReferenceId())))
                .andExpect(jsonPath("$.scope[*].name", containsInAnyOrder(facCrits.get(0).getName())))
                .andExpect(jsonPath("$.contactPersons", hasSize(2)))
                .andExpect(jsonPath("$.contactPersons[0].id").value(contactPersons.get(0).getId()))
                .andExpect(jsonPath("$.contactPersons[0].salutation").value(contactPersons.get(0).getSalutation().toString()))
                .andExpect(jsonPath("$.contactPersons[0].title").value(contactPersons.get(0).getTitle()))
                .andExpect(jsonPath("$.contactPersons[0].forename").value(contactPersons.get(0).getForename()))
                .andExpect(jsonPath("$.contactPersons[0].surname").value(contactPersons.get(0).getSurname()))
                .andExpect(jsonPath("$.contactPersons[0].contactInformation").value(contactPersons.get(0).getContactInformation()))
                .andExpect(jsonPath("$.contactPersons[0].companyName").value(contactPersons.get(0).getCompanyName()))
                .andExpect(jsonPath("$.contactPersons[0].department").value(contactPersons.get(0).getDepartment()))
                .andExpect(jsonPath("$.contactPersons[0].sector").value(contactPersons.get(0).getSector()))
                .andExpect(jsonPath("$.contactPersons[0].corporateDivision").value(contactPersons.get(0).getCorporateDivision()))
                .andExpect(jsonPath("$.contactPersons[1].salutation").value(contactPersons.get(0).getSalutation().toString()))
                .andExpect(jsonPath("$.contactPersons[1].title").value(contactPersons.get(0).getTitle()))
                .andExpect(jsonPath("$.contactPersons[1].forename").value(contactPersons.get(0).getForename()))
                .andExpect(jsonPath("$.contactPersons[1].surname").value(contactPersons.get(0).getSurname()))
                .andExpect(jsonPath("$.contactPersons[1].contactInformation").value(contactPersons.get(0).getContactInformation()))
                .andExpect(jsonPath("$.contactPersons[1].companyName").value(contactPersons.get(0).getCompanyName()))
                .andExpect(jsonPath("$.contactPersons[1].department").value(contactPersons.get(0).getDepartment()))
                .andExpect(jsonPath("$.contactPersons[1].sector").value(contactPersons.get(0).getSector()))
                .andExpect(jsonPath("$.contactPersons[1].corporateDivision").value(contactPersons.get(0).getCorporateDivision()))
                .andReturn();
    }


    @Test
    public void addContactPersonToAuditWithInvalidAuditId_returns404() throws Exception {
        String contactPersonId = setupNewContactPerson();

        mvc.perform(put("/audits/0/contactpersons/" + contactPersonId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addContactPersonToAuditWithInvalidContactPersonId_returns404() throws Exception {
        String auditId = setupNewAudit();
        mvc.perform(put("/audits/" + auditId + "/contactpersons/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void removeContactPersonFromAuditWithValidIds_returns200() throws Exception {
        String auditId = setupNewAudit();
        mvc.perform(delete("/audits/" + auditId + "/contactpersons/" + contactPersons.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(VALID_NAME))
                .andExpect(jsonPath("$.startDate").value(VALID_START_DATE.toString()))
                .andExpect(jsonPath("$.endDate").value(VALID_END_DATE.toString()))
                .andExpect(jsonPath("$.status").value(AuditStatus.OPEN.toString()))
                .andExpect(jsonPath("$.scope", hasSize(1)))
                .andExpect(jsonPath("$.scope[*].id", containsInAnyOrder(facCrits.get(0).getId())))
                .andExpect(jsonPath("$.scope[*].referenceId", containsInAnyOrder(facCrits.get(0).getReferenceId())))
                .andExpect(jsonPath("$.scope[*].name", containsInAnyOrder(facCrits.get(0).getName())))
                .andExpect(jsonPath("$.contactPersons", hasSize(0)))
                .andReturn();
    }

    @Test
    public void removeContactPersonFromAuditWithInvalidAuditId_returns404() throws Exception {
        mvc.perform(delete("/audits/0/contactpersons/" + contactPersons.get(0).getId()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void removeContactPersonFromAuditWithInvalidContactPersonId_returns400() throws Exception {
        String auditId = setupNewAudit();
        mvc.perform(delete("/audits/" + auditId + "/contactpersons/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditScopeWithValidRequest_returnsOk() throws Exception {
        String auditId = setupNewAudit();
        UpdateAuditScopeRequest request = getUpdateScopeRequest(facCrits.get(0).getId(), "", true, "");
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId + "/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(VALID_NAME))
                .andExpect(jsonPath("$.startDate").value(VALID_START_DATE.toString()))
                .andExpect(jsonPath("$.endDate").value(VALID_END_DATE.toString()))
                .andExpect(jsonPath("$.status").value(AuditStatus.OPEN.toString()))
                .andExpect(jsonPath("$.scope", hasSize(0)))
                .andReturn();
    }

    @Test
    public void updateAuditScopeWithInvalidRequestChangeNoteIsTooLong_returns400() throws Exception {
        String auditId = setupNewAudit();
        UpdateAuditScopeRequest request = getUpdateScopeRequest(facCrits.get(0).getId(), STRING_1025, true, "");
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId + "/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditScopeWithInvalidRequestChangeNoteIsMaximum_returns200() throws Exception {
        String auditId = setupNewAudit();
        UpdateAuditScopeRequest request = getUpdateScopeRequest(facCrits.get(0).getId(), STRING_1024, true, "");
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId + "/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(VALID_NAME))
                .andExpect(jsonPath("$.startDate").value(VALID_START_DATE.toString()))
                .andExpect(jsonPath("$.endDate").value(VALID_END_DATE.toString()))
                .andExpect(jsonPath("$.status").value(AuditStatus.OPEN.toString()))
                .andExpect(jsonPath("$.scope", hasSize(0)))
                .andReturn();
    }

    @Test
    public void updateAuditScopeWithInvalidRequestNoteIsTooLong_returns400() throws Exception {
        String auditId = setupNewAudit();
        UpdateAuditScopeRequest request = getUpdateScopeRequest(facCrits.get(0).getId(), "", true, STRING_8097);
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId + "/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditScopeWithInvalidRequestNoteIsMaximum_returns200() throws Exception {
        String auditId = setupNewAudit();
        UpdateAuditScopeRequest request = getUpdateScopeRequest(facCrits.get(0).getId(), "", true, STRING_8096);
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId + "/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(VALID_NAME))
                .andExpect(jsonPath("$.startDate").value(VALID_START_DATE.toString()))
                .andExpect(jsonPath("$.endDate").value(VALID_END_DATE.toString()))
                .andExpect(jsonPath("$.status").value(AuditStatus.OPEN.toString()))
                .andExpect(jsonPath("$.scope", hasSize(0)))
                .andReturn();
    }

    @Test
    public void updateAuditScopeWithInvalidRequestFacCritIdIsInvalid_returns400() throws Exception {
        String auditId = setupNewAudit();
        UpdateAuditScopeRequest request = getUpdateScopeRequest(0, "", true, "");
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId + "/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateAuditScopeWithAuditNotExisting_returnsNotFound() throws Exception {
        UpdateAuditScopeRequest request = getUpdateScopeRequest(facCrits.get(0).getId(), "", true, "");
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/100/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuditScopeWithFacCritNotExisting_returnsNotFound() throws Exception {
        String auditId = setupNewAudit();
        UpdateAuditScopeRequest request = getUpdateScopeRequest(100, "", true, "");
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId + "/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuditScopeWithScopeItemAlreadyDeleted_returnsIllegalAccess() throws Exception {
        String auditId = setupNewAudit();
        UpdateAuditScopeRequest request = getUpdateScopeRequest(facCrits.get(0).getId(), "", true, "");
        String requestAsJson = buildJson(request);

        mvc.perform(put("/audits/" + auditId + "/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(VALID_NAME))
                .andExpect(jsonPath("$.startDate").value(VALID_START_DATE.toString()))
                .andExpect(jsonPath("$.endDate").value(VALID_END_DATE.toString()))
                .andExpect(jsonPath("$.status").value(AuditStatus.OPEN.toString()))
                .andExpect(jsonPath("$.scope", hasSize(0)))
                .andReturn();
        mvc.perform(put("/audits/" + auditId + "/scope")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteAuditValidRequest_returnsOK() throws Exception {
        String auditId = setupNewAudit();

        DeleteAuditRequest request = getDeleteAuditRequest(contactPersons.get(0).getId(), Date.valueOf("2020-06-22"), "reason");
        String requestAsJson = buildJson(request);

        mvc.perform(delete("/audits/" + auditId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(VALID_NAME))
                .andExpect(jsonPath("$.startDate").value(VALID_START_DATE.toString()))
                .andExpect(jsonPath("$.endDate").value(VALID_END_DATE.toString()))
                .andExpect(jsonPath("$.status").value(AuditStatus.CANCELED.toString()))
                .andExpect(jsonPath("$.scope", hasSize(1)))
                .andExpect(jsonPath("$.scope[*].id", containsInAnyOrder(facCrits.get(0).getId())))
                .andExpect(jsonPath("$.scope[*].referenceId", containsInAnyOrder(facCrits.get(0).getReferenceId())))
                .andExpect(jsonPath("$.scope[*].name", containsInAnyOrder(facCrits.get(0).getName())))
                .andExpect(jsonPath("$.contactPersons", hasSize(1)))
                .andExpect(jsonPath("$.contactPersons[*].id", containsInAnyOrder(contactPersons.get(0).getId())))
                .andExpect(jsonPath("$.contactPersons[*].salutation", containsInAnyOrder(contactPersons.get(0).getSalutation().toString())))
                .andExpect(jsonPath("$.contactPersons[*].title", containsInAnyOrder(contactPersons.get(0).getTitle())))
                .andExpect(jsonPath("$.contactPersons[*].forename", containsInAnyOrder(contactPersons.get(0).getForename())))
                .andExpect(jsonPath("$.contactPersons[*].surname", containsInAnyOrder(contactPersons.get(0).getSurname())))
                .andExpect(jsonPath("$.contactPersons[*].contactInformation", containsInAnyOrder(contactPersons.get(0).getContactInformation())))
                .andExpect(jsonPath("$.contactPersons[*].companyName", containsInAnyOrder(contactPersons.get(0).getCompanyName())))
                .andExpect(jsonPath("$.contactPersons[*].department", containsInAnyOrder(contactPersons.get(0).getDepartment())))
                .andExpect(jsonPath("$.contactPersons[*].sector", containsInAnyOrder(contactPersons.get(0).getSector())))
                .andExpect(jsonPath("$.contactPersons[*].corporateDivision", containsInAnyOrder(contactPersons.get(0).getCorporateDivision())))
                .andReturn();
    }

    @Test
    public void deleteAuditReasonBlank__returns400() throws Exception {
        String auditId = setupNewAudit();

        DeleteAuditRequest request = getDeleteAuditRequest(contactPersons.get(0).getId(), Date.valueOf("2020-06-22"), BLANK_STRING);
        String requestAsJson = buildJson(request);

        mvc.perform(delete("/audits/" + auditId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteAuditContactPersonInvalid_returns400() throws Exception {
        String auditId = setupNewAudit();

        DeleteAuditRequest request = getDeleteAuditRequest(0, Date.valueOf("2020-06-22"), "reason");
        String requestAsJson = buildJson(request);

        mvc.perform(delete("/audits/" + auditId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteAuditContactPersonNotExisting_returns400() throws Exception {
        String auditId = setupNewAudit();

        DeleteAuditRequest request = getDeleteAuditRequest(1000, Date.valueOf("2020-06-22"), "reason");
        String requestAsJson = buildJson(request);

        mvc.perform(delete("/audits/" + auditId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteAuditInvalidAuditId_returnsNotFound() throws Exception {
        DeleteAuditRequest request = getDeleteAuditRequest(contactPersons.get(0).getId(), Date.valueOf("2020-06-22"), "reason");
        String requestAsJson = buildJson(request);

        mvc.perform(delete("/audits/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isNotFound());

    }

    @Test
    public void getAllAudits() throws Exception {
        setupNewAudit();
        mvc.perform(get("/audits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)))
                .andReturn();

    }

    private String setupNewContactPerson() {
        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setSalutation(TEST_SALUTATION);
        contactPerson.setTitle(TEST_TITLE);
        contactPerson.setContactInformation(TEST_INFORMATION);
        contactPerson.setForename(TEST_FORENAME);
        contactPerson.setSurname(TEST_SURNAME);
        contactPerson.setCompanyName(TEST_COMPANY);
        contactPerson.setDepartment(TEST_DEPARTMENT);
        contactPerson.setSector(TEST_SECTOR);
        contactPerson.setCorporateDivision(TEST_CORPORATE_DIVISION);
        contactPerson = contactPersonRepository.save(contactPerson);
        return String.valueOf(contactPerson.getId());
    }

    private String setupNewAudit() throws Exception {
        CreateAuditRequest request = getCreateAuditRequest(VALID_NAME, VALID_START_DATE, VALID_END_DATE, facCritList, contacts);
        String requestAsJson = buildJson(request);

        String result = mvc.perform(post("/audits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestAsJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString().split(",")[0];

        return result.substring(result.indexOf(":") + 1);
    }

    private CreateAuditRequest getCreateAuditRequest(String auditName, Date startDate, Date endDate, List<Integer> facCritList, List<Integer> contacts) {
        CreateAuditRequest request = new CreateAuditRequest();
        request.setName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setScope(facCritList);
        request.setContactPersons(contacts);
        return request;
    }

    private UpdateAuditRequest getUpdateAuditRequest(String auditName, Date startDate, Date endDate, AuditStatus status) {
        UpdateAuditRequest request = new UpdateAuditRequest();
        request.setName(auditName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setStatus(status);
        return request;
    }

    private UpdateAuditScopeRequest getUpdateScopeRequest(int facCritId, String changeNote, boolean removed, String note) {
        UpdateAuditScopeRequest request = new UpdateAuditScopeRequest();
        request.setFacCritId(facCritId);
        request.setChangeNote(changeNote);
        request.setRemoved(removed);
        request.setNote(note);
        return request;
    }

    private DeleteAuditRequest getDeleteAuditRequest(int contactPersonId, Date date, String reason) {
        DeleteAuditRequest request = new DeleteAuditRequest();
        request.setContactPerson(contactPersonId);
        request.setDate(date);
        request.setReason(reason);
        return request;
    }

    private String buildJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        return mapper.writer().withDefaultPrettyPrinter().writeValueAsString(object);
    }

}
