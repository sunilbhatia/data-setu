package dev.sunilb.entities;

import dev.sunilb.datasetu.entities.Records;
import dev.sunilb.datasetu.exceptions.DuplicateFieldException;
import dev.sunilb.datasetu.exceptions.InvalidFieldException;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.*;

public class RecordsFieldTest {

    @Test
    public void shouldReturnFieldNameForGivenPosition() {

        String[] fieldList = new String[]{"field1", "field2", "field3"};
        Records records = new Records(Arrays.asList(fieldList));

        assertEquals(records.getFieldNameAtPosition(0), "field1");
        assertEquals(records.getFieldNameAtPosition(1), "field2");
        assertEquals(records.getFieldNameAtPosition(2), "field3");
    }

    @Test
    public void shouldReturnFieldPositionForGivenName() {

        String[] fieldList = new String[]{"field1", "field2", "field3"};
        Records records = new Records(Arrays.asList(fieldList));

        assertEquals(records.getFieldPositionForGivenName("field1"), 0);
        assertEquals(records.getFieldPositionForGivenName("field2"), 1);
        assertEquals(records.getFieldPositionForGivenName("field3"), 2);
    }

    @Test
    public void shouldReturnFieldCount() {

        String[] fieldList = new String[]{"field1", "field2", "field3"};
        Records records = new Records(Arrays.asList(fieldList));

        assertEquals(records.fieldsCount(), 3);
    }

    @Test(expectedExceptions = InvalidFieldException.class)
    public void shouldThrowInvalidFieldExceptionWhenFieldPositionDoesNotExist() {
        String[] fieldList = new String[]{"field1", "field2", "field3"};
        Records records = new Records(Arrays.asList(fieldList));
        assertEquals(records.getFieldNameAtPosition(3), "field4");
    }

    @Test
    public void shouldReturnHeadersForRecords() {
        String[] fieldList = new String[]{"field1", "field2", "field3"};
        Records records = new Records(Arrays.asList(fieldList));
        String [] fields = records.getFields();
        for (int i = 0; i < fields.length; i++) {
            assertEquals(fields[i], fieldList[i]);
        }
    }


    @Test(expectedExceptions = InvalidFieldException.class)
    public void shouldThrowInvalidFieldExceptionWhenFieldNameDoesNotExist() {
        String[] fieldList = new String[]{"field1", "field2", "field3"};
        Records records = new Records(Arrays.asList(fieldList));
        assertEquals(records.getFieldPositionForGivenName("field4"), 3);
    }

    @Test(expectedExceptions = DuplicateFieldException.class)
    public void shouldThrowDuplicateFieldExceptionForDuplicateFieldName() {
        String[] fieldList = new String[]{"field1", "field2", "field3", "field1"};
        Records records = new Records(Arrays.asList(fieldList));
    }

}
