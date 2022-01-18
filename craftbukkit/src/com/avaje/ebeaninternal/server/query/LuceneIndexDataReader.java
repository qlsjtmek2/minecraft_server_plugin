// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLException;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.Query;
import com.avaje.ebeaninternal.server.core.LuceneOrmQueryRequest;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import java.io.IOException;
import javax.persistence.PersistenceException;
import org.apache.lucene.search.Filter;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import com.avaje.ebeaninternal.server.lucene.LIndexSearch;
import org.apache.lucene.search.ScoreDoc;
import com.avaje.ebeaninternal.server.lucene.LIndexField;
import com.avaje.ebeaninternal.server.lucene.LIndexFields;
import com.avaje.ebeaninternal.server.type.DataReader;

public class LuceneIndexDataReader implements DataReader
{
    private final LIndexFields indexFieldDefn;
    private LIndexField[] readFields;
    private ScoreDoc[] scoreDocs;
    private LIndexSearch indexSearch;
    private IndexSearcher searcher;
    private int maxReadRows;
    private int colIndex;
    private int rowIndex;
    private Document currentDoc;
    
    public LuceneIndexDataReader(final OrmQueryRequest<?> request) {
        final LIndex luceneIndex = request.getLuceneIndex();
        this.indexFieldDefn = luceneIndex.getIndexFieldDefn();
        this.readFields = this.indexFieldDefn.getReadFields();
        this.indexSearch = luceneIndex.getIndexSearch();
        this.searcher = this.indexSearch.getIndexSearcher();
        final LuceneOrmQueryRequest luceneRequest = request.getLuceneOrmQueryRequest();
        int maxRows = request.getQuery().getMaxRows();
        if (maxRows < 1) {
            maxRows = 100;
        }
        final Query luceneQuery = luceneRequest.getLuceneQuery();
        final Sort luceneSort = luceneRequest.getLuceneSort();
        try {
            TopDocs topDocs;
            if (luceneSort == null) {
                topDocs = this.searcher.search(luceneQuery, (Filter)null, maxRows);
            }
            else {
                topDocs = (TopDocs)this.searcher.search(luceneQuery, (Filter)null, maxRows, luceneSort);
            }
            this.scoreDocs = topDocs.scoreDocs;
            this.maxReadRows = this.scoreDocs.length;
        }
        catch (IOException e) {
            throw new PersistenceException(e);
        }
    }
    
    public byte[] readFieldAsBytes() {
        try {
            final String fieldName = this.readFields[this.colIndex++].getName();
            return this.currentDoc.getBinaryValue(fieldName);
        }
        catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
    
    public String readFieldAsString() {
        try {
            final String fieldName = this.readFields[this.colIndex++].getName();
            return this.currentDoc.get(fieldName);
        }
        catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
    
    public void close() throws SQLException {
        this.indexSearch.releaseClose();
    }
    
    public Array getArray() throws SQLException {
        throw new PersistenceException("Not Supported yet");
    }
    
    public BigDecimal getBigDecimal() throws SQLException {
        final String s = this.readFieldAsString();
        return (s == null) ? null : new BigDecimal(s);
    }
    
    public byte[] getBinaryBytes() throws SQLException {
        return this.readFieldAsBytes();
    }
    
    public byte[] getBlobBytes() throws SQLException {
        return this.readFieldAsBytes();
    }
    
    public Boolean getBoolean() throws SQLException {
        final String s = this.readFieldAsString();
        return (s == null) ? null : Boolean.valueOf(s);
    }
    
    public Byte getByte() throws SQLException {
        final byte[] bytes = this.readFieldAsBytes();
        return (bytes == null) ? null : bytes[0];
    }
    
    public byte[] getBytes() throws SQLException {
        return this.readFieldAsBytes();
    }
    
    public Date getDate() throws SQLException {
        final Long longVal = this.getLong();
        return (longVal == null) ? null : new Date(longVal);
    }
    
    public Double getDouble() throws SQLException {
        final String s = this.readFieldAsString();
        return (s == null) ? null : Double.valueOf(s);
    }
    
    public Float getFloat() throws SQLException {
        final String s = this.readFieldAsString();
        return (s == null) ? null : Float.valueOf(s);
    }
    
    public Integer getInt() throws SQLException {
        final String s = this.readFieldAsString();
        return (s == null) ? null : Integer.valueOf(s);
    }
    
    public Long getLong() throws SQLException {
        final String s = this.readFieldAsString();
        return (s == null) ? null : Long.valueOf(s);
    }
    
    public Short getShort() throws SQLException {
        final String s = this.readFieldAsString();
        return (s == null) ? null : Short.valueOf(s);
    }
    
    public String getString() throws SQLException {
        return this.readFieldAsString();
    }
    
    public String getStringClob() throws SQLException {
        return this.readFieldAsString();
    }
    
    public String getStringFromStream() throws SQLException {
        return this.readFieldAsString();
    }
    
    public Time getTime() throws SQLException {
        final String s = this.readFieldAsString();
        return (s == null) ? null : Time.valueOf(s);
    }
    
    public Timestamp getTimestamp() throws SQLException {
        final Long longVal = this.getLong();
        return (longVal == null) ? null : new Timestamp(longVal);
    }
    
    public void incrementPos(final int increment) {
        this.colIndex += increment;
    }
    
    public boolean next() throws SQLException {
        if (this.rowIndex >= this.maxReadRows) {
            return false;
        }
        try {
            final int docIndex = this.scoreDocs[this.rowIndex++].doc;
            this.currentDoc = this.searcher.doc(docIndex);
            return true;
        }
        catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
    
    public void resetColumnPosition() {
        this.colIndex = 0;
    }
}
