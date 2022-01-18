// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

abstract class OptionParserState
{
    static OptionParserState noMoreOptions() {
        return new OptionParserState() {
            protected void handleArgument(final OptionParser parser, final ArgumentList arguments, final OptionSet detectedOptions) {
                detectedOptions.addNonOptionArgument(arguments.next());
            }
        };
    }
    
    static OptionParserState moreOptions(final boolean posixlyCorrect) {
        return new OptionParserState() {
            protected void handleArgument(final OptionParser parser, final ArgumentList arguments, final OptionSet detectedOptions) {
                final String candidate = arguments.next();
                if (ParserRules.isOptionTerminator(candidate)) {
                    parser.noMoreOptions();
                }
                else if (ParserRules.isLongOptionToken(candidate)) {
                    parser.handleLongOptionToken(candidate, arguments, detectedOptions);
                }
                else if (ParserRules.isShortOptionToken(candidate)) {
                    parser.handleShortOptionToken(candidate, arguments, detectedOptions);
                }
                else {
                    if (posixlyCorrect) {
                        parser.noMoreOptions();
                    }
                    detectedOptions.addNonOptionArgument(candidate);
                }
            }
        };
    }
    
    protected abstract void handleArgument(final OptionParser p0, final ArgumentList p1, final OptionSet p2);
}
