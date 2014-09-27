package org.indexer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Components {
    public static CSVParser getCsvParser(String path) throws IOException {
        return CSVParser.parse(new File(path), Charset.defaultCharset(), CSVFormat.RFC4180.withHeader());
    }

    public static Document getDocument() {
        return new Document();
    }

    public static Field[] getDocumentFields(int n) {
        return new Field[n];
    }

    public static IndexWriter getIndexWriter(String outputPath) throws IOException  {
        Directory outputDir      = FSDirectory.open(new File(outputPath));
        Analyzer analyzer        = new StandardAnalyzer(Version.LUCENE_4_10_0);
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_0, analyzer).setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        return new IndexWriter(outputDir, config);
    }

    public static OutputWriter getOutputWriter(String outputPath) {
        return new OutputWriter(outputPath);
    }

    public static InputReader getInputReader(String filePath) throws IOException  {
        InputProvider provider = new InputProvider(filePath);

        return new InputReader(provider);
    }
}
