// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import javax.net.ssl.TrustManager;
import javax.net.ssl.KeyManager;
import java.security.KeyManagementException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLContext;
import java.net.MalformedURLException;
import java.security.cert.CertificateException;
import java.security.KeyStoreException;
import java.security.UnrecoverableKeyException;
import java.net.URL;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.sql.SQLException;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import javax.net.ssl.SSLSocket;

public class ExportControlled
{
    private static final String SQL_STATE_BAD_SSL_PARAMS = "08000";
    
    protected static boolean enabled() {
        return true;
    }
    
    protected static void transformSocketToSSLSocket(final MysqlIO mysqlIO) throws SQLException {
        final SSLSocketFactory sslFact = getSSLSocketFactoryDefaultOrConfigured(mysqlIO);
        try {
            mysqlIO.mysqlConnection = sslFact.createSocket(mysqlIO.mysqlConnection, mysqlIO.host, mysqlIO.port, true);
            ((SSLSocket)mysqlIO.mysqlConnection).setEnabledProtocols(new String[] { "TLSv1" });
            ((SSLSocket)mysqlIO.mysqlConnection).startHandshake();
            if (mysqlIO.connection.getUseUnbufferedInput()) {
                mysqlIO.mysqlInput = mysqlIO.mysqlConnection.getInputStream();
            }
            else {
                mysqlIO.mysqlInput = new BufferedInputStream(mysqlIO.mysqlConnection.getInputStream(), 16384);
            }
            (mysqlIO.mysqlOutput = new BufferedOutputStream(mysqlIO.mysqlConnection.getOutputStream(), 16384)).flush();
        }
        catch (IOException ioEx) {
            throw SQLError.createCommunicationsException(mysqlIO.connection, mysqlIO.getLastPacketSentTimeMs(), mysqlIO.getLastPacketReceivedTimeMs(), ioEx, mysqlIO.getExceptionInterceptor());
        }
    }
    
