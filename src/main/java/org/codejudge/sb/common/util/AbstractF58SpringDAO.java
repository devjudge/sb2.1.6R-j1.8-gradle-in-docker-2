package org.codejudge.sb.common.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This abstract class has some basic connection handling methods useful in DAO's. Note that you must inject a
 * DataSource into it.
 *
 * @author kleggett
 * @since YTTRIUM (12/9/2014 2:22 PM)
 */
public abstract class AbstractF58SpringDAO {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected Connection getUserConnection() throws SQLException {
        return F58DataSourceUtils.getConnection(dataSource);
    }


    protected void tearDownConnection(Connection connection) {
        F58DataSourceUtils.releaseConnection(connection, dataSource);
    }
}
