package com.example.DeluxeRps.Controllers;

/**
 * Controller is called upon when something is encrypted, hashes and salts via BCrypt.
 *
 * @author heidiguneriussen
 */

class BCryptic {

    static String hashPassword(String password){
        return org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt(5));
    }


}