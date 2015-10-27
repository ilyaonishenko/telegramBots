package me.glassfish.models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by woqpw on 27.10.15.
 */
@Entity
public class Subscription {

    @Id
    private ObjectId id;

    private String username;

    private int inst_id;

    public Subscription(){}

    public Subscription(String username,int inst_id){
        this.username = username;
        this.inst_id = inst_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return inst_id;
    }

    public void setId(int inst_id) {
        this.inst_id = inst_id;
    }

    @Override
    public String toString(){
        return new StringBuffer("username: ").append(username)
                .append(" id: ").append(inst_id).toString();
    }
}
