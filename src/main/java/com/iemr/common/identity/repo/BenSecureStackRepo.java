package com.iemr.common.identity.repo;

import org.springframework.stereotype.Repository;

import com.iemr.common.identity.domain.MBensecurestack;

@Repository
public interface BenSecureStackRepo {
	MBensecurestack findByMBensecurestackId(Integer id);

}
