/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.common.identity.controller;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;
import javax.persistence.QueryTimeoutException;

import com.iemr.common.identity.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.iemr.common.identity.domain.MBeneficiarymapping;
import com.iemr.common.identity.dto.BenIdImportDTO;
import com.iemr.common.identity.dto.BeneficiariesDTO;
import com.iemr.common.identity.dto.BeneficiariesPartialDTO;
import com.iemr.common.identity.dto.BeneficiaryCreateResp;
import com.iemr.common.identity.dto.IdentityDTO;
import com.iemr.common.identity.dto.IdentityEditDTO;
import com.iemr.common.identity.dto.IdentitySearchDTO;
import com.iemr.common.identity.dto.ReserveIdentityDTO;
import com.iemr.common.identity.exception.MissingMandatoryFieldsException;
import com.iemr.common.identity.mapper.IdentityMapper;
import com.iemr.common.identity.mapper.InputMapper;
import com.iemr.common.identity.mapper.OutputMapper;
import com.iemr.common.identity.service.IdentityService;
import com.iemr.common.identity.utils.OutputResponse;
import com.iemr.common.identity.utils.exception.IEMRException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(path = "/id")
public class IdentityController {
	private Logger logger = LoggerFactory.getLogger(IdentityController.class);

	@Autowired
	IdentityService svc;

	@Autowired
	IdentityMapper mapper;

	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Get beneficiaries by advance search")
	@PostMapping(path = "/advanceSearch", headers = "Authorization")
	public @ResponseBody String getBeneficiaries(
			@ApiParam(value = "{\"firstName\":\"String\",\"genderId\":\"Integer\",\"fatherName\":\"String\","
					+ "\"currentAddress\":{\"stateId\":\"Integer\",\"districtId\":\"Integer\",\"villageId\":\"Integer\", \"blockID\":\"Integer\"},"
					+ "\"permanentAddress\":{\"stateId\":\"Integer\",\"districtId\":\"Integer\",\"villageId\":\"Integer\", \"blockID\":\"Integer\"},"
					+ "\"emergencyAddress\":{\"stateId\":\"Integer\",\"districtId\":\"Integer\",\"villageId\":\"Integer\",\"blockID\":\"Integer\"},"
					+ "\"dob\":\"Timestamp\"}") @RequestBody String searchFilter) {
		logger.info("IdentityController.getBeneficiary - start");
		String response;
		try {
			JsonElement json = new JsonParser().parse(searchFilter);
			IdentitySearchDTO searchParams = InputMapper.getInstance().gson().fromJson(json, IdentitySearchDTO.class);

			List<BeneficiariesDTO> list = svc.getBeneficiaries(searchParams);
			list.removeIf(Objects::isNull);
			Collections.sort(list);
			response = getSuccessResponseString(list, 200, "success", "getBeneficiariesByAdvanceSearch");

			logger.info("IdentityController.getBeneficiary - end");
		} catch (Exception e) {
			logger.error("error in beneficiary advance search : " + e.getLocalizedMessage());
			response = getErrorResponseString("error in beneficiary advance search : " + e.getLocalizedMessage(), 5000,
					"failure", "");
		}
		return response;
	}

	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Search beneficiary based on beneficiary registration id")
	@PostMapping(path = "/getByBenRegId", headers = "Authorization")
	public @ResponseBody String getBeneficiariesByBeneficiaryRegId(
			@ApiParam(value = "\"Integer\"") @RequestParam String benRegId) {
		logger.info("IdentityController.getBeneficiary - start. benId = " + benRegId);
		String response;
		try {
			String benRegIdValue = "";
			JsonElement json = new JsonParser().parse(benRegId);

			if (json instanceof JsonNull) {
				response = getErrorResponseString("Null/Empty Beneficiary Id.", 5000, "failure", "");
				return response;
			}

			if (json instanceof JsonPrimitive) {
				benRegIdValue = benRegId;
			} else {
				benRegIdValue = InputMapper.getInstance().gson().fromJson(json, String.class);
			}

			List<BeneficiariesDTO> list = svc.getBeneficiariesByBenRegId(new BigInteger(benRegIdValue));
			list.removeIf(Objects::isNull);
			Collections.sort(list);
			response = getSuccessResponseString(list, 200, "success", "getBeneficiariesByBeneficiaryRegId");

			logger.info("IdentityController.getBeneficiary - end");
		} catch (Exception e) {
			logger.error("error in beneficiary search by beneficiaryRegID : " + e.getLocalizedMessage());
			response = getErrorResponseString(
					"error in beneficiary search by beneficiaryRegID : " + e.getLocalizedMessage(), 5000, "failure",
					"");
		}
		return response;
	}

	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Search identity based on beneficiary registration id")
	@PostMapping(path = "/getByBenId", headers = "Authorization")
	public @ResponseBody String getBeneficiariesByBeneficiaryId(
			@ApiParam(value = "\"Integer\"") @RequestParam String benId) {
		logger.info("IdentityController.getBeneficiary - start. benId = " + benId);

		String response;
		try {
			String benIdValue = "";
			JsonElement json = new JsonParser().parse(benId);

			if (json instanceof JsonNull) {
				response = getErrorResponseString("Null/Empty Beneficiary Id.", 5000, "failure", "");
				return response;
			}

			if (json instanceof JsonPrimitive) {
				benIdValue = benId;
			} else {
				benIdValue = InputMapper.getInstance().gson().fromJson(json, String.class);
			}

			List<BeneficiariesDTO> list = svc.getBeneficiariesByBenId(new BigInteger(benIdValue));
			list.removeIf(Objects::isNull);
			Collections.sort(list);
			response = getSuccessResponseString(list, 200, "success", "getIdentityByBeneficiaryId");

			logger.info("IdentityController.getBeneficiary - end");
		} catch (Exception e) {
			logger.error("error in beneficiary search by beneficiary ID : " + e.getLocalizedMessage());
			response = getErrorResponseString(
					"error in beneficiary search by beneficiary ID : " + e.getLocalizedMessage(), 5000, "failure", "");
		}
		return response;
	}

	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Search beneficiary based on phone number")
	@PostMapping(path = "/getByPhoneNum", headers = "Authorization")
	public @ResponseBody String getBeneficiariesByPhoneNum(
			@ApiParam(value = "\"String\"") @RequestParam String phoneNum) {
		logger.info("IdentityController.getBeneficiary - start. phoneNum = " + phoneNum);
		String response;
		try {
			String phoneNumValue = "";
			JsonElement json = new JsonParser().parse(phoneNum);

			if (json instanceof JsonNull) {
				response = getErrorResponseString("Null/Empty Phone Number.", 200, "success", "");
				return response;
			}

			phoneNumValue = phoneNum;

			List<BeneficiariesDTO> list = svc.getBeneficiariesByPhoneNum(phoneNumValue);
			list.removeIf(Objects::isNull);
			Collections.sort(list);
			response = getSuccessResponseString(list, 200, "success", "getIdentityByAgent");

			logger.info("IdentityController.getBeneficiary - end");
		} catch (Exception e) {
			logger.error("error in beneficiary search by phone No : " + e.getLocalizedMessage());
			response = getErrorResponseString("error in beneficiary search by phone No : " + e.getLocalizedMessage(),
					5000, "failure", "");
		}
		return response;
	}

	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Search beneficiary based on health ID / ABHA Address")
	@PostMapping(path = "/getByAbhaAddress", headers = "Authorization")
	public @ResponseBody String searhBeneficiaryByABHAAddress(
			@ApiParam(value = "\"String\"") @RequestParam String healthID) {
		logger.info("IdentityController.getBeneficiary - start. Health ID / ABHA Address = " + healthID);
		String response;
		try {
			String healthIDValue = "";
			JsonElement json = new JsonParser().parse(healthID);

			if (json instanceof JsonNull) {
				response = getErrorResponseString("Null/Empty Health ID / ABHA Address.", 5000, "failure", "");
				return response;
			}

			healthIDValue = healthID;

			List<BeneficiariesDTO> list = svc.getBeneficiaryByHealthIDAbhaAddress(healthIDValue);
			list.removeIf(Objects::isNull);
			Collections.sort(list);
			response = getSuccessResponseString(list, 200, "success", "getIdentityByAgent");

			logger.info("IdentityController.getBeneficiary - end");
		} catch (Exception e) {
			logger.error("error in beneficiary search by Health ID / ABHA address : " + e.getLocalizedMessage());
			response = getErrorResponseString(
					"error in beneficiary search by Health ID / ABHA address : " + e.getLocalizedMessage(), 5000,
					"failure", "");
		}
		return response;
	}

	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Search beneficiary based on health ID number / ABHA ID number")
	@PostMapping(path = "/getByAbhaIdNo", headers = "Authorization")
	public @ResponseBody String searhBeneficiaryByABHAIdNo(
			@ApiParam(value = "\"String\"") @RequestParam String healthIDNo) {
		logger.info("IdentityController.getBeneficiary - start. HealthIDNo / ABHA Id No = " + healthIDNo);
		String response;
		try {
			String healthIDNoValue = "";
			JsonElement json = new JsonParser().parse(healthIDNo);

			if (json instanceof JsonNull) {
				response = getErrorResponseString("Null/Empty Health ID No / ABHA Id No.", 5000, "failure", "");
				return response;
			}

			healthIDNoValue = healthIDNo;

			List<BeneficiariesDTO> list = svc.getBeneficiaryByHealthIDNoAbhaIdNo(healthIDNoValue);
			list.removeIf(Objects::isNull);
			Collections.sort(list);
			response = getSuccessResponseString(list, 200, "success", "getIdentityByAgent");

			logger.info("IdentityController.getBeneficiary - end");
		} catch (Exception e) {
			logger.error("error in beneficiary search by Health ID No / ABHA Id No : " + e.getLocalizedMessage());
			response = getErrorResponseString(
					"error in beneficiary search by Health ID No / ABHA Id No : " + e.getLocalizedMessage(), 5000,
					"failure", "");
		}
		return response;
	}

	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Search beneficiary based on family id")
	@PostMapping(path = "/searchByFamilyId", headers = "Authorization")
	public @ResponseBody String searhBeneficiaryByFamilyId(
			@ApiParam(value = "\"String\"") @RequestParam String familyId) {
		logger.info("IdentityController.getBeneficiary - start. family id = " + familyId);
		String response;
		try {

			JsonElement json = new JsonParser().parse(familyId);

			if (json instanceof JsonNull) {
				response = getErrorResponseString("Null/Empty Family Id.", 5000, "failure", "");
				return response;
			}

			List<BeneficiariesDTO> list = svc.searhBeneficiaryByFamilyId(familyId);
			list.removeIf(Objects::isNull);
			Collections.sort(list);
			response = getSuccessResponseString(list, 200, "success", "getIdentityByAgent");

			logger.info("IdentityController.getBeneficiary - end");
		} catch (Exception e) {
			logger.error("error in beneficiary search by Family Id : " + e.getLocalizedMessage());
			response = getErrorResponseString("error in beneficiary search by Family Id  : " + e.getLocalizedMessage(),
					5000, "failure", "");
		}
		return response;
	}

