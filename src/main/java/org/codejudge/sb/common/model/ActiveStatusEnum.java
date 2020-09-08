package org.codejudge.sb.common.model;

import java.util.Map;
import java.util.TreeMap;

/**
 * Yet another boolean enum.
 * This time it's "active" and "inactive".
 */
public enum ActiveStatusEnum implements F58Enum
{
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String displayName;

    private ActiveStatusEnum(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName()
    {
        return this.displayName;
    }

    /**
     * Returns 'true' if this value is {@link #ACTIVE}
     */
    public boolean isActive()
    {
        return (this == ACTIVE);
    }

    /**
     * Returns {@link #ACTIVE} if the supplied boolean is 'true', otherwise returns {@link #INACTIVE}
     */
    public static ActiveStatusEnum getStatus(boolean isActive)
    {
        return (isActive ? ACTIVE : INACTIVE);
    }

    /**
     * Gets the ActiveStatusEnum for the value of the incoming string, ignoring case.
     * Returns null if supplied with null.
     *
     * @throws IllegalArgumentException if the value cannot be parsed as 'active' or 'inactive'.
     */
    public static ActiveStatusEnum valueOfIgnoreCase(String string)
    {
        if (string == null)
        {
            return null;
        }
        else if (ACTIVE.toString().equalsIgnoreCase(string))
        {
            return ACTIVE;
        }
        else if (INACTIVE.toString().equalsIgnoreCase(string))
        {
            return INACTIVE;
        }
        throw new IllegalArgumentException("Cannot parse '" + string + "' into a ACTIVE or INACTIVE.");
    }

    public static Map<String, ActiveStatusEnum> getValues()
    {
        Map<String, ActiveStatusEnum> results = new TreeMap<String, ActiveStatusEnum>();
        for (ActiveStatusEnum e : values())
        {
            results.put(e.displayName, e);
        }

        return results;
    }

    public static Map<String, String> getSelectListValues()
    {
        Map<String, String> results = new TreeMap<String, String>();
        for (ActiveStatusEnum e : values())
        {
            results.put(e.displayName, e.toString());
        }

        return results;
    }
}
