package org.codejudge.sb.common.model;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.codejudge.sb.common.util.EqualsUtil;
import org.codejudge.sb.common.util.HashCodeUtil;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Models a specific value in a domain, or set, of values. The set this value belongs to is specified by the {@link
 * DomainClassEnum} stored in the field {@link #domainClass}. $Id: DomainValue.java,v 1.13 2009/01/31 01:22:34 kleggett
 * Exp $
 */
@XmlRootElement
public class DomainValue extends Model implements Comparable<DomainValue> {

    private static final long serialVersionUID = 201409121104L;

    private DomainClassEnum domainClass;
    private String key;
    private String displayName;
    private YesNoEnum systemImmutable = YesNoEnum.NO;
    private ActiveStatusEnum activeStatus = ActiveStatusEnum.ACTIVE;

    public DomainValue() {
    }

    public DomainValue(String key) {
        setKey(key);
    }

    /**
     * convenience, sets key, domainClass, and id
     */
    public DomainValue(String key, DomainClassEnum domainClass) {
        setKey(key);
        setDomainClass(domainClass);
        setId(generateId(this));
    }

    /**
     * convenience, sets key, domainClass, and id
     */
    public DomainValue(String displayName, String key, DomainClassEnum domainClass) {
        setDisplayName(displayName);
        setKey(key);
        setDomainClass(domainClass);
        setId(generateId(this));
    }

    @XmlAttribute
    public DomainClassEnum getDomainClass() {
        return this.domainClass;
    }

    public void setDomainClass(DomainClassEnum domainClass) {
        this.domainClass = domainClass;
    }

    @XmlAttribute
    public String getKey() {
        return this.key;
    }

    public void setKey(String name) {
        this.key = name;
    }

    @XmlAttribute
    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @XmlAttribute
    public YesNoEnum getSystemImmutable() {
        return this.systemImmutable;
    }

    public void setSystemImmutable(YesNoEnum systemImmutable) {
        this.systemImmutable = systemImmutable;
    }

    @XmlAttribute
    public ActiveStatusEnum getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(ActiveStatusEnum activeStatus) {
        this.activeStatus = activeStatus;
    }

    @Override
    protected String[] getToStringPropertyExcludes() {
        return ArrayUtils.addAll(new String[]{"lastModifiedBy"}, super.getToStringPropertyExcludes());
    }

    /**
     * This compareTo method is good for display sorting only
     */
    @Override
    public int compareTo(DomainValue other) {
        int rv = 0;

        String dn1 = other.getDisplayName();
        String dn2 = this.getDisplayName();
        if (dn1 != null && dn2 != null) {
            rv = dn2.compareTo(dn1);
        }

        if (rv == 0) {
            String key1 = other.getKey();
            String key2 = this.getKey();
            if (key1 != null && key2 != null) {
                rv = key2.compareTo(key1);
            }
        }

        if (rv == 0) {
            String s1 = other.toString();
            String s2 = this.toString();

            if (s1 != null && s2 != null) {
                rv = s2.compareTo(s1);
            }
        }

        return rv;
    }

    /**
     * The domain value is uniquely identified by domain class and value
     */
    @Override
    public boolean equals(Object o) {
        final boolean isEqual;

        if (this == o) {
            isEqual = true;
        } else if (o == null) {
            isEqual = false;
        } else if (!(o instanceof DomainValue)) {
            isEqual = false;
        } else if (StringUtils.isNotEmpty(getId()) && StringUtils.isNotEmpty(((DomainValue) o).getId())) {
            isEqual = getId().equals(((DomainValue) o).getId());
        } else {
            // domain class and key uniquely identifies a domain value
            DomainValue domainValue = (DomainValue) o;
            boolean fieldsEqual = super.equals(domainValue);
            fieldsEqual &= EqualsUtil.areEqual(domainClass, domainValue.domainClass);
            fieldsEqual &= EqualsUtil.areEqual(key, domainValue.key);
            fieldsEqual &= EqualsUtil.areEqual(displayName, domainValue.displayName);
            fieldsEqual &= EqualsUtil.areEqual(systemImmutable, domainValue.systemImmutable);

            isEqual = fieldsEqual;
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        if (StringUtils.isNotEmpty(getId())) {
            return getId().hashCode();
        }
        // domain class and key uniquely identifies a domain value
        int hashCode = super.hashCode();
        hashCode = HashCodeUtil.calcHashCode(hashCode, domainClass);
        hashCode = HashCodeUtil.calcHashCode(hashCode, key);
        hashCode = HashCodeUtil.calcHashCode(hashCode, displayName);
        hashCode = HashCodeUtil.calcHashCode(hashCode, systemImmutable);
        return hashCode;
    }

    public static String generateId(String key, String domainClassName) {
        if (StringUtils.isBlank(domainClassName) || StringUtils.isBlank(key)) {
            return null;
        }

        String id = domainClassName + "-" + key;
        id = id.toLowerCase().replaceAll("\\s", "_");
        return id;
    }

    public static String generateId(String key, DomainClassEnum domainClass) {
        if (domainClass != null) {
            return generateId(key, domainClass.toString());
        }

        return null;
    }

    public static String generateId(DomainValue data) {
        return generateId(data.getKey(), data.getDomainClass());
    }
}
