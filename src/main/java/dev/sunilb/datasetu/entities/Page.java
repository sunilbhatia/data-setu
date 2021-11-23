package dev.sunilb.datasetu.entities;

public class Page {

    final private long rowCount;
    final private String nextPageToken;

    public Page() {
        this(0, "");
    }

    public Page(long rowCount) {
        this(rowCount, "");
    }

    public Page(long rowCount, String nextPageToken) {
        this.rowCount = rowCount;
        this.nextPageToken = nextPageToken;
    }

    public boolean hasNext() {
        return nextPageToken != null;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }
}
