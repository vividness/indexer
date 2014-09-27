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
    public static class CSV {
        public static CSVParser getCsvParser(String inputFilePath) throws IOException {
            return CSVParser.parse(new File(inputFilePath), Charset.defaultCharset(), CSVFormat.RFC4180.withHeader());
        }
    }

    public static class Lucene {
        public static Document getDocument() {
            return new Document();
        }

        public static Field[] getDocumentFields(int n) {
            return new Field[n];
        }

        public static IndexWriter getIndexWriter(String outputDirPath) throws IOException {
            Directory outputDir = FSDirectory.open(new File(outputDirPath));
            Analyzer analyzer   = new StandardAnalyzer(Version.LUCENE_4_10_0);
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_0, analyzer).setOpenMode(IndexWriterConfig.OpenMode.CREATE);

            return new IndexWriter(outputDir, config);
        }
    }

    public static OutputWriter getOutputWriter(String outputDirPath) throws IOException {
        return new OutputWriter(outputDirPath);
    }

    public static InputReader getInputReader(String inputFilePath) throws IOException {
        InputProvider provider = new InputProvider(inputFilePath);

        return new InputReader(provider);
    }
}
