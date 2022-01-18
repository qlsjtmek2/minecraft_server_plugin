// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Properties;
import java.io.IOException;
import java.net.SocketException;
import java.net.Socket;

public class NamedPipeSocketFactory implements SocketFactory
{
    public static final String NAMED_PIPE_PROP_NAME = "namedPipePath";
    private Socket namedPipeSocket;
    
    public Socket afterHandshake() throws SocketException, IOException {
        return this.namedPipeSocket;
    }
    
    public Socket beforeHandshake() throws SocketException, IOException {
        return this.namedPipeSocket;
    }
    
    public Socket connect(final String host, final int portNumber, final Properties props) throws SocketException, IOException {
        String namedPipePath = props.getProperty("namedPipePath");
        if (namedPipePath == null) {
            namedPipePath = "\\\\.\\pipe\\MySQL";
        }
        else if (namedPipePath.length() == 0) {
            throw new SocketException(Messages.getString("NamedPipeSocketFactory.2") + "namedPipePath" + Messages.getString("NamedPipeSocketFactory.3"));
        }
        return this.namedPipeSocket = new NamedPipeSocket(namedPipePath);
    }
    
    class NamedPipeSocket extends Socket
    {
        private boolean isClosed;
        private RandomAccessFile namedPipeFile;
        
        NamedPipeSocket(final String filePath) throws IOException {
            this.isClosed = false;
            if (filePath == null || filePath.length() == 0) {
                throw new IOException(Messages.getString("NamedPipeSocketFactory.4"));
            }
            this.namedPipeFile = new RandomAccessFile(filePath, "rw");
        }
        
        public synchronized void close() throws IOException {
            this.namedPipeFile.close();
            this.isClosed = true;
        }
        
        public InputStream getInputStream() throws IOException {
            return new RandomAccessFileInputStream(this.namedPipeFile);
        }
        
        public OutputStream getOutputStream() throws IOException {
            return new RandomAccessFileOutputStream(this.namedPipeFile);
        }
        
        public boolean isClosed() {
            return this.isClosed;
        }
    }
    
    class RandomAccessFileInputStream extends InputStream
    {
        RandomAccessFile raFile;
        
        RandomAccessFileInputStream(final RandomAccessFile file) {
            this.raFile = file;
        }
        
        public int available() throws IOException {
            return -1;
        }
        
        public void close() throws IOException {
            this.raFile.close();
        }
        
        public int read() throws IOException {
            return this.raFile.read();
        }
        
        public int read(final byte[] b) throws IOException {
            return this.raFile.read(b);
        }
        
        public int read(final byte[] b, final int off, final int len) throws IOException {
            return this.raFile.read(b, off, len);
        }
    }
    
    class RandomAccessFileOutputStream extends OutputStream
    {
        RandomAccessFile raFile;
        
        RandomAccessFileOutputStream(final RandomAccessFile file) {
            this.raFile = file;
        }
        
        public void close() throws IOException {
            this.raFile.close();
        }
        
        public void write(final byte[] b) throws IOException {
            this.raFile.write(b);
        }
        
        public void write(final byte[] b, final int off, final int len) throws IOException {
            this.raFile.write(b, off, len);
        }
        
        public void write(final int b) throws IOException {
        }
    }
}
