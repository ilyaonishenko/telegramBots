package me.glassfish.DAOs;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import me.glassfish.models.Subscription;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by woqpw on 28.10.15.
 */
public class SubscriptionDAO implements ISubscriptionDAO {

    private Logger logger = LoggerFactory.getLogger(SubscriptionDAO.class);

    private MongoClient mongoClient = new MongoClient();

    private final Morphia morphia = new Morphia();

    private Datastore datastore  = morphia.createDatastore(mongoClient,"instabotdb");

    private DBCollection collection = datastore.getCollection(Subscription.class);

    @Override
    public void addSubscription(Subscription s){
        if (checkSubscription(s))
            datastore.save(s);
    }

    public boolean checkSubscription(Subscription s){
        logger.info("in checkSubscription with {}",s);
        if(datastore.getCollection(Subscription.class).count()>0){
            logger.info("in if-stat");
            return getTargetId(s.getTargetId());
        }
        return true;
    }

    private boolean getTargetId(int t_id) {
        BasicDBObject query = new BasicDBObject();
        query.put("t_id", t_id);
        try {
            DBObject db = collection.findOne(query);
            return db == null;
        } catch (NullPointerException e) {
            logger.info("null pointer exception");
            return true;
        }
    }

    @Override
    public boolean checkSubscriptionBytNameAndcId(String tUsername,int c_id){
        BasicDBObject query = new BasicDBObject();
        query.put("tUsername",tUsername);
        query.put("c_id",c_id);
        try{
            collection.findOne(query);
            return true;
        } catch( NullPointerException e){
            logger.info("null pointer exception in checking subscription by name and cId {}",tUsername);
            return false;
        }
    }
    @Override
    public int getTargetIdBytUsername(String username){
        BasicDBObject query = new BasicDBObject();
        query.put("tUsername",username);
        DBObject dbObject = collection.findOne(query);
        logger.info("find in collection subs this {}",dbObject);
        logger.info("t_id is {}",dbObject.get("t_id"));
        return (int)dbObject.get("t_id");
    }
}
