package org.codejudge.sb.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumeration of audit record types.
 *
 * @author $Author: kleggett $
 * @version $Revision: 1.10 $
 * @since ?
 */
public enum AuditRecordTypeEnum implements F58Enum
{
    BILL("Bill Audit", "AUDIT"),
    BILLING_ACCOUNT("Billing Account", "AUDIT"),
    BILLING_ACCOUNT_TEMPLATE("Billing Account Template", "AUDIT"),
    BILLING_ACCOUNT_GROUP("Billing Account Group", "AUDIT"),
    DOCUMENT_AUDIT("My Documents", "AUDIT"),
    FILTER_EXPRESSION("Filter Expression", "AUDIT"),
    MIGRATION_AUDIT("Migration Audit", "AUDIT"),
    PAYMENT_REQUEST_AUDIT("Payment Request Audit", "PAYMENT_REQUEST"),
    RATE_ASSIGNMENT_AUDIT("Rate Assignment", "AUDIT"),
    RATE_PLAN_AUDIT("Rate Plan", "AUDIT"),
    RATE_TYPE_AUDIT("Rate Type", "AUDIT"),
    RATE_TYPE_GROUP_AUDIT("Rate Type Group", "AUDIT"),
    RATING_REPORT_DATA("Rating Report Data", "AUDIT"),
    SCHEDULED_SERVICE_REPORT("Scheduled Service Report", "AUDIT"),
    SCHEDULED_JASPER_REPORT("Schedule Jasper Report", "AUDIT"),
    SERVICE_REPORT("Service Report", "AUDIT"),
    SERVICE_REPORT_TYPE("Service Report Type", "AUDIT"),
    TASK_AUDIT("Task Audit", "AUDIT"),
    TRADE_ACCOUNT_GROUP_AUDIT("Trade Account Group", "AUDIT"),
    VENDOR_AUDIT("Vendor Audit", "AUDIT"),
    COMPLIANCE_AUDIT("Compliance Audit", "AUDIT");

    private String displayName;
    private String domainString;

    AuditRecordTypeEnum(String displayName, String domainString)
    {
        this.displayName = displayName;
        this.domainString = domainString;
    }

    @Override
    public String getDisplayName()
    {
        return this.displayName;
    }

    public DomainClassEnum getActionDomainValue()
    {
        return DomainClassEnum.valueOfIgnoreCase(domainString + "_ACTION");
    }

    public DomainClassEnum getReasonDomainValue()
    {
        return DomainClassEnum.valueOfIgnoreCase(domainString + "_REASON");
    }

    public static List<String> asStringList()
    {
        List<String> ret = new ArrayList<>();
        for (AuditRecordTypeEnum artEnum : AuditRecordTypeEnum.values())
        {
            ret.add(artEnum.toString());
        }
        return ret;
    }

    public static AuditRecordTypeEnum valueOfIgnoreCase(String name)
    {
        if (name != null)
        {
            for (AuditRecordTypeEnum type : values())
            {
                if (name.equalsIgnoreCase(type.name()))
                {
                    return type;
                }
            }
        }

        return null;
    }
}
