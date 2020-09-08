package org.codejudge.sb.common.persistence.core;

import org.apache.commons.lang3.StringUtils;
import org.codejudge.sb.common.model.dbmap.DBMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A class providing helpful static methods for the DAO classes.
 *
 * $Id: DBHelper.java,v 1.9 2008/11/10 22:48:46 tgee Exp $
 */
public class DBHelper {

    private static final Logger logger = LoggerFactory.getLogger(DBHelper.class);
    /**
     * determine if row counts should be logged when closing result sets
     */
    private static final AtomicBoolean shouldLogRowCount = new AtomicBoolean(true);
    private static final int UNIQUE_CONSTRAINT_VIOLATION = 1;

    public static final String CLIENT_IDENTIFIER_CALL = "securityPkg.getContext()";

    /**
     * Disallow creation.
     */
    private DBHelper() {
    }

    public static boolean isUniqueConstraintViolation(SQLException e) {
        if (e != null) {
            return e.getErrorCode() == UNIQUE_CONSTRAINT_VIOLATION;
        } else {
            return false;
        }
    }

    /**
     * Closes the supplied statement Allows a null submission and catches (and ignores) all SQLExceptions on the close.
     */
    public static void close(Statement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Closes the supplied ResultSet. Allows a null submission and catches (and ignores) all SQLExceptions on the
     * close.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            if (logger.isDebugEnabled() && shouldLogRowCount.get()) {
                try {
                    int row = resultSet.getRow();
                    logger.debug("row count: " + row);
                } catch (SQLFeatureNotSupportedException e) {
                    // ResultSet.getRow() can throw a SQLFeatureNotSupportedException.
                    // In that case, turn off logging instead of throwing up all over the place
                    logger.debug("disabling row count logging.");
                    shouldLogRowCount.set(false);
                } catch (SQLException e) {
                    // not sure why we'd be here.  log it
                    logger.debug(e.getMessage(), e);
                }
            }

            try {
                resultSet.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }


    /**
     * Builds a String of comma separated question marks of the length requested.
     *
     * @see #buildQMarkString(int, boolean)
     */
    public static String buildQMarkString(int length) {
        return buildQMarkString(length, false);
    }

    /**
     * Builds a String of comma separated question marks of the length requested. Optionally wraps the String in
     * parenthesis.
     *
     * @param length The number of question marks to generate, must be greater than zero.
     * @param wrapInParens 'true' to surround returned String in parens.
     */
    public static String buildQMarkString(int length, boolean wrapInParens) {
        StringBuilder sb = new StringBuilder();
        buildQMarkString(length, wrapInParens, sb);
        return sb.toString();
    }

    /**
     * Adds to a StringBuilder comma separated question marks of the length requested. Optionally wraps the String in
     * parenthesis.
     *
     * @param length The number of question marks to generate, must be greater than zero.
     * @param wrapInParens 'true' to surround returned String in parens.
     * @param sb The StringBuilder to add to
     */
    public static void buildQMarkString(int length, boolean wrapInParens, StringBuilder sb) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be > 0.  supplied:" + length);
        }

