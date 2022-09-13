package com.iemr.common.identity.service.familyTagging;

import com.iemr.common.identity.exception.IEMRException;

public interface FamilyTagService {

	public String addTag(String request) throws IEMRException;
	public String createFamily(String request) throws IEMRException;
	public String getFamilyDetails(String request) throws IEMRException;
	public String doFamilyUntag(String request) throws IEMRException;
	public String searchFamily(String request) throws IEMRException;
}
