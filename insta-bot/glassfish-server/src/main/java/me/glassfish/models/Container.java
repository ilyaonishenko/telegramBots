package me.glassfish.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by woqpw on 27.10.15.
 */
public class Container {
    private static Container container;
    private Container(){}
    public static Container getContainer(){
        if( container==null){
            container = new Container();
        }
        return container;
    }
    public static Map<String,Integer> subscriptions = new HashMap<>();
    public static Map<String,User> users = new HashMap<>();
    public static void addToSubscriptions(String key,Integer value){
        subscriptions.put(key,value);
    }
    public static void addToUsers(String key, User user){
        users.put(key, user);
    }
    public static Integer getFromSubscriptionsWithKey(String key){
        return subscriptions.get(key);
    }
    public static String getAccessTokenFromKey(String key){
        return users.get(key).getAccess_token();
    }
}
