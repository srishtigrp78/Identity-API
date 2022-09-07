package com.iemr.common.identity.repo.rmnch;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.common.identity.data.rmnch.RMNCHMBeneficiaryaddress;
import com.iemr.common.identity.data.rmnch.RMNCHMBeneficiaryconsent;

@Repository
public interface RMNCHBenConsentRepo extends CrudRepository<RMNCHMBeneficiaryconsent, Long> {
	@Query(" SELECT t FROM RMNCHMBeneficiaryconsent t WHERE t.id = :vanSerialNo AND t.VanID = :vanID")
	public RMNCHMBeneficiaryconsent getByIdAndVanID(@Param("vanSerialNo") BigInteger vanSerialNo,
			@Param("vanID") int vanID);
}
