package com.iemr.common.identity.repo;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.common.identity.domain.MBeneficiaryregidmapping;

@Repository
public interface BenRegIdMappingRepo extends CrudRepository<MBeneficiaryregidmapping, BigInteger> {
	List<MBeneficiaryregidmapping> findByBenRegIdOrderByBenRegIdAsc(BigInteger benRegId);

	MBeneficiaryregidmapping findByBeneficiaryID(BigInteger beneficiaryID);

	// List<MBeneficiaryregidmapping> findByMBeneficiarydetail(MBeneficiarydetail
	// MBeneficiarydetail);
	List<MBeneficiaryregidmapping> findByCreatedDateBetweenOrderByBenRegIdAsc(Timestamp fromDate, Timestamp toDate);

	MBeneficiaryregidmapping findFirstByProvisionedAndReserved(Boolean isProvisioned, Boolean isReserved);

	List<MBeneficiaryregidmapping> findByProviderServiceMapIDOrderByBenRegIdAsc(Integer providerServiceMapID);

	List<MBeneficiaryregidmapping> findByProviderServiceMapIDAndVehicalNoOrderByBenRegIdAsc(
			Integer providerServiceMapID, String vehicalNo);

	MBeneficiaryregidmapping findFirstByProvisionedAndVehicalNoOrderByBenRegIdAsc(Boolean isProvisioned,
			String vehicalNo);

	MBeneficiaryregidmapping findFirstByProviderServiceMapIDAndVehicalNoOrderByBenRegIdAsc(Integer providerServiceMapID,
			String vehicalNo);

	Long countByProviderServiceMapIDAndVehicalNoOrderByBenRegIdAsc(Integer providerServiceMapID, String vehicalNo);

	Long countByProvisioned(Boolean isProvisioned);

	@Transactional
	@Modifying
	@Query("update MBeneficiaryregidmapping set providerServiceMapID = null , vehicalNo = null "
			+ "where providerServiceMapID = :providerServiceMapID and vehicalNo = :vehicalNo")
	public int unreserveBeneficiaryIds(@Param("providerServiceMapID") Integer providerServiceMapID,
			@Param("vehicalNo") String vehicalNo);

	@Query("SELECT a FROM MBeneficiaryregidmapping a WHERE a.benRegId =:vanSerialNo AND a.vanID =:vanID ")
	MBeneficiaryregidmapping getWith_vanSerialNo_vanID(@Param("vanSerialNo") BigInteger vanSerialNo,
			@Param("vanID") Integer vanID);
	


	List<MBeneficiaryregidmapping> findTop10000ByProvisionedAndReserved(Boolean isProvisioned,Boolean isReserved);

}
