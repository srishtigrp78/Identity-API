package com.iemr.common.identity.repo.rmnch;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.common.identity.data.rmnch.RMNCHMBeneficiarymapping;

@Repository
public interface RMNCHMBenMappingRepo extends CrudRepository<RMNCHMBeneficiarymapping, Long> {
	@Query(" SELECT t FROM RMNCHMBeneficiarymapping t WHERE t.id = :vanSerialNo AND t.VanID = :vanID")
	public RMNCHMBeneficiarymapping getByIdAndVanID(@Param("vanSerialNo") BigInteger vanSerialNo,
			@Param("vanID") int vanID);

	@Query(" SELECT t FROM RMNCHMBeneficiarymapping t WHERE t.benAddressId = :addressID AND t.VanID = :vanID")
	public RMNCHMBeneficiarymapping getByAddressIDAndVanID(@Param("addressID") BigInteger addressID,
			@Param("vanID") int vanID);

	@Query(nativeQuery = true, value = " SELECT userid FROM db_iemr.m_user WHERE UserName = :userName ")
	public Integer getUserIDByUserName(@Param("userName") String userName);

}
