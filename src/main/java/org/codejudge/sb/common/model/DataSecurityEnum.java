/*
 * @(#)DataSecurityEnum.java
 *
 * Copyright 2009 by Firm58, Inc.,
 * All rights reserved.
 *
 * $Id: DataSecurityEnum.java,v 1.3 2009/01/13 17:57:19 kleggett Exp $
 */
package org.codejudge.sb.common.model;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author $Author: kleggett $
 * @version $Revision: 1.3 $
 * @since ?
 */
public enum DataSecurityEnum implements F58Enum
{
    CUSTOMER("Customer"),
    HIERARCHY("Hierarchy"),
    ACCOUNT("Account");

    private final String displayName;

    private DataSecurityEnum(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName()
    {
        return this.displayName;
    }

    public static Map<String, DataSecurityEnum> getValues()
    {
        Map<String, DataSecurityEnum> results = new TreeMap<String, DataSecurityEnum>();
        for (DataSecurityEnum e : values())
        {
            results.put(e.displayName, e);
        }

        return results;
    }

    public static DataSecurityEnum valueOfIgnoreCase(String dataSecurity)
    {
        if (dataSecurity != null)
        {
            for (DataSecurityEnum type : values())
            {
                if (type.name().equalsIgnoreCase(dataSecurity))
                {
                    return type;
                }
            }
        }
        return null;
    }
}
