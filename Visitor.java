package miniTwitter;

// uses Visitor Design Pattern
// implemented by UserComponentVisitor class
public interface Visitor {
	void visitUser(User user);
    void visitUserGroup(UserGroup group);
}
