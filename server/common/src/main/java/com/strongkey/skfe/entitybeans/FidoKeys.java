/**
 * Copyright StrongAuth, Inc. All Rights Reserved.
 *
 * Use of this source code is governed by the Gnu Lesser General Public License 2.3.
 * The license can be found at https://github.com/StrongKey/fido2/LICENSE
 */

package com.strongkey.skfe.entitybeans;

import com.strongkey.saka.web.Encryption;
import com.strongkey.saka.web.EncryptionService;
import com.strongkey.saka.web.StrongKeyLiteException_Exception;
import com.strongkey.skce.utilities.SAKAConnector;
import com.strongkey.skce.utilities.skceCommon;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "fido_keys")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FidoKeys.findAll", query = "SELECT f FROM FidoKeys f"),
    @NamedQuery(name = "FidoKeys.findAllbyDid", query = "SELECT f FROM FidoKeys f WHERE f.fidoKeysPK.did = :did"),
    @NamedQuery(name = "FidoKeys.findBySid", query = "SELECT f FROM FidoKeys f WHERE f.fidoKeysPK.sid = :sid"),
    @NamedQuery(name = "FidoKeys.findByDid", query = "SELECT f FROM FidoKeys f WHERE f.fidoKeysPK.did = :did"),
    @NamedQuery(name = "FidoKeys.findBySidFkid", query = "SELECT f FROM FidoKeys f WHERE f.fidoKeysPK.sid = :sid and f.fidoKeysPK.fkid = :fkid"),
//    @NamedQuery(name = "FidoKeys.findBySidDidFkid", query = "SELECT f FROM FidoKeys f WHERE f.fidoKeysPK.sid = :sid and f.fidoKeysPK.did = :did and f.fidoKeysPK.fkid = :fkid"),
    @NamedQuery(name = "FidoKeys.findBySidDidFkid", query = "SELECT f FROM FidoKeys f WHERE f.fidoKeysPK.sid = :sid and f.fidoKeysPK.did = :did and f.fidoKeysPK.username = :username and f.fidoKeysPK.fkid = :fkid"),
    @NamedQuery(name = "FidoKeys.findByUsername", query = "SELECT f FROM FidoKeys f WHERE f.fidoKeysPK.did = :did and f.fidoKeysPK.username = :username"),
    @NamedQuery(name = "FidoKeys.findByFkid", query = "SELECT f FROM FidoKeys f WHERE f.fidoKeysPK.fkid = :fkid"),
    @NamedQuery(name = "FidoKeys.findByKeyhandle", query = "SELECT f FROM FidoKeys f WHERE f.keyhandle = :keyhandle"),
    @NamedQuery(name = "FidoKeys.findByAppid", query = "SELECT f FROM FidoKeys f WHERE f.appid = :appid"),
    @NamedQuery(name = "FidoKeys.findByPublickey", query = "SELECT f FROM FidoKeys f WHERE f.publickey = :publickey"),
    @NamedQuery(name = "FidoKeys.findByKhdigest", query = "SELECT f FROM FidoKeys f WHERE f.khdigest = :khdigest"),
    @NamedQuery(name = "FidoKeys.findByKhdigestType", query = "SELECT f FROM FidoKeys f WHERE f.khdigestType = :khdigestType"),
    @NamedQuery(name = "FidoKeys.findByTransports", query = "SELECT f FROM FidoKeys f WHERE f.transports = :transports"),
    @NamedQuery(name = "FidoKeys.findByAttcid", query = "SELECT f FROM FidoKeys f WHERE f.attcid = :attcid"),
    @NamedQuery(name = "FidoKeys.findByCounter", query = "SELECT f FROM FidoKeys f WHERE f.counter = :counter"),
    @NamedQuery(name = "FidoKeys.findByFidoVersion", query = "SELECT f FROM FidoKeys f WHERE f.fidoVersion = :fidoVersion"),
    @NamedQuery(name = "FidoKeys.findByFidoProtocol", query = "SELECT f FROM FidoKeys f WHERE f.fidoProtocol = :fidoProtocol"),
    @NamedQuery(name = "FidoKeys.findByCreateDate", query = "SELECT f FROM FidoKeys f WHERE f.createDate = :createDate"),
    @NamedQuery(name = "FidoKeys.findByCreateLocation", query = "SELECT f FROM FidoKeys f WHERE f.createLocation = :createLocation"),
    @NamedQuery(name = "FidoKeys.findByModifyDate", query = "SELECT f FROM FidoKeys f WHERE f.modifyDate = :modifyDate"),
    @NamedQuery(name = "FidoKeys.findByModifyLocation", query = "SELECT f FROM FidoKeys f WHERE f.modifyLocation = :modifyLocation"),
    @NamedQuery(name = "FidoKeys.findByStatus", query = "SELECT f FROM FidoKeys f WHERE f.status = :status"),
    @NamedQuery(name = "FidoKeys.findBySignature", query = "SELECT f FROM FidoKeys f WHERE f.signature = :signature"),
    @NamedQuery(name = "FidoKeys.findByUsernameStatus", query = "SELECT f FROM FidoKeys f WHERE f.fidoKeysPK.did = :did and f.fidoKeysPK.username = :username and f.status = :status"),
