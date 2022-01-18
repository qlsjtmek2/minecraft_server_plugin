// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.SocketException;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import io.netty.util.internal.logging.InternalLogger;
import java.net.NetworkInterface;
import java.net.InetAddress;

public final class NetUtil
{
    public static final InetAddress LOCALHOST;
    public static final NetworkInterface LOOPBACK_IF;
    public static final int SOMAXCONN;
    private static final InternalLogger logger;
    
    private static void validateHost(final InetAddress host) throws IOException {
        ServerSocket ss = null;
        Socket s1 = null;
        Socket s2 = null;
        try {
            ss = new ServerSocket();
            ss.setReuseAddress(false);
            ss.bind(new InetSocketAddress(host, 0));
            s1 = new Socket(host, ss.getLocalPort());
            s2 = ss.accept();
        }
        finally {
            if (s2 != null) {
                try {
                    s2.close();
                }
                catch (IOException ex) {}
            }
            if (s1 != null) {
                try {
                    s1.close();
                }
                catch (IOException ex2) {}
            }
            if (ss != null) {
                try {
                    ss.close();
                }
                catch (IOException ex3) {}
            }
        }
    }
    
    public static byte[] createByteArrayFromIpAddressString(String ipAddressString) {
        if (isValidIpV4Address(ipAddressString)) {
            final StringTokenizer tokenizer = new StringTokenizer(ipAddressString, ".");
            final byte[] byteAddress = new byte[4];
            for (int i = 0; i < 4; ++i) {
                final String token = tokenizer.nextToken();
                final int tempInt = Integer.parseInt(token);
                byteAddress[i] = (byte)tempInt;
            }
            return byteAddress;
        }
        if (isValidIpV6Address(ipAddressString)) {
            if (ipAddressString.charAt(0) == '[') {
                ipAddressString = ipAddressString.substring(1, ipAddressString.length() - 1);
            }
            final StringTokenizer tokenizer = new StringTokenizer(ipAddressString, ":.", true);
            final ArrayList<String> hexStrings = new ArrayList<String>();
            final ArrayList<String> decStrings = new ArrayList<String>();
            String token2 = "";
            String prevToken = "";
            int doubleColonIndex = -1;
            while (tokenizer.hasMoreTokens()) {
                prevToken = token2;
                token2 = tokenizer.nextToken();
                if (":".equals(token2)) {
                    if (":".equals(prevToken)) {
                        doubleColonIndex = hexStrings.size();
                    }
                    else {
                        if (prevToken.isEmpty()) {
                            continue;
                        }
                        hexStrings.add(prevToken);
                    }
                }
                else {
                    if (!".".equals(token2)) {
                        continue;
                    }
                    decStrings.add(prevToken);
                }
            }
            if (":".equals(prevToken)) {
                if (":".equals(token2)) {
                    doubleColonIndex = hexStrings.size();
                }
                else {
                    hexStrings.add(token2);
                }
            }
            else if (".".equals(prevToken)) {
                decStrings.add(token2);
            }
            int hexStringsLength = 8;
            if (!decStrings.isEmpty()) {
                hexStringsLength -= 2;
            }
            if (doubleColonIndex != -1) {
                for (int numberToInsert = hexStringsLength - hexStrings.size(), j = 0; j < numberToInsert; ++j) {
                    hexStrings.add(doubleColonIndex, "0");
                }
            }
            final byte[] ipByteArray = new byte[16];
            for (int j = 0; j < hexStrings.size(); ++j) {
                convertToBytes(hexStrings.get(j), ipByteArray, j * 2);
            }
            for (int j = 0; j < decStrings.size(); ++j) {
                ipByteArray[j + 12] = (byte)(Integer.parseInt(decStrings.get(j)) & 0xFF);
            }
            return ipByteArray;
        }
        return null;
    }
    
    private static void convertToBytes(final String hexWord, final byte[] ipByteArray, final int byteIndex) {
        final int hexWordLength = hexWord.length();
        int hexWordIndex = 0;
        ipByteArray[byteIndex + 1] = (ipByteArray[byteIndex] = 0);
        if (hexWordLength > 3) {
            final int charValue = getIntValue(hexWord.charAt(hexWordIndex++));
            ipByteArray[byteIndex] |= (byte)(charValue << 4);
        }
        if (hexWordLength > 2) {
            final int charValue = getIntValue(hexWord.charAt(hexWordIndex++));
            ipByteArray[byteIndex] |= (byte)charValue;
        }
        if (hexWordLength > 1) {
            final int charValue = getIntValue(hexWord.charAt(hexWordIndex++));
            final int n = byteIndex + 1;
            ipByteArray[n] |= (byte)(charValue << 4);
        }
        final int charValue = getIntValue(hexWord.charAt(hexWordIndex));
        final int n2 = byteIndex + 1;
        ipByteArray[n2] |= (byte)(charValue & 0xF);
    }
    
    static int getIntValue(char c) {
        switch (c) {
            case '0': {
                return 0;
            }
            case '1': {
                return 1;
            }
            case '2': {
                return 2;
            }
            case '3': {
                return 3;
            }
            case '4': {
                return 4;
            }
            case '5': {
                return 5;
            }
            case '6': {
                return 6;
            }
            case '7': {
                return 7;
            }
            case '8': {
                return 8;
            }
            case '9': {
                return 9;
            }
            default: {
                c = Character.toLowerCase(c);
                switch (c) {
                    case 'a': {
                        return 10;
                    }
                    case 'b': {
                        return 11;
                    }
                    case 'c': {
                        return 12;
                    }
                    case 'd': {
                        return 13;
                    }
                    case 'e': {
                        return 14;
                    }
                    case 'f': {
                        return 15;
                    }
                    default: {
                        return 0;
                    }
                }
                break;
            }
        }
    }
    
