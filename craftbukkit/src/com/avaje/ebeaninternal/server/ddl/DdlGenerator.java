// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ddl;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.sql.Connection;
import com.avaje.ebean.Transaction;
import java.util.List;
import java.io.StringReader;
import java.io.Reader;
import java.io.LineNumberReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import javax.persistence.PersistenceException;
import java.io.IOException;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.NamingConvention;
import java.io.PrintStream;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import java.util.logging.Logger;

public class DdlGenerator
{
    private static final Logger logger;
    private final SpiEbeanServer server;
    private final DatabasePlatform dbPlatform;
    private PrintStream out;
    private int summaryLength;
    private boolean debug;
    private boolean generateDdl;
    private boolean runDdl;
    private String dropContent;
    private String createContent;
    private NamingConvention namingConvention;
    
    public DdlGenerator(final SpiEbeanServer server, final DatabasePlatform dbPlatform, final ServerConfig serverConfig) {
        this.out = System.out;
        this.summaryLength = 80;
        this.debug = true;
        this.server = server;
        this.dbPlatform = dbPlatform;
        this.generateDdl = serverConfig.isDdlGenerate();
        this.runDdl = serverConfig.isDdlRun();
        this.namingConvention = serverConfig.getNamingConvention();
    }
    
    public void execute(final boolean online) {
        this.generateDdl();
        if (online) {
            this.runDdl();
        }
    }
    
    public void generateDdl() {
        if (this.generateDdl) {
            this.writeDrop(this.getDropFileName());
            this.writeCreate(this.getCreateFileName());
        }
    }
    
    public void runDdl() {
        if (this.runDdl) {
            try {
                if (this.dropContent == null) {
                    this.dropContent = this.readFile(this.getDropFileName());
                }
                if (this.createContent == null) {
                    this.createContent = this.readFile(this.getCreateFileName());
                }
                this.runScript(true, this.dropContent);
                this.runScript(false, this.createContent);
            }
            catch (IOException e) {
                final String msg = "Error reading drop/create script from file system";
                throw new RuntimeException(msg, e);
            }
        }
    }
    
    protected void writeDrop(final String dropFile) {
        try {
            final String c = this.generateDropDdl();
            this.writeFile(dropFile, c);
        }
        catch (IOException e) {
            final String msg = "Error generating Drop DDL";
            throw new PersistenceException(msg, e);
        }
    }
    
    protected void writeCreate(final String createFile) {
        try {
            final String c = this.generateCreateDdl();
            this.writeFile(createFile, c);
        }
        catch (IOException e) {
            final String msg = "Error generating Create DDL";
            throw new PersistenceException(msg, e);
        }
    }
    
    public String generateDropDdl() {
        final DdlGenContext ctx = this.createContext();
        final DropTableVisitor drop = new DropTableVisitor(ctx);
        VisitorUtil.visit(this.server, drop);
        final DropSequenceVisitor dropSequence = new DropSequenceVisitor(ctx);
        VisitorUtil.visit(this.server, dropSequence);
        ctx.flush();
        return this.dropContent = ctx.getContent();
    }
    
    public String generateCreateDdl() {
        final DdlGenContext ctx = this.createContext();
        final CreateTableVisitor create = new CreateTableVisitor(ctx);
        VisitorUtil.visit(this.server, create);
        final CreateSequenceVisitor createSequence = new CreateSequenceVisitor(ctx);
        VisitorUtil.visit(this.server, createSequence);
        final AddForeignKeysVisitor fkeys = new AddForeignKeysVisitor(ctx);
        VisitorUtil.visit(this.server, fkeys);
        ctx.flush();
        return this.createContent = ctx.getContent();
    }
    
    protected String getDropFileName() {
        return this.server.getName() + "-drop.sql";
    }
    
    protected String getCreateFileName() {
        return this.server.getName() + "-create.sql";
    }
    
    protected DdlGenContext createContext() {
        return new DdlGenContext(this.dbPlatform, this.namingConvention);
    }
    
