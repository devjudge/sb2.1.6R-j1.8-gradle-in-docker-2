package org.codejudge.sb.rating;

import java.util.Objects;

/**
 * @author jsantos
 */
public class LocalTransactionId
{
    private final long id;

    public long getId()
    {
        return id;
    }

    public static LocalTransactionId create(final String transactionIdString)
    {
        try
        {
            final Long transactionId = Long.valueOf(transactionIdString);
            return new LocalTransactionId(transactionId);
        }
        catch (NumberFormatException ex)
        {
            throw new InvalidStateException(String.format("Unable to parse transaction ID: %s", transactionIdString), ex);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocalTransactionId)) {
            return false;
        }
        LocalTransactionId that = (LocalTransactionId) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private LocalTransactionId(final long id)
    {
        this.id = id;
    }
}