	// search beneficiary by lastModDate and districtID
	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value ="Search beneficiary by villageId and last modified date-time")
	@PostMapping(path = "/searchByVillageIdAndLastModifiedDate")
	public @ResponseBody String searchBeneficiaryByVillageIdAndLastModDate(
			@ApiParam(value = "\"String\"") @RequestBody String object) {
		logger.info("IdentityController.getBeneficiary - start. search object = " + object);
		String response;
		try {

			JsonElement json = new JsonParser().parse(object);

			SearchSyncDTO search = InputMapper.getInstance().gson().fromJson(json, SearchSyncDTO.class);
			List<BeneficiariesDTO> list = svc.searchBeneficiaryByVillageIdAndLastModifyDate(search.getVillageID(), new Timestamp(search.getLastModifiedDate()));

			response = getSuccessResponseString(list, 200, "success", "getIdentityByVillageAndLastSyncTime");

			logger.info("IdentityController.getBeneficiary - end");
		} catch (Exception e) {
			logger.error("error in beneficiary search by village Ids and last sync date : " + e.getLocalizedMessage());
			response = getErrorResponseString("error in beneficiary search by village Ids and last sync date  : " + e.getLocalizedMessage(),
					5000, "failure", "");
		}
		return response;
	}

	// search beneficiary by lastModDate and districtID
	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value ="Get count of beneficiary by villageId and last modified date-time")
	@PostMapping(path = "/countBenByVillageIdAndLastModifiedDate")
	public @ResponseBody String countBeneficiaryByVillageIdAndLastModDate(
			@ApiParam(value = "\"String\"") @RequestBody String object) {
		logger.info("IdentityController.getBeneficiaryCount- start. search object = " + object);
		String response;
		try {

			JsonElement json = new JsonParser().parse(object);

			SearchSyncDTO search = InputMapper.getInstance().gson().fromJson(json, SearchSyncDTO.class);
			Long beneficiaryCount = svc.countBeneficiaryByVillageIdAndLastModifyDate(search.getVillageID(), new Timestamp(search.getLastModifiedDate()));

			response = getSuccessResponseString(String.valueOf(beneficiaryCount), 200, "success", "getIdentityCountByVillageAndLastSyncTime");

			logger.info("IdentityController.getBeneficiaryCount - end");
		} catch (Exception e) {
			logger.error("error in getting beneficiary count by village Ids and last sync date : " + e.getLocalizedMessage());
			response = getErrorResponseString("error in getting beneficiary count by village Ids and last sync date  : " + e.getLocalizedMessage(),
					5000, "failure", "");
		}
		return response;
	}

	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Search beneficiary based on government identity number")
	@PostMapping(path = "/searhByGovIdentity", headers = "Authorization")
	public @ResponseBody String searhBeneficiaryByGovIdentity(
			@ApiParam(value = "\"String\"") @RequestParam String identity) {
		logger.info("IdentityController.getBeneficiary - start. Gov Identity = " + identity);
		String response;
		try {

			JsonElement json = new JsonParser().parse(identity);

			if (json instanceof JsonNull) {
				response = getErrorResponseString("Null/Empty Gov Identity No.", 5000, "failure", "");
				return response;
			}

			List<BeneficiariesDTO> list = svc.searhBeneficiaryByGovIdentity(identity);
			list.removeIf(Objects::isNull);
			Collections.sort(list);
			response = getSuccessResponseString(list, 200, "success", "getIdentityByAgent");

			logger.info("IdentityController.getBeneficiary - end");
		} catch (Exception e) {
			logger.error("error in beneficiary search by Gov Identity No : " + e.getLocalizedMessage());
			response = getErrorResponseString(
					"error in beneficiary search by GovIdentity No : " + e.getLocalizedMessage(), 5000, "failure", "");
		}
		return response;
	}

	/**
	 * 
	 * @param identityEditData
	 * @return
	 */
	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Edit identity by agent")
	@PostMapping(path = "/edit", headers = "Authorization")
	public @ResponseBody String editIdentity(@ApiParam(value = "{\r\n" + "  \"eventTypeName\": \"String\",\r\n"
			+ "  \"eventTypeDate\": \"Timestamp\",\r\n" + "  \"agentId\": \"Integer\",\r\n"
			+ "  \"agentName\": \"String\",\r\n" + "  \"agentPSMapId\": \"Integer\",\r\n"
			+ "  \"agentComment\": \"String\",\r\n" + "  \"serviceId\": \"Integer\",\r\n"
			+ "  \"serviceName\": \"String\",\r\n" + "  \"serviceProviderId\": \"Integer\",\r\n"
			+ "  \"serviceProviderName\": \"String\",\r\n" + "  \"stateId\": \"Integer\",\r\n"
			+ "  \"stateName\": \"String\",\r\n" + "  \"providerServiceMapId\": \"Integer\",\r\n"
			+ "  \"beneficiaryId\": \"Integer\",\r\n" + "  \"beneficiaryRegId\": \"Long\",\r\n"
			+ "  \"changeInSelfDetails\": \"Boolean\",\r\n" + "  \"firstName\": \"String\",\r\n"
			+ "  \"middleName\": \"String\",\r\n" + "  \"lastName\": \"String\",\r\n"
			+ "  \"fatherName\": \"String\",\r\n" + "  \"spouseName\": \"String\",\r\n"
			+ "  \"maritalStatusId\": \"Integer\",\r\n" + "  \"dob\": \"Timestamp\",\r\n"
			+ "  \"genderId\": \"Integer\",\r\n" + "  \"gender\": \"String\",\r\n" + "  \"title\": \"String\",\r\n"
			+ "  \"titleId\": \"Integer\",\r\n" + "  \"changeInAddress\": \"Boolean\",\r\n"
			+ "  \"currentAddress\": {\r\n" + "    \"addrLine1\": \"String\",\r\n"
			+ "    \"addrLine2\": \"String\",\r\n" + "    \"addrLine3\": \"String\",\r\n"
			+ "    \"countryId\": \"Integer\",\r\n" + "    \"country\": \"String\",\r\n"
			+ "    \"stateId\": \"Integer\",\r\n" + "    \"state\": \"String\",\r\n"
			+ "    \"districtId\": \"Integer\",\r\n" + "    \"district\": \"String\",\r\n"
			+ "    \"subDistrictId\": \"Integer\",\r\n" + "    \"subDistrict\": \"String\",\r\n"
			+ "    \"villageId\": \"Integer\",\r\n" + "    \"village\": \"String\",\r\n"
			+ "    \"habitation\": \"String\",\r\n" + "    \"addressValue\": \"String\",\r\n"
			+ "    \"pinCode\": \"String\",\r\n" + "    \"zoneID\": \"Integer\",\r\n"
			+ "    \"zoneName\": \"String\",\r\n" + "    \"parkingPlaceID\": \"Integer\",\r\n"
			+ "    \"parkingPlaceName\": \"String\",\r\n" + "    \"servicePointID\": \"Integer\",\r\n"
			+ "    \"servicePointName\": \"String\"\r\n" + "  },\r\n" + "  \"permanentAddress\": {\r\n"
			+ "    \"addrLine1\": \"String\",\r\n" + "    \"addrLine2\": \"String\",\r\n"
			+ "    \"addrLine3\": \"String\",\r\n" + "    \"countryId\": \"Integer\",\r\n"
			+ "    \"country\": \"String\",\r\n" + "    \"stateId\": \"Integer\",\r\n"
			+ "    \"state\": \"String\",\r\n" + "    \"districtId\": \"Integer\",\r\n"
			+ "    \"district\": \"String\",\r\n" + "    \"subDistrictId\": \"Integer\",\r\n"
			+ "    \"subDistrict\": \"String\",\r\n" + "    \"villageId\": \"Integer\",\r\n"
			+ "    \"village\": \"String\",\r\n" + "    \"habitation\": \"String\",\r\n"
			+ "    \"addressValue\": \"String\",\r\n" + "    \"pinCode\": \"String\",\r\n"
			+ "    \"zoneID\": \"Integer\",\r\n" + "    \"zoneName\": \"String\",\r\n"
			+ "    \"parkingPlaceID\": \"Integer\",\r\n" + "    \"parkingPlaceName\": \"String\",\r\n"
			+ "    \"servicePointID\": \"Integer\",\r\n" + "    \"servicePointName\": \"String\"\r\n" + "  },\r\n"
			+ "  \"emergencyAddress\": {\r\n" + "    \"addrLine1\": \"String\",\r\n"
			+ "    \"addrLine2\": \"String\",\r\n" + "    \"addrLine3\": \"String\",\r\n"
			+ "    \"countryId\": \"Integer\",\r\n" + "    \"country\": \"String\",\r\n"
			+ "    \"stateId\": \"Integer\",\r\n" + "    \"state\": \"String\",\r\n"
			+ "    \"districtId\": \"Integer\",\r\n" + "    \"district\": \"String\",\r\n"
			+ "    \"subDistrictId\": \"Integer\",\r\n" + "    \"subDistrict\": \"String\",\r\n"
			+ "    \"villageId\": \"Integer\",\r\n" + "    \"village\": \"String\",\r\n"
			+ "    \"habitation\": \"String\",\r\n" + "    \"addressValue\": \"String\",\r\n"
			+ "    \"pinCode\": \"String\",\r\n" + "    \"zoneID\": \"Integer\",\r\n"
			+ "    \"zoneName\": \"String\",\r\n" + "    \"parkingPlaceID\": \"Integer\",\r\n"
			+ "    \"parkingPlaceName\": \"String\",\r\n" + "    \"servicePointID\": \"Integer\",\r\n"
			+ "    \"servicePointName\": \"String\"\r\n" + "  },\r\n"
			+ "  \"isPermAddrSameAsCurrAddr\": \"Boolean\",\r\n" + "  \"isPermAddrSameAsEmerAddr\": \"Boolean\",\r\n"
			+ "  \"isEmerAddrSameAsCurrAddr\": \"Boolean\",\r\n" + "  \"isEmerAddrSameAsPermAddr\": \"Boolean\",\r\n"
			+ "  \"addressType\": \"String\",\r\n" + "  \"changeInContacts\": \"Boolean\",\r\n"
			+ "  \"preferredPhoneNum\": \"String\",\r\n" + "  \"preferredPhoneTyp\": \"String\",\r\n"
			+ "  \"preferredSMSPhoneNum\": \"String\",\r\n" + "  \"preferredSMSPhoneTyp\": \"String\",\r\n"
			+ "  \"emergencyContactNum\": \"String\",\r\n" + "  \"emergencyContactTyp\": \"String\",\r\n"
			+ "  \"preferredEmailId\": \"String\",\r\n" + "  \"contact\": {\r\n"
			+ "    \"preferredPhoneNum\": \"String\",\r\n" + "    \"preferredPhoneTyp\": \"String\",\r\n"
			+ "    \"preferredSMSPhoneNum\": \"String\",\r\n" + "    \"preferredSMSPhoneTyp\": \"String\",\r\n"
			+ "    \"emergencyContactNum\": \"String\",\r\n" + "    \"emergencyContactTyp\": \"String\",\r\n"
			+ "    \"phoneNum1\": \"String\",\r\n" + "    \"phoneTyp1\": \"String\",\r\n"
			+ "    \"phoneNum2\": \"String\",\r\n" + "    \"phoneTyp2\": \"String\",\r\n"
			+ "    \"phoneNum3\": \"String\",\r\n" + "    \"phoneTyp3\": \"String\",\r\n"
			+ "    \"phoneNum4\": \"String\",\r\n" + "    \"phoneTyp4\": \"String\",\r\n"
			+ "    \"phoneNum5\": \"String\",\r\n" + "    \"phoneTyp5\": \"String\"\r\n" + "  },\r\n"
			+ "  \"changeInIdentities\": \"Boolean\",\r\n" + "  \"identities\": [\r\n" + "    {\r\n"
			+ "      \"identityNo\": \"String\",\r\n" + "      \"identityName\": \"String\",\r\n"
			+ "      \"identityNameId\": \"Integer\",\r\n" + "      \"identityType\": \"String\",\r\n"
			+ "      \"identityTypeId\": \"Integer\",\r\n" + "      \"issueDate\": \"Timestamp\",\r\n"
			+ "      \"expiryDate\": \"Timestamp\",\r\n" + "      \"isVerified\": \"Boolean\",\r\n"
			+ "      \"identityFilePath\": \"String\",\r\n" + "      \"createdBy\": \"String\",\r\n"
			+ "      \"benIdentityId\": \"BigInteger\",\r\n" + "      \"deleted\": \"Boolean\"\r\n" + "    }\r\n"
			+ "  ],\r\n" + "  \"changeInOtherDetails\": \"Boolean\",\r\n" + "  \"preferredLanguage\": \"String\",\r\n"
			+ "  \"preferredLanguageId\": \"Integer\",\r\n" + "  \"community\": \"String\",\r\n"
			+ "  \"communityId\": \"Integer\",\r\n" + "  \"education\": \"String\",\r\n"
			+ "  \"educationId\": \"Integer\",\r\n" + "  \"incomeStatus\": \"String\",\r\n"
			+ "  \"incomeStatusId\": \"Integer\",\r\n" + "  \"occupation\": \"String\",\r\n"
			+ "  \"occupationId\": \"Integer\",\r\n" + "  \"religion\": \"String\",\r\n"
			+ "  \"religionId\": \"Integer\",\r\n" + "  \"placeOfWork\": \"String\",\r\n"
			+ "  \"changeInFamilyDetails\": \"Boolean\",\r\n" + "  \"isEmergencyContact\": \"Boolean\",\r\n"
			+ "  \"relationshipToSelf\": \"String\",\r\n" + "  \"relationshipID\": \"Integer\",\r\n"
			+ "  \"associatedBenRegId\": \"BigInteger\",\r\n" + "  \"benFamilyDTOs\": [\r\n" + "    {\r\n"
			+ "      \"benFamilyMapId\": \"BigInteger\",\r\n" + "      \"associatedBenRegId\": \"BigInteger\",\r\n"
			+ "      \"createdBy\": \"String\",\r\n" + "      \"createdDate\": \"String\",\r\n"
			+ "      \"deleted\": \"Boolean\",\r\n" + "      \"isEmergencyContact\": \"Boolean\",\r\n"
			+ "      \"lastModDate\": \"Timestamp\",\r\n" + "      \"modifiedBy\": \"String\",\r\n"
			+ "      \"relationshipToSelf\": \"String\",\r\n" + "      \"relationshipID\": \"Integer\",\r\n"
			+ "      \"vanID\": \"Integer\",\r\n" + "      \"parkingPlaceID\": \"Integer\"\r\n" + "    }\r\n"
			+ "  ],\r\n" + "  \"changeInAssociations\": \"Boolean\",\r\n" + "  \"zoneId\": \"Integer\",\r\n"
			+ "  \"areaId\": \"Integer\",\r\n" + "  \"servicePointId\": \"Integer\",\r\n"
			+ "  \"phcId\": \"Integer\",\r\n" + "  \"remarks\": \"String\",\r\n" + "  \"sourceOfInfo\": \"String\",\r\n"
			+ "  \"status\": \"String\",\r\n" + "  \"statusId\": \"Integer\",\r\n"
			+ "  \"healthCareWorkerType\": \"String\",\r\n" + "  \"healthCareWorkerId\": \"Short\",\r\n"
			+ "  \"changeInBankDetails\": \"Boolean\",\r\n" + "  \"bankName\": \"String\",\r\n"
			+ "  \"branchName\": \"String\",\r\n" + "  \"ifscCode\": \"String\",\r\n"
			+ "  \"accountNo\": \"String\",\r\n" + "  \"benAccountID\": \"Long\",\r\n"
			+ "  \"changeInBenImage\": \"Boolean\",\r\n" + "  \"benImage\": \"String\",\r\n"
			+ "  \"benImageId\": \"Long\",\r\n" + "  \"motherName\": \"String\",\r\n"
			+ "  \"maritalStatus\": \"String\",\r\n" + "  \"marriageDate\": \"Timestamp\",\r\n"
			+ "  \"ageAtMarriage\": \"Integer\",\r\n" + "  \"literacyStatus\": \"String\",\r\n"
			+ "  \"isHIVPositive\": \"String\",\r\n" + "  \"sourceOfInformation\": \"String\",\r\n"
			+ "  \"sexualOrientationID\": \"Integer\",\r\n" + "  \"sexualOrientationType\": \"String\",\r\n"
			+ "  \"vanID\": \"Integer\",\r\n" + "  \"parkingPlaceId\": \"Integer\"\r\n"
			+ "}") @RequestBody String identityEditData) {
		logger.info("IdentityController.editIdentity - start");
		JsonElement json = new JsonParser().parse(identityEditData);

		if (json instanceof JsonNull || json instanceof JsonPrimitive) {
			String response = getErrorResponseString("Null/Empty Identity Edit Data.", 200, "success", "");
			return response;
		}

		IdentityEditDTO identity = InputMapper.getInstance().gson().fromJson(json, IdentityEditDTO.class);
		try {
			svc.editIdentity(identity);
			String response = getSuccessResponseString("Updated successfully", 200, "success", "editIdentityByAgent");
			logger.info("IdentityController.editIdentity - end " + response);
			return response;
		} catch (MissingMandatoryFieldsException e) {
			e.printStackTrace();
			String response = getErrorResponseString(e.getMessage(), 200, "success", "editIdentityByAgent");
			logger.info("IdentityController.editIdentity - end " + response);
			return response;
		}
	}

	/**
	 * 
	 * @param identityData
	 * @return
	 */
	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Create identity by agent")
	@PostMapping(path = "/create", headers = "Authorization")
	public @ResponseBody String createIdentity(@ApiParam(value = "{\r\n" + "  \"eventTypeName\": \"String\",\r\n"
			+ "  \"eventTypeDate\": \"Timestamp\",\r\n" + "  \"agentId\": \"Integer\",\r\n"
			+ "  \"agentName\": \"String\",\r\n" + "  \"agentPSMapId\": \"Integer\",\r\n"
			+ "  \"agentComment\": \"String\",\r\n" + "  \"serviceId\": \"Integer\",\r\n"
			+ "  \"serviceName\": \"String\",\r\n" + "  \"serviceProviderId\": \"Integer\",\r\n"
			+ "  \"serviceProviderName\": \"String\",\r\n" + "  \"stateId\": \"Integer\",\r\n"
			+ "  \"stateName\": \"String\",\r\n" + "  \"providerServiceMapId\": \"Integer\",\r\n"
			+ "  \"areaId\": \"Integer\",\r\n" + "  \"beneficiaryId\": \"Long\",\r\n"
			+ "  \"beneficiaryRegId\": \"Long\",\r\n" + "  \"communityId\": \"Integer\",\r\n"
			+ "  \"community\": \"String\",\r\n" + "  \"dob\": \"Timestamp\",\r\n"
			+ "  \"ageAtMarriage\": \"Integer\",\r\n" + "  \"educationId\": \"Integer\",\r\n"
			+ "  \"education\": \"String\",\r\n" + "  \"emergencyRegistration\": \"Boolean\",\r\n"
			+ "  \"healthCareWorkerId\": \"Short\",\r\n" + "  \"healthCareWorker\": \"Integer\",\r\n"
			+ "  \"fatherName\": \"String\",\r\n" + "  \"firstName\": \"String\",\r\n"
			+ "  \"genderId\": \"Integer\",\r\n" + "  \"gender\": \"String\",\r\n"
			+ "  \"incomeStatusId\": \"Integer\",\r\n" + "  \"incomeStatus\": \"String\",\r\n"
			+ "  \"lastName\": \"String\",\r\n" + "  \"maritalStatusId\": \"Integer\",\r\n"
			+ "  \"middleName\": \"String\",\r\n" + "  \"occupationId\": \"Integer\",\r\n"
			+ "  \"occupation\": \"String\",\r\n" + "  \"phcId\": \"Integer\",\r\n"
			+ "  \"parkingPlaceId\": \"Integer\",\r\n" + "  \"placeOfWork\": \"String\",\r\n"
			+ "  \"preferredLanguageId\": \"Integer\",\r\n" + "  \"preferredLanguage\": \"String\",\r\n"
			+ "  \"religionId\": \"Integer\",\r\n" + "  \"religion\": \"String\",\r\n"
			+ "  \"remarks\": \"String\",\r\n" + "  \"servicePointId\": \"Integer\",\r\n"
			+ "  \"sourceOfInfo\": \"String\",\r\n" + "  \"spouseName\": \"String\",\r\n"
			+ "  \"statusId\": \"Integer\",\r\n" + "  \"status\": \"String\",\r\n" + "  \"titleId\": \"Integer\",\r\n"
			+ "  \"title\": \"String\",\r\n" + "  \"zoneId\": \"Integer\",\r\n" + "  \"currentAddress\": {\r\n"
			+ "    \"addrLine1\": \"String\",\r\n" + "    \"addrLine2\": \"String\",\r\n"
			+ "    \"addrLine3\": \"String\",\r\n" + "    \"countryId\": \"Integer\",\r\n"
			+ "    \"country\": \"String\",\r\n" + "    \"stateId\": \"Integer\",\r\n"
			+ "    \"state\": \"String\",\r\n" + "    \"districtId\": \"Integer\",\r\n"
			+ "    \"district\": \"String\",\r\n" + "    \"subDistrictId\": \"Integer\",\r\n"
			+ "    \"subDistrict\": \"String\",\r\n" + "    \"villageId\": \"Integer\",\r\n"
			+ "    \"village\": \"String\",\r\n" + "    \"habitation\": \"String\",\r\n"
			+ "    \"addressValue\": \"String\",\r\n" + "    \"pinCode\": \"String\",\r\n"
			+ "    \"zoneID\": \"Integer\",\r\n" + "    \"zoneName\": \"String\",\r\n"
			+ "    \"parkingPlaceID\": \"Integer\",\r\n" + "    \"parkingPlaceName\": \"String\",\r\n"
			+ "    \"servicePointID\": \"Integer\",\r\n" + "    \"servicePointName\": \"String\"\r\n" + "  },\r\n"
			+ "  \"permanentAddress\": {\r\n" + "    \"addrLine1\": \"String\",\r\n"
			+ "    \"addrLine2\": \"String\",\r\n" + "    \"addrLine3\": \"String\",\r\n"
			+ "    \"countryId\": \"Integer\",\r\n" + "    \"country\": \"Integer\",\r\n"
			+ "    \"stateId\": \"Integer\",\r\n" + "    \"state\": \"String\",\r\n"
			+ "    \"districtId\": \"Integer\",\r\n" + "    \"district\": \"String\",\r\n"
			+ "    \"subDistrictId\": \"Integer\",\r\n" + "    \"subDistrict\": \"String\",\r\n"
			+ "    \"villageId\": \"Integer\",\r\n" + "    \"village\": \"String\",\r\n"
			+ "    \"habitation\": \"String\",\r\n" + "    \"addressValue\": \"String\",\r\n"
			+ "    \"pinCode\": \"String\",\r\n" + "    \"zoneID\": \"Integer\",\r\n"
			+ "    \"zoneName\": \"String\",\r\n" + "    \"parkingPlaceID\": \"Integer\",\r\n"
			+ "    \"parkingPlaceName\": \"String\",\r\n" + "    \"servicePointID\": \"Integer\",\r\n"
			+ "    \"servicePointName\": \"String\"\r\n" + "  },\r\n" + "  \"emergencyAddress\": {\r\n"
			+ "    \"addrLine1\": \"String\",\r\n" + "    \"addrLine2\": \"String\",\r\n"
			+ "    \"addrLine3\": \"String\",\r\n" + "    \"countryId\": \"Integer\",\r\n"
			+ "    \"country\": \"India\",\r\n" + "    \"stateId\": \"Integer\",\r\n" + "    \"state\": \"String\",\r\n"
			+ "    \"districtId\": \"Integer\",\r\n" + "    \"district\": \"String\",\r\n"
			+ "    \"subDistrictId\": \"Integer\",\r\n" + "    \"subDistrict\": \"String\",\r\n"
			+ "    \"villageId\": \"Integer\",\r\n" + "    \"village\": \"String\",\r\n"
			+ "    \"habitation\": \"String\",\r\n" + "    \"addressValue\": \"String\",\r\n"
			+ "    \"pinCode\": \"String\",\r\n" + "    \"zoneID\": \"Integer\",\r\n"
			+ "    \"zoneName\": \"String\",\r\n" + "    \"parkingPlaceID\": \"Integer\",\r\n"
			+ "    \"parkingPlaceName\": \"String\",\r\n" + "    \"servicePointID\": \"Integer\",\r\n"
			+ "    \"servicePointName\": \"String\"\r\n" + "  },\r\n"
			+ "  \"isPermAddrSameAsCurrAddr\": \"Boolean\",\r\n" + "  \"isPermAddrSameAsEmerAddr\": \"Boolean\",\r\n"
			+ "  \"isEmerAddrSameAsCurrAddr\": \"Boolean\",\r\n" + "  \"isEmerAddrSameAsPermAddr\": \"Boolean\",\r\n"
			+ "  \"addressType\": \"String\",\r\n" + "  \"preferredEmailId\": \"String\",\r\n" + "  \"contact\": {\r\n"
			+ "    \"preferredPhoneNum\": \"String\",\r\n" + "    \"preferredPhoneTyp\": \"String\",\r\n"
			+ "    \"preferredSMSPhoneNum\": \"String\",\r\n" + "    \"preferredSMSPhoneTyp\": \"String\",\r\n"
			+ "    \"emergencyContactNum\": \"String\",\r\n" + "    \"emergencyContactTyp\": \"String\",\r\n"
			+ "    \"phoneNum1\": \"String\",\r\n" + "    \"phoneTyp1\": \"String\",\r\n"
			+ "    \"phoneNum2\": \"String\",\r\n" + "    \"phoneTyp2\": \"String\",\r\n"
			+ "    \"phoneNum3\": \"String\",\r\n" + "    \"phoneTyp3\": \"String\",\r\n"
			+ "    \"phoneNum4\": \"String\",\r\n" + "    \"phoneTyp4\": \"String\",\r\n"
			+ "    \"phoneNum5\": \"String\",\r\n" + "    \"phoneTyp5\": \"String\"\r\n" + "  },\r\n"
			+ "  \"benFamilyDTOs\": [\r\n" + "    {\r\n" + "      \"benFamilyMapId\": \"BigInteger\",\r\n"
			+ "      \"associatedBenRegId\": \"BigInteger\",\r\n" + "      \"createdBy\": \"String\",\r\n"
			+ "      \"createdDate\": \"String\",\r\n" + "      \"deleted\": \"Boolean\",\r\n"
			+ "      \"isEmergencyContact\": \"Boolean\",\r\n" + "      \"lastModDate\": \"Timestamp\",\r\n"
			+ "      \"modifiedBy\": \"String\",\r\n" + "      \"relationshipToSelf\": \"String\",\r\n"
			+ "      \"relationshipID\": \"Integer\",\r\n" + "      \"vanID\": \"Integer\",\r\n"
			+ "      \"parkingPlaceID\": \"Integer\"\r\n" + "    }\r\n" + "  ],\r\n" + "  \"identities\": [\r\n"
			+ "    {\r\n" + "      \"identityNo\": \"String\",\r\n" + "      \"identityName\": \"String\",\r\n"
			+ "      \"identityNameId\": \"Integer\",\r\n" + "      \"identityType\": \"String\",\r\n"
			+ "      \"identityTypeId\": \"Integer\",\r\n" + "      \"issueDate\": \"Timestamp\",\r\n"
			+ "      \"expiryDate\": \"Timestamp\",\r\n" + "      \"isVerified\": \"Boolean\",\r\n"
			+ "      \"identityFilePath\": \"String\",\r\n" + "      \"createdBy\": \"String\",\r\n"
			+ "      \"benIdentityId\": \"BigInteger\",\r\n" + "      \"deleted\": \"Boolean\"\r\n" + "    }\r\n"
			+ "  ],\r\n" + "  \"bankName\": \"String\",\r\n" + "  \"branchName\": \"String\",\r\n"
			+ "  \"ifscCode\": \"String\",\r\n" + "  \"accountNo\": \"String\",\r\n" + "  \"benImage\": \"String\",\r\n"
			+ "  \"motherName\": \"String\",\r\n" + "  \"maritalStatus\": \"String\",\r\n"
			+ "  \"marriageDate\": \"Timestamp\",\r\n" + "  \"literacyStatus\": \"String\",\r\n"
			+ "  \"isHIVPositive\": \"String\",\r\n" + "  \"sexualOrientationID\": \"Integer\",\r\n"
			+ "  \"sexualOrientationType\": \"String\",\r\n" + "  \"vanID\": \"Integer\",\r\n"
			+ "  \"createdDate\": \"Timestamp\"\r\n" + "  \"faceEmbedding\": [\"Float\"]\r\n" + "}") @RequestBody String identityData) {
		logger.info("IdentityController.createIdentity - start");
		JsonElement json = new JsonParser().parse(identityData);

		if (json instanceof JsonNull || json instanceof JsonPrimitive) {
			String response = getErrorResponseString("Null/Empty Identity Create Data.", 200, "success", "");
			return response;
		}

		IdentityDTO identity = InputMapper.getInstance().gson().fromJson(json, IdentityDTO.class);
		logger.info("identity hit: " + identity);
		BeneficiaryCreateResp map;
		try {
			map = svc.createIdentity(identity);
		} catch (IEMRException e) {
			logger.info("IEMRException: " + e.getMessage());
			return getErrorResponseString(e.getMessage(), 5000, "error", "");

		}
		String data = InputMapper.getInstance().gson().toJson(map).toString();
		String response = getSuccessResponseString(data, 200, "success", "createIdentityByAgent");

		logger.info("IdentityController.createIdentity - end " + response);
		return response;
	}

	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Reserve identity by agent")
	@PostMapping(path = "/reserve", headers = "Authorization")
	public @ResponseBody String reserveIdentity(@RequestBody String reserveIdentity) {
		logger.info("IdentityController.reserveIdentity - start");

		JsonElement json = new JsonParser().parse(reserveIdentity);

		if (json instanceof JsonNull || json instanceof JsonPrimitive) {
			String response = getErrorResponseString("Null/Empty Identity Create Data.", 200, "success", "");
			return response;
		}

		ReserveIdentityDTO reserveIdentityDTO = InputMapper.getInstance().gson().fromJson(json,
				ReserveIdentityDTO.class);
		String data = svc.reserveIdentity(reserveIdentityDTO);
		String response = getSuccessResponseString(data, 200, "success", "createIdentityByAgent");
		logger.info("IdentityController.reserveIdentity - end");
		return response;
	}

	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Unreserve identity by agent")
	@PostMapping(path = "/unreserve", headers = "Authorization")
	public @ResponseBody String unreserveIdentity(@RequestBody String unreserve) {
		logger.info("IdentityController.unreserveIdentity - start");

		JsonElement json = new JsonParser().parse(unreserve);

		if (json instanceof JsonNull || json instanceof JsonPrimitive) {
			String response = getErrorResponseString("Null/Empty Identity Create Data.", 200, "success", "");
			return response;
		}

		ReserveIdentityDTO unreserveDTO = InputMapper.getInstance().gson().fromJson(json, ReserveIdentityDTO.class);
		String data = svc.unReserveIdentity(unreserveDTO);
		String response = getSuccessResponseString(data, 200, "success", "createIdentityByAgent");

		logger.info("IdentityController.unreserveIdentity - end");
		return response;
	}

	/**
	 * get partial list ben name and last name and ben id
	 * 
	 * @param benRegIds
	 * @return
	 */
	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Get beneficiaries partial details by beneficiary registration id list")
	@PostMapping(path = "/getByPartialBenRegIdList", headers = "Authorization")
	public @ResponseBody String getPartialBeneficiariesByBenRegIds(
			@ApiParam(value = "[Integer,Integer…..(array of benRegId)]") @RequestBody String benRegIds) {
		logger.info("IdentityController.getBeneficiariesByBenRegIds - start. benRegIdList = " + benRegIds);
		BigInteger[] benRegIdarray = null;
		JsonElement json = new JsonParser().parse(benRegIds);

		if (json instanceof JsonNull) {
			String response = getErrorResponseString("Null/Empty Phone Number.", 200, "success", "");
			return response;
		}

		benRegIdarray = InputMapper.getInstance().gson().fromJson(json, BigInteger[].class);

		List<BeneficiariesPartialDTO> list = svc
				.getBeneficiariesPartialDeatilsByBenRegIdList(Arrays.asList(benRegIdarray));
		list.removeIf(Objects::isNull);
		Collections.sort(list);
		String data = InputMapper.getInstance().gson().toJson(list).toString();
		String response = getSuccessResponseString(data, 200, "success", "getBeneficiariesByBenRegIds");

		logger.info("IdentityController.getBeneficiariesByBenRegIds - end ");
		return response;
	}

	/**
	 * get partial list ben name and last name and ben id
	 * 
	 * @param benRegIds
	 * @return
	 */
	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Get beneficiaries by beneficiary registration id")
	@PostMapping(path = "/getByBenRegIdList", headers = "Authorization")
	public @ResponseBody String getBeneficiariesByBenRegIds(
			@ApiParam(value = " {\"beneficiaryRegID\": \"Long\"}") @RequestBody String benRegIds) {
		logger.info("IdentityController.getBeneficiariesByBenRegIds - start. benRegIdList = " + benRegIds);
		BigInteger[] benRegIdarray = null;
		JsonElement json = new JsonParser().parse(benRegIds);

		if (json instanceof JsonNull) {
			String response = getErrorResponseString("Null/Empty Phone Number.", 200, "success", "");
			return response;
		}

		benRegIdarray = InputMapper.getInstance().gson().fromJson(json, BigInteger[].class);

		List<BeneficiariesDTO> list = svc.getBeneficiariesDeatilsByBenRegIdList(Arrays.asList(benRegIdarray));
		list.removeIf(Objects::isNull);
		Collections.sort(list);
		String response = getSuccessResponseString(list, 200, "success", "getBeneficiariesByBenRegIds");

		logger.info("IdentityController.getBeneficiariesByBenRegIds - end : ");
		return response;
	}

	/**
	 * 
	 * @param map
	 * @param statusCode
	 * @param statusMsg
	 * @param methodName
	 * @return
	 */
	private String getSuccessResponseString(MBeneficiarymapping map, Integer statusCode, String statusMsg,
			String methodName) {
		logger.info("IdentityController.getResponseString of map parameter - start");
		BeneficiariesDTO bdto = mapper.mBeneficiarymappingToBeneficiariesDTO(map);
		bdto.setBeneficiaryFamilyTags(
				mapper.mBeneficiaryfamilymappingListToBenFamilyDTOList(map.getMBeneficiaryfamilymappings()));
		bdto.setBeneficiaryIdentites(
				mapper.mBeneficiaryidentityListToBenIdentityDTOList(map.getMBeneficiaryidentities()));
		bdto.setBeneficiaryServiceMap(
				mapper.mBeneficiaryservicemappingListToBenServiceDTOList(map.getMBeneficiaryservicemappings()));

		String data = InputMapper.getInstance().gson().toJson(bdto).toString();
		logger.info("data: " + data);

		OutputResponse response = new OutputResponse.Builder().setDataJsonType("JsonObject.class")
				.setStatusCode(statusCode).setStatusMessage(statusMsg)
				.setDataObjectType(this.getClass().getSimpleName()).setMethodName(methodName).setData(data).build();

		logger.info("IdentityController.getResponseString of map parameter - end");

		return response.toString();
	}

	/**
	 * Overloaded method with string
	 * 
	 * @param data
	 * @param statusCode
	 * @param statusMsg
	 * @param methodName
	 * @return
	 */
	private String getSuccessResponseString(String data, Integer statusCode, String statusMsg, String methodName) {
		logger.info("IdentityController.getResponseString of string parameter - start");


		OutputResponse response = new OutputResponse.Builder().setDataJsonType("JsonObject.class")
				.setStatusCode(statusCode).setStatusMessage(statusMsg)
				.setDataObjectType(this.getClass().getSimpleName()).setMethodName(methodName).setData(data).build();

		logger.info("IdentityController.getResponseString of string paramete - end");

		return response.toString();
	}

	private String getSuccessResponseString(List<BeneficiariesDTO> list, Integer statusCode, String statusMsg,
			String methodName) {
		Type typeOfSrc = new TypeToken<List<BeneficiariesDTO>>() {
			private static final long serialVersionUID = 1L;
		}.getType();
		String data = OutputMapper.getInstance().gson().toJson(list, typeOfSrc);
		logger.info("data response size:" + (list != null ? list.size() : "No Beneficiary Found"));
		OutputResponse response = new OutputResponse.Builder().setDataJsonType("JsonObject.class")
				.setStatusCode(statusCode).setStatusMessage(statusMsg)
				.setDataObjectType(this.getClass().getSimpleName()).setMethodName(methodName).setData(data).build();
		return response.toString();
	}

	/**
	 * 
	 * @param map
	 * @param errorMsg
	 * @param statusCode
	 * @param statusMsg
	 * @param methodName
	 * @return
	 */
	private String getErrorResponseString(MBeneficiarymapping map, String errorMsg, Integer statusCode,
			String statusMsg, String methodName) {
		String data = InputMapper.getInstance().gson().toJson(errorMsg).toString();
		logger.info("data: " + data);
		OutputResponse response = new OutputResponse.Builder().setDataJsonType("JsonObject.class")
				.setStatusCode(statusCode).setStatusMessage(statusMsg)
				.setDataObjectType(this.getClass().getSimpleName()).setMethodName(methodName).setData(data).build();

		logger.info("IdentityController.getResponseString - end");

		return response.toString();
	}

	private String getErrorResponseString(String errorMsg, Integer statusCode, String statusMsg, String methodName) {
		JsonObject dta = new JsonObject();
		dta.addProperty("error", errorMsg);
		String data = OutputMapper.getInstance().gson().toJson(errorMsg).toString();
		logger.info("data: " + data);

		OutputResponse response = new OutputResponse.Builder().setDataJsonType("JsonObject.class")
				.setStatusCode(statusCode).setStatusMessage(statusMsg)
				.setDataObjectType(this.getClass().getSimpleName()).setMethodName(methodName).setData(data).build();

		logger.info("IdentityController.getResponseString - end");

		return response.toString();
	}

	public String getJsonAsString(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(mapper.writeValueAsString(obj));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}

		return sb.toString();
	}

	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Get finite beneficiaries")
	@PostMapping(path = "/finiteSearch", headers = "Authorization")
	public @ResponseBody String getFiniteBeneficiaries(@RequestBody String searchFilter) {
		logger.info("IdentityController.getFiniteBeneficiaries - start");

		String response;

		try {
			List<BeneficiariesDTO> list;
			JsonElement json = new JsonParser().parse(searchFilter);
			IdentitySearchDTO searchParams = InputMapper.getInstance().gson().fromJson(json, IdentitySearchDTO.class);

			list = svc.getBeneficiaries(searchParams);
			response = getSuccessResponseString(list, 200, "success", "getFiniteBeneficiaries");
			logger.info("IdentityController.getFiniteBeneficiaries - end");
		} catch (NoResultException e) {
			logger.error(e.getMessage());
			response = getErrorResponseString(e.getLocalizedMessage(), 5000, "failure", "");
		} catch (QueryTimeoutException e) {
			logger.error(e.getMessage());
			response = getErrorResponseString(e.getLocalizedMessage(), 5000, "failure", "");
		} catch (Exception e) {
			logger.error(e.getMessage());
			response = getErrorResponseString(e.getLocalizedMessage(), 5000, "failure", "");
		}

		return response;
	}

	// New API for getting beneficiary image only.
	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Get beneficiary image by beneficiary registration id")
	@PostMapping(path = "/benImageByBenRegID", headers = "Authorization")
	public @ResponseBody String getBeneficiaryImageByBenRegID(@RequestBody String identityData) {
		String benImage = null;
		try {
			logger.info("IdentityController.createIdentity - start. Request Object = " + identityData);
			benImage = svc.getBeneficiaryImage(identityData);
		} catch (Exception e) {

		}

		return benImage;
	}

	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Edit education or community by agent")
	@PostMapping(path = "/editEducationOrCommunity", headers = "Authorization")
	public @ResponseBody String editIdentityEducationOrCommunity(@ApiParam(value = "{\r\n"
			+ "  \"eventTypeName\": \"String\",\r\n" + "  \"eventTypeDate\": \"Timestamp\",\r\n"
			+ "  \"agentId\": \"Integer\",\r\n" + "  \"agentName\": \"String\",\r\n"
			+ "  \"agentPSMapId\": \"Integer\",\r\n" + "  \"agentComment\": \"String\",\r\n"
			+ "  \"serviceId\": \"Integer\",\r\n" + "  \"serviceName\": \"String\",\r\n"
			+ "  \"serviceProviderId\": \"Integer\",\r\n" + "  \"serviceProviderName\": \"String\",\r\n"
			+ "  \"stateId\": \"Integer\",\r\n" + "  \"stateName\": \"String\",\r\n"
			+ "  \"providerServiceMapId\": \"Integer\",\r\n" + "  \"beneficiaryId\": \"Integer\",\r\n"
			+ "  \"beneficiaryRegId\": \"Long\",\r\n" + "  \"changeInSelfDetails\": \"Boolean\",\r\n"
			+ "  \"firstName\": \"String\",\r\n" + "  \"middleName\": \"String\",\r\n"
			+ "  \"lastName\": \"String\",\r\n" + "  \"fatherName\": \"String\",\r\n"
			+ "  \"spouseName\": \"String\",\r\n" + "  \"maritalStatusId\": \"Integer\",\r\n"
			+ "  \"dob\": \"Timestamp\",\r\n" + "  \"genderId\": \"Integer\",\r\n" + "  \"gender\": \"String\",\r\n"
			+ "  \"title\": \"String\",\r\n" + "  \"titleId\": \"Integer\",\r\n"
			+ "  \"changeInAddress\": \"Boolean\",\r\n" + "  \"currentAddress\": {\r\n"
			+ "    \"addrLine1\": \"String\",\r\n" + "    \"addrLine2\": \"String\",\r\n"
			+ "    \"addrLine3\": \"String\",\r\n" + "    \"countryId\": \"Integer\",\r\n"
			+ "    \"country\": \"String\",\r\n" + "    \"stateId\": \"Integer\",\r\n"
			+ "    \"state\": \"String\",\r\n" + "    \"districtId\": \"Integer\",\r\n"
			+ "    \"district\": \"String\",\r\n" + "    \"subDistrictId\": \"Integer\",\r\n"
			+ "    \"subDistrict\": \"String\",\r\n" + "    \"villageId\": \"Integer\",\r\n"
			+ "    \"village\": \"String\",\r\n" + "    \"habitation\": \"String\",\r\n"
			+ "    \"addressValue\": \"String\",\r\n" + "    \"pinCode\": \"String\",\r\n"
			+ "    \"zoneID\": \"Integer\",\r\n" + "    \"zoneName\": \"String\",\r\n"
			+ "    \"parkingPlaceID\": \"Integer\",\r\n" + "    \"parkingPlaceName\": \"String\",\r\n"
			+ "    \"servicePointID\": \"Integer\",\r\n" + "    \"servicePointName\": \"String\"\r\n" + "  },\r\n"
			+ "  \"permanentAddress\": {\r\n" + "    \"addrLine1\": \"String\",\r\n"
			+ "    \"addrLine2\": \"String\",\r\n" + "    \"addrLine3\": \"String\",\r\n"
			+ "    \"countryId\": \"Integer\",\r\n" + "    \"country\": \"String\",\r\n"
			+ "    \"stateId\": \"Integer\",\r\n" + "    \"state\": \"String\",\r\n"
			+ "    \"districtId\": \"Integer\",\r\n" + "    \"district\": \"String\",\r\n"
			+ "    \"subDistrictId\": \"Integer\",\r\n" + "    \"subDistrict\": \"String\",\r\n"
			+ "    \"villageId\": \"Integer\",\r\n" + "    \"village\": \"String\",\r\n"
			+ "    \"habitation\": \"String\",\r\n" + "    \"addressValue\": \"String\",\r\n"
			+ "    \"pinCode\": \"String\",\r\n" + "    \"zoneID\": \"Integer\",\r\n"
			+ "    \"zoneName\": \"String\",\r\n" + "    \"parkingPlaceID\": \"Integer\",\r\n"
			+ "    \"parkingPlaceName\": \"String\",\r\n" + "    \"servicePointID\": \"Integer\",\r\n"
			+ "    \"servicePointName\": \"String\"\r\n" + "  },\r\n" + "  \"emergencyAddress\": {\r\n"
			+ "    \"addrLine1\": \"String\",\r\n" + "    \"addrLine2\": \"String\",\r\n"
			+ "    \"addrLine3\": \"String\",\r\n" + "    \"countryId\": \"Integer\",\r\n"
			+ "    \"country\": \"String\",\r\n" + "    \"stateId\": \"Integer\",\r\n"
			+ "    \"state\": \"String\",\r\n" + "    \"districtId\": \"Integer\",\r\n"
			+ "    \"district\": \"String\",\r\n" + "    \"subDistrictId\": \"Integer\",\r\n"
			+ "    \"subDistrict\": \"String\",\r\n" + "    \"villageId\": \"Integer\",\r\n"
			+ "    \"village\": \"String\",\r\n" + "    \"habitation\": \"String\",\r\n"
			+ "    \"addressValue\": \"String\",\r\n" + "    \"pinCode\": \"String\",\r\n"
			+ "    \"zoneID\": \"Integer\",\r\n" + "    \"zoneName\": \"String\",\r\n"
			+ "    \"parkingPlaceID\": \"Integer\",\r\n" + "    \"parkingPlaceName\": \"String\",\r\n"
			+ "    \"servicePointID\": \"Integer\",\r\n" + "    \"servicePointName\": \"String\"\r\n" + "  },\r\n"
			+ "  \"isPermAddrSameAsCurrAddr\": \"Boolean\",\r\n" + "  \"isPermAddrSameAsEmerAddr\": \"Boolean\",\r\n"
			+ "  \"isEmerAddrSameAsCurrAddr\": \"Boolean\",\r\n" + "  \"isEmerAddrSameAsPermAddr\": \"Boolean\",\r\n"
			+ "  \"addressType\": \"String\",\r\n" + "  \"changeInContacts\": \"Boolean\",\r\n"
			+ "  \"preferredPhoneNum\": \"String\",\r\n" + "  \"preferredPhoneTyp\": \"String\",\r\n"
			+ "  \"preferredSMSPhoneNum\": \"String\",\r\n" + "  \"preferredSMSPhoneTyp\": \"String\",\r\n"
			+ "  \"emergencyContactNum\": \"String\",\r\n" + "  \"emergencyContactTyp\": \"String\",\r\n"
			+ "  \"preferredEmailId\": \"String\",\r\n" + "  \"contact\": {\r\n"
			+ "    \"preferredPhoneNum\": \"String\",\r\n" + "    \"preferredPhoneTyp\": \"String\",\r\n"
			+ "    \"preferredSMSPhoneNum\": \"String\",\r\n" + "    \"preferredSMSPhoneTyp\": \"String\",\r\n"
			+ "    \"emergencyContactNum\": \"String\",\r\n" + "    \"emergencyContactTyp\": \"String\",\r\n"
			+ "    \"phoneNum1\": \"String\",\r\n" + "    \"phoneTyp1\": \"String\",\r\n"
			+ "    \"phoneNum2\": \"String\",\r\n" + "    \"phoneTyp2\": \"String\",\r\n"
			+ "    \"phoneNum3\": \"String\",\r\n" + "    \"phoneTyp3\": \"String\",\r\n"
			+ "    \"phoneNum4\": \"String\",\r\n" + "    \"phoneTyp4\": \"String\",\r\n"
			+ "    \"phoneNum5\": \"String\",\r\n" + "    \"phoneTyp5\": \"String\"\r\n" + "  },\r\n"
			+ "  \"changeInIdentities\": \"Boolean\",\r\n" + "  \"identities\": [\r\n" + "    {\r\n"
			+ "      \"identityNo\": \"String\",\r\n" + "      \"identityName\": \"String\",\r\n"
			+ "      \"identityNameId\": \"Integer\",\r\n" + "      \"identityType\": \"String\",\r\n"
			+ "      \"identityTypeId\": \"Integer\",\r\n" + "      \"issueDate\": \"Timestamp\",\r\n"
			+ "      \"expiryDate\": \"Timestamp\",\r\n" + "      \"isVerified\": \"Boolean\",\r\n"
			+ "      \"identityFilePath\": \"String\",\r\n" + "      \"createdBy\": \"String\",\r\n"
			+ "      \"benIdentityId\": \"BigInteger\",\r\n" + "      \"deleted\": \"Boolean\"\r\n" + "    }\r\n"
			+ "  ],\r\n" + "  \"changeInOtherDetails\": \"Boolean\",\r\n" + "  \"preferredLanguage\": \"String\",\r\n"
			+ "  \"preferredLanguageId\": \"Integer\",\r\n" + "  \"community\": \"String\",\r\n"
			+ "  \"communityId\": \"Integer\",\r\n" + "  \"education\": \"String\",\r\n"
			+ "  \"educationId\": \"Integer\",\r\n" + "  \"incomeStatus\": \"String\",\r\n"
			+ "  \"incomeStatusId\": \"Integer\",\r\n" + "  \"occupation\": \"String\",\r\n"
			+ "  \"occupationId\": \"Integer\",\r\n" + "  \"religion\": \"String\",\r\n"
			+ "  \"religionId\": \"Integer\",\r\n" + "  \"placeOfWork\": \"String\",\r\n"
			+ "  \"changeInFamilyDetails\": \"Boolean\",\r\n" + "  \"isEmergencyContact\": \"Boolean\",\r\n"
			+ "  \"relationshipToSelf\": \"String\",\r\n" + "  \"relationshipID\": \"Integer\",\r\n"
			+ "  \"associatedBenRegId\": \"BigInteger\",\r\n" + "  \"benFamilyDTOs\": [\r\n" + "    {\r\n"
			+ "      \"benFamilyMapId\": \"BigInteger\",\r\n" + "      \"associatedBenRegId\": \"BigInteger\",\r\n"
			+ "      \"createdBy\": \"String\",\r\n" + "      \"createdDate\": \"String\",\r\n"
			+ "      \"deleted\": \"Boolean\",\r\n" + "      \"isEmergencyContact\": \"Boolean\",\r\n"
			+ "      \"lastModDate\": \"Timestamp\",\r\n" + "      \"modifiedBy\": \"String\",\r\n"
			+ "      \"relationshipToSelf\": \"String\",\r\n" + "      \"relationshipID\": \"Integer\",\r\n"
			+ "      \"vanID\": \"Integer\",\r\n" + "      \"parkingPlaceID\": \"Integer\"\r\n" + "    }\r\n"
			+ "  ],\r\n" + "  \"changeInAssociations\": \"Boolean\",\r\n" + "  \"zoneId\": \"Integer\",\r\n"
			+ "  \"areaId\": \"Integer\",\r\n" + "  \"servicePointId\": \"Integer\",\r\n"
			+ "  \"phcId\": \"Integer\",\r\n" + "  \"remarks\": \"String\",\r\n" + "  \"sourceOfInfo\": \"String\",\r\n"
			+ "  \"status\": \"String\",\r\n" + "  \"statusId\": \"Integer\",\r\n"
			+ "  \"healthCareWorkerType\": \"String\",\r\n" + "  \"healthCareWorkerId\": \"Short\",\r\n"
			+ "  \"changeInBankDetails\": \"Boolean\",\r\n" + "  \"bankName\": \"String\",\r\n"
			+ "  \"branchName\": \"String\",\r\n" + "  \"ifscCode\": \"String\",\r\n"
			+ "  \"accountNo\": \"String\",\r\n" + "  \"benAccountID\": \"Long\",\r\n"
			+ "  \"changeInBenImage\": \"Boolean\",\r\n" + "  \"benImage\": \"String\",\r\n"
			+ "  \"benImageId\": \"Long\",\r\n" + "  \"motherName\": \"String\",\r\n"
			+ "  \"maritalStatus\": \"String\",\r\n" + "  \"marriageDate\": \"Timestamp\",\r\n"
			+ "  \"ageAtMarriage\": \"Integer\",\r\n" + "  \"literacyStatus\": \"String\",\r\n"
			+ "  \"isHIVPositive\": \"String\",\r\n" + "  \"sourceOfInformation\": \"String\",\r\n"
			+ "  \"sexualOrientationID\": \"Integer\",\r\n" + "  \"sexualOrientationType\": \"String\",\r\n"
			+ "  \"vanID\": \"Integer\",\r\n" + "  \"parkingPlaceId\": \"Integer\"\r\n"
			+ "}") @RequestBody String identityEditData) {
		logger.info("IdentityController.editIdentity - start");
		JsonElement json = new JsonParser().parse(identityEditData);

		if (json instanceof JsonNull || json instanceof JsonPrimitive) {
			String response = getErrorResponseString("Null/Empty Identity Edit Data.", 200, "success", "");
			return response;
		}

		IdentityEditDTO identity = InputMapper.getInstance().gson().fromJson(json, IdentityEditDTO.class);
		try {
			svc.editIdentityEducationOrCommunity(identity);
			String response = getSuccessResponseString("Updated successfully", 200, "success", "editIdentityByAgent");
			logger.info("IdentityController.editIdentity - end " + response);
			return response;
		} catch (MissingMandatoryFieldsException e) {
			e.printStackTrace();
			String response = getErrorResponseString(e.getMessage(), 200, "success", "editIdentityByAgent");
			logger.info("IdentityController.editIdentity - end " + response);
			return response;
		}
	}

	@CrossOrigin()
	@ApiOperation(value = "Check available beneficary id in local server", consumes = "application/json", produces = "application/json")
	@GetMapping(path = "/checkAvailablBenIDLocalServer", headers = "Authorization")
	public @ResponseBody String checkAvailablBenIDLocalServer() {
		com.iemr.common.identity.utils.response.OutputResponse response = new com.iemr.common.identity.utils.response.OutputResponse();
		try {
			Long l = svc.checkBenIDAvailabilityLocal();
			response.setResponse(l.toString());
		} catch (Exception e) {
			logger.error("error while checking BENID availability to local server : " + e);
			response.setError(5000, "error while checking BENID availability to local server : " + e);
		}
		return response.toString();
	}

	@CrossOrigin(origins = { "*commonapi*" })
	@ApiOperation(value = "Save server generated beneficiary ID & beneficiary registration ID to local server", consumes = "application/json", produces = "application/json")
	@PostMapping(path = "/saveGeneratedBenIDToLocalServer", headers = "Authorization")
	public @ResponseBody String saveGeneratedBenIDToLocalServer(
			@ApiParam(value = "{\r\n" + "        \"vanID\": \"Integer\",\r\n"
					+ "        \"benIDRequired\": \"Integer\"\r\n" + "       }") @RequestBody String regIDList) {
		com.iemr.common.identity.utils.response.OutputResponse response = new com.iemr.common.identity.utils.response.OutputResponse();
		try {
			BenIdImportDTO[] benIdImportDTOArr = InputMapper.getInstance().gson().fromJson(regIDList,
					BenIdImportDTO[].class);

			List<BenIdImportDTO> benIdImportDTOList = Arrays.asList(benIdImportDTOArr);

			int i = svc.importBenIdToLocalServer(benIdImportDTOList);
			if (i > 0)
				response.setResponse(i + " Unique benid imported to local server");
			else {
				response.setResponse("Empty or invalid data");
				logger.error("Empty or invalid data. Data Size is : " + benIdImportDTOList.size());
			}
		} catch (Exception e) {
			logger.error("Exception in importing benID to local server : " + e);
			response.setError(5000, "error while importing benid to local server : " + e);
		}
		return response.toString();
	}

}
