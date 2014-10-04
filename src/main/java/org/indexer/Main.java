package org.indexer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            Command.printUsageAndExit(1);
        }

        if (args[0].toLowerCase().equals("create")) {
            if (args[1].length() == 0 || args[2].length() == 0) {
                Command.printUsageAndExit(1);
            }

            Command.create(args[1], args[2]);
        } else if (args[0].toLowerCase().equals("append")) {
            if (args[1].length() == 0 || args[2].length() == 0) {
                Command.printUsageAndExit(1);
            }

            Command.append(args[1], args[2]);
        } else if (args[0].toLowerCase().equals("update")) {
            if (args[1].length() == 0 || args[2].length() == 0) {
                Command.printUsageAndExit(1);
            }

            Command.update(args[1], args[2]);
        } else if (args[0].toLowerCase().equals("drop")) {
            if (args[1].length() == 0) {
                Command.printUsageAndExit(1);
            }

            Command.drop(args[1]);
        } else if (args[0].toLowerCase().equals("find")) {
            if (args[1].length() == 0) {
                Command.printUsageAndExit(1);
            }

            Command.find(args[4], args[2]);
        }


        System.exit(0);
    }
}