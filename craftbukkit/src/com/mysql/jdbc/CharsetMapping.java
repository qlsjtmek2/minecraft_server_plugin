// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.Set;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Iterator;
import java.util.List;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

public class CharsetMapping
{
    private static final Properties CHARSET_CONFIG;
    public static final String[] INDEX_TO_CHARSET;
    public static final String[] INDEX_TO_COLLATION;
    private static final Map JAVA_TO_MYSQL_CHARSET_MAP;
    private static final Map JAVA_UC_TO_MYSQL_CHARSET_MAP;
    private static final Map ERROR_MESSAGE_FILE_TO_MYSQL_CHARSET_MAP;
    private static final Map MULTIBYTE_CHARSETS;
    private static final Map MYSQL_TO_JAVA_CHARSET_MAP;
    private static final Map MYSQL_ENCODING_NAME_TO_CHARSET_INDEX_MAP;
    private static final String NOT_USED = "ISO8859_1";
    public static final Map STATIC_CHARSET_TO_NUM_BYTES_MAP;
    
    public static final String getJavaEncodingForMysqlEncoding(final String mysqlEncoding, final Connection conn) throws SQLException {
        if (conn != null && conn.versionMeetsMinimum(4, 1, 0) && "latin1".equalsIgnoreCase(mysqlEncoding)) {
            return "Cp1252";
        }
        return CharsetMapping.MYSQL_TO_JAVA_CHARSET_MAP.get(mysqlEncoding);
    }
    
    public static final String getMysqlEncodingForJavaEncoding(final String javaEncodingUC, final Connection conn) throws SQLException {
        final List mysqlEncodings = CharsetMapping.JAVA_UC_TO_MYSQL_CHARSET_MAP.get(javaEncodingUC);
        if (mysqlEncodings != null) {
            final Iterator iter = mysqlEncodings.iterator();
            VersionedStringProperty versionedProp = null;
            while (iter.hasNext()) {
                final VersionedStringProperty propToCheck = iter.next();
                if (conn == null) {
                    return propToCheck.toString();
                }
                if (versionedProp != null && !versionedProp.preferredValue && versionedProp.majorVersion == propToCheck.majorVersion && versionedProp.minorVersion == propToCheck.minorVersion && versionedProp.subminorVersion == propToCheck.subminorVersion) {
                    return versionedProp.toString();
                }
                if (!propToCheck.isOkayForVersion(conn)) {
                    break;
                }
                if (propToCheck.preferredValue) {
                    return propToCheck.toString();
                }
                versionedProp = propToCheck;
            }
            if (versionedProp != null) {
                return versionedProp.toString();
            }
        }
        return null;
    }
    
    static final int getNumberOfCharsetsConfigured() {
        return CharsetMapping.MYSQL_TO_JAVA_CHARSET_MAP.size() / 2;
    }
    
    static final String getCharacterEncodingForErrorMessages(final ConnectionImpl conn) throws SQLException {
        String errorMessageFile = conn.getServerVariable("language");
        if (errorMessageFile == null || errorMessageFile.length() == 0) {
            return "Cp1252";
        }
        int endWithoutSlash = errorMessageFile.length();
        if (errorMessageFile.endsWith("/") || errorMessageFile.endsWith("\\")) {
            --endWithoutSlash;
        }
        int lastSlashIndex = errorMessageFile.lastIndexOf(47, endWithoutSlash - 1);
        if (lastSlashIndex == -1) {
            lastSlashIndex = errorMessageFile.lastIndexOf(92, endWithoutSlash - 1);
        }
        if (lastSlashIndex == -1) {
            lastSlashIndex = 0;
        }
        if (lastSlashIndex == endWithoutSlash || endWithoutSlash < lastSlashIndex) {
            return "Cp1252";
        }
        errorMessageFile = errorMessageFile.substring(lastSlashIndex + 1, endWithoutSlash);
        final String errorMessageEncodingMysql = CharsetMapping.ERROR_MESSAGE_FILE_TO_MYSQL_CHARSET_MAP.get(errorMessageFile);
        if (errorMessageEncodingMysql == null) {
            return "Cp1252";
        }
        final String javaEncoding = getJavaEncodingForMysqlEncoding(errorMessageEncodingMysql, conn);
        if (javaEncoding == null) {
            return "Cp1252";
        }
        return javaEncoding;
    }
    
    static final boolean isAliasForSjis(final String encoding) {
        return "SJIS".equalsIgnoreCase(encoding) || "WINDOWS-31J".equalsIgnoreCase(encoding) || "MS932".equalsIgnoreCase(encoding) || "SHIFT_JIS".equalsIgnoreCase(encoding) || "CP943".equalsIgnoreCase(encoding);
    }
    
    static final boolean isMultibyteCharset(final String javaEncodingName) {
        final String javaEncodingNameUC = javaEncodingName.toUpperCase(Locale.ENGLISH);
        return CharsetMapping.MULTIBYTE_CHARSETS.containsKey(javaEncodingNameUC);
    }
    
    private static void populateMapWithKeyValuePairs(final String configKey, final Map mapToPopulate, final boolean addVersionedProperties, final boolean addUppercaseKeys) {
        final String javaToMysqlConfig = CharsetMapping.CHARSET_CONFIG.getProperty(configKey);
        if (javaToMysqlConfig == null) {
            throw new RuntimeException("Could not find configuration value \"" + configKey + "\" in Charsets.properties resource");
        }
        final List mappings = StringUtils.split(javaToMysqlConfig, ",", true);
        if (mappings != null) {
            for (final String aMapping : mappings) {
                final List parsedPair = StringUtils.split(aMapping, "=", true);
                if (parsedPair.size() != 2) {
                    throw new RuntimeException("Syntax error in Charsets.properties resource for token \"" + aMapping + "\".");
                }
                final String key = parsedPair.get(0).toString();
                final String value = parsedPair.get(1).toString();
                if (addVersionedProperties) {
                    List versionedProperties = mapToPopulate.get(key);
                    if (versionedProperties == null) {
                        versionedProperties = new ArrayList();
                        mapToPopulate.put(key, versionedProperties);
                    }
                    final VersionedStringProperty verProp = new VersionedStringProperty(value);
                    versionedProperties.add(verProp);
                    if (!addUppercaseKeys) {
                        continue;
                    }
                    final String keyUc = key.toUpperCase(Locale.ENGLISH);
                    versionedProperties = mapToPopulate.get(keyUc);
                    if (versionedProperties == null) {
                        versionedProperties = new ArrayList();
                        mapToPopulate.put(keyUc, versionedProperties);
                    }
                    versionedProperties.add(verProp);
                }
                else {
                    mapToPopulate.put(key, value);
                    if (!addUppercaseKeys) {
                        continue;
                    }
                    mapToPopulate.put(key.toUpperCase(Locale.ENGLISH), value);
                }
            }
            return;
        }
        throw new RuntimeException("Missing/corrupt entry for \"" + configKey + "\" in Charsets.properties.");
    }
    
