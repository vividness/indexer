package org.indexer.indexing;

import org.apache.lucene.document.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

final class InputReader implements Iterator<Document> {
    private CSVParser input;
    private Document  document;
    private Field[]   fields;
    private String[]  fieldNames;

    private Iterator<CSVRecord> iterator;

    public InputReader(String inputFilePath) throws IOException {
        this.input    = Components.CSV.getCsvParser(inputFilePath);
        this.document = Components.Lucene.getEmptyDocument();
        this.iterator = input.iterator();

        this.initFieldNames();
        this.initDocumentFields();
    }

    @Override
    public Document next() {
        CSVRecord row = this.iterator.next();

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
        return this.iterator.hasNext();
    }

    @Override
    public void remove() {}

    private void initFieldNames() {
        Object[] header = this.input.getHeaderMap().keySet().toArray();
        this.fieldNames = Arrays.copyOf(header, header.length, String[].class);
        this.fields     = new Field[this.fieldNames.length];
    }

    private void initDocumentFields() {
        for (int i = 0; i < this.fieldNames.length; i++) {
            //todo: 
            // read the first record and try to detect which types are the fields
            // so we don't use the StringField class for all the fields
            // CSVRecord row = this.iterator.next();
            // 
            // support Integer Float String and Text for the beginning.
            this.fields[i] = new StringField(this.fieldNames[i], "", Field.Store.YES);

            this.document.add(this.fields[i]);
        }
    }
}
