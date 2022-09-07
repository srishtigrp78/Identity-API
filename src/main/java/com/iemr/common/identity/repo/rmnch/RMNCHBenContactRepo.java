package com.iemr.common.identity.repo.rmnch;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.common.identity.data.rmnch.RMNCHMBeneficiarycontact;

@Repository
public interface RMNCHBenContactRepo extends CrudRepository<RMNCHMBeneficiarycontact, Long> {
	@Query(" SELECT t FROM RMNCHMBeneficiarycontact t WHERE t.id = :vanSerialNo AND t.VanID = :vanID")
	public RMNCHMBeneficiarycontact getByIdAndVanID(@Param("vanSerialNo") BigInteger vanSerialNo,
			@Param("vanID") int vanID);
}
