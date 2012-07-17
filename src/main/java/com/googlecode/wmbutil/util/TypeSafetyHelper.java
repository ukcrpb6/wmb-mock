package com.googlecode.wmbutil.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.List;

public class TypeSafetyHelper {
    public static <T> List<T> typeSafeList(List<?> untypedList, final Class<T> clazz) {
        return ImmutableList.copyOf(typeSafeIterable(untypedList, clazz));
    }

    public static <T> Iterable<T> typeSafeIterable(Iterable<?> untypedIterable, final Class<T> clazz) {
        return Iterables.filter(untypedIterable, clazz);
    }
}
