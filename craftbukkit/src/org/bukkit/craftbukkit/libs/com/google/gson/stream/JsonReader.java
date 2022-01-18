// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.com.google.gson.stream;

import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.JsonTreeReader;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.JsonReaderInternalAccess;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.Closeable;

public class JsonReader implements Closeable
{
    private static final char[] NON_EXECUTE_PREFIX;
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private final StringPool stringPool;
    private final Reader in;
    private boolean lenient;
    private final char[] buffer;
    private int pos;
    private int limit;
    private int bufferStartLine;
    private int bufferStartColumn;
    private JsonScope[] stack;
    private int stackSize;
    private JsonToken token;
    private String name;
    private String value;
    private int valuePos;
    private int valueLength;
    private boolean skipping;
    
    public JsonReader(final Reader in) {
        this.stringPool = new StringPool();
        this.lenient = false;
        this.buffer = new char[1024];
        this.pos = 0;
        this.limit = 0;
        this.bufferStartLine = 1;
        this.bufferStartColumn = 1;
        this.stack = new JsonScope[32];
        this.stackSize = 0;
        this.push(JsonScope.EMPTY_DOCUMENT);
        this.skipping = false;
        if (in == null) {
            throw new NullPointerException("in == null");
        }
        this.in = in;
    }
    
    public final void setLenient(final boolean lenient) {
        this.lenient = lenient;
    }
    
    public final boolean isLenient() {
        return this.lenient;
    }
    
    public void beginArray() throws IOException {
        this.expect(JsonToken.BEGIN_ARRAY);
    }
    
    public void endArray() throws IOException {
        this.expect(JsonToken.END_ARRAY);
    }
    
    public void beginObject() throws IOException {
        this.expect(JsonToken.BEGIN_OBJECT);
    }
    
    public void endObject() throws IOException {
        this.expect(JsonToken.END_OBJECT);
    }
    
