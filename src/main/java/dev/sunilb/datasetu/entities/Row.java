package dev.sunilb.datasetu.entities;

import java.util.Arrays;
import java.util.List;

public class Row {

    private List<String> rowData;
    private Records forRecord;

    public Row(String[] data, Records forRecord) {
        this.rowData = Arrays.asList(data);
        this.forRecord = forRecord;
    }

    public String valueOfField(int fieldNumber) {
        return rowData.get(fieldNumber);
    }

    public String valueOfField(String fieldName) {
        return valueOfField(forRecord.getFieldPositionForGivenName(fieldName));
    }
}
