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

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rowData.size(); i++) {
            sb.append(rowData.get(i));
            if (i + 1 != rowData.size()) {
                sb.append(",");
            }
        }

        return sb.toString();

    }
}
