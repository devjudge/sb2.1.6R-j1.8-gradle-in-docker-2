package org.codejudge.sb.common.model;

import org.apache.commons.lang3.ArrayUtils;
import org.codejudge.sb.common.util.F58ToStringAdapter;
import org.codejudge.sb.common.util.security.UsernameUtil;
import org.codejudge.sb.common.util.xml.impl.JAXBDateAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.*;

/**
 * An abstract representation of any business model object.  It contains the common attributes for all model entities.
 *
 * User: rmarquez
 * Date: Mar 11, 2011
 * Time: 1:11:11 PM
 */
public abstract class SuperModel<K> extends F58ToStringAdapter implements Serializable
{
    private static final long serialVersionUID = 111L;

    public static final String UNKNOWN_LAST_MODIFIED_BY = "Unknown";

    protected String lastModifiedBy;
    protected Date lastModifiedDate;
    protected Map<String, AuditChange> auditChangeMap = new HashMap<>();
    protected boolean audit;

    public SuperModel()
    {
        setLastModifiedBy(UsernameUtil.getUsername() != null ?
                          UsernameUtil.getUsername() : SuperModel.UNKNOWN_LAST_MODIFIED_BY);
        setLastModifiedDate(new Date(0));
    }

    public SuperModel(SuperModel m)
    {
        if (m != null)
        {
            setLastModifiedBy(m.getLastModifiedBy());
            setLastModifiedDate(m.getLastModifiedDate());
        }
    }

    @XmlTransient
    public abstract K getId();

    public abstract void setId(K primaryKey);

    public abstract boolean isPrimaryKeySet();

    public abstract void clearPrimaryKey();

    @XmlAttribute(name = "lastModBy")
    public String getLastModifiedBy()
    {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy)
    {
        this.lastModifiedBy = lastModifiedBy;
    }

    @XmlAttribute(name = "lastModDt")
    @XmlJavaTypeAdapter(JAXBDateAdapter.class)
    public Date getLastModifiedDate()
    {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate)
    {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    protected String[] getToStringPropertyExcludes()
    {
        return ArrayUtils.addAll(new String[] { "lastModifiedDate", "audit", "auditChangeMap" }, super.getToStringPropertyExcludes());
    }

    /**
     * @param fieldName
     * @param auditChange
     */
    public void addAuditChange(String fieldName, AuditChange auditChange)
    {
        if (!auditChangeMap.containsKey(fieldName))
        {
            auditChangeMap.put(fieldName, auditChange);
        }
        else
        {
            //keep oldValue and update newValue to the newest
            AuditChange existingChange = auditChangeMap.get(fieldName);
            existingChange.setNewValue(auditChange.getNewValue());
        }
    }

    /**
     * Returns AuditChange for the passed field name
     *
     * @param fieldName
     * @return
     */
    public AuditChange getAuditChange(String fieldName)
    {
        return auditChangeMap.get(fieldName);
    }

    /**
     * Returns Map of AuditChanges with fieldName as key
     *
     * @return
     */
    @XmlTransient
    public Map<String, AuditChange> getAllAuditChanges()
    {
        return auditChangeMap;
    }

    /**
     * Starts auditing changes of annotated classes & fields after this call
     */
    public void startAudit()
    {
        audit = true;
    }

    /**
     * Stops auditing changes of annotated classes & fields after this call
     */
    public void stopAudit()
    {
        audit = false;
    }

    /**
     * Returns true if we should audit changes of annotated classes & fields
     *
     * @return
     */
    public boolean shouldAudit()
    {
        return audit;
    }

    public static <T,X extends SuperModel<T>> List<T> toIds(Collection<X> models)
    {
        if (models == null) return null;
        ArrayList <T> ids = new ArrayList<>(models.size());
        for (X model : models)
        {
            ids.add(model.getId());
        }
        return ids;
    }

    public static <T extends SuperModel<?>> void addIfNew(T newModel, List<T> existingModels)
    {
        boolean notFound = true;
        for (T existing : existingModels)
        {
            if (existing.getId().equals(newModel.getId()))
            {
                notFound = false;
                break;
            }
        }
        if (notFound) existingModels.add(newModel);
    }
}
