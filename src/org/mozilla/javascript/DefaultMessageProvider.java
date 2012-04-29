package org.mozilla.javascript;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.harmony.Locale;
import org.mozilla.javascript.ScriptRuntime.MessageProvider;

/* OPT there's a noticable delay for the first error!  Maybe it'd
 * make sense to use a ListResourceBundle instead of a properties
 * file to avoid (synchronized) text parsing.
 */
class DefaultMessageProvider implements MessageProvider {
    public String getMessage(String messageId, Object[] arguments) {
        final String defaultResource
            = "org.mozilla.javascript.resources.Messages";

        Context cx = Context.getCurrentContext();
        Locale locale = cx != null ? cx.getLocale() : Locale.getDefault();

        // ResourceBundle does caching.
        ResourceBundle rb = ResourceBundle.getBundle(defaultResource, new java.util.Locale(locale.getCountry()));

        String formatString;
        try {
            formatString = rb.getString(messageId);
        } catch (java.util.MissingResourceException mre) {
            throw new RuntimeException
                ("no message resource found for message property "+ messageId);
        }

        /*
         * It's OK to format the string, even if 'arguments' is null;
         * we need to format it anyway, to make double ''s collapse to
         * single 's.
         */
        MessageFormat formatter = new MessageFormat(formatString);
        return formatter.format(arguments);
    }
}