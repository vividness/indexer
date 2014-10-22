package org.indexer.indexing;

import org.apache.lucene.document.Document;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class InputReaderTest {
    @Test(expected = IOException.class)
    public void testInputReaderWithAnEmptyFile() throws Exception {
        InputReader reader = new InputReader("src/test/resources/InputReaderTest/empty.csv");
    }

    @Test
    public void testHasNextEmptyFile() throws Exception {
        InputReader reader = new InputReader("src/test/resources/InputReaderTest/empty_with_headers.csv");
        assertFalse(reader.hasNext());
    }

    @Test
    public void testHasNextNonEmptyFile() throws Exception {
        InputReader reader = new InputReader("src/test/resources/InputReaderTest/sample_data.csv");
        assertTrue(reader.hasNext());
    }

    @Test
    public void testNext() throws Exception {
        InputReader reader = new InputReader("src/test/resources/InputReaderTest/sample_data.csv");
        assertTrue(reader.hasNext());

        Document doc = reader.next();
        assertEquals(doc.get("id"), "1");
        assertEquals(doc.get("email"), "vlada.ivic@gm.com");
        assertEquals(doc.get("name"), "Vladimir");

        assertTrue(reader.hasNext());
        doc = reader.next();
        assertEquals(doc.get("id"), "2");
        assertEquals(doc.get("email"), "mika@rs.com");
        assertEquals(doc.get("name"), "Mika");

        assertFalse(reader.hasNext());
    }
}