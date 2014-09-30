package org.indexer.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

public class InputProvider {
    public static final int DEFAULT_NUM_RESULTS = 10;

    private IndexSearcher searcher;
    private QueryParser parser;

    public InputProvider(String indexDirPath) throws IOException {
        FSDirectory directory  = FSDirectory.open(new File(indexDirPath));
        DirectoryReader reader = DirectoryReader.open(directory);

        this.searcher = new IndexSearcher(reader); //todo: move to Components.Lucene
    }

    public TopDocs query(String field, String queryString) throws IOException, ParseException {
        return query(field, queryString, DEFAULT_NUM_RESULTS);
    }

    public TopDocs query(String field, String queryString, int limit) throws IOException, ParseException {
        this.parser = new QueryParser(field, new StandardAnalyzer());  //todo: Move to Components.Lucene

        return this.searcher.search(this.parser.parse(queryString), limit);
    }
}
