package org.indexer.search;

import java.io.IOException;

abstract class Components {
    public static class Lucene {
        public static InputProvider getInputProvider(String indexDirPath) throws IOException {
            return new InputProvider(indexDirPath);
        }
    }

    public static InputReader getInputReader(String indexDirPath) throws IOException {
        return new InputReader(indexDirPath);
    }
}
