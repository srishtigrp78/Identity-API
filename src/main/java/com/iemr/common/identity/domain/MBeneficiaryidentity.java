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
 * The persistent class for the m_beneficiaryidentity database table.
 * 
 */
@Entity
@Table(name = "i_beneficiaryidentity")
@NamedQuery(name = "MBeneficiaryidentity.findAll", query = "SELECT m FROM MBeneficiaryidentity m order by benIdentityId asc")
@Data
public class MBeneficiaryidentity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private BigInteger benIdentityId;

	@Column(nullable = false, length = 50, insertable = true, updatable = false)
	private String createdBy;

	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted = false;

	private Timestamp expiryDate;

	@Column(length = 100)
	private String identityFilePath;

	private Integer identityNameId;

	@Column(length = 20)
	private String identityName;

	@Column(length = 30)
	private String identityNo;

	private Integer identityTypeId;

	@Column(length = 20)
	private String identityType;

	private Timestamp issueDate;

	private Boolean isVerified;

	private Timestamp lastModDate;

	@Column(length = 50)
	private String modifiedBy;

	@Column(nullable = false, length = 4)
	private String processed = "N";

	private Boolean reserved;

	private Integer reservedById;

	@Column(length = 45)
	private String reservedFor;

	private Timestamp reservedOn;

	// commented, 28-09-2018, unidirectional
	// bi-directional many-to-one association to MBeneficiarymapping
//	@ManyToOne(/* fetch = FetchType.EAGER */) /* (fetch = FetchType.LAZY) */
//	@JoinColumn(name = "BenMapId", insertable = false, updatable = false)
//	private MBeneficiarymapping MBeneficiarymapping;

	@Column(name = "BenMapId", insertable = true, updatable = true)
	private BigInteger benMapId;

	// new column added for data sync
	// 17-06-2018
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