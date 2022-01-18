// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.text.csv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.Reader;
import java.io.BufferedReader;

public class CsvUtilReader
{
    private BufferedReader br;
    private boolean hasNext;
    private char separator;
    private char quotechar;
    private int skipLines;
    private boolean linesSkiped;
    public static final char DEFAULT_SEPARATOR = ',';
    public static final char DEFAULT_QUOTE_CHARACTER = '\"';
    public static final int DEFAULT_SKIP_LINES = 0;
    
    public CsvUtilReader(final Reader reader) {
        this(reader, ',');
    }
    
    public CsvUtilReader(final Reader reader, final char separator) {
        this(reader, separator, '\"');
    }
    
    public CsvUtilReader(final Reader reader, final char separator, final char quotechar) {
        this(reader, separator, quotechar, 0);
    }
    
    public CsvUtilReader(final Reader reader, final char separator, final char quotechar, final int line) {
        this.hasNext = true;
        this.br = new BufferedReader(reader);
        this.separator = separator;
        this.quotechar = quotechar;
        this.skipLines = line;
    }
    
    public List<String[]> readAll() throws IOException {
        final List<String[]> allElements = new ArrayList<String[]>();
        while (this.hasNext) {
            final String[] nextLineAsTokens = this.readNext();
            if (nextLineAsTokens != null) {
                allElements.add(nextLineAsTokens);
            }
        }
        return allElements;
    }
    
    public String[] readNext() throws IOException {
        final String nextLine = this.getNextLine();
        return (String[])(this.hasNext ? this.parseLine(nextLine) : null);
    }
    
    private String getNextLine() throws IOException {
        if (!this.linesSkiped) {
            for (int i = 0; i < this.skipLines; ++i) {
                this.br.readLine();
            }
            this.linesSkiped = true;
        }
        final String nextLine = this.br.readLine();
        if (nextLine == null) {
            this.hasNext = false;
        }
        return this.hasNext ? nextLine : null;
    }
    
    private String[] parseLine(String nextLine) throws IOException {
        if (nextLine == null) {
            return null;
        }
        final List<String> tokensOnThisLine = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        do {
            if (inQuotes) {
                sb.append("\n");
                nextLine = this.getNextLine();
                if (nextLine == null) {
                    break;
                }
            }
            for (int i = 0; i < nextLine.length(); ++i) {
                final char c = nextLine.charAt(i);
                if (c == this.quotechar) {
                    if (inQuotes && nextLine.length() > i + 1 && nextLine.charAt(i + 1) == this.quotechar) {
                        sb.append(nextLine.charAt(i + 1));
                        ++i;
                    }
                    else {
                        inQuotes = !inQuotes;
                        if (i > 2 && nextLine.charAt(i - 1) != this.separator && nextLine.length() > i + 1 && nextLine.charAt(i + 1) != this.separator) {
                            sb.append(c);
                        }
                    }
                }
                else if (c == this.separator && !inQuotes) {
                    tokensOnThisLine.add(sb.toString().trim());
                    sb = new StringBuilder();
                }
                else {
                    sb.append(c);
                }
            }
        } while (inQuotes);
        tokensOnThisLine.add(sb.toString().trim());
        return tokensOnThisLine.toArray(new String[tokensOnThisLine.size()]);
    }
    
    public void close() throws IOException {
        this.br.close();
    }
}
