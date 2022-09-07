package com.iemr.common.identity.repo;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.common.identity.domain.MBeneficiaryfamilymapping;

@Repository
public interface BenFamilyMappingRepo extends CrudRepository<MBeneficiaryfamilymapping, BigInteger> {
	List<MBeneficiaryfamilymapping> findByBenFamilyMapIdOrderByBenFamilyMapIdAsc(BigInteger benFamilyMapId);

	// List<MBeneficiaryfamilymapping> findByAssociatedBenRegID(BigInteger
	// associatedBenRegID);
	// List<MBeneficiaryfamilymapping>
	// findByAssociatedBenRegIDAndIsEmergencyContact(BigInteger associatedBenRegID,
	// Boolean isEmergencyContact);
	List<MBeneficiaryfamilymapping> findByCreatedDateBetweenOrderByBenFamilyMapIdAsc(Timestamp fromDate,
			Timestamp toDate);

	// @Query("select f from MBeneficiaryfamilymapping f where
	// f.MBeneficiaryregid.benRegId = :associatedBenID")
	// List<MBeneficiaryfamilymapping> findByAssociatedBenID(BigInteger
	// associatedBenID);

	List<MBeneficiaryfamilymapping> findByBenMapIdOrderByBenFamilyMapIdAsc(BigInteger benMapId);

	List<MBeneficiaryfamilymapping> findByBenMapIdAndVanIDOrderByBenFamilyMapIdAsc(BigInteger benMapId, int vanID);

	@Transactional
	@Modifying
	@Query(" UPDATE MBeneficiaryfamilymapping set vanSerialNo = :benFamilyMapId WHERE benFamilyMapId = :benFamilyMapId")
	int updateVanSerialNo(@Param("benFamilyMapId") BigInteger benFamilyMapId);

}
