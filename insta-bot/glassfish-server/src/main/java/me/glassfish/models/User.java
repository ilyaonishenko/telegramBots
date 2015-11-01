package me.glassfish.models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by woqpw on 24.10.15.
 */
@Entity
public class User {

    @Id
    private ObjectId id;

    private String username;

    private String bio;

    private String website;

    private String profile_picture;

    private String full_name;

    private int instId;

    private String access_token;

    public User(){}
    public User (String username,String bio,String website,String profile_picture,
                 String full_name,int inst_id,String access_token){
        this.username = username;
        this.bio = bio;
        this.website = website;
        this.profile_picture = profile_picture;
        this.full_name = full_name;
        this.instId = inst_id;
        this.access_token = access_token;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getInstId() {
        return instId;
    }

    public void setInstId(int instId) {
        this.instId = instId;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAccess_token() {
        return access_token;
    }
    @Override
    public String toString(){
        return new StringBuffer("username: ").append(this.username)
                .append(" bio: ").append(this.bio)
                .append(" website: ").append(this.website)
                .append(" profile_picture: ").append(this.profile_picture)
                .append(" full_name: ").append(this.full_name)
                .append(" instId: ").append(this.instId)
                .append(" access_token: ").append(this.access_token).toString();
    }
}
