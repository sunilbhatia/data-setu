package dev.sunilb;

import java.util.ArrayList;
import java.util.List;

public class Records {

    private long count;
    private List<String> fieldList;
    private List<Row> records;

    public Records(List<String> fieldList) {
        this.fieldList = new ArrayList<String>(fieldList);
        this.records = new ArrayList<Row>();
    }

    public Records() {
        // TODO: initialize the fieldList to empty
        this.fieldList = new ArrayList<String>();
        this.records = new ArrayList<Row>();
    }


    public long count() {
        return records.size();
    }

    public String getFieldName(int fieldPosition) {
        if (fieldPosition >= fieldList.size()) throw new RuntimeException("Invalid Field Position");
        return fieldList.get(fieldPosition);
    }

    public int getFieldPosition(String fieldName) {
        return fieldList.indexOf(fieldName);
    }

    public void insert(String[] data) {
        Row row = new Row(data, this);
        records.add(row);
    }

    public Row getRow(int recordNumber) {
        return records.get(recordNumber);
    }
}
