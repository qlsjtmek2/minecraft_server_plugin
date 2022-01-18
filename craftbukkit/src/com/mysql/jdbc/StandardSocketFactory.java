// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.util.Properties;
import java.io.IOException;
import java.net.SocketException;
import java.net.Socket;
import java.lang.reflect.Method;

public class StandardSocketFactory implements SocketFactory
{
    public static final String TCP_NO_DELAY_PROPERTY_NAME = "tcpNoDelay";
    public static final String TCP_KEEP_ALIVE_DEFAULT_VALUE = "true";
    public static final String TCP_KEEP_ALIVE_PROPERTY_NAME = "tcpKeepAlive";
    public static final String TCP_RCV_BUF_PROPERTY_NAME = "tcpRcvBuf";
    public static final String TCP_SND_BUF_PROPERTY_NAME = "tcpSndBuf";
    public static final String TCP_TRAFFIC_CLASS_PROPERTY_NAME = "tcpTrafficClass";
    public static final String TCP_RCV_BUF_DEFAULT_VALUE = "0";
    public static final String TCP_SND_BUF_DEFAULT_VALUE = "0";
    public static final String TCP_TRAFFIC_CLASS_DEFAULT_VALUE = "0";
    public static final String TCP_NO_DELAY_DEFAULT_VALUE = "true";
    private static Method setTraficClassMethod;
    protected String host;
    protected int port;
    protected Socket rawSocket;
    
    public StandardSocketFactory() {
        this.host = null;
        this.port = 3306;
        this.rawSocket = null;
    }
    
    public Socket afterHandshake() throws SocketException, IOException {
        return this.rawSocket;
    }
    
    public Socket beforeHandshake() throws SocketException, IOException {
        return this.rawSocket;
    }
    
    private void configureSocket(final Socket sock, final Properties props) throws SocketException, IOException {
        try {
            sock.setTcpNoDelay(Boolean.valueOf(props.getProperty("tcpNoDelay", "true")));
            final String keepAlive = props.getProperty("tcpKeepAlive", "true");
            if (keepAlive != null && keepAlive.length() > 0) {
                sock.setKeepAlive(Boolean.valueOf(keepAlive));
            }
            final int receiveBufferSize = Integer.parseInt(props.getProperty("tcpRcvBuf", "0"));
            if (receiveBufferSize > 0) {
                sock.setReceiveBufferSize(receiveBufferSize);
            }
            final int sendBufferSize = Integer.parseInt(props.getProperty("tcpSndBuf", "0"));
            if (sendBufferSize > 0) {
                sock.setSendBufferSize(sendBufferSize);
            }
            final int trafficClass = Integer.parseInt(props.getProperty("tcpTrafficClass", "0"));
            if (trafficClass > 0 && StandardSocketFactory.setTraficClassMethod != null) {
                StandardSocketFactory.setTraficClassMethod.invoke(sock, new Integer(trafficClass));
            }
        }
        catch (Throwable t) {
            this.unwrapExceptionToProperClassAndThrowIt(t);
        }
    }
    
