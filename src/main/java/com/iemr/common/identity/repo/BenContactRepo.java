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

import com.iemr.common.identity.domain.MBeneficiarycontact;

@Repository
public interface BenContactRepo extends CrudRepository<MBeneficiarycontact, BigInteger> {
	List<MBeneficiarycontact> findByBenContactsIDOrderByBenContactsIDAsc(BigInteger BenContactsID);

	List<MBeneficiarycontact> findByCreatedDateBetweenOrderByBenContactsIDAsc(Timestamp fromDate, Timestamp toDate);

	List<MBeneficiarycontact> findByEmergencyContactNumOrderByBenContactsIDAsc(String emergencyContactNum);

	List<MBeneficiarycontact> findByEmailIdOrderByBenContactsIDAsc(String emailId);

	List<MBeneficiarycontact> findByPreferredPhoneNumOrderByBenContactsIDAsc(String phoneNum);

	List<MBeneficiarycontact> findByPreferredSMSPhoneNumOrderByBenContactsIDAsc(String SMSPhoneNum);

//	@Query("select c from MBeneficiarycontact c where c.preferredPhoneNum = :phoneNum or phoneNum1 = :phoneNum "
//			+ "or c.phoneNum2 = :phoneNum or c.phoneNum3 = :phoneNum or c.phoneNum4 = :phoneNum "
//			+ "or c.phoneNum5 = :phoneNum order by c.benContactsID desc")
//	List<MBeneficiarycontact> findByAnyPhoneNum(@Param("phoneNum") String phoneNum);
	
	@Query("select c from MBeneficiarycontact c where c.preferredPhoneNum = :phoneNum ")
	List<MBeneficiarycontact> findByAnyPhoneNum(@Param("phoneNum") String phoneNum);


	@Query("select c from MBeneficiarycontact c where c.preferredPhoneNum = :phoneNum or c.phoneNum1 = :phoneNum "
			+ "or c.phoneNum2 = :phoneNum or c.phoneNum3 = :phoneNum or c.phoneNum4 = :phoneNum "
			+ "or c.phoneNum5 = :phoneNum or c.emergencyContactNum = :phoneNum order by c.benContactsID")
	List<MBeneficiarycontact> findByAllPhoneNum(@Param("phoneNum") String phoneNum);

	@Transactional
	@Modifying
	@Query(" UPDATE MBeneficiarycontact set vanSerialNo = :benContactsID WHERE benContactsID = :benContactsID")
	int updateVanSerialNo(@Param("benContactsID") BigInteger benContactsID);

	@Query("SELECT benContactsID FROM MBeneficiarycontact WHERE vanSerialNo =:vanSerialNo AND vanID =:vanID ")
	BigInteger findIdByVanSerialNoAndVanID(@Param("vanSerialNo") BigInteger vanSerialNo, @Param("vanID") Integer vanID);

	@Query("SELECT a FROM MBeneficiarycontact a WHERE a.vanSerialNo =:vanSerialNo AND a.vanID =:vanID ")
	MBeneficiarycontact getWith_vanSerialNo_vanID(@Param("vanSerialNo") BigInteger vanSerialNo,
			@Param("vanID") Integer vanID);

}
