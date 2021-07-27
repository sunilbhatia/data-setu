package dev.sunilb;

import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.*;

public class RecordsWithNamedFieldsAndDataTest {

    @Test
    public void shouldCreateRecordsObjectWithNoData() {
        Records records = new Records();
        assertEquals(records.count(), 0);
    }


    @Test
    public void shouldCreateRecordsObjectWithNamedFields() {
        String[] fieldList = new String[]{"field1", "field2", "field3"};
        Records records = new Records(Arrays.asList(fieldList));

        Row row1 = new Row(new String[]{"data1", "data2", "data3"}, records);
        Row row2 = new Row(new String[]{"data11", "data22", "data33"}, records);
        Row row3 = new Row(new String[]{"data111", "data222", "data333"}, records);

        records.insert(row1);
        records.insert(row2);
        records.insert(row3);

        assertEquals(records.getRow(0).field("field1"), "data1");
        assertEquals(records.getRow(0).field(1), "data2");
        assertEquals(records.getRow(0).field(2), "data3");

        assertEquals(records.getRow(1).field(0), "data11");
        assertEquals(records.getRow(1).field(1), "data22");
        assertEquals(records.getRow(1).field(2), "data33");

        assertEquals(records.getFieldName(0), "field1");
        assertEquals(records.getFieldName(1), "field2");
        assertEquals(records.getFieldName(2), "field3");
    }


    @Test(expectedExceptions = RuntimeException.class)
    public void shouldThrowRuntimeExceptionOnInvalidFieldPosition() {
        String[] fieldList = new String[]{"field1", "field2", "field3"};
        Records records = new Records(Arrays.asList(fieldList));
        assertEquals(records.getFieldName(3), "field4");
    }


    public void shouldIterateOverNamedFieldRecords() {

    }

    public void shouldCreateRecordsObjectWithUnnamedFields() {

    }

    public void shouldIterateOverUnnamedFieldRecords() {

    }

}
