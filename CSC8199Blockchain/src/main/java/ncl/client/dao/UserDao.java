package ncl.client.dao;

import ncl.client.entity.User;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

/**
 * @ClassName UserDao
 * @Description: TODO
 * @Author BENY
 * @Date 2020/3/10
 * @Version V1.0
 **/
public class UserDao {


    @Inject
    private @Named("logger")
    Logger log;

    @Inject
    private EntityManager em;


    public List<User> getUserByUserName(String userName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> user = criteria.from(User.class);
        criteria.select(user).where(cb.equal(user.get("userName"), userName));
        return em.createQuery(criteria).getResultList();
    }



}
