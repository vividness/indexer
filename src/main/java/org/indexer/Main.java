package org.indexer;

import org.apache.lucene.queryparser.classic.ParseException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
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

            if (args[1].equals("all")) {
                Command.find(args[5], args[3].split(","), args[7]);
            } else {
                Command.find(args[5], args[3].split(","), args[7], Integer.valueOf(args[1]));
            }
        }

        System.exit(0);
    }
}