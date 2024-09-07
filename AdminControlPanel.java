package miniTwitter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdminControlPanel {
    public AdminControlPanel(UserGroup root) {
        JFrame frame = new JFrame("Admin Control Panel");
        frame.setSize(1300, 740); // width, height

        frame.setLayout(new BorderLayout());

        JPanel firstColumnPanel = new JPanel(new BorderLayout());
        firstColumnPanel.setPreferredSize(new Dimension(530, 740)); // width, height
        firstColumnPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 7)); // top, left, bottom, right

        updateTreeView(firstColumnPanel, root);
        
        JPanel secondColumnPanel = new JPanel(new GridLayout(0, 1)); // rows (0 means the number is not fixed), 1 column
        secondColumnPanel.setBorder(BorderFactory.createEmptyBorder(15, 7, 15, 15)); // top, left, bottom, right

        frame.add(firstColumnPanel, BorderLayout.WEST);
        frame.add(secondColumnPanel, BorderLayout.CENTER);

        addContentToSecondColumn(frame, firstColumnPanel, secondColumnPanel, root);

        frame.setVisible(true);
    }
    
    // Left side of Admin Control Panel
    private void updateTreeView(JPanel panel, UserGroup root) {
    	JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        String htmlContent = "<html><span style='font-family: Arial; font-size: 25pt;'><u>Tree View (Click an item to select it):</u></span><br>";
        
        List<Object> htmlAndObjects = root.getHTML(0);
        
        htmlContent += htmlAndObjects.get(0);
        
        htmlContent += "</html>";
        
        editorPane.setText(htmlContent);
        
        
        List<Object> subList = htmlAndObjects.subList(1, htmlAndObjects.size());
        
        SelectedSingleton selectedNode = SelectedSingleton.getInstance();
        
        for (Object element : subList) {
        	editorPane.addHyperlinkListener(new HyperlinkListener() {
                @Override
                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        if (e.getDescription().equals(((UserComponent)element).getID())) {
                            selectedNode.setSelection((UserComponent)element);
                        }
                    }
                }
            });
        }
        
        editorPane.setEditable(false);
        editorPane.setCaretColor(editorPane.getBackground());
        JScrollPane scrollPane = new JScrollPane(editorPane);
        
        panel.add(scrollPane);
    }

    // Right side of Admin Control Panel
    private void addContentToSecondColumn(JFrame frame, JPanel firstColumnPanel, JPanel secondColumnPanel, UserComponent root) {
    	SelectedSingleton selectedNode = SelectedSingleton.getInstance();

        // Row 1: contains User ID Input and Add User Button
        JPanel row1 = new JPanel(new GridLayout(1, 2));
        JTextField userIDInput = new JTextField();
        userIDInput.setFont(new Font("Arial", Font.PLAIN, 25));
        String userIDInputPlaceholderText = "Input User Name to Add";
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
        
        JButton addUserButton = new JButton("Add User");
        addUserButton.setFont(new Font("Arial", Font.PLAIN, 25));
        
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserGroup selectedGroup = (UserGroup)selectedNode.getSelection();
                selectedGroup.addComponent(new User(userIDInput.getText()));
                clearPanel(firstColumnPanel);
                updateTreeView(firstColumnPanel, (UserGroup) root);
                frame.setVisible(true);
                
                userIDInput.setText(userIDInputPlaceholderText);
                userIDInput.setForeground(Color.GRAY);
                
                secondColumnPanel.requestFocusInWindow();
            }
        });
        
        row1.add(userIDInput);
        row1.add(addUserButton);
        secondColumnPanel.add(row1);
        
        // Row 2: contains Group ID Input and Add Group Button
        JPanel row2 = new JPanel(new GridLayout(1, 2));
        JTextField groupIDInput = new JTextField();
        groupIDInput.setFont(new Font("Arial", Font.PLAIN, 25));
        String groupIDInputPlaceholderText = "Input Group Name to Add";
        groupIDInput.setText(groupIDInputPlaceholderText);
        groupIDInput.setForeground(Color.GRAY);
        
        groupIDInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (groupIDInput.getText().equals(groupIDInputPlaceholderText)) {
                    groupIDInput.setText("");
                    groupIDInput.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (groupIDInput.getText().isEmpty()) {
                    groupIDInput.setText(groupIDInputPlaceholderText);
                    groupIDInput.setForeground(Color.GRAY);
                }
            }
        });
        
        JButton addGroupButton = new JButton("Add Group");
        addGroupButton.setFont(new Font("Arial", Font.PLAIN, 25));
        
        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserGroup selectedGroup = (UserGroup)selectedNode.getSelection();
                selectedGroup.addComponent(new UserGroup(groupIDInput.getText()));
                clearPanel(firstColumnPanel);
                updateTreeView(firstColumnPanel, (UserGroup)root);
                frame.setVisible(true);
                
                groupIDInput.setText(groupIDInputPlaceholderText);
                groupIDInput.setForeground(Color.GRAY);
                
                secondColumnPanel.requestFocusInWindow();
            }
        });
        
        row2.add(groupIDInput);
        row2.add(addGroupButton);
        secondColumnPanel.add(row2);

        // Row 3: contains Open User View Button (of current selected User)
        JPanel row3 = new JPanel(new BorderLayout());
        JButton openUserViewButton = new JButton("Open User View");
        openUserViewButton.setFont(new Font("Arial", Font.PLAIN, 25));
        openUserViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                User selectedUser = (User)selectedNode.getSelection();
                UserViewPanel userView = new UserViewPanel(selectedUser);
                
                secondColumnPanel.requestFocusInWindow();
            }
        });
        row3.add(openUserViewButton, BorderLayout.CENTER);
        secondColumnPanel.add(row3);
        
        // Empty Space: 1 empty row
        secondColumnPanel.add(Box.createVerticalStrut(0));
        
        // Row 4: Validate User/Group IDs and Find Last Updated User Buttons
        JPanel row4 = new JPanel(new GridLayout(1, 2));
        JButton validateIDsButton = new JButton("Validate User & Group IDs");
        validateIDsButton.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton findLastUpdatedUserButton = new JButton("Find Last Updated User");
        findLastUpdatedUserButton.setFont(new Font("Arial", Font.PLAIN, 25));

        validateIDsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UserComponentVisitor visitor = new UserComponentVisitor();
        		root.accept(visitor);
        		String message;
        		
        		if (visitor.getAreIDsValid()) {
        			message = "<html><span style='font-size: 25pt;'>All User and UserGroup IDs are valid.</span></html>";
        		} else {
        			message = "<html><span style='font-size: 25pt;'>All User and UserGroup IDs are not valid.</span></html>";
        		}
        		
                JOptionPane.showMessageDialog(null, message);
            }
        });
        findLastUpdatedUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UserComponentVisitor visitor = new UserComponentVisitor();
        		root.accept(visitor);
        		
                JOptionPane.showMessageDialog(null, "<html><span style='font-size: 25pt;'>The last updated user is "
                + visitor.getLastUpdatedUser()
                + ".</span></html>");
            }
        });
        
        row4.add(validateIDsButton);
        row4.add(findLastUpdatedUserButton);
        secondColumnPanel.add(row4);

        // Row 5: Show User Total and Show Group Total Buttons
        JPanel row5 = new JPanel(new GridLayout(1, 2));
        JButton showUserTotalButton = new JButton("Show User Total");
        showUserTotalButton.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton showGroupTotalButton = new JButton("Show Group Total");
        showGroupTotalButton.setFont(new Font("Arial", Font.PLAIN, 25));

        showUserTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UserComponentVisitor visitor = new UserComponentVisitor();
        		root.accept(visitor);
            	
                JOptionPane.showMessageDialog(null, "<html><span style='font-size: 25pt;'>There are " + visitor.getUserCount() + " users.</span></html>");
            }
        });
        showGroupTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UserComponentVisitor visitor = new UserComponentVisitor();
        		root.accept(visitor);
        		
                JOptionPane.showMessageDialog(null, "<html><span style='font-size: 25pt;'>There are " + visitor.getUserGroupCount() + " groups.</span></html>");
            }
        });
        
        row5.add(showUserTotalButton);
        row5.add(showGroupTotalButton);
        secondColumnPanel.add(row5);
        
        // Row 6: Show Messages Total and Show Positive Percentage Buttons
        JPanel row6 = new JPanel(new GridLayout(1, 2));
        JButton showMessagesTotalButton = new JButton("Show Messages Total");
        showMessagesTotalButton.setFont(new Font("Arial", Font.PLAIN, 25));
        JButton showPositivePercentageButton = new JButton("Show Positive Percentage");
        showPositivePercentageButton.setFont(new Font("Arial", Font.PLAIN, 25));

        showMessagesTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UserComponentVisitor visitor = new UserComponentVisitor();
        		root.accept(visitor);
            	
                JOptionPane.showMessageDialog(null, "<html><span style='font-size: 25pt;'>There are " + visitor.getMessageCount() + " messages.</span></html>");
            }
        });
        showPositivePercentageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	UserComponentVisitor visitor = new UserComponentVisitor();
        		root.accept(visitor);
            	
                JOptionPane.showMessageDialog(null, "<html><span style='font-size: 25pt;'>" + visitor.getPositiveMessagePercentage() + "% of the messages are positive.</span></html>");
            }
        });
        
        row6.add(showMessagesTotalButton);
        row6.add(showPositivePercentageButton);
        secondColumnPanel.add(row6);
    }
    private void clearPanel(JPanel panel) {
    	panel.removeAll();
    	panel.revalidate();
    	panel.repaint();
    }
}