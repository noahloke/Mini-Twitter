package miniTwitter;

import java.util.HashMap;
import java.util.Map;

// uses Singleton Design Pattern
// a directory containing all users (key value pairs are added to the userMap during User construction)
// allows you to get a reference to the User object itself from just a String of its userID
public class UserDirSingleton {

    private static UserDirSingleton instance;
    private Map<String, User> userMap;

    private UserDirSingleton() {
    	userMap = new HashMap<>();
    }

    public static UserDirSingleton getInstance() {
        if (instance == null) {
            instance = new UserDirSingleton();
        }
        return instance;
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public void addUser(User user) {
    	userMap.put(user.getID(), user);
    }
    
    public User getUserByName(String name) {
        return userMap.get(name);
    }
}