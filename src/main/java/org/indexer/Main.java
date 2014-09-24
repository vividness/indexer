package org.indexer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Indexer indexer = new Indexer("./files/input/users.csv");

        long start_time = System.currentTimeMillis();
        System.out.println("Creating index");

        indexer.run();

        long finish_time = System.currentTimeMillis();
        System.out.format("Done! Finished in %d seconds.\n", (finish_time - start_time) / 1000);
    }
}