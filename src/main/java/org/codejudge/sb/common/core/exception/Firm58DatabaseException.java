package org.codejudge.sb.common.core.exception;

/**
 * TODO [provide a nice descriptive comment for the type here]
 * $Id: Firm58DatabaseException.java,v 1.2 2007/12/14 16:40:58 tgee Exp $
 */
public class Firm58DatabaseException extends Firm58GeneralException
{
    private static final long serialVersionUID = 7212898911405919726L;

    public Firm58DatabaseException(Firm58ExceptionTypeEnum firm58ExceptionType)
    {
        super(firm58ExceptionType);
    }

    public Firm58DatabaseException(String message, Firm58ExceptionTypeEnum firm58ExceptionType)
    {
        super(message, firm58ExceptionType);
    }

    public Firm58DatabaseException(String message, Throwable innerException, Firm58ExceptionTypeEnum firm58ExceptionType)
    {
        super(message, innerException, firm58ExceptionType);
    }

    public Firm58DatabaseException(Throwable innerException, Firm58ExceptionTypeEnum firm58ExceptionType)
    {
        super(innerException, firm58ExceptionType);
    }

    public Firm58DatabaseException(String message, Throwable innerException)
    {
        this(message, innerException, Firm58ExceptionTypeEnum.DATABASE_ERROR);
    }

    public boolean isResourceLockedError()
    {
        return getCause().getMessage().contains("ORA-00054");
    }
}
