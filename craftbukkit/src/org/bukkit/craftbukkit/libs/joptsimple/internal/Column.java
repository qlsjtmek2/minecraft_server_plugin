// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple.internal;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.BreakIterator;
import java.util.Locale;
import java.util.LinkedList;
import java.util.List;
import java.util.Comparator;

public class Column
{
    static final Comparator<Column> BY_HEIGHT;
    private final String header;
    private final List<String> data;
    private final int width;
    private int height;
    
    Column(final String header, final int width) {
        this.header = header;
        this.width = Math.max(width, header.length());
        this.data = new LinkedList<String>();
        this.height = 0;
    }
    
    int addCells(final Object cellCandidate) {
        final int originalHeight = this.height;
        final String source = String.valueOf(cellCandidate).trim();
        for (final String eachPiece : source.split(System.getProperty("line.separator"))) {
            this.processNextEmbeddedLine(eachPiece);
        }
        return this.height - originalHeight;
    }
    
    private void processNextEmbeddedLine(final String line) {
        final BreakIterator words = BreakIterator.getLineInstance(Locale.US);
        words.setText(line);
        StringBuilder nextCell = new StringBuilder();
        int start = words.first();
        for (int end = words.next(); end != -1; end = words.next()) {
            nextCell = this.processNextWord(line, nextCell, start, end);
            start = end;
        }
        if (nextCell.length() > 0) {
            this.addCell(nextCell.toString());
        }
    }
    
    private StringBuilder processNextWord(final String source, final StringBuilder nextCell, final int start, final int end) {
        StringBuilder augmented = nextCell;
        final String word = source.substring(start, end);
        if (augmented.length() + word.length() > this.width) {
            this.addCell(augmented.toString());
            augmented = new StringBuilder("  ").append(word);
        }
        else {
            augmented.append(word);
        }
        return augmented;
    }
    
    void addCell(final String newCell) {
        this.data.add(newCell);
        ++this.height;
    }
    
    void writeHeaderOn(final StringBuilder buffer, final boolean appendSpace) {
        buffer.append(this.header).append(Strings.repeat(' ', this.width - this.header.length()));
        if (appendSpace) {
            buffer.append(' ');
        }
    }
    
    void writeSeparatorOn(final StringBuilder buffer, final boolean appendSpace) {
        buffer.append(Strings.repeat('-', this.header.length())).append(Strings.repeat(' ', this.width - this.header.length()));
        if (appendSpace) {
            buffer.append(' ');
        }
    }
    
    void writeCellOn(final int index, final StringBuilder buffer, final boolean appendSpace) {
        if (index < this.data.size()) {
            final String item = this.data.get(index);
            buffer.append(item).append(Strings.repeat(' ', this.width - item.length()));
            if (appendSpace) {
                buffer.append(' ');
            }
        }
    }
    
    int height() {
        return this.height;
    }
    
    static {
        BY_HEIGHT = new Comparator<Column>() {
            public int compare(final Column first, final Column second) {
                if (first.height() < second.height()) {
                    return -1;
                }
                return (first.height() != second.height()) ? 1 : 0;
            }
        };
    }
}
