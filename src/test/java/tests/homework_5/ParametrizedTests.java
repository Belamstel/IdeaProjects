package tests.homework_5;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("simple_tests")
public class ParametrizedTests {
    static Logger logger = LoggerFactory.getLogger(ParametrizedTests.class);


    @Test
    @Tag("apple_tag")
    void paramitrizedStringTest() {
        String name = System.getProperty("name", "lemon");
        logger.info("name = " + name);
        assertEquals("apple", name, "name = " + name);
    }
}