// 
// Decompiled by Procyon v0.5.30
// 

package org.fusesource.jansi.internal;

import java.io.IOException;

public class WindowsSupport
{
    public static String getLastErrorMessage() {
        final int errorCode = Kernel32.GetLastError();
        final int bufferSize = 160;
        final byte[] data = new byte[bufferSize];
        Kernel32.FormatMessageW(Kernel32.FORMAT_MESSAGE_FROM_SYSTEM, 0L, errorCode, 0, data, bufferSize, null);
        return new String(data);
    }
    
    public static int readByte() {
        return Kernel32._getch();
    }
    
    public static int getConsoleMode() {
        final long hConsole = Kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
        if (hConsole == Kernel32.INVALID_HANDLE_VALUE) {
            return -1;
        }
        final int[] mode = { 0 };
        if (Kernel32.GetConsoleMode(hConsole, mode) == 0) {
            return -1;
        }
        return mode[0];
    }
    
    public static void setConsoleMode(final int mode) {
        final long hConsole = Kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
        if (hConsole == Kernel32.INVALID_HANDLE_VALUE) {
            return;
        }
        Kernel32.SetConsoleMode(hConsole, mode);
    }
    
    public static int getWindowsTerminalWidth() {
        final long outputHandle = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
        final Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        Kernel32.GetConsoleScreenBufferInfo(outputHandle, info);
        return info.windowWidth();
    }
    
    public static int getWindowsTerminalHeight() {
        final long outputHandle = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
        final Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        Kernel32.GetConsoleScreenBufferInfo(outputHandle, info);
        return info.windowHeight();
    }
    
    public static int writeConsole(final String msg) {
        final long hConsole = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
        if (hConsole == Kernel32.INVALID_HANDLE_VALUE) {
            return 0;
        }
        final char[] chars = msg.toCharArray();
        final int[] written = { 0 };
        if (Kernel32.WriteConsoleW(hConsole, chars, chars.length, written, 0L) != 0) {
            return written[0];
        }
        return 0;
    }
    
    public static Kernel32.INPUT_RECORD[] readConsoleInput(final int count) throws IOException {
        final long hConsole = Kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
        if (hConsole == Kernel32.INVALID_HANDLE_VALUE) {
            return null;
        }
        return Kernel32.readConsoleKeyInput(hConsole, count, false);
    }
    
    public static Kernel32.INPUT_RECORD[] peekConsoleInput(final int count) throws IOException {
        final long hConsole = Kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
        if (hConsole == Kernel32.INVALID_HANDLE_VALUE) {
            return null;
        }
        return Kernel32.readConsoleKeyInput(hConsole, count, true);
    }
    
    public static void flushConsoleInputBuffer() {
        final long hConsole = Kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
        if (hConsole == Kernel32.INVALID_HANDLE_VALUE) {
            return;
        }
        Kernel32.FlushConsoleInputBuffer(hConsole);
    }
}
