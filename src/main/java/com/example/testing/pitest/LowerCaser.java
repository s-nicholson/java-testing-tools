package com.example.testing.pitest;

import java.util.ArrayList;
import java.util.List;

public class LowerCaser {
    public List<String> lowerCase(List<String> strings) {
        var list = new ArrayList<String>();
        if (!strings.isEmpty()) {
            for (var s : strings) {
                list.add(s.toLowerCase());
            }
        }
        return list;
    }
}
