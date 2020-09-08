package org.codejudge.sb.common.persistence;

import org.codejudge.sb.common.model.Model;

/**
 * @author kleggett
 * @since BROMINE (12/16/13 12:19 PM)
 * @param <M> the type of the {@link Model} object which the DAO is for
 */
public interface ModelCrudDAO<M extends Model> extends F58CrudDAO<String, M>
{
}
