package com.iemr.common.identity.data.familyTagging;

import java.util.List;

import lombok.Data;

@Data
public class FamilySearchResponse {

	String familyId;
	String headOfTheFamily;
	String familyName;
	Integer noOfMembers;
	List<FamilyMembers> familyMembers;
}
