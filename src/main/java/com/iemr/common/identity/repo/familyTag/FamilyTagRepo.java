package com.iemr.common.identity.repo.familyTag;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.common.identity.data.familyTagging.BenFamilyMapping;


@Repository
public interface FamilyTagRepo extends CrudRepository<BenFamilyMapping,Long> {
	@Transactional
	@Modifying
	@Query("update BenFamilyMapping set deleted=true, "
			+ " modifiedBy=:modifiedBy where benFamilyTagId IN (:benFamilyTagId)")
	public int untagFamily(@Param("benFamilyTagId") List<Long> benFamilyTagId,@Param("modifiedBy") String modifiedBy);
	
	
	@Query("SELECT obj FROM BenFamilyMapping obj WHERE obj.familyName =:familyName AND obj.villageId =:villageId AND (obj.noOfmembers is not null "
			+ " AND obj.noOfmembers >0)")
	List<BenFamilyMapping> searchFamily(@Param("familyName") String familyName,@Param("villageId") Integer villageId);
	
	@Query("SELECT obj FROM BenFamilyMapping obj WHERE obj.familyName =:familyName AND obj.villageId =:villageId AND obj.familyId =:familyId AND (obj.noOfmembers is not null "
			+ " AND obj.noOfmembers >0)")
	List<BenFamilyMapping> searchFamilyWithFamilyId(@Param("familyName") String familyName,@Param("villageId") Integer villageId,@Param("familyId") String familyId);
	
	@Query("SELECT obj FROM BenFamilyMapping obj WHERE obj.familyId =:familyId")
	BenFamilyMapping searchFamilyByFamilyId(@Param("familyId") String familyId);
	
	@Query(value="SELECT userid FROM db_iemr.m_user where username =:username",nativeQuery=true)
	public Integer getUserId(@Param("username") String username);
}
