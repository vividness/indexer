package org.indexer.indexing;

import org.apache.commons.csv.CSVParser;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComponentsTest {
    @Test
    public void testGetOutputWriterOKWithCreateOpenMode() throws Exception {
        OutputWriter writer = Components.getOutputWriter("src/test/resources/index1");

        assertEquals(writer.getClass(), OutputWriter.class);
        writer.close();
    }

    @Test
    public void testGetOutputWriterOKWithAppendOpenMode() throws Exception {
        OutputWriter writer = Components.getOutputWriter("src/test/resources/index1", Indexer.OpenMode.APPEND);

        assertEquals(writer.getClass(), OutputWriter.class);
        writer.close();
    }

    @Test
    public void testGetOutputWriterOKWithAppendOrCreateOpenMode() throws Exception {
        OutputWriter writer = Components.getOutputWriter("src/test/resources/index1", Indexer.OpenMode.APPEND);

        assertEquals(writer.getClass(), OutputWriter.class);
        writer.close();
    }

    @Test
    public void testGetInputReaderOK() throws Exception {
        InputReader reader = Components.getInputReader("src/test/resources/input_files/empty.csv");

        assertEquals(reader.getClass(), InputReader.class);
    }

    @Test(expected=java.io.FileNotFoundException.class)
    public void testGetInputReaderError() throws Exception {
        Components.getInputReader("src/test/resources/files");
    }

    @Test
    public void testGetCsvParserOK() throws Exception {
        CSVParser parser = Components.CSV.getCsvParser("src/test/resources/input_files/empty.csv");
        assertEquals(parser.getClass(), CSVParser.class);
    }

    @Test(expected=java.io.FileNotFoundException.class)
    public void testGetCsvParserError() throws Exception {
        Components.CSV.getCsvParser("src/test/resources/input_files/file_does_not_exist");
    }
}