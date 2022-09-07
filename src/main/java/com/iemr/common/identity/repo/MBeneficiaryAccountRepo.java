package com.iemr.common.identity.repo;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.common.identity.domain.MBeneficiaryAccount;

public interface MBeneficiaryAccountRepo extends CrudRepository<MBeneficiaryAccount, Long> {
	@Transactional
	@Modifying
	@Query(" UPDATE MBeneficiaryAccount set vanSerialNo = :benAccountID WHERE benAccountID = :benAccountID")
	int updateVanSerialNo(@Param("benAccountID") BigInteger benAccountID);

	@Query("SELECT benAccountID FROM MBeneficiaryAccount WHERE vanSerialNo =:vanSerialNo AND vanID =:vanID ")
	BigInteger findIdByVanSerialNoAndVanID(@Param("vanSerialNo") BigInteger vanSerialNo, @Param("vanID") Integer vanID);

	@Query("SELECT a FROM MBeneficiaryAccount a WHERE a.vanSerialNo =:vanSerialNo AND a.vanID =:vanID ")
	MBeneficiaryAccount getWith_vanSerialNo_vanID(@Param("vanSerialNo") BigInteger vanSerialNo,
			@Param("vanID") Integer vanID);

}
