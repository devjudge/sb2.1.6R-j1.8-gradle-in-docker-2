package org.codejudge.sb.common.persistence;


import org.codejudge.sb.common.model.DomainClassEnum;
import org.codejudge.sb.common.model.DomainValue;
import org.codejudge.sb.common.model.Model;
import org.codejudge.sb.common.persistence.core.PageResult;
import org.codejudge.sb.common.service.facade.AbstractSearchCriteria;
import org.codejudge.sb.common.service.facade.DynamicSearchCriteria;

import java.util.List;

/**
 * Interface supporting generic persistence
 */
public interface DAO
{
    PageResult<?> getListPage(AbstractSearchCriteria criteria);

    /**
     * This is the DAO interface for managing DomainValues.
     *
     * @author $Author: kleggett $
     * @version $Revision: 1.9 $
     * @see org.codejudge.sb.common.model.DomainValue
     * @since ?
     */
    interface DomainValueDAO extends ModelCrudDAO<DomainValue>
    {
        public List<DomainValue> getDomainValuesForDomainClassEnum(DomainClassEnum dce);

        public List<DomainValue> getDomainValuesByClass(DomainClassEnum domainClass, boolean activeOnly);

        public boolean isDomainValueImmutable(String domainValueId);

        public boolean doesDomainValueExist(String key, DomainClassEnum dce);

        public String getDomainValueIdForClassEnumAndKey(DomainClassEnum dce, String key);

        public DomainValue getDomainValueForClassEnumAndKey(DomainClassEnum dce, String key);

        public String createDomainValueIfNew(DomainValue domainValue);

        public PageResult<DomainValue> searchDomainValues(DynamicSearchCriteria criteria);
    }

    /**
     * @author kleggett
     * @since BROMINE (12/16/13 12:19 PM)
     * @param <M> the type of the {@link Model} object which the DAO is for
     */
    interface ModelCrudDAO<M extends Model> extends F58CrudDAO<String, M>
    {
    }
}
