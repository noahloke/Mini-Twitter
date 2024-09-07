package miniTwitter;

import java.util.List;

// implemented by User and UserGroup classes
// for tree like structure
// uses Composite Design Pattern
public interface UserComponent {
	String getID();
	long getCreationTime();
	List<Object> getHTML(int indentationLevel);
	void accept(Visitor visitor);
}