// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.socket;

import java.io.IOException;
import java.net.Socket;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class SocketConnection
{
    ObjectOutputStream oos;
    ObjectInputStream ois;
    InputStream is;
    OutputStream os;
    Socket socket;
    
    public SocketConnection(final Socket socket) throws IOException {
        this.is = socket.getInputStream();
        this.os = socket.getOutputStream();
        this.socket = socket;
    }
    
    public void disconnect() throws IOException {
        this.os.flush();
        this.socket.close();
    }
    
    public void flush() throws IOException {
        this.os.flush();
    }
    
    public Object readObject() throws IOException, ClassNotFoundException {
        return this.getObjectInputStream().readObject();
    }
    
    public ObjectOutputStream writeObject(final Object object) throws IOException {
        final ObjectOutputStream oos = this.getObjectOutputStream();
        oos.writeObject(object);
        return oos;
    }
    
    public ObjectOutputStream getObjectOutputStream() throws IOException {
        if (this.oos == null) {
            this.oos = new ObjectOutputStream(this.os);
        }
        return this.oos;
    }
    
    public ObjectInputStream getObjectInputStream() throws IOException {
        if (this.ois == null) {
            this.ois = new ObjectInputStream(this.is);
        }
        return this.ois;
    }
    
    public void setObjectInputStream(final ObjectInputStream ois) {
        this.ois = ois;
    }
    
    public void setObjectOutputStream(final ObjectOutputStream oos) {
        this.oos = oos;
    }
    
    public InputStream getInputStream() throws IOException {
        return this.is;
    }
    
    public OutputStream getOutputStream() throws IOException {
        return this.os;
    }
}
