package com.amos2020.javabackend.rest_service.request;

import org.junit.platform.commons.util.StringUtils;

import java.util.Date;
import java.util.List;

public abstract class BasicRequest {

    public static final int MAX_NAME_LENGTH = 256;
    public static final int MAX_SMALL_TEXT_LENGTH = 1024;
    public static final int MAX_TEXT_LENGTH = 8096;

    public abstract void isValid();

    /**
     * Asserts that an attribute that represents a name cannot be null, empty or longer than 256 chars
     *
     * @param name String
     */
    public void assertNameIsValid(String name) {
        if (name == null || StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("name is null or empty");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("name is too long");
        }
    }

    /**
     * Asserts that an attribute that represent a note cannot be null, empty or longer than 1024 chars
     *
     * @param note String
     */
    public void assertNoteIsValid(String note) {
        if (note == null) {
            throw new IllegalArgumentException("note is null or empty");
        }
        if (note.length() > MAX_SMALL_TEXT_LENGTH) {
            throw new IllegalArgumentException("note is too long");
        }
    }

    /**
     * Asserts that an attribute that represent a text cannot be null, empty or longer than 8096 chars
     *
     * @param note String
     */
    public void assertTextIsValid(String note) {
        if (note == null) {
            throw new IllegalArgumentException("note is null or empty");
        }
        if (note.length() > MAX_TEXT_LENGTH) {
            throw new IllegalArgumentException("note is too long");
        }
    }

    /**
     * Asserts that a date cannot be null
     *
     * @param date String
     */
    public void assertDateIsNotNull(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        }
    }

    /**
     * Asserts that end date cannot be older than start date
     *
     * @param startDate Date
     * @param endDate   Date
     */
    public void assertDatesAreValid(Date startDate, Date endDate) {
        if (endDate != null && endDate.before(startDate)) {
            throw new IllegalArgumentException("end date cannot be older than start date");
        }
    }

    /**
     * Asserts that all ids in a list are valid
     *
     * @param ids List<Integer>
     */
    public void assertIdsAreValid(List<Integer> ids) {
        if (ids == null) {
            return;
        }

        for (Integer id : ids) {
            assertIdIsValid(id);
        }
    }

    /**
     * Asserts that the given id is valid
     *
     * @param id Integer
     */
    public void assertIdIsValid(Integer id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("invalid id");
        }
    }
}

