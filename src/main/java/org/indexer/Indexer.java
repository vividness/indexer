package org.indexer;

import java.io.IOException;

public class Indexer {
    private InputReader input;
    private OutputWriter output;

    public Indexer(String inputFilePath, String outputDirPath) throws IOException {
        this.input  = Components.getInputReader(inputFilePath);
        this.output = Components.getOutputWriter(outputDirPath);
    }

    public void index() throws IOException {
        while (this.input.hasNext()) {
            this.output.write(this.input.next());
        }

        this.output.close();
    }

    public void create() throws IOException {
        index();
    }

    public void update() throws IOException {
        index();
    }

    public static void drop(String outputDirPath) throws IOException {
        OutputWriter output = Components.getOutputWriter(outputDirPath);

        output.drop();
        output.close();
    }
}
