package org.codejudge.sb.common.model.dbmap;

import java.io.Serializable;

/**
 * The base interface for all Enums holding information on fields in the
 * database tables comprising the F58 schema.
 * $Id: DBMap.java,v 1.3 2007/07/12 18:21:04 tgee Exp $
 */
public interface DBMap extends Serializable
{
    public String getDBName();

    public String getAlias();

    public String getQualifiedName();
}
