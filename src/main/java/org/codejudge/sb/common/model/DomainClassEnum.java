/*
 * @(#)DomainClassEnum.java
 *
 * Copyright 2008 by Firm58, Inc.,
 * All rights reserved.
 *
 * $Id: DomainClassEnum.java,v 1.18 2009/04/28 01:49:52 jkratz Exp $
 */
package org.codejudge.sb.common.model;

import java.util.Map;
import java.util.TreeMap;

/**
 * Enumerates various types, or classes, to which a {@link DomainValue} can
 * belong.
 *
 * @author $Author: jkratz $
 * @version $Revision: 1.18 $
 * @since ?
 */
public enum DomainClassEnum implements F58Enum
{
    NODE_TYPE("Node Type"),
    JOURNAL_TYPE("Journal Type"),
    TRADE_TYPE("Trade Type"),
    PAGE_SIZE("Page Size"),
    CONFIG("Configuration"),
    UNIT_OF_MEASURE("Unit of Measure"),
    PAYMENT_REQUEST_ACTION("Payment Request Action"),
    PAYMENT_REQUEST_REASON("Payment Request Action Reason"),
    VENDOR_TERMS("Vendor Terms"),
    VENDOR_CATEGORY("Vendor Category"),
    SERVICE_CLASSIFICATION("Service Classification"),
    USER_IMPORT_PARAM("User Import Parameter"),
    PARTY_RELATION_TYPE("Party Relation Type"),
    AUDIT_ACTION("Audit Action"),
    AUDIT_REASON("Audit Reason"),
    PARTY_WORKFLOW_TYPE("Party Workflow Type"),
    PAYMENT_REF_TYPE("Payment Reference Type"),
    DOCUMENT_TYPE("Document Type"),
    TRADE_ACCOUNT_GROUP_CATEGORY("Account Group Category"),
    ASSET_IMPORT_PARAM("Asset Import Parameter"),
    // ALPHA-13032: making 4 Trade fields dynamic
    LIQUIDITY("Liquidity"),
    LISTED("Listed"),
    AGENT_PRINCIPAL("Agent Principal"),
    LOT_TYPE("Lot Type"),
    // ALPHA-12778: fields added to take the place of party/flex hacks
    PRODUCT_TYPE("Product Type"),
    CUSTOMER_TYPE("Customer Type"),
    SUB_CUSTOMER_TYPE("Sub Customer Type"),
    ORDER_TYPE("Order Type"),
    ORDER_TIME_IN_FORCE("Order Time In Force"),
    SUB_TRADE_TYPE("Sub Trade Type"),
    SETTLEMENT_TYPE("Settlement Type"),
    COMPLIANCE_RESOLUTION("Compliance Resolution Type"),
    ACCOUNT_TYPE("Account Type"),
    RATE_ASSIGNMENT_CATEGORY("Rate Assignment Category")
    ;

    private final String displayName;

    private DomainClassEnum(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName()
    {
        return this.displayName;
    }

    public static Map<String, DomainClassEnum> getValues()
    {
        Map<String, DomainClassEnum> results = new TreeMap<String, DomainClassEnum>();
        for (DomainClassEnum e : values())
        {
            results.put(e.displayName, e);
        }

        return results;
    }

    public static DomainClassEnum valueOfIgnoreCase(String domainClassName)
    {
        if (domainClassName != null)
        {
            for (DomainClassEnum dce : values())
            {
                if (dce.name().equalsIgnoreCase(domainClassName))
                {
                    return dce;
                }
            }
        }
        return null;
    }
}
