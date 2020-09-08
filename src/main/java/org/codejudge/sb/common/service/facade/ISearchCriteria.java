/*
 * @(#)ISearchCriteria.java
 *
 * Copyright 2009 by Firm58, Inc.,
 * All rights reserved.
 *
 * $Id: $
 */
package org.codejudge.sb.common.service.facade;

import java.io.Serializable;

/**
 * This interface marks search criterias and defines some basic methods.
 *
 * @author $Author: kleggett$
 * @version $Revision: $
 * @since SILICON
 */
public interface ISearchCriteria extends Serializable
{
    /**
     * Returns the row number that the results should start on.
     *
     * @return the row number that the results should start on
     */
    public int getStartRow();

    public void setStartRow(int startRow);

    /**
     * Returns the number of rows the search should return.
     *
     * @return the number of rows the search should return
     */
    public int getNumRows();

    public void setNumRows(int numRows);

    /**
     * Returns the computed row the results should end on.  numRows and
     * startRow need to be set for this to return a valid result.
     *
     * @return the row the results should end on
     */
    public int getEndRow();

    /**
     * Gets the name of the column the results should be sorted by.
     *
     * @return the name of the column the results should be sorted by
     */
    public String getSortColumn();

    public void setSortColumn(String sortColumn);

    /**
     * Gets the direction of the sort, either ASC or DESC.
     *
     * @return the direction of the sort
     */
    public String getSortDirection();

    public void setSortDirection(String sortDirection);
}
