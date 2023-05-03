package dynamiccalls.action.stateful.blog;

import java.util.List;

public class Blog {
    public BlogEntry addEntry(BlogEntry blogEntry) {
        BlogStash.ENTRIES.add(blogEntry);
        return blogEntry;
    }

    public List<BlogEntry> getEntries() {
        return BlogStash.ENTRIES;
    }
}
