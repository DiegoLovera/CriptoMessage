package com.diegolovera.simplecypher;

import com.diegolovera.simplecypher.Exceptions.InvalidLetterException;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void encodeIsOk() {
        SimpleCypher cypher = new SimpleCypher.SimpleCypherBuilder("onlyou").build();
        try {
            String originalMessage = "Example local unit test, which will execute on the development machine (host).";
            String s = cypher.encode(originalMessage);

            String a = cypher.decode(s);

            assertEquals(originalMessage, a);
        } catch (InvalidLetterException e) {
            e.printStackTrace();
        }
    }
}