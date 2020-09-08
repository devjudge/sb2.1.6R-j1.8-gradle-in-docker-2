package org.codejudge.sb.common.service.facade;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the basic elements for doing paginated DB searches.
 *
 * @author kleggett
 * @version 1.11
 * @since ?
 */
public class AbstractSearchCriteria implements ISearchCriteria
{
    private static final long serialVersionUID = 2168855829322230887L;

    private String id;
    private String customerId;

    private String firstId;
    private String lastId;

    private Object lessThan;
    private Object greaterThan;

    private String searchType;
    private String orderBy;
    private String orderByDirection;

    private boolean naturalAlphaOrdering;
    private List<String> noAlphaSort;

    /**
     * The max number of rows to return on one page of results.
     */
    private int max;

    /**
     * The numbers of pages (of {@link #max} results) to skip.
     */
    private int pageNumber;

    /**
     * The row the results should skip to.  You can use this instead of
     * {@link #pageNumber}, it gets checked first.
     */
    private Integer startRow;

    public AbstractSearchCriteria()
    {
        naturalAlphaOrdering = false;
        noAlphaSort = new ArrayList<>();
        addToNoAlphaSort("last_modified_date");
    }

    public AbstractSearchCriteria(AbstractSearchCriteria copy)
    {
        this.id = copy.id;
        this.customerId = copy.customerId;
        this.firstId = copy.firstId;
        this.lastId = copy.lastId;
        this.lessThan = copy.lessThan;
        this.greaterThan = copy.greaterThan;
        this.searchType = copy.searchType;
        this.orderBy = copy.orderBy;
        this.orderByDirection = copy.orderByDirection;
        this.max = copy.max;
        this.pageNumber = copy.pageNumber;
        this.startRow = copy.startRow;
        naturalAlphaOrdering = false;
        noAlphaSort = new ArrayList<>();
        addToNoAlphaSort("last_modified_date");
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

    public String getFirstId()
    {
        return firstId;
    }

    public void setFirstId(String firstId)
    {
        this.firstId = firstId;
    }

    public String getLastId()
    {
        return lastId;
    }

    public void setLastId(String lastId)
    {
        this.lastId = lastId;
    }

    public Object getLessThan()
    {
        return lessThan;
    }

    public void setLessThan(Object lessThan)
    {
        this.lessThan = lessThan;
    }

    public Object getGreaterThan()
    {
        return greaterThan;
    }

    public void setGreaterThan(Object greaterThan)
    {
        this.greaterThan = greaterThan;
    }

    public String getSearchType()
    {
        return searchType;
    }

    public void setSearchType(String searchType)
    {
        this.searchType = searchType;
    }

    public String getOrderBy()
    {
        return orderBy;
    }

    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }

    public String getOrderByDirection()
    {
        return this.orderByDirection;
    }

    public void setOrderByDirection(String orderByDirection)
    {
        this.orderByDirection = orderByDirection;
    }

    public int getMax()
    {
        return max;
    }

    public void setMax(int max)
    {
        this.max = max;
        if (this.max < 0)
        {
            this.max = Integer.MAX_VALUE;
        }
    }

    public int getPageNumber()
    {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber)
    {
        setPageNumber(pageNumber, false);
    }

    public void setPageNumber(int pageNumber, boolean allowNegative)
    {
        if (allowNegative)
        {
            this.pageNumber = pageNumber;
        }
        else
        {
            this.pageNumber = pageNumber < 0 ? 0 : pageNumber;
        }
    }

    @Override
    public void setStartRow(int startRow)
    {
        this.startRow = startRow;
        if (this.startRow != null && this.startRow < 0)
        {
            this.startRow = 0;
        }
    }

    /**
     * Returns the row number that the results should start on.  If
     * {@link #startRow} is set, it will use that.  Otherwise the value will be
     * computed based upon the pageNumber and the {@link #max} values.
     * The startRow or page number and max must be set prior to this call.
     *
     * @return the row number that the results should start on
     * @see #getEndRow()
     */
    @Override
    public final int getStartRow()
    {
        if (startRow != null)
        {
            return startRow;
        }
        return getPageNumber() * getMax();
    }

    /**
     * Returns the computed end row.
     * The page number and max returned result must be set prior to this call.
     *
     * @see #getStartRow()
     */
    @Override
    public final int getEndRow()
    {
        if (getMax() == Integer.MAX_VALUE)
        {
            return getMax();
        }
        else
        {
            return getStartRow() + getMax();
        }
    }


    /**
     * Returns the arguments to an order by clause,
     * combining the orderBy and orderByDirection field.
     * (Does not include the "order by" part.)
     */
    public String getOrderByClause()
    {
        if (StringUtils.isBlank(getOrderBy()))
        {
            return "id";
        }

        StringBuilder orderBy = new StringBuilder();

        if (isNaturalAlphaOrdering() && canAlphaSort(getOrderBy()))
        {
            orderBy.append("nlssort(");
            orderBy.append(getSortColumn());
            orderBy.append(", 'NLS_SORT=BINARY_CI')");
        }
        else
        {
            orderBy.append(getOrderBy());
        }

        orderBy.append(" ");

        if (StringUtils.isBlank(getSortDirection()))
        {
            orderBy.append("ASC");
        }
        else
        {
            orderBy.append(getSortDirection());
        }

        orderBy.append(", id");

        return orderBy.toString();
    }

    protected final String getLikeParam(String value)
    {
        if (StringUtils.isNotBlank(value))
        {
            return "'%" + value.toUpperCase() + "%'";
        }
        else
        {
            return null;
        }
    }

    @Override
    public int getNumRows()
    {
        return getMax();
    }

    @Override
    public void setNumRows(int numRows)
    {
        setMax(numRows);
    }

    @Override
    public String getSortColumn()
    {
        return getOrderBy();
    }

    @Override
    public void setSortColumn(String sortColumn)
    {
        setOrderBy(sortColumn);
    }

    @Override
    public String getSortDirection()
    {
        return getOrderByDirection();
    }

    @Override
    public void setSortDirection(String sortDirection)
    {
        setOrderByDirection(sortDirection);
    }

    public boolean isNaturalAlphaOrdering()
    {
        return naturalAlphaOrdering;
    }

    public void setNaturalAlphaOrdering(boolean naturalAlphaOrdering)
    {
        this.naturalAlphaOrdering = naturalAlphaOrdering;
    }

    public void addToNoAlphaSort(String column)
    {
        noAlphaSort.add(column);
    }

    protected boolean canAlphaSort(String column)
    {
        return !noAlphaSort.contains(column);
    }
}
