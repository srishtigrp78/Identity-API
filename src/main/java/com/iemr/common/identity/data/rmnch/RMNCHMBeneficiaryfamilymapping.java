package com.iemr.common.identity.data.rmnch;

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

@Entity
@Table(name = "i_beneficiaryfamilymapping")
@Data
public class RMNCHMBeneficiaryfamilymapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private BigInteger benFamilyMapId;

	private BigInteger associatedBenRegId;

	@Column(nullable = false, updatable = false, insertable = true, length = 50)
	private String createdBy;

	@Column(name = "CreatedDate")
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

	@Column(name = "BenMapId", insertable = true, updatable = true)
	private BigInteger benMapId;

	@Expose
	@Column(name = "vanID", updatable = false)
	private Integer VanID;
	
	@Expose
	@Column(name = "parkingPlaceID", updatable = false)
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "VanSerialNo", updatable = false)
	private BigInteger id;
	
	@Expose
	@Column(name = "SyncedBy")
	private String SyncedBy;

	@Expose
	@Column(name = "SyncedDate")
	private Timestamp SyncedDate;

}