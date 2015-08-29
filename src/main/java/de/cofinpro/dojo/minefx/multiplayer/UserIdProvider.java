package de.cofinpro.dojo.minefx.multiplayer;

import java.util.Random;

/**
 * Created by mheck on 29.08.2015.
 */
public class UserIdProvider {

    private static final UserIdProvider instance = new UserIdProvider();

    private static final String userId = System.getProperty("user.name",  Integer.toString(new Random().nextInt()));

    private UserIdProvider(){};

    public static UserIdProvider getInstance() {
        return instance;
    }

    public String getUserId() {
        return userId;
    }
}
