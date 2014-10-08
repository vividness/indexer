package org.indexer.indexing;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import java.io.IOException;

final class OutputWriter {
    private IndexWriter output;

    public OutputWriter(String outputDirPath) throws IOException {
        this.output = Components.Lucene.getIndexWriter(outputDirPath);
    }

    public OutputWriter(String outputDirPath, Indexer.OpenMode mode) throws IOException {
        this.output = Components.Lucene.getIndexWriter(outputDirPath, mode);
    }

    public void insert(Document doc) throws IOException {
        this.output.addDocument(doc);
    }

    // todo: make possible to specify different unique identifier
    public void update(Document doc) throws IOException {
        this.output.updateDocument(new Term("id", doc.get("id")), doc);
    }

    public void delete() throws IOException {
        this.output.deleteAll();
        this.output.commit();
    }

    public void close() throws IOException {
        this.output.close();
    }
}