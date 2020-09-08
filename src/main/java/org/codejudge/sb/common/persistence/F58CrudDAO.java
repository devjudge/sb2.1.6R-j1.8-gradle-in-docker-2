package org.codejudge.sb.common.persistence;


import org.codejudge.sb.common.model.SuperModel;

/**
 * @author kleggett
 * @since BROMINE (12/10/13 1:39 PM)
 * @param <ID> the type of the model id
 * @param <M> the type of the model that extends {@link SuperModel}
 */
public interface F58CrudDAO<ID, M extends SuperModel>
{
    ID saveModel(M model);

    M getById(ID id);

    ID insertModel(M model);

    int updateModel(M model);

    int deleteById(ID id);
}
