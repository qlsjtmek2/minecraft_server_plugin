// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.conversations;

import org.apache.commons.lang.math.NumberUtils;

public abstract class NumericPrompt extends ValidatingPrompt
{
    protected boolean isInputValid(final ConversationContext context, final String input) {
        return NumberUtils.isNumber(input) && this.isNumberValid(context, NumberUtils.createNumber(input));
    }
    
    protected boolean isNumberValid(final ConversationContext context, final Number input) {
        return true;
    }
    
    protected Prompt acceptValidatedInput(final ConversationContext context, final String input) {
        try {
            return this.acceptValidatedInput(context, NumberUtils.createNumber(input));
        }
        catch (NumberFormatException e) {
            return this.acceptValidatedInput(context, NumberUtils.INTEGER_ZERO);
        }
    }
    
    protected abstract Prompt acceptValidatedInput(final ConversationContext p0, final Number p1);
    
    protected String getFailedValidationText(final ConversationContext context, final String invalidInput) {
        if (NumberUtils.isNumber(invalidInput)) {
            return this.getFailedValidationText(context, NumberUtils.createNumber(invalidInput));
        }
        return this.getInputNotNumericText(context, invalidInput);
    }
    
    protected String getInputNotNumericText(final ConversationContext context, final String invalidInput) {
        return null;
    }
    
    protected String getFailedValidationText(final ConversationContext context, final Number invalidInput) {
        return null;
    }
}
