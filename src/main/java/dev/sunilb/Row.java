package dev.sunilb;

import java.util.Arrays;
import java.util.List;

public class Row {

    List<String> rowData;
    Records forRecord;

    public Row(String [] data, Records forRecord) {
        this.rowData = Arrays.asList(data);
        this.forRecord = forRecord;
    }

    public String field(int fieldNumber) {
        return rowData.get(fieldNumber);
    }

    public String field(String fieldName) {
        return field(forRecord.getFieldPosition(fieldName));
    }
}
