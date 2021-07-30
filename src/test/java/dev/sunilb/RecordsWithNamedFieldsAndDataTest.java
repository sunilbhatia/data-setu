package dev.sunilb;

import dev.sunilb.datasetu.entities.Records;
import dev.sunilb.datasetu.entities.Row;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.*;

public class RecordsWithNamedFieldsAndDataTest {

    @Test
    public void shouldReturnZeroWhenNoRecordsAvailable() {
        Records records = new Records();
        assertEquals(records.count(), 0);
    }


    @Test
    public void shouldReturnCorrectNumberOfRecords() {
        Records records = getRecordObjectsWithNamedFieldsAndThreeRecords();
        assertEquals(records.count(), 3);
    }

    @Test
    public void shouldCreateRecordsWithNamedFields() {
        Records records = getRecordObjectsWithNamedFieldsAndThreeRecords();

        assertEquals(records.getRow(0).field("field1"), "data1");
        assertEquals(records.getRow(0).field("field2"), "data2");
        assertEquals(records.getRow(0).field("field3"), "data3");

        assertEquals(records.getRow(1).field("field1"), "data11");
        assertEquals(records.getRow(1).field("field2"), "data22");
        assertEquals(records.getRow(1).field("field3"), "data33");

        assertEquals(records.getRow(2).field("field1"), "data111");
        assertEquals(records.getRow(2).field("field2"), "data222");
        assertEquals(records.getRow(2).field("field3"), "data333");

    }

    @Test
    public void shouldIterateOverNamedFieldRecords() {

        Records records = getRecordObjectsWithNamedFieldsAndThreeRecords();

        String[][] recordsToCompareWith = {
                {"data1", "data2", "data3"},
                {"data11", "data22", "data33"},
                {"data111", "data222", "data333"},
        };

        int rowPosition = 0;
        for (Row row : records) {
            assertEquals(row.field("field1"), recordsToCompareWith[rowPosition][0]);
            assertEquals(row.field("field2"), recordsToCompareWith[rowPosition][1]);
            assertEquals(row.field("field3"), recordsToCompareWith[rowPosition][2]);
            rowPosition = rowPosition + 1;
        }

    }

    @Test
    public void shouldCreateRecordsObjectWithUnnamedFields() {
        Records records = getRecordObjectsWithUnamedFieldsAndThreeRecords();

        assertEquals(records.getRow(0).field(0), "data1");
        assertEquals(records.getRow(0).field(1), "data2");
        assertEquals(records.getRow(0).field(2), "data3");

        assertEquals(records.getRow(1).field(0), "data11");
        assertEquals(records.getRow(1).field(1), "data22");
        assertEquals(records.getRow(1).field(2), "data33");

        assertEquals(records.getRow(2).field(0), "data111");
        assertEquals(records.getRow(2).field(1), "data222");
        assertEquals(records.getRow(2).field(2), "data333");
    }

    @Test
    public void shouldIterateOverUnamedFieldRecords() {

        Records records = getRecordObjectsWithUnamedFieldsAndThreeRecords();

        String[][] recordsToCompareWith = {
                {"data1", "data2", "data3"},
                {"data11", "data22", "data33"},
                {"data111", "data222", "data333"},
        };

        int rowPosition = 0;
        for (Row row : records) {
            assertEquals(row.field(0), recordsToCompareWith[rowPosition][0]);
            assertEquals(row.field(1), recordsToCompareWith[rowPosition][1]);
            assertEquals(row.field(2), recordsToCompareWith[rowPosition][2]);
            rowPosition = rowPosition + 1;
        }

    }


    private Records getRecordObjectsWithNamedFieldsAndThreeRecords() {
        String[] fieldList = new String[]{"field1", "field2", "field3"};
        Records records = new Records(Arrays.asList(fieldList));

        records.insert(new String[]{"data1", "data2", "data3"});
        records.insert(new String[]{"data11", "data22", "data33"});
        records.insert(new String[]{"data111", "data222", "data333"});
        return records;
    }

    private Records getRecordObjectsWithUnamedFieldsAndThreeRecords() {
        Records records = new Records();
        records.insert(new String[]{"data1", "data2", "data3"});
        records.insert(new String[]{"data11", "data22", "data33"});
        records.insert(new String[]{"data111", "data222", "data333"});
        return records;
    }
}
