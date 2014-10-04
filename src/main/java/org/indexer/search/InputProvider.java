package org.indexer.search;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

class InputProvider {
    private DirectoryReader reader;

    public InputProvider(String indexDirPath) throws IOException {
        FSDirectory directory  = FSDirectory.open(new File(indexDirPath)); //todo: move to Components
        this.reader = DirectoryReader.open(directory); //todo: move to Components
    }

    public DirectoryReader getReader() {
        return this.reader;
    }
}
