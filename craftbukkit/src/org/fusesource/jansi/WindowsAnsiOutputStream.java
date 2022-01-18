// 
// Decompiled by Procyon v0.5.30
// 

package org.fusesource.jansi;

import org.fusesource.jansi.internal.WindowsSupport;
import java.io.IOException;
import java.io.OutputStream;
import org.fusesource.jansi.internal.Kernel32;

public final class WindowsAnsiOutputStream extends AnsiOutputStream
{
    private static final long console;
    private static final short FOREGROUND_BLACK = 0;
    private static final short FOREGROUND_YELLOW;
    private static final short FOREGROUND_MAGENTA;
    private static final short FOREGROUND_CYAN;
    private static final short FOREGROUND_WHITE;
    private static final short BACKGROUND_BLACK = 0;
    private static final short BACKGROUND_YELLOW;
    private static final short BACKGROUND_MAGENTA;
    private static final short BACKGROUND_CYAN;
    private static final short BACKGROUND_WHITE;
    private static final short[] ANSI_FOREGROUND_COLOR_MAP;
    private static final short[] ANSI_BACKGROUND_COLOR_MAP;
    private final Kernel32.CONSOLE_SCREEN_BUFFER_INFO info;
    private final short originalColors;
    private boolean negative;
    private short savedX;
    private short savedY;
    
    public WindowsAnsiOutputStream(final OutputStream os) throws IOException {
        super(os);
        this.info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        this.savedX = -1;
        this.savedY = -1;
        this.getConsoleInfo();
        this.originalColors = this.info.attributes;
    }
    
    private void getConsoleInfo() throws IOException {
        this.out.flush();
        if (Kernel32.GetConsoleScreenBufferInfo(WindowsAnsiOutputStream.console, this.info) == 0) {
            throw new IOException("Could not get the screen info: " + WindowsSupport.getLastErrorMessage());
        }
        if (this.negative) {
            this.info.attributes = this.invertAttributeColors(this.info.attributes);
        }
    }
    
    private void applyAttribute() throws IOException {
        this.out.flush();
        short attributes = this.info.attributes;
        if (this.negative) {
            attributes = this.invertAttributeColors(attributes);
        }
        if (Kernel32.SetConsoleTextAttribute(WindowsAnsiOutputStream.console, attributes) == 0) {
            throw new IOException(WindowsSupport.getLastErrorMessage());
        }
    }
    
    private short invertAttributeColors(short attibutes) {
        int fg = 0xF & attibutes;
        fg <<= 8;
        int bg = 240 * attibutes;
        bg >>= 8;
        attibutes = (short)((attibutes & 0xFF00) | fg | bg);
        return attibutes;
    }
    
    private void applyCursorPosition() throws IOException {
        if (Kernel32.SetConsoleCursorPosition(WindowsAnsiOutputStream.console, this.info.cursorPosition.copy()) == 0) {
            throw new IOException(WindowsSupport.getLastErrorMessage());
        }
    }
    
    protected void processEraseScreen(final int eraseOption) throws IOException {
        this.getConsoleInfo();
        final int[] written = { 0 };
        switch (eraseOption) {
            case 2: {
                final Kernel32.COORD topLeft = new Kernel32.COORD();
                topLeft.x = 0;
                topLeft.y = this.info.window.top;
                final int screenLength = this.info.window.height() * this.info.size.x;
                Kernel32.FillConsoleOutputCharacterW(WindowsAnsiOutputStream.console, ' ', screenLength, topLeft, written);
                break;
            }
            case 1: {
                final Kernel32.COORD topLeft2 = new Kernel32.COORD();
                topLeft2.x = 0;
                topLeft2.y = this.info.window.top;
                final int lengthToCursor = (this.info.cursorPosition.y - this.info.window.top) * this.info.size.x + this.info.cursorPosition.x;
                Kernel32.FillConsoleOutputCharacterW(WindowsAnsiOutputStream.console, ' ', lengthToCursor, topLeft2, written);
                break;
            }
            case 0: {
                final int lengthToEnd = (this.info.window.bottom - this.info.cursorPosition.y) * this.info.size.x + (this.info.size.x - this.info.cursorPosition.x);
                Kernel32.FillConsoleOutputCharacterW(WindowsAnsiOutputStream.console, ' ', lengthToEnd, this.info.cursorPosition.copy(), written);
                break;
            }
        }
    }
    
