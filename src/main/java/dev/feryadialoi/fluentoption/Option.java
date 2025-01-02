package dev.feryadialoi.fluentoption;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

public sealed interface Option<T> permits Some, None {

    static <T> Option<T> some(T data) {
        return new Some<>(data);
    }

    static <T> Option<T> none() {
        return new None<>();
    }

    default boolean isSome() {
        return this instanceof Some;
    }

    default boolean isNone() {
        return this instanceof None;
    }

    default <U> Option<U> map(Function<T, U> mapper) {
        return switch (this) {
            case Some<T>(T data) -> some(mapper.apply(data));
            case None<T>() -> none();
        };
    }

    default <U> Option<U> flatMap(Function<T, Option<U>> mapper) {
        return switch (this) {
            case Some<T>(T data) -> mapper.apply(data);
            case None<T>() -> none();
        };
    }

    default T get() {
        return switch (this) {
            case Some<T>(T data) -> data;
            case None<T>() -> throw new NoSuchElementException("No data present");
        };
    }

    default T orElse(T other) {
        return switch (this) {
            case Some<T>(T data) -> data;
            case None<T>() -> other;
        };
    }

    default Option<T> or(Supplier<Option<T>> other) {
        return switch (this) {
            case Some<T> some -> some;
            case None<T>() -> other.get();
        };
    }

    default T orElseGet(Supplier<T> other) {
        return switch (this) {
            case Some<T>(T data) -> data;
            case None<T>() -> other.get();
        };
    }

    default T orElseThrow() {
        return switch (this) {
            case Some<T>(T data) -> data;
            case None<T>() -> throw new NoSuchElementException("No data present");
        };
    }

    default <E extends Exception> T orElseThrow(Supplier<E> supplier) throws E {
        return switch (this) {
            case Some<T>(T data) -> data;
            case None<T>() -> throw supplier.get();
        };
    }

    default <U> U match(Function<Option<T>, U> matcher) {
        return matcher.apply(this);
    }

}
