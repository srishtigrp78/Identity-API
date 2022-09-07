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
 * The persistent class for the m_beneficiaryfamilymapping database table.
 * 
 */
@Entity
@Table(name = "i_beneficiaryfamilymapping")
@NamedQuery(name = "MBeneficiaryfamilymapping.findAll", query = "SELECT m FROM MBeneficiaryfamilymapping m")
@Data
// @SelectBeforeUpdate(value=true)
// @DynamicUpdate(value=true)
public class MBeneficiaryfamilymapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private BigInteger benFamilyMapId;

	private BigInteger associatedBenRegId;

	@Column(nullable = false, updatable = false, insertable = true, length = 50)
	private String createdBy;

	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	private Boolean isEmergencyContact;

	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	@Column(length = 50)
	private String modifiedBy;

	@Column(nullable = false, length = 4)
	private String processed = "N";

	@Column(length = 15)
	private String relationshipToSelf;

	@Column
	private Integer relationshipID;

	private Boolean reserved;

	private Integer reservedById;

	@Column(length = 45)
	private String reservedFor;

	private Timestamp reservedOn;

	// commented, 28-09-2018
	// bi-directional many-to-one association to MBeneficiarymapping
//	@ManyToOne(/* fetch = FetchType.EAGER */)
//	@JoinColumn(name = "BenMapId", insertable = false, updatable = false)
//	private MBeneficiarymapping MBeneficiarymapping;

	@Column(name = "BenMapId", insertable = true, updatable = true)
	private BigInteger benMapId;

	// new column added for data sync
	// 17-06-2018
	@Expose
	@Column(name = "VanID", updatable = false)
	private Integer vanID;
	@Expose
	@Column(name = "parkingPlaceID", updatable = false)
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "VanSerialNo", updatable = false)
	private BigInteger vanSerialNo;

	// END OF new column added for data sync

}