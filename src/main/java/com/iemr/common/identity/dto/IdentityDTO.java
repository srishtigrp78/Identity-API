package com.iemr.common.identity.dto;

import java.sql.Timestamp;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.iemr.common.identity.domain.Address;
import com.iemr.common.identity.domain.Contact;
import com.iemr.common.identity.domain.Identity;
// import com.iemr.common.identity.domain.Phone;

import lombok.Data;

@Data
public class IdentityDTO {

	private String eventTypeName; // created, reserved, registered, modified,
									// deleted
	private Timestamp eventTypeDate;
	private Integer agentId;
	private String agentName;
	private Integer agentPSMapId; // ProviderServiceMapId
	private String agentComment;
	private Integer serviceId;
	private String serviceName;
	private Integer serviceProviderId;
	private String serviceProviderName;
	private Integer stateId;
	private String stateName;
	private Integer providerServiceMapId;

	// details
	private Integer areaId;
	private Integer beneficiaryId;
	private Integer beneficiaryRegId;
	private Integer communityId;
	private String community;
	private Timestamp dob;
	private Integer educationId;
	private String education;
	private Boolean emergencyRegistration;
	private Integer healthCareWorkerId;
	private String healthCareWorker;
	private String fatherName;
	private String motherName;
	private String firstName;
	private Integer genderId;
	private String gender;
	private String lastName;
	private Integer maritalStatusId;
	private String maritalStatus;
	private String middleName;
	private Integer occupationId;
	private String occupation;
	private Integer phcId;
	private Integer parkingPlaceId;
	private String placeOfWork;
	private String literacyStatus;
	private Integer preferredLanguageId;
	private String preferredLanguage;
	private Integer religionId;
	private String religion;
	private String remarks;
	private Integer servicePointId;
	private String sourceOfInfo;
	private String spouseName;
	private Integer statusId;
	private String status;
	private Integer titleId;
	private String title;
	private Integer zoneId;

	// address
	private Address currentAddress;
	private Address permanentAddress;
	private Address emergencyAddress;
	private Boolean isPermAddrSameAsCurrAddr;
	private Boolean isPermAddrSameAsEmerAddr;
	private Boolean isEmerAddrSameAsCurrAddr;
	private Boolean isEmerAddrSameAsPermAddr;
	private String addressType;

	// contacts
	private String preferredEmailId;
	private Contact contact;

	// family tagging
	List<BenFamilyDTO> benFamilyDTOs;
	// private Boolean isEmergencyContact;
	// private String relationshipToSelf;
	// private BigInteger associatedBenRegId;

	// identities
	private List<Identity> identities;

	// Start 1097
	private Integer sexualOrientationID;
	private String sexualOrientationType;
	private String isHIVPositive;
	// End 1097

	/*
	 * New columns added for MMU integration 11-04-2018
	 */
	private String bankName;
	private String branchName;
	private String ifscCode;
	private String accountNo;
	private String benImage;

	private Timestamp marriageDate;
	private Integer ageAtMarriage;
	private Integer incomeStatusId;
	private String incomeStatus;
	// End

	// new column added for data sync
	// 17-06-2018
	@Expose
	private Integer vanID;

	// END OF new column added for data sync

	@Expose
	private Boolean beneficiaryConsent;

}
