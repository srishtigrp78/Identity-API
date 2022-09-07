package com.iemr.common.identity.repo;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.common.identity.domain.MBeneficiaryconsent;

@Repository
public interface BenConsentRepo extends CrudRepository<MBeneficiaryconsent, BigInteger> {
	List<MBeneficiaryconsent> findByBenConsentIDOrderByBenConsentIDAsc(BigInteger benConsentID);

	List<MBeneficiaryconsent> findByCreatedDateBetweenOrderByBenConsentIDAsc(Timestamp fromDate, Timestamp toDate);

	@Transactional
	@Modifying
	@Query(" UPDATE MBeneficiaryconsent set vanSerialNo = :benConsentID WHERE benConsentID = :benConsentID")
	int updateVanSerialNo(@Param("benConsentID") BigInteger benConsentID);

	@Query("SELECT a FROM MBeneficiaryconsent a WHERE a.vanSerialNo =:vanSerialNo AND a.vanID =:vanID ")
	MBeneficiaryconsent getWith_vanSerialNo_vanID(@Param("vanSerialNo") BigInteger vanSerialNo,
			@Param("vanID") Integer vanID);
}
