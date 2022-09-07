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

import com.iemr.common.identity.domain.MBeneficiaryservicemapping;

@Repository
public interface BenServiceMappingRepo extends CrudRepository<MBeneficiaryservicemapping, BigInteger> {
	List<MBeneficiaryservicemapping> findByBenServiceMapID(BigInteger benServiceMapID);

	List<MBeneficiaryservicemapping> findByProviderServiceMapId(Integer registeredProviderServiceMapId);

	List<MBeneficiaryservicemapping> findByProviderServiceMapIdAndRegisteredDateBetween(
			Integer registeredProviderServiceMapId, Timestamp from, Timestamp To);

	List<MBeneficiaryservicemapping> findByServiceIdAndRegisteredDateBetween(Integer serviceId, Timestamp from,
			Timestamp To);

	List<MBeneficiaryservicemapping> findByServiceNameAndRegisteredDateBetween(String serviceName, Timestamp from,
			Timestamp To);

	List<MBeneficiaryservicemapping> findByStateIdAndRegisteredDateBetween(Integer stateId, Timestamp from,
			Timestamp To);

	List<MBeneficiaryservicemapping> findByStateNameAndRegisteredDateBetween(String stateName, Timestamp from,
			Timestamp To);

	List<MBeneficiaryservicemapping> findByServiceProviderIdAndRegisteredDateBetween(Integer serviceProviderId,
			Timestamp from, Timestamp To);

	List<MBeneficiaryservicemapping> findByServiceProviderNameAndRegisteredDateBetween(String serviceProviderName,
			Timestamp from, Timestamp To);

	Long countByProviderServiceMapIdAndRegisteredDateBetween(Integer registeredProviderServiceMapId, Timestamp from,
			Timestamp To);

	Long countByServiceIdAndRegisteredDateBetween(Integer serviceId, Timestamp from, Timestamp To);

	Long countByServiceNameAndRegisteredDateBetween(String serviceName, Timestamp from, Timestamp To);

	Long countByStateIdAndRegisteredDateBetween(Integer stateId, Timestamp from, Timestamp To);

	Long countByStateNameAndRegisteredDateBetween(String stateName, Timestamp from, Timestamp To);

	Long countByServiceProviderIdAndRegisteredDateBetween(Integer serviceProviderId, Timestamp from, Timestamp To);

	Long countByServiceProviderNameAndRegisteredDateBetween(String serviceProviderName, Timestamp from, Timestamp To);

	List<MBeneficiaryservicemapping> findByCreatedDateBetween(Timestamp fromDate, Timestamp toDate);

	@Transactional
	@Modifying
	@Query(" UPDATE MBeneficiaryservicemapping set vanSerialNo = :benServiceMapID WHERE benServiceMapID = :benServiceMapID ")
	int updateVanSerialNo(@Param("benServiceMapID") BigInteger benServiceMapID);
}
