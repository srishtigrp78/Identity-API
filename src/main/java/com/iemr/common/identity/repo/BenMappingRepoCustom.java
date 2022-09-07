package com.iemr.common.identity.repo;

import java.util.List;

import com.iemr.common.identity.domain.MBeneficiarymapping;
import com.iemr.common.identity.domain.V_BenAdvanceSearch;
import com.iemr.common.identity.dto.IdentityDTO;
import com.iemr.common.identity.dto.IdentitySearchDTO;

public interface BenMappingRepoCustom {

	List<MBeneficiarymapping> dynamicFilterSearch(IdentitySearchDTO searchDTO);

	/**
	 * This method is to search finite beneficiary for MCTS before in order to
	 * generate iemr ID and can be use to finite search generally
	 * 
	 * @param identityDTO
	 * @return mBeneficiarymappingList
	 */
	List<MBeneficiarymapping> finiteSearch(IdentityDTO identityDTO);

	List<V_BenAdvanceSearch> dynamicFilterSearchNew(IdentitySearchDTO searchDTO);
}
