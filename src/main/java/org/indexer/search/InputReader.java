package org.indexer.search;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

class InputReader {
    private QueryParser   parser;
    private IndexSearcher searcher;
    private ArrayList<LinkedHashMap<String, String>> results = new ArrayList<LinkedHashMap<String, String>>();

    public InputReader(String indexDirPath) throws IOException {
        this.parser   = Components.getQueryParser();
        this.searcher = Components.getIndexSearcher(indexDirPath);
    }

    public ArrayList<LinkedHashMap<String, String>> query(String[] fields, String queryString) throws IOException, ParseException {
        Query query = this.parseQuery(queryString);
        int maxHits = this.findTotalHits(query);

        return this.query(fields, queryString, maxHits);
    }

    public ArrayList<LinkedHashMap<String, String>> query(String[] fields, String queryString, int limit) throws IOException, ParseException {
        Query query = this.parseQuery(queryString);

        for (ScoreDoc result : this.findMatchingDocuments(query, limit)) {
            LinkedHashMap<String, String> document = new LinkedHashMap<String, String>();

            for (String field : fields) {
                document.put(field, this.searcher.doc(result.doc).get(field));
            }

            results.add(document);
        }

        return results;
    }

    private int findTotalHits(Query query) throws IOException {
        TotalHitCountCollector hitsCollector = new TotalHitCountCollector(); //todo move to components
        this.searcher.search(query, hitsCollector);

        return Math.max(1, hitsCollector.getTotalHits());
    }

    private ScoreDoc[] findMatchingDocuments(Query query, int maxHits) throws IOException {
        TopScoreDocCollector docsCollector = TopScoreDocCollector.create(maxHits, true); //todo move to components
        this.searcher.search(query, docsCollector);

        return docsCollector.topDocs().scoreDocs;
    }

    private Query parseQuery(String queryString) throws ParseException {
        return this.parser.parse(queryString);
    }
}