package miniTwitter;

import java.util.ArrayList;

// uses Visitor Design Pattern
// implementation of Visitor interface
// gathers statistics for 4 buttons at the bottom-right (of Admin Control Panel)
public class UserComponentVisitor implements Visitor {
	private int userCount = 0;
    private int userGroupCount = 0;
    private int messageCount = 0;
    private double positiveMessageCount = 0;
    private String[] positiveSubstrings = {"good", "great", "excellent", "wonderful", "well"};
    private ArrayList<String> userComponentIDs = new ArrayList<String>();
    private boolean areIDsValid = true;
    private long lastUpdateTime;
    private String lastUpdatedUser;
	
	@Override
	public void visitUser(User user) {
		userCount++;
		messageCount += user.getTweets().size();
		
		// checks if the User ID is a duplicate or if it contains spaces
		// if so, then not all IDs are valid
		// otherwise, add the User ID to the userComponentIDs ArrayList
		if (userComponentIDs.contains(user.getID()) || user.getID().contains(" ")) {
			areIDsValid = false;
		} else {
			userComponentIDs.add(user.getID());
		}
		
		// keep track of the UserID with the latest update time
		if (user.getLastUpdateTime() > lastUpdateTime) {
			lastUpdateTime = user.getLastUpdateTime();
			lastUpdatedUser = user.getID();
		}
		
		for (String tweet : user.getTweets()) {
			for (String substring : positiveSubstrings) {
				if (tweet.contains(substring)) {
					positiveMessageCount++;
					break;
				}
			}
		}
	}

	@Override
	public void visitUserGroup(UserGroup group) {
        userGroupCount++;
        
        // checks if the Group ID is a duplicate or if it contains spaces
     	// if so, then not all IDs are valid
     	// otherwise, add the Group ID to the userComponentIDs ArrayList
     	if (userComponentIDs.contains(group.getID()) || group.getID().contains(" ")) {
     		areIDsValid = false;
     	} else {
     		userComponentIDs.add(group.getID());
     	}
	}
	
	public int getUserCount() {
        return userCount;
    }

    public int getUserGroupCount() {
        return userGroupCount;
    }
    
    public int getMessageCount() {
        return messageCount;
    }
    
    public boolean getAreIDsValid() {
    	return areIDsValid;
    }
    
    public String getLastUpdatedUser() {
    	return lastUpdatedUser;
    }
    
    // round positiveMessagePercentage to 2 decimal places then multiply by 100 (returns format "33.33%")
    public double getPositiveMessagePercentage() {
    	return Math.round(positiveMessageCount / messageCount * 10000.0) / 100.0; 
    }
}