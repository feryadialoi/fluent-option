package dev.feryadialoi.fluentoption;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class OptionTest {

    @Test
    void testSome() {
        var option = Option.some("value");
        assertNotNull(option);
        assertEquals("value", option.get());
    }

    @Test
    void testNone() {
        var option = Option.none();
        assertNotNull(option);
        assertThrows(NoSuchElementException.class, option::get);
    }

    @Test
    void testIsSome() {
        var option = Option.some("value");
        assertNotNull(option);
        assertTrue(option.isSome());
    }

    @Test
    void testIsNone() {
        var option = Option.none();
        assertNotNull(option);
        assertTrue(option.isNone());
    }

    @Test
    void testMapSome() {
        var option = Option.some("value").map(String::toUpperCase);
        assertNotNull(option);
        assertEquals("VALUE", option.get());
    }

    @Test
    void testMapNone() {
        var option = Option.<String>none().map(String::toUpperCase);
        assertNotNull(option);
        assertThrows(NoSuchElementException.class, option::get);
    }

    @Test
    void testFlatMapSome() {
        var option = Option.some("value").flatMap(data -> Option.some(data.toUpperCase()));
        assertNotNull(option);
        assertEquals("VALUE", option.get());
    }

    @Test
    void testFlatMapNone() {
        var option = Option.<String>none().flatMap(data -> Option.some(data.toUpperCase()));
        assertNotNull(option);
        assertThrows(NoSuchElementException.class, option::get);
    }

    @Test
    void testGetSome() {
        var option = Option.some("value");
        assertNotNull(option);
        assertEquals("value", option.get());
    }

    @Test
    void testGetNone() {
        var option = Option.none();
        assertNotNull(option);
        assertThrows(NoSuchElementException.class, option::get);
    }

    @Test
    void testOrElseSome() {
        var option = Option.some("value");
        assertNotNull(option);
        assertEquals("value", option.orElse("value"));
    }

    @Test
    void testOrElseNone() {
        var option = Option.none();
        assertNotNull(option);
        assertEquals("value", option.orElse("value"));
    }

    @Test
    void testOrSome() {
        var option = Option.some("value").or(() -> Option.some("value"));
        assertNotNull(option);
        assertEquals("value", option.get());
    }

    @Test
    void testOrNone() {
        var option = Option.none().or(() -> Option.some("value"));
        assertNotNull(option);
        assertEquals("value", option.get());
    }

    @Test
    void testOrElseGetSome() {
        var data = Option.some("value").orElseGet(() -> "value");
        assertEquals("value", data);
    }

    @Test
    void testOrElseGetNone() {
        var data = Option.none().orElseGet(() -> "value");
        assertEquals("value", data);
    }

    @Test
    void testOrElseThrowSome() {
        var data = Option.some("value").orElseThrow();
        assertEquals("value", data);
    }

    @Test
    void testOrElseThrowNone() {
        assertThrows(NoSuchElementException.class, () -> Option.none().orElseThrow());
    }

    @Test
    void testOrElseThrowSupplierExceptionSome() {
        var data = Option.some("value").orElseThrow(RuntimeException::new);
        assertEquals("value", data);
    }

    @Test
    void testOrElseThrowSupplierExceptionNone() {
        assertThrows(RuntimeException.class, () -> Option.none().orElseThrow(RuntimeException::new));
    }

    @Test
    void testMatch() {
        var finalOption = Option.some("value").match(option -> switch (option) {
            case Some<String>(var data) -> Option.some(data.toUpperCase());
            case None<String> none -> none;
        });
        assertNotNull(finalOption);
        assertEquals("VALUE", finalOption.get());
    }

}