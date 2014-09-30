package org.indexer.search;

import java.io.IOException;

class InputReader {
    InputProvider provider;

    public InputReader(String indexDirPath) throws IOException {
        this.provider = Components.Lucene.getInputProvider(indexDirPath);
    }
}
