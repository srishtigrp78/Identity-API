package com.iemr.common.identity.repo.rmnch;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.common.identity.data.rmnch.RMNCHBeneficiaryDetailsRmnch;

@Repository
public interface RMNCHBeneficiaryDetailsRmnchRepo extends CrudRepository<RMNCHBeneficiaryDetailsRmnch, Long> {
	@Query(" SELECT t FROM RMNCHBeneficiaryDetailsRmnch t WHERE t.id = :vanSerialNo AND t.VanID = :vanID")
	public RMNCHBeneficiaryDetailsRmnch getByIdAndVanID(@Param("vanSerialNo") Long vanSerialNo,
			@Param("vanID") int vanID);

	@Query(" SELECT t FROM RMNCHBeneficiaryDetailsRmnch t WHERE t.BenRegId =:benRegID ")
	public RMNCHBeneficiaryDetailsRmnch getByRegID(@Param("benRegID") Long benRegID);
}
