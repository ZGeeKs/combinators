package io.zgeeks.combinators;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Arrays.stream;

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

    public static <T> Predicate<T> isNull(Object object) {
        return x -> Objects.isNull(object);
    }

    public static <T> Predicate<T> notNull(Object object) {
        return negate(isNull(object));
    }

    public static <T> Predicate<T> equalTo(Function<? super T, ?> mapper, Object value) {
        return combine(Objects::equals, mapper, value);
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
