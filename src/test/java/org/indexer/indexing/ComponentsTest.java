package org.indexer.indexing;

import org.apache.commons.csv.CSVParser;
import org.apache.lucene.index.IndexWriter;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComponentsTest {
    @Test
    public void testGetOutputWriterOKWithCreateOpenMode() throws Exception {
        String indexDir = "src/test/resources/ComponentsTest/testGetOutputWriterOKWithCreateOpenMode";

        OutputWriter writer = Components.getOutputWriter(indexDir);
        assertEquals(writer.getClass(), OutputWriter.class);
        writer.close();

        writer = Components.getOutputWriter(indexDir, Indexer.OpenMode.CREATE);
        assertEquals(writer.getClass(), OutputWriter.class);
        writer.close();

        writer = Components.getOutputWriter(indexDir, Indexer.OpenMode.APPEND);
        assertEquals(writer.getClass(), OutputWriter.class);
        writer.close();

        writer = Components.getOutputWriter(indexDir, Indexer.OpenMode.CREATE_OR_APPEND);

        assertEquals(writer.getClass(), OutputWriter.class);
        writer.close();
    }

    @Test
    public void testGetInputReaderOK() throws Exception {
        InputReader reader = Components.getInputReader("src/test/resources/ComponentsTest/testGetInputReaderOK/empty_with_headers.csv");

        assertEquals(reader.getClass(), InputReader.class);
    }

    @Test(expected = java.io.FileNotFoundException.class)
    public void testGetInputReaderError() throws Exception {
        Components.getInputReader("src/test/resources/ComponentsTest/testGetInputReaderError");
    }

    @Test
    public void testGetCsvParserOK() throws Exception {
        CSVParser parser = Components.CSV.getCsvParser("src/test/resources/ComponentsTest/testGetCsvParserOK/empty.csv");
        assertEquals(parser.getClass(), CSVParser.class);
    }

    @Test(expected = java.io.FileNotFoundException.class)
    public void testGetCsvParserError() throws Exception {
        Components.CSV.getCsvParser("src/test/resources/ComponentsTest/testGetCsvParserError/file_does_not_exist");
    }

    @Test
    public void testGetIndexWriterOK() throws Exception {
        IndexWriter writer = Components.Lucene.getIndexWriter("src/test/resources/ComponentsTest/testGetIndexWriterOK");

        assertEquals(writer.getClass(), IndexWriter.class);
        writer.close();
    }

    @Test
    public void testGetIndexWriterOKWithOpenMode() throws Exception {
        String indexPath = "src/test/resources/ComponentsTest/testGetIndexWriterOKWithOpenMode";

        IndexWriter writer = Components.Lucene.getIndexWriter(indexPath, Indexer.OpenMode.CREATE);
        assertEquals(writer.getClass(), IndexWriter.class);
        writer.close();

        writer = Components.Lucene.getIndexWriter(indexPath, Indexer.OpenMode.CREATE_OR_APPEND);
        assertEquals(writer.getClass(), IndexWriter.class);
        writer.close();

        writer = Components.Lucene.getIndexWriter(indexPath, Indexer.OpenMode.APPEND);
        assertEquals(writer.getClass(), IndexWriter.class);
        writer.close();
    }
}