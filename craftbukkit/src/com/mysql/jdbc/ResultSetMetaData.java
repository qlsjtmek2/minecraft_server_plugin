// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLException;

public class ResultSetMetaData implements java.sql.ResultSetMetaData
{
    Field[] fields;
    boolean useOldAliasBehavior;
    private ExceptionInterceptor exceptionInterceptor;
    
    private static int clampedGetLength(final Field f) {
        long fieldLength = f.getLength();
        if (fieldLength > 2147483647L) {
            fieldLength = 2147483647L;
        }
        return (int)fieldLength;
    }
    
    private static final boolean isDecimalType(final int type) {
        switch (type) {
            case -7:
            case -6:
            case -5:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public ResultSetMetaData(final Field[] fields, final boolean useOldAliasBehavior, final ExceptionInterceptor exceptionInterceptor) {
        this.useOldAliasBehavior = false;
        this.fields = fields;
        this.useOldAliasBehavior = useOldAliasBehavior;
        this.exceptionInterceptor = exceptionInterceptor;
    }
    
    public String getCatalogName(final int column) throws SQLException {
        final Field f = this.getField(column);
        final String database = f.getDatabaseName();
        return (database == null) ? "" : database;
    }
    
    public String getColumnCharacterEncoding(final int column) throws SQLException {
        final String mysqlName = this.getColumnCharacterSet(column);
        String javaName = null;
        if (mysqlName != null) {
            javaName = CharsetMapping.getJavaEncodingForMysqlEncoding(mysqlName, null);
        }
        return javaName;
    }
    
    public String getColumnCharacterSet(final int column) throws SQLException {
        return this.getField(column).getCharacterSet();
    }
    
    public String getColumnClassName(final int column) throws SQLException {
        final Field f = this.getField(column);
        return getClassNameForJavaType(f.getSQLType(), f.isUnsigned(), f.getMysqlType(), f.isBinary() || f.isBlob(), f.isOpaqueBinary());
    }
    
    public int getColumnCount() throws SQLException {
        return this.fields.length;
    }
    
    public int getColumnDisplaySize(final int column) throws SQLException {
        final Field f = this.getField(column);
        final int lengthInBytes = clampedGetLength(f);
        return lengthInBytes / f.getMaxBytesPerCharacter();
    }
    
    public String getColumnLabel(final int column) throws SQLException {
        if (this.useOldAliasBehavior) {
            return this.getColumnName(column);
        }
        return this.getField(column).getColumnLabel();
    }
    
    public String getColumnName(final int column) throws SQLException {
        if (this.useOldAliasBehavior) {
            return this.getField(column).getName();
        }
        final String name = this.getField(column).getNameNoAliases();
        if (name != null && name.length() == 0) {
            return this.getField(column).getName();
        }
        return name;
    }
    
    public int getColumnType(final int column) throws SQLException {
        return this.getField(column).getSQLType();
    }
    
    public String getColumnTypeName(final int column) throws SQLException {
        final Field field = this.getField(column);
        final int mysqlType = field.getMysqlType();
        final int jdbcType = field.getSQLType();
        switch (mysqlType) {
            case 16: {
                return "BIT";
            }
            case 0:
            case 246: {
                return field.isUnsigned() ? "DECIMAL UNSIGNED" : "DECIMAL";
            }
            case 1: {
                return field.isUnsigned() ? "TINYINT UNSIGNED" : "TINYINT";
            }
            case 2: {
                return field.isUnsigned() ? "SMALLINT UNSIGNED" : "SMALLINT";
            }
            case 3: {
                return field.isUnsigned() ? "INT UNSIGNED" : "INT";
            }
            case 4: {
                return field.isUnsigned() ? "FLOAT UNSIGNED" : "FLOAT";
            }
            case 5: {
                return field.isUnsigned() ? "DOUBLE UNSIGNED" : "DOUBLE";
            }
            case 6: {
                return "NULL";
            }
            case 7: {
                return "TIMESTAMP";
            }
            case 8: {
                return field.isUnsigned() ? "BIGINT UNSIGNED" : "BIGINT";
            }
            case 9: {
                return field.isUnsigned() ? "MEDIUMINT UNSIGNED" : "MEDIUMINT";
            }
            case 10: {
                return "DATE";
            }
            case 11: {
                return "TIME";
            }
            case 12: {
                return "DATETIME";
            }
            case 249: {
                return "TINYBLOB";
            }
            case 250: {
                return "MEDIUMBLOB";
            }
            case 251: {
                return "LONGBLOB";
            }
            case 252: {
                if (this.getField(column).isBinary()) {
                    return "BLOB";
                }
                return "TEXT";
            }
            case 15: {
                return "VARCHAR";
            }
            case 253: {
                if (jdbcType == -3) {
                    return "VARBINARY";
                }
                return "VARCHAR";
            }
            case 254: {
                if (jdbcType == -2) {
                    return "BINARY";
                }
                return "CHAR";
            }
            case 247: {
                return "ENUM";
            }
            case 13: {
                return "YEAR";
            }
            case 248: {
                return "SET";
            }
            case 255: {
                return "GEOMETRY";
            }
            default: {
                return "UNKNOWN";
            }
        }
    }
    
    protected Field getField(final int columnIndex) throws SQLException {
        if (columnIndex < 1 || columnIndex > this.fields.length) {
            throw SQLError.createSQLException(Messages.getString("ResultSetMetaData.46"), "S1002", this.exceptionInterceptor);
        }
        return this.fields[columnIndex - 1];
    }
    
    public int getPrecision(final int column) throws SQLException {
        final Field f = this.getField(column);
        if (isDecimalType(f.getSQLType())) {
            if (f.getDecimals() > 0) {
                return clampedGetLength(f) - 1 + f.getPrecisionAdjustFactor();
            }
            return clampedGetLength(f) + f.getPrecisionAdjustFactor();
        }
        else {
            switch (f.getMysqlType()) {
                case 249:
                case 250:
                case 251:
                case 252: {
                    return clampedGetLength(f);
                }
                default: {
                    return clampedGetLength(f) / f.getMaxBytesPerCharacter();
                }
            }
        }
    }
    
    public int getScale(final int column) throws SQLException {
        final Field f = this.getField(column);
        if (isDecimalType(f.getSQLType())) {
            return f.getDecimals();
        }
        return 0;
    }
    
    public String getSchemaName(final int column) throws SQLException {
        return "";
    }
    
    public String getTableName(final int column) throws SQLException {
        if (this.useOldAliasBehavior) {
            return this.getField(column).getTableName();
        }
        return this.getField(column).getTableNameNoAliases();
    }
    
    public boolean isAutoIncrement(final int column) throws SQLException {
        final Field f = this.getField(column);
        return f.isAutoIncrement();
    }
    
    public boolean isCaseSensitive(final int column) throws SQLException {
        final Field field = this.getField(column);
        final int sqlType = field.getSQLType();
        switch (sqlType) {
            case -7:
            case -6:
            case -5:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 91:
            case 92:
            case 93: {
                return false;
            }
            case -1:
            case 1:
            case 12: {
                if (field.isBinary()) {
                    return true;
                }
                final String collationName = field.getCollation();
                return collationName != null && !collationName.endsWith("_ci");
            }
            default: {
                return true;
            }
        }
    }
    
    public boolean isCurrency(final int column) throws SQLException {
        return false;
    }
    
    public boolean isDefinitelyWritable(final int column) throws SQLException {
        return this.isWritable(column);
    }
    
    public int isNullable(final int column) throws SQLException {
        if (!this.getField(column).isNotNull()) {
            return 1;
        }
        return 0;
    }
    
    public boolean isReadOnly(final int column) throws SQLException {
        return this.getField(column).isReadOnly();
    }
    
    public boolean isSearchable(final int column) throws SQLException {
        return true;
    }
    
    public boolean isSigned(final int column) throws SQLException {
        final Field f = this.getField(column);
        final int sqlType = f.getSQLType();
        switch (sqlType) {
            case -6:
            case -5:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8: {
                return !f.isUnsigned();
            }
            case 91:
            case 92:
            case 93: {
                return false;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean isWritable(final int column) throws SQLException {
        return !this.isReadOnly(column);
    }
    
    public String toString() {
        final StringBuffer toStringBuf = new StringBuffer();
        toStringBuf.append(super.toString());
        toStringBuf.append(" - Field level information: ");
        for (int i = 0; i < this.fields.length; ++i) {
            toStringBuf.append("\n\t");
            toStringBuf.append(this.fields[i].toString());
        }
        return toStringBuf.toString();
    }
    
    static String getClassNameForJavaType(final int javaType, final boolean isUnsigned, final int mysqlTypeIfKnown, final boolean isBinaryOrBlob, final boolean isOpaqueBinary) {
        switch (javaType) {
            case -7:
            case 16: {
                return "java.lang.Boolean";
            }
            case -6: {
                if (isUnsigned) {
                    return "java.lang.Integer";
                }
                return "java.lang.Integer";
            }
            case 5: {
                if (isUnsigned) {
                    return "java.lang.Integer";
                }
                return "java.lang.Integer";
            }
            case 4: {
                if (!isUnsigned || mysqlTypeIfKnown == 9) {
                    return "java.lang.Integer";
                }
                return "java.lang.Long";
            }
            case -5: {
                if (!isUnsigned) {
                    return "java.lang.Long";
                }
                return "java.math.BigInteger";
            }
            case 2:
            case 3: {
                return "java.math.BigDecimal";
            }
            case 7: {
                return "java.lang.Float";
            }
            case 6:
            case 8: {
                return "java.lang.Double";
            }
            case -1:
            case 1:
            case 12: {
                if (!isOpaqueBinary) {
                    return "java.lang.String";
                }
                return "[B";
            }
            case -4:
            case -3:
            case -2: {
                if (mysqlTypeIfKnown == 255) {
                    return "[B";
                }
                if (isBinaryOrBlob) {
                    return "[B";
                }
                return "java.lang.String";
            }
            case 91: {
                return "java.sql.Date";
            }
            case 92: {
                return "java.sql.Time";
            }
            case 93: {
                return "java.sql.Timestamp";
            }
            default: {
                return "java.lang.Object";
            }
        }
    }
    
    public boolean isWrapperFor(final Class iface) throws SQLException {
        return iface.isInstance(this);
    }
    
    public Object unwrap(final Class iface) throws SQLException {
        try {
            return Util.cast(iface, this);
        }
        catch (ClassCastException cce) {
            throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.exceptionInterceptor);
        }
    }
}
