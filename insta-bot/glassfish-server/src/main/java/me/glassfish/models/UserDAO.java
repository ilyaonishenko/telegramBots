package me.glassfish.models;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by woqpw on 28.10.15.
 */
public class UserDAO {
    private static  UserDAO userDAO;
    static MongoClient mongoClient = new MongoClient();
    final static Morphia morphia = new Morphia();
    static Datastore datastore  = morphia.createDatastore(mongoClient,"instabotdb");
    private UserDAO(){}
    private static Logger logger = LoggerFactory.getLogger(UserDAO.class);
    public static UserDAO getUserDAO(){
        if(userDAO==null)
            userDAO = new UserDAO();
        morphia.map(User.class);
        return userDAO;
    }
    public static void addUser(User u){
        if(checkStatus(u)) {
            logger.info("no such user in db {}",u.getUsername());
            datastore.save(u);
        }
    }
    private static boolean checkStatus(User u){
        logger.info("user is {}",u.toString());
        if(datastore.getCollection(User.class).count()>0) {
//            Заменить на норм проверку.
            List<User> consists = datastore.createQuery(User.class).field("instId").equal(u.getInstId()).asList();
            return consists.size() == 0;
        } else return true;

    }
}
