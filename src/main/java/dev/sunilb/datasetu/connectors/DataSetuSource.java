package dev.sunilb.datasetu.connectors;

import dev.sunilb.datasetu.Page;

import java.util.List;

public interface DataSetuSource {
    public String fetch();
    public void updatePage(Page page);
}
