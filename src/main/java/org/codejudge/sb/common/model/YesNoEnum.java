package org.codejudge.sb.common.model;

import java.util.Map;
import java.util.TreeMap;

/**
 * An Enumeration holding values "Yes" and "No".
 *
 * @see ActiveStatusEnum $Id: YesNoEnum.java,v 1.9 2008/09/05 15:23:56 tgee Exp $
 */
public enum YesNoEnum implements F58Enum {
    YES("Yes"),
    NO("No");

    private final String displayName;

    private YesNoEnum(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Returns 'true' if the enum value is "YES".
     */
    public boolean isTrue() {
        return (this == YES);
    }

    /**
     * Returns the YesNoEnum appropriate matching the incoming boolean.
     */
    public static YesNoEnum getYesNoForBoolean(boolean b) {
        return (b ? YES : NO);
    }

    /**
     * Gets the YesNoEnum for the value of the incoming string, ignoring case. Returns null if supplied with null.
     *
     * @throws IllegalArgumentException if the value cannot be parsed as 'yes' or 'no'.
     */
    public static YesNoEnum valueOfIgnoreCase(String string) {
        if (string == null) {
            return null;
        } else if (YES.toString().equalsIgnoreCase(string)) {
            return YES;
        } else if (NO.toString().equalsIgnoreCase(string)) {
            return NO;
        }
        throw new IllegalArgumentException("Cannot parse '" + string + "' into a YES or NO.");
    }

    public static Map<String, YesNoEnum> getValues() {
        Map<String, YesNoEnum> results = new TreeMap<String, YesNoEnum>();
        for (YesNoEnum e : values()) {
            results.put(e.displayName, e);
        }

        return results;
    }

    public static Map<String, String> getSelectListValues() {
        Map<String, String> results = new TreeMap<String, String>();
        for (YesNoEnum e : values()) {
            results.put(e.displayName, e.toString());
        }

        return results;
    }

    public static boolean isTrue(YesNoEnum yn) {
        return yn != null && yn.isTrue();
    }
}
