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

    private String tUsername;

    private int t_id;

    private int c_id;

    public Subscription(){}

    public Subscription(String tUsername,int t_id, int c_id ){
        this.tUsername = tUsername;
        this.t_id = t_id;
        this.c_id = c_id;
    }

    public String getUsername() {
        return tUsername;
    }

    public void setUsername(String username) {
        this.tUsername = username;
    }

    public int getTargetId() {
        return t_id;
    }

    public void setTargetId(int t_id) {
        this.t_id = t_id;
    }

    @Override
    public String toString(){
        return new StringBuffer("target username: ").append(tUsername)
                .append(" target id: ").append(t_id)
                .append(" client id: ").append(c_id).toString();
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }
}
