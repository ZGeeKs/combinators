package com.github.zgeeks.combinators;

import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;

public final class Predicates {

    public static <T> Predicate<T> negate(Predicate<T> predicate) {
        return predicate.negate();
    }

    public static <T> Predicate<T> and(Predicate<T> first, Predicate<T> second) {
        return first.and(second);
    }

    public static <T> Predicate<T> and(Predicate<T>... predicates) {
        return stream(predicates).reduce(always(), Predicates::and);
    }

    public static <T> Predicate<T> or(Predicate<T> first, Predicate<T> second) {
        return first.or(second);
    }

    public static <T> Predicate<T> or(Predicate<T>... predicates) {
        return stream(predicates).reduce(never(), Predicates::or);
    }

    public static <T> Predicate<T> equalTo(Function<? super T, ?> keyExtractor, Object value) {
        return combine(Objects::equals, keyExtractor, value);
    }

    public static <T> Predicate<T> isAssignableFrom(Function<? super T, ?> keyExtractor, Class<?> clazz) {
        return x -> keyExtractor.apply(x).getClass().isAssignableFrom(clazz);
    }

    public static <T> Predicate<T> isAssignableFrom(Class<?> clazz) {
        return isAssignableFrom(identity(), clazz);
    }

    public static <T> Predicate<T> isAnnotationPresent(Function<? super T, ?> keyExtractor, Class<? extends Annotation> annotationClass) {
        return x -> keyExtractor.apply(x).getClass().isAnnotationPresent(annotationClass);
    }

    public static <T> Predicate<T> isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return isAnnotationPresent(identity(), annotationClass);
    }

    private static <T, U> Predicate<T> comparing(Function<? super T, ? extends U> keyExtractor, U value, Comparator<U> comparator, int expected) {
        return x -> comparator.compare(keyExtractor.apply(x), value) == expected;
    }

    public static <T, U extends Comparable<U>> Predicate<T> gt(Function<? super T, ? extends U> keyExtractor, U value) {
        return x -> keyExtractor.apply(x).compareTo(value) == 1;
    }

    public static <T, U> Predicate<T> gt(Function<? super T, ? extends U> keyExtractor, U value, Comparator<? super U> comparator) {
        return comparing(keyExtractor, value, comparator, 1);
    }

    public static <T, U extends Comparable<U>> Predicate<T> gte(Function<? super T, ? extends U> keyExtractor, U value) {
        return x -> keyExtractor.apply(x).compareTo(value) >= 0;
    }

    public static <T, U extends Comparable<U>> Predicate<T> lt(Function<? super T, ? extends U> keyExtractor, U value) {
        return x -> keyExtractor.apply(x).compareTo(value) == -1;
    }

    public static <T, U extends Comparable<U>> Predicate<T> lte(Function<? super T, ? extends U> keyExtractor, U value) {
        return x -> keyExtractor.apply(x).compareTo(value) <= 0;
    }

    public static <T, U> Predicate<T> combine(BiPredicate<? super U, ? super U> combinator,
                                              Function<? super T, ? extends U> mapper, U value) {
        Objects.requireNonNull(combinator);
        Objects.requireNonNull(mapper);
        return x -> combinator.test(mapper.apply(x), value);
    }

    public static <T> Predicate<T> always() {
        return x -> true;
    }

    public static <T> Predicate<T> never() {
        return x -> false;
    }

    private Predicates() {}
}
