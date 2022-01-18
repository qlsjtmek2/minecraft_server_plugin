// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.text.csv;

import java.util.Date;
import java.text.ParseException;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import java.util.Arrays;
import com.avaje.ebean.text.csv.CsvCallback;
import com.avaje.ebean.text.csv.DefaultCsvCallback;
import java.io.Reader;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.avaje.ebean.text.TextException;
import com.avaje.ebean.text.StringParser;
import java.util.ArrayList;
import java.util.Locale;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.text.TimeStringParser;
import com.avaje.ebean.text.csv.CsvReader;

public class TCsvReader<T> implements CsvReader<T>
{
    private static final TimeStringParser TIME_PARSER;
    private final EbeanServer server;
    private final BeanDescriptor<T> descriptor;
    private final List<CsvColumn> columnList;
    private final CsvColumn ignoreColumn;
    private boolean treatEmptyStringAsNull;
    private boolean hasHeader;
    private int logInfoFrequency;
    private String defaultTimeFormat;
    private String defaultDateFormat;
    private String defaultTimestampFormat;
    private Locale defaultLocale;
    protected int persistBatchSize;
    private boolean addPropertiesFromHeader;
    
    public TCsvReader(final EbeanServer server, final BeanDescriptor<T> descriptor) {
        this.columnList = new ArrayList<CsvColumn>();
        this.ignoreColumn = new CsvColumn();
        this.treatEmptyStringAsNull = true;
        this.logInfoFrequency = 1000;
        this.defaultTimeFormat = "HH:mm:ss";
        this.defaultDateFormat = "yyyy-MM-dd";
        this.defaultTimestampFormat = "yyyy-MM-dd hh:mm:ss.fffffffff";
        this.defaultLocale = Locale.getDefault();
        this.persistBatchSize = 30;
        this.server = server;
        this.descriptor = descriptor;
    }
    
    public void setDefaultLocale(final Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }
    
    public void setDefaultTimeFormat(final String defaultTimeFormat) {
        this.defaultTimeFormat = defaultTimeFormat;
    }
    
    public void setDefaultDateFormat(final String defaultDateFormat) {
        this.defaultDateFormat = defaultDateFormat;
    }
    
    public void setDefaultTimestampFormat(final String defaultTimestampFormat) {
        this.defaultTimestampFormat = defaultTimestampFormat;
    }
    
    public void setPersistBatchSize(final int persistBatchSize) {
        this.persistBatchSize = persistBatchSize;
    }
    
    public void setIgnoreHeader() {
        this.setHasHeader(true, false);
    }
    
    public void setAddPropertiesFromHeader() {
        this.setHasHeader(true, true);
    }
    
    public void setHasHeader(final boolean hasHeader, final boolean addPropertiesFromHeader) {
        this.hasHeader = hasHeader;
        this.addPropertiesFromHeader = addPropertiesFromHeader;
    }
    
    public void setLogInfoFrequency(final int logInfoFrequency) {
        this.logInfoFrequency = logInfoFrequency;
    }
    
    public void addIgnore() {
        this.columnList.add(this.ignoreColumn);
    }
    
    public void addProperty(final String propertyName) {
        this.addProperty(propertyName, null);
    }
    
    public void addReference(final String propertyName) {
        this.addProperty(propertyName, null, true);
    }
    
    public void addProperty(final String propertyName, final StringParser parser) {
        this.addProperty(propertyName, parser, false);
    }
    
    public void addDateTime(final String propertyName, final String dateTimeFormat) {
        this.addDateTime(propertyName, dateTimeFormat, Locale.getDefault());
    }
    
