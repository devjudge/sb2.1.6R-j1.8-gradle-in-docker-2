package org.codejudge.sb.common.model;

import org.codejudge.sb.common.util.EqualsUtil;
import org.codejudge.sb.common.util.HashCodeUtil;

/**
 * TODO: [provide a nice descriptive comment for the type here] $Id$
 */

public class Individual {

    private static final long serialVersionUID = -7436695073725809787L;

    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private String company;
    private String email;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public int hashCode() {
        int hashCode = super.hashCode();
        hashCode = HashCodeUtil.calcHashCode(hashCode, title);
        hashCode = HashCodeUtil.calcHashCode(hashCode, firstName);
        hashCode = HashCodeUtil.calcHashCode(hashCode, middleName);
        hashCode = HashCodeUtil.calcHashCode(hashCode, lastName);
        hashCode = HashCodeUtil.calcHashCode(hashCode, suffix);
        hashCode = HashCodeUtil.calcHashCode(hashCode, company);
        hashCode = HashCodeUtil.calcHashCode(hashCode, email);
        return hashCode;
    }

    @Override
    public boolean equals(Object object) {
        final boolean isEqual;

        if (this == object) {
            isEqual = true;
        } else if (object == null) {
            isEqual = false;
        } else if (!(object instanceof Individual)) {
            isEqual = false;
        } else {
            Individual other = (Individual) object;
            boolean fieldsEqual = super.equals(other);
            fieldsEqual &= EqualsUtil.areEqual(title, other.title);
            fieldsEqual &= EqualsUtil.areEqual(firstName, other.firstName);
            fieldsEqual &= EqualsUtil.areEqual(middleName, other.middleName);
            fieldsEqual &= EqualsUtil.areEqual(lastName, other.lastName);
            fieldsEqual &= EqualsUtil.areEqual(suffix, other.suffix);
            fieldsEqual &= EqualsUtil.areEqual(company, other.company);
            fieldsEqual &= EqualsUtil.areEqual(email, other.email);
            isEqual = fieldsEqual;
        }

        return isEqual;
    }
}
