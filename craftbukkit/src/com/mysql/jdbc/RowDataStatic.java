// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLException;
import java.util.List;

public class RowDataStatic implements RowData
{
    private Field[] metadata;
    private int index;
    ResultSetImpl owner;
    private List rows;
    
    public RowDataStatic(final List rows) {
        this.index = -1;
        this.rows = rows;
    }
    
    public void addRow(final ResultSetRow row) {
        this.rows.add(row);
    }
    
    public void afterLast() {
        this.index = this.rows.size();
    }
    
    public void beforeFirst() {
        this.index = -1;
    }
    
    public void beforeLast() {
        this.index = this.rows.size() - 2;
    }
    
    public void close() {
    }
    
    public ResultSetRow getAt(final int atIndex) throws SQLException {
        if (atIndex < 0 || atIndex >= this.rows.size()) {
            return null;
        }
        return this.rows.get(atIndex).setMetadata(this.metadata);
    }
    
    public int getCurrentRowNumber() {
        return this.index;
    }
    
    public ResultSetInternalMethods getOwner() {
        return this.owner;
    }
    
    public boolean hasNext() {
        final boolean hasMore = this.index + 1 < this.rows.size();
        return hasMore;
    }
    
    public boolean isAfterLast() {
        return this.index >= this.rows.size();
    }
    
    public boolean isBeforeFirst() {
        return this.index == -1 && this.rows.size() != 0;
    }
    
    public boolean isDynamic() {
        return false;
    }
    
    public boolean isEmpty() {
        return this.rows.size() == 0;
    }
    
    public boolean isFirst() {
        return this.index == 0;
    }
    
    public boolean isLast() {
        return this.rows.size() != 0 && this.index == this.rows.size() - 1;
    }
    
    public void moveRowRelative(final int rowsToMove) {
        this.index += rowsToMove;
    }
    
    public ResultSetRow next() throws SQLException {
        ++this.index;
        if (this.index < this.rows.size()) {
            final ResultSetRow row = this.rows.get(this.index);
            return row.setMetadata(this.metadata);
        }
        return null;
    }
    
    public void removeRow(final int atIndex) {
        this.rows.remove(atIndex);
    }
    
    public void setCurrentRow(final int newIndex) {
        this.index = newIndex;
    }
    
    public void setOwner(final ResultSetImpl rs) {
        this.owner = rs;
    }
    
    public int size() {
        return this.rows.size();
    }
    
    public boolean wasEmpty() {
        return this.rows != null && this.rows.size() == 0;
    }
    
    public void setMetadata(final Field[] metadata) {
        this.metadata = metadata;
    }
}
