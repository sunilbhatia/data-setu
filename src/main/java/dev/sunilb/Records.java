package dev.sunilb;

import java.util.ArrayList;
import java.util.List;

public class Records {

    //TODO: validate duplicate fieldname
    //TODO: trying to get a field that is not available

    private long count;
    private List<String> fieldList;
    private List<Row> records;

    public Records(List<String> fieldList) {
        this.fieldList = new ArrayList<String>(fieldList);
        this.records = new ArrayList<Row>();
    }

    public Records() {
        // TODO: initialize the fieldList to empty
    }


    public long count() {
        return 0;
    }

    public String getFieldName(int fieldPosition) {
        if (fieldPosition >= fieldList.size()) throw new RuntimeException("Invalid Field Position");
        return this.fieldList.get(fieldPosition);
    }

    public int getFieldPosition(String fieldName) {
        return fieldList.indexOf(fieldName);
    }

    public void insert(Row row) {
        records.add(row);
    }

    public Row getRow(int recordNumber) {
        return records.get(recordNumber);
    }
}
