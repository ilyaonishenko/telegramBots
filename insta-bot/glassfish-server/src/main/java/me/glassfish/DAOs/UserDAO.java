package me.glassfish.DAOs;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import me.glassfish.models.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by woqpw on 28.10.15.
 */
public class UserDAO implements IUserDAO{

    private MongoClient mongoClient = new MongoClient();

    private final Morphia morphia = new Morphia();

    private Datastore datastore  = morphia.createDatastore(mongoClient,"instabotdb");

    private Logger logger = LoggerFactory.getLogger(UserDAO.class);

    private DBCollection collection = datastore.getCollection(User.class);

    @Override
    public void addUser(User u){
        if(checkUser(u)) {
            logger.info("no such user in db {}",u.getUsername());
            datastore.save(u);
        }
    }
    /*private boolean checkUser1(User u){
        logger.info("user is {}",u.toString());
        if(datastore.getCollection(User.class).count()>0) {
//            Заменить на норм проверку.
            List<User> consists = datastore.createQuery(User.class).field("instId").equal(u.getInstId()).asList();
            return consists.size() == 0;
        } else return true;
    }*/
    public boolean checkUser(User u){
        logger.info("in checkUser with {}",u);
        if(datastore.getCollection(User.class).count()>0){
            logger.info("in if-stat");
            return getUserId(u.getInstId());
        }
        return true;
    }
    private boolean getUserId(int u_id){
        BasicDBObject query = new BasicDBObject();
        query.put("instId",u_id);
        try{
            collection.findOne(query);
            return false;
        } catch (NullPointerException e){
            logger.info("null pointer exception");
            return true;
        }
    }

    @Override
    public boolean checkUserByUsername(String username){
        BasicDBObject query = new BasicDBObject();
        query.put("username",username);
        try{
            collection.findOne(query);
            return true;
        } catch (NullPointerException e){
            logger.info("null pointer exception in checkUserByName with name {}",username);
            return false;
        }
    }

    @Override
    public int getIdByUsername(String username){
        DBObject dbObject;
        BasicDBObject query = new BasicDBObject();
        query.put("username",username);
        try{
            dbObject =  collection.findOne(query);
            return (int) dbObject.get("instId");
        } catch(NullPointerException e){
            return 0;
        }
    }

    @Override
    public String getAccessTokenBycId(int c_id){
        BasicDBObject query = new BasicDBObject();
        DBObject dbObject = collection.findOne(query);
        return dbObject.get("access_token").toString();
    }
}
