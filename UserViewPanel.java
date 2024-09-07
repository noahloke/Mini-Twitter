package miniTwitter;

import javax.swing.*;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;

public class UserViewPanel {
	public UserViewPanel(User user) {
        JFrame frame = new JFrame(user.getID() + " View");
        frame.setSize(770, 740); // width, height
        frame.setLayout(new BorderLayout());
        refresh(user, frame);
        updateFrame(user, frame);
	}

	private void updateFrame(User user, JFrame frame) {
		
		JEditorPane followingList = new JEditorPane(); // needed to be created early to be referenced to clear cursor focus
		
		JPanel panel = new JPanel();

		panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // top, left, bottom, right
		
		// Header - Creation Time Display
		
		JEditorPane creationTimeDisplay = new JEditorPane();
        creationTimeDisplay.setPreferredSize(new Dimension(720, 30)); // width, height
        creationTimeDisplay.setContentType("text/html");
        String creationTimeDisplayHtmlContent = "<html><span style='font-family: Arial; font-size: 20pt;'>Creation Time: "
        + new Date(user.getCreationTime()) +
        "</span><br>";
        
        creationTimeDisplay.setText(creationTimeDisplayHtmlContent);

        creationTimeDisplay.setEditable(false);
        creationTimeDisplay.setCaretColor(creationTimeDisplay.getBackground());

        panel.add(creationTimeDisplay);
        
     	// Header - Last Update Time Display
		
     	JEditorPane lastUpdateTimeDisplay = new JEditorPane();
        lastUpdateTimeDisplay.setPreferredSize(new Dimension(720, 30)); // width, height
        lastUpdateTimeDisplay.setContentType("text/html");
        String lastUpdateTimeDisplayHtmlContent;
        
        if (user.getLastUpdateTime() == 0) {
        	lastUpdateTimeDisplayHtmlContent = "<html><span style='font-family: Arial; font-size: 20pt;'>Last Update Time: n/a</span><br>";
        } else {
        	lastUpdateTimeDisplayHtmlContent = "<html><span style='font-family: Arial; font-size: 20pt;'>Last Update Time: "
        			+ new Date(user.getLastUpdateTime()) +
        			"</span><br>";
        }
        
        lastUpdateTimeDisplay.setText(lastUpdateTimeDisplayHtmlContent);

        lastUpdateTimeDisplay.setEditable(false);
        lastUpdateTimeDisplay.setCaretColor(lastUpdateTimeDisplay.getBackground());

        panel.add(lastUpdateTimeDisplay);
		
		// Row 1
        JPanel row1 = new JPanel(new GridLayout(1, 2));
        row1.setPreferredSize(new Dimension(720, 50)); // width, height
        JTextField userIDInput = new JTextField();
        userIDInput.setFont(new Font("Arial", Font.PLAIN, 25));
        String userIDInputPlaceholderText = "Input User Name to Follow";
        userIDInput.setText(userIDInputPlaceholderText);
        userIDInput.setForeground(Color.GRAY);
        
        userIDInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userIDInput.getText().equals(userIDInputPlaceholderText)) {
                	userIDInput.setText("");
                	userIDInput.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (userIDInput.getText().isEmpty()) {
                	userIDInput.setText(userIDInputPlaceholderText);
                	userIDInput.setForeground(Color.GRAY);
                }
            }
        });
        
        JButton followUserButton = new JButton("Follow User");
        followUserButton.setFont(new Font("Arial", Font.PLAIN, 25));
        followUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                UserDirSingleton userMap = UserDirSingleton.getInstance();
                user.follow(userMap.getUserByName(userIDInput.getText()));
                
                userIDInput.setText(userIDInputPlaceholderText);
                userIDInput.setForeground(Color.GRAY);
                
                updateFrame(user, frame);
                
                followingList.requestFocusInWindow();
                
                frame.setVisible(true);
            }
        });
        
        row1.add(userIDInput);
        row1.add(followUserButton);
        panel.add(row1);
        
        // Following List
        followingList.setPreferredSize(new Dimension(720, 235)); // width, height
        followingList.setContentType("text/html");
        String followingListHtmlContent = "<html><span style='font-family: Arial; font-size: 25pt;'><u>List View (Currently Following):</u></span><br>";

        for (User following : user.getFollowings()) {
        	followingListHtmlContent += "<html><span style='font-family: Arial; font-size: 25pt;'>- "
        			+ following.getID()
        			+ "</span><br>";
        	}
        
        followingList.setText(followingListHtmlContent);

        followingList.setEditable(false);
        followingList.setCaretColor(followingList.getBackground());
        JScrollPane followingListScrollPane = new JScrollPane(followingList);
        
        panel.add(followingListScrollPane);
        
        // Row 3: contains Tweet Input and Post Tweet Button
        JPanel row3 = new JPanel(new GridLayout(1, 2));
        row3.setPreferredSize(new Dimension(720, 50)); // width, height
        JTextField tweetInput = new JTextField();
        tweetInput.setFont(new Font("Arial", Font.PLAIN, 25));
        String tweetInputPlaceholderText = "Input Tweet";
        tweetInput.setText(tweetInputPlaceholderText);
        tweetInput.setForeground(Color.GRAY);
        
        tweetInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tweetInput.getText().equals(tweetInputPlaceholderText)) {
                	tweetInput.setText("");
                	tweetInput.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (tweetInput.getText().isEmpty()) {
                	tweetInput.setText(tweetInputPlaceholderText);
                	tweetInput.setForeground(Color.GRAY);
                }
            }
        });
        
        // Post Tweet Button
        JButton postTweetButton = new JButton("Post Tweet");
        postTweetButton.setFont(new Font("Arial", Font.PLAIN, 25));
        postTweetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                user.postTweet(tweetInput.getText());
                
                tweetInput.setText(tweetInputPlaceholderText);
                tweetInput.setForeground(Color.GRAY);
                
                updateFrame(user, frame);
                
                followingList.requestFocusInWindow();
                
                frame.setVisible(true);
            }
        });
        
        row3.add(tweetInput);
        row3.add(postTweetButton);
        panel.add(row3);
        
        // News Feed
        JEditorPane newsFeed = new JEditorPane();
        newsFeed.setPreferredSize(new Dimension(720, 235)); // width, height
        newsFeed.setContentType("text/html");
        String newsFeedHtmlContent = "<html><span style='font-family: Arial; font-size: 25pt;'><u>List View (News Feed) - (Top is most recent):</u></span><br>";

        for (String news : user.getNewsFeed()) {
        	newsFeedHtmlContent += "<html><span style='font-family: Arial; font-size: 25pt;'>"
        			+ news
        			+ "</span><br>";
        }
        
        newsFeed.setText(newsFeedHtmlContent);

        newsFeed.setEditable(false);
        newsFeed.setCaretColor(newsFeed.getBackground());
        JScrollPane newsFeedScrollPane = new JScrollPane(newsFeed);

        panel.add(newsFeedScrollPane);
        
        frame.add(panel);
        frame.setVisible(true);
        
        followingList.requestFocusInWindow();
	}
	private void refresh(User user, JFrame frame) {
		Timer timer = new Timer(1000, event -> {
	        if (user.getNeedToRefresh()) {
	        	user.setNeedToRefresh(false);
	        	updateFrame(user, frame);
	        }
	    });
	    timer.start();
	}
}