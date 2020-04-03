package com.example.DeluxeRps.Controllers;


class BCryptic {

    static String hashPassword(String password){
        return org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt(5));
    }


}