package com.dimsoft.clinicStackProd.util;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper<T> {
    public List<Object> castToObject(List<T> objectList) {
        List<Object> objList = new ArrayList<Object>();
        for (T object : objectList)
            objList.add(castToObject(object));
        return objList;
    }

    public Object castToObject(T object) {
        return (Object) object;
    }
}