//    @NamedQuery(name = "FidoKeys.maxpk", query = "SELECT max(f.fidoKeysPK.fkid) FROM FidoKeys f where f.fidoKeysPK.sid = :sid and f.fidoKeysPK.did = :did and f.fidoKeysPK.username = :username"),
    @NamedQuery(name = "FidoKeys.maxpk", query = "SELECT max(f.fidoKeysPK.fkid) FROM FidoKeys f where f.fidoKeysPK.sid = :sid"),
    @NamedQuery(name = "FidoKeys.findNewestKeyByUsernameStatus", query = "SELECT f FROM FidoKeys f where f.fidoKeysPK.did = :did and f.fidoKeysPK.username = :username and f.status = :status ORDER BY f.createDate DESC"),
    @NamedQuery(name = "FidoKeys.findByUsernameKH", query = "SELECT f FROM FidoKeys f WHERE f.fidoKeysPK.did = :did and f.fidoKeysPK.username = :username and f.keyhandle = :keyhandle")})
public class FidoKeys implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FidoKeysPK fidoKeysPK;
    @Size(max = 128)
    @Column(name = "userid")
    private String userid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "keyhandle")
    private String keyhandle;
    @Size(max = 512)
    @Column(name = "appid")
    private String appid;
    @Size(max = 512)
    @Column(name = "publickey")
    private String publickey;
    @Size(max = 512)
    @Column(name = "khdigest")
    private String khdigest;
    @Size(max = 7)
    @Column(name = "khdigest_type")
    private String khdigestType;
    @Column(name = "transports")
    private Short transports;
    @Column(name = "attsid")
    private Short attsid;
    @Column(name = "attdid")
    private Short attdid;
    @Column(name = "attcid")
    private Integer attcid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "counter")
    private int counter;
    @Size(max = 45)
    @Column(name = "fido_version")
    private String fidoVersion;
    @Size(max = 7)
    @Column(name = "fido_protocol")
    private String fidoProtocol;
    @Size(max = 36)
    @Column(name = "aaguid")
    private String aaguid;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "registration_settings")
    private String registrationSettings;
    @Column(name = "registration_settings_version")
    private Integer registrationSettingsVersion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "create_location")
    private String createLocation;
    @Column(name = "modify_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;
    @Size(max = 256)
    @Column(name = "modify_location")
    private String modifyLocation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "status")
    private String status;
    @Size(max = 2048)
    @Column(name = "signature")
    private String signature;

    @Transient
    private String id;
    
    public FidoKeys() {
    }

    public FidoKeys(FidoKeysPK fidoKeysPK) {
        this.fidoKeysPK = fidoKeysPK;
    }

    public FidoKeys(FidoKeysPK fidoKeysPK, String keyhandle, int counter, Date createDate, String createLocation, String status) {
        this.fidoKeysPK = fidoKeysPK;
        this.keyhandle = keyhandle;
        this.counter = counter;
        this.createDate = createDate;
        this.createLocation = createLocation;
        this.status = status;
    }

    public FidoKeys(short sid, short did, String username, long fkid) {
        this.fidoKeysPK = new FidoKeysPK(sid, did, username, fkid);
    }

    public FidoKeysPK getFidoKeysPK() {
        return fidoKeysPK;
    }

    public void setFidoKeysPK(FidoKeysPK fidoKeysPK) {
        this.fidoKeysPK = fidoKeysPK;
    }
    
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getKeyhandle() {
        return keyhandle;
    }

    public void setKeyhandle(String keyhandle) {
        String keyhandletoken = keyhandle;
        if (skceCommon.getConfigurationProperty("skce.cfg.property.db.keyhandle.encrypt").equalsIgnoreCase("true")) {
            String clusterid = "1";
            String domainid = skceCommon.getConfigurationProperty("skce.cfg.property.db.keyhandle.encrypt.saka.domainid");
            String sakausername = skceCommon.getClusterDomainProperty(Long.parseLong(clusterid), Long.parseLong(domainid), "username");
            String sakapassword = skceCommon.getClusterDomainProperty(Long.parseLong(clusterid), Long.parseLong(domainid), "password");
            String hosturl = skceCommon.getWorkingHostURLInCluster(Long.parseLong(clusterid), Long.parseLong(domainid));
            Encryption port = SAKAConnector.getSAKAConn().getSAKAPort(Integer.parseInt(clusterid), hosturl);
            if (port == null) {
                // Create URL for calling web-service
                URL baseUrl = com.strongkey.saka.web.EncryptionService.class.getResource(".");
                String ENCRYPTION_SERVICE_WSDL_SUFFIX = skceCommon.getConfigurationProperty("skce.cfg.property.saka.encryption.wsdlsuffix");
                URL url = null;
                try {
                    url = new URL(baseUrl, hosturl + ENCRYPTION_SERVICE_WSDL_SUFFIX);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(FidoKeys.class.getName()).log(Level.SEVERE, null, ex);
                }

                //  Check to see if the url is available.
                if (skceCommon.isURLAccessible(url)) {
                    // Create EncryptionService and Encryption objects
                    EncryptionService cryptosvc = new EncryptionService(url);
                    port = cryptosvc.getEncryptionPort();
                }
            }

            // Escrow the key and place the key/token in local map
            if (port != null) {
                try {
                    keyhandletoken = port.encrypt(Long.parseLong(domainid), sakausername, sakapassword, keyhandle);
                } catch (StrongKeyLiteException_Exception ex) {
                    Logger.getLogger(FidoKeys.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        this.keyhandle = keyhandletoken;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public String getKhdigest() {
        return khdigest;
    }

    public void setKhdigest(String khdigest) {
        this.khdigest = khdigest;
    }

    public String getKhdigestType() {
        return khdigestType;
    }

    public void setKhdigestType(String khdigestType) {
        this.khdigestType = khdigestType;
    }

    public Short getTransports() {
        return transports;
    }

    public void setTransports(Short transports) {
        this.transports = transports;
    }

    public Short getAttsid() {
        return attsid;
    }

    public void setAttsid(Short attsid) {
        this.attsid = attsid;
    }

    public Short getAttdid() {
        return attdid;
    }

    public void setAttdid(Short attdid) {
        this.attdid = attdid;
    }

    public Integer getAttcid() {
        return attcid;
    }

    public void setAttcid(Integer attcid) {
        this.attcid = attcid;
    }

    @XmlTransient
    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getFidoVersion() {
        return fidoVersion;
    }

    public void setFidoVersion(String fidoVersion) {
        this.fidoVersion = fidoVersion;
    }

    public String getFidoProtocol() {
        return fidoProtocol;
    }

    public void setFidoProtocol(String fidoProtocol) {
        this.fidoProtocol = fidoProtocol;
    }
    
    public String getAaguid() {
        return aaguid;
    }

    public void setAaguid(String aaguid) {
        this.aaguid = aaguid;
    }

    public String getRegistrationSettings() {
        return registrationSettings;
    }

    public void setRegistrationSettings(String registrationSettings) {
        this.registrationSettings = registrationSettings;
    }

    public Integer getRegistrationSettingsVersion() {
        return registrationSettingsVersion;
    }

    public void setRegistrationSettingsVersion(Integer registrationSettingsVersion) {
        this.registrationSettingsVersion = registrationSettingsVersion;
    }

    @XmlTransient
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @XmlTransient
    public String getCreateLocation() {
        return createLocation;
    }

    public void setCreateLocation(String createLocation) {
        this.createLocation = createLocation;
    }

    @XmlTransient
    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @XmlTransient
    public String getModifyLocation() {
        return modifyLocation;
    }

    public void setModifyLocation(String modifyLocation) {
        this.modifyLocation = modifyLocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getId() {
        return id;
    }

    @XmlAttribute
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fidoKeysPK != null ? fidoKeysPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FidoKeys)) {
            return false;
        }
        FidoKeys other = (FidoKeys) object;
        if ((this.fidoKeysPK == null && other.fidoKeysPK != null) || (this.fidoKeysPK != null && !this.fidoKeysPK.equals(other.fidoKeysPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.strongauth.skce.entitybeans.FidoKeys[ fidoKeysPK=" + fidoKeysPK + " ]";
    }
}