    public void addDateTime(final String propertyName, String dateTimeFormat, Locale locale) {
        final ElPropertyValue elProp = this.descriptor.getElGetValue(propertyName);
        if (!elProp.isDateTimeCapable()) {
            throw new TextException("Property " + propertyName + " is not DateTime capable");
        }
        if (dateTimeFormat == null) {
            dateTimeFormat = this.getDefaultDateTimeFormat(elProp.getJdbcType());
        }
        if (locale == null) {
            locale = this.defaultLocale;
        }
        final SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat, locale);
        final DateTimeParser parser = new DateTimeParser(sdf, dateTimeFormat, elProp);
        final CsvColumn column = new CsvColumn(elProp, parser, false);
        this.columnList.add(column);
    }
    
    private String getDefaultDateTimeFormat(final int jdbcType) {
        switch (jdbcType) {
            case 92: {
                return this.defaultTimeFormat;
            }
            case 91: {
                return this.defaultDateFormat;
            }
            case 93: {
                return this.defaultTimestampFormat;
            }
            default: {
                throw new RuntimeException("Expected java.sql.Types TIME,DATE or TIMESTAMP but got [" + jdbcType + "]");
            }
        }
    }
    
    public void addProperty(final String propertyName, StringParser parser, final boolean reference) {
        final ElPropertyValue elProp = this.descriptor.getElGetValue(propertyName);
        if (parser == null) {
            parser = elProp.getStringParser();
        }
        final CsvColumn column = new CsvColumn(elProp, parser, reference);
        this.columnList.add(column);
    }
    
    public void process(final Reader reader) throws Exception {
        final DefaultCsvCallback<T> callback = new DefaultCsvCallback<T>(this.persistBatchSize, this.logInfoFrequency);
        this.process(reader, callback);
    }
    
    public void process(final Reader reader, final CsvCallback<T> callback) throws Exception {
        if (reader == null) {
            throw new NullPointerException("reader is null?");
        }
        if (callback == null) {
            throw new NullPointerException("callback is null?");
        }
        final CsvUtilReader utilReader = new CsvUtilReader(reader);
        callback.begin(this.server);
        int row = 0;
        Label_0084: {
            if (!this.hasHeader) {
                break Label_0084;
            }
            String[] line = utilReader.readNext();
            if (this.addPropertiesFromHeader) {
                this.addPropertiesFromHeader(line);
            }
            callback.readHeader(line);
            try {
                while (true) {
                    ++row;
                    line = utilReader.readNext();
                    if (line == null) {
                        --row;
                        callback.end(row);
                        break;
                    }
                    if (!callback.processLine(row, line)) {
                        continue;
                    }
                    if (line.length != this.columnList.size()) {
                        final String msg = "Error at line " + row + ". Expected [" + this.columnList.size() + "] columns " + "but instead we have [" + line.length + "].  Line[" + Arrays.toString(line) + "]";
                        throw new TextException(msg);
                    }
                    final T bean = this.buildBeanFromLineContent(row, line);
                    callback.processBean(row, line, bean);
                }
            }
            catch (Exception e) {
                callback.endWithError(row, e);
                throw e;
            }
        }
    }
    
    private void addPropertiesFromHeader(final String[] line) {
        for (int i = 0; i < line.length; ++i) {
            final ElPropertyValue elProp = this.descriptor.getElGetValue(line[i]);
            if (elProp == null) {
                throw new TextException("Property [" + line[i] + "] not found");
            }
            if (92 == elProp.getJdbcType()) {
                this.addProperty(line[i], TCsvReader.TIME_PARSER);
            }
            else if (this.isDateTimeType(elProp.getJdbcType())) {
                this.addDateTime(line[i], null, null);
            }
            else if (elProp.isAssocProperty()) {
                final BeanPropertyAssocOne<?> assocOne = (BeanPropertyAssocOne<?>)elProp.getBeanProperty();
                final String idProp = assocOne.getBeanDescriptor().getIdBinder().getIdProperty();
                this.addReference(line[i] + "." + idProp);
            }
            else {
                this.addProperty(line[i]);
            }
        }
    }
    
    private boolean isDateTimeType(final int t) {
        return t == 93 || t == 91 || t == 92;
    }
    
    protected T buildBeanFromLineContent(final int row, final String[] line) {
        try {
            final T bean;
            final EntityBean entityBean = (EntityBean)(bean = (T)this.descriptor.createEntityBean());
            for (int columnPos = 0; columnPos < line.length; ++columnPos) {
                this.convertAndSetColumn(columnPos, line[columnPos], entityBean);
            }
            return bean;
        }
        catch (RuntimeException e) {
            final String msg = "Error at line: " + row + " line[" + Arrays.toString(line) + "]";
            throw new RuntimeException(msg, e);
        }
    }
    
    protected void convertAndSetColumn(final int columnPos, String strValue, final Object bean) {
        strValue = strValue.trim();
        if (strValue.length() == 0 && this.treatEmptyStringAsNull) {
            return;
        }
        final CsvColumn c = this.columnList.get(columnPos);
        c.convertAndSet(strValue, bean);
    }
    
    static {
        TIME_PARSER = new TimeStringParser();
    }
    
    public static class CsvColumn
    {
        private final ElPropertyValue elProp;
        private final StringParser parser;
        private final boolean ignore;
        private final boolean reference;
        
        private CsvColumn() {
            this.elProp = null;
            this.parser = null;
            this.reference = false;
            this.ignore = true;
        }
        
        public CsvColumn(final ElPropertyValue elProp, final StringParser parser, final boolean reference) {
            this.elProp = elProp;
            this.parser = parser;
            this.reference = reference;
            this.ignore = false;
        }
        
        public void convertAndSet(final String strValue, final Object bean) {
            if (!this.ignore) {
                final Object value = this.parser.parse(strValue);
                this.elProp.elSetValue(bean, value, true, this.reference);
            }
        }
    }
    
    private static class DateTimeParser implements StringParser
    {
        private final DateFormat dateFormat;
        private final ElPropertyValue elProp;
        private final String format;
        
        DateTimeParser(final DateFormat dateFormat, final String format, final ElPropertyValue elProp) {
            this.dateFormat = dateFormat;
            this.elProp = elProp;
            this.format = format;
        }
        
        public Object parse(final String value) {
            try {
                final Date dt = this.dateFormat.parse(value);
                return this.elProp.parseDateTime(dt.getTime());
            }
            catch (ParseException e) {
                throw new TextException("Error parsing [" + value + "] using format[" + this.format + "]", e);
            }
        }
    }
}
