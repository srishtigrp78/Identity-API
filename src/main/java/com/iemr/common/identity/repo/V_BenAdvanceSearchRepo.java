package com.iemr.common.identity.repo;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.common.identity.domain.V_BenAdvanceSearch;

@Repository
public interface V_BenAdvanceSearchRepo extends CrudRepository<V_BenAdvanceSearch, BigInteger> {

	@Query(nativeQuery = true, value = "SELECT BeneficiaryRegID, HealthID, HealthIDNumber, AuthenticationMode, CreatedDate"
			+ "  FROM db_iemr.m_benhealthidmapping WHERE BeneficiaryRegID=:benRegID")
	List<Object[]> getBenAbhaDetailsByBenRegID(@Param("benRegID") BigInteger benRegID);

	@Query(nativeQuery = true, value = "SELECT BeneficiaryRegID "
			+ "  FROM db_iemr.m_benhealthidmapping WHERE HealthID=:healthID AND BeneficiaryRegID is not null ")
	List<BigInteger> getBenRegIDByHealthID_AbhaAddress(@Param("healthID") String healthID);

	@Query(nativeQuery = true, value = "SELECT BeneficiaryRegID "
			+ "  FROM db_iemr.m_benhealthidmapping WHERE HealthIDNumber=:healthIDNo  AND BeneficiaryRegID is not null ")
	List<BigInteger> getBenRegIDByHealthIDNo_AbhaIdNo(@Param("healthIDNo") String healthIDNo);

}
