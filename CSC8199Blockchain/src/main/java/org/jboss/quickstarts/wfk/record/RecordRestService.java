package org.jboss.quickstarts.wfk.record;


import io.swagger.annotations.*;

import org.jboss.quickstarts.wfk.contact.UniqueEmailException;
import org.jboss.quickstarts.wfk.record.Record;
import org.jboss.quickstarts.wfk.record.RecordService;
import org.jboss.quickstarts.wfk.util.RestServiceException;
import org.jboss.resteasy.annotations.cache.Cache;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * <p>This class produces a RESTful service exposing the functionality of {@link ContactService}.</p>
 *
 * <p>The Path annotation defines this as a REST Web Service using JAX-RS.</p>
 *
 * <p>By placing the Consumes and Produces annotations at the class level the methods all default to JSON.  However, they
 * can be overriden by adding the Consumes or Produces annotations to the individual methods.</p>
 *
 * <p>It is Stateless to "inform the container that this RESTful web service should also be treated as an EJB and allow
 * transaction demarcation when accessing the database." - Antonio Goncalves</p>
 *
 * <p>The full path for accessing endpoints defined herein is: api/contacts/*</p>
 * 
 * @author Xujie
 * @see ContactService
 * @see Response
 */
@Path("/records")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/records", description = "Operations about records")
@Stateless
public class RecordRestService {
    @Inject
    private @Named("logger") Logger log;
    @Inject
    private org.jboss.quickstarts.wfk.record.RecordService service;

    /**
     * <p>Return all the Contacts.  They are sorted alphabetically by name.</p>
     *
     * <p>The url may optionally include query parameters specifying a Contact's name</p>
     *
     * <p>Examples: <pre>GET api/contacts?username=John</pre>, <pre>GET api/contacts?username=John&lastname=Smith</pre></p>
     *
     * @return A Response containing a list of Contacts
     */
    @GET
    @ApiOperation(value = "Fetch all Contacts", notes = "Returns a JSON array of all stored Contact objects.")
    public Response retrieveAllContacts(@QueryParam("firstname") String firstname, @QueryParam("lastname") String lastname) {
        //Create an empty collection to contain the intersection of records to be returned
        List<Record> record;

        if(firstname == null && lastname == null) {
            record = service.findAllOrderedByName();
        } else if(lastname == null) {
                record = service.findAllByFirstName(firstname);
        } else if(firstname == null) {
                record = service.findAllByLastName(lastname);
        } else {
                record = service.findAllByFirstName(firstname);
                record.retainAll(service.findAllByLastName(lastname));
        }

        return Response.ok(record).build();
    }

/*    @GET
    @ApiOperation(value = "Fetch Contacts Imformation", notes = "Returns a JSON array of all stored Contacts Imformation objects.")
    public Response retrieveUserPassword(@QueryParam("userName") String userName, @QueryParam("password") String password) {
        //Create an empty collection to contain the intersection of records to be returned
        List<Record> records;

        if(userName == null && password == null) {
            records = service.findOrderedByName();
        } else if(userName == null) {
                records = service.findByUserName(userName);
        } else if(password == null) {
                records = service.findByPassword(password);
        } else {
                records = service.findByUserName(userName);
                records.retainAll(service.findByPassword(password));
        }

        return Response.ok(records).build();
    }*/
    
    /**
     * <p>Search for and return a Record identified by email address.<p/>
     *
     * <p>Path annotation includes very simple regex to differentiate between email addresses and Ids.
     * <strong>DO NOT</strong> attempt to use this regex to validate email addresses.</p>
     *
     *
     * @param email The string parameter value provided as a Record's email
     * @return A Response containing a single Record
     */
    /*@GET
    @Cache
    @Path("/username/{username:[a-z-A-Z]+}")
    @ApiOperation(
            value = "Fetch a Record by Username",
            notes = "Returns a JSON representation of the Record object with the provided username."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message ="Record found"),
            @ApiResponse(code = 404, message = "Record with username not found")
    })
    public Response retrieveRecordsByUsername(
            @ApiParam(value = "Username of Record to be fetched", required = true)
            @QueryParam("username") String username, @QueryParam("password") String password) {
    	@PathParam("username")
        String username, @PathParam("password") String password
    	
    	List<Record> records;
        try {
            records = service.findAllByFirstName(username);
            records.retainAll(service.findAllByLastName(password));
        } catch (NoResultException e) {
            // Verify that the record exists. Return 404, if not present.
            throw new RestServiceException("No Record with the username " + username + " was found!", Response.Status.NOT_FOUND);
        }
        return Response.ok(records).build();
    }*/
    @GET
    @Path("/record")
    @ApiOperation(value = "Fetch all Records", notes = "Returns a JSON array of all stored Contact objects.")
    public Response retrieveAllRecords(@QueryParam("username") String username, @QueryParam("password") String password) {
        //Create an empty collection to contain the intersection of records to be returned
        List<Record> records;

        if(username == null && password == null) {
            records = service.findAllOrderedByName();
        } else if(password == null) {
                records = service.findAllByUserName(username);
        } else if(username == null) {
                records = service.findAllByPassword(password);
        } else {
                records = service.findAllByUserName(username);
                records.retainAll(service.findAllByPassword(password));
        }

        return Response.ok(records).build();
    }
    
