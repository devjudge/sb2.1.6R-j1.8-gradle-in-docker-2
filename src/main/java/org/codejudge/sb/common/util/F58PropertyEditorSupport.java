package org.codejudge.sb.common.util;

import java.beans.PropertyEditorSupport;

/**
 * @author $Author: ksagon $
 * @version $Revision: 1.1 $
 */
public class F58PropertyEditorSupport extends PropertyEditorSupport
{
    public F58PropertyEditorSupport()
    {
    }

    public F58PropertyEditorSupport(Object source)
    {
        super(source);
    }

    @Override
    public String getAsText()
    {
        return (getValue() != null) ? this.getValue().toString() : null;
    }
}