    protected void writeFile(final String fileName, final String fileContent) throws IOException {
        final File f = new File(fileName);
        final FileWriter fw = new FileWriter(f);
        try {
            fw.write(fileContent);
            fw.flush();
        }
        finally {
            fw.close();
        }
    }
    
    protected String readFile(final String fileName) throws IOException {
        final File f = new File(fileName);
        if (!f.exists()) {
            return null;
        }
        final StringBuilder buf = new StringBuilder();
        final FileReader fr = new FileReader(f);
        final LineNumberReader lr = new LineNumberReader(fr);
        try {
            String s = null;
            while ((s = lr.readLine()) != null) {
                buf.append(s).append("\n");
            }
        }
        finally {
            lr.close();
        }
        return buf.toString();
    }
    
    public void runScript(final boolean expectErrors, final String content) {
        final StringReader sr = new StringReader(content);
        final List<String> statements = this.parseStatements(sr);
        final Transaction t = this.server.createTransaction();
        try {
            final Connection connection = t.getConnection();
            this.out.println("runScript");
            this.out.flush();
            this.runStatements(expectErrors, statements, connection);
            this.out.println("... end of script");
            this.out.flush();
            t.commit();
        }
        catch (Exception e) {
            final String msg = "Error: " + e.getMessage();
            throw new PersistenceException(msg, e);
        }
        finally {
            t.end();
        }
    }
    
    private void runStatements(final boolean expectErrors, final List<String> statements, final Connection c) {
        for (int i = 0; i < statements.size(); ++i) {
            final String xOfy = i + 1 + " of " + statements.size();
            this.runStatement(expectErrors, xOfy, statements.get(i), c);
        }
    }
    
    private void runStatement(final boolean expectErrors, final String oneOf, String stmt, final Connection c) {
        PreparedStatement pstmt = null;
        try {
            stmt = stmt.trim();
            if (stmt.endsWith(";")) {
                stmt = stmt.substring(0, stmt.length() - 1);
            }
            else if (stmt.endsWith("/")) {
                stmt = stmt.substring(0, stmt.length() - 1);
            }
            if (this.debug) {
                this.out.println("executing " + oneOf + " " + this.getSummary(stmt));
                this.out.flush();
            }
            pstmt = c.prepareStatement(stmt);
            pstmt.execute();
        }
        catch (Exception e) {
            if (!expectErrors) {
                final String msg = "Error executing stmt[" + stmt + "] error[" + e.getMessage() + "]";
                throw new RuntimeException(msg, e);
            }
            this.out.println(" ... ignoring error executing " + this.getSummary(stmt) + "  error: " + e.getMessage());
            e.printStackTrace();
            this.out.flush();
            if (pstmt != null) {
                try {
                    pstmt.close();
                }
                catch (SQLException e2) {
                    DdlGenerator.logger.log(Level.SEVERE, "Error closing pstmt", e2);
                }
            }
        }
        finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                }
                catch (SQLException e3) {
                    DdlGenerator.logger.log(Level.SEVERE, "Error closing pstmt", e3);
                }
            }
        }
    }
    
    protected List<String> parseStatements(final StringReader reader) {
        try {
            final BufferedReader br = new BufferedReader(reader);
            final ArrayList<String> statements = new ArrayList<String>();
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = br.readLine()) != null) {
                s = s.trim();
                final int semiPos = s.indexOf(59);
                if (semiPos == -1) {
                    sb.append(s).append(" ");
                }
                else if (semiPos == s.length() - 1) {
                    sb.append(s);
                    statements.add(sb.toString().trim());
                    sb = new StringBuilder();
                }
                else {
                    final String preSemi = s.substring(0, semiPos);
                    sb.append(preSemi);
                    statements.add(sb.toString().trim());
                    sb = new StringBuilder();
                    sb.append(s.substring(semiPos + 1));
                }
            }
            return statements;
        }
        catch (IOException e) {
            throw new PersistenceException(e);
        }
    }
    
    private String getSummary(final String s) {
        if (s.length() > this.summaryLength) {
            return s.substring(0, this.summaryLength).trim() + "...";
        }
        return s;
    }
    
    static {
        logger = Logger.getLogger(DdlGenerator.class.getName());
    }
}
