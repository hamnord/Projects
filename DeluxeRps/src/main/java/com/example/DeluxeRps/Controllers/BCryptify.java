package com.example.DeluxeRps.Controllers;


public class BCryptify {

    public static String hashPassword(String password){
        return org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt(5));
    }



}