package dev.sunilb;

import java.util.ArrayList;
import java.util.List;

public class Records {

    private long count;
    private List<String> fieldList;

    public Records(List<String> fieldList) {
        this.fieldList = new ArrayList<String>(fieldList);
    }

    public Records() {
        // TODO: initialize the fieldList to empty
    }


    public long count() {
        return 0;
    }

    public String getField(int fieldPosition) {
        if (fieldPosition >= fieldList.size()) throw new RuntimeException("Invalid Field Position");
        return this.fieldList.get(fieldPosition);
    }
}
