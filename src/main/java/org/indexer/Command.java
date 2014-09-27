package org.indexer;

import java.io.IOException;

public class Command {
    public static void create(String inputFilePath, String outputDirPath) throws IOException {
        Long startTime = System.currentTimeMillis();
        Indexer index  = new Indexer(inputFilePath, outputDirPath);

        System.out.print("Indexing " + inputFilePath + " into " + outputDirPath + " ... ");
        index.create();

        System.out.format("Finished in %.2f seconds.\n", (float) (System.currentTimeMillis() - startTime) / 1000);
    }

    public static void update(String inputFilePath, String outputDirPath) throws IOException {
        new Indexer(inputFilePath, outputDirPath).create();
    }

    public static void drop(String outputDirPath) throws IOException {
        Indexer.drop(outputDirPath);
    }

    public static void select(int limit) {

    }

    public static void select(int offset, int limit) {

    }

    public static void printUsage() {
        System.out.println("USAGE: For now read the source :)");
    }
}
