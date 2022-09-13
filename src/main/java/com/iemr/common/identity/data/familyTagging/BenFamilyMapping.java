package com.iemr.common.identity.data.familyTagging;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
@Entity
@Table(name = "i_benfamilytag")
public class BenFamilyMapping {

	@Id
	@GeneratedValue
	@Expose
	@Column(name = "BenFamilyTagId")
	private Long benFamilyTagId;

	@Expose
	@Column(name = "Familyid")
	private String familyId;

	@Expose
	@Column(name = "FamilyName")
	private String familyName;

	@Expose
	@Column(name = "noofmembers")
	private Integer noOfmembers;

	@Expose
	@Column(name = "Villageid")
	private Integer villageId;

	@Expose
	@Column(name = "FamilyHeadName")
	private String familyHeadName;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;

	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;

	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	@Expose
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;

	@Expose
	@Column(name = "VehicalNo")
	private String vehicalNo;

	@Expose
	@Column(name = "vanID")
	private Integer vanID;

	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;

	@Expose
	@Column(name = "SyncedBy")
	private String syncedBy;

	@Expose
	@Column(name = "SyncedDate")
	private Timestamp syncedDate;

	@Transient
	Integer districtId;

	@Transient
	Integer blockId;

	@Transient
	private BenFamilyMapping memberList[];

	@Transient
	Long beneficiaryRegId;

	@Transient
	private Integer headofFamily_RelationID;

	@Transient
	private String headofFamily_Relation;
	
	@Transient
	private String other;

}
