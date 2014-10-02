package org.indexer.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class InputProvider {
    private IndexSearcher searcher;
    private QueryParser parser;

    public InputProvider(String indexDirPath) throws IOException {
        FSDirectory directory  = FSDirectory.open(new File(indexDirPath)); //todo: move to Components
        DirectoryReader reader = DirectoryReader.open(directory); //todo: move to Components

        this.searcher = new IndexSearcher(reader); //todo: move to Components.Lucene
        this.parser   = new QueryParser("id", new StandardAnalyzer()); //todo: Move to Components.Lucene
    }

    public ArrayList<String> query(String queryString) throws IOException {
        Query query;
        ArrayList<String> results = new ArrayList<String>();

        try {
            query = this.parser.parse(queryString);
        } catch (ParseException error) {
            return null;
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

        return results;
    }
}
