package org.indexer;

import java.io.IOException;

public class Command {
    public static void create(String inputFilePath, String outputDirPath) throws IOException {
        Long startTime = System.currentTimeMillis();
        Indexer index  = new Indexer(inputFilePath, outputDirPath);

        System.out.print("Creating index " + outputDirPath + " from " + inputFilePath + " ... ");
        index.create();

        System.out.format("Finished in %.2f seconds.\n", (float) (System.currentTimeMillis() - startTime) / 1000);
    }

    public static void append(String inputFilePath, String outputDirPath) throws IOException {
        Long startTime = System.currentTimeMillis();
        Indexer index  = new Indexer(inputFilePath, outputDirPath, Indexer.OpenMode.APPEND);

        System.out.print("Updating index " + outputDirPath + " with " + inputFilePath + " ... ");
        index.update();

        System.out.format("Finished in %.2f seconds.\n", (float) (System.currentTimeMillis() - startTime) / 1000);
    }

    public static void drop(String outputDirPath) throws IOException {
        Long startTime = System.currentTimeMillis();
        System.out.print("Dropping index " + outputDirPath + " ... ");

        Indexer.drop(outputDirPath);
        System.out.format("Finished in %.2f seconds.\n", (float) (System.currentTimeMillis() - startTime) / 1000);
    }

    public static void select(int limit) {

    }

    public static void printUsage() {
        System.out.println("Indexer, version 0.0\n");

        System.out.println("Indexes all columns of the input CSV file.");
        System.out.println("- All the fields in the file must be named in the following fashion [field_name]:[field_type] (e.g. id:integer,email:string).");
        System.out.println("- The CSV file must have the first field named as id:integer.");

        System.out.println("\nUsage:\n");

        System.out.println("- indexer create [ input.csv ] [ index/dir ]");
        System.out.println("- indexer update [ input.csv ] [ index/dir ]");
        System.out.println("- indexer drop [ index/dir ]");
        System.out.println("- indexer select [ all|first n|last n ] from [ index/dir ] where [ criteria ]");
    }
}