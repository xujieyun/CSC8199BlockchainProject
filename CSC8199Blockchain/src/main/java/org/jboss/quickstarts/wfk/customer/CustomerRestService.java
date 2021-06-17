package org.jboss.quickstarts.wfk.customer;


import io.swagger.annotations.*;

import org.jboss.quickstarts.wfk.contact.UniqueEmailException;
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
 * @see javax.ws.rs.core.Response
 */
@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/customers", description = "Operations about customers")
@Stateless
public class CustomerRestService {
    @Inject
    private @Named("logger") Logger log;
    @Inject
    private CustomerService service;

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
        //Create an empty collection to contain the intersection of customers to be returned
        List<Customer> customers;

        if(firstname == null && lastname == null) {
            customers = service.findAllOrderedByName();
        } else if(lastname == null) {
                customers = service.findAllByFirstName(firstname);
        } else if(firstname == null) {
                customers = service.findAllByLastName(lastname);
        } else {
                customers = service.findAllByFirstName(firstname);
                customers.retainAll(service.findAllByLastName(lastname));
        }

        return Response.ok(customers).build();
    }

/*    @GET
    @ApiOperation(value = "Fetch Contacts Imformation", notes = "Returns a JSON array of all stored Contacts Imformation objects.")
    public Response retrieveUserPassword(@QueryParam("userName") String userName, @QueryParam("password") String password) {
        //Create an empty collection to contain the intersection of customers to be returned
        List<Customer> customers;

        if(userName == null && password == null) {
            customers = service.findOrderedByName();
        } else if(userName == null) {
                customers = service.findByUserName(userName);
        } else if(password == null) {
                customers = service.findByPassword(password);
        } else {
                customers = service.findByUserName(userName);
                customers.retainAll(service.findByPassword(password));
        }

        return Response.ok(customers).build();
    }*/
    
    /**
     * <p>Search for and return a Customer identified by email address.<p/>
     *
     * <p>Path annotation includes very simple regex to differentiate between email addresses and Ids.
     * <strong>DO NOT</strong> attempt to use this regex to validate email addresses.</p>
     *
     *
     * @param email The string parameter value provided as a Customer's email
     * @return A Response containing a single Customer
     */
    /*@GET
    @Cache
    @Path("/username/{username:[a-z-A-Z]+}")
    @ApiOperation(
            value = "Fetch a Customer by Username",
            notes = "Returns a JSON representation of the Customer object with the provided username."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message ="Customer found"),
            @ApiResponse(code = 404, message = "Customer with username not found")
    })
    public Response retrieveCustomersByUsername(
            @ApiParam(value = "Username of Customer to be fetched", required = true)
            @QueryParam("username") String username, @QueryParam("password") String password) {
    	@PathParam("username")
        String username, @PathParam("password") String password
    	
    	List<Customer> customers;
        try {
            customers = service.findAllByFirstName(username);
            customers.retainAll(service.findAllByLastName(password));
        } catch (NoResultException e) {
            // Verify that the customer exists. Return 404, if not present.
            throw new RestServiceException("No Customer with the username " + username + " was found!", Response.Status.NOT_FOUND);
        }
        return Response.ok(customers).build();
    }*/
    @GET
    @Path("/customer")
    @ApiOperation(value = "Fetch all Customers", notes = "Returns a JSON array of all stored Contact objects.")
    public Response retrieveAllCustomers(@QueryParam("username") String username, @QueryParam("password") String password) {
        //Create an empty collection to contain the intersection of customers to be returned
        List<Customer> customers;

        if(username == null && password == null) {
            customers = service.findAllOrderedByName();
        } else if(password == null) {
                customers = service.findAllByUserName(username);
        } else if(username == null) {
                customers = service.findAllByPassword(password);
        } else {
                customers = service.findAllByUserName(username);
                customers.retainAll(service.findAllByPassword(password));
        }

        return Response.ok(customers).build();
    }
    
    /**
     * <p>Search for and return a Customer identified by email address.<p/>
     *
     * <p>Path annotation includes very simple regex to differentiate between email addresses and Ids.
     * <strong>DO NOT</strong> attempt to use this regex to validate email addresses.</p>
     *
     *
     * @param email The string parameter value provided as a Customer's email
     * @return A Response containing a single Customer
     */
    @GET
    @Cache
    @Path("/email/{email:.+[%40|@].+}")
    @ApiOperation(
            value = "Fetch a Customer by Email",
            notes = "Returns a JSON representation of the Customer object with the provided email."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message ="Customer found"),
            @ApiResponse(code = 404, message = "Customer with email not found")
    })
    public Response retrieveCustomersByEmail(
            @ApiParam(value = "Email of Customer to be fetched", required = true)
            @PathParam("email")
            String email) {

        Customer customer;
        try {
            customer = service.findByEmail(email);
        } catch (NoResultException e) {
            // Verify that the customer exists. Return 404, if not present.
            throw new RestServiceException("No Customer with the email " + email + " was found!", Response.Status.NOT_FOUND);
        }
        return Response.ok(customer).build();
    }

    /**
     * <p>Search for and return a customer identified by id.</p>
     *
     * @param id The long parameter value provided as a customer's id
     * @return A Response containing a single customer
     */
    @GET
    @Cache
    @Path("/{id:[0-9]+}")
    @ApiOperation(
            value = "Fetch a Customer by id",
            notes = "Returns a JSON representation of the Customer object with the provided id."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message ="Customer found"),
            @ApiResponse(code = 404, message = "Customer with id not found")
    })
    public Response retrieveCustomerById(
            @ApiParam(value = "Id of Customer to be fetched", allowableValues = "range[0, infinity]", required = true)
            @PathParam("id")
            long id) {

        Customer customer = service.findById(id);
        if (customer == null) {
            // Verify that the customer exists. Return 404, if not present.
            throw new RestServiceException("No Customer with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }
        log.info("findById " + id + ": found Customer = " + customer.toString());

        return Response.ok(customer).build();
    }

    /**
     * <p>Creates a new customer from the values provided. Performs validation and will return a JAX-RS response with
     * either 201 (Resource created) or with a map of fields, and related errors.</p>
     *
     * @param customer The Customer object, constructed automatically from JSON input, to be <i>created</i> via
     * {@link CustomerService#create(Customer)}
     * @return A Response indicating the outcome of the create operation
     */
    @SuppressWarnings("unused")
    @POST
    @ApiOperation(value = "Add a new Customer to the database")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Customer created successfully."),
            @ApiResponse(code = 400, message = "Invalid Customer supplied in request body"),
            @ApiResponse(code = 409, message = "Customer supplied in request body conflicts with an existing Customer"),
            @ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request")
    })
    public Response createCustomer(
            @ApiParam(value = "JSON representation of Customer object to be added to the database", required = true)
                    Customer customer) {


        if (customer == null) {
            throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
        }

        Response.ResponseBuilder builder;

        try {
            // Go add the new customer.
            service.create(customer);

            // Create a "Resource Created" 201 Response and pass the customer back in case it is needed.
            builder = Response.status(Response.Status.CREATED).entity(customer);


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

        log.info("createCustomer completed. Customer = " + customer.toString());
        return builder.build();
    }

    /**
     * <p>Updates the customer with the ID provided in the database. Performs validation, and will return a JAX-RS response
     * with either 200 (ok), or with a map of fields, and related errors.</p>
     *
     * @param customer The customer object, constructed automatically from JSON input, to be <i>updated</i> via
     * {@link customerService#update(customer)}
     * @param id The long parameter value provided as the id of the customer to be updated
     * @return A Response indicating the outcome of the create operation
     */
    @PUT
    @Path("/{id:[0-9]+}")
    @ApiOperation(value = "Update a Customer in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer updated successfully"),
            @ApiResponse(code = 400, message = "Invalid Customer supplied in request body"),
            @ApiResponse(code = 404, message = "Customer with id not found"),
            @ApiResponse(code = 409, message = "Customer details supplied in request body conflict with another existing Customer"),
            @ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request")
    })
    public Response updateCustomer(
            @ApiParam(value = "Id of Customer to be updated", allowableValues = "range[0, infinity]", required = true)
            @PathParam("id")
            long id,
            @ApiParam(value = "JSON representation of Customer object to be updated in the database", required = true)
                    Customer customer) {

        if (customer == null || customer.getId() == null) {
            throw new RestServiceException("Invalid Customer supplied in request body", Response.Status.BAD_REQUEST);
        }

        if (customer.getId() != null && customer.getId() != id) {
            // The client attempted to update the read-only Id. This is not permitted.
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("id", "The Customer ID in the request body must match that of the Customer being updated");
            throw new RestServiceException("Customer details supplied in request body conflict with another Customer",
                    responseObj, Response.Status.CONFLICT);
        }

        if (service.findById(customer.getId()) == null) {
            // Verify that the customer exists. Return 404, if not present.
            throw new RestServiceException("No Customer with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }

        Response.ResponseBuilder builder;

        try {
            // Apply the changes the Customer.
            service.update(customer);

            // Create an OK Response and pass the customer back in case it is needed.
            builder = Response.ok(customer);


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
            throw new RestServiceException("Customer details supplied in request body conflict with another Customer",
                    responseObj, Response.Status.CONFLICT, e);
        } catch (Exception e) {
            // Handle generic exceptions
            throw new RestServiceException(e);
        }

        log.info("updateCustomer completed. Customer = " + customer.toString());
        return builder.build();
    }

    /**
     * <p>Deletes a customer using the ID provided. If the ID is not present then nothing can be deleted.</p>
     *
     * <p>Will return a JAX-RS response with either 204 NO CONTENT or with a map of fields, and related errors.</p>
     *
     * @param id The Long parameter value provided as the id of the Customer to be deleted
     * @return A Response indicating the outcome of the delete operation
     */
    @DELETE
    @Path("/{id:[0-9]+}")
    @ApiOperation(value = "Delete a Customer from the database")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The customer has been successfully deleted"),
            @ApiResponse(code = 400, message = "Invalid Customer id supplied"),
            @ApiResponse(code = 404, message = "Customer with id not found"),
            @ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request")
    })
    public Response deleteCustomer(
            @ApiParam(value = "Id of Customer to be deleted", allowableValues = "range[0, infinity]", required = true)
            @PathParam("id")
            long id) {

        Response.ResponseBuilder builder;

        Customer customer = service.findById(id);
        if (customer == null) {
            // Verify that the customer exists. Return 404, if not present.
            throw new RestServiceException("No Customer with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }

        try {
            service.delete(customer);

            builder = Response.noContent();

        } catch (Exception e) {
            // Handle generic exceptions
            throw new RestServiceException(e);
        }
        log.info("deleteCustomer completed. Customer = " + customer.toString());
        return builder.build();
    }
}
