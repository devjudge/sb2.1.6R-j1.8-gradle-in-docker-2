package org.codejudge.sb.common.util;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;

public class F58ToStringAdapter
{
    public static final StandardToStringStyle MODEL_STRING_STYLE;

    static
    {
        MODEL_STRING_STYLE = new StandardToStringStyle();
        MODEL_STRING_STYLE.setUseIdentityHashCode(false);
        MODEL_STRING_STYLE.setUseShortClassName(true);
        MODEL_STRING_STYLE.setDefaultFullDetail(true);
    }

    public String toString()
    {
        final ReflectionToStringBuilder sb = new ReflectionToStringBuilder(this, MODEL_STRING_STYLE);

        sb.setExcludeFieldNames(getToStringPropertyExcludes());

        return sb.toString();
    }

    protected String[] getToStringPropertyExcludes()
    {
        return new String[] { };
    }

    public static String toString(Object obj, String[] excludeFieldNames)
    {
        final ReflectionToStringBuilder sb = new ReflectionToStringBuilder(obj, MODEL_STRING_STYLE);
        if (excludeFieldNames != null && excludeFieldNames.length > 0)
        {
            sb.setExcludeFieldNames(excludeFieldNames);
        }
        return sb.toString();
    }
}
