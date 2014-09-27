package org.indexer;

import java.io.IOException;

public class Indexer {
    private InputReader input;
    private OutputWriter output;

    public Indexer(String inputFilePath, String outputDirPath) throws IOException {
        this.input  = Components.getInputReader(inputFilePath);
        this.output = Components.getOutputWriter(outputDirPath);
    }

    public void run() throws IOException {
        while (this.input.hasNext()) {
            this.output.write(this.input.next());
        }

        this.output.close();
    }
}
