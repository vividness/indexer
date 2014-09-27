package org.indexer;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class InputProvider implements Iterator<CSVRecord> {
    private CSVParser parser;
    private Iterator<CSVRecord> iterator;

    private LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();

    public InputProvider(String inputFilePath) throws IOException {
        this.parser   = Components.CSV.getCsvParser(inputFilePath);
        this.iterator = this.parser.iterator();

        parseHeader();
    }

    private void parseHeader() {
        for (String fieldAndType : this.parser.getHeaderMap().keySet()) {
            String[] definition = fieldAndType.split(":");

            this.fields.put(definition[0], definition[1]);
        }
    }

    public LinkedHashMap<String, String> getFields() {
        return fields;
    }

    @Override
    public CSVRecord next() {
        return this.iterator.next();
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public void remove() {}
}
