package org.codejudge.sb.common.model;

import org.apache.commons.lang3.StringUtils;
import org.codejudge.sb.common.util.EqualsUtil;
import org.codejudge.sb.common.util.HashCodeUtil;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author $Author: kleggett $
 * @version $Revision: 1.11 $
 * @since SODIUM
 */
public class Model extends SuperModel<String> implements Serializable {

    // ALPHA 11647:  set serialVersionUID to that auto-generated as of VANADIUM
    private static final long serialVersionUID = 201312051136L;

    private String id;

    public Model() {
        super();
    }

    public Model(Model m) {
        super(m);
        if (m != null) {
            id = m.id;
        }
    }

    @Override
    @XmlAttribute
    public String getId() {
        return isPrimaryKeySet() ? id : null;
    }

    @Override
    public void setId(String primaryKey) {
        this.id = primaryKey;
    }

    @Override
    public boolean isPrimaryKeySet() {
        return StringUtils.isNotBlank(id);
    }

    @Override
    public void clearPrimaryKey() {
        if (isPrimaryKeySet()) {
            this.id = null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        final boolean isEqual;
        // try an instance equality check
        if (this == obj) {
            isEqual = true;
        } else if (obj == null) {
            isEqual = false;
        } else if (!(obj instanceof Model)) {
            isEqual = false;
        } else {
            // although we use the class in the hash code, we can't used it to check equality due to polymorphism
            final Model other = (Model) obj;
            isEqual = isPrimaryKeySet() && EqualsUtil.areEqual(id, other.id);
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        // need to add the class to the hash code to make the reflection toString builder work correctly.  this is lame.
        // it stems from the fact the primary key may not be unique across objects types.
        hashCode = HashCodeUtil.calcHashCode(hashCode, getClass());
        hashCode = isPrimaryKeySet() ? HashCodeUtil.calcHashCode(hashCode, id) : super.hashCode();
        return hashCode;
    }

    public static List<String> extractIds(List<? extends Model> modelList) {
        if (modelList != null) {
            ArrayList<String> ids = new ArrayList<>(modelList.size());
            for (Model model : modelList) {
                ids.add(model.getId());
            }
            return ids;
        }

        return null;
    }
}