    protected void processEraseLine(final int eraseOption) throws IOException {
        this.getConsoleInfo();
        final int[] written = { 0 };
        switch (eraseOption) {
            case 2: {
                final Kernel32.COORD leftColCurrRow = this.info.cursorPosition.copy();
                leftColCurrRow.x = 0;
                Kernel32.FillConsoleOutputCharacterW(WindowsAnsiOutputStream.console, ' ', this.info.size.x, leftColCurrRow, written);
                break;
            }
            case 1: {
                final Kernel32.COORD leftColCurrRow2 = this.info.cursorPosition.copy();
                leftColCurrRow2.x = 0;
                Kernel32.FillConsoleOutputCharacterW(WindowsAnsiOutputStream.console, ' ', this.info.cursorPosition.x, leftColCurrRow2, written);
                break;
            }
            case 0: {
                final int lengthToLastCol = this.info.size.x - this.info.cursorPosition.x;
                Kernel32.FillConsoleOutputCharacterW(WindowsAnsiOutputStream.console, ' ', lengthToLastCol, this.info.cursorPosition.copy(), written);
                break;
            }
        }
    }
    
    protected void processCursorLeft(final int count) throws IOException {
        this.getConsoleInfo();
        this.info.cursorPosition.x = (short)Math.max(0, this.info.cursorPosition.x - count);
        this.applyCursorPosition();
    }
    
    protected void processCursorRight(final int count) throws IOException {
        this.getConsoleInfo();
        this.info.cursorPosition.x = (short)Math.min(this.info.window.width(), this.info.cursorPosition.x + count);
        this.applyCursorPosition();
    }
    
    protected void processCursorDown(final int count) throws IOException {
        this.getConsoleInfo();
        this.info.cursorPosition.y = (short)Math.min(this.info.size.y, this.info.cursorPosition.y + count);
        this.applyCursorPosition();
    }
    
    protected void processCursorUp(final int count) throws IOException {
        this.getConsoleInfo();
        this.info.cursorPosition.y = (short)Math.max(this.info.window.top, this.info.cursorPosition.y - count);
        this.applyCursorPosition();
    }
    
    protected void processCursorTo(final int row, final int col) throws IOException {
        this.getConsoleInfo();
        this.info.cursorPosition.y = (short)Math.max(this.info.window.top, Math.min(this.info.size.y, this.info.window.top + row - 1));
        this.info.cursorPosition.x = (short)Math.max(0, Math.min(this.info.window.width(), col - 1));
        this.applyCursorPosition();
    }
    
    protected void processCursorToColumn(final int x) throws IOException {
        this.getConsoleInfo();
        this.info.cursorPosition.x = (short)Math.max(0, Math.min(this.info.window.width(), x - 1));
        this.applyCursorPosition();
    }
    
    protected void processSetForegroundColor(final int color) throws IOException {
        this.info.attributes = (short)((this.info.attributes & 0xFFFFFFF8) | WindowsAnsiOutputStream.ANSI_FOREGROUND_COLOR_MAP[color]);
        this.applyAttribute();
    }
    
    protected void processSetBackgroundColor(final int color) throws IOException {
        this.info.attributes = (short)((this.info.attributes & 0xFFFFFF8F) | WindowsAnsiOutputStream.ANSI_BACKGROUND_COLOR_MAP[color]);
        this.applyAttribute();
    }
    
