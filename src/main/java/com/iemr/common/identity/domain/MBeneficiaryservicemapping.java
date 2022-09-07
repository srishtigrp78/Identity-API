package com.iemr.common.identity.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import lombok.Data;

/**
 * The persistent class for the m_beneficiaryservicemapping database table.
 * 
 */
@Entity
@Table(name = "i_beneficiaryservicemapping")
@NamedQuery(name = "MBeneficiaryservicemapping.findAll", query = "SELECT m FROM MBeneficiaryservicemapping m")
@Data
public class MBeneficiaryservicemapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private BigInteger benServiceMapID;

	@Column(nullable = false, length = 50)
	private String createdBy;

	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Column(nullable = false)
	private Boolean deleted = false;

	private Timestamp firstAvailedOn;

	private Timestamp lastModDate;

	@Column(length = 50)
	private String modifiedBy;

	@Column(nullable = false, length = 4)
	private String processed = "N";

	@Column(length = 45)
	private String registeredByName;

	private Integer registeredById;

	private Timestamp registeredDate;

	private Integer providerServiceMapId;

	private Boolean reserved;

	private Integer reservedById;

	@Column(length = 45)
	private String reservedFor;

	private Timestamp reservedOn;

	private Integer serviceId;

	@Column(length = 45)
	private String serviceName;

	private Integer serviceProviderId;

	@Column(length = 45)
	private String serviceProviderName;

	private Integer stateId;

	@Column(length = 45)
	private String stateName;

	// bi-directional many-to-one association to MBeneficiarymapping
//	@ManyToOne(/* fetch = FetchType.EAGER */) /* (fetch = FetchType.LAZY) */
//	@JoinColumn(name = "BenMapId")
//	private MBeneficiarymapping MBeneficiarymapping;
	
	@Column(name = "BenMapId", insertable = true, updatable = true)
	private BigInteger benMapId;

	// new column added for data sync
	// 17-06-2018
	@Expose
	@Column(name = "vanID", updatable = false)
	private Integer vanID;
	@Expose
	@Column(name = "parkingPlaceID", updatable = false)
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "VanSerialNo", updatable = false)
	private BigInteger vanSerialNo;

	// END OF new column added for data sync
}