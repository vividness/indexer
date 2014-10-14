package org.indexer.search;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

class InputReader {
    /**
     * Query parser instance. Use Components to create a new instance.
     */
    private final QueryParser parser;

    /**
     * Index searcher instance. Use Components to create a new instance.
     */
    private final IndexSearcher searcher;

    /**
     * ArrayList of LinkedHashMap results.
     */
    private ArrayList<LinkedHashMap<String, String>> results = new ArrayList<LinkedHashMap<String, String>>();

    /**
     * Creates a new instance and initializes the parser and searcher.
     *
     * @param indexDirPath Path to the dir where your index is stored.
     * @throws IOException
     */
    public InputReader(String indexDirPath) throws IOException {
        this.parser   = Components.getQueryParser();
        this.searcher = Components.getIndexSearcher(indexDirPath);
    }

    /**
     * Queries the index and returns the result.
     *
     * @param fields Fields to return e.g. id, email, username etc.
     * @param queryString Lucene query string.
     * @return List of results.
     * @throws IOException
     * @throws ParseException
     */
    public ArrayList<LinkedHashMap<String, String>> query(String[] fields, String queryString) throws IOException, ParseException {
        Query query = this.parseQuery(queryString);
        int maxHits = this.findTotalHits(query);

        return this.query(fields, queryString, maxHits);
    }

    /**
     * Queries the index and returns the result.
     *
     * @param fields Fields to return e.g. id, email, username etc.
     * @param queryString Lucene query string.
     * @param limit Maximum number of results to return.
     * @return List of results.
     * @throws IOException
     * @throws ParseException
     */
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

    /**
     * Finds the number of documents that match the search criteria.
     *
     * @param query Search criteria, i.e. query string.
     * @return Number of all matching documents.
     * @throws IOException
     */
    private int findTotalHits(Query query) throws IOException {
        TotalHitCountCollector hitsCollector = new TotalHitCountCollector();
        this.searcher.search(query, hitsCollector);

        return Math.max(1, hitsCollector.getTotalHits());
    }

    /**
     * Finds all documents that match the search criteria.
     *
     * @param query Search criteria, i.e. query string.
     * @param maxHits Maximum number of documents that will be returned i.e. limit.
     * @return An array of Lucene's ScoreDoc elements.
     * @throws IOException
     */
    private ScoreDoc[] findMatchingDocuments(Query query, int maxHits) throws IOException {
        TopScoreDocCollector docsCollector = TopScoreDocCollector.create(maxHits, true);
        this.searcher.search(query, docsCollector);

        return docsCollector.topDocs().scoreDocs;
    }

    /**
     * Returns a Query object that we will use to search the index.
     *
     * @param queryString Search criteria, i.e. query string.
     * @return An instance of the Query class.
     * @throws ParseException
     */
    private Query parseQuery(String queryString) throws ParseException {
        return this.parser.parse(queryString);
    }
}