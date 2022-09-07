package com.iemr.common.identity.repo.rmnch;

import java.math.BigInteger;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.common.identity.data.rmnch.RMNCHMBeneficiaryregidmapping;

@Repository
public interface RMNCHMBenRegIdMapRepo extends CrudRepository<RMNCHMBeneficiaryregidmapping, Long> {
	@Query(" SELECT beneficiaryID FROM RMNCHMBeneficiaryregidmapping t "
			+ " WHERE t.benRegId = :benRegID AND t.vanID = :vanID ")
	public BigInteger getBenIdFromRegIDAndVanID(@Param("benRegID") Long benRegID, @Param("vanID") int vanID);

	@Query(" SELECT beneficiaryID FROM RMNCHMBeneficiaryregidmapping t  WHERE t.benRegId = :benRegID ")
	public BigInteger getBenIdFromRegID(@Param("benRegID") Long benRegID);

	@Transactional
	@Modifying
	@Query(" UPDATE RMNCHMBeneficiaryregidmapping t set t.providerServiceMapID = :providerServiceMapID "
			+ " WHERE t.benRegId = :benRegID ")
	public int updateProviderServiceMapID(@Param("benRegID") BigInteger benRegID,
			@Param("providerServiceMapID") int providerServiceMapID);

	@Query(" SELECT t.benRegId FROM RMNCHMBeneficiaryregidmapping t  WHERE t.beneficiaryID = :benID ")
	public Long getRegID(@Param("benID") Long benID);
}
