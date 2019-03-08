package com.diegolovera.simplecypher;

import com.diegolovera.simplecypher.Exceptions.EmptyPasswordException;
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
    public void encodeIsOk() {
        SimpleCypher cypher = new SimpleCypher.SimpleCypherBuilder("onlyou").build();
        try {
            String originalMessage = "Example local unit test, which will execute on the development machine (host).";
            String s = cypher.encrypt(originalMessage);

            String a = cypher.decrypt(s);

            assertEquals(originalMessage, a);
        } catch (InvalidLetterException e) {
            e.printStackTrace();
        } catch (EmptyPasswordException e) {
            e.printStackTrace();
        }
    }
}