package com.iemr.common.identity.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class BenServiceDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private BigInteger benServiceMapID;
	private String createdBy;
	private Timestamp createdDate;
	private Timestamp firstAvailedOn;
	private Timestamp lastModDate;
	private String modifiedBy;
	private String registeredByName;
	private Integer registeredById;
	private Timestamp registeredDate;
	private Integer providerServiceMapId;
	private Integer serviceId;
	private String serviceName;
	private Integer serviceProviderId;
	private String serviceProviderName;
	private Integer stateId;
	private String stateName;

	private Integer vanID;
	private Integer parkingPlaceID;
}