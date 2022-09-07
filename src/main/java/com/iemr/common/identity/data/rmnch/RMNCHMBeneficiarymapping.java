package com.iemr.common.identity.data.rmnch;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Entity
@Table(name = "i_beneficiarymapping")
@Data
public class RMNCHMBeneficiarymapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private BigInteger benMapId;

	@Column(nullable = false, length = 50)
	private String createdBy;

	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Column(nullable = false)
	private Boolean deleted = false;

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

	@Column(name = "BenAddressId")
	private BigInteger benAddressId;

	@Column(name = "BenConsentId")
	private BigInteger benConsentId;

	@Column(name = "BenContactsId")
	private BigInteger benContactsId;

	@Column(name = "BenDetailsId")
	private BigInteger benDetailsId;

	@Column(name = "benImageId")
	private BigInteger benImageId;

	@Column(name = "benAccountID")
	private BigInteger benAccountID;

	@Column(name = "BenRegId")
	private BigInteger benRegId;

	@Expose
	@Column(name = "vanID")
	private Integer VanID;

	@Expose
	@Column(name = "parkingPlaceID")
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "VanSerialNo")
	private BigInteger id;
	
	@Expose
	@Column(name = "SyncedBy")
	private String SyncedBy;

	@Expose
	@Column(name = "SyncedDate")
	private Timestamp SyncedDate;
}