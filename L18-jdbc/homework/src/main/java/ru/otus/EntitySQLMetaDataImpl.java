package ru.otus;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private EntityClassMetaData<T> classMetData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> classMetaData) {
        this.classMetData = classMetaData;
    }


    @Override
    public String getSelectAllSql() {
        return "select * from " + classMetData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("select * from %s where %s  = ?", classMetData.getName(), classMetData.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        return String.format("insert into %s(%s) values (?)", classMetData.getName(),
                classMetData.getFieldsWithoutId().stream().map(Field::getName).collect(Collectors.joining(",")));
    }

    @Override
    public String getUpdateSql() {
        return String.format("update %s set %s where %s = ?", classMetData.getName(),
                classMetData.getFieldsWithoutId().stream().map(Field::getName).collect(Collectors.joining(" = ?,")),
                classMetData.getIdField().getName());
    }
}
