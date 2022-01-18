// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline.console;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import java.net.URL;
import java.util.Map;

public class ConsoleKeys
{
    private KeyMap keys;
    private boolean viEditMode;
    private Map<String, KeyMap> keyMaps;
    
    public ConsoleKeys(final String appName, final URL inputrcUrl) {
        this.keyMaps = KeyMap.keyMaps();
        this.loadKeys(appName, inputrcUrl);
    }
    
    protected Map<String, KeyMap> getKeyMaps() {
        return this.keyMaps;
    }
    
    protected KeyMap getKeys() {
        return this.keys;
    }
    
    protected void setKeys(final KeyMap keys) {
        this.keys = keys;
    }
    
    protected boolean getViEditMode() {
        return this.viEditMode;
    }
    
    protected void setViEditMode(final boolean viEditMode) {
        this.viEditMode = viEditMode;
    }
    
    protected void loadKeys(final String appName, final URL inputrcUrl) {
        this.keys = this.keyMaps.get("emacs");
        try {
            final InputStream input = inputrcUrl.openStream();
            try {
                this.loadKeys(input, appName);
                Log.debug("Loaded user configuration: ", inputrcUrl);
            }
            finally {
                try {
                    input.close();
                }
                catch (IOException ex) {}
            }
        }
        catch (IOException e) {
            if (inputrcUrl.getProtocol().equals("file")) {
                final File file = new File(inputrcUrl.getPath());
                if (file.exists()) {
                    Log.warn("Unable to read user configuration: ", inputrcUrl, e);
                }
            }
            else {
                Log.warn("Unable to read user configuration: ", inputrcUrl, e);
            }
        }
        this.keys.bindArrowKeys();
        this.keys = (this.viEditMode ? this.keyMaps.get("vi") : this.keyMaps.get("emacs"));
    }
    
