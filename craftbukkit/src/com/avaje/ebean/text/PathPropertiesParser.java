// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text;

class PathPropertiesParser
{
    private final PathProperties pathProps;
    private final String source;
    private final char[] chars;
    private final int eof;
    private int pos;
    private int startPos;
    private PathProperties.Props currentPathProps;
    
    public static PathProperties parse(final String source) {
        return new PathPropertiesParser(source).pathProps;
    }
    
    private PathPropertiesParser(String src) {
        if (src.startsWith(":")) {
            src = src.substring(1);
        }
        this.pathProps = new PathProperties();
        this.source = src;
        this.chars = src.toCharArray();
        this.eof = this.chars.length;
        if (this.eof > 0) {
            this.currentPathProps = this.pathProps.getRootProperties();
            this.parse();
        }
    }
    
    private String getPath() {
        do {
            final char c1 = this.chars[this.pos++];
            switch (c1) {
                case '(': {
                    return this.currentWord();
                }
                default: {
                    continue;
                }
            }
        } while (this.pos < this.eof);
        throw new RuntimeException("Hit EOF while reading sectionTitle from " + this.startPos);
    }
    
    private void parse() {
        do {
            final String path = this.getPath();
            this.pushPath(path);
            this.parseSection();
        } while (this.pos < this.eof);
    }
    
    private void parseSection() {
        do {
            final char c1 = this.chars[this.pos++];
            switch (c1) {
                case ':': {
                    this.startPos = this.pos;
                }
                default: {
                    continue;
                }
                case '(': {
                    this.addSubpath();
                    continue;
                }
                case ',': {
                    this.addCurrentProperty();
                    continue;
                }
                case ')': {
                    this.addCurrentProperty();
                    this.popSubpath();
                    continue;
                }
            }
        } while (this.pos < this.eof);
    }
    
    private void addSubpath() {
        this.pushPath(this.currentWord());
    }
    
    private void addCurrentProperty() {
        final String w = this.currentWord();
        if (w.length() > 0) {
            this.currentPathProps.addProperty(w);
        }
    }
    
    private String currentWord() {
        if (this.startPos == this.pos) {
            return "";
        }
        final String currentWord = this.source.substring(this.startPos, this.pos - 1);
        this.startPos = this.pos;
        return currentWord;
    }
    
    private void pushPath(final String title) {
        if (!"".equals(title)) {
            this.currentPathProps = this.currentPathProps.addChild(title);
        }
    }
    
    private void popSubpath() {
        this.currentPathProps = this.currentPathProps.getParent();
    }
}
