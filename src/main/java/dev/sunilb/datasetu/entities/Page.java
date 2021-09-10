package dev.sunilb.datasetu.entities;

public class Page {

    final private int rowCount;
    final private String nextPageToken;

    public Page() {
        this(0, "");
    }

    public Page(int rowCount) {
        this(rowCount, "");
    }

    public Page(int rowCount, String nextPageToken) {
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
