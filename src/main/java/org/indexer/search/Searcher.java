package org.indexer.search;

import java.io.IOException;
import java.util.ArrayList;

public class Searcher {
    private InputReader input;

    public Searcher(String indexDirPath) throws IOException {
        this.input = Components.getInputReader(indexDirPath);
    }

    public ArrayList<String> find(String queryString) throws IOException {
        ArrayList<String> result = new ArrayList<String>();

        this.input.query(queryString);

        while (this.input.hasNext()) {
            result.add(this.input.next());
        }

        return result;
    }
}