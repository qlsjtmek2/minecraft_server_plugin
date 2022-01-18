// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.util;

import org.xml.sax.XMLReader;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.InputSource;
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.ByteArrayOutputStream;

public class DnodeReader
{
    public Dnode parseXml(final String str) {
        try {
            final ByteArrayOutputStream bao = new ByteArrayOutputStream(str.length());
            final OutputStreamWriter osw = new OutputStreamWriter(bao);
            final StringReader sr = new StringReader(str);
            final int charBufferSize = 1024;
            final char[] buf = new char[charBufferSize];
            int len;
            while ((len = sr.read(buf, 0, buf.length)) != -1) {
                osw.write(buf, 0, len);
            }
            sr.close();
            osw.flush();
            osw.close();
            bao.flush();
            bao.close();
            final InputStream is = new ByteArrayInputStream(bao.toByteArray());
            return this.parseXml(is);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public Dnode parseXml(final InputStream in) {
        try {
            final InputSource inSource = new InputSource(in);
            final DnodeParser parser = new DnodeParser();
            final XMLReader myReader = XMLReaderFactory.createXMLReader();
            myReader.setContentHandler(parser);
            myReader.parse(inSource);
            return parser.getRoot();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
