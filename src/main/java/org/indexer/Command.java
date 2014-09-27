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
        System.out.println("Indexer, version 0.0");
        System.out.println("\nUsage:\n");
        System.out.println("- indexer create [input_file] [output_dir]");
        System.out.println("- indexer update [input_file] [index_dir]");
        System.out.println("- indexer drop [index_dir]");
        System.out.println("- indexer select [all|first [n]|last [n]] from [index_dir] where [criteria]");
    }
}
