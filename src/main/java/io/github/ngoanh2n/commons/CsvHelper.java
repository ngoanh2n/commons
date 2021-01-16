package io.github.ngoanh2n.commons;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Utilities for CSV processing: reading, writing
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
public class CsvHelper {

    public static List<String[]> read(File csv) {
        return read(csv, defaultCsvParserSettings());
    }

    public static List<String[]> read(File csv, Charset encoding) {
        return read(csv, encoding, defaultCsvParserSettings());
    }

    public static List<String[]> read(File csv, CsvParserSettings settings) {
        return read(csv, Charset.defaultCharset(), settings);
    }

    public static List<String[]> read(File csv, Charset encoding, CsvParserSettings settings) {
        checkNotNull(csv, "csv cannot be null");
        checkNotNull(encoding, "encoding cannot be null");
        checkNotNull(settings, "settings cannot be null");
        return new CsvParser(settings).parseAll(csv, encoding);
    }

    public static CsvParserSettings defaultCsvParserSettings() {
        return new CsvParserSettings();
    }

    public static File write(List<String[]> rows, File toCsv) {
        return write(rows, toCsv, Charset.defaultCharset());
    }

    public static File write(List<String[]> rows, File toCsv, Charset encoding) {
        return write(rows, toCsv, encoding, defaultCsvWriterSettings());
    }

    public static File write(List<String[]> rows, File toCsv, CsvWriterSettings settings) {
        return write(rows, toCsv, Charset.defaultCharset(), settings);
    }

    public static File write(List<String[]> rows, File toCsv, Charset encoding, CsvWriterSettings settings) {
        checkNotNull(rows, "rows cannot be null");
        checkArgument(rows.size() > 0, "rows cannot be empty");
        checkNotNull(toCsv, "toCsv cannot be null");
        checkNotNull(encoding, "encoding cannot be null");
        checkNotNull(settings, "settings cannot be null");

        CsvWriter writer = new CsvWriter(toCsv, encoding, settings);
        for (String[] row : rows) writer.writeRow(row);
        writer.close();
        return toCsv;
    }

    public static CsvWriterSettings defaultCsvWriterSettings() {
        return new CsvWriterSettings();
    }
}
