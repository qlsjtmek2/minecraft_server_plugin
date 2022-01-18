// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline.console;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.ByteArrayInputStream;
import java.util.ListIterator;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.awt.datatransfer.DataFlavor;
import java.awt.Toolkit;
import java.util.Iterator;
import java.util.Collections;
import java.util.Collection;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import java.util.Arrays;
import org.fusesource.jansi.AnsiOutputStream;
import java.io.ByteArrayOutputStream;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import java.io.FilterInputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import org.bukkit.craftbukkit.libs.jline.TerminalFactory;
import org.bukkit.craftbukkit.libs.jline.internal.Configuration;
import java.util.HashMap;
import org.bukkit.craftbukkit.libs.jline.console.history.MemoryHistory;
import org.bukkit.craftbukkit.libs.jline.console.completer.CandidateListCompletionHandler;
import java.util.LinkedList;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileDescriptor;
import java.awt.event.ActionListener;
import java.util.Map;
import org.bukkit.craftbukkit.libs.jline.console.history.History;
import org.bukkit.craftbukkit.libs.jline.console.completer.CompletionHandler;
import org.bukkit.craftbukkit.libs.jline.console.completer.Completer;
import java.util.List;
import java.net.URL;
import java.io.Reader;
import java.io.Writer;
import java.io.InputStream;
import org.bukkit.craftbukkit.libs.jline.Terminal;
import java.util.ResourceBundle;

public class ConsoleReader
{
    public static final String JLINE_NOBELL = "org.bukkit.craftbukkit.libs.jline.nobell";
    public static final char BACKSPACE = '\b';
    public static final char RESET_LINE = '\r';
    public static final char KEYBOARD_BELL = '\u0007';
    public static final char NULL_MASK = '\0';
    public static final int TAB_WIDTH = 4;
    private static final ResourceBundle resources;
    private final Terminal terminal;
    private InputStream in;
    private final Writer out;
    private final CursorBuffer buf;
    private String prompt;
    private boolean expandEvents;
    private Character mask;
    private Character echoCharacter;
    private StringBuffer searchTerm;
    private String previousSearchTerm;
    private int searchIndex;
    private Reader reader;
    private String encoding;
    private boolean recording;
    private String macro;
    private String appName;
    private URL inputrcUrl;
    private ConsoleKeys consoleKeys;
    private boolean skipLF;
    public static final String JLINE_COMPLETION_THRESHOLD = "org.bukkit.craftbukkit.libs.jline.completion.threshold";
    private final List<Completer> completers;
    private CompletionHandler completionHandler;
    private int autoprintThreshold;
    private boolean paginationEnabled;
    private History history;
    private boolean historyEnabled;
    public static final String CR;
    private final Map<Character, ActionListener> triggeredActions;
    private Thread maskThread;
    
    public ConsoleReader() throws IOException {
        this(null, new FileInputStream(FileDescriptor.in), System.out, null);
    }
    
    public ConsoleReader(final InputStream in, final OutputStream out) throws IOException {
        this(null, in, out, null);
    }
    
    public ConsoleReader(final InputStream in, final OutputStream out, final Terminal term) throws IOException {
        this(null, in, out, term);
    }
    
    public ConsoleReader(final String appName, final InputStream in, final OutputStream out, final Terminal term) throws IOException {
        this.buf = new CursorBuffer();
        this.expandEvents = true;
        this.searchTerm = null;
        this.previousSearchTerm = "";
        this.searchIndex = -1;
        this.macro = "";
        this.skipLF = false;
        this.completers = new LinkedList<Completer>();
        this.completionHandler = new CandidateListCompletionHandler();
        this.autoprintThreshold = Integer.getInteger("org.bukkit.craftbukkit.libs.jline.completion.threshold", 100);
        this.history = new MemoryHistory();
        this.historyEnabled = true;
        this.triggeredActions = new HashMap<Character, ActionListener>();
        this.appName = ((appName != null) ? appName : "JLine");
        this.encoding = ((this.encoding != null) ? this.encoding : Configuration.getEncoding());
        this.terminal = ((term != null) ? term : TerminalFactory.get());
        this.out = new OutputStreamWriter(this.terminal.wrapOutIfNeeded(out), this.encoding);
        this.setInput(in);
        this.inputrcUrl = Configuration.getUrlFrom(Configuration.getString("org.bukkit.craftbukkit.libs.jline.inputrc", Configuration.getUrlFrom(new File(Configuration.getUserHome(), ".inputrc")).toExternalForm()));
        this.consoleKeys = new ConsoleKeys(appName, this.inputrcUrl);
    }
    
    public KeyMap getKeys() {
        return this.consoleKeys.getKeys();
    }
    
    void setInput(final InputStream in) throws IOException {
        final InputStream wrapped = this.terminal.wrapInIfNeeded(in);
        this.in = new FilterInputStream(wrapped) {
            public int read(final byte[] b, final int off, final int len) throws IOException {
                if (b == null) {
                    throw new NullPointerException();
                }
                if (off < 0 || len < 0 || len > b.length - off) {
                    throw new IndexOutOfBoundsException();
                }
                if (len == 0) {
                    return 0;
                }
                final int c = this.read();
                if (c == -1) {
                    return -1;
                }
                b[off] = (byte)c;
                return 1;
            }
        };
        this.reader = new InputStreamReader(this.in, this.encoding);
    }
    
    public InputStream getInput() {
        return this.in;
    }
    
    public Writer getOutput() {
        return this.out;
    }
    
    public Terminal getTerminal() {
        return this.terminal;
    }
    
    public CursorBuffer getCursorBuffer() {
        return this.buf;
    }
    
    public void setExpandEvents(final boolean expand) {
        this.expandEvents = expand;
    }
    
    public boolean getExpandEvents() {
        return this.expandEvents;
    }
    
    public void setPrompt(final String prompt) {
        this.prompt = prompt;
    }
    
