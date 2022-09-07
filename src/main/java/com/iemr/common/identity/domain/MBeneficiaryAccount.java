package com.iemr.common.identity.domain;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;

import lombok.Data;

@Entity
@Table(name = "i_beneficiaryaccount")
@Data
public class MBeneficiaryAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BenAccountID")
	private BigInteger benAccountID;

	@Column(name = "BankName", length = 100)
	private String bankName;

	@Column(name = "branchName", length = 100)
	private String branchName;

	@Column(name = "IFSCCode", length = 20)
	private String ifscCode;

	@Column(name = "AccountNo", length = 50)
	private String accountNo;

	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted = false;

	@Column(name = "Processed", insertable = false)
	private String processed = "N";

	@Column(name = "CreatedBy", length = 50, updatable = false)
	private String createdBy;

	@Column(name = "CreatedDate", insertable = false, updatable = false)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
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
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Timestamp lastModDate;

	@Column(name = "VehicalNo", length = 30)
	private String vehicleNo;

	// @Column(name = "ParkingPlaceID")
	// private Integer parkingPlaceID;

	@Column(name = "SyncedBy", length = 30)
	private String syncedBy;

	@Column(name = "SyncedDate", unique = true, nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Timestamp syncedDate;

	@Column(name = "ReservedForChange", length = 30)
	private String reservedForChange;

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
