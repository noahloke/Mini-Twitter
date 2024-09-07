package miniTwitter;

// implemented by the User class
// used for Observer Design Pattern
public interface Subject {
    void register(Observer o);
    void unregister(Observer o);
    void notifyObservers(String tweet);
}