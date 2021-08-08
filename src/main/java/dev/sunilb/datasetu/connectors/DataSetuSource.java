package dev.sunilb.datasetu.connectors;

import dev.sunilb.datasetu.entities.Page;

public interface DataSetuSource {
    public String fetch();
    public void updatePage(Page page);
}
