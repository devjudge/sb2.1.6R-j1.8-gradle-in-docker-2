package org.codejudge.sb.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: dpurdy Date: 12/2/13 Time: 9:18 AM
 */
public class F58DataSourceUtils {

    private static final Logger logger = LoggerFactory.getLogger(F58DataSourceUtils.class);

    public static Connection getConnection(final DataSource dataSource) throws SQLException {
        //Assume connection created
        return dataSource.getConnection();
    }

    public static void setupConnectionSecurity(final DataSource dataSource) throws SQLException {
        Connection connection = getConnection(dataSource);
        setupConnectionSecurity(connection);
        releaseConnection(connection, dataSource);
    }

    public static void setupConnectionSecurity(Connection connection) {
        //do nothing
    }

    public static void releaseConnection(final Connection connection, final DataSource dataSource) {
        //do nothing
    }


    public static void logWhoCalled() {
        //do nothing
    }

    public static void logConnectionId(Connection connection) {
        //do nothing
    }

    public static List<StackTraceElement> removeUninterestingCalls(List<StackTraceElement> stack) {
        List<StackTraceElement> result = new ArrayList<>();
        for (StackTraceElement element : stack) {
            if (element.getClassName().contains("com.firm58") &&
                    !(element.getClassName().contains("F58DataSourceUtils") ||
                            element.getClassName().contains("UserDetailLoggerMethodInterceptor") ||
                            element.getClassName().contains("ExceptionInterceptor"))) {
                result.add(element);
            }
        }
        return result;
    }

    /**
     * only should be used outside of DBConnectionInterceptor when testing
     */
    public static void setOracleMetricUserName(final Connection connection, final String userName) throws SQLException {
        //do nothing
    }


}
