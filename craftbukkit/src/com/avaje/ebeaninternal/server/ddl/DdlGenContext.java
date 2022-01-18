// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebean.config.dbplatform.DbType;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.util.Iterator;
import java.util.HashSet;
import java.util.ArrayList;
import com.avaje.ebean.config.NamingConvention;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import java.util.Set;
import java.util.List;
import com.avaje.ebean.config.dbplatform.DbDdlSyntax;
import com.avaje.ebean.config.dbplatform.DbTypeMap;
import java.io.StringWriter;

public class DdlGenContext
{
    private final StringWriter stringWriter;
    private final DbTypeMap dbTypeMap;
    private final DbDdlSyntax ddlSyntax;
    private final String newLine;
    private final List<String> contentBuffer;
    private Set<String> intersectionTables;
    private List<String> intersectionTablesCreateDdl;
    private List<String> intersectionTablesFkDdl;
    private final DatabasePlatform dbPlatform;
    private final NamingConvention namingConvention;
    private int fkCount;
    private int ixCount;
    
    public DdlGenContext(final DatabasePlatform dbPlatform, final NamingConvention namingConvention) {
        this.stringWriter = new StringWriter();
        this.contentBuffer = new ArrayList<String>();
        this.intersectionTables = new HashSet<String>();
        this.intersectionTablesCreateDdl = new ArrayList<String>();
        this.intersectionTablesFkDdl = new ArrayList<String>();
        this.dbPlatform = dbPlatform;
        this.dbTypeMap = dbPlatform.getDbTypeMap();
        this.ddlSyntax = dbPlatform.getDbDdlSyntax();
        this.newLine = this.ddlSyntax.getNewLine();
        this.namingConvention = namingConvention;
    }
    
    public DatabasePlatform getDbPlatform() {
        return this.dbPlatform;
    }
    
    public boolean isProcessIntersectionTable(final String tableName) {
        return this.intersectionTables.add(tableName);
    }
    
    public void addCreateIntersectionTable(final String createTableDdl) {
        this.intersectionTablesCreateDdl.add(createTableDdl);
    }
    
    public void addIntersectionTableFk(final String intTableFk) {
        this.intersectionTablesFkDdl.add(intTableFk);
    }
    
    public void addIntersectionCreateTables() {
        for (final String intTableCreate : this.intersectionTablesCreateDdl) {
            this.write(this.newLine);
            this.write(intTableCreate);
        }
    }
    
    public void addIntersectionFkeys() {
        this.write(this.newLine);
        this.write(this.newLine);
        for (final String intTableFk : this.intersectionTablesFkDdl) {
            this.write(this.newLine);
            this.write(intTableFk);
        }
    }
    
    public String getContent() {
        return this.stringWriter.toString();
    }
    
    public DbTypeMap getDbTypeMap() {
        return this.dbTypeMap;
    }
    
    public DbDdlSyntax getDdlSyntax() {
        return this.ddlSyntax;
    }
    
    public String getColumnDefn(final BeanProperty p) {
        final DbType dbType = this.getDbType(p);
        return p.renderDbType(dbType);
    }
    
    private DbType getDbType(final BeanProperty p) {
        final ScalarType<?> scalarType = p.getScalarType();
        if (scalarType == null) {
            throw new RuntimeException("No scalarType for " + p.getFullBeanName());
        }
        if (p.isDbEncrypted()) {
            return this.dbTypeMap.get(p.getDbEncryptedType());
        }
        int jdbcType = scalarType.getJdbcType();
        if (p.isLob() && jdbcType == 12) {
            jdbcType = 2005;
        }
        return this.dbTypeMap.get(jdbcType);
    }
    
    public DdlGenContext write(String content, final int minWidth) {
        content = this.pad(content, minWidth);
        this.contentBuffer.add(content);
        return this;
    }
    
    public DdlGenContext write(final String content) {
        return this.write(content, 0);
    }
    
    public DdlGenContext writeNewLine() {
        this.write(this.newLine);
        return this;
    }
    
    public DdlGenContext removeLast() {
        if (!this.contentBuffer.isEmpty()) {
            this.contentBuffer.remove(this.contentBuffer.size() - 1);
            return this;
        }
        throw new RuntimeException("No lastContent to remove?");
    }
    
    public DdlGenContext flush() {
        if (!this.contentBuffer.isEmpty()) {
            for (final String s : this.contentBuffer) {
                if (s != null) {
                    this.stringWriter.write(s);
                }
            }
            this.contentBuffer.clear();
        }
        return this;
    }
    
    private String padding(final int length) {
        final StringBuffer sb = new StringBuffer(length);
        for (int i = 0; i < length; ++i) {
            sb.append(" ");
        }
        return sb.toString();
    }
    
    public String pad(final String content, final int minWidth) {
        if (minWidth > 0 && content.length() < minWidth) {
            final int padding = minWidth - content.length();
            return content + this.padding(padding);
        }
        return content;
    }
    
    public NamingConvention getNamingConvention() {
        return this.namingConvention;
    }
    
    public int incrementFkCount() {
        return ++this.fkCount;
    }
    
    public int incrementIxCount() {
        return ++this.ixCount;
    }
}
