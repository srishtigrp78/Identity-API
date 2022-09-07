package com.iemr.common.identity.repo.rmnch;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.common.identity.data.rmnch.RMNCHMBeneficiaryAccount;

@Repository
public interface RMNCHBenAccountRepo extends CrudRepository<RMNCHMBeneficiaryAccount, Long> {
	@Query(" SELECT t FROM RMNCHMBeneficiaryAccount t WHERE t.id = :vanSerialNo AND t.VanID = :vanID")
	public RMNCHMBeneficiaryAccount getByIdAndVanID(@Param("vanSerialNo") BigInteger vanSerialNo,
			@Param("vanID") int vanID);
}
