package ru.otus;

import ru.otus.crm.model.ID;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> entity;

    EntityClassMetaDataImpl(Class<T> entity) {
        this.entity = entity;
    }


    @Override
    public String getName() {
        return entity.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() throws NoSuchMethodException {
        return entity.getConstructor(getAllFields().stream().map(Field::getType).toArray(Class[]::new));
    }

    @Override
    public Field getIdField() {
        return getAllFields().stream().filter(field -> field.isAnnotationPresent(ID.class)).findFirst().orElseThrow();
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.asList(entity.getDeclaredFields().clone());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return getAllFields().stream().filter(field -> !field.isAnnotationPresent(ID.class)).collect(Collectors.toList());
    }
}
