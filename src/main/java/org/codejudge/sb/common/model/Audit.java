package org.codejudge.sb.common.model;

import org.codejudge.sb.common.util.security.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;

/**
 * @author $Author: slathrop $
 * @version $Revision: 1.4 $
 * @since Copyright 2009 Firm58, Inc.
 */
public class Audit implements Serializable {

    private static final long serialVersionUID = -6245660938413624509L;
    protected final Logger logger = LoggerFactory.getLogger(getClass().getName());

    private String id;
    private String lastModifiedBy;
    private Date lastModifiedDate;

    private String referenceId;
    private AuditRecordTypeEnum auditRecordType;
    private DomainValue auditAction;
    private DomainValue auditReason;
    /**
     * Username
     */
    private String user;
    private Date modificationDate;
    private String comment;

    public Audit(AuditRecordTypeEnum type, String referenceId, DomainValue auditAction) {
        this(type, referenceId, auditAction, UserUtil.getUser() == null ? null : UserUtil.getUser().getUserName());
    }

    public Audit(AuditRecordTypeEnum type, String referenceId, DomainValue auditAction, String user) {
        this.auditRecordType = type;
        this.referenceId = referenceId;
        this.auditAction = auditAction;
        this.user = user;
        this.modificationDate = new Date();
        if (user == null) {
            logger.error("Creating an audit record with a null username", new RuntimeException());
            this.user = "null";
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public AuditRecordTypeEnum getAuditRecordType() {
        return auditRecordType;
    }

    public void setAuditRecordType(AuditRecordTypeEnum auditRecordType) {
        this.auditRecordType = auditRecordType;
    }

    public DomainValue getAuditAction() {
        return auditAction;
    }

    public void setAuditAction(DomainValue auditAction) {
        this.auditAction = auditAction;
    }

    public DomainValue getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(DomainValue auditReason) {
        this.auditReason = auditReason;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder("Audit[");

        sb.append("type=").append(auditRecordType);
        sb.append(",action=").append(auditAction);
        sb.append(",reason=").append(auditReason);
        sb.append(",user=").append(user);
        sb.append(",modificationDate=").append(modificationDate);

        return sb.append("]").toString();
    }
}
