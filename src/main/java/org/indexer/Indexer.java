package org.indexer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

public class Indexer {
    private InputReader input  = null;
    private IndexWriter output = null;

    public Indexer(String filePath) throws IOException {
        Directory outputDir = FSDirectory.open(new File("./files/output"));

        InputProvider provider   = new InputProvider(filePath);
        Analyzer analyzer        = new StandardAnalyzer(Version.LUCENE_4_10_0);
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_0, analyzer).setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        this.output = new IndexWriter(outputDir, config);
        this.input  = new InputReader(provider);
    }

    public void run() throws IOException {
        while (this.input.hasNext()) {
            this.output.addDocument(this.input.next());
        }

        this.output.close();
    }
}