        if (wrapInParens) {
            sb.append("(");
        }
        for (int x = 0; x < length; x++) {
            if (x > 0) {
                sb.append(",");
            }
            sb.append("?");
        }
        if (wrapInParens) {
            sb.append(")");
        }
    }


    /**
     * Returns a comma separated String of fields from a {@link DBMap}. Fields in generated string are in order that
     * they're specified in the {@link DBMap}. Exploits the pattern that F58 DBMap Enums use constants starting with
     * "FIELD" to denote fields.
     *
     * @param enumType The Class for the {@link DBMap} Enum to process.
     * @param aliased If 'true', uses the aliased version of the field name; if 'false' just uses the simple field
     * name.
     * @throws IllegalArgumentException If the supplied Enum doesn't implement the {@link DBMap} interface.
     */
    public static <T extends Enum<T>> String buildFieldList(Class<T> enumType, boolean aliased) {
        // quick check
        ensureClassIsDBMap(enumType);
        String fieldList[] = getFieldList(enumType, aliased);
        StringBuilder ret = new StringBuilder();
        for (String field : fieldList) {
            if (ret.length() > 0) {
                ret.append(",");
            }
            ret.append(field);
        }
        return ret.toString();
    }


    /**
     * Generates a SELECT statement given the fields for which to build WHERE clauses. Generated SELECT statement
     * returns all fields.
     *
     * @param enumType The Class for the {@link DBMap} Enum to process.
     * @param whereFields Fields from the DBMap for which to generate where clauses for; for each supplied, the
     * generated where clause will include "[fieldname] = ?".
     * @throws IllegalArgumentException If the supplied Enum doesn't implement the {@link DBMap} interface.
     */
    public static <T extends Enum<T>> String buildSimpleSelect(Class<T> enumType, DBMap... whereFields) {
        return buildSelect(enumType,
                buildWhereClausesForFieldList(whereFields, getTableRef(enumType).getAlias() + "."));
    }

    /**
     * Generates a SELECT statement given the WHERE clauses. Generated SELECT statement returns all fields.
     *
     * @param enumType The Class for the {@link DBMap} Enum to process.
     * @param whereClauses The (variable length) list of String where clauses to append to the SELECT; clauses are
     * appended as is, no munging.
     * @throws IllegalArgumentException If the supplied Enum doesn't implement the {@link DBMap} interface.
     */
    public static <T extends Enum<T>> String buildSelect(Class<T> enumType, String... whereClauses) {
        // quick check
        ensureClassIsDBMap(enumType);

        DBMap tableRef = getTableRef(enumType);

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ");
        sb.append(buildFieldList(enumType, true));
        sb.append(" FROM ");
        sb.append(tableRef.getDBName()).append(" ").append(tableRef.getAlias());
        sb.append(assembleWhereClauses(whereClauses, true));

        return sb.toString();
    }


    /**
     * Generates a simple UPDATE statement. The update statement will update all fields, handle the LAST_MODIFIED*
     * fields and use the FIELD_ID field in the update.
     *
     * @throws IllegalArgumentException If the supplied Enum doesn't implement the {@link DBMap} interface.
     * @throws IllegalArgumentException If an ID field doesn't exist.
     */
    public static <T extends Enum<T>> String buildSimpleUpdate(Class<T> enumType) {
        // quick check
        ensureClassIsDBMap(enumType);
        DBMap idField = (DBMap) Enum.valueOf(enumType, "FIELD_ID");
        if (idField == null) {
            throw new IllegalArgumentException("Supplied DBMap does not have an entry 'FIELD_ID'");
        }
        return buildUpdate(enumType, true, idField);
    }

    /**
     * Generates an UPDATE statement. Items specified in WHERE clauses will not be included in the set of updated
     * fields.
     *
     * @param enumType The Class for the {@link DBMap} Enum to process.
     * @param autoLastModifiedFields if 'true', will automatically handle DBMap elements named 'FIELD_LAST_MODIFIED_BY'
     * and 'FIELD_LAST_MODIFIED_DATE'.
     * @param whereFields Fields from the DBMap for which to generate where clauses for; for each supplied, the
     * generated where clause will include "[fieldname] = ?".
     * @throws IllegalArgumentException If the supplied Enum doesn't implement the {@link DBMap} interface.
     * @throws IllegalArgumentException If the whereFields are not supplied or are null.
     */
    public static <T extends Enum<T>> String buildUpdate(Class<T> enumType, boolean autoLastModifiedFields,
            DBMap... whereFields) {
        // quick check
        ensureClassIsDBMap(enumType);

        // another quick check
        if ((whereFields == null) || (whereFields.length == 0)) {
            throw new IllegalArgumentException("where fields must be supplied");
        }

        // Build set of all "where" fields so they can be left out
        // of the updated field set.
        Set<String> whereFieldSet = new HashSet<>();
        for (DBMap whereField : whereFields) {
            whereFieldSet.add(whereField.getDBName());
        }

        StringBuilder sb = new StringBuilder();

        sb.append("UPDATE ");
        sb.append(getTableRef(enumType).getDBName());
        sb.append(" SET ");
        boolean first = true;
        for (String field : getFieldList(enumType, false)) {
            // process field
            if (autoLastModifiedFields && field.equals("last_modified_by")) {
                if (!first) {
                    sb.append(",");
                }
                sb.append(field).append(" = sys_context('userenv','client_identifier')");
                first = false;
            } else if (autoLastModifiedFields && field.equals("last_modified_date")) {
                if (!first) {
                    sb.append(",");
                }
                sb.append(field).append(" = sysdate");
                first = false;
            } else if (!whereFieldSet.contains(field)) {
                if (!first) {
                    sb.append(",");
                }
                sb.append(field).append(" = ?");
                first = false;
            }
        }
        sb.append(assembleWhereClauses(buildWhereClausesForFieldList(whereFields, null), true));

        return sb.toString();
    }


    /**
     * Generates an INSERT statement.
     *
     * @param enumType The Class for the {@link DBMap} Enum to process.
     * @param autoLastModifiedFields if 'true', will automatically handle DBMap elements named 'FIELD_LAST_MODIFIED_BY'
     * and 'FIELD_LAST_MODIFIED_DATE'.
     * @throws IllegalArgumentException If the supplied Enum doesn't implement the {@link DBMap} interface.
     */
    public static <T extends Enum<T>> String buildInsert(Class<T> enumType, boolean autoLastModifiedFields) {
        // quick check
        ensureClassIsDBMap(enumType);

        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO ");
        sb.append(getTableRef(enumType).getDBName());
        sb.append(" (");

        StringBuilder values = new StringBuilder();
        boolean first = true;
        for (String field : getFieldList(enumType, false)) {
            // process field
            if (!first) {
                sb.append(",");
                values.append(",");
            }
            first = false;
            sb.append(field);
            if (autoLastModifiedFields && field.equals("last_modified_by")) {
                values.append("sys_context('userenv','client_identifier')");
            } else if (autoLastModifiedFields && field.equals("last_modified_date")) {
                values.append("sysdate");
            } else {
                values.append("?");
            }
        }
        sb.append(") VALUES (");
        sb.append(values);
        sb.append(")");

        return sb.toString();
    }


    /**
     * Generates a DELETE statement given the fields for which to build WHERE clauses.
     *
     * @param enumType The Class for the {@link DBMap} Enum to process.
     * @param whereFields Fields from the DBMap for which to generate where clauses for; for each supplied, the
     * generated where clause will include "[fieldname] = ?".
     * @throws IllegalArgumentException If the supplied Enum doesn't implement the {@link DBMap} interface.
     */
    public static <T extends Enum<T>> String buildSimpleDelete(Class<T> enumType, DBMap... whereFields) {
        return buildDelete(enumType,
                buildWhereClausesForFieldList(whereFields, getTableRef(enumType).getAlias() + "."));
    }

    /**
     * Generates a DELETE statement given the WHERE clauses.
     *
     * @param enumType The Class for the {@link DBMap} Enum to process.
     * @param whereClauses The (variable length) list of String where clauses to append to the DELETE; clauses are
     * appended as is, no munging.
     * @throws IllegalArgumentException If the supplied Enum doesn't implement the {@link DBMap} interface.
     */
    public static <T extends Enum<T>> String buildDelete(Class<T> enumType, String... whereClauses) {
        // quick check
        ensureClassIsDBMap(enumType);

        DBMap tableRef = getTableRef(enumType);

        StringBuilder sb = new StringBuilder();

        sb.append("DELETE FROM ");
        sb.append(tableRef.getDBName()).append(" ").append(tableRef.getAlias());
        sb.append(assembleWhereClauses(whereClauses, true));

        return sb.toString();
    }


    /**
     * Ensures that a class implements the {@link DBMap} interface. If it does, call is a no-op; if not, throws {@link
     * IllegalArgumentException}
     */
    private static void ensureClassIsDBMap(Class<?> klass) {
        if (!DBMap.class.isAssignableFrom(klass)) {
            throw new IllegalArgumentException("Supplied Enum must implement com.firm58.common.model.dbmap.DBMap");
        }
    }

    /**
     * Ensures that a class implements the {@link DBMap} interface. If it does, call is a no-op; if not, throws {@link
     * IllegalArgumentException}
     *
     * @throws IllegalArgumentException If the supplied Enum does not have a TABLE element
     * @throws ClassCastException If the supplied Enum is not of type DBMap
     */
    private static <T extends Enum<T>> DBMap getTableRef(Class<T> enumType) {
        T ret = Enum.valueOf(enumType, "TABLE");
        if (ret == null) {
            throw new IllegalArgumentException("Enum does not have 'TABLE' constant.");
        }
        return (DBMap) ret;
    }

    /**
     * Returns a comma separated String of fields from a {@link DBMap}. Fields in generated string are in order that
     * they're specified in the {@link DBMap}. Exploits the pattern that F58 DBMap Enums use constants starting with
     * "FIELD" to denote fields. Callers to this method should call {@link #ensureClassIsDBMap(Class)} first.
     *
     * @param enumType The Class for the {@link DBMap} Enum to process.
     * @param aliased If 'true', uses the aliased version of the field name; if 'false' just uses the simple field
     * name.
     * @throws IllegalArgumentException If the supplied Enum doesn't implement the {@link DBMap} interface.
     */
    private static <T extends Enum<T>> String[] getFieldList(Class<T> enumType, boolean aliased) {
        List<String> retList = new ArrayList<>();

        // Get all enum fields
        T[] enumElements = enumType.getEnumConstants();
        DBMap dbMapElement = null;
        for (T element : enumElements) {
            if (element.toString().startsWith("FIELD")) {
                dbMapElement = (DBMap) element;
                if (aliased) {
                    retList.add(dbMapElement.getQualifiedName() + " " + dbMapElement.getAlias());
                } else {
                    retList.add(dbMapElement.getDBName());
                }
            }
        }

        return retList.toArray(new String[retList.size()]);
    }

    /**
     * Builds a set of where sub-clauses from a field list.
     */
    private static String[] buildWhereClausesForFieldList(DBMap[] whereFields, String prefix) {
        if ((whereFields == null) || (whereFields.length == 0)) {
            return new String[0];
        }

        String whereClauses[] = new String[whereFields.length];
        for (int x = 0; x < whereFields.length; x++) {
            whereClauses[x] = ((StringUtils.isNotEmpty(prefix)) ? prefix : "") + whereFields[x].getDBName() + " = ?";
        }
        return whereClauses;
    }

    /**
     * Builds a where clause given a list of sub-clauses. Returned String will be empty if supplied array is empty.
     *
     * @param prependSpace If true, a space will be prepended before the "WHERE".
     */
    private static String assembleWhereClauses(String[] whereClauses, boolean prependSpace) {
        // short-cut?
        if ((whereClauses == null) || (whereClauses.length == 0)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(prependSpace ? " WHERE " : "WHERE ");
        Iterator<String> i = Arrays.asList(whereClauses).iterator();
        while (i.hasNext()) {
            sb.append(i.next());
            if (i.hasNext()) {
                sb.append(" AND ");
            }
        }
        return sb.toString();
    }

    /**
     * callback for insertWithBatch
     */
    public interface StatementCallback {

        public void call(PreparedStatement statement) throws SQLException;
    }

    @SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
    public static boolean isNoWaitException(Exception e) {
        // ORA-00054: resource busy and acquire with NOWAIT specified or timeout expired
        SQLException ex = getSQLException(e);
        return ex != null && ex.getErrorCode() == 54;
    }

    public static SQLException getSQLException(Throwable t) {
        if (t == null) {
            return null;
        } else if (t instanceof SQLException) {
            return (SQLException) t;
        } else {
            return getSQLException(t.getCause());
        }
    }

    public static void setStringOrNull(PreparedStatement ps, int index, String value) throws SQLException {
        if (value != null) {
            ps.setString(index, value);
        } else {
            ps.setNull(index, Types.VARCHAR);
        }
    }

    public static void setStringOrNullIfBlank(PreparedStatement ps, int index, String value) throws SQLException {
        if (StringUtils.isNotBlank(value)) {
            ps.setString(index, value);
        } else {
            ps.setNull(index, Types.VARCHAR);
        }
    }
}
