package com.iemr.common.identity.dto;

import java.math.BigInteger;
import java.sql.Timestamp;

//@Data
public class BenIdImportDTO {
	private BigInteger benRegId;
	private BigInteger beneficiaryId;
	private String createdBy;
	private Timestamp createdDate;

	public BigInteger getBenRegId() {
		return benRegId;
	}

	public void setBenRegId(BigInteger benRegId) {
		this.benRegId = benRegId;
	}

	public BigInteger getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(BigInteger beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

}
