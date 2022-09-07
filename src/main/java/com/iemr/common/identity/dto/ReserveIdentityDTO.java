package com.iemr.common.identity.dto;

import lombok.Data;

@Data
public class ReserveIdentityDTO {

	private Integer providerServiceMapID;
	private String vehicalNo;
	private Long reserveCount;
}