    private static SSLSocketFactory getSSLSocketFactoryDefaultOrConfigured(final MysqlIO mysqlIO) throws SQLException {
        final String clientCertificateKeyStoreUrl = mysqlIO.connection.getClientCertificateKeyStoreUrl();
        final String trustCertificateKeyStoreUrl = mysqlIO.connection.getTrustCertificateKeyStoreUrl();
        final String clientCertificateKeyStoreType = mysqlIO.connection.getClientCertificateKeyStoreType();
        final String clientCertificateKeyStorePassword = mysqlIO.connection.getClientCertificateKeyStorePassword();
        final String trustCertificateKeyStoreType = mysqlIO.connection.getTrustCertificateKeyStoreType();
        final String trustCertificateKeyStorePassword = mysqlIO.connection.getTrustCertificateKeyStorePassword();
        if (StringUtils.isNullOrEmpty(clientCertificateKeyStoreUrl) && StringUtils.isNullOrEmpty(trustCertificateKeyStoreUrl) && mysqlIO.connection.getVerifyServerCertificate()) {
            return (SSLSocketFactory)SSLSocketFactory.getDefault();
        }
        TrustManagerFactory tmf = null;
        KeyManagerFactory kmf = null;
        try {
            tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        }
        catch (NoSuchAlgorithmException nsae) {
            throw SQLError.createSQLException("Default algorithm definitions for TrustManager and/or KeyManager are invalid.  Check java security properties file.", "08000", 0, false, mysqlIO.getExceptionInterceptor());
        }
        if (!StringUtils.isNullOrEmpty(clientCertificateKeyStoreUrl)) {
            try {
                if (!StringUtils.isNullOrEmpty(clientCertificateKeyStoreType)) {
                    final KeyStore clientKeyStore = KeyStore.getInstance(clientCertificateKeyStoreType);
                    final URL ksURL = new URL(clientCertificateKeyStoreUrl);
                    final char[] password = (clientCertificateKeyStorePassword == null) ? new char[0] : clientCertificateKeyStorePassword.toCharArray();
                    clientKeyStore.load(ksURL.openStream(), password);
                    kmf.init(clientKeyStore, password);
                }
            }
            catch (UnrecoverableKeyException uke) {
                throw SQLError.createSQLException("Could not recover keys from client keystore.  Check password?", "08000", 0, false, mysqlIO.getExceptionInterceptor());
            }
            catch (NoSuchAlgorithmException nsae) {
                throw SQLError.createSQLException("Unsupported keystore algorithm [" + nsae.getMessage() + "]", "08000", 0, false, mysqlIO.getExceptionInterceptor());
            }
            catch (KeyStoreException kse) {
                throw SQLError.createSQLException("Could not create KeyStore instance [" + kse.getMessage() + "]", "08000", 0, false, mysqlIO.getExceptionInterceptor());
            }
            catch (CertificateException nsae2) {
                throw SQLError.createSQLException("Could not load client" + clientCertificateKeyStoreType + " keystore from " + clientCertificateKeyStoreUrl, mysqlIO.getExceptionInterceptor());
            }
            catch (MalformedURLException mue) {
                throw SQLError.createSQLException(clientCertificateKeyStoreUrl + " does not appear to be a valid URL.", "08000", 0, false, mysqlIO.getExceptionInterceptor());
            }
            catch (IOException ioe) {
                final SQLException sqlEx = SQLError.createSQLException("Cannot open " + clientCertificateKeyStoreUrl + " [" + ioe.getMessage() + "]", "08000", 0, false, mysqlIO.getExceptionInterceptor());
                sqlEx.initCause(ioe);
                throw sqlEx;
            }
        }
        if (!StringUtils.isNullOrEmpty(trustCertificateKeyStoreUrl)) {
            try {
                if (!StringUtils.isNullOrEmpty(trustCertificateKeyStoreType)) {
                    final KeyStore trustKeyStore = KeyStore.getInstance(trustCertificateKeyStoreType);
                    final URL ksURL = new URL(trustCertificateKeyStoreUrl);
                    final char[] password = (trustCertificateKeyStorePassword == null) ? new char[0] : trustCertificateKeyStorePassword.toCharArray();
                    trustKeyStore.load(ksURL.openStream(), password);
                    tmf.init(trustKeyStore);
                }
            }
            catch (NoSuchAlgorithmException nsae) {
                throw SQLError.createSQLException("Unsupported keystore algorithm [" + nsae.getMessage() + "]", "08000", 0, false, mysqlIO.getExceptionInterceptor());
            }
            catch (KeyStoreException kse) {
                throw SQLError.createSQLException("Could not create KeyStore instance [" + kse.getMessage() + "]", "08000", 0, false, mysqlIO.getExceptionInterceptor());
            }
            catch (CertificateException nsae2) {
                throw SQLError.createSQLException("Could not load trust" + trustCertificateKeyStoreType + " keystore from " + trustCertificateKeyStoreUrl, "08000", 0, false, mysqlIO.getExceptionInterceptor());
            }
            catch (MalformedURLException mue) {
                throw SQLError.createSQLException(trustCertificateKeyStoreUrl + " does not appear to be a valid URL.", "08000", 0, false, mysqlIO.getExceptionInterceptor());
            }
            catch (IOException ioe) {
                final SQLException sqlEx = SQLError.createSQLException("Cannot open " + trustCertificateKeyStoreUrl + " [" + ioe.getMessage() + "]", "08000", 0, false, mysqlIO.getExceptionInterceptor());
                sqlEx.initCause(ioe);
                throw sqlEx;
            }
        }
        SSLContext sslContext = null;
        try {
            final SSLContext instance;
            sslContext = (instance = SSLContext.getInstance("TLS"));
            final KeyManager[] array = (KeyManager[])(StringUtils.isNullOrEmpty(clientCertificateKeyStoreUrl) ? null : kmf.getKeyManagers());
            TrustManager[] trustManagers;
            if (mysqlIO.connection.getVerifyServerCertificate()) {
                trustManagers = tmf.getTrustManagers();
            }
            else {
                final X509TrustManager[] array2 = (X509TrustManager[])(trustManagers = new X509TrustManager[] { null });
                array2[0] = new X509TrustManager() {
                    public void checkClientTrusted(final X509Certificate[] chain, final String authType) {
                    }
                    
                    public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
                    }
                    
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };
            }
            instance.init(array, trustManagers, null);
            return sslContext.getSocketFactory();
        }
        catch (NoSuchAlgorithmException nsae3) {
            throw SQLError.createSQLException("TLS is not a valid SSL protocol.", "08000", 0, false, mysqlIO.getExceptionInterceptor());
        }
        catch (KeyManagementException kme) {
            throw SQLError.createSQLException("KeyManagementException: " + kme.getMessage(), "08000", 0, false, mysqlIO.getExceptionInterceptor());
        }
    }
}
