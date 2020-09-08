package org.codejudge.sb.common.model;


import org.codejudge.sb.common.model.dbmap.DBFieldType;
import org.codejudge.sb.common.model.dbmap.DBMapWithFieldTypeInfo;

/**
 * A DBMap for the 'co_audit' table.
 *
 * @author $Author: ksagon $
 * @version $Revision: 1.1 $
 * @since NITROGEN
 */
public enum AuditDBMap implements DBMapWithFieldTypeInfo {
    TABLE("co_audit", "aud"),
    FIELD_ID("id", DBFieldType.String),
    FIELD_LAST_MODIFIED_DATE("last_modified_date", DBFieldType.Timestamp),
    FIELD_LAST_MODIFIED_BY("last_modified_by", DBFieldType.String),
    FIELD_REFERENCE_ID("reference_id", DBFieldType.String),
    FIELD_USER_NAME("user_name", DBFieldType.String),
    FIELD_AUDIT_DATE("audit_date", DBFieldType.Timestamp),
    FIELD_AUDIT_TYPE("audit_type", DBFieldType.Enumeration),
    FIELD_AUDIT_ACTION("audit_action", DBFieldType.String),
    FIELD_AUDIT_REASON("audit_reason", DBFieldType.String),
    FIELD_AUDIT_COMMENT("audit_comment", DBFieldType.String);

    private String dbName;
    private String alias;

    /**
     * The database column type
     */
    private DBFieldType fieldType = null;

    private AuditDBMap(String dbName, String dbAlias) {
        this.dbName = dbName;
        this.alias = dbAlias;
    }

    private AuditDBMap(String dbName, DBFieldType fieldType) {
        this(dbName, "facct_" + dbName);
        this.fieldType = fieldType;
    }

    @Override
    public String getDBName() {
        return this.dbName;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getQualifiedName() {
        return TABLE.getAlias() + "." + this.dbName;
    }

    public String getAliasedDBName() {
        return this.getQualifiedName() + " " + this.alias;
    }

    /**
     * Get the field type of this column or null if this field is not a db column
     */
    @Override
    public DBFieldType getFieldType() {
        return this.fieldType;
    }

    /**
     * Get the qualified name for the column. Doesn't result the query to alias the table
     */
    public String getQualifiedNameForSQL() {
        return TABLE.getDBName() + "." + this.dbName;
    }
}