    /**
     * <p>Search for and return a Record identified by email address.<p/>
     *
     * <p>Path annotation includes very simple regex to differentiate between email addresses and Ids.
     * <strong>DO NOT</strong> attempt to use this regex to validate email addresses.</p>
     *
     *
     * @param email The string parameter value provided as a Record's email
     * @return A Response containing a single Record
     */
    /*@GET
    @Cache
    @Path("/email/{email:.+[%40|@].+}")
    @ApiOperation(
            value = "Fetch a Record by Email",
            notes = "Returns a JSON representation of the Record object with the provided email."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message ="Record found"),
            @ApiResponse(code = 404, message = "Record with email not found")
    })
    public Response retrieveRecordsByEmail(
            @ApiParam(value = "Email of Record to be fetched", required = true)
            @PathParam("email")
            String email) {

        Record record;
        try {
            record = service.findByEmail(email);
        } catch (NoResultException e) {
            // Verify that the record exists. Return 404, if not present.
            throw new RestServiceException("No Record with the email " + email + " was found!", Response.Status.NOT_FOUND);
        }
        return Response.ok(record).build();
    }*/

    /**
     * <p>Search for and return a record identified by id.</p>
     *
     * @param id The long parameter value provided as a record's id
     * @return A Response containing a single record
     */
    @GET
    @Cache
    @Path("/{id:[0-9]+}")
    @ApiOperation(
            value = "Fetch a Record by id",
            notes = "Returns a JSON representation of the Record object with the provided id."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message ="Record found"),
            @ApiResponse(code = 404, message = "Record with id not found")
    })
    public Response retrieveRecordById(
            @ApiParam(value = "Id of Record to be fetched", allowableValues = "range[0, infinity]", required = true)
            @PathParam("id")
            long id) {

        Record record = service.findById(id);
        if (record == null) {
            // Verify that the record exists. Return 404, if not present.
            throw new RestServiceException("No Record with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }
        log.info("findById " + id + ": found Record = " + record.toString());

        return Response.ok(record).build();
    }

    /**
     * <p>Creates a new record from the values provided. Performs validation and will return a JAX-RS response with
     * either 201 (Resource created) or with a map of fields, and related errors.</p>
     *
     * @param record The Record object, constructed automatically from JSON input, to be <i>created</i> via
     * {@link RecordService#create(Record)}
     * @return A Response indicating the outcome of the create operation
     */
    @SuppressWarnings("unused")
    @POST
    @ApiOperation(value = "Add a new Record to the database")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Record created successfully."),
            @ApiResponse(code = 400, message = "Invalid Record supplied in request body"),
            @ApiResponse(code = 409, message = "Record supplied in request body conflicts with an existing Record"),
            @ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request")
    })
    public Response createRecord(
            @ApiParam(value = "JSON representation of Record object to be added to the database", required = true)
                    Record record) {


        if (record == null) {
            throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
        }

        Response.ResponseBuilder builder;

        try {
            // Go add the new record.
            service.create(record);

            // Create a "Resource Created" 201 Response and pass the record back in case it is needed.
            builder = Response.status(Response.Status.CREATED).entity(record);


        } catch (ConstraintViolationException ce) {
            //Handle bean validation issues
            Map<String, String> responseObj = new HashMap<>();

            for (ConstraintViolation<?> violation : ce.getConstraintViolations()) {
                responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, ce);

        } catch (UniqueEmailException e) {
            // Handle the unique constraint violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("email", "That email is already used, please use a unique email");
            throw new RestServiceException("Bad Request", responseObj, Response.Status.CONFLICT, e);
        }  catch (Exception e) {
            // Handle generic exceptions
            throw new RestServiceException(e);
        }

        log.info("createRecord completed. Record = " + record.toString());
        return builder.build();
    }

    /**
     * <p>Updates the record with the ID provided in the database. Performs validation, and will return a JAX-RS response
     * with either 200 (ok), or with a map of fields, and related errors.</p>
     *
     * @param record The record object, constructed automatically from JSON input, to be <i>updated</i> via
     * {@link recordService#update(record)}
     * @param id The long parameter value provided as the id of the record to be updated
     * @return A Response indicating the outcome of the create operation
     */
    @PUT
    @Path("/{id:[0-9]+}")
    @ApiOperation(value = "Update a Record in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Record updated successfully"),
            @ApiResponse(code = 400, message = "Invalid Record supplied in request body"),
            @ApiResponse(code = 404, message = "Record with id not found"),
            @ApiResponse(code = 409, message = "Record details supplied in request body conflict with another existing Record"),
            @ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request")
    })
    public Response updateRecord(
            @ApiParam(value = "Id of Record to be updated", allowableValues = "range[0, infinity]", required = true)
            @PathParam("id")
            long id,
            @ApiParam(value = "JSON representation of Record object to be updated in the database", required = true)
                    Record record) {

        if (record == null || record.getId() == null) {
            throw new RestServiceException("Invalid Record supplied in request body", Response.Status.BAD_REQUEST);
        }

        if (record.getId() != null && record.getId() != id) {
            // The client attempted to update the read-only Id. This is not permitted.
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("id", "The Record ID in the request body must match that of the Record being updated");
            throw new RestServiceException("Record details supplied in request body conflict with another Record",
                    responseObj, Response.Status.CONFLICT);
        }

        if (service.findById(record.getId()) == null) {
            // Verify that the record exists. Return 404, if not present.
            throw new RestServiceException("No Record with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }

        Response.ResponseBuilder builder;

        try {
            // Apply the changes the Record.
            service.update(record);

            // Create an OK Response and pass the record back in case it is needed.
            builder = Response.ok(record);


        } catch (ConstraintViolationException ce) {
            //Handle bean validation issues
            Map<String, String> responseObj = new HashMap<>();

            for (ConstraintViolation<?> violation : ce.getConstraintViolations()) {
                responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, ce);
        } catch (UniqueEmailException e) {
            // Handle the unique constraint violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("email", "That email is already used, please use a unique email");
            throw new RestServiceException("Record details supplied in request body conflict with another Record",
                    responseObj, Response.Status.CONFLICT, e);
        } catch (Exception e) {
            // Handle generic exceptions
            throw new RestServiceException(e);
        }

        log.info("updateRecord completed. Record = " + record.toString());
        return builder.build();
    }

    /**
     * <p>Deletes a record using the ID provided. If the ID is not present then nothing can be deleted.</p>
     *
     * <p>Will return a JAX-RS response with either 204 NO CONTENT or with a map of fields, and related errors.</p>
     *
     * @param id The Long parameter value provided as the id of the Record to be deleted
     * @return A Response indicating the outcome of the delete operation
     */
    @DELETE
    @Path("/{id:[0-9]+}")
    @ApiOperation(value = "Delete a Record from the database")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The record has been successfully deleted"),
            @ApiResponse(code = 400, message = "Invalid Record id supplied"),
            @ApiResponse(code = 404, message = "Record with id not found"),
            @ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request")
    })
    public Response deleteRecord(
            @ApiParam(value = "Id of Record to be deleted", allowableValues = "range[0, infinity]", required = true)
            @PathParam("id")
            long id) {

        Response.ResponseBuilder builder;

        Record record = service.findById(id);
        if (record == null) {
            // Verify that the record exists. Return 404, if not present.
            throw new RestServiceException("No Record with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }

        try {
            service.delete(record);

            builder = Response.noContent();

        } catch (Exception e) {
            // Handle generic exceptions
            throw new RestServiceException(e);
        }
        log.info("deleteRecord completed. Record = " + record.toString());
        return builder.build();
    }
}
