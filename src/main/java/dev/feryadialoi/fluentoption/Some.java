package dev.feryadialoi.fluentoption;

public record Some<T>(T data) implements Option<T> {
}
