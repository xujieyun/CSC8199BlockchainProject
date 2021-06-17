package org.jboss.quickstarts.wfk.record;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.jboss.quickstarts.wfk.contact.UniqueEmailException;
import org.jboss.quickstarts.wfk.record.Record;
import org.jboss.quickstarts.wfk.record.RecordRepository;

public class RecordValidator {
    @Inject
    private Validator validator;

    @Inject
    private RecordRepository crud;

    /**
     * <p>Validates the given Record object and throws validation exceptions based on the type of error. If the error is standard
     * bean validation errors then it will throw a ConstraintValidationException with the set of the constraints violated.<p/>
     *
     *
     * <p>If the error is caused because an existing Record with the same email is registered it throws a regular validation
     * exception so that it can be interpreted separately.</p>
     *
     *
     * @param record The Record object to be validated
     * @throws ConstraintViolationException If Bean Validation errors exist
     * @throws ValidationException If Record with the same email already exists
     */
    void validateRecord(Record record) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Record>> violations = validator.validate(record);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        // Check the uniqueness of the email address
        /*if (emailAlreadyExists(record.getEmail(), record.getId())) {
            throw new UniqueEmailException("Unique Email Violation");
        }*/
    }

    /**
     * <p>Checks if a Record with the same email address is already registered. This is the only way to easily capture the
     * "@UniqueConstraint(columnNames = "email")" constraint from the Record class.</p>
     *
     * <p>Since Update will being using an email that is already in the database we need to make sure that it is the email
     * from the record being updated.</p>
     *
     * @param email The email to check is unique
     * @param id The user id to check the email against if it was found
     * @return boolean which represents whether the email was found, and if so if it belongs to the user with id
     */
    /*boolean emailAlreadyExists(String email, Long id) {
        Record record = null;
        Record recordWithID = null;
        try {
            record = crud.findByEmail(email);
        } catch (NoResultException e) {
            // ignore
        }

        *//*if (record != null && id != null) {
            try {
                recordWithID = crud.findById(id);
                if (recordWithID != null && recordWithID.getEmail().equals(email)) {
                    record = null;
                }
            } catch (NoResultException e) {
                // ignore
            }
        }*//*
        return record != null;
    }*/


}
