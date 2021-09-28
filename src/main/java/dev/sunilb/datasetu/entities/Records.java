package dev.sunilb.datasetu.entities;

import dev.sunilb.datasetu.exceptions.DuplicateFieldException;
import dev.sunilb.datasetu.exceptions.InvalidFieldException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Records implements Iterable<Row> {

    private long count;
    private List<String> fieldList;
    private List<Row> records;

    public Records(List<String> fieldList) {

        if (doesFieldListHaveDuplicates(fieldList))
            throw new DuplicateFieldException("field1 detected to be a duplicate");

        this.fieldList = new ArrayList<String>(fieldList);
        this.records = new ArrayList<Row>();
    }


    public Records() {
        this.fieldList = new ArrayList<String>();
        this.records = new ArrayList<Row>();
    }

    public long count() {
        return records.size();
    }


    public String getFieldNameAtPosition(int fieldPosition) {
        if (fieldPosition >= fieldList.size()) throw new InvalidFieldException("Invalid Field Position");
        return fieldList.get(fieldPosition);
    }

    public int getFieldPositionForGivenName(String fieldName) {
        int fieldPosition = fieldList.indexOf(fieldName);
        if (fieldPosition == -1) throw new InvalidFieldException("Invalid Field Position");
        return fieldPosition;
    }

    public void insert(String[] data) {
        Row row = new Row(data, this);
        records.add(row);
    }

    public Row getRow(int recordNumber) {
        return records.get(recordNumber);
    }


    private boolean doesFieldListHaveDuplicates(List<String> fieldList) {

        HashMap<String, Integer> fieldMap = new HashMap<>();
        fieldList.forEach(s -> {
            fieldMap.put(s, 1);
        });

        if (fieldList.size() != fieldMap.size()) return true;

        return false;
    }

    @Override
    public Iterator<Row> iterator() {
        return new RecordsIterator<Row>(this);
    }

    public int fieldsCount() {
        return fieldList.size();
    }

    private class RecordsIterator<Row> implements Iterator<Row> {

        Records records;
        int recordPosition;

        public RecordsIterator(Records records) {
            this.records = records;
            this.recordPosition = 0;
        }

        @Override
        public boolean hasNext() {
            return recordPosition < records.count();
        }

        @Override
        public Row next() {
            Row row = (Row) records.getRow(recordPosition);
            recordPosition = recordPosition + 1;
            return row;

        }
    }

}
