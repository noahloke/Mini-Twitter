package miniTwitter;

// keeps track of which entity the Admin has most recently clicked on (selected)
// this allows User and UserGroup objects (both UserComponents) to be placed in a selected UserGroup, if a UserGroup is selected
// it also allows for a User View Panel to pop up if a User is selected
// uses Singleton Design Pattern
public class SelectedSingleton {

    private static SelectedSingleton instance;
    private UserComponent selection;

    private SelectedSingleton() {
    }

    public static SelectedSingleton getInstance() {
        if (instance == null) {
            instance = new SelectedSingleton();
        }
        return instance;
    }

    public UserComponent getSelection() {
        return selection;
    }

    public void setSelection(UserComponent selection) {
        this.selection = selection;
    }
}
