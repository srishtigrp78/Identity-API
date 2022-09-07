package com.iemr.common.identity.dto;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import com.iemr.common.identity.domain.Address;
import com.iemr.common.identity.domain.Phone;

import lombok.Data;

@Data
public class BeneficiariesDTO implements Comparable<BeneficiariesDTO> {
	private BigInteger benMapId; //
	private BigInteger benId;
	private BigInteger benRegId;
	private String createdBy; //
	private Timestamp createdDate; //
	private Boolean deleted; //
	private Timestamp lastModDate; //
	private String modifiedBy; //
	private Address currentAddress; //
	private Address permanentAddress; //
	private Address emergencyAddress; //
	private String preferredPhoneNum; //
	private String preferredPhoneTyp; //
	private String preferredSMSPhoneNum; //
	private String preferredSMSPhoneTyp; //
	private String emergencyContactNum; //
	private String emergencyContactTyp; //
	private String preferredEmailId; //
	private BenDetailDTO beneficiaryDetails;
	private List<BenFamilyDTO> beneficiaryFamilyTags;
	// private List<BenFamilyDTO> benFamilyDTOs;
	private List<BenIdentityDTO> beneficiaryIdentites;
	private List<BenServiceDTO> beneficiaryServiceMap;
	private List<Phone> contacts;

// ABHA address
	List<AbhaAddressDTO> abhaDetails;

	@Override
	public int compareTo(BeneficiariesDTO ben) {
		return this.benMapId.compareTo(ben.benMapId);
	}

	// Start Outreach
	// New columns added for MMU integration 09-04-2018
	private Timestamp marriageDate;
	private Integer ageAtMarriage;
	private String literacyStatus;
	private String motherName;
	private String email;
	private String bankName;
	private String branchName;
	private String ifscCode;
	private String accountNo;
	private Long benAccountID;
	private Integer occupationId;
	private String occupation;
	private String incomeStatus;
	private BigInteger religionId;
	private String religion;
	// End Outreach

	// Start 1097
	private Integer beneficiaryAge;
	private String sourceOfInformation;
	private String isHIVPos;
	// End 1097
}
