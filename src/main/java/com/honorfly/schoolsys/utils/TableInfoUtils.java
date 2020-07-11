package com.honorfly.schoolsys.utils;


import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Method;

public class TableInfoUtils {
    public static String getTableName(Class clazz) {
        Table annotation = (Table) clazz.getAnnotation(Table.class);
        if (annotation != null) {
            return annotation.name();
        }
        return null;
    }


//获取字段名

    public static String getColumnName(Class clazz, String fieldName) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Column.class)) {
                if (method.getName().equalsIgnoreCase("get" + fieldName)) {
                    Column column = method.getAnnotation(Column.class);
                    return column.name();
                }

            }
        }
        return null;

    }
}
