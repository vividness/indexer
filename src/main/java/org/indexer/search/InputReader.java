package org.indexer.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

class InputReader implements Iterator<String> {
    private InputProvider provider;
    private ArrayList<String> result;

    public InputReader(String indexDirPath) throws IOException {
        this.provider = Components.Lucene.getInputProvider(indexDirPath);
    }

    public void query(String queryString) throws IOException {
        this.result = this.provider.query(queryString);
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public String next() {
        return this.result.toString();
    }

    @Override
    public void remove() {}
}
