package org.indexer.indexing;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import java.io.IOException;

class OutputWriter {
    /**
     * Index writer instance. Use Components to create a new instance.
     */
    private final IndexWriter output;

    /**
     * Opens index for writing with default CREATE mode and "id" as the unique identifier.
     *
     * @param outputDirPath Path to the dir where the index will be created.
     * @throws IOException
     */
    public OutputWriter(String outputDirPath) throws IOException {
        this.output = Components.Lucene.getIndexWriter(outputDirPath);
    }

    /**
     * Opens index for writing with "id" as the unique identifier.
     *
     * @param outputDirPath Path to the dir where the index will be created.
     * @param mode Index open mode.
     * @throws IOException
     */
    public OutputWriter(String outputDirPath, Indexer.OpenMode mode) throws IOException {
        this.output = Components.Lucene.getIndexWriter(outputDirPath, mode);
    }

    /**
     * Inserts a new document into the index.
     *
     * @param doc Document object that to be added to the index.
     * @throws IOException
     */
    public void insert(Document doc) throws IOException {
        this.output.addDocument(doc);
    }

    /**
     * Removes and adds an updated version for the document identified by it's id field
     *
     * @param doc New version of the document to index.
     * @throws IOException
     */
    public void update(Document doc) throws IOException {
        String idField = doc.getFields().get(0).name();
        this.output.updateDocument(new Term(idField, doc.get(idField)), doc);
    }

    /**
     * Removes all entries from an index.
     *
     * @throws IOException
     */
    public void drop() throws IOException {
        this.output.deleteAll();
        this.output.commit();
    }

    /**
     * Closes the index writer.
     *
     * @throws IOException
     */
    public void close() throws IOException {
        this.output.close();
    }
}
