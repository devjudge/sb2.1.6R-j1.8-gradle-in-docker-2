package org.codejudge.sb.common.model;

public class AuditChange
{
    private String oldValue;
    private String newValue;

    public AuditChange(String oldValue, String newValue)
    {
        super();
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getOldValue()
    {
        return oldValue;
    }

    public void setOldValue(String oldValue)
    {
        this.oldValue = oldValue;
    }

    public String getNewValue()
    {
        return newValue;
    }

    public void setNewValue(String newValue)
    {
        this.newValue = newValue;
    }
}
