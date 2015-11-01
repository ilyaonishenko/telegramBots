package me.glassfish.controllers;

import me.glassfish.DAOs.IUserDAO;
import me.glassfish.models.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by woqpw on 31.10.15.
 */
@Named
@RequestScoped
public class UserController {

    @Inject
    private IUserDAO userDAO;

    public void addUser(User u){
        userDAO.addUser(u);
    }
    public boolean checkUserByUsername(String username){
        return userDAO.checkUserByUsername(username);
    }
    public int getIdByUsername(String username){
        return userDAO.getIdByUsername(username);
    }
    public String getAccessTokenBycId(int c_id){
        return userDAO.getAccessTokenBycId(c_id);
    }
}
