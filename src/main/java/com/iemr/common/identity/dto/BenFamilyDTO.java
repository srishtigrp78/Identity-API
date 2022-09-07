package com.iemr.common.identity.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class BenFamilyDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private BigInteger benFamilyMapId;
	private BigInteger associatedBenRegId;
	private String createdBy;
	private Timestamp createdDate;
	private Boolean deleted;
	private Boolean isEmergencyContact;
	private Timestamp lastModDate;
	private String modifiedBy;
	private String relationshipToSelf;
	private Integer relationshipID;
	
	private Integer vanID;
	private Integer parkingPlaceID;
}