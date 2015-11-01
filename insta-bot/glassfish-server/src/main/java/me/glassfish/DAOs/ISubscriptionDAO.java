package me.glassfish.DAOs;

import me.glassfish.models.Subscription;

/**
 * Created by woqpw on 31.10.15.
 */
public interface ISubscriptionDAO {
    void addSubscription(Subscription s);
    boolean checkSubscriptionBytNameAndcId(String tUsername,int c_id);
    int getTargetIdBytUsername(String username);
}
