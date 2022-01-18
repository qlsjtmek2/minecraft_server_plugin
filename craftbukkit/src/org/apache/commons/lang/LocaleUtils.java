// 
// Decompiled by Procyon v0.5.30
// 

package org.apache.commons.lang;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.List;

public class LocaleUtils
{
    private static final List cAvailableLocaleList;
    private static Set cAvailableLocaleSet;
    private static final Map cLanguagesByCountry;
    private static final Map cCountriesByLanguage;
    
    public static Locale toLocale(final String str) {
        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len != 2 && len != 5 && len < 7) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        final char ch0 = str.charAt(0);
        final char ch2 = str.charAt(1);
        if (ch0 < 'a' || ch0 > 'z' || ch2 < 'a' || ch2 > 'z') {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        if (len == 2) {
            return new Locale(str, "");
        }
        if (str.charAt(2) != '_') {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        final char ch3 = str.charAt(3);
        final char ch4 = str.charAt(4);
        if (ch3 < 'A' || ch3 > 'Z' || ch4 < 'A' || ch4 > 'Z') {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        if (len == 5) {
            return new Locale(str.substring(0, 2), str.substring(3, 5));
        }
        if (str.charAt(5) != '_') {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        return new Locale(str.substring(0, 2), str.substring(3, 5), str.substring(6));
    }
    
    public static List localeLookupList(final Locale locale) {
        return localeLookupList(locale, locale);
    }
    
    public static List localeLookupList(final Locale locale, final Locale defaultLocale) {
        final List list = new ArrayList(4);
        if (locale != null) {
            list.add(locale);
            if (locale.getVariant().length() > 0) {
                list.add(new Locale(locale.getLanguage(), locale.getCountry()));
            }
            if (locale.getCountry().length() > 0) {
                list.add(new Locale(locale.getLanguage(), ""));
            }
            if (!list.contains(defaultLocale)) {
                list.add(defaultLocale);
            }
        }
        return Collections.unmodifiableList((List<?>)list);
    }
    
    public static List availableLocaleList() {
        return LocaleUtils.cAvailableLocaleList;
    }
    
    public static Set availableLocaleSet() {
        Set set = LocaleUtils.cAvailableLocaleSet;
        if (set == null) {
            set = new HashSet(availableLocaleList());
            set = (LocaleUtils.cAvailableLocaleSet = Collections.unmodifiableSet((Set<?>)set));
        }
        return set;
    }
    
    public static boolean isAvailableLocale(final Locale locale) {
        return availableLocaleList().contains(locale);
    }
    
    public static List languagesByCountry(final String countryCode) {
        List langs = LocaleUtils.cLanguagesByCountry.get(countryCode);
        if (langs == null) {
            if (countryCode != null) {
                langs = new ArrayList();
                final List locales = availableLocaleList();
                for (int i = 0; i < locales.size(); ++i) {
                    final Locale locale = locales.get(i);
                    if (countryCode.equals(locale.getCountry()) && locale.getVariant().length() == 0) {
                        langs.add(locale);
                    }
                }
                langs = Collections.unmodifiableList((List<?>)langs);
            }
            else {
                langs = Collections.EMPTY_LIST;
            }
            LocaleUtils.cLanguagesByCountry.put(countryCode, langs);
        }
        return langs;
    }
    
    public static List countriesByLanguage(final String languageCode) {
        List countries = LocaleUtils.cCountriesByLanguage.get(languageCode);
        if (countries == null) {
            if (languageCode != null) {
                countries = new ArrayList();
                final List locales = availableLocaleList();
                for (int i = 0; i < locales.size(); ++i) {
                    final Locale locale = locales.get(i);
                    if (languageCode.equals(locale.getLanguage()) && locale.getCountry().length() != 0 && locale.getVariant().length() == 0) {
                        countries.add(locale);
                    }
                }
                countries = Collections.unmodifiableList((List<?>)countries);
            }
            else {
                countries = Collections.EMPTY_LIST;
            }
            LocaleUtils.cCountriesByLanguage.put(languageCode, countries);
        }
        return countries;
    }
    
    static {
        cLanguagesByCountry = Collections.synchronizedMap(new HashMap<Object, Object>());
        cCountriesByLanguage = Collections.synchronizedMap(new HashMap<Object, Object>());
        final List list = Arrays.asList(Locale.getAvailableLocales());
        cAvailableLocaleList = Collections.unmodifiableList((List<?>)list);
    }
}
