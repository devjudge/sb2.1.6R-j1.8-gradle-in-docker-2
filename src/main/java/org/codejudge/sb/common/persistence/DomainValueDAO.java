/*
 * @(#)DomainValueDAO.java
 *
 * Copyright 2008 by Firm58, Inc.,
 * All rights reserved.
 *
 * $Id: DomainDAO.java,v 1.9 2009/01/31 01:22:34 kleggett Exp $
 */
package org.codejudge.sb.common.persistence;

import org.codejudge.sb.common.model.DomainClassEnum;
import org.codejudge.sb.common.model.DomainValue;
import org.codejudge.sb.common.persistence.core.PageResult;
import org.codejudge.sb.common.service.facade.DynamicSearchCriteria;

import java.util.List;

/**
 * This is the DAO interface for managing DomainValues.
 *
 * @author $Author: kleggett $
 * @version $Revision: 1.9 $
 * @see org.codejudge.sb.common.model.DomainValue
 * @since ?
 */
public interface DomainValueDAO extends ModelCrudDAO<DomainValue> {

    public List<DomainValue> getDomainValuesForDomainClassEnum(DomainClassEnum dce);

    public List<DomainValue> getDomainValuesByClass(DomainClassEnum domainClass, boolean activeOnly);

    public boolean isDomainValueImmutable(String domainValueId);

    public boolean doesDomainValueExist(String key, DomainClassEnum dce);

    public String getDomainValueIdForClassEnumAndKey(DomainClassEnum dce, String key);

    public DomainValue getDomainValueForClassEnumAndKey(DomainClassEnum dce, String key);

    public String createDomainValueIfNew(DomainValue domainValue);

    public PageResult<DomainValue> searchDomainValues(DynamicSearchCriteria criteria);
}
