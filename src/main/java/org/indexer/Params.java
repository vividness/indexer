package org.indexer;

import java.util.LinkedHashMap;

public class Params {
    public enum Cmd { CREATE, DROP, UPDATE, APPEND, FIND }

    public final String[] args;
    public final Cmd cmd;
    public final LinkedHashMap<String, String> cmdParams;

    public Params(String[] args) {
        this.args      = args;
        this.cmd       = parseCommand();
        this.cmdParams = parseCommandParams();
    }

    public Cmd parseCommand() {
        if (args[0].toLowerCase().equals("create")) {
            return Cmd.CREATE;
        } else if (args[0].toLowerCase().equals("append")) {
            return Cmd.APPEND;
        } else if (args[0].toLowerCase().equals("update")) {
            return Cmd.UPDATE;
        } else if (args[0].toLowerCase().equals("drop")) {
            return Cmd.DROP;
        } else if (args[0].toLowerCase().equals("find")) {
            return Cmd.FIND;
        }

        return cmd;
    }

    public LinkedHashMap<String, String> parseCommandParams() {
        LinkedHashMap<String, String> cmdParams = new LinkedHashMap<String, String>();

        switch (this.cmd) {
            case CREATE:
            case APPEND:
            case UPDATE:
                cmdParams.put("indexDirPath", this.args[1]);
                cmdParams.put("inputFilePath", this.args[3]);

                break;
            case DROP:
                cmdParams.put("indexDirPath", this.args[1]);

                break;
            case FIND:
                cmdParams.put("quantity", this.args[1]);
                cmdParams.put("fields", this.args[3]);
                cmdParams.put("indexDirPath", this.args[5]);
                cmdParams.put("criteria", this.args[7]);

                break;
            default:
        }

        return cmdParams;
    }
}