package org.codejudge.sb.common.util;

/**
 * Created by IntelliJ IDEA.
 * User: ptom
 * Date: 12/30/11
 * Time: 10:52 AM
 */
public class HashCodeUtil
{
    /**
     * Add an object to an existing hash code value
     *
     * @param hashCode current hash code
     * @param object   object to add to the hash code
     * @param <T>
     * @return
     */
    public static <T extends Object> int calcHashCode(final int hashCode, final T object)
    {
        return hashCode * 31 + (object == null ? 0 : object.hashCode());
    }
}
