package org.indexer.indexing;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class OutputWriterTest {
    @Test
    public void testInsert() throws Exception {
        OutputWriter output = new OutputWriter("src/test/resources/OutputWriter/testInsert");

        Document doc = new Document();
        doc.add(new StringField("ID", "1", Field.Store.YES));
        doc.add(new StringField("NAME", "TEST USER", Field.Store.YES));

        output.insert(doc);
        output.close();

        FSDirectory directory = FSDirectory.open(new File("src/test/resources/OutputWriter/testInsert"));
        DirectoryReader index = DirectoryReader.open(directory);

        assertEquals(index.numDocs(), 1);
        assertEquals(index.document(index.maxDoc() - 1).get("ID"), "1");
        assertEquals(index.document(index.maxDoc() - 1).get("NAME"), "TEST USER");

        index.close();
    }

    @Test
    public void testUpdate() throws Exception {
        OutputWriter output = new OutputWriter("src/test/resources/OutputWriterTest/testUpdate");

        Document doc = new Document();
        doc.add(new StringField("ID", "1", Field.Store.YES));
        doc.add(new StringField("NAME", "TEST USER", Field.Store.YES));

        output.insert(doc);
        output.close();

        output = new OutputWriter("src/test/resources/OutputWriterTest/testUpdate");

        doc = new Document();
        doc.add(new StringField("ID", "1", Field.Store.YES));
        doc.add(new StringField("NAME", "UPDATED TEST USER", Field.Store.YES));

        output.update(doc);
        output.close();

        FSDirectory directory = FSDirectory.open(new File("src/test/resources/OutputWriterTest/testUpdate"));
        DirectoryReader index = DirectoryReader.open(directory);

        assertEquals(index.numDocs(), 1);
        assertEquals(index.document(index.maxDoc() - 1).get("ID"), "1");
        assertEquals(index.document(index.maxDoc() - 1).get("NAME"), "UPDATED TEST USER");

        index.close();
    }

    @Test
    public void testDrop() throws Exception {
        OutputWriter output = new OutputWriter("src/test/resources/OutputWriter/testDrop");

        Document doc = new Document();
        doc.add(new StringField("ID", "1", Field.Store.YES));
        doc.add(new StringField("NAME", "TEST USER", Field.Store.YES));

        output.insert(doc);
        output.close();

        FSDirectory directory = FSDirectory.open(new File("src/test/resources/OutputWriter/testDrop"));
        DirectoryReader index = DirectoryReader.open(directory);

        assertEquals(index.numDocs(), 1);
        assertEquals(index.document(index.maxDoc() - 1).get("ID"), "1");
        assertEquals(index.document(index.maxDoc() - 1).get("NAME"), "TEST USER");

        index.close();

        output = new OutputWriter("src/test/resources/OutputWriter/testDrop");
        output.drop();
        output.close();

        index = DirectoryReader.open(directory);
        assertEquals(index.numDocs(), 0);

        index.close();
    }

    @Test(expected = org.apache.lucene.store.AlreadyClosedException.class)
    public void testClose() throws Exception {
        OutputWriter output = new OutputWriter("src/test/resources/OutputWriter/testClose");

        Document doc = new Document();
        doc.add(new StringField("ID", "1", Field.Store.YES));
        doc.add(new StringField("NAME", "TEST USER", Field.Store.YES));

        output.insert(doc);
        output.close();

        output.insert(doc);
    }
}