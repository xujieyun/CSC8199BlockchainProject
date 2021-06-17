package org.jboss.quickstarts.wfk.record;

import org.jboss.quickstarts.wfk.area.AreaService;
import org.jboss.quickstarts.wfk.record.Record;
import org.jboss.quickstarts.wfk.record.RecordRepository;
import org.jboss.quickstarts.wfk.record.RecordValidator;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.logging.Logger;

public class RecordService {

    @Inject
    private @Named("logger") Logger log;

    @Inject
    private RecordValidator validator;

    @Inject
    private org.jboss.quickstarts.wfk.record.RecordRepository crud;

    private ResteasyClient client;

    /**
     * <p>Create a new client which will be used for our outgoing REST client communication</p>
     */
    public RecordService() {
        // Create client service instance to make REST requests to upstream service
        client = new ResteasyClientBuilder().build();
    }

    /**
     * <p>Returns a List of all persisted {@link Record} objects, sorted alphabetically by last name.<p/>
     *
     * @return List of Record objects
     */
    List<Record> findAllOrderedByName() {
        return crud.findAllOrderedByName();
    }

    /**
     * <p>Returns a single Record object, specified by a Long id.<p/>
     *
     * @param id The id field of the Record to be returned
     * @return The Record with the specified id
     */
    Record findById(Long id) {
        return crud.findById(id);
    }

    /**
     * <p>Returns a single Record object, specified by a String email.</p>
     *
     * <p>If there is more than one Record with the specified email, only the first encountered will be returned.<p/>
     *
     * @param email The email field of the Record to be returned
     * @return The first Record with the specified email
     */
    /*Record findByEmail(String email) {
        return crud.findByEmail(email);
    }*/

    /**
     * <p>Returns a single Record object, specified by a String firstName.<p/>
     *
     * @param firstName The firstName field of the Record to be returned
     * @return The first Record with the specified firstName
     */
    List<Record> findAllByFirstName(String firstName) {
        return crud.findAllByFirstName(firstName);
    }

    /**
     * <p>Returns a single Record object, specified by a String lastName.<p/>
     *
     * @param lastName The lastName field of the Records to be returned
     * @return The Records with the specified lastName
     */
    List<Record> findAllByLastName(String lastName) {
        return crud.findAllByLastName(lastName);
    }
    
    /**
     * <p>Returns a single Record object, specified by a String lastName.<p/>
     *
     * @param lastName The lastName field of the Records to be returned
     * @return The Records with the specified lastName
     */
    List<Record> findAllByUserName(String username) {
        return crud.findALLByUserName(username);
    }
    
    List<Record> findAllByPassword(String password) {
        return crud.findAllByPassword(password);
    }
    
    /**
     * <p>Returns a single Record object, specified by a String lastName.<p/>
     *
     * @param lastName The lastName field of the Records to be returned
     * @return The Records with the specified lastName
     */
/*    Record findByPassword(String password) {
        return crud.findByPassword(password);
    }*/
    /**
     * <p>Writes the provided Record object to the application database.<p/>
     *
     * <p>Validates the data in the provided Record object using a {@link RecordValidator} object.<p/>
     *
     * @param Record The Record object to be written to the database using a {@link RecordRepository} object
     * @return The Record object that has been successfully written to the application database
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    public Record create(Record record) throws ConstraintViolationException, ValidationException, Exception {
        log.info("RecordService.create() - Creating " + record.getFirstName() + " " + record.getLastName());
        
        // Check to make sure the data fits with the parameters in the Record model and passes validation.
        validator.validateRecord(record);

        // Write the record to the database.
        return crud.create(record);
    }

    /**
     * <p>Updates an existing Record object in the application database with the provided Record object.<p/>
     *
     * <p>Validates the data in the provided Record object using a RecordValidator object.<p/>
     *
     * @param Record The Record object to be passed as an update to the application database
     * @return The Record object that has been successfully updated in the application database
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    Record update(Record record) throws ConstraintViolationException, ValidationException, Exception {
        log.info("RecordService.update() - Updating " + record.getFirstName() + " " + record.getLastName());
        
        // Check to make sure the data fits with the parameters in the Record model and passes validation.
        validator.validateRecord(record);

        // Set client target location and define the proxy API class
        ResteasyWebTarget target = client.target("http://csc8104-states.b9ad.pro-us-east-1.openshiftapps.com");
        AreaService service = target.proxy(AreaService.class);

        // Either update the Record or add it if it can't be found.
        return crud.update(record);
    }

    /**
     * <p>Deletes the provided Record object from the application database if found there.<p/>
     *
     * @param Record The Record object to be removed from the application database
     * @return The Record object that has been successfully removed from the application database; or null
     * @throws Exception
     */
    Record delete(Record record) throws Exception {
        log.info("delete() - Deleting " + record.toString());

        Record deletedRecord = null;

        if (record.getId() != null) {
            deletedRecord = crud.delete(record);
        } else {
            log.info("delete() - No ID was found so can't Delete.");
        }

        return deletedRecord;
    }
}