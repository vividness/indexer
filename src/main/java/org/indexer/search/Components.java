package org.indexer.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

abstract class Components {
    /**
     * Opens an existing index and returns the instance of InputReader.
     *
     * @param indexDirPath Path to the dir where your index is stored.
     * @return Input reader instance which implements methods for querying the index.
     * @throws IOException
     */
    public static InputReader getInputReader(String indexDirPath) throws IOException {
        return new InputReader(indexDirPath);
    }

    /**
     * Returns Lucene's QueryParser instance.
     *
     * @return Query parser instance used in parsing lucene queries.
     */
    public static QueryParser getQueryParser() {
        return new QueryParser(null, new StandardAnalyzer());
    }

    /**
     * Returns and instance of Lucene's index searcher.
     *
     * @param indexDirPath Path to the dir where your index is stored.
     * @return Index searcher instance.
     * @throws IOException
     */
    public static IndexSearcher getIndexSearcher(String indexDirPath) throws IOException {
        FSDirectory directory  = FSDirectory.open(new File(indexDirPath));
        DirectoryReader reader = DirectoryReader.open(directory);

        return new IndexSearcher(reader);
    }
}
