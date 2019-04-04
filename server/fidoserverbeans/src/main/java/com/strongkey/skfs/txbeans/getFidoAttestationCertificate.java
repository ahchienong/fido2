/**
 * Copyright StrongAuth, Inc. All Rights Reserved.
 *
 * Use of this source code is governed by the Gnu Lesser General Public License 2.3.
 * The license can be found at https://github.com/StrongKey/fido2/LICENSE
 */

package com.strongkey.skfs.txbeans;

import com.strongkey.skfs.entitybeans.AttestationCertificates;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mishimoto
 */
@Stateless
public class getFidoAttestationCertificate implements getFidoAttestationCertificateLocal {

    /**
     * Persistence context for derby
     */
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public AttestationCertificates getByPK(Long did, Long sid, Long attcid){
        try {
            Query q = em.createNamedQuery("AttestationCertificates.findBySidDidAttcid");
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            q.setParameter("did", did);
            q.setParameter("sid", sid);
            q.setParameter("attcid", attcid);
            AttestationCertificates ac = (AttestationCertificates) q.getSingleResult();
//            if (ac != null) {                       //TODO verify signature
//                verifyDBRecordSignature(did, fp);
//            }
            return ac;
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    @Override
    public AttestationCertificates getByIssuerDnSerialNumber(String issuerDn, String serialNumber){
        try {
            Query q = em.createNamedQuery("AttestationCertificates.findByIssuerDnSerialNumber");
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            q.setParameter("issuerDn", issuerDn);
            q.setParameter("serialNumber", serialNumber);
            AttestationCertificates ac = (AttestationCertificates) q.getSingleResult();
//            if (ac != null) {                       //TODO verify signature
//                verifyDBRecordSignature(did, fp);
//            }
            return ac;
        } catch (NoResultException ex) {
            return null;
        }
    }
}
