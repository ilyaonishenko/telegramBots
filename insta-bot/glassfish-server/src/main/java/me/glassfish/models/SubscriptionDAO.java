package me.glassfish.models;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by woqpw on 28.10.15.
 */
public class SubscriptionDAO {

    private static Logger logger = LoggerFactory.getLogger(SubscriptionDAO.class);

    private static SubscriptionDAO subscriptionDAO;

    static MongoClient mongoClient = new MongoClient();

    final static Morphia morphia = new Morphia();

    static Datastore datastore  = morphia.createDatastore(mongoClient,"instabotdb");

    static DBCollection collection = datastore.getCollection(Subscription.class);

    private SubscriptionDAO (){}

    public static SubscriptionDAO getSubscriptionDAO(){
        if(subscriptionDAO==null){
            subscriptionDAO = new SubscriptionDAO();
        }
        morphia.map(Subscription.class);
        return subscriptionDAO;
    }

    public static void addSubscription(Subscription s){
        if (checkSubscription(s))
            datastore.save(s);
    }
    private static boolean checkSubscription(Subscription s){
        logger.info("in checkSubscription with {}",s);
        if(datastore.getCollection(Subscription.class).count()>0){
            logger.info("in if-stat");
            return getTargetId(s.getTargetId());
        }
        return true;
    }
    private static boolean getTargetId(int t_id) {
        BasicDBObject query = new BasicDBObject();
        query.put("t_id", t_id);
        try {
            DBObject dbObj = collection.findOne(query);
            logger.info("object founded {}", dbObj.toString());
            return false;
        } catch (NullPointerException e) {
            logger.info("null pointer exception");
            return true;
        }
    }


}
