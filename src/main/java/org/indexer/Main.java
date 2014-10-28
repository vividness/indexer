package org.indexer;

/**
 * Indexer, version 1.0
 * 
 * Indexes and searches the contents of a CSV file.
 * The first column in the input file is the ID field for each indexed document. 
 * 
 * Usage:
 * - indexer create [ index/dir ] from [ input.csv ]
 * - indexer append [ index/dir ] from [ input.csv ]
 * - indexer update [ index/dir ] from [ input.csv ]
 * - indexer drop [ index/dir ]
 * - indexer find [ all | n ] return [ "field1,field2 ..." ] from [ index/dir ] where [ "criteria [[AND | OR] criteria]"]
 */
public class Main {
    public static void main(String[] args) {
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
