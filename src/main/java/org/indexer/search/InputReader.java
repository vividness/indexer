package org.indexer.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

class InputReader implements Iterator<String> {
    private InputProvider provider;
    private Iterator<String> result;
    private QueryParser parser;
    private IndexSearcher searcher;

    public InputReader(String indexDirPath) throws IOException {
        this.provider = Components.Lucene.getInputProvider(indexDirPath);
        this.parser   = new QueryParser(null, new StandardAnalyzer()); //todo: Move to Components.Lucene
        this.searcher = new IndexSearcher(this.provider.getReader()); //todo: move to Components.Lucene
    }

    public void query(String queryString) throws IOException {
        Query query;
        ArrayList<String> results = new ArrayList<String>();

        try {
            query = this.parser.parse(queryString);
        } catch (ParseException error) {
            // todo handle this exception maybe in the command class
            return;
        }

        TotalHitCountCollector hitsCollector = new TotalHitCountCollector();
        this.searcher.search(query, hitsCollector);

        int maxHits = Math.max(1, hitsCollector.getTotalHits());

        TopScoreDocCollector docsCollector = TopScoreDocCollector.create(maxHits, true);
        this.searcher.search(query, docsCollector);

        ScoreDoc[] topDocs = docsCollector.topDocs().scoreDocs;

        for (ScoreDoc hit : topDocs) {
            results.add(this.searcher.doc(hit.doc).get("id"));
        }

        this.result = results.iterator();
    }

    @Override
    public boolean hasNext() {
        return this.result.hasNext();
    }

    @Override
    public String next() {
        return this.result.next();
    }

    @Override
    public void remove() {}
}
//role, iterator over the results