    public static boolean isValidIpV6Address(final String ipAddress) {
        final int length = ipAddress.length();
        boolean doubleColon = false;
        int numberOfColons = 0;
        int numberOfPeriods = 0;
        int numberOfPercent = 0;
        final StringBuilder word = new StringBuilder();
        char c = '\0';
        int offset = 0;
        if (length < 2) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            final char prevChar = c;
            c = ipAddress.charAt(i);
            switch (c) {
                case '[': {
                    if (i != 0) {
                        return false;
                    }
                    if (ipAddress.charAt(length - 1) != ']') {
                        return false;
                    }
                    offset = 1;
                    if (length < 4) {
                        return false;
                    }
                    continue;
                }
                case ']': {
                    if (i != length - 1) {
                        return false;
                    }
                    if (ipAddress.charAt(0) != '[') {
                        return false;
                    }
                    continue;
                }
                case '.': {
                    if (++numberOfPeriods > 3) {
                        return false;
                    }
                    if (!isValidIp4Word(word.toString())) {
                        return false;
                    }
                    if (numberOfColons != 6 && !doubleColon) {
                        return false;
                    }
                    if (numberOfColons == 7 && ipAddress.charAt(offset) != ':' && ipAddress.charAt(1 + offset) != ':') {
                        return false;
                    }
                    word.delete(0, word.length());
                    continue;
                }
                case ':': {
                    if (i == offset && (ipAddress.length() <= i || ipAddress.charAt(i + 1) != ':')) {
                        return false;
                    }
                    if (++numberOfColons > 7) {
                        return false;
                    }
                    if (numberOfPeriods > 0) {
                        return false;
                    }
                    if (prevChar == ':') {
                        if (doubleColon) {
                            return false;
                        }
                        doubleColon = true;
                    }
                    word.delete(0, word.length());
                    continue;
                }
                case '%': {
                    if (numberOfColons == 0) {
                        return false;
                    }
                    ++numberOfPercent;
                    if (i + 1 >= length) {
                        return false;
                    }
                    try {
                        Integer.parseInt(ipAddress.substring(i + 1));
                        continue;
                    }
                    catch (NumberFormatException e) {
                        return false;
                    }
                    break;
                }
            }
            if (numberOfPercent == 0) {
                if (word != null && word.length() > 3) {
                    return false;
                }
                if (!isValidHexChar(c)) {
                    return false;
                }
            }
            word.append(c);
        }
        if (numberOfPeriods > 0) {
            if (numberOfPeriods != 3 || !isValidIp4Word(word.toString()) || numberOfColons >= 7) {
                return false;
            }
        }
        else {
            if (numberOfColons != 7 && !doubleColon) {
                return false;
            }
            if (numberOfPercent == 0 && word.length() == 0 && ipAddress.charAt(length - 1 - offset) == ':' && ipAddress.charAt(length - 2 - offset) != ':') {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isValidIp4Word(final String word) {
        if (word.length() < 1 || word.length() > 3) {
            return false;
        }
        for (int i = 0; i < word.length(); ++i) {
            final char c = word.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return Integer.parseInt(word) <= 255;
    }
    
    static boolean isValidHexChar(final char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }
    
    public static boolean isValidIpV4Address(final String value) {
        int periods = 0;
        final int length = value.length();
        if (length > 15) {
            return false;
        }
        final StringBuilder word = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            final char c = value.charAt(i);
            if (c == '.') {
                if (++periods > 3) {
                    return false;
                }
                if (word.length() == 0) {
                    return false;
                }
                if (Integer.parseInt(word.toString()) > 255) {
                    return false;
                }
                word.delete(0, word.length());
            }
            else {
                if (!Character.isDigit(c)) {
                    return false;
                }
                if (word.length() > 2) {
                    return false;
                }
                word.append(c);
            }
        }
        return word.length() != 0 && Integer.parseInt(word.toString()) <= 255 && periods == 3;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(NetUtil.class);
        InetAddress localhost;
        try {
            localhost = InetAddress.getLocalHost();
            validateHost(localhost);
        }
        catch (IOException e4) {
            try {
                localhost = InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 });
                validateHost(localhost);
            }
            catch (IOException e5) {
                try {
                    localhost = InetAddress.getByAddress(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
                    validateHost(localhost);
                }
                catch (IOException e2) {
                    throw new Error("Failed to resolve localhost - incorrect network configuration?", e2);
                }
            }
        }
        LOCALHOST = localhost;
        NetworkInterface loopbackInterface;
        try {
            loopbackInterface = NetworkInterface.getByInetAddress(NetUtil.LOCALHOST);
        }
        catch (SocketException e3) {
            loopbackInterface = null;
        }
        if (loopbackInterface == null) {
            try {
                final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    final NetworkInterface networkInterface = interfaces.nextElement();
                    if (networkInterface.isLoopback()) {
                        loopbackInterface = networkInterface;
                        break;
                    }
                }
            }
            catch (SocketException e3) {
                NetUtil.logger.error("Failed to enumerate network interfaces", e3);
            }
        }
        LOOPBACK_IF = loopbackInterface;
        int somaxconn = 3072;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("/proc/sys/net/core/somaxconn"));
            somaxconn = Integer.parseInt(in.readLine());
        }
        catch (Exception e6) {}
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (Exception ex) {}
            }
        }
        SOMAXCONN = somaxconn;
    }
}
