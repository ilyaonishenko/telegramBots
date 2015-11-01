package me.glassfish.DAOs;

import me.glassfish.models.User;

/**
 * Created by woqpw on 31.10.15.
 */
public interface IUserDAO {
    void addUser(User u);
    boolean checkUserByUsername(String username);
    int getIdByUsername(String username);
    String getAccessTokenBycId(int c_id);
}
