package org.codejudge.sb.common.util;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ptom
 * Date: 12/23/11
 * Time: 2:20 PM
 */
public class EqualsUtil
{
    /**
     * Safely checks if two objects are equal.
     *
     * @param first
     * @param second
     * @param <T>
     * @return true if the objects are equal.  otherwise, false.
     */
    public static <T> boolean areEqual(T first, T second)
    {
        return first == second || (first != null && first.equals(second));
    }

    /**
     * Check if two lists of Model objects are equal.  In this case, we re-order the lists
     * before checking for equality.
     *
     * @param first  a list of Model objects
     * @param second another list of Model objects
     * @param <T>    a type that extends Comparable
     * @return true if the list are equal.  otherwise, false.
     */
    public static <T extends Comparable> boolean areUnorderedListsEqual(List<T> first, List<T> second)
    {
        final boolean isEqual;

        if (first == second)
        {
            isEqual = true;
        }
        else if (first == null || second == null)
        {
            isEqual = false;
        }
        else
        {
            final List<T> orderedFirst = new ArrayList<T>(first);
            Collections.sort(orderedFirst);
            final List<T> orderedSecond = new ArrayList<T>(second);
            Collections.sort(orderedSecond);
            isEqual = orderedFirst.equals(orderedSecond);
        }

        return isEqual;
    }

    public static boolean areUnorderedGrantAuthoritiesEqual(List<GrantedAuthority> first, List<GrantedAuthority> second)
    {
        final boolean isEqual;

        if (first == second)
        {
            isEqual = true;
        }
        else if (first == null || second == null)
        {
            isEqual = false;
        }
        else
        {
            final List<String> firstAuthorities = extractAndOrderAuthorities(first);
            final List<String> secondAuthorities = extractAndOrderAuthorities(second);
            isEqual = firstAuthorities.equals(secondAuthorities);
        }

        return isEqual;
    }

    private static List<String> extractAndOrderAuthorities(List<GrantedAuthority> grantedAuthorities)
    {
        if (grantedAuthorities == null)
        {
            return null;
        }

        final List<String> authorities = new ArrayList<String>();
        for (final GrantedAuthority authority : grantedAuthorities)
        {
            authorities.add(authority.getAuthority());
        }
        Collections.sort(authorities);
        return authorities;
    }
}
