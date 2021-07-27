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
        String [] fieldList = new String[]{"field1", "field2", "field3"};
        Records records = new Records(Arrays.asList(fieldList));
        assertEquals(records.getField(0), "field1");
        assertEquals(records.getField(1), "field2");
        assertEquals(records.getField(2), "field3");
    }


    @Test (expectedExceptions = RuntimeException.class)
    public void shouldThrowRuntimeExceptionOnInvalidFieldPosition() {
        String [] fieldList = new String[]{"field1", "field2", "field3"};
        Records records = new Records(Arrays.asList(fieldList));
        assertEquals(records.getField(3), "field4");
    }



    public void shouldIterateOverNamedFieldRecords() {

    }

    public void shouldCreateRecordsObjectWithUnnamedFields() {

    }

    public void shouldIterateOverUnnamedFieldRecords() {

    }

}
