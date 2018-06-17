package michael.attend;

import java.util.ArrayList;

public class ListData {

    public String title;
    public String latitude;
    public String longitude;
    public String description;
    public String hostName;
    public String numEvents;
    public boolean inSession;
    ArrayList<Event> history;
    ArrayList<User> attendees;
    ArrayList<ListData> studentGroups;
}
