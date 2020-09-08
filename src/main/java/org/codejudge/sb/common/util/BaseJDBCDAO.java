package org.codejudge.sb.common.util;

import org.codejudge.sb.common.persistence.core.PageResult;
import org.codejudge.sb.common.service.facade.AbstractSearchCriteria;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * AbstractJDBCDAO.java,v 1.21 2008/08/22 21:23:35 ddurham Exp $
 */
@SuppressWarnings({"deprecation"})
public class BaseJDBCDAO extends AbstractF58SpringDAO {

    // Batch values between 5 and 30 tend to be the most effective. Setting a very high value might even have a negative
    // effect. It is worth trying different values to verify the effectiveness for your particular application.
    // http://download.oracle.com/docs/cd/B10501_01/java.920/a96654/oraperf.htm#1056233
    // this should probably be made configurable
    private static final int MAX_SQL_BATCH_SIZE = 34;

    public boolean setupConnection() {
        return true;
    }

    public void tearDownConnection(Connection connection, boolean connectionBoundHere, boolean exceptionOccurred) {
        // assume returns some connection
    }

    public PageResult<?> getListPage(AbstractSearchCriteria criteria) {
        return null;
    }

    public static void executeIfMaxBatch(final Statement statement, final int count) throws SQLException {
        if ((count % MAX_SQL_BATCH_SIZE) == 0) {
            statement.executeBatch();
        }
    }
}
