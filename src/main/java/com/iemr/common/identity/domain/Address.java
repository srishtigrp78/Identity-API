package com.iemr.common.identity.domain;

import lombok.Data;

public @Data class Address {
	private String addrLine1;
	private String addrLine2;
	private String addrLine3;
	private Integer countryId;
	private String country;
	private Integer stateId;
	private String state;
	private Integer districtId;
	private String district;
	private Integer subDistrictId;
	private String subDistrict;
	private Integer villageId;
	private String village;
	private String habitation;
	private String addressValue;
	private String pinCode;
	/*
	 * New columns added for MMU integration 09-04-2018
	 */
	private Integer zoneID;
	private String zoneName;
	// private Integer parkingPlaceID;
	private String parkingPlaceName;
	private Integer servicePointID;
	private String servicePointName;

	private Integer vanID;
	private Integer parkingPlaceID;
}
