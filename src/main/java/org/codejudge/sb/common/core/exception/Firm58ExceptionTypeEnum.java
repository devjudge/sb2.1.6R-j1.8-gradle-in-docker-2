package org.codejudge.sb.common.core.exception;


import org.codejudge.sb.common.model.F58Enum;

import java.util.Map;
import java.util.TreeMap;

/**
 * An Ennumeration of exception condition types used to populate an {@link Firm58GeneralException}
 * $Id: Firm58ExceptionTypeEnum.java,v 1.27 2009/05/07 14:54:24 mbeauford Exp $
 */
public enum Firm58ExceptionTypeEnum implements F58Enum
{
    UNKNOWN_SYSTEM_ERROR(0, "Unknown System Error", "Unknown System Error"),
    COMMUNICATION_ERROR(1, "Communication Error", "Communication Error"),
    FILENAME_FORMAT_ERROR(2, "Invalid filename format error", "Invalid filename format error"),
    FILEUTIL_ERROR(3, "A file system error", "A file system error"),
    INVALID_LOGIN_ATTEMPT(1000, "Invalid Login Attempt", "Invalid Login Attempt"),
    DUPLICATE_LOGIN_ID_PASSWORD(1001, "Duplicate login id / password combination", "Invalid login id / password values"),
    DUPLICATE_CUSTOMER_LOGIN(1002, "Duplicate customer user login id", "Invalid login id / password values"),
    AMBIGUOUS_LOGIN_RETURNED(1003, "Ambiguous login attempt", "Ambiguous login attempt, customer id is required"),
    AUTHENTICATION_SYSTEM_ERROR(1004, "A general error occurred during authentication", "A system error occurred"),
    LDAP_USER_NOT_IN_DATASTORE(1005, "System attempted to allocate a user id that already exists in ldap, ldap appears to be out of sync with the data store.", "A system error occurred"),
    PASSWORD_CHECK_ERROR(1006, "Password did not pass strength validation", "Password did not pass strength validation"),
    UNKNOWN_EVENT_HANDLER(2000, "Event submitted with unknown event handler", "Event submitted with unknown event handler"),
    UNKNOWN_EVENT_PRODUCER(2003, "Event submitted with unknown message producer", "Event submitted with unknown message producer"),
    EVENT_PROCESSOR(2001, "Event processor error", "Unable to process events"),
    EVENT_LOCKED(2002, "Event locked", "Unable to lock event for processing"),
    TRANSACTION_PROCESSOR(3000, "Transaction processor error", "Unable to process transactions"),
    TRANSFORMATION_PROCESSOR(4000, "Transformation processor error", "Unable to transform record"),
    WORKFLOW_ERROR(5000, "Workflow Error", "Unable to continue workflow process"),
    UNKNOWN_AGGREGATION_SUMMARIZER(6000, "Bill summary request with unknown aggregation summarizer", "Bill summary request with unknown aggregation summarizer"),
    DATABASE_ERROR(7000, "Unknown Database Error", "A system error occurred"),
    DATE_PARSE_ERROR(8000, "Date Parse Error", "A system error occurred"),
    NUMBER_PARSE_ERROR(9000, "Number Parse Error", "A system error occurred"),
    CAPTURE_ERROR_CUSTOMER_NOT_FOUND(10000, "Unable to find customer for given id", "Unable to find customer for given id"),
    FLEX_VALIDATION_ERROR(10001, "Flex field failed validation", "Flex field failed validation"),
    TRANSACTION_NOT_FOUND(11000, "Unable to find transaction for given id", "Unable to find transaction for given id"),
    ILLEGAL_ARGUMENT(12000, "Unable to find record for the entered parameters", "Unable to find record for the entered parameters"),
    XSLT_TRANSFORMATION_ERROR(13000, "An xslt transformation engine error occurred", "An xslt transformation engine error occurred"),
    EXPORT_ERROR(14000, "An error occurred during the export process", "An error occurred during the export process"),
    IMPORT_ERROR(15000, "An error occurred during an import process", "An error occurred during an import process"),
    DATA_ERROR(16000, "Unspecified data error", "Unspecified data error"),
    FILTER_ENGINE_ERROR(17000, "An error occurred during the evaluation of a filter", "An error occurred during the evaluation of a filter"),
    FILTER_ENGINE_COMPILE_ERROR(18000, "An error occurred during the compilation of a filter engine", "An error occurred during the compilation of a filter engine"),
    RATING_ERROR(19000, "An error occurred during the rating", "An error occurred during rating"),
    INTROSPECTOR_ERROR(20000, "An error occurred during introspection", "An error occurred during introspection"),
    FILTER_ERROR(21000, "A filter resource error occurred", "A filter resource error occurred"),
    FILTER_CONSTRUCTION_ERROR(22000, "An error occured while constructing a filter", "An error occured while constructing a filter"),
    FILE_IO_ERROR(24000, "An error occurred while attempting file I/O", "An error occurred while attempting file I/O"),
    LEDGER_SETUP_ERROR(25000, "Posting level for gl account not compatible with balance adjustment", "Posting level for gl account not compatible with balance adjustment"),
    JASPER_REPORTS_ERROR(26000, "An error occurred while trying to run a Jasper-based report", "An error occurred while trying to run a Jasper-based report"),

