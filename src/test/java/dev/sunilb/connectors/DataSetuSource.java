package dev.sunilb.connectors;

import dev.sunilb.datasetu.entities.Records;

public interface DataSetuSource {
    public Records fetch();
}
