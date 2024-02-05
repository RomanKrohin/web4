package beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import utils.DatabaseHandler;
import utils.Result;
import utils.User;

@Stateless
@LocalBean
public class AuthBean {


    DatabaseHandler databaseHandler= new DatabaseHandler();

    public Boolean auth (String login, String password){
        return databaseHandler.isUserExists(login, password);
    }

    public String reg (String login, String password){
        if (login.length()<10 || password.length()<10 || password.contains(".")){
            return "{\"message\": \"Password and login must have 10 chars and password must do not contain points\"}";
        }
        if (!databaseHandler.isRegUserExists(login, password)){
            User user= new User();
            user.setLogin(login);
            user.setPassword(password);
            databaseHandler.addUser(user);
            return "reg";
        }
        return "{\"message\": \"User already exist\"}";
    }
    
}