    private void loadKeys(final InputStream input, final String appName) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        boolean parsing = true;
        final List<Boolean> ifsStack = new ArrayList<Boolean>();
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.length() == 0) {
                continue;
            }
            if (line.charAt(0) == '#') {
                continue;
            }
            int i = 0;
            if (line.charAt(i) == '$') {
                ++i;
                while (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
                    ++i;
                }
                int s = i;
                while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
                    ++i;
                }
                final String cmd = line.substring(s, i);
                while (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
                    ++i;
                }
                s = i;
                while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
                    ++i;
                }
                final String args = line.substring(s, i);
                if ("if".equalsIgnoreCase(cmd)) {
                    ifsStack.add(parsing);
                    if (!parsing) {
                        continue;
                    }
                    if (args.startsWith("term=")) {
                        continue;
                    }
                    if (args.startsWith("mode=")) {
                        if (args.equalsIgnoreCase("mode=vi")) {
                            parsing = this.viEditMode;
                        }
                        else {
                            parsing = (args.equals("mode=emacs") && !this.viEditMode);
                        }
                    }
                    else {
                        parsing = args.equalsIgnoreCase(appName);
                    }
                }
                else if ("else".equalsIgnoreCase(cmd)) {
                    if (ifsStack.isEmpty()) {
                        throw new IllegalArgumentException("$else found without matching $if");
                    }
                    boolean invert = true;
                    for (final boolean b : ifsStack) {
                        if (!b) {
                            invert = false;
                            break;
                        }
                    }
                    if (!invert) {
                        continue;
                    }
                    parsing = !parsing;
                }
                else if ("endif".equalsIgnoreCase(cmd)) {
                    if (ifsStack.isEmpty()) {
                        throw new IllegalArgumentException("endif found without matching $if");
                    }
                    parsing = ifsStack.remove(ifsStack.size() - 1);
                }
                else {
                    if ("include".equalsIgnoreCase(cmd)) {
                        continue;
                    }
                    continue;
                }
            }
            else {
                if (!parsing) {
                    continue;
                }
                String keySeq = "";
                Label_0689: {
                    if (line.charAt(i++) == '\"') {
                        boolean esc = false;
                        while (i < line.length()) {
                            if (esc) {
                                esc = false;
                            }
                            else if (line.charAt(i) == '\\') {
                                esc = true;
                            }
                            else if (line.charAt(i) == '\"') {
                                break Label_0689;
                            }
                            ++i;
                        }
                        throw new IllegalArgumentException("Missing closing quote on line '" + line + "'");
                    }
                }
                while (i < line.length() && line.charAt(i) != ':' && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
                    ++i;
                }
                keySeq = line.substring(0, i);
                final boolean equivalency = i + 1 < line.length() && line.charAt(i) == ':' && line.charAt(i + 1) == '=';
                ++i;
                if (equivalency) {
                    ++i;
                }
                if (keySeq.equalsIgnoreCase("set")) {
                    while (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
                        ++i;
                    }
                    int s2 = i;
                    while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
                        ++i;
                    }
                    final String key = line.substring(s2, i);
                    while (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
                        ++i;
                    }
                    s2 = i;
                    while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
                        ++i;
                    }
                    final String val = line.substring(s2, i);
                    this.setVar(key, val);
                }
                else {
                    while (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
                        ++i;
                    }
                    final int start = i;
                    if (i < line.length() && (line.charAt(i) == '\'' || line.charAt(i) == '\"')) {
                        final char delim = line.charAt(i++);
                        boolean esc2 = false;
                        while (i < line.length()) {
                            if (esc2) {
                                esc2 = false;
                            }
                            else if (line.charAt(i) == '\\') {
                                esc2 = true;
                            }
                            else if (line.charAt(i) == delim) {
                                break;
                            }
                            ++i;
                        }
                    }
                    while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
                        ++i;
                    }
                    String val = line.substring(Math.min(start, line.length()), Math.min(i, line.length()));
                    if (keySeq.charAt(0) == '\"') {
                        keySeq = this.translateQuoted(keySeq);
                    }
                    else {
                        String keyName = (keySeq.lastIndexOf(45) > 0) ? keySeq.substring(keySeq.lastIndexOf(45) + 1) : keySeq;
                        char key2 = this.getKeyFromName(keyName);
                        keyName = keySeq.toLowerCase();
                        keySeq = "";
                        if (keyName.contains("meta-") || keyName.contains("m-")) {
                            keySeq += "\u001b";
                        }
                        if (keyName.contains("control-") || keyName.contains("c-") || keyName.contains("ctrl-")) {
                            key2 = (char)(Character.toUpperCase(key2) & '\u001f');
                        }
                        keySeq += key2;
                    }
                    if (val.length() > 0 && (val.charAt(0) == '\'' || val.charAt(0) == '\"')) {
                        this.keys.bind(keySeq, this.translateQuoted(val));
                    }
                    else {
                        val = val.replace('-', '_').toUpperCase();
                        this.keys.bind(keySeq, Operation.valueOf(val));
                    }
                }
            }
        }
    }
    
    private String translateQuoted(String keySeq) {
        final String str = keySeq.substring(1, keySeq.length() - 1);
        keySeq = "";
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (c == '\\') {
                final boolean ctrl = str.regionMatches(i, "\\C-", 0, 3) || str.regionMatches(i, "\\M-\\C-", 0, 6);
                final boolean meta = str.regionMatches(i, "\\M-", 0, 3) || str.regionMatches(i, "\\C-\\M-", 0, 6);
                i += (meta ? 3 : 0) + (ctrl ? 3 : 0) + ((!meta && !ctrl) ? 1 : 0);
                if (i >= str.length()) {
                    break;
                }
                c = str.charAt(i);
                if (meta) {
                    keySeq += "\u001b";
                }
                if (ctrl) {
                    c = ((c == '?') ? '\u007f' : ((char)(Character.toUpperCase(c) & '\u001f')));
                }
                if (!meta && !ctrl) {
                    switch (c) {
                        case 'a': {
                            c = '\u0007';
                            break;
                        }
                        case 'b': {
                            c = '\b';
                            break;
                        }
                        case 'd': {
                            c = '\u007f';
                            break;
                        }
                        case 'e': {
                            c = '\u001b';
                            break;
                        }
                        case 'f': {
                            c = '\f';
                            break;
                        }
                        case 'n': {
                            c = '\n';
                            break;
                        }
                        case 'r': {
                            c = '\r';
                            break;
                        }
                        case 't': {
                            c = '\t';
                            break;
                        }
                        case 'v': {
                            c = '\u000b';
                            break;
                        }
                        case '\\': {
                            c = '\\';
                            break;
                        }
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7': {
                            c = '\0';
                            for (int j = 0; j < 3; ++j, ++i) {
                                if (i >= str.length()) {
                                    break;
                                }
                                final int k = Character.digit(str.charAt(i), 8);
                                if (k < 0) {
                                    break;
                                }
                                c = (char)(c * '\b' + k);
                            }
                            c &= '\u00ff';
                            break;
                        }
                        case 'x': {
                            ++i;
                            c = '\0';
                            for (int j = 0; j < 2; ++j, ++i) {
                                if (i >= str.length()) {
                                    break;
                                }
                                final int k = Character.digit(str.charAt(i), 16);
                                if (k < 0) {
                                    break;
                                }
                                c = (char)(c * '\u0010' + k);
                            }
                            c &= '\u00ff';
                            break;
                        }
                        case 'u': {
                            ++i;
                            c = '\0';
                            for (int j = 0; j < 4; ++j, ++i) {
                                if (i >= str.length()) {
                                    break;
                                }
                                final int k = Character.digit(str.charAt(i), 16);
                                if (k < 0) {
                                    break;
                                }
                                c = (char)(c * '\u0010' + k);
                            }
                            break;
                        }
                    }
                }
                keySeq += c;
            }
            else {
                keySeq += c;
            }
        }
        return keySeq;
    }
    
    private char getKeyFromName(final String name) {
        if ("DEL".equalsIgnoreCase(name) || "Rubout".equalsIgnoreCase(name)) {
            return '\u007f';
        }
        if ("ESC".equalsIgnoreCase(name) || "Escape".equalsIgnoreCase(name)) {
            return '\u001b';
        }
        if ("LFD".equalsIgnoreCase(name) || "NewLine".equalsIgnoreCase(name)) {
            return '\n';
        }
        if ("RET".equalsIgnoreCase(name) || "Return".equalsIgnoreCase(name)) {
            return '\r';
        }
        if ("SPC".equalsIgnoreCase(name) || "Space".equalsIgnoreCase(name)) {
            return ' ';
        }
        if ("Tab".equalsIgnoreCase(name)) {
            return '\t';
        }
        return name.charAt(0);
    }
    
    private void setVar(final String key, final String val) {
        if ("keymap".equalsIgnoreCase(key)) {
            if (this.keyMaps.containsKey(val)) {
                this.keys = this.keyMaps.get(val);
            }
        }
        else if ("editing-mode".equals(key)) {
            if ("vi".equalsIgnoreCase(val)) {
                this.keys = this.keyMaps.get("vi-insert");
                this.viEditMode = true;
            }
            else if ("emacs".equalsIgnoreCase(key)) {
                this.keys = this.keyMaps.get("emacs");
                this.viEditMode = false;
            }
        }
    }
}
