package org.indexer.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Searcher {
    private InputReader input;

    public Searcher(String indexDirPath) throws IOException {
        this.input = Components.getInputReader(indexDirPath);
    }

    public ArrayList<LinkedHashMap<String, String>> find() {
        return new ArrayList<LinkedHashMap<String, String>>();
    }
}