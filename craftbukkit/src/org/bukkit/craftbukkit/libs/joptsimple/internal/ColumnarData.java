// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple.internal;

import java.util.Comparator;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ColumnarData
{
    private static final String LINE_SEPARATOR;
    private static final int TOTAL_WIDTH = 80;
    private final ColumnWidthCalculator widthCalculator;
    private final List<Column> columns;
    private final String[] headers;
    
    public ColumnarData(final String... headers) {
        this.headers = headers.clone();
        this.widthCalculator = new ColumnWidthCalculator();
        this.columns = new LinkedList<Column>();
        this.clear();
    }
    
    public void addRow(final Object... rowData) {
        final int[] numberOfCellsAddedAt = this.addRowCells(rowData);
        this.addPaddingCells(numberOfCellsAddedAt);
    }
    
    public String format() {
        final StringBuilder buffer = new StringBuilder();
        this.writeHeadersOn(buffer);
        this.writeSeparatorsOn(buffer);
        this.writeRowsOn(buffer);
        return buffer.toString();
    }
    
    public final void clear() {
        this.columns.clear();
        final int desiredColumnWidth = this.widthCalculator.calculate(80, this.headers.length);
        for (final String each : this.headers) {
            this.columns.add(new Column(each, desiredColumnWidth));
        }
    }
    
    private void writeHeadersOn(final StringBuilder buffer) {
        final Iterator<Column> iter = this.columns.iterator();
        while (iter.hasNext()) {
            iter.next().writeHeaderOn(buffer, iter.hasNext());
        }
        buffer.append(ColumnarData.LINE_SEPARATOR);
    }
    
    private void writeSeparatorsOn(final StringBuilder buffer) {
        final Iterator<Column> iter = this.columns.iterator();
        while (iter.hasNext()) {
            iter.next().writeSeparatorOn(buffer, iter.hasNext());
        }
        buffer.append(ColumnarData.LINE_SEPARATOR);
    }
    
    private void writeRowsOn(final StringBuilder buffer) {
        for (int maxHeight = Collections.max((Collection<? extends Column>)this.columns, (Comparator<? super Column>)Column.BY_HEIGHT).height(), i = 0; i < maxHeight; ++i) {
            this.writeRowOn(buffer, i);
        }
    }
    
    private void writeRowOn(final StringBuilder buffer, final int rowIndex) {
        final Iterator<Column> iter = this.columns.iterator();
        while (iter.hasNext()) {
            iter.next().writeCellOn(rowIndex, buffer, iter.hasNext());
        }
        buffer.append(ColumnarData.LINE_SEPARATOR);
    }
    
    private int arrayMax(final int[] numbers) {
        int maximum = Integer.MIN_VALUE;
        for (final int each : numbers) {
            maximum = Math.max(maximum, each);
        }
        return maximum;
    }
    
    private int[] addRowCells(final Object... rowData) {
        final int[] cellsAddedAt = new int[rowData.length];
        final Iterator<Column> iter = this.columns.iterator();
        for (int i = 0; iter.hasNext() && i < rowData.length; ++i) {
            cellsAddedAt[i] = iter.next().addCells(rowData[i]);
        }
        return cellsAddedAt;
    }
    
    private void addPaddingCells(final int... numberOfCellsAddedAt) {
        final int maxHeight = this.arrayMax(numberOfCellsAddedAt);
        final Iterator<Column> iter = this.columns.iterator();
        for (int i = 0; iter.hasNext() && i < numberOfCellsAddedAt.length; ++i) {
            this.addPaddingCellsForColumn(iter.next(), maxHeight, numberOfCellsAddedAt[i]);
        }
    }
    
    private void addPaddingCellsForColumn(final Column column, final int maxHeight, final int numberOfCellsAdded) {
        for (int i = 0; i < maxHeight - numberOfCellsAdded; ++i) {
            column.addCell("");
        }
    }
    
    static {
        LINE_SEPARATOR = System.getProperty("line.separator");
    }
}
