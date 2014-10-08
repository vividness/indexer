package org.indexer.indexing;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import java.io.IOException;

final class OutputWriter {
    private IndexWriter  output;
    private final String ID_FIELD;

    public OutputWriter(String outputDirPath) throws IOException {
        this.output   = Components.Lucene.getIndexWriter(outputDirPath);
        this.ID_FIELD = "id";
    }

    public OutputWriter(String outputDirPath, Indexer.OpenMode mode) throws IOException {
        this.output   = Components.Lucene.getIndexWriter(outputDirPath, mode);
        this.ID_FIELD = "id";
    }

    public OutputWriter(String outputDirPath, Indexer.OpenMode mode, String idField) throws IOException {
        this.output   = Components.Lucene.getIndexWriter(outputDirPath, mode);
        this.ID_FIELD = idField;
    }

    public OutputWriter(String outputDirPath, String idField) throws IOException {
        this.output   = Components.Lucene.getIndexWriter(outputDirPath);
        this.ID_FIELD = idField;
    }

    public void insert(Document doc) throws IOException {
        this.output.addDocument(doc);
    }

    public void update(Document doc) throws IOException {
        this.output.updateDocument(new Term(this.ID_FIELD, doc.get(this.ID_FIELD)), doc);
    }

    public void delete() throws IOException {
        this.output.deleteAll();
        this.output.commit();
    }

    public void close() throws IOException {
        this.output.close();
    }
}