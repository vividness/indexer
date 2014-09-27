package org.indexer;

import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.document.*;
import java.util.Iterator;

public class InputReader implements Iterator<Document> {
    private InputProvider provider;
    private Document      document;
    private Field         fields[];

    public InputReader(InputProvider input) {
        this.provider = input;
        this.document = Components.Lucene.getDocument();
        this.fields   = Components.Lucene.getDocumentFields(provider.getFields().size());

        this.initDocumentFields();
    }

    private void initDocumentFields() {
        Object[] inputFieldNames = this.provider.getFields().keySet().toArray();
        Object[] inputFieldTypes = this.provider.getFields().values().toArray();

        for (int i = 0; i < this.fields.length; i++) {
            String fieldName = (String) inputFieldNames[i];
            String fieldType = (String) inputFieldTypes[i];

            if (fieldType.equals("integer")) {
                if (fieldName.equals("id")) {
                    fields[i] = new IntField(fieldName, 0, Field.Store.YES);
                } else {
                    fields[i] = new IntField(fieldName, 0, Field.Store.NO);
                }
            } else if (fieldType.equals("float")) {
                fields[i] = new FloatField(fieldName, 0F, Field.Store.NO);
            } else {
                fields[i] = new StringField(fieldName, "", Field.Store.NO);
            }

            this.document.add(fields[i]);
        }
    }

    @Override
    public Document next() {
        CSVRecord row = this.provider.next();

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getClass() == IntField.class) {
                fields[i].setIntValue(Integer.valueOf(row.get(i)));
            } else if (fields[i].getClass() == FloatField.class) {
                fields[i].setFloatValue(Float.valueOf(row.get(i)));
            } else {
                fields[i].setStringValue(row.get(i));
            }
        }

        return this.document;
    }

    @Override
    public boolean hasNext() {
        return this.provider.hasNext();
    }

    @Override
    public void remove() {}
}
