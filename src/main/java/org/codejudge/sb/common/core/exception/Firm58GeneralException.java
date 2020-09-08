package org.codejudge.sb.common.core.exception;
/**
 * Generic unchecked base class implementation for Firm58 exceptions.
 * Firm58GeneralException.java,v 1.6 2007/12/14 16:43:50 tgee Exp
 */
public class Firm58GeneralException extends RuntimeException
{
    private static final long serialVersionUID = 2770614951482587603L;

    private Firm58ExceptionTypeEnum firm58ExceptionType;
    private boolean logged = false;
    /**
     * A String that can be used to convey an error message back to the user.
     * This will be deprecated once we have the capacity to access ResourceBundles of
     * error message content.
     */
    private String defaultErrorString;


    public Firm58GeneralException(Firm58ExceptionTypeEnum firm58ExceptionType)
    {
        super();
        this.firm58ExceptionType = firm58ExceptionType;
    }

    /**
     * @param message The exception message
     */
    public Firm58GeneralException(String message, Firm58ExceptionTypeEnum firm58ExceptionType)
    {
        super(message);
        this.firm58ExceptionType = firm58ExceptionType;
    }

    /**
     * @param message        The exception message
     * @param innerException The root cause
     */
    public Firm58GeneralException(String message, Throwable innerException, Firm58ExceptionTypeEnum firm58ExceptionType)
    {
        super(message, innerException);
        this.firm58ExceptionType = firm58ExceptionType;
    }

    /**
     * @param innerException The root cause
     */
    public Firm58GeneralException(Throwable innerException, Firm58ExceptionTypeEnum firm58ExceptionType)
    {
        super(innerException);
        this.firm58ExceptionType = firm58ExceptionType;
    }

    /**
     * @param innerException The root cause
     * @deprecated Use the version that specifies a Firm58ExceptionType
     */
    @Deprecated
    public Firm58GeneralException(Throwable innerException)
    {
        super(innerException);
        this.firm58ExceptionType = Firm58ExceptionTypeEnum.UNKNOWN_SYSTEM_ERROR;
    }


    public Firm58ExceptionTypeEnum getFirm58ExceptionType()
    {
        return this.firm58ExceptionType;
    }

    public boolean isLogged()
    {
        return logged;
    }

    public void setLogged(boolean logged)
    {
        this.logged = logged;
    }

    public String getDefaultErrorString()
    {
        return defaultErrorString;
    }

    public void setDefaultErrorString(String defaultErrorString)
    {
        this.defaultErrorString = defaultErrorString;
    }
}
