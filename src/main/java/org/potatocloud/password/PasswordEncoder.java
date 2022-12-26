package org.potatocloud.password;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncoder {

    public static String generateSalt(Integer logsRound) {
        return BCrypt.gensalt(logsRound);
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static String hashPassword(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

    public static String hashPassword(String password, Integer logsRound) {
        return BCrypt.hashpw(password, BCrypt.gensalt(logsRound));
    }

    public static Boolean match(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }

}
