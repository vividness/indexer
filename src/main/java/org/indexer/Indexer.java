package org.indexer;

import org.apache.lucene.index.IndexWriter;
import java.io.IOException;

public class Indexer {
    private InputReader input;
    private IndexWriter output;

    public Indexer(String inputFilePath, String outputDirPath) throws IOException {
        this.output = Factory.getOutputWriter(outputDirPath);
        this.input  = Factory.getInputReader(inputFilePath);
    }

    public void run() throws IOException {
        while (this.input.hasNext()) {
            this.output.addDocument(this.input.next());
        }

        this.output.close();
    }
}
