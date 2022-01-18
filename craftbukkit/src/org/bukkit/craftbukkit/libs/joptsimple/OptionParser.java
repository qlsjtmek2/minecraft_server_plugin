// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.craftbukkit.libs.joptsimple.util.KeyValuePair;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import org.bukkit.craftbukkit.libs.joptsimple.internal.AbbreviationMap;

public class OptionParser
{
    private final AbbreviationMap<AbstractOptionSpec<?>> recognizedOptions;
    private OptionParserState state;
    private boolean posixlyCorrect;
    
    public OptionParser() {
        this.recognizedOptions = new AbbreviationMap<AbstractOptionSpec<?>>();
        this.state = OptionParserState.moreOptions(false);
    }
    
    public OptionParser(final String optionSpecification) {
        this();
        new OptionSpecTokenizer(optionSpecification).configure(this);
    }
    
    public OptionSpecBuilder accepts(final String option) {
        return this.acceptsAll(Collections.singletonList(option));
    }
    
    public OptionSpecBuilder accepts(final String option, final String description) {
        return this.acceptsAll(Collections.singletonList(option), description);
    }
    
    public OptionSpecBuilder acceptsAll(final Collection<String> options) {
        return this.acceptsAll(options, "");
    }
    
    public OptionSpecBuilder acceptsAll(final Collection<String> options, final String description) {
        if (options.isEmpty()) {
            throw new IllegalArgumentException("need at least one option");
        }
        ParserRules.ensureLegalOptions(options);
        return new OptionSpecBuilder(this, options, description);
    }
    
    public void posixlyCorrect(final boolean setting) {
        this.posixlyCorrect = setting;
        this.state = OptionParserState.moreOptions(setting);
    }
    
    boolean posixlyCorrect() {
        return this.posixlyCorrect;
    }
    
    public void recognizeAlternativeLongOptions(final boolean recognize) {
        if (recognize) {
            this.recognize(new AlternativeLongOptionSpec());
        }
        else {
            this.recognizedOptions.remove(String.valueOf("W"));
        }
    }
    
    void recognize(final AbstractOptionSpec<?> spec) {
        this.recognizedOptions.putAll(spec.options(), spec);
    }
    
    public void printHelpOn(final OutputStream sink) throws IOException {
        this.printHelpOn(new OutputStreamWriter(sink));
    }
    
    public void printHelpOn(final Writer sink) throws IOException {
        sink.write(new HelpFormatter().format(this.recognizedOptions.toJavaUtilMap()));
        sink.flush();
    }
    
    public OptionSet parse(final String... arguments) {
        final ArgumentList argumentList = new ArgumentList(arguments);
        final OptionSet detected = new OptionSet(this.defaultValues());
        while (argumentList.hasMore()) {
            this.state.handleArgument(this, argumentList, detected);
        }
        this.reset();
        return detected;
    }
    
    void handleLongOptionToken(final String candidate, final ArgumentList arguments, final OptionSet detected) {
        final KeyValuePair optionAndArgument = parseLongOptionWithArgument(candidate);
        if (!this.isRecognized(optionAndArgument.key)) {
            throw OptionException.unrecognizedOption(optionAndArgument.key);
        }
        final AbstractOptionSpec<?> optionSpec = this.specFor(optionAndArgument.key);
        optionSpec.handleOption(this, arguments, detected, optionAndArgument.value);
    }
    
    void handleShortOptionToken(final String candidate, final ArgumentList arguments, final OptionSet detected) {
        final KeyValuePair optionAndArgument = parseShortOptionWithArgument(candidate);
        if (this.isRecognized(optionAndArgument.key)) {
            this.specFor(optionAndArgument.key).handleOption(this, arguments, detected, optionAndArgument.value);
        }
        else {
            this.handleShortOptionCluster(candidate, arguments, detected);
        }
    }
    
    private void handleShortOptionCluster(final String candidate, final ArgumentList arguments, final OptionSet detected) {
        final char[] options = extractShortOptionsFrom(candidate);
        this.validateOptionCharacters(options);
        final AbstractOptionSpec<?> optionSpec = this.specFor(options[0]);
        if (optionSpec.acceptsArguments() && options.length > 1) {
            final String detectedArgument = String.valueOf(options, 1, options.length - 1);
            optionSpec.handleOption(this, arguments, detected, detectedArgument);
        }
        else {
            for (final char each : options) {
                this.specFor(each).handleOption(this, arguments, detected, null);
            }
        }
    }
    
    void noMoreOptions() {
        this.state = OptionParserState.noMoreOptions();
    }
    
    boolean looksLikeAnOption(final String argument) {
        return ParserRules.isShortOptionToken(argument) || ParserRules.isLongOptionToken(argument);
    }
    
    private boolean isRecognized(final String option) {
        return this.recognizedOptions.contains(option);
    }
    
    private AbstractOptionSpec<?> specFor(final char option) {
        return this.specFor(String.valueOf(option));
    }
    
    private AbstractOptionSpec<?> specFor(final String option) {
        return this.recognizedOptions.get(option);
    }
    
    private void reset() {
        this.state = OptionParserState.moreOptions(this.posixlyCorrect);
    }
    
    private static char[] extractShortOptionsFrom(final String argument) {
        final char[] options = new char[argument.length() - 1];
        argument.getChars(1, argument.length(), options, 0);
        return options;
    }
    
    private void validateOptionCharacters(final char[] options) {
        int i = 0;
        while (i < options.length) {
            final String option = String.valueOf(options[i]);
            if (!this.isRecognized(option)) {
                throw OptionException.unrecognizedOption(option);
            }
            if (this.specFor(option).acceptsArguments()) {
                if (i > 0) {
                    throw OptionException.illegalOptionCluster(option);
                }
            }
            else {
                ++i;
            }
        }
    }
    
    private static KeyValuePair parseLongOptionWithArgument(final String argument) {
        return KeyValuePair.valueOf(argument.substring(2));
    }
    
    private static KeyValuePair parseShortOptionWithArgument(final String argument) {
        return KeyValuePair.valueOf(argument.substring(1));
    }
    
    private Map<String, List<?>> defaultValues() {
        final Map<String, List<?>> defaults = new HashMap<String, List<?>>();
        for (final Map.Entry<String, AbstractOptionSpec<?>> each : this.recognizedOptions.toJavaUtilMap().entrySet()) {
            defaults.put(each.getKey(), each.getValue().defaultValues());
        }
        return defaults;
    }
}
