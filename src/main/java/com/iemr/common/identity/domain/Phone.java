package com.iemr.common.identity.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Phone
{
	private String phoneNum;
	private String phoneType;
	private Boolean isPreferredCallIncoming;
	private Boolean isPreferredCallOutgoing;
	private Boolean isPreferredForSMSSend;
	private Boolean isPreferredForSMSRecv;
	private Boolean isSmartPhone;
	private Boolean isSelfNumber;
	private String belongsToBenRegId;
	private String belongsToName;

	public static List<Phone> createContactList(MBeneficiarycontact benContact, String belongsToBenRegId,
			String belongsToName)
	{
		List<Phone> contactsList = null;
		if (benContact.getPreferredPhoneNum() != null || benContact.getPhoneNum1() != null
				|| benContact.getPhoneNum2() != null || benContact.getPhoneNum3() != null
				|| benContact.getPhoneNum4() != null || benContact.getPhoneNum5() != null)
		{
			contactsList = new ArrayList<Phone>();
			Phone phoneMap = null;
			if (benContact.getPreferredPhoneNum() != null)
			{
				phoneMap = new Phone();
				phoneMap.setPhoneNum(benContact.getPreferredPhoneNum());
				phoneMap.setPhoneType(benContact.getPreferredPhoneTyp());
				phoneMap.setBelongsToBenRegId(belongsToBenRegId);
				phoneMap.setBelongsToName(belongsToName);
				contactsList.add(phoneMap);
			}
			if (benContact.getPhoneNum1() != null)
			{
				phoneMap = new Phone();
				phoneMap.setPhoneNum(benContact.getPhoneNum1());
				phoneMap.setPhoneType(benContact.getPhoneTyp1());
				phoneMap.setBelongsToBenRegId(belongsToBenRegId);
				phoneMap.setBelongsToName(belongsToName);
				contactsList.add(phoneMap);
			}
			if (benContact.getPhoneNum2() != null)
			{
				phoneMap = new Phone();
				phoneMap.setPhoneNum(benContact.getPhoneNum2());
				phoneMap.setPhoneType(benContact.getPhoneTyp2());
				phoneMap.setBelongsToBenRegId(belongsToBenRegId);
				phoneMap.setBelongsToName(belongsToName);
				contactsList.add(phoneMap);
			}
			if (benContact.getPhoneNum3() != null)
			{
				phoneMap = new Phone();
				phoneMap.setPhoneNum(benContact.getPhoneNum3());
				phoneMap.setPhoneType(benContact.getPhoneTyp3());
				phoneMap.setBelongsToBenRegId(belongsToBenRegId);
				phoneMap.setBelongsToName(belongsToName);
				contactsList.add(phoneMap);
			}
			if (benContact.getPhoneNum4() != null)
			{
				phoneMap = new Phone();
				phoneMap.setPhoneNum(benContact.getPhoneNum4());
				phoneMap.setPhoneType(benContact.getPhoneTyp4());
				phoneMap.setBelongsToBenRegId(belongsToBenRegId);
				phoneMap.setBelongsToName(belongsToName);
				contactsList.add(phoneMap);
			}
			if (benContact.getPhoneNum5() != null)
			{
				phoneMap = new Phone();
				phoneMap.setPhoneNum(benContact.getPhoneNum5());
				phoneMap.setPhoneType(benContact.getPhoneTyp5());
				phoneMap.setBelongsToBenRegId(belongsToBenRegId);
				phoneMap.setBelongsToName(belongsToName);
				contactsList.add(phoneMap);
			}
		}
		return contactsList;
	}
}
