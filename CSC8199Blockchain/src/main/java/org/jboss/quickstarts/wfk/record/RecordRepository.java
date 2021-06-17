package org.jboss.quickstarts.wfk.record;

import org.jboss.quickstarts.wfk.record.Record;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

public class RecordRepository {

    @Inject
    private @Named("logger") Logger log;

    @Inject
    private EntityManager em;

    /**
     * <p>Returns a List of all persisted {@link Record} objects, sorted alphabetically by last name.</p>
     *
     * @return List of Customer objects
     */
    List<org.jboss.quickstarts.wfk.record.Record> findAllOrderedByName() {
        TypedQuery<Record> query = em.createNamedQuery(Record.FIND_ALL, Record.class);
        return query.getResultList();
    }

    /**
     * <p>Returns a single Customer object, specified by a Long id.<p/>
     *
     * @param id The id field of the Customer to be returned
     * @return The Customer with the specified id
     */
    Record findById(Long id) {
        return em.find(Record.class, id);
    }

    /**
     * <p>Returns a single Customer object, specified by a String email.</p>
     *
     * <p>If there is more than one Customer with the specified email, only the first encountered will be returned.<p/>
     *
     * @param email The email field of the Customer to be returned
     * @return The first Customer with the specified email
     */
    /*Record findByEmail(String email) {
        TypedQuery<Record> query = em.createNamedQuery(Record.FIND_BY_EMAIL, Record.class).setParameter("email", email);
        return query.getSingleResult();
    }*/

    /**
     * <p>Returns a list of Customer objects, specified by a String firstName.<p/>
     *
     * @param firstName The firstName field of the Customers to be returned
     * @return The Customers with the specified firstName
     */
    List<Record> findAllByFirstName(String firstName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Record> criteria = cb.createQuery(Record.class);
        Root<Record> customer = criteria.from(Record.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new feature in JPA 2.0.
        // criteria.select(customer).where(cb.equal(customer.get(customer_.firstName), firstName));
        criteria.select(customer).where(cb.equal(customer.get("firstName"), firstName));
        return em.createQuery(criteria).getResultList();
    }

    /**
     * <p>Returns a single Customer object, specified by a String lastName.<p/>
     *
     * @param lastName The lastName field of the Customers to be returned
     * @return The Customers with the specified lastName
     */
    List<Record> findAllByLastName(String lastName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Record> criteria = cb.createQuery(Record.class);
        Root<Record> customer = criteria.from(Record.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new feature in JPA 2.0.
        // criteria.select(customer).where(cb.equal(customer.get(Customer_.lastName), lastName));
        criteria.select(customer).where(cb.equal(customer.get("lastName"), lastName));
        return em.createQuery(criteria).getResultList();
    }

    /**
     * <p>Returns a single Customer object, specified by a String lastName.<p/>
     *
     * @param lastName The lastName field of the Customers to be returned
     * @return The Customers with the specified lastName
     */
    List<Record> findALLByUserName(String userName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Record> criteria = cb.createQuery(Record.class);
        Root<Record> customer = criteria.from(Record.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new feature in JPA 2.0.
        // criteria.select(customer).where(cb.equal(customer.get(Customer_.lastName), lastName));
        criteria.select(customer).where(cb.equal(customer.get("userName"), userName));
        return em.createQuery(criteria).getResultList();
    }
/*    Customer findByUserName(String username) {
        TypedQuery<Customer> query = em.createNamedQuery(Customer.FIND_BY_USERNAME, Customer.class).setParameter("username", username);
        return query.getSingleResult();
    }*/
    /**
     * <p>Returns a single Customer object, specified by a String lastName.<p/>
     *
     * @param lastName The lastName field of the Customers to be returned
     * @return The Customers with the specified lastName
     */
    List<Record> findAllByPassword(String password) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Record> criteria = cb.createQuery(Record.class);
        Root<Record> customer = criteria.from(Record.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new feature in JPA 2.0.
        // criteria.select(customer).where(cb.equal(customer.get(Customer_.lastName), lastName));
        criteria.select(customer).where(cb.equal(customer.get("password"), password));
        return em.createQuery(criteria).getResultList();
    }
    /**
     * <p>Persists the provided Customer object to the application database using the EntityManager.</p>
     *
     * <p>{@link EntityManager#persist(Object) persist(Object)} takes an entity instance, adds it to the
     * context and makes that instance managed (ie future updates to the entity will be tracked)</p>
     *
     * <p>persist(Object) will set the @GeneratedValue @Id for an object.</p>
     *
     * @param Record The Customer object to be persisted
     * @return The Customer object that has been persisted
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    Record create(Record customer) throws ConstraintViolationException, ValidationException, Exception {
        log.info("CustomerRepository.create() - Creating " + customer.getFirstName() + " " + customer.getLastName());

        // Write the Customer to the database.
        em.persist(customer);

        return customer;
    }

    /**
     * <p>Updates an existing Customer object in the application database with the provided Customer object.</p>
     *
     * <p>{@link EntityManager#merge(Object) merge(Object)} creates a new instance of your entity,
     * copies the state from the supplied entity, and makes the new copy managed. The instance you pass in will not be
     * managed (any changes you make will not be part of the transaction - unless you call merge again).</p>
     *
     * <p>merge(Object) however must have an object with the @Id already generated.</p>
     *
     * @param Record The Customer object to be merged with an existing Customer
     * @return The Customer that has been merged
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    Record update(Record customer) throws ConstraintViolationException, ValidationException, Exception {
        log.info("CustomerRepository.update() - Updating " + customer.getFirstName() + " " + customer.getLastName());

        // Either update the Customer or add it if it can't be found.
        em.merge(customer);

        return customer;
    }

    /**
     * <p>Deletes the provided Customer object from the application database if found there</p>
     *
     * @param Record The Customer object to be removed from the application database
     * @return The Customer object that has been successfully removed from the application database; or null
     * @throws Exception
     */
    Record delete(Record customer) throws Exception {
        log.info("CustomerRepository.delete() - Deleting " + customer.getFirstName() + " " + customer.getLastName());

        if (customer.getId() != null) {
            /*
             * The Hibernate session (aka EntityManager's persistent context) is closed and invalidated after the commit(), 
             * because it is bound to a transaction. The object goes into a detached status. If you open a new persistent 
             * context, the object isn't known as in a persistent state in this new context, so you have to merge it. 
             * 
             * Merge sees that the object has a primary key (id), so it knows it is not new and must hit the database 
             * to reattach it. 
             * 
             * Note, there is NO remove method which would just take a primary key (id) and a entity class as argument. 
             * You first need an object in a persistent state to be able to delete it.
             * 
             * Therefore we merge first and then we can remove it.
             */
            em.remove(em.merge(customer));

        } else {
            log.info("CustomerRepository.delete() - No ID was found so can't Delete.");
        }

        return customer;
    }

}
