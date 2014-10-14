package org.indexer.indexing;

import org.apache.lucene.document.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

final class InputReader implements Iterator<Document> {
    /**
     * Apache commons csv parser.
     */
    private final CSVParser input;

    /**
     * Lucene's document object.
     * We are reusing single document object for obvious performance reasons.
     */
    private final Document  document;

    /**
     * An array of fields. It's a de facto schema of the index.
     */
    private Field[]   fields;

    /**
     * CSV column names extracted from the csv file header.
     */
    private String[]  fieldNames;

    /**
     * Use iterator to read the contents of the CSV file.
     */
    private Iterator<CSVRecord> iterator;

    /**
     * Initializes CSV file reader, document object and fields.
     *
     * @param inputFilePath Path to the CSV file that is going to be indexed.
     * @throws IOException
     */
    public InputReader(String inputFilePath) throws IOException {
        this.input    = Components.CSV.getCsvParser(inputFilePath);
        this.document = Components.Lucene.getEmptyDocument();
        this.iterator = input.iterator();

        this.initFieldNames();
        this.initDocumentFields();
    }

    /**
     * Gets a CSV record and loads the values in the document fields.
     *
     * @return Document with the fields loaded from the CSV.
     */
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

    /**
     * Checks if the CSV file has more contents to process.
     *
     * @return True if the iterator has next element.
     */
    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    /**
     * Not used by this class.
     */
    @Override
    public void remove() {}

    /**
     * Initializes filed names from the CSV file header.
     */
    private void initFieldNames() {
        Object[] header = this.input.getHeaderMap().keySet().toArray();
        this.fieldNames = Arrays.copyOf(header, header.length, String[].class);
        this.fields     = new Field[this.fieldNames.length];
    }

    /**
     * Initialized the document with empty fields. The field names are the column names in the CSV file.
     */
    private void initDocumentFields() {
        for (int i = 0; i < this.fieldNames.length; i++) {
            this.fields[i] = new StringField(this.fieldNames[i], "", Field.Store.YES);
            this.document.add(this.fields[i]);
        }
    }
}
