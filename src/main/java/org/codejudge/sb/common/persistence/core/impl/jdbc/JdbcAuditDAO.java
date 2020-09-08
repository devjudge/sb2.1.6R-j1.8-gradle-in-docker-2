package org.codejudge.sb.common.persistence.core.impl.jdbc;

import org.apache.commons.lang3.NotImplementedException;
import org.codejudge.sb.common.core.exception.Firm58DatabaseException;
import org.codejudge.sb.common.core.exception.Firm58ExceptionTypeEnum;
import org.codejudge.sb.common.model.Audit;
import org.codejudge.sb.common.model.AuditDBMap;
import org.codejudge.sb.common.persistence.AuditDAO;
import org.codejudge.sb.common.persistence.core.DBHelper;
import org.codejudge.sb.common.util.BaseJDBCDAO;
import org.codejudge.sb.common.util.IdManager;

import java.sql.*;
import java.util.List;

/**
 * DAO supplying interactions with system audits
 *
 * @author $Author: jqian $
 * @version $Revision: 1.8 $
 * @since NITROGEN
 */
public class JdbcAuditDAO extends BaseJDBCDAO implements AuditDAO {

    private static final String INSERT_AUDIT =
            "insert into " + AuditDBMap.TABLE.getDBName() + "("
                    + AuditDBMap.FIELD_AUDIT_ACTION.getDBName() + ", "
                    + AuditDBMap.FIELD_AUDIT_COMMENT.getDBName() + ", "
                    + AuditDBMap.FIELD_AUDIT_DATE.getDBName() + ", "
                    + AuditDBMap.FIELD_AUDIT_REASON.getDBName() + ", "
                    + AuditDBMap.FIELD_AUDIT_TYPE.getDBName() + ", "
                    + AuditDBMap.FIELD_ID.getDBName() + ", "
                    + AuditDBMap.FIELD_LAST_MODIFIED_BY.getDBName() + ", "
                    + AuditDBMap.FIELD_LAST_MODIFIED_DATE.getDBName() + ", "
                    + AuditDBMap.FIELD_REFERENCE_ID.getDBName() + ", "
                    + AuditDBMap.FIELD_USER_NAME.getDBName()
                    + ") values (?, ?, ?, ?, ?, ?, " + DBHelper.CLIENT_IDENTIFIER_CALL + ", sysdate, ?, ?)";

    @Override
    public void createAudit(Audit audit) {
        PreparedStatement insertStmt = null;
        Connection connection = null;

        boolean connectionBoundHere = false;
        boolean exceptionOccurred = false;

        try {
            connectionBoundHere = setupConnection();
            connection = getUserConnection();

            insertStmt = connection.prepareStatement(INSERT_AUDIT);

            String id = IdManager.getIdManager().getUniqueId();

            if (audit.getAuditAction() != null) {
                insertStmt.setString(1, audit.getAuditAction().getKey());
            } else {
                insertStmt.setNull(1, Types.VARCHAR);
            }

            if (audit.getComment() != null && audit.getComment().length() > 900) {
                // chop the comment to fit into the database
                insertStmt.setString(2, audit.getComment().substring(0, 900));
            } else {
                insertStmt.setString(2, audit.getComment());
            }
            insertStmt.setTimestamp(3, new Timestamp(audit.getModificationDate().getTime()));

            if (audit.getAuditReason() != null) {
                insertStmt.setString(4, audit.getAuditReason().getKey());
            } else {
                insertStmt.setNull(4, Types.VARCHAR);
            }

            insertStmt.setString(5, audit.getAuditRecordType().toString());
            insertStmt.setString(6, id);
            insertStmt.setString(7, audit.getReferenceId());
            insertStmt.setString(8, audit.getUser());

            insertStmt.executeUpdate();

            audit.setId(id);
        } catch (SQLException e) {
            exceptionOccurred = true;

            throw new Firm58DatabaseException("Caught exception while inserting audit", e,
                    Firm58ExceptionTypeEnum.DATABASE_ERROR);
        } finally {
            DBHelper.close(insertStmt);

            tearDownConnection(connection, connectionBoundHere, exceptionOccurred);
        }
    }

    @Override
    public Audit getAuditByAction(String objectId, String actionKey) {
        throw new NotImplementedException();
    }

    @Override
    public List<Audit> getAuditsForObject(String objectId) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAuditsForObject(String objectId) {
        throw new NotImplementedException();
    }

}