    protected void processAttributeRest() throws IOException {
        this.info.attributes = (short)((this.info.attributes & 0xFFFFFF00) | this.originalColors);
        this.negative = false;
        this.applyAttribute();
    }
    
    protected void processSetAttribute(final int attribute) throws IOException {
        switch (attribute) {
            case 1: {
                this.info.attributes |= Kernel32.FOREGROUND_INTENSITY;
                this.applyAttribute();
                break;
            }
            case 22: {
                this.info.attributes &= (short)~Kernel32.FOREGROUND_INTENSITY;
                this.applyAttribute();
                break;
            }
            case 4: {
                this.info.attributes |= Kernel32.BACKGROUND_INTENSITY;
                this.applyAttribute();
                break;
            }
            case 24: {
                this.info.attributes &= (short)~Kernel32.BACKGROUND_INTENSITY;
                this.applyAttribute();
                break;
            }
            case 7: {
                this.negative = true;
                this.applyAttribute();
                break;
            }
            case 27: {
                this.negative = false;
                this.applyAttribute();
                break;
            }
        }
    }
    
    protected void processSaveCursorPosition() throws IOException {
        this.getConsoleInfo();
        this.savedX = this.info.cursorPosition.x;
        this.savedY = this.info.cursorPosition.y;
    }
    
    protected void processRestoreCursorPosition() throws IOException {
        if (this.savedX != -1 && this.savedY != -1) {
            this.out.flush();
            this.info.cursorPosition.x = this.savedX;
            this.info.cursorPosition.y = this.savedY;
            this.applyCursorPosition();
        }
    }
    
    protected void processChangeWindowTitle(final String label) {
        Kernel32.SetConsoleTitle(label);
    }
    
    static {
        console = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
        FOREGROUND_YELLOW = (short)(Kernel32.FOREGROUND_RED | Kernel32.FOREGROUND_GREEN);
        FOREGROUND_MAGENTA = (short)(Kernel32.FOREGROUND_BLUE | Kernel32.FOREGROUND_RED);
        FOREGROUND_CYAN = (short)(Kernel32.FOREGROUND_BLUE | Kernel32.FOREGROUND_GREEN);
        FOREGROUND_WHITE = (short)(Kernel32.FOREGROUND_RED | Kernel32.FOREGROUND_GREEN | Kernel32.FOREGROUND_BLUE);
        BACKGROUND_YELLOW = (short)(Kernel32.BACKGROUND_RED | Kernel32.BACKGROUND_GREEN);
        BACKGROUND_MAGENTA = (short)(Kernel32.BACKGROUND_BLUE | Kernel32.BACKGROUND_RED);
        BACKGROUND_CYAN = (short)(Kernel32.BACKGROUND_BLUE | Kernel32.BACKGROUND_GREEN);
        BACKGROUND_WHITE = (short)(Kernel32.BACKGROUND_RED | Kernel32.BACKGROUND_GREEN | Kernel32.BACKGROUND_BLUE);
        ANSI_FOREGROUND_COLOR_MAP = new short[] { 0, Kernel32.FOREGROUND_RED, Kernel32.FOREGROUND_GREEN, WindowsAnsiOutputStream.FOREGROUND_YELLOW, Kernel32.FOREGROUND_BLUE, WindowsAnsiOutputStream.FOREGROUND_MAGENTA, WindowsAnsiOutputStream.FOREGROUND_CYAN, WindowsAnsiOutputStream.FOREGROUND_WHITE };
        ANSI_BACKGROUND_COLOR_MAP = new short[] { 0, Kernel32.BACKGROUND_RED, Kernel32.BACKGROUND_GREEN, WindowsAnsiOutputStream.BACKGROUND_YELLOW, Kernel32.BACKGROUND_BLUE, WindowsAnsiOutputStream.BACKGROUND_MAGENTA, WindowsAnsiOutputStream.BACKGROUND_CYAN, WindowsAnsiOutputStream.BACKGROUND_WHITE };
    }
}
