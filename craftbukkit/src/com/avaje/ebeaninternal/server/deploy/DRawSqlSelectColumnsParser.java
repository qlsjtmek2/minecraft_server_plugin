// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.util.logging.Level;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import com.avaje.ebean.config.NamingConvention;
import java.util.List;
import java.util.logging.Logger;

public final class DRawSqlSelectColumnsParser
{
    private static Logger logger;
    private String matchDescription;
    private String searchColumn;
    private int columnIndex;
    private int pos;
    private final int end;
    private final String sqlSelect;
    private final List<DRawSqlColumnInfo> columns;
    private final BeanDescriptor<?> desc;
    private final NamingConvention namingConvention;
    private final DRawSqlSelectBuilder parent;
    private final boolean debug;
    
    public DRawSqlSelectColumnsParser(final DRawSqlSelectBuilder parent, final String sqlSelect) {
        this.columns = new ArrayList<DRawSqlColumnInfo>();
        this.parent = parent;
        this.debug = parent.isDebug();
        this.namingConvention = parent.getNamingConvention();
        this.desc = parent.getBeanDescriptor();
        this.sqlSelect = sqlSelect;
        this.end = sqlSelect.length();
    }
    
    public List<DRawSqlColumnInfo> parse() {
        while (this.pos <= this.end) {
            this.nextColumnInfo();
        }
        return this.columns;
    }
    
    private void nextColumnInfo() {
        final int start = this.pos;
        this.nextComma();
        String colInfo = this.sqlSelect.substring(start, this.pos);
        ++this.pos;
        colInfo = colInfo.trim();
        int secLastSpace = -1;
        final int lastSpace = colInfo.lastIndexOf(32);
        if (lastSpace > -1) {
            secLastSpace = colInfo.lastIndexOf(32, lastSpace - 1);
        }
        String colName = null;
        String colLabel = null;
        if (lastSpace == -1) {
            colName = (colLabel = colInfo);
        }
        else if (secLastSpace == -1) {
            colName = colInfo.substring(0, lastSpace);
            colLabel = colInfo.substring(lastSpace + 1);
            if (colName.equals("")) {
                colName = colLabel;
            }
        }
        else {
            final String expectedAs = colInfo.substring(secLastSpace + 1, lastSpace);
            if (!expectedAs.toLowerCase().equals("as")) {
                String msg = "Error in " + this.parent.getErrName() + ". ";
                msg = msg + "Expected \"AS\" keyword but got [" + expectedAs + "] in select clause [" + colInfo + "]";
                throw new PersistenceException(msg);
            }
            colName = colInfo.substring(0, secLastSpace);
            colLabel = colInfo.substring(lastSpace + 1);
        }
        final BeanProperty prop = this.findProperty(colLabel);
        if (prop == null) {
            if (this.debug) {
                final String msg = "ColumnMapping ... idx[" + this.columnIndex + "] ERROR, no property found to match... column[" + colName + "] label[" + colLabel + "] search[" + this.searchColumn + "]";
                this.parent.debug(msg);
            }
            String msg = "Error in " + this.parent.getErrName() + ". ";
            msg = msg + "No matching bean property for column[" + colName + "] columnLabel[" + colLabel + "] idx[" + this.columnIndex + "] using search[" + this.searchColumn + "] found?";
            DRawSqlSelectColumnsParser.logger.log(Level.SEVERE, msg);
        }
        else {
            String msg = null;
            if (this.debug || DRawSqlSelectColumnsParser.logger.isLoggable(Level.FINE)) {
                msg = "ColumnMapping ... idx[" + this.columnIndex + "] match column[" + colName + "] label[" + colLabel + "] to property[" + prop + "]" + this.matchDescription;
            }
            if (this.debug) {
                this.parent.debug(msg);
            }
            if (DRawSqlSelectColumnsParser.logger.isLoggable(Level.FINE)) {
                DRawSqlSelectColumnsParser.logger.fine(msg);
            }
            final DRawSqlColumnInfo info = new DRawSqlColumnInfo(colName, colLabel, prop.getName(), prop.isScalar());
            this.columns.add(info);
            ++this.columnIndex;
        }
    }
    
    private String removeQuotedIdentifierChars(final String columnLabel) {
        final char c = columnLabel.charAt(0);
        if (Character.isJavaIdentifierStart(c)) {
            return columnLabel;
        }
        final String result = columnLabel.substring(1, columnLabel.length() - 1);
        final String msg = "sql-select trimming quoted identifier from[" + columnLabel + "] to[" + result + "]";
        DRawSqlSelectColumnsParser.logger.fine(msg);
        return result;
    }
    
    private BeanProperty findProperty(final String column) {
        this.searchColumn = column;
        final int dotPos = this.searchColumn.indexOf(".");
        if (dotPos > -1) {
            this.searchColumn = this.searchColumn.substring(dotPos + 1);
        }
        this.searchColumn = this.removeQuotedIdentifierChars(this.searchColumn);
        BeanProperty matchingProp = this.desc.getBeanProperty(this.searchColumn);
        if (matchingProp != null) {
            this.matchDescription = "";
            return matchingProp;
        }
        final String propertyName = this.namingConvention.getPropertyFromColumn(this.desc.getBeanType(), this.searchColumn);
        matchingProp = this.desc.getBeanProperty(propertyName);
        if (matchingProp != null) {
            this.matchDescription = " ... using naming convention";
            return matchingProp;
        }
        this.matchDescription = " ... by linear search";
        final BeanProperty[] propertiesBase = this.desc.propertiesBaseScalar();
        for (int i = 0; i < propertiesBase.length; ++i) {
            final BeanProperty prop = propertiesBase[i];
            if (this.isMatch(prop, this.searchColumn)) {
                return prop;
            }
        }
        final BeanProperty[] propertiesId = this.desc.propertiesId();
        for (int j = 0; j < propertiesId.length; ++j) {
            final BeanProperty prop2 = propertiesId[j];
            if (this.isMatch(prop2, this.searchColumn)) {
                return prop2;
            }
        }
        final BeanPropertyAssocOne<?>[] propertiesAssocOne = this.desc.propertiesOne();
        for (int k = 0; k < propertiesAssocOne.length; ++k) {
            final BeanProperty prop3 = propertiesAssocOne[k];
            if (this.isMatch(prop3, this.searchColumn)) {
                return prop3;
            }
        }
        return null;
    }
    
    private boolean isMatch(final BeanProperty prop, final String columnLabel) {
        return columnLabel.equalsIgnoreCase(prop.getDbColumn()) || columnLabel.equalsIgnoreCase(prop.getName());
    }
    
    private int nextComma() {
        boolean inQuote = false;
        while (this.pos < this.end) {
            final char c = this.sqlSelect.charAt(this.pos);
            if (c == '\'') {
                inQuote = !inQuote;
            }
            else if (!inQuote && c == ',') {
                return this.pos;
            }
            ++this.pos;
        }
        return this.pos;
    }
    
    static {
        DRawSqlSelectColumnsParser.logger = Logger.getLogger(DRawSqlSelectColumnsParser.class.getName());
    }
}
