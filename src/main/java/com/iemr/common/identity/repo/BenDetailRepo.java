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

import com.iemr.common.identity.domain.MBeneficiarydetail;

@Repository
public interface BenDetailRepo extends CrudRepository<MBeneficiarydetail, BigInteger>, BenDetailRepoCustom {
	List<MBeneficiarydetail> findByBeneficiaryDetailsIdOrderByBeneficiaryDetailsIdAsc(BigInteger beneficiaryDetailsId);

	List<MBeneficiarydetail> findByCreatedDateBetweenOrderByBeneficiaryDetailsIdAsc(Timestamp fromDate,
			Timestamp toDate);

	// List<MBeneficiarydetail> findByMBeneficiaryregid(MBeneficiaryregidmapping
	// MBeneficiaryregid);
	List<MBeneficiarydetail> findByFirstNameAndLastNameOrderByBeneficiaryDetailsIdAsc(String fName, String mName);

	// searching with criterias

	@Query("select d from MBeneficiarydetail d " + "where d.mBeneficiarymapping.benRegId = :benRegId")
	MBeneficiarydetail findByBenRegId(BigInteger benRegId);

	// @Query("select d from MBeneficiarydetail d "
	// + "where d.mBeneficiarymapping.beneficiaryID = :beneficiaryID")
	// MBeneficiarydetail findByBeneficiaryID(BigInteger beneficiaryID);

	@Query("select d from MBeneficiarydetail d where d.firstName = :fName and d.middleName = :mName and "
			+ "d.lastName = :lName Order By d.beneficiaryDetailsId Asc")
	List<MBeneficiarydetail> findByName(String fName, String mName, String lName);

	@Query("select d from MBeneficiarydetail d where d.firstName = :fName and d.middleName = :mName and "
			+ "d.lastName = :lName and d.spouseName = :spouseName Order By d.beneficiaryDetailsId Asc")
	List<MBeneficiarydetail> findByNameAndSpouseName(String fName, String mName, String lName, String spouseName);

	@Query("select d from MBeneficiarydetail d where d.firstName = :fName and d.middleName = :mName and "
			+ "d.lastName = :lName and d.fatherName = :fatherName and d.dob = :dob  Order By d.beneficiaryDetailsId Asc")
	List<MBeneficiarydetail> findByNameAndFatherNameAndDob(String fName, String mName, String lName, String fatherName,
			Timestamp dob);

	@Query("select d from MBeneficiarydetail d where d.firstName = :fName and d.middleName = :mName and "
			+ "d.lastName = :lName and d.dob = :dob Order By d.beneficiaryDetailsId Asc")
	List<MBeneficiarydetail> findByNameAndDob(String fName, String mName, String lName, Timestamp dob);

	@Query("select d from MBeneficiarydetail d where d.firstName = :fName and d.middleName = :mName and "
			+ "d.lastName = :lName and d.spouseName = :spouseName and d.dob = :dob Order By d.beneficiaryDetailsId Asc")
	List<MBeneficiarydetail> findByNameAndSpouseNameAndDob(String fName, String mName, String lName, String spouseName,
			Timestamp dob);

	@Query("select d from MBeneficiarydetail d where d.firstName = :fName and d.middleName = :mName and "
			+ "d.lastName = :lName and d.fatherName = :fatherName Order By d.beneficiaryDetailsId Asc")
	List<MBeneficiarydetail> findByNameAndFatherName(String fName, String mName, String lName, String fatherName);

	@Query("select new MBeneficiarydetail(d.beneficiaryDetailsId, d.firstName, d.lastName, d.middleName, "
			+ "d.fatherName, d.spouseName) "
			+ "from MBeneficiarydetail d where d.mBeneficiarymapping.benRegId = :benRegId")
	// + "in (select beneficiaryDetailsId from MBeneficiarymapping where benRegId =
	// :benRegId)")
	MBeneficiarydetail findPartialBeneficiaryDetailByBenRegId(@Param("benRegId") BigInteger benRegId);

	@Query("select new MBeneficiarydetail(d.beneficiaryDetailsId, d.firstName, d.lastName, d.middleName, "
			+ "d.fatherName, d.spouseName) "
			+ "from MBeneficiarydetail d where d.mBeneficiarymapping.benRegId in :benRegIds")
	List<MBeneficiarydetail> findPartialBeneficiaryDetailByBenRegId(@Param("benRegIds") List<BigInteger> benRegId);
	// @Query("select d from MBeneficiarydetail d where d.MBeneficiaryregid.benRegId
	// = :benRegid")
	// List<MBeneficiarydetail> findByBeneficiaryRegid(BigInteger benRegid);

	@Transactional
	@Modifying
	@Query("UPDATE MBeneficiarydetail c SET c.communityId = :communityId WHERE c.vanSerialNo = :id AND c.vanID = :vanID  ")
	Integer updateCommunity(@Param("id") BigInteger id, @Param("vanID") Integer vanID,
			@Param("communityId") Integer communityId);

	@Transactional
	@Modifying
	@Query("UPDATE MBeneficiarydetail c SET c.educationId = :educationId WHERE c.vanSerialNo = :id AND c.vanID = :vanID ")
	Integer updateEducation(@Param("id") BigInteger id, @Param("vanID") Integer vanID,
			@Param("educationId") Integer educationId);

	@Transactional
	@Modifying
	@Query(" UPDATE MBeneficiarydetail set vanSerialNo = :beneficiaryDetailsId WHERE beneficiaryDetailsId = :beneficiaryDetailsId")
	int updateVanSerialNo(@Param("beneficiaryDetailsId") BigInteger beneficiaryDetailsId);

	@Query("SELECT beneficiaryDetailsId FROM MBeneficiarydetail WHERE vanSerialNo =:vanSerialNo AND vanID =:vanID ")
	BigInteger findIdByVanSerialNoAndVanID(@Param("vanSerialNo") BigInteger vanSerialNo, @Param("vanID") Integer vanID);

	@Query("SELECT a FROM MBeneficiarydetail a WHERE a.vanSerialNo =:vanSerialNo AND a.vanID =:vanID ")
	MBeneficiarydetail getWith_vanSerialNo_vanID(@Param("vanSerialNo") BigInteger vanSerialNo,
			@Param("vanID") Integer vanID);

}