    NO_CURRENT_ACCT_BALANCE(27000, "No current balance exists for the account Id", "No current balance exists for the account Id"),
    TRADE_CONFIRM_DATE_PARSE_ERROR(27001, "Error while trying to convert a String date to a real Date object, wrong String date format", "Error while trying to convert a String date to a real Date object, wrong String date format"),

    GENERAL_WORKFLOW_ERROR(30000, "A general WORKFLOW error has occurred within a workflow", "A general WORKFLOW error has occurred within a workflow"),
    NO_WORKFLOW_DEF_FOUND_ERROR(30001, "No workflow definitions found for domain", "No workflow definitions found for domain"),
    UNABLE_TO_FIND_WORKFLOW_DEF_ERROR(30002, "Unable to find matching workflow definition for domain", "Unable to find matching workflow definition for domain"),
    CANNOT_FIND_ACCOUNT_ID_ERROR(30003, "Unable to find matching workflow definition for domain", "Unable to find matching workflow definition for domain"),
    INVALID_WORKFLOW_ACTION_ERROR(31000, "An invalid ACTION occurred within a workflow step", "An invalid ACTION occurred within a workflow step"),
    INVALID_WORKFLOW_ROLE_ERROR(32000, "An invalid ROLE occurred within a workflow", "An invalid ROLE occurred within a workflow"),
    INVALID_WORKFLOW_INPUT_ERROR(33000, "An invalid INPUT occurred within a workflow", "An invalid INPUT occurred within a workflow"),
    INVALID_WORKFLOW_ENTRY_STATE_ERROR(34000, "An invalid ENTRY STATE occurred within a workflow", "An invalid INPUT occurred within a workflow"),

    USER_IMPORT_ERROR(35000, "An error occurred while processing a user import", "An error occurred while processing a user import"),
    DIRTY_WRITE_ERROR(36000, "The data being updated is out of date", "The data being updated is out of date"),

    RESOURCE_DELETE_FROM_NODE_HIERARCHY_ERROR(36100, "An error occurred while attempting to delete a hierarchy node that still has resources associated to it!", "An error occurred while attempting to delete a hierarchy node that still has resources associated to it!"),
    RESOURCE_LOCKED_ERROR(36200, "The requested resources has been locked by another processes", "The requested resources has been locked by another processes"),

    ACCOUNT_GROUP_CIRCULAR_REFERENCE_ERROR(37100, "The account group children would cause a circular reference with account group parent.", "The account group children would cause a circular reference with account group parent."),
    ACCOUNT_GROUP_ADD_INACTIVE_ERROR(37200, "Cannot add inactive account group members to account group.", "Cannot add inactive account group members to account group."),
    ACCOUNT_GROUP_RELATED_BILLING_ACCOUNT_ERROR(37300, "Cannot deactivate account group related to billing account.", "Cannot deactivate account group related to billing account."),

    UNDEFINED_DOMAIN_VALUE_ERROR(38000, "Cannot find domain value.", "Cannot find domain value."),
    SEND_EMAIL_ERROR(39000, "Error sending email.", "Error sending email."),

    BILLING_INVALID_CYCLE(40000, "Billing account has an invalid billing cycle", "Billing account has an invalid billing cycle"),
    BILLING_INVALID_OPEN_DATE(40100, "Billing account has an invalid open date", "Billing account has an invalid open date"),
    BILLING_CLOSED_BILLS_EXIST(40200, "Close bills exist on the billing account", "Close bills exist on the billing account"),

    PUBLICATION_ERROR(50000, "Error while attemption to publish a publication", "Error while attemption to publish a publication");

    private final int errorCode;
    private final String systemMessage;
    private final String displayName;


    private Firm58ExceptionTypeEnum(int errorCode, String systemMessage, String displayName)
    {
        this.errorCode = errorCode;
        this.systemMessage = systemMessage;
        this.displayName = displayName;
    }


    public int getErrorCode()
    {
        return this.errorCode;
    }

    public String getSystemMessage()
    {
        return this.systemMessage;
    }

    @Override
    public String getDisplayName()
    {
        return this.displayName;
    }

    public static Map<String, Firm58ExceptionTypeEnum> getValues()
    {
        Map<String, Firm58ExceptionTypeEnum> results = new TreeMap<String, Firm58ExceptionTypeEnum>();
        for (Firm58ExceptionTypeEnum e : values())
        {
            results.put(e.displayName, e);
        }

        return results;
    }
}
