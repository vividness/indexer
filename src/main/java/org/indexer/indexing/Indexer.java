package org.indexer.indexing;

import java.io.IOException;

public class Indexer {
    public enum OpenMode { CREATE, APPEND, CREATE_OR_APPEND }

    private InputReader input;
    private OutputWriter output;

    public Indexer(String inputFilePath, String outputDirPath) throws IOException {
        this.input  = Components.getInputReader(inputFilePath);
        this.output = Components.getOutputWriter(outputDirPath);
    }

    public Indexer(String inputFilePath, String outputDirPath, OpenMode mode) throws IOException {
        this.input  = Components.getInputReader(inputFilePath);
        this.output = Components.getOutputWriter(outputDirPath, mode);
    }

    public void insert() throws IOException {
        while (this.input.hasNext()) {
            this.output.insert(this.input.next());
        }

        this.output.close();
    }

    public void update() throws IOException {
        while (this.input.hasNext()) {
            this.output.update(this.input.next());
        }

        this.output.close();
    }

    public static void drop(String outputDirPath) throws IOException {
        OutputWriter output = Components.getOutputWriter(outputDirPath);

        output.delete();
        output.close();
    }
}
