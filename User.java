package miniTwitter;

import java.util.*;

// implementation of UserComponent and Subject interfaces
// uses Observer Design Pattern
// uses Composite Design Pattern
public class User implements UserComponent, Subject {
    private String ID;
    private long creationTime;
    private long lastUpdateTime;
    private List<Observer> followers;
    private List<User> followings;
    private List<String> newsFeed;
    private List<String> tweets; // used just to easily count the total number of messages, as newsFeeds can overlap
    private boolean needToRefresh;

    public User(String ID) {
        this.ID = ID;
        creationTime = System.currentTimeMillis();
        followers = new ArrayList<>();
        followings = new ArrayList<>();
        newsFeed = new ArrayList<>();
        tweets = new ArrayList<>();
        needToRefresh = false;
        
        UserDirSingleton userMap = UserDirSingleton.getInstance();
        userMap.addUser(this);
    }
    
    public void setNeedToRefresh(boolean needToRefresh) {
    	this.needToRefresh = needToRefresh;
    }
    public boolean getNeedToRefresh() {
    	return needToRefresh;
    }

    // returns a list of objects: the first is a HTML string then the second is a reference to this User
    @Override
    public List<Object> getHTML(int indentationLevel) {
        List<Object> htmlAndObject = new ArrayList<>();
    	
        String html = "<span style='font-family: Arial; font-size: 25pt;'>" +
                getIndentation(indentationLevel) +
                "<a href=\"" + ID + "\" style=\"text-decoration: none; color: black;\">- " + ID + "</a>" +
                "</span><br>";
        
        htmlAndObject.add(html);
        htmlAndObject.add(this);
        return htmlAndObject;
    }

    @Override
    public String getID() {
        return ID;
    }
    
    public long getCreationTime() {
    	return creationTime;
    }
    
    public long getLastUpdateTime() {
    	return lastUpdateTime;
    }

    // returns an HTML string to add a specified number of tabs for Tree View
    private String getIndentation(int level) {
        String indent = "";
        for (int i = 0; i < level; i++) {
            indent += "&nbsp;&nbsp;&nbsp;&nbsp;";
        }
        return indent;
    }

	@Override
	public void accept(Visitor visitor) {
		visitor.visitUser(this);		
	}
	
	@Override
    public void register(Observer o) {
        followers.add(o);
    }

    @Override
    public void unregister(Observer o) {
        followers.remove(o);
    }

    @Override
    public void notifyObservers(String tweet) {
        for (Observer follower : followers) {
            follower.update(tweet);
        }
    }
    
    public List<User> getFollowers() {
        return followings;
    }

    public List<User> getFollowings() {
        return followings;
    }
    
    public List<String> getTweets() {
    	return tweets;
    }

    public void postTweet(String tweet) {
    	tweets.add(tweet);
    	this.addToNewsFeed("- You: " + tweet);
        notifyObservers("- " + ID + ": " + tweet);
    }

    public void follow(User user) {
        if (!followings.contains(user)) {
            followings.add(user);
            user.register(new Follower(this));
        }
    }

    public List<String> getNewsFeed() {
        return newsFeed;
    }

    public void addToNewsFeed(String tweet) {
        newsFeed.add(0, tweet);
        needToRefresh = true;
        lastUpdateTime = System.currentTimeMillis();
    }
}