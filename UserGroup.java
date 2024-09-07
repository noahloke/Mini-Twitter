package miniTwitter;

import java.util.ArrayList;
import java.util.List;

// implements the UserComponent interface
// uses Observer Design Pattern
// uses the Component Design Pattern
public class UserGroup implements UserComponent {
    private String ID;
    private long creationTime;
    private ArrayList<UserComponent> components = new ArrayList<>();

    public UserGroup(String ID) {
        this.ID = ID;
        creationTime = System.currentTimeMillis();
    }

    public void addComponent(UserComponent component) {
        components.add(component);
    }

    // returns a list of objects: the first is a HTML String, the rest are all the Users in this UserGroup
    @Override
    public List<Object> getHTML(int indentationLevel) {
    	List<Object> htmlAndObjects = new ArrayList<>();
    	
    	String optionalDash = "- ";    	
    	if (indentationLevel == 0) {
    		optionalDash = "";
    	}
    	String html = "<span style='font-family: Arial; font-size: 25pt;'>"
        					+ getIndentation(indentationLevel) +
        					"<a href=\""
        					+ ID +
        					"\" style=\"text-decoration: none; color: black; font-weight: bold;\">"
        					+ optionalDash
        					+ ID +
        					"</a></span><br>";

        htmlAndObjects.add(html);
        htmlAndObjects.add(this);

        for (UserComponent component : components) {
        	List<Object> newHTML = component.getHTML(indentationLevel + 1);
        	htmlAndObjects.set(0, String.valueOf(htmlAndObjects.get(0)) + String.valueOf(newHTML.get(0)));
        	
        	List<Object> subList = newHTML.subList(1, newHTML.size());
        	for (Object element : subList) {
        		htmlAndObjects.add(element);
            }
        }

        return htmlAndObjects;
    }

    @Override
    public String getID() {
        return ID;
    }

	@Override
	public void accept(Visitor visitor) {
		visitor.visitUserGroup(this);
		
		for (UserComponent component : components) {
            component.accept(visitor);
        }
	}

	@Override
	public long getCreationTime() {
    	return creationTime;
    }
	
	// returns an HTML string to add a specified number of tabs for Tree View
    private String getIndentation(int level) {
        String indent = "";
        for (int i = 0; i < level; i++) {
            indent += "&nbsp;&nbsp;&nbsp;&nbsp;";
        }
        return indent;
    }
}