    private void expect(final JsonToken expected) throws IOException {
        this.peek();
        if (this.token != expected) {
            throw new IllegalStateException("Expected " + expected + " but was " + this.peek() + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        this.advance();
    }
    
    public boolean hasNext() throws IOException {
        this.peek();
        return this.token != JsonToken.END_OBJECT && this.token != JsonToken.END_ARRAY;
    }
    
    public JsonToken peek() throws IOException {
        if (this.token != null) {
            return this.token;
        }
        switch (this.stack[this.stackSize - 1]) {
            case EMPTY_DOCUMENT: {
                if (this.lenient) {
                    this.consumeNonExecutePrefix();
                }
                this.stack[this.stackSize - 1] = JsonScope.NONEMPTY_DOCUMENT;
                final JsonToken firstToken = this.nextValue();
                if (!this.lenient && this.token != JsonToken.BEGIN_ARRAY && this.token != JsonToken.BEGIN_OBJECT) {
                    throw new IOException("Expected JSON document to start with '[' or '{' but was " + this.token + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
                }
                return firstToken;
            }
            case EMPTY_ARRAY: {
                return this.nextInArray(true);
            }
            case NONEMPTY_ARRAY: {
                return this.nextInArray(false);
            }
            case EMPTY_OBJECT: {
                return this.nextInObject(true);
            }
            case DANGLING_NAME: {
                return this.objectValue();
            }
            case NONEMPTY_OBJECT: {
                return this.nextInObject(false);
            }
            case NONEMPTY_DOCUMENT: {
                final int c = this.nextNonWhitespace(false);
                if (c == -1) {
                    return JsonToken.END_DOCUMENT;
                }
                --this.pos;
                if (!this.lenient) {
                    throw this.syntaxError("Expected EOF");
                }
                return this.nextValue();
            }
            case CLOSED: {
                throw new IllegalStateException("JsonReader is closed");
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    private void consumeNonExecutePrefix() throws IOException {
        this.nextNonWhitespace(true);
        --this.pos;
        if (this.pos + JsonReader.NON_EXECUTE_PREFIX.length > this.limit && !this.fillBuffer(JsonReader.NON_EXECUTE_PREFIX.length)) {
            return;
        }
        for (int i = 0; i < JsonReader.NON_EXECUTE_PREFIX.length; ++i) {
            if (this.buffer[this.pos + i] != JsonReader.NON_EXECUTE_PREFIX[i]) {
                return;
            }
        }
        this.pos += JsonReader.NON_EXECUTE_PREFIX.length;
    }
    
    private JsonToken advance() throws IOException {
        this.peek();
        final JsonToken result = this.token;
        this.token = null;
        this.value = null;
        this.name = null;
        return result;
    }
    
    public String nextName() throws IOException {
        this.peek();
        if (this.token != JsonToken.NAME) {
            throw new IllegalStateException("Expected a name but was " + this.peek() + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        final String result = this.name;
        this.advance();
        return result;
    }
    
    public String nextString() throws IOException {
        this.peek();
        if (this.token != JsonToken.STRING && this.token != JsonToken.NUMBER) {
            throw new IllegalStateException("Expected a string but was " + this.peek() + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        final String result = this.value;
        this.advance();
        return result;
    }
    
    public boolean nextBoolean() throws IOException {
        this.peek();
        if (this.token != JsonToken.BOOLEAN) {
            throw new IllegalStateException("Expected a boolean but was " + this.token + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        final boolean result = this.value == "true";
        this.advance();
        return result;
    }
    
    public void nextNull() throws IOException {
        this.peek();
        if (this.token != JsonToken.NULL) {
            throw new IllegalStateException("Expected null but was " + this.token + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        this.advance();
    }
    
    public double nextDouble() throws IOException {
        this.peek();
        if (this.token != JsonToken.STRING && this.token != JsonToken.NUMBER) {
            throw new IllegalStateException("Expected a double but was " + this.token + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        final double result = Double.parseDouble(this.value);
        if (result >= 1.0 && this.value.startsWith("0")) {
            throw new MalformedJsonException("JSON forbids octal prefixes: " + this.value + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        if (!this.lenient && (Double.isNaN(result) || Double.isInfinite(result))) {
            throw new MalformedJsonException("JSON forbids NaN and infinities: " + this.value + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        this.advance();
        return result;
    }
    
    public long nextLong() throws IOException {
        this.peek();
        if (this.token != JsonToken.STRING && this.token != JsonToken.NUMBER) {
            throw new IllegalStateException("Expected a long but was " + this.token + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        long result;
        try {
            result = Long.parseLong(this.value);
        }
        catch (NumberFormatException ignored) {
            final double asDouble = Double.parseDouble(this.value);
            result = (long)asDouble;
            if (result != asDouble) {
                throw new NumberFormatException("Expected a long but was " + this.value + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
            }
        }
        if (result >= 1L && this.value.startsWith("0")) {
            throw new MalformedJsonException("JSON forbids octal prefixes: " + this.value + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        this.advance();
        return result;
    }
    
    public int nextInt() throws IOException {
        this.peek();
        if (this.token != JsonToken.STRING && this.token != JsonToken.NUMBER) {
            throw new IllegalStateException("Expected an int but was " + this.token + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        int result;
        try {
            result = Integer.parseInt(this.value);
        }
        catch (NumberFormatException ignored) {
            final double asDouble = Double.parseDouble(this.value);
            result = (int)asDouble;
            if (result != asDouble) {
                throw new NumberFormatException("Expected an int but was " + this.value + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
            }
        }
        if (result >= 1L && this.value.startsWith("0")) {
            throw new MalformedJsonException("JSON forbids octal prefixes: " + this.value + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
        }
        this.advance();
        return result;
    }
    
    public void close() throws IOException {
        this.value = null;
        this.token = null;
        this.stack[0] = JsonScope.CLOSED;
        this.stackSize = 1;
        this.in.close();
    }
    
    public void skipValue() throws IOException {
        this.skipping = true;
        try {
            int count = 0;
            do {
                final JsonToken token = this.advance();
                if (token == JsonToken.BEGIN_ARRAY || token == JsonToken.BEGIN_OBJECT) {
                    ++count;
                }
                else {
                    if (token != JsonToken.END_ARRAY && token != JsonToken.END_OBJECT) {
                        continue;
                    }
                    --count;
                }
            } while (count != 0);
        }
        finally {
            this.skipping = false;
        }
    }
    
    private void push(final JsonScope newTop) {
        if (this.stackSize == this.stack.length) {
            final JsonScope[] newStack = new JsonScope[this.stackSize * 2];
            System.arraycopy(this.stack, 0, newStack, 0, this.stackSize);
            this.stack = newStack;
        }
        this.stack[this.stackSize++] = newTop;
    }
    
    private JsonToken nextInArray(final boolean firstElement) throws IOException {
        if (firstElement) {
            this.stack[this.stackSize - 1] = JsonScope.NONEMPTY_ARRAY;
        }
        else {
            switch (this.nextNonWhitespace(true)) {
                case 93: {
                    --this.stackSize;
                    return this.token = JsonToken.END_ARRAY;
                }
                case 59: {
                    this.checkLenient();
                }
                case 44: {
                    break;
                }
                default: {
                    throw this.syntaxError("Unterminated array");
                }
            }
        }
        switch (this.nextNonWhitespace(true)) {
            case 93: {
                if (firstElement) {
                    --this.stackSize;
                    return this.token = JsonToken.END_ARRAY;
                }
            }
            case 44:
            case 59: {
                this.checkLenient();
                --this.pos;
                this.value = "null";
                return this.token = JsonToken.NULL;
            }
            default: {
                --this.pos;
                return this.nextValue();
            }
        }
    }
    
    private JsonToken nextInObject(final boolean firstElement) throws IOException {
        if (firstElement) {
            switch (this.nextNonWhitespace(true)) {
                case 125: {
                    --this.stackSize;
                    return this.token = JsonToken.END_OBJECT;
                }
                default: {
                    --this.pos;
                    break;
                }
            }
        }
        else {
            switch (this.nextNonWhitespace(true)) {
                case 125: {
                    --this.stackSize;
                    return this.token = JsonToken.END_OBJECT;
                }
                case 44:
                case 59: {
                    break;
                }
                default: {
                    throw this.syntaxError("Unterminated object");
                }
            }
        }
        final int quote = this.nextNonWhitespace(true);
        switch (quote) {
            case 39: {
                this.checkLenient();
            }
            case 34: {
                this.name = this.nextString((char)quote);
                break;
            }
            default: {
                this.checkLenient();
                --this.pos;
                this.name = this.nextLiteral(false);
                if (this.name.length() == 0) {
                    throw this.syntaxError("Expected name");
                }
                break;
            }
        }
        this.stack[this.stackSize - 1] = JsonScope.DANGLING_NAME;
        return this.token = JsonToken.NAME;
    }
    
    private JsonToken objectValue() throws IOException {
        switch (this.nextNonWhitespace(true)) {
            case 58: {
                break;
            }
            case 61: {
                this.checkLenient();
                if ((this.pos < this.limit || this.fillBuffer(1)) && this.buffer[this.pos] == '>') {
                    ++this.pos;
                    break;
                }
                break;
            }
            default: {
                throw this.syntaxError("Expected ':'");
            }
        }
        this.stack[this.stackSize - 1] = JsonScope.NONEMPTY_OBJECT;
        return this.nextValue();
    }
    
    private JsonToken nextValue() throws IOException {
        final int c = this.nextNonWhitespace(true);
        switch (c) {
            case 123: {
                this.push(JsonScope.EMPTY_OBJECT);
                return this.token = JsonToken.BEGIN_OBJECT;
            }
            case 91: {
                this.push(JsonScope.EMPTY_ARRAY);
                return this.token = JsonToken.BEGIN_ARRAY;
            }
            case 39: {
                this.checkLenient();
            }
            case 34: {
                this.value = this.nextString((char)c);
                return this.token = JsonToken.STRING;
            }
            default: {
                --this.pos;
                return this.readLiteral();
            }
        }
    }
    
    private boolean fillBuffer(final int minimum) throws IOException {
        final char[] buffer = this.buffer;
        int line = this.bufferStartLine;
        int column = this.bufferStartColumn;
        for (int i = 0, p = this.pos; i < p; ++i) {
            if (buffer[i] == '\n') {
                ++line;
                column = 1;
            }
            else {
                ++column;
            }
        }
        this.bufferStartLine = line;
        this.bufferStartColumn = column;
        if (this.limit != this.pos) {
            this.limit -= this.pos;
            System.arraycopy(buffer, this.pos, buffer, 0, this.limit);
        }
        else {
            this.limit = 0;
        }
        this.pos = 0;
        int total;
        while ((total = this.in.read(buffer, this.limit, buffer.length - this.limit)) != -1) {
            this.limit += total;
            if (this.bufferStartLine == 1 && this.bufferStartColumn == 1 && this.limit > 0 && buffer[0] == '\ufeff') {
                ++this.pos;
                --this.bufferStartColumn;
            }
            if (this.limit >= minimum) {
                return true;
            }
        }
        return false;
    }
    
    private int getLineNumber() {
        int result = this.bufferStartLine;
        for (int i = 0; i < this.pos; ++i) {
            if (this.buffer[i] == '\n') {
                ++result;
            }
        }
        return result;
    }
    
    private int getColumnNumber() {
        int result = this.bufferStartColumn;
        for (int i = 0; i < this.pos; ++i) {
            if (this.buffer[i] == '\n') {
                result = 1;
            }
            else {
                ++result;
            }
        }
        return result;
    }
    
    private int nextNonWhitespace(final boolean throwOnEof) throws IOException {
        final char[] buffer = this.buffer;
        int p = this.pos;
        int l = this.limit;
        while (true) {
            if (p == l) {
                this.pos = p;
                if (!this.fillBuffer(1)) {
                    if (throwOnEof) {
                        throw new EOFException("End of input at line " + this.getLineNumber() + " column " + this.getColumnNumber());
                    }
                    return -1;
                }
                else {
                    p = this.pos;
                    l = this.limit;
                }
            }
            final int c = buffer[p++];
            switch (c) {
                case 9:
                case 10:
                case 13:
                case 32: {
                    continue;
                }
                case 47: {
                    this.pos = p;
                    if (p == l && !this.fillBuffer(1)) {
                        return c;
                    }
                    this.checkLenient();
                    final char peek = buffer[this.pos];
                    switch (peek) {
                        case '*': {
                            ++this.pos;
                            if (!this.skipTo("*/")) {
                                throw this.syntaxError("Unterminated comment");
                            }
                            p = this.pos + 2;
                            l = this.limit;
                            continue;
                        }
                        case '/': {
                            ++this.pos;
                            this.skipToEndOfLine();
                            p = this.pos;
                            l = this.limit;
                            continue;
                        }
                        default: {
                            return c;
                        }
                    }
                    break;
                }
                case 35: {
                    this.pos = p;
                    this.checkLenient();
                    this.skipToEndOfLine();
                    p = this.pos;
                    l = this.limit;
                    continue;
                }
                default: {
                    this.pos = p;
                    return c;
                }
            }
        }
    }
    
    private void checkLenient() throws IOException {
        if (!this.lenient) {
            throw this.syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }
    
    private void skipToEndOfLine() throws IOException {
        while (this.pos < this.limit || this.fillBuffer(1)) {
            final char c = this.buffer[this.pos++];
            if (c == '\r') {
                break;
            }
            if (c == '\n') {
                break;
            }
        }
    }
    
    private boolean skipTo(final String toFind) throws IOException {
    Label_0000:
        while (this.pos + toFind.length() <= this.limit || this.fillBuffer(toFind.length())) {
            for (int c = 0; c < toFind.length(); ++c) {
                if (this.buffer[this.pos + c] != toFind.charAt(c)) {
                    ++this.pos;
                    continue Label_0000;
                }
            }
            return true;
        }
        return false;
    }
    
    private String nextString(final char quote) throws IOException {
        final char[] buffer = this.buffer;
        StringBuilder builder = null;
        while (true) {
            int p = this.pos;
            int l = this.limit;
            int start = p;
            while (p < l) {
                final int c = buffer[p++];
                if (c == quote) {
                    this.pos = p;
                    if (this.skipping) {
                        return "skipped!";
                    }
                    if (builder == null) {
                        return this.stringPool.get(buffer, start, p - start - 1);
                    }
                    builder.append(buffer, start, p - start - 1);
                    return builder.toString();
                }
                else {
                    if (c != 92) {
                        continue;
                    }
                    this.pos = p;
                    if (builder == null) {
                        builder = new StringBuilder();
                    }
                    builder.append(buffer, start, p - start - 1);
                    builder.append(this.readEscapeCharacter());
                    p = this.pos;
                    l = this.limit;
                    start = p;
                }
            }
            if (builder == null) {
                builder = new StringBuilder();
            }
            builder.append(buffer, start, p - start);
            this.pos = p;
            if (!this.fillBuffer(1)) {
                throw this.syntaxError("Unterminated string");
            }
        }
    }
    
    private String nextLiteral(final boolean assignOffsetsOnly) throws IOException {
        StringBuilder builder = null;
        this.valuePos = -1;
        this.valueLength = 0;
        int i = 0;
    Label_0180:
        while (true) {
            if (this.pos + i < this.limit) {
                switch (this.buffer[this.pos + i]) {
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\': {
                        this.checkLenient();
                    }
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}': {
                        break Label_0180;
                    }
                    default: {
                        ++i;
                        continue;
                    }
                }
            }
            else if (i < this.buffer.length) {
                if (this.fillBuffer(i + 1)) {
                    continue;
                }
                this.buffer[this.limit] = '\0';
                break;
            }
            else {
                if (builder == null) {
                    builder = new StringBuilder();
                }
                builder.append(this.buffer, this.pos, i);
                this.valueLength += i;
                this.pos += i;
                i = 0;
                if (!this.fillBuffer(1)) {
                    break;
                }
                continue;
            }
        }
        String result;
        if (assignOffsetsOnly && builder == null) {
            this.valuePos = this.pos;
            result = null;
        }
        else if (this.skipping) {
            result = "skipped!";
        }
        else if (builder == null) {
            result = this.stringPool.get(this.buffer, this.pos, i);
        }
        else {
            builder.append(this.buffer, this.pos, i);
            result = builder.toString();
        }
        this.valueLength += i;
        this.pos += i;
        return result;
    }
    
    public String toString() {
        return this.getClass().getSimpleName() + " near " + (Object)this.getSnippet();
    }
    
    private char readEscapeCharacter() throws IOException {
        if (this.pos == this.limit && !this.fillBuffer(1)) {
            throw this.syntaxError("Unterminated escape sequence");
        }
        final char escaped = this.buffer[this.pos++];
        switch (escaped) {
            case 'u': {
                if (this.pos + 4 > this.limit && !this.fillBuffer(4)) {
                    throw this.syntaxError("Unterminated escape sequence");
                }
                char result = '\0';
                for (int i = this.pos, end = i + 4; i < end; ++i) {
                    final char c = this.buffer[i];
                    result <<= 4;
                    if (c >= '0' && c <= '9') {
                        result += (char)(c - '0');
                    }
                    else if (c >= 'a' && c <= 'f') {
                        result += (char)(c - 'a' + '\n');
                    }
                    else {
                        if (c < 'A' || c > 'F') {
                            throw new NumberFormatException("\\u" + this.stringPool.get(this.buffer, this.pos, 4));
                        }
                        result += (char)(c - 'A' + '\n');
                    }
                }
                this.pos += 4;
                return result;
            }
            case 't': {
                return '\t';
            }
            case 'b': {
                return '\b';
            }
            case 'n': {
                return '\n';
            }
            case 'r': {
                return '\r';
            }
            case 'f': {
                return '\f';
            }
            default: {
                return escaped;
            }
        }
    }
    
    private JsonToken readLiteral() throws IOException {
        this.value = this.nextLiteral(true);
        if (this.valueLength == 0) {
            throw this.syntaxError("Expected literal value");
        }
        this.token = this.decodeLiteral();
        if (this.token == JsonToken.STRING) {
            this.checkLenient();
        }
        return this.token;
    }
    
    private JsonToken decodeLiteral() throws IOException {
        if (this.valuePos == -1) {
            return JsonToken.STRING;
        }
        if (this.valueLength == 4 && ('n' == this.buffer[this.valuePos] || 'N' == this.buffer[this.valuePos]) && ('u' == this.buffer[this.valuePos + 1] || 'U' == this.buffer[this.valuePos + 1]) && ('l' == this.buffer[this.valuePos + 2] || 'L' == this.buffer[this.valuePos + 2]) && ('l' == this.buffer[this.valuePos + 3] || 'L' == this.buffer[this.valuePos + 3])) {
            this.value = "null";
            return JsonToken.NULL;
        }
        if (this.valueLength == 4 && ('t' == this.buffer[this.valuePos] || 'T' == this.buffer[this.valuePos]) && ('r' == this.buffer[this.valuePos + 1] || 'R' == this.buffer[this.valuePos + 1]) && ('u' == this.buffer[this.valuePos + 2] || 'U' == this.buffer[this.valuePos + 2]) && ('e' == this.buffer[this.valuePos + 3] || 'E' == this.buffer[this.valuePos + 3])) {
            this.value = "true";
            return JsonToken.BOOLEAN;
        }
        if (this.valueLength == 5 && ('f' == this.buffer[this.valuePos] || 'F' == this.buffer[this.valuePos]) && ('a' == this.buffer[this.valuePos + 1] || 'A' == this.buffer[this.valuePos + 1]) && ('l' == this.buffer[this.valuePos + 2] || 'L' == this.buffer[this.valuePos + 2]) && ('s' == this.buffer[this.valuePos + 3] || 'S' == this.buffer[this.valuePos + 3]) && ('e' == this.buffer[this.valuePos + 4] || 'E' == this.buffer[this.valuePos + 4])) {
            this.value = "false";
            return JsonToken.BOOLEAN;
        }
        this.value = this.stringPool.get(this.buffer, this.valuePos, this.valueLength);
        return this.decodeNumber(this.buffer, this.valuePos, this.valueLength);
    }
    
    private JsonToken decodeNumber(final char[] chars, int offset, final int length) {
        int c = chars[offset];
        if (c == 45) {
            c = chars[++offset];
        }
        if (c == 48) {
            c = chars[++offset];
        }
        else {
            if (c < 49 || c > 57) {
                return JsonToken.STRING;
            }
            for (c = chars[++offset]; c >= 48 && c <= 57; c = chars[++offset]) {}
        }
        if (c == 46) {
            for (c = chars[++offset]; c >= 48 && c <= 57; c = chars[++offset]) {}
        }
        if (c == 101 || c == 69) {
            c = chars[++offset];
            if (c == 43 || c == 45) {
                c = chars[++offset];
            }
            if (c < 48 || c > 57) {
                return JsonToken.STRING;
            }
            for (c = chars[++offset]; c >= 48 && c <= 57; c = chars[++offset]) {}
        }
        if (offset == offset + length) {
            return JsonToken.NUMBER;
        }
        return JsonToken.STRING;
    }
    
    private IOException syntaxError(final String message) throws IOException {
        throw new MalformedJsonException(message + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
    }
    
    private CharSequence getSnippet() {
        final StringBuilder snippet = new StringBuilder();
        final int beforePos = Math.min(this.pos, 20);
        snippet.append(this.buffer, this.pos - beforePos, beforePos);
        final int afterPos = Math.min(this.limit - this.pos, 20);
        snippet.append(this.buffer, this.pos, afterPos);
        return snippet;
    }
    
    static {
        NON_EXECUTE_PREFIX = ")]}'\n".toCharArray();
        JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess() {
            public void promoteNameToValue(final JsonReader reader) throws IOException {
                if (reader instanceof JsonTreeReader) {
                    ((JsonTreeReader)reader).promoteNameToValue();
                    return;
                }
                reader.peek();
                if (reader.token != JsonToken.NAME) {
                    throw new IllegalStateException("Expected a name but was " + reader.peek() + " " + " at line " + reader.getLineNumber() + " column " + reader.getColumnNumber());
                }
                reader.value = reader.name;
                reader.name = null;
                reader.token = JsonToken.STRING;
            }
        };
    }
}
