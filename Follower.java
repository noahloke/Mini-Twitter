package miniTwitter;

// implementation of Observer interface
// used for Observer Design Pattern
public class Follower implements Observer {
    private User user;

    public Follower(User user) {
        this.user = user;
    }

    @Override
    public void update(String tweet) {
        user.addToNewsFeed(tweet);
    }
}