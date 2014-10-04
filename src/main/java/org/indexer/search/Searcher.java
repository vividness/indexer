package org.indexer.search;

import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Searcher {
    private InputReader input;

    public Searcher(String indexDirPath) throws IOException {
        this.input = Components.getInputReader(indexDirPath);
    }

    public ArrayList<LinkedHashMap<String, String>> find(String[] fields, String queryString) throws IOException, ParseException {
        return this.input.query(fields, queryString);
    }

    public ArrayList<LinkedHashMap<String, String>> find(String[] fields, String queryString, int limit) throws IOException, ParseException {
        return this.input.query(fields, queryString, limit);
    }
}