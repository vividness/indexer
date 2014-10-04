package org.indexer.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

abstract class Components {
    public static InputReader getInputReader(String indexDirPath) throws IOException {
        return new InputReader(indexDirPath);
    }

    public static QueryParser getQueryParser() {
        return new QueryParser(null, new StandardAnalyzer());
    }

    public static IndexSearcher getIndexSearcher(String indexDirPath) throws IOException {
        FSDirectory directory  = FSDirectory.open(new File(indexDirPath));
        DirectoryReader reader = DirectoryReader.open(directory);

        return new IndexSearcher(reader);
    }
}
