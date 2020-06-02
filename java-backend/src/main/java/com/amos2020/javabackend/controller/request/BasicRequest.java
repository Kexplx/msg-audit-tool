package com.amos2020.javabackend.controller.request;

import org.junit.platform.commons.util.StringUtils;

import java.util.Date;
import java.util.List;

public abstract class BasicRequest {

    public abstract void isValid();

    /**
     * Asserts that an audit name cannot be null, empty, shorter than 3 oder longer than 45 chars
     *
     * @param auditName
     */
    public void assertAuditNameIsValid(String auditName) {
        if (auditName == null || StringUtils.isBlank(auditName)) {
            throw new IllegalArgumentException("audit name is null or empty");
        }
        if (auditName.length() < 3) {
            throw new IllegalArgumentException("audit name is too short");
        }
        if (auditName.length() > 45) {
            throw new IllegalArgumentException("audit name is too long");
        }
    }

    /**
     * Asserts that a change note cannot be null or longer than 256 chars
     *
     * @param changeNote
     */
    public void assertChangeNoteIsValid(String changeNote){
        if (changeNote != null && changeNote.length() > 256) {
            throw new IllegalArgumentException("Change note too long");
        }
    }

    /**
     * Asserts that a date cannot be null
     *
     * @param date
     */
    public void assertDateIsNotNull(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        }
    }

    /**
     * Asserts that enddate cannot be older than start date
     *
     * @param startDate
     * @param endDate
     */
    public void assertDatesAreValid(Date startDate, Date endDate) {
        if (endDate!= null && endDate.before(startDate)) {
            throw new IllegalArgumentException("end date cannot be older than start date");
        }
    }

    /**
     * Asserts that all ids in a list are valid
     *
     * @param ids
     */
    public void assertIdsAreValid(List<Integer> ids) {
        if(ids == null){
            return;
        }

        for (Integer id : ids) {
            assertIdIsValid(id);
        }
    }

    /**
     * Asserts that the given id is valid
     *
     * @param id
     */
    public void assertIdIsValid(Integer id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("invalid id");
        }
    }
}
