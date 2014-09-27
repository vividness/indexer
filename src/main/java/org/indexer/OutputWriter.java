package org.indexer;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import java.io.IOException;

public class OutputWriter {
    private IndexWriter provider;

    public OutputWriter(String outputDirPath) throws IOException {
        this.provider = Components.Lucene.getIndexWriter(outputDirPath);
    }

    public OutputWriter(String outputDirPath, Indexer.OpenMode mode) throws IOException {
        this.provider = Components.Lucene.getIndexWriter(outputDirPath, mode);
    }

    public void insert(Document doc) throws IOException {
        this.provider.addDocument(doc);
    }

    public void update(Document doc) throws IOException {
        this.provider.updateDocument(new Term("id", doc.get("id")), doc);
    }

    public void delete() throws IOException {
        this.provider.deleteAll();
        this.provider.commit();
    }

    public void close() throws IOException {
        this.provider.close();
    }
}