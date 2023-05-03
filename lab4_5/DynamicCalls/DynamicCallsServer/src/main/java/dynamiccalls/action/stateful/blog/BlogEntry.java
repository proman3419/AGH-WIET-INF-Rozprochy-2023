package dynamiccalls.action.stateful.blog;


import java.util.Date;
import java.util.List;

public class BlogEntry {
    private final String title;
    private final String content;
    private final List<String> tags;
    private final Date addedDate;

    public BlogEntry(String title, String content, List<String> tags, Date addedDate) {
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.addedDate = addedDate;
    }
}
