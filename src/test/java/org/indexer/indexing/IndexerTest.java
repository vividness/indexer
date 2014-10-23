package org.indexer.indexing;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class IndexerTest {
    @Test
    public void testInsert() throws Exception {
        String inputFile = "src/test/resources/IndexerTest/testInsert/input.csv";
        String outputDir = "src/test/resources/IndexerTest/testInsert/output";

        Indexer indexer = new Indexer(inputFile, outputDir);
        indexer.insert();

        FSDirectory directory = FSDirectory.open(new File(outputDir));
        DirectoryReader index = DirectoryReader.open(directory);

        assertEquals(index.numDocs(), 3);

        assertEquals(index.document(0).get("id"), "1");
        assertEquals(index.document(0).get("name"), "TEST USER 1");
        assertEquals(index.document(1).get("id"), "2");
        assertEquals(index.document(1).get("name"), "TEST USER 2");
        assertEquals(index.document(2).get("id"), "3");
        assertEquals(index.document(2).get("name"), "TEST USER 3");

        indexer.close();
        index.close();
    }

    @Test
    public void testUpdate() throws Exception {
        String insertFile = "src/test/resources/IndexerTest/testUpdate/insert_users.csv";
        String updateFile = "src/test/resources/IndexerTest/testUpdate/update_users.csv";
        String outputDir  = "src/test/resources/IndexerTest/testUpdate/output";

        Indexer inserter = new Indexer(insertFile, outputDir);
        inserter.insert();
        inserter.close();

        Indexer updater = new Indexer(updateFile, outputDir);
        updater.update();
        updater.close();

        FSDirectory directory = FSDirectory.open(new File(outputDir));
        DirectoryReader index = DirectoryReader.open(directory);

        assertEquals(index.numDocs(), 3);
        assertEquals(index.document(0).get("id"), "1");
        assertEquals(index.document(0).get("name"), "USER 1 UPDATED");
        assertEquals(index.document(1).get("id"), "2");
        assertEquals(index.document(1).get("name"), "USER 2");
        assertEquals(index.document(2).get("id"), "3");
        assertEquals(index.document(2).get("name"), "USER 3 UPDATED");

        index.close();
    }

    @Test
    public void testDrop() throws Exception {

    }
}