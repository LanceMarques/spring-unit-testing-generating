package com.tcc.springunittestinggenerating.utils;

import com.tcc.springunittestinggenerating.utils.enums.BounderTestType;

import javax.validation.constraints.Size;
import java.lang.reflect.Field;
import java.util.Arrays;

import static com.tcc.springunittestinggenerating.utils.enums.BounderTestType.*;

public class StringBounderPopulator {

    public static void initializeSizeAnnotedStringFields(final Object object, final BounderTestType bounderTestType) {

        final Class<?> objectClass = object.getClass();

        final Field[] objectFields = objectClass.getDeclaredFields();

        Arrays.stream(objectFields).forEach(field -> {
            try {
                setStringValue(object, field, bounderTestType);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

    }

    private static void setStringValue(final Object object, final Field field, BounderTestType bounderTestType) throws IllegalAccessException {

        final boolean isSizeAnnotationPresent = field.isAnnotationPresent(Size.class);

        if (isSizeAnnotationPresent){

            Integer stringSize = 0;

            char[] charFill = new char[stringSize];

            final Size sizeAnnotation = field.getAnnotation(Size.class);

            if (MINIMUM_ALLOWED.equals(bounderTestType)||UNDERFLOW.equals(bounderTestType)){
                stringSize += sizeAnnotation.min();
            } else if (MAXIMUM_ALLOWED.equals(bounderTestType)||OVERFLOW.equals(bounderTestType)){
                stringSize += sizeAnnotation.max();
            }

            stringSize += bounderTestType.getSizeIncrement();

            if (stringSize > 0){

                charFill = new char[stringSize];

            }

            String stringFill = new String(charFill);

            field.setAccessible(true);

            field.set(object, stringFill);

        }

    }

}
