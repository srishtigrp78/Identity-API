package com.iemr.common.identity.repo.rmnch;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.common.identity.data.rmnch.RMNCHMBeneficiarydetail;

@Repository
public interface RMNCHBenDetailsRepo extends CrudRepository<RMNCHMBeneficiarydetail, Long> {
	@Query(" SELECT t FROM RMNCHMBeneficiarydetail t WHERE t.id = :vanSerialNo AND t.VanID = :vanID")
	public RMNCHMBeneficiarydetail getByIdAndVanID(@Param("vanSerialNo") BigInteger vanSerialNo,
			@Param("vanID") int vanID);
}
