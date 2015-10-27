package me.glassfish.models;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by woqpw on 28.10.15.
 */
public class SubscriptionDAO {
    private static SubscriptionDAO subscriptionDAO;

    static MongoClient mongoClient = new MongoClient();

    final static Morphia morphia = new Morphia();

    static Datastore datastore  = morphia.createDatastore(mongoClient,"instabotdb");

    private static Logger logger = LoggerFactory.getLogger(UserDAO.class);

    private SubscriptionDAO (){}

    public static SubscriptionDAO getSubscriptionDAO(){
        if(subscriptionDAO==null){
            subscriptionDAO = new SubscriptionDAO();
        }
        morphia.map(Subscription.class);
        return subscriptionDAO;
    }

    public static void addSubscription(Subscription s){
        datastore.save(s);
    }
    private static void checkSubscription(Subscription s){
        logger.info("in checkSubscription with {}",s);
        if(datastore.getCollection(Subscription.class).count()>0){
            List<Subscription> consists = 
        }
    }


}
