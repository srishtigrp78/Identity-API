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

import com.iemr.common.identity.domain.MBeneficiaryaddress;

@Repository
public interface BenAddressRepo extends CrudRepository<MBeneficiaryaddress, BigInteger> {
	List<MBeneficiaryaddress> findByBenAddressIDOrderByBenAddressIDAsc(BigInteger benAddressID);

	List<MBeneficiaryaddress> findByCurrPinCodeOrderByBenAddressIDAsc(String currPinCode);

	List<MBeneficiaryaddress> findByPermPinCodeOrderByBenAddressIDAsc(String permPinCode);

	List<MBeneficiaryaddress> findByCurrStateAndCurrDistrictOrderByBenAddressIDAsc(String currState, String currDist);

	List<MBeneficiaryaddress> findByCurrStateIdAndCurrDistrictIdOrderByBenAddressIDAsc(Integer currStateID,
			Integer currDistID);

	List<MBeneficiaryaddress> findByPermStateAndPermDistrictOrderByBenAddressIDAsc(String permState, String permDist);

	List<MBeneficiaryaddress> findByPermStateIdAndPermDistrictIdOrderByBenAddressIDAsc(Integer permStateID,
			Integer permDistID);

	List<MBeneficiaryaddress> findByEmerStateAndEmerDistrictOrderByBenAddressIDAsc(String EmerState, String EmerDist);

	List<MBeneficiaryaddress> findByEmerStateIdAndEmerDistrictIdOrderByBenAddressIDAsc(Integer EmerStateID,
			Integer EmerDistID);

	List<MBeneficiaryaddress> findByCreatedDateBetweenOrderByBenAddressIDAsc(Timestamp fromDate, Timestamp toDate);

	@Query("select a from MBeneficiaryaddress a where a.currAddressValue = :address or "
			+ "a.emerAddressValue = :address or a.permAddressValue = :address order by a.benAddressID asc")
	List<MBeneficiaryaddress> findByAddress(String address);

	@Transactional
	@Modifying
	@Query(" UPDATE MBeneficiaryaddress set vanSerialNo = :benAddressID WHERE benAddressID = :benAddressID")
	int updateVanSerialNo(@Param("benAddressID") BigInteger benAddressID);

	@Query("SELECT benAddressID FROM MBeneficiaryaddress WHERE vanSerialNo =:vanSerialNo AND vanID =:vanID ")
	BigInteger findIdByVanSerialNoAndVanID(@Param("vanSerialNo") BigInteger vanSerialNo, @Param("vanID") Integer vanID);

	@Query("SELECT a FROM MBeneficiaryaddress a WHERE a.vanSerialNo =:vanSerialNo AND a.vanID =:vanID ")
	MBeneficiaryaddress getWith_vanSerialNo_vanID(@Param("vanSerialNo") BigInteger vanSerialNo,
			@Param("vanID") Integer vanID);
}
