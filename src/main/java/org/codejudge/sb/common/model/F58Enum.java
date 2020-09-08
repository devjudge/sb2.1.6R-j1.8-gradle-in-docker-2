package org.codejudge.sb.common.model;

import java.io.Serializable;

/**
 * An interface all {@link Enum Enums} in the F58 codebase.
 * $Id: F58Enum.java,v 1.3 2007/07/12 16:59:58 tgee Exp $
 */
public interface F58Enum extends Serializable
{
    public String getDisplayName();

    public String name();
}