    public Socket connect(final String hostname, final int portNumber, final Properties props) throws SocketException, IOException {
        if (props != null) {
            this.host = hostname;
            this.port = portNumber;
            Method connectWithTimeoutMethod = null;
            Method socketBindMethod = null;
            Class socketAddressClass = null;
            final String localSocketHostname = props.getProperty("localSocketAddress");
            final String connectTimeoutStr = props.getProperty("connectTimeout");
            int connectTimeout = 0;
            final boolean wantsTimeout = connectTimeoutStr != null && connectTimeoutStr.length() > 0 && !connectTimeoutStr.equals("0");
            final boolean wantsLocalBind = localSocketHostname != null && localSocketHostname.length() > 0;
            final boolean needsConfigurationBeforeConnect = this.socketNeedsConfigurationBeforeConnect(props);
            if (wantsTimeout || wantsLocalBind || needsConfigurationBeforeConnect) {
                if (connectTimeoutStr != null) {
                    try {
                        connectTimeout = Integer.parseInt(connectTimeoutStr);
                    }
                    catch (NumberFormatException nfe) {
                        throw new SocketException("Illegal value '" + connectTimeoutStr + "' for connectTimeout");
                    }
                }
                try {
                    socketAddressClass = Class.forName("java.net.SocketAddress");
                    connectWithTimeoutMethod = Socket.class.getMethod("connect", socketAddressClass, Integer.TYPE);
                    socketBindMethod = Socket.class.getMethod("bind", socketAddressClass);
                }
                catch (NoClassDefFoundError noClassDefFound) {}
                catch (NoSuchMethodException noSuchMethodEx) {}
                catch (Throwable t2) {}
                if (wantsLocalBind && socketBindMethod == null) {
                    throw new SocketException("Can't specify \"localSocketAddress\" on JVMs older than 1.4");
                }
                if (wantsTimeout && connectWithTimeoutMethod == null) {
                    throw new SocketException("Can't specify \"connectTimeout\" on JVMs older than 1.4");
                }
            }
            if (this.host != null) {
                if (!wantsLocalBind && !wantsTimeout && !needsConfigurationBeforeConnect) {
                    final InetAddress[] possibleAddresses = InetAddress.getAllByName(this.host);
                    Throwable caughtWhileConnecting = null;
                    int i = 0;
                    while (i < possibleAddresses.length) {
                        try {
                            this.configureSocket(this.rawSocket = new Socket(possibleAddresses[i], this.port), props);
                        }
                        catch (Exception ex) {
                            caughtWhileConnecting = ex;
                            ++i;
                            continue;
                        }
                        break;
                    }
                    if (this.rawSocket == null) {
                        this.unwrapExceptionToProperClassAndThrowIt(caughtWhileConnecting);
                    }
                }
                else {
                    try {
                        final InetAddress[] possibleAddresses = InetAddress.getAllByName(this.host);
                        Throwable caughtWhileConnecting = null;
                        Object localSockAddr = null;
                        Class inetSocketAddressClass = null;
                        Constructor addrConstructor = null;
                        try {
                            inetSocketAddressClass = Class.forName("java.net.InetSocketAddress");
                            addrConstructor = inetSocketAddressClass.getConstructor(InetAddress.class, Integer.TYPE);
                            if (wantsLocalBind) {
                                localSockAddr = addrConstructor.newInstance(InetAddress.getByName(localSocketHostname), new Integer(0));
                            }
                        }
                        catch (Throwable ex2) {
                            this.unwrapExceptionToProperClassAndThrowIt(ex2);
                        }
                        int j = 0;
                        while (j < possibleAddresses.length) {
                            try {
                                this.configureSocket(this.rawSocket = new Socket(), props);
                                final Object sockAddr = addrConstructor.newInstance(possibleAddresses[j], new Integer(this.port));
                                socketBindMethod.invoke(this.rawSocket, localSockAddr);
                                connectWithTimeoutMethod.invoke(this.rawSocket, sockAddr, new Integer(connectTimeout));
                            }
                            catch (Exception ex3) {
                                this.rawSocket = null;
                                caughtWhileConnecting = ex3;
                                ++j;
                                continue;
                            }
                            break;
                        }
                        if (this.rawSocket == null) {
                            this.unwrapExceptionToProperClassAndThrowIt(caughtWhileConnecting);
                        }
                    }
                    catch (Throwable t) {
                        this.unwrapExceptionToProperClassAndThrowIt(t);
                    }
                }
                return this.rawSocket;
            }
        }
        throw new SocketException("Unable to create socket");
    }
    
    private boolean socketNeedsConfigurationBeforeConnect(final Properties props) {
        final int receiveBufferSize = Integer.parseInt(props.getProperty("tcpRcvBuf", "0"));
        if (receiveBufferSize > 0) {
            return true;
        }
        final int sendBufferSize = Integer.parseInt(props.getProperty("tcpSndBuf", "0"));
        if (sendBufferSize > 0) {
            return true;
        }
        final int trafficClass = Integer.parseInt(props.getProperty("tcpTrafficClass", "0"));
        return trafficClass > 0 && StandardSocketFactory.setTraficClassMethod != null;
    }
    
    private void unwrapExceptionToProperClassAndThrowIt(Throwable caughtWhileConnecting) throws SocketException, IOException {
        if (caughtWhileConnecting instanceof InvocationTargetException) {
            caughtWhileConnecting = ((InvocationTargetException)caughtWhileConnecting).getTargetException();
        }
        if (caughtWhileConnecting instanceof SocketException) {
            throw (SocketException)caughtWhileConnecting;
        }
        if (caughtWhileConnecting instanceof IOException) {
            throw (IOException)caughtWhileConnecting;
        }
        throw new SocketException(caughtWhileConnecting.toString());
    }
    
    static {
        try {
            StandardSocketFactory.setTraficClassMethod = Socket.class.getMethod("setTrafficClass", Integer.TYPE);
        }
        catch (SecurityException e) {
            StandardSocketFactory.setTraficClassMethod = null;
        }
        catch (NoSuchMethodException e2) {
            StandardSocketFactory.setTraficClassMethod = null;
        }
    }
}
