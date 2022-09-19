package com.iemr.common.identity.service.familyTagging;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.common.identity.data.familyTagging.BenFamilyMapping;
import com.iemr.common.identity.data.familyTagging.FamilyMembers;
import com.iemr.common.identity.domain.MBeneficiarydetail;
import com.iemr.common.identity.exception.IEMRException;
import com.iemr.common.identity.repo.BenDetailRepo;
import com.iemr.common.identity.repo.BenMappingRepo;
import com.iemr.common.identity.repo.familyTag.FamilyTagRepo;
import com.iemr.common.identity.utils.mapper.InputMapper;

@Service
public class FamilyTagServiceImpl implements FamilyTagService {

	@Autowired
	private FamilyTagRepo familyTagRepo;
	@Autowired
	private BenDetailRepo benDetailRepo;
	@Autowired
	private BenMappingRepo benMappingRepo;

	@Override
	public String addTag(String request) throws IEMRException {
		try {
			BenFamilyMapping benFamilyObj = InputMapper.gson().fromJson(request, BenFamilyMapping.class);
			BigInteger benDetailsId = benMappingRepo
					.getBenDetailsId(BigInteger.valueOf(benFamilyObj.getBeneficiaryRegId()));
			if (benDetailsId != null) {
				int c = benDetailRepo.updateFamilyDetails(benFamilyObj.getFamilyId(), benDetailsId,
						benFamilyObj.getHeadofFamily_RelationID(), benFamilyObj.getHeadofFamily_Relation(),
						benFamilyObj.getOther());
			}
			benFamilyObj = familyTagRepo.searchFamilyByFamilyId(benFamilyObj.getFamilyId());
			int noOfMembers = benFamilyObj.getNoOfmembers();
			noOfMembers++;
			benFamilyObj.setNoOfmembers(noOfMembers);
			benFamilyObj = familyTagRepo.save(benFamilyObj);
			if (benFamilyObj != null && benFamilyObj.getBenFamilyTagId() != null)
				return "Family Tagging successfully completed";
			else
				throw new IEMRException("Error while tagging family to beneficiary");
		} catch (Exception e) {
			throw new IEMRException("Error while tagging family to beneficiary :" + e.getLocalizedMessage());
		}

	}

	@Override
	public String doFamilyUntag(String request) throws IEMRException {
		String response = null;
		try {
			BenFamilyMapping benFamilyObj = InputMapper.gson().fromJson(request, BenFamilyMapping.class);
			List<String> benFamilyIdList = new ArrayList<String>();
			int c = 0;
			int noOfMembers = 0;
			if (benFamilyObj != null && benFamilyObj.getMemberList() != null
					&& benFamilyObj.getMemberList().length > 0) {
				for (BenFamilyMapping obj : benFamilyObj.getMemberList()) {
					c = 0;
					noOfMembers = 0;
					if (obj.getFamilyId() != null) {
						BigInteger benDetailsId = benMappingRepo
								.getBenDetailsId(BigInteger.valueOf(obj.getBeneficiaryRegId()));
						c = benDetailRepo.untagFamily(obj.getFamilyId(),benDetailsId,obj.getModifiedBy());
						benFamilyObj = familyTagRepo.searchFamilyByFamilyId(obj.getFamilyId());
						noOfMembers = benFamilyObj.getNoOfmembers();
						if(noOfMembers >0)
						noOfMembers--;
						benFamilyObj.setNoOfmembers(noOfMembers);
						benFamilyObj.setModifiedBy(obj.getModifiedBy());
						benFamilyObj = familyTagRepo.save(benFamilyObj);
						if (c > 0)
							benFamilyIdList.add(obj.getFamilyId());
					}
				}
				if (benFamilyIdList != null && benFamilyIdList.size() > 0)
					response = benFamilyIdList.size() + " beneficiary untagged successfully";
				else
					response = "Error while untagging family";
			} else
				throw new IEMRException("Incorrect data received");
		} catch (Exception e) {
			throw new IEMRException("Error while untagging family :" + e.getLocalizedMessage());
		}
		return response;
	}

	@Override
	public String searchFamily(String request) throws IEMRException {
		try {
			BenFamilyMapping benFamilyObj = InputMapper.gson().fromJson(request, BenFamilyMapping.class);
			List<BenFamilyMapping> list = familyTagRepo.searchFamily(benFamilyObj.getFamilyName(),
					benFamilyObj.getVillageId());
			if (list != null && list.size() > 0)
				return new Gson().toJson(list);
			else
				return "No records found";
		} catch (Exception e) {
			throw new IEMRException("Error while searching family :" + e.getLocalizedMessage());
		}
	}

	@Override
	public String createFamily(String request) throws IEMRException {
		try {
			BenFamilyMapping benFamilyObj = InputMapper.gson().fromJson(request, BenFamilyMapping.class);
			benFamilyObj.setFamilyId(getFamilyId(benFamilyObj.getCreatedBy()));
			benFamilyObj.setNoOfmembers(1);
			benFamilyObj = familyTagRepo.save(benFamilyObj);
			// update family details to beneficiary
			BigInteger benDetailsId = benMappingRepo
					.getBenDetailsId(BigInteger.valueOf(benFamilyObj.getBeneficiaryRegId()));
			if (benDetailsId != null) {
				int c = benDetailRepo.updateFamilyDetails(benFamilyObj.getFamilyId(), benDetailsId,
						benFamilyObj.getHeadofFamily_RelationID(), benFamilyObj.getHeadofFamily_Relation(),
						benFamilyObj.getOther());
			}
			if (benFamilyObj != null && benFamilyObj.getBenFamilyTagId() != null)
				return new Gson().toJson(benFamilyObj);
			else
				throw new IEMRException("Error while creating family");
		} catch (Exception e) {
			throw new IEMRException("Error while creating family :" + e.getLocalizedMessage());
		}
	}

	String getFamilyId(String username) throws IEMRException {
		try {
			Integer userId = familyTagRepo.getUserId(username);
			return String.valueOf((System.currentTimeMillis())) + userId;
		} catch (Exception e) {
			throw new IEMRException("Error while generating family Id :" + e.getLocalizedMessage());
		}
	}

	@Override
	public String getFamilyDetails(String request) throws IEMRException {
		try {
			BenFamilyMapping benFamilyObj = InputMapper.gson().fromJson(request, BenFamilyMapping.class);
			List<FamilyMembers> responseList = new ArrayList<FamilyMembers>();
			if (benFamilyObj.getFamilyId() != null) {
				List<MBeneficiarydetail> list = benDetailRepo.getFamilyDetails(benFamilyObj.getFamilyId());
				String name = "";
				for (MBeneficiarydetail obj : list) {
					name = "";
					FamilyMembers famObj = new FamilyMembers();
					BigInteger benRegId = benMappingRepo.getBenRegId(obj.getBeneficiaryDetailsId());
					if (benRegId != null)
						famObj.setMemberId(benRegId.longValue());
					name = name + (obj.getTitle() != null ? obj.getTitle() : "")
							+ (obj.getFirstName() != null ? obj.getFirstName() : "")
							+ (obj.getLastName() != null ? obj.getLastName() : "");
					famObj.setMemberName(name);
					famObj.setRelationWithHead(obj.getHeadOfFamily_Relation());
					responseList.add(famObj);
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("familyMembers", responseList);
				return new Gson().toJson(map);
			} else
				throw new IEMRException("Error while fetching family member details");
		} catch (Exception e) {
			throw new IEMRException("Error while fetching family member details :" + e.getLocalizedMessage());
		}
	}
}
