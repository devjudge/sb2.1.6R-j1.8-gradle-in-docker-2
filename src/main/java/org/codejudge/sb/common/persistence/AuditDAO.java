package org.codejudge.sb.common.persistence;


import org.codejudge.sb.common.model.Audit;

import java.util.List;

/**
 * DAO supplying interactions with system audits.
 * See the AuditService for documentation on pass thru methods.
 *
 * @author $Author: jqian $
 * @version $Revision: 1.3 $
 * @since NITROGEN
 */
public interface AuditDAO extends DAO
{

    public void createAudit(Audit audit);

    public Audit getAuditByAction(String objectId, String actionKey);

    public List<Audit> getAuditsForObject(String objectId);

    /**
     * Delete all audits for the given object. This is typically called when the object is being removed from the system
     *
     * @param objectId
     */
    public void deleteAuditsForObject(String objectId);
}
