package com.dimsoft.clinicStackProd.util;

public  class  StringUtil {
    public static Integer getLastMatch(String term) {
        int index = term.lastIndexOf(':');
        String res = term.substring(index+1);
        return Integer.valueOf(res);
    }
}
