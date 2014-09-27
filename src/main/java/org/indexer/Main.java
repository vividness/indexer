package org.indexer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args[0].toLowerCase().equals("create")) {
            Command.create(args[1], args[2]);
        } else if (args[0].toLowerCase().equals("drop")) {
            Command.drop(args[1]);
        } else {
            Command.printUsage();
        }
    }
}