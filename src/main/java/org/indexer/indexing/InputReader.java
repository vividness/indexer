package org.indexer.indexing;

import org.apache.commons.csv.CSVParser;
import org.apache.lucene.document.*;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

final class InputReader implements Iterator<Document> {
    private CSVParser input;
    private Document  document;
    private Field     fields[];

    private Iterator<CSVRecord> inputIterator;

    public InputReader(String inputFilePath) throws IOException {
        this.input    = Components.CSV.getCsvParser(inputFilePath);
        this.document = Components.Lucene.getEmptyDocument();

        this.inputIterator = input.iterator();

        // todo: this can be one method
        this.fields   = Components.Lucene.getEmptyDocumentFields(this.input.getHeaderMap().size());
        this.initDocumentFields();
    }

    @Override
    public Document next() {
        CSVRecord row = this.inputIterator.next();

        for (int i = 0; i < this.fields.length; i++) {
            if (this.fields[i].getClass() == IntField.class) {
                this.fields[i].setIntValue(Integer.valueOf(row.get(i)));
            } else if (this.fields[i].getClass() == FloatField.class) {
                this.fields[i].setFloatValue(Float.valueOf(row.get(i)));
            } else {
                this.fields[i].setStringValue(row.get(i));
            }
        }

        return this.document;
    }

    @Override
    public boolean hasNext() {
        return this.inputIterator.hasNext();
    }

    @Override
    public void remove() {}

    private void initDocumentFields() {
        String[] inputFieldNames = this.getFieldNames();

        for (int i = 0; i < inputFieldNames.length; i++) {
            this.fields[i] = new StringField(inputFieldNames[i], "", Field.Store.YES);

            this.document.add(this.fields[i]);
        }
    }

    private String[] getFieldNames() {
        Object[] fieldNames = this.input.getHeaderMap().keySet().toArray();

        return Arrays.copyOf(fieldNames, fieldNames.length, String[].class);
    }
}
