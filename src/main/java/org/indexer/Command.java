package org.indexer;

import org.apache.lucene.queryparser.classic.ParseException;
import org.indexer.indexing.Indexer;
import org.indexer.search.Searcher;

import java.io.IOException;

abstract class Command {
    public static void create(String inputFilePath, String outputDirPath) throws IOException {
        Long startTime = System.currentTimeMillis();
        Indexer index  = new Indexer(inputFilePath, outputDirPath);

        System.out.print("Creating index " + outputDirPath + " from " + inputFilePath + " ... ");
        index.insert();

        System.out.format("Finished in %.2f seconds.\n", (float) (System.currentTimeMillis() - startTime) / 1000);
    }

    public static void append(String inputFilePath, String outputDirPath) throws IOException {
        Long startTime = System.currentTimeMillis();
        Indexer index  = new Indexer(inputFilePath, outputDirPath, Indexer.OpenMode.APPEND);

        System.out.print("Appending index " + outputDirPath + " with " + inputFilePath + " ... ");
        index.insert();

        System.out.format("Finished in %.2f seconds.\n", (float) (System.currentTimeMillis() - startTime) / 1000);
    }

    public static void drop(String outputDirPath) throws IOException {
        Long startTime = System.currentTimeMillis();
        System.out.print("Dropping index " + outputDirPath + " ... ");

        Indexer.drop(outputDirPath);
        System.out.format("Finished in %.2f seconds.\n", (float) (System.currentTimeMillis() - startTime) / 1000);
    }

    public static void find(String indexDirPath, String[] fields, String queryString) throws IOException, ParseException {
        Searcher searcher = new Searcher(indexDirPath);

        System.out.println(searcher.find(fields, queryString).toString());
    }

    public static void find(String indexDirPath, String[] fields, String queryString, int limit) throws IOException, ParseException {
        Searcher searcher = new Searcher(indexDirPath);

        System.out.println(searcher.find(fields, queryString, limit).toString());
    }

    /**
     * WARNING! This one appears broken. It doesn't remove documents
     * and maybe it shouldn't because of the lib performance.
     * Double check updates once the search components is built.
     */
    public static void update(String inputFilePath, String outputDirPath) throws IOException {
        Long startTime = System.currentTimeMillis();
        Indexer index  = new Indexer(inputFilePath, outputDirPath, Indexer.OpenMode.CREATE_OR_APPEND);

        System.out.print("Updating index " + outputDirPath + " with " + inputFilePath + " ... ");
        index.update();

        System.out.format("Finished in %.2f seconds.\n", (float) (System.currentTimeMillis() - startTime) / 1000);
    }

    public static void printUsage() {
        System.out.println("Indexer, version 0.0\n");

        System.out.println("Indexes all columns of the input CSV file.");
        System.out.println("- All the fields in the file must be named in the following fashion [field_name]:[field_type] (e.g. id:integer,email:string).");
        System.out.println("- The CSV file must have the first field named as id:integer.");

        System.out.println("\nUsage:\n");

        System.out.println("- indexer create index [ index/dir ] from [ input.csv ]");
        System.out.println("- indexer update index [ index/dir ] from [ input.csv ]");
        System.out.println("- indexer drop index [ index/dir ]");
        System.out.println("- indexer find [ all | n ] return [ field1,field2 ... ] from [ index/dir ] where [ criteria ]");
    }

    public static void printUsageAndExit(int exitCode) {
        printUsage();
        System.exit(exitCode);
    }
}