package com.iemr.common.identity.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="m_bensecurestack")
@NamedQuery(name="MBensecurestack.findAll", query="SELECT m FROM MBensecurestack m")
@Data
public class MBensecurestack {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer benSecureStackId;
	
	private Integer benSecureStackSchemeId;
	
	@Column(nullable=false, length=50)
	private String benSecureStackSchemeName;
	
	@Column(nullable=false, length=128)
	private String benKey1;
	
	@Column(nullable=false, length=128)
	private String benKey2;
	
	@Column(nullable=false, length=128)
	private String benKey3;
	
	@Column(nullable=false, length=256)
	private String benKey4;
	
	@Column(nullable=false, length=256)
	private String benKey5;
	
	@Column(nullable=false, length=256)
	private String benKey6;
	
	@Column(nullable=false, length=32)
	private String benSalt1;
	
	@Column(nullable=false, length=32)
	private String benSalt2;
	
	@Column(nullable=false, length=64)
	private String benSalt3;
	
	@Column(nullable=false, length=64)
	private String benSalt4;
	
	@Column(nullable=false, length=128)
	private String benSalt5;
	
	@Column(nullable=false, length=128)
	private String benSalt6;
	
	@Column(nullable=false)
	private Timestamp startDate;
	
	@Column(nullable=false)
	private Timestamp expiryDate;
	
	@Column(nullable=false, length=50)
	private String createdBy;
	
	@Column(name="CreatedDate", insertable=false, updatable=false)
	private Timestamp createdDate;
}
