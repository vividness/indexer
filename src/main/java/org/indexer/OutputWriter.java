package org.indexer;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import java.io.IOException;

public class OutputWriter {
    private IndexWriter provider;

    public OutputWriter(String outputPath) throws IOException {
        this.provider = Components.getIndexWriter(outputPath);
    }

    public void write(Document doc) throws IOException {
        this.provider.addDocument(doc);
    }

    public void close() throws IOException {
        this.provider.close();
    }
}
