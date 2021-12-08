package com.karrotmvp.ourapt.v1.common;

import java.util.*;
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
                .collect(Collectors.groupingBy(leftPKGetter, LinkedHashMap::new, Collectors.toList()));
        rightList.forEach((r) -> {
            Optional.ofNullable(hashTable.get(rightPKGetter.apply(r)))
                    .orElseGet(ArrayList::new)
                    .forEach(l -> leftJoinFieldSetter.accept(l, r));
        });

        List<L> resultList = new ArrayList<>();
        hashTable.values().forEach(resultList::addAll);
        return resultList;
    }

    public static int getRandomInt(int max) {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return random.nextInt(max);
    }

    public static Date addDate(Date source, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(source);
        calendar.add(Calendar.DATE, amount);
        return calendar.getTime();
    }
}
