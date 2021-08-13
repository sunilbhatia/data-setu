package dev.sunilb.datasetu.entities;

public class Page {

    final private int rowCount;
    final private String nextPageToken;

    public Page() {
        rowCount = 0;
        nextPageToken = "";
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
