package org.codejudge.sb.common.model.dbmap;

/**
 * DBMap that contains field type information for each db column
 * $Id: DBMapWithFieldTypeInfo.java,v 1.3 2008/09/16 14:14:24 tgee Exp $
 */
public interface DBMapWithFieldTypeInfo extends DBMap
{

    /**
     * Return column field type or null if this is not a column
     */
    public DBFieldType getFieldType();
}
