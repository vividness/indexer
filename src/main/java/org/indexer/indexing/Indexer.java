package org.indexer.indexing;

import java.io.IOException;

public class Indexer {
    /**
     * Open modes that the Indexer class supports
     */
    public enum OpenMode { CREATE, APPEND, CREATE_OR_APPEND }

    /**
     * This instance reads the input file. Use Components to create a new instance.
     */
    private final InputReader input;

    /**
     * This instance creates the index. Use Components to create a new instance.
     */
    private final OutputWriter output;

    /**
     * Initializes the input, creates an output instance in CREATE open mode.
     *
     * @param inputFilePath Path to the file that is going to be indexed.
     * @param outputDirPath Path to the directory where the index will be stored.
     * @throws IOException
     */
    public Indexer(String inputFilePath, String outputDirPath) throws IOException {
        this.input  = Components.getInputReader(inputFilePath);
        this.output = Components.getOutputWriter(outputDirPath);
    }

    /**
     * Initializes the input, creates an output instance in specified open mode.
     *
     * @param inputFilePath Path to the file that is going to be indexed.
     * @param outputDirPath Path to the directory where the index will be stored.
     * @param mode CREATE, APPEND or CREATE_OR_APPEND.
     * @throws IOException
     */
    public Indexer(String inputFilePath, String outputDirPath, OpenMode mode) throws IOException {
        this.input  = Components.getInputReader(inputFilePath);
        this.output = Components.getOutputWriter(outputDirPath, mode);
    }

    /**
     * Takes a next entry from the input file and adds the entry to the index.
     *
     * @throws IOException
     */
    public void insert() throws IOException {
        while (this.input.hasNext()) {
            this.output.insert(this.input.next());
        }

        this.output.close();
    }

    /**
     * Takes a next entry from the input file and tries to update an existing document in the index.
     * If an existing document cannot be found for update, a new document will be added to the index.
     *
     * @throws IOException
     */
    public void update() throws IOException {
        while (this.input.hasNext()) {
            this.output.update(this.input.next());
        }

        this.output.close();
    }

    /**
     * Removes the contents of an index.
     *
     * @param outputDirPath Path to the directory where the index will be stored.
     * @throws IOException
     */
    public static void drop(String outputDirPath) throws IOException {
        OutputWriter output = Components.getOutputWriter(outputDirPath);

        output.drop();
        output.close();
    }

    /**
     * Closes the input and the output resources.
     *
     * @throws IOException
     */
    public void close() throws IOException {
        this.input.close();
        this.output.close();
    }
}
