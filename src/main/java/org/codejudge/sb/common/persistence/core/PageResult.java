package org.codejudge.sb.common.persistence.core;

import java.io.Serializable;
import java.util.List;

/**
 * The PageResult object wraps a List of DB result rows and contains meta information
 * regarding the list within the larger dataset for paging.
 * Usage should include parameterization on the type in the results List.
 * $Id$
 */
public class PageResult<T> implements Serializable
{
    private static final long serialVersionUID = -5685905515319381300L;

    /**
     * The list of DB results.  A List of some View object.
     */
    // TODO make a parent View class object and constrain this list to that one.
    private List<T> results;
    /**
     * The number of rows that could be returned if the original query were run unconstrained by page limits.
     */
    private int virtualCount;
    /**
     * 'true' if there exists rows *before* the returned segment.
     */
    // TODO rename to hasPrevious
    private boolean previous;
    /**
     * 'true' if there exists rows *after* the returned segment.
     */
    // TODO rename to hasNext
    private boolean next;


    /**
     * For serialization only not to be used.
     *
     * @deprecated TODO Make protected
     */
    @Deprecated
    public PageResult()
    { }

    public PageResult(List<T> results)
    {
        this(results, 0, results.size() - 1, results.size());
    }

    public PageResult(List<T> results, int startRow, int endRow, int virtualCount)
    {
        this.results = results;
        this.virtualCount = virtualCount;

        previous = (startRow > 0);
        next = (endRow < virtualCount);
    }


    public List<T> getResults()
    {
        return results;
    }

    /**
     * @deprecated Roll into constructor
     */
    @Deprecated
    public void setResults(List<T> results)
    {
        this.results = results;
    }

    public int getVirtualCount()
    {
        return virtualCount;
    }

    /**
     * @deprecated Roll into constructor
     */
    @Deprecated
    public void setVirtualCount(int virtualCount)
    {
        this.virtualCount = virtualCount;
    }

    public boolean isPrevious()
    {
        return previous;
    }

    /**
     * @deprecated Roll into constructor
     */
    @Deprecated
    public void setPrevious(boolean previous)
    {
        this.previous = previous;
    }

    public boolean isNext()
    {
        return next;
    }

    /**
     * @deprecated Roll into constructor
     */
    @Deprecated
    public void setNext(boolean next)
    {
        this.next = next;
    }
}
