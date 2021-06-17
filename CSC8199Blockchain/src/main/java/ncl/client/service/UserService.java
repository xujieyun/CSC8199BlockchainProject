package ncl.client.service;

import ncl.client.dao.UserDao;
import ncl.client.entity.User;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Logger;

/**
 * @ClassName UserService
 * @Description: TODO
 * @Author BENY
 * @Date 2020/3/10
 * @Version V1.0
 **/
@Dependent
public class UserService {


    @Inject
    private @Named("logger")
    Logger log;


    @Inject
    private UserDao userDao;


    User getUserByUserName(String userName){
        return userDao.getUserByUserName(userName).get(0);
    }


}
