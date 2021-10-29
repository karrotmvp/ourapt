package com.karrotmvp.ourapt.v1.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utils {

    @FunctionalInterface
    public interface TwoParameterConsumer<T, U> {
        void accept(T t, U u);
    }

    public static <L, R, P> List<L> leftOuterHashJoin(
            List<L> leftList,
            List<R> rightList,
            Function<L, P> leftPKGetter,
            Function<R, P> rightPKGetter,
            TwoParameterConsumer<L, R> leftJoinFieldSetter
    ) {

        Map<P, List<L>> hashTable = leftList.stream()
                .collect(Collectors.groupingBy(leftPKGetter));
        rightList.forEach((r) -> {
            Optional.ofNullable(hashTable.get(rightPKGetter.apply(r)))
                    .orElseGet(ArrayList::new)
                    .forEach(l -> leftJoinFieldSetter.accept(l, r));
        });

        List<L> resultList = new ArrayList<>();
        hashTable.values().forEach(resultList::addAll);
        return resultList;
    }


    public static <T> T parseJsonData(String json, TypeReference<T> t) {
        return new ObjectMapper().convertValue(json, t);
    }

    public static <T> T parseJsonData(String json, Class<T> t) {
        return new ObjectMapper().convertValue(json, t);
    }
}
