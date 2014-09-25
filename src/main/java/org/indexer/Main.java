package org.indexer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Indexer indexer = new Indexer("./files/input/users.csv", "./files/output");

        long startTime = System.currentTimeMillis();
        System.out.println("Creating index");

        indexer.run();

        long finishTime = System.currentTimeMillis();
        System.out.format("Done! Finished in %d seconds.\n", (finishTime - startTime) / 1000);
    }
}