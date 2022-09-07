package com.iemr.common.identity.dto;

import java.math.BigInteger;

import lombok.Data;

@Data
public class BeneficiariesPartialDTO implements Comparable<BeneficiariesPartialDTO>
{

	private BigInteger benId;
	private BigInteger benRegId;
	private BigInteger beneficiaryDetailsId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String fatherName;
	private String spouseName;
	private Integer beneficiaryAge;

	@Override
	public int compareTo(BeneficiariesPartialDTO ben)
	{
		return this.benRegId.compareTo(ben.benRegId);
	}
}
