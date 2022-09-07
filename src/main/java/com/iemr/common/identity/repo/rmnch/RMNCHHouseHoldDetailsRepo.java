package com.iemr.common.identity.repo.rmnch;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.common.identity.data.rmnch.RMNCHHouseHoldDetails;

@Repository
public interface RMNCHHouseHoldDetailsRepo extends CrudRepository<RMNCHHouseHoldDetails, Long> {
	@Query(" SELECT t FROM RMNCHHouseHoldDetails t WHERE t.id = :vanSerialNo AND t.VanID = :vanID")
	public RMNCHHouseHoldDetails getByIdAndVanID(@Param("vanSerialNo") long vanSerialNo, @Param("vanID") int vanID);

	@Query(" SELECT t FROM RMNCHHouseHoldDetails t WHERE t.houseoldId =:houseoldId ")
	public RMNCHHouseHoldDetails getByHouseHoldID(@Param("houseoldId") long houseoldId);
}
