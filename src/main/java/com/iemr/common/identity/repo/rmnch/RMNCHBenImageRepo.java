package com.iemr.common.identity.repo.rmnch;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.common.identity.data.rmnch.RMNCHMBeneficiaryImage;

@Repository
public interface RMNCHBenImageRepo extends CrudRepository<RMNCHMBeneficiaryImage, Long> {
	@Query(" SELECT t FROM RMNCHMBeneficiaryImage t WHERE t.id = :vanSerialNo AND t.VanID = :vanID")
	public RMNCHMBeneficiaryImage getByIdAndVanID(@Param("vanSerialNo") Long vanSerialNo, @Param("vanID") int vanID);
}
