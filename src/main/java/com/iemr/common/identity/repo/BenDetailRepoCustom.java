package com.iemr.common.identity.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.iemr.common.identity.domain.MBeneficiarydetail;
import com.iemr.common.identity.dto.IdentitySearchDTO;

@Repository
public interface BenDetailRepoCustom {

	List<MBeneficiarydetail> advanceFilterSearch(IdentitySearchDTO searchDTO);
}