    public static int getCharsetIndexForMysqlEncodingName(final String name) {
        if (name == null) {
            return 0;
        }
        final Integer asInt = CharsetMapping.MYSQL_ENCODING_NAME_TO_CHARSET_INDEX_MAP.get(name);
        if (asInt == null) {
            return 0;
        }
        return asInt;
    }
    
    static {
        CHARSET_CONFIG = new Properties();
        final HashMap tempNumBytesMap = new HashMap();
        tempNumBytesMap.put("big5", Constants.integerValueOf(2));
        tempNumBytesMap.put("dec8", Constants.integerValueOf(1));
        tempNumBytesMap.put("cp850", Constants.integerValueOf(1));
        tempNumBytesMap.put("hp8", Constants.integerValueOf(1));
        tempNumBytesMap.put("koi8r", Constants.integerValueOf(1));
        tempNumBytesMap.put("latin1", Constants.integerValueOf(1));
        tempNumBytesMap.put("latin2", Constants.integerValueOf(1));
        tempNumBytesMap.put("swe7", Constants.integerValueOf(1));
        tempNumBytesMap.put("ascii", Constants.integerValueOf(1));
        tempNumBytesMap.put("ujis", Constants.integerValueOf(3));
        tempNumBytesMap.put("sjis", Constants.integerValueOf(2));
        tempNumBytesMap.put("hebrew", Constants.integerValueOf(1));
        tempNumBytesMap.put("tis620", Constants.integerValueOf(1));
        tempNumBytesMap.put("euckr", Constants.integerValueOf(2));
        tempNumBytesMap.put("koi8u", Constants.integerValueOf(1));
        tempNumBytesMap.put("gb2312", Constants.integerValueOf(2));
        tempNumBytesMap.put("greek", Constants.integerValueOf(1));
        tempNumBytesMap.put("cp1250", Constants.integerValueOf(1));
        tempNumBytesMap.put("gbk", Constants.integerValueOf(2));
        tempNumBytesMap.put("latin5", Constants.integerValueOf(1));
        tempNumBytesMap.put("armscii8", Constants.integerValueOf(1));
        tempNumBytesMap.put("utf8", Constants.integerValueOf(3));
        tempNumBytesMap.put("ucs2", Constants.integerValueOf(2));
        tempNumBytesMap.put("cp866", Constants.integerValueOf(1));
        tempNumBytesMap.put("keybcs2", Constants.integerValueOf(1));
        tempNumBytesMap.put("macce", Constants.integerValueOf(1));
        tempNumBytesMap.put("macroman", Constants.integerValueOf(1));
        tempNumBytesMap.put("cp852", Constants.integerValueOf(1));
        tempNumBytesMap.put("latin7", Constants.integerValueOf(1));
        tempNumBytesMap.put("cp1251", Constants.integerValueOf(1));
        tempNumBytesMap.put("cp1256", Constants.integerValueOf(1));
        tempNumBytesMap.put("cp1257", Constants.integerValueOf(1));
        tempNumBytesMap.put("binary", Constants.integerValueOf(1));
        tempNumBytesMap.put("geostd8", Constants.integerValueOf(1));
        tempNumBytesMap.put("cp932", Constants.integerValueOf(2));
        tempNumBytesMap.put("eucjpms", Constants.integerValueOf(3));
        tempNumBytesMap.put("utf8mb4", Constants.integerValueOf(4));
        STATIC_CHARSET_TO_NUM_BYTES_MAP = Collections.unmodifiableMap((Map<?, ?>)tempNumBytesMap);
        CharsetMapping.CHARSET_CONFIG.setProperty("javaToMysqlMappings", "US-ASCII =\t\t\tusa7,US-ASCII =\t\t\t>4.1.0 ascii,Big5 = \t\t\t\tbig5,GBK = \t\t\t\tgbk,SJIS = \t\t\t\tsjis,EUC_CN = \t\t\tgb2312,EUC_JP = \t\t\tujis,EUC_JP_Solaris = \t>5.0.3 eucjpms,EUC_KR = \t\t\teuc_kr,EUC_KR = \t\t\t>4.1.0 euckr,ISO8859_1 =\t\t\t*latin1,ISO8859_1 =\t\t\tlatin1_de,ISO8859_1 =\t\t\tgerman1,ISO8859_1 =\t\t\tdanish,ISO8859_2 =\t\t\tlatin2,ISO8859_2 =\t\t\tczech,ISO8859_2 =\t\t\thungarian,ISO8859_2  =\t\tcroat,ISO8859_7  =\t\tgreek,ISO8859_7  =\t\tlatin7,ISO8859_8  = \t\thebrew,ISO8859_9  =\t\tlatin5,ISO8859_13 =\t\tlatvian,ISO8859_13 =\t\tlatvian1,ISO8859_13 =\t\testonia,Cp437 =             *>4.1.0 cp850,Cp437 =\t\t\t\tdos,Cp850 =\t\t\t\tcp850,Cp852 = \t\t\tcp852,Cp866 = \t\t\tcp866,KOI8_R = \t\t\tkoi8_ru,KOI8_R = \t\t\t>4.1.0 koi8r,TIS620 = \t\t\ttis620,Cp1250 = \t\t\tcp1250,Cp1250 = \t\t\twin1250,Cp1251 = \t\t\t*>4.1.0 cp1251,Cp1251 = \t\t\twin1251,Cp1251 = \t\t\tcp1251cias,Cp1251 = \t\t\tcp1251csas,Cp1256 = \t\t\tcp1256,Cp1251 = \t\t\twin1251ukr,Cp1252 =             latin1,Cp1257 = \t\t\tcp1257,MacRoman = \t\t\tmacroman,MacCentralEurope = \tmacce,UTF-8 = \t\tutf8,UTF-8 =\t\t\t\t*> 5.5.2 utf8mb4,UnicodeBig = \tucs2,US-ASCII =\t\tbinary,Cp943 =        \tsjis,MS932 =\t\t\tsjis,MS932 =        \t>4.1.11 cp932,WINDOWS-31J =\tsjis,WINDOWS-31J = \t>4.1.11 cp932,CP932 =\t\t\tsjis,CP932 =\t\t\t*>4.1.11 cp932,SHIFT_JIS = \tsjis,ASCII =\t\t\tascii,LATIN5 =\t\tlatin5,LATIN7 =\t\tlatin7,HEBREW =\t\thebrew,GREEK =\t\t\tgreek,EUCKR =\t\t\teuckr,GB2312 =\t\tgb2312,LATIN2 =\t\tlatin2,UTF-16 = \t>5.2.0 utf16,UTF-32 = \t>5.2.0 utf32");
        final HashMap javaToMysqlMap = new HashMap();
        populateMapWithKeyValuePairs("javaToMysqlMappings", javaToMysqlMap, true, false);
        JAVA_TO_MYSQL_CHARSET_MAP = Collections.unmodifiableMap((Map<?, ?>)javaToMysqlMap);
        final HashMap mysqlToJavaMap = new HashMap();
        final Set keySet = CharsetMapping.JAVA_TO_MYSQL_CHARSET_MAP.keySet();
        for (final Object javaEncodingName : keySet) {
            final List mysqlEncodingList = CharsetMapping.JAVA_TO_MYSQL_CHARSET_MAP.get(javaEncodingName);
            final Iterator mysqlEncodings = mysqlEncodingList.iterator();
            String mysqlEncodingName = null;
            while (mysqlEncodings.hasNext()) {
                final VersionedStringProperty mysqlProp = mysqlEncodings.next();
                mysqlEncodingName = mysqlProp.toString();
                mysqlToJavaMap.put(mysqlEncodingName, javaEncodingName);
                mysqlToJavaMap.put(mysqlEncodingName.toUpperCase(Locale.ENGLISH), javaEncodingName);
            }
        }
        mysqlToJavaMap.put("cp932", "Windows-31J");
        mysqlToJavaMap.put("CP932", "Windows-31J");
        MYSQL_TO_JAVA_CHARSET_MAP = Collections.unmodifiableMap((Map<?, ?>)mysqlToJavaMap);
        final TreeMap ucMap = new TreeMap((Comparator<? super K>)String.CASE_INSENSITIVE_ORDER);
        for (final String key : CharsetMapping.JAVA_TO_MYSQL_CHARSET_MAP.keySet()) {
            ucMap.put(key.toUpperCase(Locale.ENGLISH), CharsetMapping.JAVA_TO_MYSQL_CHARSET_MAP.get(key));
        }
        JAVA_UC_TO_MYSQL_CHARSET_MAP = Collections.unmodifiableMap((Map<?, ?>)ucMap);
        final HashMap tempMapMulti = new HashMap();
        CharsetMapping.CHARSET_CONFIG.setProperty("multibyteCharsets", "Big5 = \t\t\tbig5,GBK = \t\t\tgbk,SJIS = \t\t\tsjis,EUC_CN = \t\tgb2312,EUC_JP = \t\tujis,EUC_JP_Solaris = eucjpms,EUC_KR = \t\teuc_kr,EUC_KR = \t\t>4.1.0 euckr,Cp943 =        \tsjis,Cp943 = \t\tcp943,WINDOWS-31J =\tsjis,WINDOWS-31J = \tcp932,CP932 =\t\t\tcp932,MS932 =\t\t\tsjis,MS932 =        \tcp932,SHIFT_JIS = \tsjis,EUCKR =\t\t\teuckr,GB2312 =\t\tgb2312,UTF-8 = \t\tutf8,utf8 =          utf8,UnicodeBig = \tucs2");
        populateMapWithKeyValuePairs("multibyteCharsets", tempMapMulti, false, true);
        MULTIBYTE_CHARSETS = Collections.unmodifiableMap((Map<?, ?>)tempMapMulti);
        INDEX_TO_CHARSET = new String[255];
        try {
            CharsetMapping.INDEX_TO_CHARSET[1] = getJavaEncodingForMysqlEncoding("big5", null);
            CharsetMapping.INDEX_TO_CHARSET[2] = getJavaEncodingForMysqlEncoding("czech", null);
            CharsetMapping.INDEX_TO_CHARSET[3] = "ISO8859_1";
            CharsetMapping.INDEX_TO_CHARSET[4] = "ISO8859_1";
            CharsetMapping.INDEX_TO_CHARSET[5] = getJavaEncodingForMysqlEncoding("german1", null);
            CharsetMapping.INDEX_TO_CHARSET[6] = "ISO8859_1";
            CharsetMapping.INDEX_TO_CHARSET[7] = getJavaEncodingForMysqlEncoding("koi8_ru", null);
            CharsetMapping.INDEX_TO_CHARSET[8] = getJavaEncodingForMysqlEncoding("latin1", null);
            CharsetMapping.INDEX_TO_CHARSET[9] = getJavaEncodingForMysqlEncoding("latin2", null);
            CharsetMapping.INDEX_TO_CHARSET[10] = "ISO8859_1";
            CharsetMapping.INDEX_TO_CHARSET[11] = getJavaEncodingForMysqlEncoding("usa7", null);
            CharsetMapping.INDEX_TO_CHARSET[12] = getJavaEncodingForMysqlEncoding("ujis", null);
            CharsetMapping.INDEX_TO_CHARSET[13] = getJavaEncodingForMysqlEncoding("sjis", null);
            CharsetMapping.INDEX_TO_CHARSET[14] = getJavaEncodingForMysqlEncoding("cp1251", null);
            CharsetMapping.INDEX_TO_CHARSET[15] = getJavaEncodingForMysqlEncoding("danish", null);
            CharsetMapping.INDEX_TO_CHARSET[16] = getJavaEncodingForMysqlEncoding("hebrew", null);
            CharsetMapping.INDEX_TO_CHARSET[17] = "ISO8859_1";
            CharsetMapping.INDEX_TO_CHARSET[18] = getJavaEncodingForMysqlEncoding("tis620", null);
            CharsetMapping.INDEX_TO_CHARSET[19] = getJavaEncodingForMysqlEncoding("euc_kr", null);
            CharsetMapping.INDEX_TO_CHARSET[20] = getJavaEncodingForMysqlEncoding("estonia", null);
            CharsetMapping.INDEX_TO_CHARSET[21] = getJavaEncodingForMysqlEncoding("hungarian", null);
            CharsetMapping.INDEX_TO_CHARSET[22] = "KOI8_R";
            CharsetMapping.INDEX_TO_CHARSET[23] = getJavaEncodingForMysqlEncoding("win1251ukr", null);
            CharsetMapping.INDEX_TO_CHARSET[24] = getJavaEncodingForMysqlEncoding("gb2312", null);
            CharsetMapping.INDEX_TO_CHARSET[25] = getJavaEncodingForMysqlEncoding("greek", null);
            CharsetMapping.INDEX_TO_CHARSET[26] = getJavaEncodingForMysqlEncoding("win1250", null);
            CharsetMapping.INDEX_TO_CHARSET[27] = getJavaEncodingForMysqlEncoding("croat", null);
            CharsetMapping.INDEX_TO_CHARSET[28] = getJavaEncodingForMysqlEncoding("gbk", null);
            CharsetMapping.INDEX_TO_CHARSET[29] = getJavaEncodingForMysqlEncoding("cp1257", null);
            CharsetMapping.INDEX_TO_CHARSET[30] = getJavaEncodingForMysqlEncoding("latin5", null);
            CharsetMapping.INDEX_TO_CHARSET[31] = getJavaEncodingForMysqlEncoding("latin1_de", null);
            CharsetMapping.INDEX_TO_CHARSET[32] = "ISO8859_1";
            CharsetMapping.INDEX_TO_CHARSET[33] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[34] = "Cp1250";
            CharsetMapping.INDEX_TO_CHARSET[35] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[36] = getJavaEncodingForMysqlEncoding("cp866", null);
            CharsetMapping.INDEX_TO_CHARSET[37] = "Cp895";
            CharsetMapping.INDEX_TO_CHARSET[38] = getJavaEncodingForMysqlEncoding("macce", null);
            CharsetMapping.INDEX_TO_CHARSET[39] = getJavaEncodingForMysqlEncoding("macroman", null);
            CharsetMapping.INDEX_TO_CHARSET[40] = "latin2";
            CharsetMapping.INDEX_TO_CHARSET[41] = getJavaEncodingForMysqlEncoding("latvian", null);
            CharsetMapping.INDEX_TO_CHARSET[42] = getJavaEncodingForMysqlEncoding("latvian1", null);
            CharsetMapping.INDEX_TO_CHARSET[43] = getJavaEncodingForMysqlEncoding("macce", null);
            CharsetMapping.INDEX_TO_CHARSET[44] = getJavaEncodingForMysqlEncoding("macce", null);
            CharsetMapping.INDEX_TO_CHARSET[45] = getJavaEncodingForMysqlEncoding("utf8mb4", null);
            CharsetMapping.INDEX_TO_CHARSET[46] = getJavaEncodingForMysqlEncoding("utf8mb4", null);
            CharsetMapping.INDEX_TO_CHARSET[47] = getJavaEncodingForMysqlEncoding("latin1", null);
            CharsetMapping.INDEX_TO_CHARSET[48] = getJavaEncodingForMysqlEncoding("latin1", null);
            CharsetMapping.INDEX_TO_CHARSET[49] = getJavaEncodingForMysqlEncoding("latin1", null);
            CharsetMapping.INDEX_TO_CHARSET[50] = getJavaEncodingForMysqlEncoding("cp1251", null);
            CharsetMapping.INDEX_TO_CHARSET[51] = getJavaEncodingForMysqlEncoding("cp1251", null);
            CharsetMapping.INDEX_TO_CHARSET[52] = getJavaEncodingForMysqlEncoding("cp1251", null);
            CharsetMapping.INDEX_TO_CHARSET[53] = getJavaEncodingForMysqlEncoding("macroman", null);
            CharsetMapping.INDEX_TO_CHARSET[54] = getJavaEncodingForMysqlEncoding("macroman", null);
            CharsetMapping.INDEX_TO_CHARSET[55] = getJavaEncodingForMysqlEncoding("macroman", null);
            CharsetMapping.INDEX_TO_CHARSET[56] = getJavaEncodingForMysqlEncoding("macroman", null);
            CharsetMapping.INDEX_TO_CHARSET[57] = getJavaEncodingForMysqlEncoding("cp1256", null);
            CharsetMapping.INDEX_TO_CHARSET[58] = "ISO8859_1";
            CharsetMapping.INDEX_TO_CHARSET[59] = "ISO8859_1";
            CharsetMapping.INDEX_TO_CHARSET[60] = "ISO8859_1";
            CharsetMapping.INDEX_TO_CHARSET[61] = "ISO8859_1";
            CharsetMapping.INDEX_TO_CHARSET[62] = "ISO8859_1";
            CharsetMapping.INDEX_TO_CHARSET[63] = getJavaEncodingForMysqlEncoding("binary", null);
            CharsetMapping.INDEX_TO_CHARSET[64] = "ISO8859_2";
            CharsetMapping.INDEX_TO_CHARSET[65] = getJavaEncodingForMysqlEncoding("ascii", null);
            CharsetMapping.INDEX_TO_CHARSET[66] = getJavaEncodingForMysqlEncoding("cp1250", null);
            CharsetMapping.INDEX_TO_CHARSET[67] = getJavaEncodingForMysqlEncoding("cp1256", null);
            CharsetMapping.INDEX_TO_CHARSET[68] = getJavaEncodingForMysqlEncoding("cp866", null);
            CharsetMapping.INDEX_TO_CHARSET[69] = "US-ASCII";
            CharsetMapping.INDEX_TO_CHARSET[70] = getJavaEncodingForMysqlEncoding("greek", null);
            CharsetMapping.INDEX_TO_CHARSET[71] = getJavaEncodingForMysqlEncoding("hebrew", null);
            CharsetMapping.INDEX_TO_CHARSET[72] = "US-ASCII";
            CharsetMapping.INDEX_TO_CHARSET[73] = "Cp895";
            CharsetMapping.INDEX_TO_CHARSET[74] = getJavaEncodingForMysqlEncoding("koi8r", null);
            CharsetMapping.INDEX_TO_CHARSET[75] = "KOI8_r";
            CharsetMapping.INDEX_TO_CHARSET[76] = "ISO8859_1";
            CharsetMapping.INDEX_TO_CHARSET[77] = getJavaEncodingForMysqlEncoding("latin2", null);
            CharsetMapping.INDEX_TO_CHARSET[78] = getJavaEncodingForMysqlEncoding("latin5", null);
            CharsetMapping.INDEX_TO_CHARSET[79] = getJavaEncodingForMysqlEncoding("latin7", null);
            CharsetMapping.INDEX_TO_CHARSET[80] = getJavaEncodingForMysqlEncoding("cp850", null);
            CharsetMapping.INDEX_TO_CHARSET[81] = getJavaEncodingForMysqlEncoding("cp852", null);
            CharsetMapping.INDEX_TO_CHARSET[82] = "ISO8859_1";
            CharsetMapping.INDEX_TO_CHARSET[83] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[84] = getJavaEncodingForMysqlEncoding("big5", null);
            CharsetMapping.INDEX_TO_CHARSET[85] = getJavaEncodingForMysqlEncoding("euckr", null);
            CharsetMapping.INDEX_TO_CHARSET[86] = getJavaEncodingForMysqlEncoding("gb2312", null);
            CharsetMapping.INDEX_TO_CHARSET[87] = getJavaEncodingForMysqlEncoding("gbk", null);
            CharsetMapping.INDEX_TO_CHARSET[88] = getJavaEncodingForMysqlEncoding("sjis", null);
            CharsetMapping.INDEX_TO_CHARSET[89] = getJavaEncodingForMysqlEncoding("tis620", null);
            CharsetMapping.INDEX_TO_CHARSET[90] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[91] = getJavaEncodingForMysqlEncoding("ujis", null);
            CharsetMapping.INDEX_TO_CHARSET[92] = "US-ASCII";
            CharsetMapping.INDEX_TO_CHARSET[93] = "US-ASCII";
            CharsetMapping.INDEX_TO_CHARSET[94] = getJavaEncodingForMysqlEncoding("latin1", null);
            CharsetMapping.INDEX_TO_CHARSET[95] = getJavaEncodingForMysqlEncoding("cp932", null);
            CharsetMapping.INDEX_TO_CHARSET[96] = getJavaEncodingForMysqlEncoding("cp932", null);
            CharsetMapping.INDEX_TO_CHARSET[97] = getJavaEncodingForMysqlEncoding("eucjpms", null);
            CharsetMapping.INDEX_TO_CHARSET[98] = getJavaEncodingForMysqlEncoding("eucjpms", null);
            for (int i = 99; i < 128; ++i) {
                CharsetMapping.INDEX_TO_CHARSET[i] = "ISO8859_1";
            }
            CharsetMapping.INDEX_TO_CHARSET[128] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[129] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[130] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[131] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[132] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[133] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[134] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[135] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[136] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[137] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[138] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[139] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[140] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[141] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[142] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[143] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[144] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[145] = getJavaEncodingForMysqlEncoding("ucs2", null);
            CharsetMapping.INDEX_TO_CHARSET[146] = getJavaEncodingForMysqlEncoding("ucs2", null);
            for (int i = 147; i < 192; ++i) {
                CharsetMapping.INDEX_TO_CHARSET[i] = "ISO8859_1";
            }
            CharsetMapping.INDEX_TO_CHARSET[192] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[193] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[194] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[195] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[196] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[197] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[198] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[199] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[200] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[201] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[202] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[203] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[204] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[205] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[206] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[207] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[208] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[209] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[210] = getJavaEncodingForMysqlEncoding("utf8", null);
            CharsetMapping.INDEX_TO_CHARSET[211] = getJavaEncodingForMysqlEncoding("utf8", null);
            for (int i = 212; i < 224; ++i) {
                CharsetMapping.INDEX_TO_CHARSET[i] = "ISO8859_1";
            }
            for (int i = 224; i <= 243; ++i) {
                CharsetMapping.INDEX_TO_CHARSET[i] = getJavaEncodingForMysqlEncoding("utf8", null);
            }
            for (int i = 101; i <= 120; ++i) {
                CharsetMapping.INDEX_TO_CHARSET[i] = getJavaEncodingForMysqlEncoding("utf16", null);
            }
            for (int i = 160; i <= 179; ++i) {
                CharsetMapping.INDEX_TO_CHARSET[i] = getJavaEncodingForMysqlEncoding("utf32", null);
            }
            for (int i = 244; i < 254; ++i) {
                CharsetMapping.INDEX_TO_CHARSET[i] = "ISO8859_1";
            }
            CharsetMapping.INDEX_TO_CHARSET[254] = getJavaEncodingForMysqlEncoding("utf8", null);
            for (int i = 1; i < CharsetMapping.INDEX_TO_CHARSET.length; ++i) {
                if (CharsetMapping.INDEX_TO_CHARSET[i] == null) {
                    throw new RuntimeException("Assertion failure: No mapping from charset index " + i + " to a Java character set");
                }
            }
        }
        catch (SQLException ex) {}
        (INDEX_TO_COLLATION = new String[255])[1] = "big5_chinese_ci";
        CharsetMapping.INDEX_TO_COLLATION[2] = "latin2_czech_cs";
        CharsetMapping.INDEX_TO_COLLATION[3] = "dec8_swedish_ci";
        CharsetMapping.INDEX_TO_COLLATION[4] = "cp850_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[5] = "latin1_german1_ci";
        CharsetMapping.INDEX_TO_COLLATION[6] = "hp8_english_ci";
        CharsetMapping.INDEX_TO_COLLATION[7] = "koi8r_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[8] = "latin1_swedish_ci";
        CharsetMapping.INDEX_TO_COLLATION[9] = "latin2_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[10] = "swe7_swedish_ci";
        CharsetMapping.INDEX_TO_COLLATION[11] = "ascii_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[12] = "ujis_japanese_ci";
        CharsetMapping.INDEX_TO_COLLATION[13] = "sjis_japanese_ci";
        CharsetMapping.INDEX_TO_COLLATION[14] = "cp1251_bulgarian_ci";
        CharsetMapping.INDEX_TO_COLLATION[15] = "latin1_danish_ci";
        CharsetMapping.INDEX_TO_COLLATION[16] = "hebrew_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[18] = "tis620_thai_ci";
        CharsetMapping.INDEX_TO_COLLATION[19] = "euckr_korean_ci";
        CharsetMapping.INDEX_TO_COLLATION[20] = "latin7_estonian_cs";
        CharsetMapping.INDEX_TO_COLLATION[21] = "latin2_hungarian_ci";
        CharsetMapping.INDEX_TO_COLLATION[22] = "koi8u_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[23] = "cp1251_ukrainian_ci";
        CharsetMapping.INDEX_TO_COLLATION[24] = "gb2312_chinese_ci";
        CharsetMapping.INDEX_TO_COLLATION[25] = "greek_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[26] = "cp1250_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[27] = "latin2_croatian_ci";
        CharsetMapping.INDEX_TO_COLLATION[28] = "gbk_chinese_ci";
        CharsetMapping.INDEX_TO_COLLATION[29] = "cp1257_lithuanian_ci";
        CharsetMapping.INDEX_TO_COLLATION[30] = "latin5_turkish_ci";
        CharsetMapping.INDEX_TO_COLLATION[31] = "latin1_german2_ci";
        CharsetMapping.INDEX_TO_COLLATION[32] = "armscii8_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[33] = "utf8_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[34] = "cp1250_czech_cs";
        CharsetMapping.INDEX_TO_COLLATION[35] = "ucs2_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[36] = "cp866_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[37] = "keybcs2_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[38] = "macce_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[39] = "macroman_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[40] = "cp852_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[41] = "latin7_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[42] = "latin7_general_cs";
        CharsetMapping.INDEX_TO_COLLATION[43] = "macce_bin";
        CharsetMapping.INDEX_TO_COLLATION[44] = "cp1250_croatian_ci";
        CharsetMapping.INDEX_TO_COLLATION[45] = "utf8mb4_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[46] = "utf8mb4_bin";
        CharsetMapping.INDEX_TO_COLLATION[47] = "latin1_bin";
        CharsetMapping.INDEX_TO_COLLATION[48] = "latin1_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[49] = "latin1_general_cs";
        CharsetMapping.INDEX_TO_COLLATION[50] = "cp1251_bin";
        CharsetMapping.INDEX_TO_COLLATION[51] = "cp1251_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[52] = "cp1251_general_cs";
        CharsetMapping.INDEX_TO_COLLATION[53] = "macroman_bin";
        CharsetMapping.INDEX_TO_COLLATION[57] = "cp1256_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[58] = "cp1257_bin";
        CharsetMapping.INDEX_TO_COLLATION[59] = "cp1257_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[63] = "binary";
        CharsetMapping.INDEX_TO_COLLATION[64] = "armscii8_bin";
        CharsetMapping.INDEX_TO_COLLATION[65] = "ascii_bin";
        CharsetMapping.INDEX_TO_COLLATION[66] = "cp1250_bin";
        CharsetMapping.INDEX_TO_COLLATION[67] = "cp1256_bin";
        CharsetMapping.INDEX_TO_COLLATION[68] = "cp866_bin";
        CharsetMapping.INDEX_TO_COLLATION[69] = "dec8_bin";
        CharsetMapping.INDEX_TO_COLLATION[70] = "greek_bin";
        CharsetMapping.INDEX_TO_COLLATION[71] = "hebrew_bin";
        CharsetMapping.INDEX_TO_COLLATION[72] = "hp8_bin";
        CharsetMapping.INDEX_TO_COLLATION[73] = "keybcs2_bin";
        CharsetMapping.INDEX_TO_COLLATION[74] = "koi8r_bin";
        CharsetMapping.INDEX_TO_COLLATION[75] = "koi8u_bin";
        CharsetMapping.INDEX_TO_COLLATION[77] = "latin2_bin";
        CharsetMapping.INDEX_TO_COLLATION[78] = "latin5_bin";
        CharsetMapping.INDEX_TO_COLLATION[79] = "latin7_bin";
        CharsetMapping.INDEX_TO_COLLATION[80] = "cp850_bin";
        CharsetMapping.INDEX_TO_COLLATION[81] = "cp852_bin";
        CharsetMapping.INDEX_TO_COLLATION[82] = "swe7_bin";
        CharsetMapping.INDEX_TO_COLLATION[83] = "utf8_bin";
        CharsetMapping.INDEX_TO_COLLATION[84] = "big5_bin";
        CharsetMapping.INDEX_TO_COLLATION[85] = "euckr_bin";
        CharsetMapping.INDEX_TO_COLLATION[86] = "gb2312_bin";
        CharsetMapping.INDEX_TO_COLLATION[87] = "gbk_bin";
        CharsetMapping.INDEX_TO_COLLATION[88] = "sjis_bin";
        CharsetMapping.INDEX_TO_COLLATION[89] = "tis620_bin";
        CharsetMapping.INDEX_TO_COLLATION[90] = "ucs2_bin";
        CharsetMapping.INDEX_TO_COLLATION[91] = "ujis_bin";
        CharsetMapping.INDEX_TO_COLLATION[92] = "geostd8_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[93] = "geostd8_bin";
        CharsetMapping.INDEX_TO_COLLATION[94] = "latin1_spanish_ci";
        CharsetMapping.INDEX_TO_COLLATION[95] = "cp932_japanese_ci";
        CharsetMapping.INDEX_TO_COLLATION[96] = "cp932_bin";
        CharsetMapping.INDEX_TO_COLLATION[97] = "eucjpms_japanese_ci";
        CharsetMapping.INDEX_TO_COLLATION[98] = "eucjpms_bin";
        CharsetMapping.INDEX_TO_COLLATION[99] = "cp1250_polish_ci";
        CharsetMapping.INDEX_TO_COLLATION[128] = "ucs2_unicode_ci";
        CharsetMapping.INDEX_TO_COLLATION[129] = "ucs2_icelandic_ci";
        CharsetMapping.INDEX_TO_COLLATION[130] = "ucs2_latvian_ci";
        CharsetMapping.INDEX_TO_COLLATION[131] = "ucs2_romanian_ci";
        CharsetMapping.INDEX_TO_COLLATION[132] = "ucs2_slovenian_ci";
        CharsetMapping.INDEX_TO_COLLATION[133] = "ucs2_polish_ci";
        CharsetMapping.INDEX_TO_COLLATION[134] = "ucs2_estonian_ci";
        CharsetMapping.INDEX_TO_COLLATION[135] = "ucs2_spanish_ci";
        CharsetMapping.INDEX_TO_COLLATION[136] = "ucs2_swedish_ci";
        CharsetMapping.INDEX_TO_COLLATION[137] = "ucs2_turkish_ci";
        CharsetMapping.INDEX_TO_COLLATION[138] = "ucs2_czech_ci";
        CharsetMapping.INDEX_TO_COLLATION[139] = "ucs2_danish_ci";
        CharsetMapping.INDEX_TO_COLLATION[140] = "ucs2_lithuanian_ci ";
        CharsetMapping.INDEX_TO_COLLATION[141] = "ucs2_slovak_ci";
        CharsetMapping.INDEX_TO_COLLATION[142] = "ucs2_spanish2_ci";
        CharsetMapping.INDEX_TO_COLLATION[143] = "ucs2_roman_ci";
        CharsetMapping.INDEX_TO_COLLATION[144] = "ucs2_persian_ci";
        CharsetMapping.INDEX_TO_COLLATION[145] = "ucs2_esperanto_ci";
        CharsetMapping.INDEX_TO_COLLATION[146] = "ucs2_hungarian_ci";
        CharsetMapping.INDEX_TO_COLLATION[192] = "utf8_unicode_ci";
        CharsetMapping.INDEX_TO_COLLATION[193] = "utf8_icelandic_ci";
        CharsetMapping.INDEX_TO_COLLATION[194] = "utf8_latvian_ci";
        CharsetMapping.INDEX_TO_COLLATION[195] = "utf8_romanian_ci";
        CharsetMapping.INDEX_TO_COLLATION[196] = "utf8_slovenian_ci";
        CharsetMapping.INDEX_TO_COLLATION[197] = "utf8_polish_ci";
        CharsetMapping.INDEX_TO_COLLATION[198] = "utf8_estonian_ci";
        CharsetMapping.INDEX_TO_COLLATION[199] = "utf8_spanish_ci";
        CharsetMapping.INDEX_TO_COLLATION[200] = "utf8_swedish_ci";
        CharsetMapping.INDEX_TO_COLLATION[201] = "utf8_turkish_ci";
        CharsetMapping.INDEX_TO_COLLATION[202] = "utf8_czech_ci";
        CharsetMapping.INDEX_TO_COLLATION[203] = "utf8_danish_ci";
        CharsetMapping.INDEX_TO_COLLATION[204] = "utf8_lithuanian_ci ";
        CharsetMapping.INDEX_TO_COLLATION[205] = "utf8_slovak_ci";
        CharsetMapping.INDEX_TO_COLLATION[206] = "utf8_spanish2_ci";
        CharsetMapping.INDEX_TO_COLLATION[207] = "utf8_roman_ci";
        CharsetMapping.INDEX_TO_COLLATION[208] = "utf8_persian_ci";
        CharsetMapping.INDEX_TO_COLLATION[209] = "utf8_esperanto_ci";
        CharsetMapping.INDEX_TO_COLLATION[210] = "utf8_hungarian_ci";
        CharsetMapping.INDEX_TO_COLLATION[33] = "utf8mb3_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[83] = "utf8mb3_bin";
        CharsetMapping.INDEX_TO_COLLATION[192] = "utf8mb3_unicode_ci";
        CharsetMapping.INDEX_TO_COLLATION[193] = "utf8mb3_icelandic_ci";
        CharsetMapping.INDEX_TO_COLLATION[194] = "utf8mb3_latvian_ci";
        CharsetMapping.INDEX_TO_COLLATION[195] = "utf8mb3_romanian_ci";
        CharsetMapping.INDEX_TO_COLLATION[196] = "utf8mb3_slovenian_ci";
        CharsetMapping.INDEX_TO_COLLATION[197] = "utf8mb3_polish_ci";
        CharsetMapping.INDEX_TO_COLLATION[198] = "utf8mb3_estonian_ci";
        CharsetMapping.INDEX_TO_COLLATION[199] = "utf8mb3_spanish_ci";
        CharsetMapping.INDEX_TO_COLLATION[200] = "utf8mb3_swedish_ci";
        CharsetMapping.INDEX_TO_COLLATION[201] = "utf8mb3_turkish_ci";
        CharsetMapping.INDEX_TO_COLLATION[202] = "utf8mb3_czech_ci";
        CharsetMapping.INDEX_TO_COLLATION[203] = "utf8mb3_danish_ci";
        CharsetMapping.INDEX_TO_COLLATION[204] = "utf8mb3_lithuanian_ci";
        CharsetMapping.INDEX_TO_COLLATION[205] = "utf8mb3_slovak_ci";
        CharsetMapping.INDEX_TO_COLLATION[206] = "utf8mb3_spanish2_ci";
        CharsetMapping.INDEX_TO_COLLATION[207] = "utf8mb3_roman_ci";
        CharsetMapping.INDEX_TO_COLLATION[208] = "utf8mb3_persian_ci";
        CharsetMapping.INDEX_TO_COLLATION[209] = "utf8mb3_esperanto_ci";
        CharsetMapping.INDEX_TO_COLLATION[210] = "utf8mb3_hungarian_ci";
        CharsetMapping.INDEX_TO_COLLATION[211] = "utf8mb3_sinhala_ci";
        CharsetMapping.INDEX_TO_COLLATION[254] = "utf8mb3_general_cs";
        CharsetMapping.INDEX_TO_COLLATION[54] = "utf16_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[55] = "utf16_bin";
        CharsetMapping.INDEX_TO_COLLATION[101] = "utf16_unicode_ci";
        CharsetMapping.INDEX_TO_COLLATION[102] = "utf16_icelandic_ci";
        CharsetMapping.INDEX_TO_COLLATION[103] = "utf16_latvian_ci";
        CharsetMapping.INDEX_TO_COLLATION[104] = "utf16_romanian_ci";
        CharsetMapping.INDEX_TO_COLLATION[105] = "utf16_slovenian_ci";
        CharsetMapping.INDEX_TO_COLLATION[106] = "utf16_polish_ci";
        CharsetMapping.INDEX_TO_COLLATION[107] = "utf16_estonian_ci";
        CharsetMapping.INDEX_TO_COLLATION[108] = "utf16_spanish_ci";
        CharsetMapping.INDEX_TO_COLLATION[109] = "utf16_swedish_ci";
        CharsetMapping.INDEX_TO_COLLATION[110] = "utf16_turkish_ci";
        CharsetMapping.INDEX_TO_COLLATION[111] = "utf16_czech_ci";
        CharsetMapping.INDEX_TO_COLLATION[112] = "utf16_danish_ci";
        CharsetMapping.INDEX_TO_COLLATION[113] = "utf16_lithuanian_ci";
        CharsetMapping.INDEX_TO_COLLATION[114] = "utf16_slovak_ci";
        CharsetMapping.INDEX_TO_COLLATION[115] = "utf16_spanish2_ci";
        CharsetMapping.INDEX_TO_COLLATION[116] = "utf16_roman_ci";
        CharsetMapping.INDEX_TO_COLLATION[117] = "utf16_persian_ci";
        CharsetMapping.INDEX_TO_COLLATION[118] = "utf16_esperanto_ci";
        CharsetMapping.INDEX_TO_COLLATION[119] = "utf16_hungarian_ci";
        CharsetMapping.INDEX_TO_COLLATION[120] = "utf16_sinhala_ci";
        CharsetMapping.INDEX_TO_COLLATION[60] = "utf32_general_ci";
        CharsetMapping.INDEX_TO_COLLATION[61] = "utf32_bin";
        CharsetMapping.INDEX_TO_COLLATION[160] = "utf32_unicode_ci";
        CharsetMapping.INDEX_TO_COLLATION[161] = "utf32_icelandic_ci";
        CharsetMapping.INDEX_TO_COLLATION[162] = "utf32_latvian_ci";
        CharsetMapping.INDEX_TO_COLLATION[163] = "utf32_romanian_ci";
        CharsetMapping.INDEX_TO_COLLATION[164] = "utf32_slovenian_ci";
        CharsetMapping.INDEX_TO_COLLATION[165] = "utf32_polish_ci";
        CharsetMapping.INDEX_TO_COLLATION[166] = "utf32_estonian_ci";
        CharsetMapping.INDEX_TO_COLLATION[167] = "utf32_spanish_ci";
        CharsetMapping.INDEX_TO_COLLATION[168] = "utf32_swedish_ci";
        CharsetMapping.INDEX_TO_COLLATION[169] = "utf32_turkish_ci";
        CharsetMapping.INDEX_TO_COLLATION[170] = "utf32_czech_ci";
        CharsetMapping.INDEX_TO_COLLATION[171] = "utf32_danish_ci";
        CharsetMapping.INDEX_TO_COLLATION[172] = "utf32_lithuanian_ci";
        CharsetMapping.INDEX_TO_COLLATION[173] = "utf32_slovak_ci";
        CharsetMapping.INDEX_TO_COLLATION[174] = "utf32_spanish2_ci";
        CharsetMapping.INDEX_TO_COLLATION[175] = "utf32_roman_ci";
        CharsetMapping.INDEX_TO_COLLATION[176] = "utf32_persian_ci";
        CharsetMapping.INDEX_TO_COLLATION[177] = "utf32_esperanto_ci";
        CharsetMapping.INDEX_TO_COLLATION[178] = "utf32_hungarian_ci";
        CharsetMapping.INDEX_TO_COLLATION[179] = "utf32_sinhala_ci";
        CharsetMapping.INDEX_TO_COLLATION[224] = "utf8mb4_unicode_ci";
        CharsetMapping.INDEX_TO_COLLATION[225] = "utf8mb4_icelandic_ci";
        CharsetMapping.INDEX_TO_COLLATION[226] = "utf8mb4_latvian_ci";
        CharsetMapping.INDEX_TO_COLLATION[227] = "utf8mb4_romanian_ci";
        CharsetMapping.INDEX_TO_COLLATION[228] = "utf8mb4_slovenian_ci";
        CharsetMapping.INDEX_TO_COLLATION[229] = "utf8mb4_polish_ci";
        CharsetMapping.INDEX_TO_COLLATION[230] = "utf8mb4_estonian_ci";
        CharsetMapping.INDEX_TO_COLLATION[231] = "utf8mb4_spanish_ci";
        CharsetMapping.INDEX_TO_COLLATION[232] = "utf8mb4_swedish_ci";
        CharsetMapping.INDEX_TO_COLLATION[233] = "utf8mb4_turkish_ci";
        CharsetMapping.INDEX_TO_COLLATION[234] = "utf8mb4_czech_ci";
        CharsetMapping.INDEX_TO_COLLATION[235] = "utf8mb4_danish_ci";
        CharsetMapping.INDEX_TO_COLLATION[236] = "utf8mb4_lithuanian_ci";
        CharsetMapping.INDEX_TO_COLLATION[237] = "utf8mb4_slovak_ci";
        CharsetMapping.INDEX_TO_COLLATION[238] = "utf8mb4_spanish2_ci";
        CharsetMapping.INDEX_TO_COLLATION[239] = "utf8mb4_roman_ci";
        CharsetMapping.INDEX_TO_COLLATION[240] = "utf8mb4_persian_ci";
        CharsetMapping.INDEX_TO_COLLATION[241] = "utf8mb4_esperanto_ci";
        CharsetMapping.INDEX_TO_COLLATION[242] = "utf8mb4_hungarian_ci";
        CharsetMapping.INDEX_TO_COLLATION[243] = "utf8mb4_sinhala_ci";
        CharsetMapping.INDEX_TO_COLLATION[244] = "utf8mb4_german2_ci";
        final Map indexMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (int j = 0; j < CharsetMapping.INDEX_TO_CHARSET.length; ++j) {
            final String mysqlEncodingName2 = CharsetMapping.INDEX_TO_CHARSET[j];
            if (mysqlEncodingName2 != null) {
                indexMap.put(CharsetMapping.INDEX_TO_CHARSET[j], Constants.integerValueOf(j));
            }
        }
        MYSQL_ENCODING_NAME_TO_CHARSET_INDEX_MAP = Collections.unmodifiableMap((Map<?, ?>)indexMap);
        final Map tempMap = new HashMap();
        tempMap.put("czech", "latin2");
        tempMap.put("danish", "latin1");
        tempMap.put("dutch", "latin1");
        tempMap.put("english", "latin1");
        tempMap.put("estonian", "latin7");
        tempMap.put("french", "latin1");
        tempMap.put("german", "latin1");
        tempMap.put("greek", "greek");
        tempMap.put("hungarian", "latin2");
        tempMap.put("italian", "latin1");
        tempMap.put("japanese", "ujis");
        tempMap.put("japanese-sjis", "sjis");
        tempMap.put("korean", "euckr");
        tempMap.put("norwegian", "latin1");
        tempMap.put("norwegian-ny", "latin1");
        tempMap.put("polish", "latin2");
        tempMap.put("portuguese", "latin1");
        tempMap.put("romanian", "latin2");
        tempMap.put("russian", "koi8r");
        tempMap.put("serbian", "cp1250");
        tempMap.put("slovak", "latin2");
        tempMap.put("spanish", "latin1");
        tempMap.put("swedish", "latin1");
        tempMap.put("ukrainian", "koi8u");
        ERROR_MESSAGE_FILE_TO_MYSQL_CHARSET_MAP = Collections.unmodifiableMap((Map<?, ?>)tempMap);
    }
}
