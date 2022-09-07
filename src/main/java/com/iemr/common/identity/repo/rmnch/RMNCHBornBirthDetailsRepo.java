package com.iemr.common.identity.repo.rmnch;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.common.identity.data.rmnch.RMNCHBornBirthDetails;

@Repository
public interface RMNCHBornBirthDetailsRepo extends CrudRepository<RMNCHBornBirthDetails, Long> {
	@Query(" SELECT t FROM RMNCHBornBirthDetails t WHERE t.id = :vanSerialNo AND t.VanID = :vanID")
	public RMNCHBornBirthDetails getByIdAndVanID(@Param("vanSerialNo") Long vanSerialNo, @Param("vanID") int vanID);

	@Query(" SELECT t FROM RMNCHBornBirthDetails t WHERE t.BenRegId =:benRegID ")
	public RMNCHBornBirthDetails getByRegID(@Param("benRegID") Long benRegID);
}
