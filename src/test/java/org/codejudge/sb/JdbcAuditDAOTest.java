package org.codejudge.sb;

import org.apache.commons.lang3.NotImplementedException;
import org.codejudge.sb.common.core.exception.Firm58DatabaseException;
import org.codejudge.sb.common.model.Audit;
import org.codejudge.sb.common.model.DomainValue;
import org.codejudge.sb.common.persistence.core.impl.jdbc.JdbcAuditDAO;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.codejudge.sb.common.model.AuditRecordTypeEnum.BILLING_ACCOUNT_GROUP;
import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JdbcAuditDAO.class)
public class JdbcAuditDAOTest {

    private JdbcAuditDAO dao;
    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private String auditComment;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        dao = new JdbcAuditDAO();
        dataSource = PowerMockito.mock(DataSource.class);
        connection = PowerMockito.mock(Connection.class);
        preparedStatement = PowerMockito.mock(PreparedStatement.class);
        auditComment = PowerMockito.mock(String.class);
        PowerMockito.when(auditComment.length()).thenReturn(1000);
        PowerMockito.whenNew(java.util.Date.class).withAnyArguments().thenReturn(new Date(0));
        dao.setDataSource(dataSource);
        PowerMockito.when(dataSource.getConnection()).thenReturn(connection);
        PowerMockito.when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
    }

    @Test
    public void shouldFail_WhenNoUserIsPassed() throws SQLException {
        exceptionRule.expect(Firm58DatabaseException.class);
        PowerMockito.when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        dao.createAudit(new Audit(BILLING_ACCOUNT_GROUP, null, new DomainValue("What's up"), "username"));
    }

    @Test
    public void shouldPass_WhenAllParametersPassedForAudit() {
        dao.createAudit(new Audit(BILLING_ACCOUNT_GROUP, null, new DomainValue("What's up"), "username"));
    }

    @Test
    public void shouldPass_WhenNulPassedForAuditAction() {
        dao.createAudit(new Audit(BILLING_ACCOUNT_GROUP, null, null, "username"));
    }

    @Test
    public void shouldPass_WhenAuditCommentLengthGreaterThan900() {
        Audit audit = new Audit(BILLING_ACCOUNT_GROUP, null, null, "username");
        audit.setComment(auditComment);
        dao.createAudit(audit);
    }

    @Test
    public void shouldPass_WhenAuditHasAuditReason() {
        Audit audit = new Audit(BILLING_ACCOUNT_GROUP, null, null, "username");
        audit.setAuditReason(new DomainValue("Heyyy!!!"));
        dao.createAudit(audit);
    }

    @Test
    public void shouldFail_GetAuditByAction() {
        exceptionRule.expect(NotImplementedException.class);
        dao.getAuditByAction("objectId", "action");
    }

    @Test
    public void shouldFail_getAuditsForObject() {
        exceptionRule.expect(NotImplementedException.class);
        dao.getAuditsForObject("objectId");
    }

    @Test
    public void shouldFail_DeleteAuditsForObject() {
        exceptionRule.expect(NotImplementedException.class);
        dao.deleteAuditsForObject("objectId");
    }
}
