package com.nisum.users.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
public class EncryptUtil {

    public static String createHash(String email, String password) {
        return BCrypt.hashpw(email + password, BCrypt.gensalt());
    }

    public static Boolean checkHash(String email, String password, String hash) {
        return BCrypt.checkpw(email.concat(password), hash);
    }
}