    public String getPrompt() {
        return this.prompt;
    }
    
    public void setEchoCharacter(final Character c) {
        this.echoCharacter = c;
    }
    
    public Character getEchoCharacter() {
        return this.echoCharacter;
    }
    
    protected final boolean resetLine() throws IOException {
        if (this.buf.cursor == 0) {
            return false;
        }
        this.backspaceAll();
        return true;
    }
    
    int getCursorPosition() {
        final String prompt = this.getPrompt();
        return ((prompt == null) ? 0 : this.stripAnsi(this.lastLine(prompt)).length()) + this.buf.cursor;
    }
    
    private String lastLine(final String str) {
        if (str == null) {
            return "";
        }
        final int last = str.lastIndexOf("\n");
        if (last >= 0) {
            return str.substring(last + 1, str.length());
        }
        return str;
    }
    
    private String stripAnsi(final String str) {
        if (str == null) {
            return "";
        }
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final AnsiOutputStream aos = new AnsiOutputStream(baos);
            aos.write(str.getBytes());
            aos.flush();
            return baos.toString();
        }
        catch (IOException e) {
            return str;
        }
    }
    
    public final boolean setCursorPosition(final int position) throws IOException {
        return this.moveCursor(position - this.buf.cursor) != 0;
    }
    
    private void setBuffer(final String buffer) throws IOException {
        if (buffer.equals(this.buf.buffer.toString())) {
            return;
        }
        int sameIndex = 0;
        for (int i = 0, l1 = buffer.length(), l2 = this.buf.buffer.length(); i < l1 && i < l2 && buffer.charAt(i) == this.buf.buffer.charAt(i); ++i) {
            ++sameIndex;
        }
        int diff = this.buf.cursor - sameIndex;
        if (diff < 0) {
            this.moveToEnd();
            diff = this.buf.buffer.length() - sameIndex;
        }
        this.backspace(diff);
        this.killLine();
        this.buf.buffer.setLength(sameIndex);
        this.putString(buffer.substring(sameIndex));
    }
    
    private void setBuffer(final CharSequence buffer) throws IOException {
        this.setBuffer(String.valueOf(buffer));
    }
    
    public final void drawLine() throws IOException {
        final String prompt = this.getPrompt();
        if (prompt != null) {
            this.print(prompt);
        }
        this.print(this.buf.buffer.toString());
        if (this.buf.length() != this.buf.cursor) {
            this.back(this.buf.length() - this.buf.cursor - 1);
        }
        this.drawBuffer();
    }
    
    public final void redrawLine() throws IOException {
        this.print(13);
        this.drawLine();
    }
    
    final String finishBuffer() throws IOException {
        String historyLine;
        String str = historyLine = this.buf.buffer.toString();
        if (this.expandEvents) {
            str = this.expandEvents(str);
            historyLine = str.replaceAll("\\!", "\\\\!");
        }
        if (str.length() > 0) {
            if (this.mask == null && this.isHistoryEnabled()) {
                this.history.add(historyLine);
            }
            else {
                this.mask = null;
            }
        }
        this.history.moveToEnd();
        this.buf.buffer.setLength(0);
        this.buf.cursor = 0;
        return str;
    }
    
    protected String expandEvents(final String str) throws IOException {
        final StringBuilder sb = new StringBuilder();
        boolean escaped = false;
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (escaped) {
                sb.append(c);
                escaped = false;
            }
            else if (c == '\\') {
                escaped = true;
            }
            else {
                escaped = false;
                switch (c) {
                    case '!': {
                        if (i + 1 < str.length()) {
                            c = str.charAt(++i);
                            boolean neg = false;
                            String rep = null;
                            switch (c) {
                                case '!': {
                                    if (this.history.size() == 0) {
                                        throw new IllegalArgumentException("!!: event not found");
                                    }
                                    rep = this.history.get(this.history.index() - 1).toString();
                                    break;
                                }
                                case '#': {
                                    sb.append(sb.toString());
                                    break;
                                }
                                case '?': {
                                    int i2 = str.indexOf(63, i + 1);
                                    if (i2 < 0) {
                                        i2 = str.length();
                                    }
                                    final String sc = str.substring(i + 1, i2);
                                    i = i2;
                                    final int idx = this.searchBackwards(sc);
                                    if (idx < 0) {
                                        throw new IllegalArgumentException("!?" + sc + ": event not found");
                                    }
                                    rep = this.history.get(idx).toString();
                                    break;
                                }
                                case '\t':
                                case ' ': {
                                    sb.append('!');
                                    sb.append(c);
                                    break;
                                }
                                case '-': {
                                    neg = true;
                                    ++i;
                                }
                                case '0':
                                case '1':
                                case '2':
                                case '3':
                                case '4':
                                case '5':
                                case '6':
                                case '7':
                                case '8':
                                case '9': {
                                    final int i2 = i;
                                    while (i < str.length()) {
                                        c = str.charAt(i);
                                        if (c < '0') {
                                            break;
                                        }
                                        if (c > '9') {
                                            break;
                                        }
                                        ++i;
                                    }
                                    int idx = 0;
                                    try {
                                        idx = Integer.parseInt(str.substring(i2, i));
                                    }
                                    catch (NumberFormatException e) {
                                        throw new IllegalArgumentException((neg ? "!-" : "!") + str.substring(i2, i) + ": event not found");
                                    }
                                    if (neg) {
                                        if (idx < this.history.size()) {
                                            rep = this.history.get(this.history.index() - idx).toString();
                                            break;
                                        }
                                        throw new IllegalArgumentException((neg ? "!-" : "!") + str.substring(i2, i) + ": event not found");
                                    }
                                    else {
                                        if (idx >= this.history.index() - this.history.size() && idx < this.history.index()) {
                                            rep = this.history.get(idx).toString();
                                            break;
                                        }
                                        throw new IllegalArgumentException((neg ? "!-" : "!") + str.substring(i2, i) + ": event not found");
                                    }
                                    break;
                                }
                                default: {
                                    final String ss = str.substring(i);
                                    i = str.length();
                                    final int idx = this.searchBackwards(ss, this.history.index(), true);
                                    if (idx < 0) {
                                        throw new IllegalArgumentException("!" + ss + ": event not found");
                                    }
                                    rep = this.history.get(idx).toString();
                                    break;
                                }
                            }
                            if (rep != null) {
                                sb.append(rep);
                            }
                            break;
                        }
                        sb.append(c);
                        break;
                    }
                    case '^': {
                        if (i == 0) {
                            final int i3 = str.indexOf(94, i + 1);
                            int i4 = str.indexOf(94, i3 + 1);
                            if (i4 < 0) {
                                i4 = str.length();
                            }
                            if (i3 > 0 && i4 > 0) {
                                final String s1 = str.substring(i + 1, i3);
                                final String s2 = str.substring(i3 + 1, i4);
                                final String s3 = this.history.get(this.history.index() - 1).toString().replace(s1, s2);
                                sb.append(s3);
                                i = i4 + 1;
                                break;
                            }
                        }
                        sb.append(c);
                        break;
                    }
                    default: {
                        sb.append(c);
                        break;
                    }
                }
            }
        }
        if (escaped) {
            sb.append('\\');
        }
        final String result = sb.toString();
        if (!str.equals(result)) {
            this.print(result);
            this.println();
            this.flush();
        }
        return result;
    }
    
    public final void putString(final CharSequence str) throws IOException {
        this.buf.write(str);
        if (this.mask == null) {
            this.print(str);
        }
        else if (this.mask != '\0') {
            this.print((char)this.mask, str.length());
        }
        this.drawBuffer();
    }
    
    private void drawBuffer(final int clear) throws IOException {
        if (this.buf.cursor != this.buf.length() || clear != 0) {
            final char[] chars = this.buf.buffer.substring(this.buf.cursor).toCharArray();
            if (this.mask != null) {
                Arrays.fill(chars, this.mask);
            }
            if (this.terminal.hasWeirdWrap()) {
                final int width = this.terminal.getWidth();
                final int pos = this.getCursorPosition();
                for (int i = 0; i < chars.length; ++i) {
                    this.print(chars[i]);
                    if ((pos + i + 1) % width == 0) {
                        this.print(32);
                        this.print(13);
                    }
                }
            }
            else {
                this.print(chars);
            }
            this.clearAhead(clear, chars.length);
            if (this.terminal.isAnsiSupported()) {
                if (chars.length > 0) {
                    this.back(chars.length);
                }
            }
            else {
                this.back(chars.length);
            }
        }
        if (this.terminal.hasWeirdWrap()) {
            final int width2 = this.terminal.getWidth();
            if (this.getCursorPosition() > 0 && this.getCursorPosition() % width2 == 0 && this.buf.cursor == this.buf.length() && clear == 0) {
                this.print(32);
                this.print(13);
            }
        }
    }
    
    private void drawBuffer() throws IOException {
        this.drawBuffer(0);
    }
    
    private void clearAhead(final int num, final int delta) throws IOException {
        if (num == 0) {
            return;
        }
        if (this.terminal.isAnsiSupported()) {
            final int width = this.terminal.getWidth();
            final int screenCursorCol = this.getCursorPosition() + delta;
            this.printAnsiSequence("K");
            final int curCol = screenCursorCol % width;
            final int endCol = (screenCursorCol + num - 1) % width;
            int lines = num / width;
            if (endCol < curCol) {
                ++lines;
            }
            for (int i = 0; i < lines; ++i) {
                this.printAnsiSequence("B");
                this.printAnsiSequence("2K");
            }
            for (int i = 0; i < lines; ++i) {
                this.printAnsiSequence("A");
            }
            return;
        }
        this.print(' ', num);
        this.back(num);
    }
    
    protected void back(final int num) throws IOException {
        if (num == 0) {
            return;
        }
        if (this.terminal.isAnsiSupported()) {
            final int width = this.getTerminal().getWidth();
            final int cursor = this.getCursorPosition();
            final int realCursor = cursor + num;
            final int realCol = realCursor % width;
            final int newCol = cursor % width;
            int moveup = num / width;
            final int delta = realCol - newCol;
            if (delta < 0) {
                ++moveup;
            }
            if (moveup > 0) {
                this.printAnsiSequence(moveup + "A");
            }
            this.printAnsiSequence(1 + newCol + "G");
            return;
        }
        this.print('\b', num);
    }
    
    public void flush() throws IOException {
        this.out.flush();
    }
    
    private int backspaceAll() throws IOException {
        return this.backspace(Integer.MAX_VALUE);
    }
    
    private int backspace(final int num) throws IOException {
        if (this.buf.cursor == 0) {
            return 0;
        }
        int count = 0;
        final int termwidth = this.getTerminal().getWidth();
        final int lines = this.getCursorPosition() / termwidth;
        count = this.moveCursor(-1 * num) * -1;
        this.buf.buffer.delete(this.buf.cursor, this.buf.cursor + count);
        if (this.getCursorPosition() / termwidth != lines && this.terminal.isAnsiSupported()) {
            this.printAnsiSequence("K");
        }
        this.drawBuffer(count);
        return count;
    }
    
    public boolean backspace() throws IOException {
        return this.backspace(1) == 1;
    }
    
    protected boolean moveToEnd() throws IOException {
        return this.moveCursor(this.buf.length() - this.buf.cursor) > 0;
    }
    
    private boolean deleteCurrentCharacter() throws IOException {
        if (this.buf.length() == 0 || this.buf.cursor == this.buf.length()) {
            return false;
        }
        this.buf.buffer.deleteCharAt(this.buf.cursor);
        this.drawBuffer(1);
        return true;
    }
    
    private boolean previousWord() throws IOException {
        while (this.isDelimiter(this.buf.current()) && this.moveCursor(-1) != 0) {}
        while (!this.isDelimiter(this.buf.current()) && this.moveCursor(-1) != 0) {}
        return true;
    }
    
    private boolean nextWord() throws IOException {
        while (this.isDelimiter(this.buf.nextChar()) && this.moveCursor(1) != 0) {}
        while (!this.isDelimiter(this.buf.nextChar()) && this.moveCursor(1) != 0) {}
        return true;
    }
    
    private boolean deletePreviousWord() throws IOException {
        while (this.isDelimiter(this.buf.current()) && this.backspace()) {}
        while (!this.isDelimiter(this.buf.current()) && this.backspace()) {}
        return true;
    }
    
    private boolean deleteNextWord() throws IOException {
        while (this.isDelimiter(this.buf.nextChar()) && this.delete()) {}
        while (!this.isDelimiter(this.buf.nextChar()) && this.delete()) {}
        return true;
    }
    
    private boolean capitalizeWord() throws IOException {
        boolean first = true;
        int i;
        char c;
        for (i = 1; this.buf.cursor + i - 1 < this.buf.length() && !this.isDelimiter(c = this.buf.buffer.charAt(this.buf.cursor + i - 1)); ++i) {
            this.buf.buffer.setCharAt(this.buf.cursor + i - 1, first ? Character.toUpperCase(c) : Character.toLowerCase(c));
            first = false;
        }
        this.drawBuffer();
        this.moveCursor(i - 1);
        return true;
    }
    
    private boolean upCaseWord() throws IOException {
        int i;
        char c;
        for (i = 1; this.buf.cursor + i - 1 < this.buf.length() && !this.isDelimiter(c = this.buf.buffer.charAt(this.buf.cursor + i - 1)); ++i) {
            this.buf.buffer.setCharAt(this.buf.cursor + i - 1, Character.toUpperCase(c));
        }
        this.drawBuffer();
        this.moveCursor(i - 1);
        return true;
    }
    
    private boolean downCaseWord() throws IOException {
        int i;
        char c;
        for (i = 1; this.buf.cursor + i - 1 < this.buf.length() && !this.isDelimiter(c = this.buf.buffer.charAt(this.buf.cursor + i - 1)); ++i) {
            this.buf.buffer.setCharAt(this.buf.cursor + i - 1, Character.toLowerCase(c));
        }
        this.drawBuffer();
        this.moveCursor(i - 1);
        return true;
    }
    
    public int moveCursor(final int num) throws IOException {
        int where = num;
        if (this.buf.cursor == 0 && where <= 0) {
            return 0;
        }
        if (this.buf.cursor == this.buf.buffer.length() && where >= 0) {
            return 0;
        }
        if (this.buf.cursor + where < 0) {
            where = -this.buf.cursor;
        }
        else if (this.buf.cursor + where > this.buf.buffer.length()) {
            where = this.buf.buffer.length() - this.buf.cursor;
        }
        this.moveInternal(where);
        return where;
    }
    
    private void moveInternal(final int where) throws IOException {
        final CursorBuffer buf = this.buf;
        buf.cursor += where;
        if (this.terminal.isAnsiSupported()) {
            if (where < 0) {
                this.back(Math.abs(where));
            }
            else {
                final int width = this.getTerminal().getWidth();
                final int cursor = this.getCursorPosition();
                final int oldLine = (cursor - where) / width;
                final int newLine = cursor / width;
                if (newLine > oldLine) {
                    if (this.terminal.hasWeirdWrap() && this.getCurrentAnsiRow() == this.terminal.getHeight()) {
                        this.printAnsiSequence(newLine - oldLine + "S");
                    }
                    this.printAnsiSequence(newLine - oldLine + "B");
                }
                this.printAnsiSequence(1 + cursor % width + "G");
            }
            return;
        }
        if (where < 0) {
            int len = 0;
            for (int i = this.buf.cursor; i < this.buf.cursor - where; ++i) {
                if (this.buf.buffer.charAt(i) == '\t') {
                    len += 4;
                }
                else {
                    ++len;
                }
            }
            final char[] chars = new char[len];
            Arrays.fill(chars, '\b');
            this.out.write(chars);
            return;
        }
        if (this.buf.cursor == 0) {
            return;
        }
        if (this.mask == null) {
            this.print(this.buf.buffer.substring(this.buf.cursor - where, this.buf.cursor).toCharArray());
            return;
        }
        final char c = this.mask;
        if (this.mask == '\0') {
            return;
        }
        this.print(c, Math.abs(where));
    }
    
    public final boolean replace(final int num, final String replacement) {
        this.buf.buffer.replace(this.buf.cursor - num, this.buf.cursor, replacement);
        try {
            this.moveCursor(-num);
            this.drawBuffer(Math.max(0, num - replacement.length()));
            this.moveCursor(replacement.length());
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public final int readCharacter() throws IOException {
        final int c = this.reader.read();
        if (c >= 0) {
            Log.trace("Keystroke: ", c);
            this.clearEcho(c);
        }
        return c;
    }
    
    private int clearEcho(final int c) throws IOException {
        if (!this.terminal.isEchoEnabled()) {
            return 0;
        }
        final int num = this.countEchoCharacters(c);
        this.back(num);
        this.drawBuffer(num);
        return num;
    }
    
    private int countEchoCharacters(final int c) {
        if (c == 9) {
            final int tabStop = 8;
            final int position = this.getCursorPosition();
            return tabStop - position % tabStop;
        }
        return this.getPrintableCharacters(c).length();
    }
    
    private StringBuilder getPrintableCharacters(final int ch) {
        final StringBuilder sbuff = new StringBuilder();
        if (ch >= 32) {
            if (ch < 127) {
                sbuff.append(ch);
            }
            else if (ch == 127) {
                sbuff.append('^');
                sbuff.append('?');
            }
            else {
                sbuff.append('M');
                sbuff.append('-');
                if (ch >= 160) {
                    if (ch < 255) {
                        sbuff.append((char)(ch - 128));
                    }
                    else {
                        sbuff.append('^');
                        sbuff.append('?');
                    }
                }
                else {
                    sbuff.append('^');
                    sbuff.append((char)(ch - 128 + 64));
                }
            }
        }
        else {
            sbuff.append('^');
            sbuff.append((char)(ch + 64));
        }
        return sbuff;
    }
    
    public final int readCharacter(final char... allowed) throws IOException {
        Arrays.sort(allowed);
        char c;
        while (Arrays.binarySearch(allowed, c = (char)this.readCharacter()) < 0) {}
        return c;
    }
    
    public String readLine() throws IOException {
        return this.readLine((String)null);
    }
    
    public String readLine(final Character mask) throws IOException {
        return this.readLine(null, mask);
    }
    
    public String readLine(final String prompt) throws IOException {
        return this.readLine(prompt, null);
    }
    
    public String readLine(String prompt, final Character mask) throws IOException {
        this.mask = mask;
        if (prompt != null) {
            this.setPrompt(prompt);
        }
        else {
            prompt = this.getPrompt();
        }
        try {
            if (!this.terminal.isSupported()) {
                this.beforeReadLine(prompt, mask);
            }
            if (prompt != null && prompt.length() > 0) {
                this.out.write(prompt);
                this.out.flush();
            }
            if (!this.terminal.isSupported()) {
                return this.readLineSimple();
            }
            final String originalPrompt = this.prompt;
            final int NORMAL = 1;
            final int SEARCH = 2;
            int state = 1;
            boolean success = true;
            final StringBuilder sb = new StringBuilder();
            final List<Character> pushBackChar = new ArrayList<Character>();
            while (true) {
                int c = pushBackChar.isEmpty() ? this.readCharacter() : ((char)pushBackChar.remove(pushBackChar.size() - 1));
                if (c == -1) {
                    return null;
                }
                sb.append((char)c);
                if (this.recording) {
                    this.macro += (char)c;
                }
                Object o = this.getKeys().getBound(sb);
                if (o == Operation.DO_LOWERCASE_VERSION) {
                    sb.setLength(sb.length() - 1);
                    sb.append(Character.toLowerCase((char)c));
                    o = this.getKeys().getBound(sb);
                }
                if (o instanceof KeyMap) {
                    continue;
                }
                while (o == null && sb.length() > 0) {
                    c = sb.charAt(sb.length() - 1);
                    sb.setLength(sb.length() - 1);
                    final Object o2 = this.getKeys().getBound(sb);
                    if (o2 instanceof KeyMap) {
                        o = ((KeyMap)o2).getAnotherKey();
                        if (o == null) {
                            continue;
                        }
                        pushBackChar.add((char)c);
                    }
                }
                if (o == null) {
                    continue;
                }
                Log.trace("Binding: ", o);
                if (o instanceof String) {
                    final String macro = (String)o;
                    for (int i = 0; i < macro.length(); ++i) {
                        pushBackChar.add(macro.charAt(macro.length() - 1 - i));
                    }
                    sb.setLength(0);
                }
                else if (o instanceof ActionListener) {
                    ((ActionListener)o).actionPerformed(null);
                    sb.setLength(0);
                }
                else {
                    if (state == 2) {
                        int cursorDest = -1;
                        switch ((Operation)o) {
                            case ABORT: {
                                state = 1;
                                break;
                            }
                            case REVERSE_SEARCH_HISTORY: {
                                if (this.searchTerm.length() == 0) {
                                    this.searchTerm.append(this.previousSearchTerm);
                                }
                                if (this.searchIndex == -1) {
                                    this.searchIndex = this.searchBackwards(this.searchTerm.toString());
                                    break;
                                }
                                this.searchIndex = this.searchBackwards(this.searchTerm.toString(), this.searchIndex);
                                break;
                            }
                            case BACKWARD_DELETE_CHAR: {
                                if (this.searchTerm.length() > 0) {
                                    this.searchTerm.deleteCharAt(this.searchTerm.length() - 1);
                                    this.searchIndex = this.searchBackwards(this.searchTerm.toString());
                                    break;
                                }
                                break;
                            }
                            case SELF_INSERT: {
                                this.searchTerm.appendCodePoint(c);
                                this.searchIndex = this.searchBackwards(this.searchTerm.toString());
                                break;
                            }
                            default: {
                                if (this.searchIndex != -1) {
                                    this.history.moveTo(this.searchIndex);
                                    cursorDest = this.history.current().toString().indexOf(this.searchTerm.toString());
                                }
                                state = 1;
                                break;
                            }
                        }
                        if (state == 2) {
                            if (this.searchTerm.length() == 0) {
                                this.printSearchStatus("", "");
                                this.searchIndex = -1;
                            }
                            else if (this.searchIndex == -1) {
                                this.beep();
                            }
                            else {
                                this.printSearchStatus(this.searchTerm.toString(), this.history.get(this.searchIndex).toString());
                            }
                        }
                        else {
                            this.restoreLine(originalPrompt, cursorDest);
                        }
                    }
                    if (state == 1 && o instanceof Operation) {
                        switch ((Operation)o) {
                            case COMPLETE: {
                                success = this.complete();
                                break;
                            }
                            case POSSIBLE_COMPLETIONS: {
                                this.printCompletionCandidates();
                                success = true;
                                break;
                            }
                            case BEGINNING_OF_LINE: {
                                success = this.setCursorPosition(0);
                                break;
                            }
                            case KILL_LINE: {
                                success = this.killLine();
                                break;
                            }
                            case KILL_WHOLE_LINE: {
                                success = (this.setCursorPosition(0) && this.killLine());
                                break;
                            }
                            case CLEAR_SCREEN: {
                                success = this.clearScreen();
                                break;
                            }
                            case OVERWRITE_MODE: {
                                this.buf.setOverTyping(!this.buf.isOverTyping());
                                break;
                            }
                            case SELF_INSERT: {
                                this.putString(sb);
                                success = true;
                                break;
                            }
                            case ACCEPT_LINE: {
                                this.moveToEnd();
                                this.println();
                                this.flush();
                                return this.finishBuffer();
                            }
                            case BACKWARD_WORD: {
                                success = this.previousWord();
                                break;
                            }
                            case FORWARD_WORD: {
                                success = this.nextWord();
                                break;
                            }
                            case PREVIOUS_HISTORY: {
                                success = this.moveHistory(false);
                                break;
                            }
                            case NEXT_HISTORY: {
                                success = this.moveHistory(true);
                                break;
                            }
                            case BACKWARD_DELETE_CHAR: {
                                success = this.backspace();
                                break;
                            }
                            case EXIT_OR_DELETE_CHAR: {
                                if (this.buf.buffer.length() == 0) {
                                    return null;
                                }
                                success = this.deleteCurrentCharacter();
                                break;
                            }
                            case DELETE_CHAR: {
                                success = this.deleteCurrentCharacter();
                                break;
                            }
                            case BACKWARD_CHAR: {
                                success = (this.moveCursor(-1) != 0);
                                break;
                            }
                            case FORWARD_CHAR: {
                                success = (this.moveCursor(1) != 0);
                                break;
                            }
                            case UNIX_LINE_DISCARD: {
                                success = this.resetLine();
                                break;
                            }
                            case UNIX_WORD_RUBOUT:
                            case BACKWARD_KILL_WORD: {
                                success = this.deletePreviousWord();
                                break;
                            }
                            case KILL_WORD: {
                                success = this.deleteNextWord();
                                break;
                            }
                            case BEGINNING_OF_HISTORY: {
                                success = this.history.moveToFirst();
                                if (success) {
                                    this.setBuffer(this.history.current());
                                    break;
                                }
                                break;
                            }
                            case END_OF_HISTORY: {
                                success = this.history.moveToLast();
                                if (success) {
                                    this.setBuffer(this.history.current());
                                    break;
                                }
                                break;
                            }
                            case REVERSE_SEARCH_HISTORY: {
                                if (this.searchTerm != null) {
                                    this.previousSearchTerm = this.searchTerm.toString();
                                }
                                this.searchTerm = new StringBuffer(this.buf.buffer);
                                state = 2;
                                if (this.searchTerm.length() > 0) {
                                    this.searchIndex = this.searchBackwards(this.searchTerm.toString());
                                    if (this.searchIndex == -1) {
                                        this.beep();
                                    }
                                    this.printSearchStatus(this.searchTerm.toString(), (this.searchIndex > -1) ? this.history.get(this.searchIndex).toString() : "");
                                    break;
                                }
                                this.searchIndex = -1;
                                this.printSearchStatus("", "");
                                break;
                            }
                            case CAPITALIZE_WORD: {
                                success = this.capitalizeWord();
                                break;
                            }
                            case UPCASE_WORD: {
                                success = this.upCaseWord();
                                break;
                            }
                            case DOWNCASE_WORD: {
                                success = this.downCaseWord();
                                break;
                            }
                            case END_OF_LINE: {
                                success = this.moveToEnd();
                                break;
                            }
                            case TAB_INSERT: {
                                this.putString("\t");
                                success = true;
                                break;
                            }
                            case RE_READ_INIT_FILE: {
                                this.consoleKeys.loadKeys(this.appName, this.inputrcUrl);
                                success = true;
                                break;
                            }
                            case START_KBD_MACRO: {
                                this.recording = true;
                                break;
                            }
                            case END_KBD_MACRO: {
                                this.recording = false;
                                this.macro = this.macro.substring(0, this.macro.length() - sb.length());
                                break;
                            }
                            case CALL_LAST_KBD_MACRO: {
                                for (int j = 0; j < this.macro.length(); ++j) {
                                    pushBackChar.add(this.macro.charAt(this.macro.length() - 1 - j));
                                }
                                sb.setLength(0);
                                break;
                            }
                            case VI_EDITING_MODE: {
                                this.consoleKeys.setViEditMode(true);
                                this.consoleKeys.setKeys(this.consoleKeys.getKeyMaps().get("vi"));
                                break;
                            }
                            case EMACS_EDITING_MODE: {
                                this.consoleKeys.setViEditMode(false);
                                this.consoleKeys.setKeys(this.consoleKeys.getKeyMaps().get("emacs"));
                                break;
                            }
                            default: {
                                final int j = 0;
                                break;
                            }
                        }
                    }
                    if (!success) {
                        this.beep();
                    }
                    sb.setLength(0);
                    this.flush();
                }
            }
        }
        finally {
            if (!this.terminal.isSupported()) {
                this.afterReadLine();
            }
        }
    }
    
    private String readLineSimple() throws IOException {
        final StringBuilder buff = new StringBuilder();
        if (this.skipLF) {
            this.skipLF = false;
            final int i = this.readCharacter();
            if (i == -1 || i == 13) {
                return buff.toString();
            }
            if (i != 10) {
                buff.append((char)i);
            }
        }
        while (true) {
            final int i = this.readCharacter();
            if (i == -1 || i == 10) {
                return buff.toString();
            }
            if (i == 13) {
                this.skipLF = true;
                return buff.toString();
            }
            buff.append((char)i);
        }
    }
    
    public boolean addCompleter(final Completer completer) {
        return this.completers.add(completer);
    }
    
    public boolean removeCompleter(final Completer completer) {
        return this.completers.remove(completer);
    }
    
    public Collection<Completer> getCompleters() {
        return (Collection<Completer>)Collections.unmodifiableList((List<?>)this.completers);
    }
    
    public void setCompletionHandler(final CompletionHandler handler) {
        assert handler != null;
        this.completionHandler = handler;
    }
    
    public CompletionHandler getCompletionHandler() {
        return this.completionHandler;
    }
    
    protected boolean complete() throws IOException {
        if (this.completers.size() == 0) {
            return false;
        }
        final List<CharSequence> candidates = new LinkedList<CharSequence>();
        final String bufstr = this.buf.buffer.toString();
        final int cursor = this.buf.cursor;
        int position = -1;
        for (final Completer comp : this.completers) {
            if ((position = comp.complete(bufstr, cursor, candidates)) != -1) {
                break;
            }
        }
        return candidates.size() != 0 && this.getCompletionHandler().complete(this, candidates, position);
    }
    
    protected void printCompletionCandidates() throws IOException {
        if (this.completers.size() == 0) {
            return;
        }
        final List<CharSequence> candidates = new LinkedList<CharSequence>();
        final String bufstr = this.buf.buffer.toString();
        final int cursor = this.buf.cursor;
        for (final Completer comp : this.completers) {
            if (comp.complete(bufstr, cursor, candidates) != -1) {
                break;
            }
        }
        CandidateListCompletionHandler.printCandidates(this, candidates);
        this.drawLine();
    }
    
    public void setAutoprintThreshold(final int threshold) {
        this.autoprintThreshold = threshold;
    }
    
    public int getAutoprintThreshold() {
        return this.autoprintThreshold;
    }
    
    public void setPaginationEnabled(final boolean enabled) {
        this.paginationEnabled = enabled;
    }
    
    public boolean isPaginationEnabled() {
        return this.paginationEnabled;
    }
    
    public void setHistory(final History history) {
        this.history = history;
    }
    
    public History getHistory() {
        return this.history;
    }
    
    public void setHistoryEnabled(final boolean enabled) {
        this.historyEnabled = enabled;
    }
    
    public boolean isHistoryEnabled() {
        return this.historyEnabled;
    }
    
    private boolean moveHistory(final boolean next) throws IOException {
        if (next && !this.history.next()) {
            return false;
        }
        if (!next && !this.history.previous()) {
            return false;
        }
        this.setBuffer(this.history.current());
        return true;
    }
    
    private void print(final int c) throws IOException {
        if (c == 9) {
            final char[] chars = new char[4];
            Arrays.fill(chars, ' ');
            this.out.write(chars);
            return;
        }
        this.out.write(c);
    }
    
    private void print(final char... buff) throws IOException {
        int len = 0;
        for (final char c : buff) {
            if (c == '\t') {
                len += 4;
            }
            else {
                ++len;
            }
        }
        char[] chars;
        if (len == buff.length) {
            chars = buff;
        }
        else {
            chars = new char[len];
            int pos = 0;
            for (final char c2 : buff) {
                if (c2 == '\t') {
                    Arrays.fill(chars, pos, pos + 4, ' ');
                    pos += 4;
                }
                else {
                    chars[pos] = c2;
                    ++pos;
                }
            }
        }
        this.out.write(chars);
    }
    
    private void print(final char c, final int num) throws IOException {
        if (num == 1) {
            this.print(c);
        }
        else {
            final char[] chars = new char[num];
            Arrays.fill(chars, c);
            this.print(chars);
        }
    }
    
    public final void print(final CharSequence s) throws IOException {
        assert s != null;
        this.print(s.toString().toCharArray());
    }
    
    public final void println(final CharSequence s) throws IOException {
        assert s != null;
        this.print(s.toString().toCharArray());
        this.println();
    }
    
    public final void println() throws IOException {
        this.print(ConsoleReader.CR);
    }
    
    public final boolean delete() throws IOException {
        return this.delete(1) == 1;
    }
    
    private int delete(final int num) throws IOException {
        this.buf.buffer.delete(this.buf.cursor, this.buf.cursor + 1);
        this.drawBuffer(1);
        return 1;
    }
    
    public boolean killLine() throws IOException {
        final int cp = this.buf.cursor;
        final int len = this.buf.buffer.length();
        if (cp >= len) {
            return false;
        }
        final int num = this.buf.buffer.length() - cp;
        this.clearAhead(num, 0);
        for (int i = 0; i < num; ++i) {
            this.buf.buffer.deleteCharAt(len - i - 1);
        }
        return true;
    }
    
    public boolean clearScreen() throws IOException {
        if (!this.terminal.isAnsiSupported()) {
            return false;
        }
        this.printAnsiSequence("2J");
        this.printAnsiSequence("1;1H");
        this.redrawLine();
        return true;
    }
    
    public void beep() throws IOException {
        if (!Configuration.getBoolean("org.bukkit.craftbukkit.libs.jline.nobell", true)) {
            this.print(7);
            this.flush();
        }
    }
    
    public boolean paste() throws IOException {
        Clipboard clipboard;
        try {
            clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        }
        catch (Exception e2) {
            return false;
        }
        if (clipboard == null) {
            return false;
        }
        final Transferable transferable = clipboard.getContents(null);
        if (transferable == null) {
            return false;
        }
        try {
            Object content = transferable.getTransferData(DataFlavor.plainTextFlavor);
            if (content == null) {
                try {
                    content = new DataFlavor().getReaderForText(transferable);
                }
                catch (Exception ex) {}
            }
            if (content == null) {
                return false;
            }
            String value;
            if (content instanceof Reader) {
                value = "";
                final BufferedReader read = new BufferedReader((Reader)content);
                String line;
                while ((line = read.readLine()) != null) {
                    if (value.length() > 0) {
                        value += "\n";
                    }
                    value += line;
                }
            }
            else {
                value = content.toString();
            }
            if (value == null) {
                return true;
            }
            this.putString(value);
            return true;
        }
        catch (UnsupportedFlavorException e) {
            Log.error("Paste failed: ", e);
            return false;
        }
    }
    
    public void addTriggeredAction(final char c, final ActionListener listener) {
        this.triggeredActions.put(c, listener);
    }
    
    public void printColumns(final Collection<? extends CharSequence> items) throws IOException {
        if (items == null || items.isEmpty()) {
            return;
        }
        final int width = this.getTerminal().getWidth();
        final int height = this.getTerminal().getHeight();
        int maxWidth = 0;
        for (final CharSequence item : items) {
            maxWidth = Math.max(maxWidth, item.length());
        }
        maxWidth += 3;
        Log.debug("Max width: ", maxWidth);
        int showLines;
        if (this.isPaginationEnabled()) {
            showLines = height - 1;
        }
        else {
            showLines = Integer.MAX_VALUE;
        }
        final StringBuilder buff = new StringBuilder();
        for (final CharSequence item2 : items) {
            if (buff.length() + maxWidth > width) {
                this.println(buff);
                buff.setLength(0);
                if (--showLines == 0) {
                    this.print(ConsoleReader.resources.getString("DISPLAY_MORE"));
                    this.flush();
                    final int c = this.readCharacter();
                    if (c == 13 || c == 10) {
                        showLines = 1;
                    }
                    else if (c != 113) {
                        showLines = height - 1;
                    }
                    this.back(ConsoleReader.resources.getString("DISPLAY_MORE").length());
                    if (c == 113) {
                        break;
                    }
                }
            }
            buff.append(item2.toString());
            for (int i = 0; i < maxWidth - item2.length(); ++i) {
                buff.append(' ');
            }
        }
        if (buff.length() > 0) {
            this.println(buff);
        }
    }
    
    private void beforeReadLine(final String prompt, final Character mask) {
        if (mask != null && this.maskThread == null) {
            final String fullPrompt = "\r" + prompt + "                 " + "                 " + "                 " + "\r" + prompt;
            (this.maskThread = new Thread() {
                public void run() {
                    while (!Thread.interrupted()) {
                        try {
                            final Writer out = ConsoleReader.this.getOutput();
                            out.write(fullPrompt);
                            out.flush();
                            Thread.sleep(3L);
                            continue;
                        }
                        catch (IOException e) {
                            return;
                        }
                        catch (InterruptedException e2) {
                            return;
                        }
                        break;
                    }
                }
            }).setPriority(10);
            this.maskThread.setDaemon(true);
            this.maskThread.start();
        }
    }
    
    private void afterReadLine() {
        if (this.maskThread != null && this.maskThread.isAlive()) {
            this.maskThread.interrupt();
        }
        this.maskThread = null;
    }
    
    public void resetPromptLine(final String prompt, final String buffer, int cursorDest) throws IOException {
        this.moveToEnd();
        this.buf.buffer.append(this.prompt);
        final CursorBuffer buf = this.buf;
        buf.cursor += this.prompt.length();
        this.prompt = "";
        this.backspaceAll();
        this.prompt = prompt;
        this.redrawLine();
        this.setBuffer(buffer);
        if (cursorDest < 0) {
            cursorDest = buffer.length();
        }
        this.setCursorPosition(cursorDest);
        this.flush();
    }
    
    public void printSearchStatus(final String searchTerm, final String match) throws IOException {
        final String prompt = "(reverse-i-search)`" + searchTerm + "': ";
        final int cursorDest = match.indexOf(searchTerm);
        this.resetPromptLine(prompt, match, cursorDest);
    }
    
    public void restoreLine(final String originalPrompt, final int cursorDest) throws IOException {
        final String prompt = this.lastLine(originalPrompt);
        final String buffer = this.buf.buffer.toString();
        this.resetPromptLine(prompt, buffer, cursorDest);
    }
    
    public int searchBackwards(final String searchTerm, final int startIndex) {
        return this.searchBackwards(searchTerm, startIndex, false);
    }
    
    public int searchBackwards(final String searchTerm) {
        return this.searchBackwards(searchTerm, this.history.index());
    }
    
    public int searchBackwards(final String searchTerm, final int startIndex, final boolean startsWith) {
        final ListIterator<History.Entry> it = this.history.entries(startIndex);
        while (it.hasPrevious()) {
            final History.Entry e = it.previous();
            if (startsWith) {
                if (e.value().toString().startsWith(searchTerm)) {
                    return e.index();
                }
                continue;
            }
            else {
                if (e.value().toString().contains(searchTerm)) {
                    return e.index();
                }
                continue;
            }
        }
        return -1;
    }
    
    private boolean isDelimiter(final char c) {
        return !Character.isLetterOrDigit(c);
    }
    
    private void printAnsiSequence(final String sequence) throws IOException {
        this.print(27);
        this.print(91);
        this.print(sequence);
        this.flush();
    }
    
    private int getCurrentPosition() {
        if (this.terminal.isAnsiSupported() && !(this.in instanceof ByteArrayInputStream)) {
            try {
                this.printAnsiSequence("6n");
                this.flush();
                final StringBuffer b = new StringBuffer(8);
                int r;
                while ((r = this.in.read()) > -1 && r != 82) {
                    if (r != 27 && r != 91) {
                        b.append((char)r);
                    }
                }
                final String[] pos = b.toString().split(";");
                return Integer.parseInt(pos[1]);
            }
            catch (Exception ex) {}
        }
        return -1;
    }
    
    private int getCurrentAnsiRow() {
        if (this.terminal.isAnsiSupported() && !(this.in instanceof ByteArrayInputStream)) {
            try {
                this.printAnsiSequence("6n");
                this.flush();
                final StringBuffer b = new StringBuffer(8);
                int r;
                while ((r = this.in.read()) > -1 && r != 82) {
                    if (r != 27 && r != 91) {
                        b.append((char)r);
                    }
                }
                final String[] pos = b.toString().split(";");
                return Integer.parseInt(pos[0]);
            }
            catch (Exception ex) {}
        }
        return -1;
    }
    
    static {
        resources = ResourceBundle.getBundle(CandidateListCompletionHandler.class.getName());
        CR = System.getProperty("line.separator");
    }
}
