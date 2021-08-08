package dev.sunilb.datasetu;

public class Page {

    private int rowCount;
    private String nextPageToken;

    public Page(int rowCount, String nextPageToken) {
        this.rowCount = rowCount;
        this.nextPageToken = nextPageToken;
    }

    public boolean hasNext() {
        return nextPageToken != null;
    }

}
