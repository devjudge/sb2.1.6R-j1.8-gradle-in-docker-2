/*
 * @(#)DBFieldType.java
 *
 * Copyright 2009 by Firm58, Inc.,
 * All rights reserved.
 *
 * $Id: DBFieldType.java,v 1.9 2009/01/29 22:27:23 kleggett Exp $
 */
package org.codejudge.sb.common.model.dbmap;

import org.codejudge.sb.common.model.F58Enum;
import org.codejudge.sb.common.util.CustomDateEditor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Date;

/**
 * An enumeration of column types in the firm58 database
 *
 * @author jqian
 */
public enum DBFieldType implements F58Enum
{
    String(java.lang.String.class),
    Timestamp(Date.class),
    Integer(java.lang.Integer.class),
    Long(java.lang.Long.class),
    Double(java.lang.Double.class),
    Enumeration(java.lang.String.class),
    Boolean(java.lang.String.class),
    DomainValue(java.lang.String.class);

    private Class<?> javaType;

    private static BeanWrapper converterUtil = new BeanWrapperImpl();

    static
    {
        converterUtil.registerCustomEditor(Date.class, new CustomDateEditor());
    }

    private DBFieldType(Class<?> javaType)
    {
        this.javaType = javaType;
    }

    public Object cast(Object value)
    {
        return converterUtil.convertIfNecessary(value, javaType);
    }

    public Object cast(Object value, Class<? extends Enum> enumType)
    {
        return converterUtil.convertIfNecessary(value, enumType);
    }

    public static DBFieldType valueOfIgnoreCase(String type)
    {
        if (type != null)
        {
            for (DBFieldType dbft : values())
            {
                if (dbft.name().equalsIgnoreCase(type))
                {
                    return dbft;
                }
            }
        }
        return null;
    }


    @Override
    public String getDisplayName()
    {
        return this.name();
    }
}
