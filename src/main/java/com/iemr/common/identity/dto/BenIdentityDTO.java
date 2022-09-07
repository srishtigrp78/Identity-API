package com.iemr.common.identity.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class BenIdentityDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private BigInteger benIdentityId;
	private String createdBy;
	private Timestamp createdDate;
	private Boolean deleted = false;
	private Timestamp expiryDate;
	private String identityFilePath;
	private Integer identityNameId;
	private String identityName;
	private String identityNo;
	private Integer identityTypeId;
	private String identityType;
	private Timestamp issueDate;
	private Boolean isVerified;
	private Timestamp lastModDate;
	private String modifiedBy;

	private Integer vanID;
	private Integer parkingPlaceID;
}