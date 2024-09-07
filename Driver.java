package miniTwitter;

// entry point of the application
// creates default Root group and launches AdminControlPanel object
public class Driver {
	public static void main(String[] args) {
		UserGroup root = new UserGroup("Root");
		AdminControlPanel mainUI = new AdminControlPanel(root);
	}
}