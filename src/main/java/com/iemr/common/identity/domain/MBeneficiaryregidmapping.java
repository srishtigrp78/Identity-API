package com.iemr.common.identity.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import lombok.Data;

/**
 * The persistent class for the m_beneficiaryregid database table.
 * 
 */
@Entity
@Table(name = "m_beneficiaryregidmapping")
@NamedQuery(name = "MBeneficiaryregidmapping.findAll", query = "SELECT m FROM MBeneficiaryregidmapping m")
@Data
public class MBeneficiaryregidmapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private BigInteger benRegId;

	@Column(nullable = false)
	private BigInteger beneficiaryID;

	@Column(length = 30)
	private String createdBy;

	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	private Boolean deleted = false;

	private Boolean provisioned;

	private Integer providerServiceMapID;

	private Boolean reserved;

	private Integer reservedById;

	@Column(length = 45)
	private String reservedFor;

	private Timestamp reservedOn;

	@Column(name = "VehicalNo")
	private String vehicalNo;

	// private Integer ParkingPlaceID;

	private String syncedBy;

	private Timestamp syncedDate;

	private String reservedForChange;

	// //bi-directional one-to-one association to MBeneficiarydetail
	// @OneToOne(fetch=FetchType.LAZY)
	// @JoinColumn(name="BenRegId", referencedColumnName="BeneficiaryRegID",
	// nullable=false, insertable=false, updatable=false)
	// private MBeneficiarydetail MBeneficiarydetail;
	//
	// //bi-directional one-to-one association to MBeneficiarymapping
	// @OneToOne(fetch=FetchType.LAZY)
	// @JoinColumn(name="BenRegId", referencedColumnName="BenRegId", nullable=false,
	// insertable=false, updatable=false)
	// private MBeneficiarymapping MBeneficiarymapping;
	//
	// //bi-directional many-to-one association to MBeneficiaryfamilymapping
	// @OneToMany(mappedBy="MBeneficiaryregid")
	// private List<MBeneficiaryfamilymapping> MBeneficiaryfamilymappings;

	// new column added for data sync
	// 17-06-2018
	@Expose
	@Column(name = "vanID")
	private Integer vanID;
	@Expose
	@Column(name = "parkingPlaceID")
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "VanSerialNo")
	private BigInteger vanSerialNo;

	// END OF new column added for data sync
}