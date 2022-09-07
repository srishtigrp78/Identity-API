package com.iemr.common.identity.data.rmnch;

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
@Table(name = "i_beneficiaryservicemapping")
@Data
public class RMNCHMBeneficiaryServiceMap {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private BigInteger benServiceMapID;

	@Column(name = "BenMapID")
	private BigInteger benMapID;

	private Integer ProviderServiceMapID;

	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted = false;

	@Column(name = "Processed")
	private String processed = "N";

	@Column(name = "CreatedBy", length = 50, updatable = false)
	private String createdBy;

	@Column(name = "CreatedDate", updatable = false)
	private Timestamp createdDate;

	@Column(name = "Reserved")
	private Boolean reserved;

	@Column(name = "ReservedFor", length = 45)
	private String reservedFor;

	@Column(name = "ReservedOn", length = 45)
	private String reservedOn;

	@Column(name = "ReservedById")
	private Integer reservedById;

	@Column(name = "ModifiedBy", length = 50)
	private String modifiedBy;

	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	@Column(name = "VehicalNo", length = 30)
	private String vehicleNo;

	@Column(name = "ReservedForChange", length = 30)
	private String reservedForChange;

	@Expose
	@Column(name = "vanID", updatable = false)
	private Integer VanID;

	@Expose
	@Column(name = "parkingPlaceID", updatable = false)
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "VanSerialNo", updatable = false)
	private Long id;

	@Expose
	@Column(name = "SyncedBy")
	private String SyncedBy;

	@Expose
	@Column(name = "SyncedDate")
	private Timestamp SyncedDate;
}
