package dev.sunilb;

import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.*;

public class RecordsFieldTest {

    @Test
    public void shouldReturnFieldNameForGivenPosition() {

        String[] fieldList = new String[]{"field1", "field2", "field3"};
        Records records = new Records(Arrays.asList(fieldList));

        assertEquals(records.getFieldName(0), "field1");
        assertEquals(records.getFieldName(1), "field2");
        assertEquals(records.getFieldName(2), "field3");
    }

    @Test
    public void shouldReturnFieldPositionForGivenName() {

        String[] fieldList = new String[]{"field1", "field2", "field3"};
        Records records = new Records(Arrays.asList(fieldList));

        assertEquals(records.getFieldPosition("field1"), 0);
        assertEquals(records.getFieldPosition("field2"), 1);
        assertEquals(records.getFieldPosition("field3"), 2);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void shouldThrowRuntimeExceptionOnInvalidFieldPosition() {
        String[] fieldList = new String[]{"field1", "field2", "field3"};
        Records records = new Records(Arrays.asList(fieldList));
        assertEquals(records.getFieldName(3), "field4");
    }

    @Test
    public void shouldThrowRuntimeExceptionForDuplicateFieldName() {
        //TODO: validate duplicate fieldname
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenFieldNameOrPositionDoesNotExist() {
        //TODO: trying to get a field that is not available
    }

}
