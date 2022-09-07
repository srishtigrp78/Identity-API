package com.iemr.common.identity.data.rmnch;

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
@Table(name = "i_beneficiaryimage")
@Data
public class RMNCHMBeneficiaryImage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BenImageId")
	private BigInteger benImageId;

	@Column(name = "BenImage")
	private String user_image;

	@Column(name = "BenBiometric")
	private String benBiometric;

	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted = false;

	@Column(name = "Processed")
	private String Processed = "N";

	@Column(name = "CreatedBy", updatable = false)
	private String createdBy;

	@Column(name = "CreatedDate", updatable = false)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Timestamp createdDate;

	@Column(name = "Reserved")
	private Boolean reserved;

	@Column(name = "ReservedFor")
	private String reservedFor;

	@Column(name = "ReservedOn")
	private String reservedOn;

	@Column(name = "ReservedById")
	private Integer reservedById;

	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Column(name = "LastModDate")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Timestamp lastModDate;

	@Column(name = "VehicalNo")
	private String vehicalNo;

	@Column(name = "ReservedForChange")
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
