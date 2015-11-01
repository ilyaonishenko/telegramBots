package me.glassfish.controllers;

import me.glassfish.DAOs.ISubscriptionDAO;
import me.glassfish.models.Subscription;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by woqpw on 31.10.15.
 */
@Named
@RequestScoped
public class SubscriptionController {

    @Inject
    private ISubscriptionDAO subscriptionDAO;

    public void addSubscription(Subscription s){
        subscriptionDAO.addSubscription(s);
    }
    public boolean checkSubscriptionBytNameAndcId(String tUsername,int c_id){
        return subscriptionDAO.checkSubscriptionBytNameAndcId(tUsername,c_id);
    }
    public int getTargetIdBytUsername(String username){
        return subscriptionDAO.getTargetIdBytUsername(username);
    }
}
