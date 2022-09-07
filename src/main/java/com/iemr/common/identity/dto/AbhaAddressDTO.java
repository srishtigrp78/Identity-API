package com.iemr.common.identity.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class AbhaAddressDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigInteger BeneficiaryRegID;
	@Expose
	private String HealthID;
	@Expose
	private String HealthIDNumber;
	@Expose
	private String AuthenticationMode;
	@Expose
	private Timestamp CreatedDate;

}
