package org.codejudge.sb.common.model;

import org.apache.commons.lang3.ArrayUtils;
import org.codejudge.sb.common.util.EqualsUtil;
import org.codejudge.sb.common.util.HashCodeUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * Primary security class to represent a user and their associated roles/groups/permissions. User.java,v 1.16 2007/08/06
 * 18:47:50 tgee Exp
 */
public class User extends Individual implements UserDetails {

    private static final long serialVersionUID = 201604041039L;

    private String userName;
    private String nodeId;
    private Long preferencesId;
    private Long securitySettingsId;
    private DataSecurityEnum dataSecurityLevel;
    private Timestamp lastLoginDate;
    private YesNoEnum changePasswordAtNextLogin;
    private String securityGroupId;

    private List<GrantedAuthority> authorities = AuthorityUtils.NO_AUTHORITIES;

    /**
     * For security reason, the password should be populated only when needed.  It is transient to prevent it from being
     * serialized.
     */
    private transient String password;

    private Date passwordChangeDate;
    private int graceLoginCount;
    private YesNoEnum lockedStatus = YesNoEnum.NO;
    private Date lockedDate;
    private int failedLoginCount;
    private Date failedLoginDate;

    private Attributes attributes = new BasicAttributes();

    public Date getLockedDate() {
        return lockedDate;
    }

    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }

    public Date getFailedLoginDate() {
        return failedLoginDate;
    }

    public void setFailedLoginDate(Date failedLoginDate) {
        this.failedLoginDate = failedLoginDate;
    }

    public YesNoEnum getLockedStatus() {
        return lockedStatus;
    }

    public void setLockedStatus(YesNoEnum lockedStatus) {
        this.lockedStatus = lockedStatus;
    }

    public int getFailedLoginCount() {
        return failedLoginCount;
    }

    public void setFailedLoginCount(int failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    public int getGraceLoginCount() {
        return graceLoginCount;
    }

    public void setGraceLoginCount(int graceLoginCount) {
        this.graceLoginCount = graceLoginCount;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Date getPasswordChangeDate() {
        return passwordChangeDate;
    }

    public void setPasswordChangeDate(final Date passwordChangeDate) {
        this.passwordChangeDate = passwordChangeDate;
    }

    public Attributes getAttributes() {
        return this.attributes;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // we don't have the notion of accounts expiring
    }

    @Override
    public boolean isAccountNonLocked() {
        return lockedStatus == null || lockedStatus == YesNoEnum.NO;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // This is actually dependant on a SecurityProfile,
        // so just return true since it'll be checked later down the line anyway.
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUsername() {
        return getUserName();
    }

    public String getUserName() {
        return this.userName;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public Long getPreferencesId() {
        return preferencesId;
    }

    public void setPreferencesId(Long preferencesId) {
        this.preferencesId = preferencesId;
    }

    public Long getSecuritySettingsId() {
        return securitySettingsId;
    }

    public void setSecuritySettingsId(Long securitySettingsId) {
        this.securitySettingsId = securitySettingsId;
    }

    public DataSecurityEnum getDataSecurityLevel() {
        return dataSecurityLevel;
    }

    public void setDataSecurityLevel(DataSecurityEnum dataSecurityLevel) {
        this.dataSecurityLevel = dataSecurityLevel;
    }

    public Timestamp getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Timestamp lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public YesNoEnum getChangePasswordAtNextLogin() {
        return changePasswordAtNextLogin;
    }

    public void setChangePasswordAtNextLogin(YesNoEnum changePasswordAtNextLogin) {
        this.changePasswordAtNextLogin = changePasswordAtNextLogin;
    }

    public String getSecurityGroupId() {
        return securityGroupId;
    }

    public void setSecurityGroupId(String securityGroupId) {
        this.securityGroupId = securityGroupId;
    }

    @Override
    public int hashCode() {
        int hashCode = super.hashCode();
        hashCode = HashCodeUtil.calcHashCode(hashCode, userName);
        hashCode = HashCodeUtil.calcHashCode(hashCode, nodeId);
        hashCode = HashCodeUtil.calcHashCode(hashCode, securitySettingsId);
        hashCode = HashCodeUtil.calcHashCode(hashCode, dataSecurityLevel);
        hashCode = HashCodeUtil.calcHashCode(hashCode, lastLoginDate);
        hashCode = HashCodeUtil.calcHashCode(hashCode, changePasswordAtNextLogin);
        hashCode = HashCodeUtil.calcHashCode(hashCode, authorities);
        return hashCode;
    }

    @Override
    public boolean equals(Object object) {
        final boolean isEqual;

        if (this == object) {
            isEqual = true;
        } else if (object == null) {
            isEqual = false;
        } else if (!(object instanceof User)) {
            isEqual = false;
        } else {
            User other = (User) object;
            boolean fieldsEqual = super.equals(other);
            fieldsEqual &= EqualsUtil.areEqual(userName, other.userName);
            fieldsEqual &= EqualsUtil.areEqual(nodeId, other.nodeId);
            fieldsEqual &= EqualsUtil.areEqual(securitySettingsId, other.securitySettingsId);
            fieldsEqual &= EqualsUtil.areEqual(dataSecurityLevel, other.dataSecurityLevel);
            fieldsEqual &= EqualsUtil.areEqual(lastLoginDate, other.lastLoginDate);
            fieldsEqual &= EqualsUtil.areEqual(changePasswordAtNextLogin, other.changePasswordAtNextLogin);
            fieldsEqual &= EqualsUtil.areUnorderedGrantAuthoritiesEqual(authorities, other.authorities);

            isEqual = fieldsEqual;
        }

        return isEqual;
    }

    protected String[] getToStringPropertyExcludes() {
        return ArrayUtils.addAll(new String[]{"passwordChangeDate"});
    }
}
