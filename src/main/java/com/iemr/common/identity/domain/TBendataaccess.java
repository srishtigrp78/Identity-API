package com.iemr.common.identity.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

/**
 * The persistent class for the t_bendataaccess database table.
 * 
 */
@Entity
@Table(name = "t_bendataaccess")
@NamedQuery(name = "TBendataaccess.findAll", query = "SELECT t FROM TBendataaccess t")
@Data
public class TBendataaccess implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private BigInteger accessId;

	@Column(nullable = false, length = 45)
	private String accessedBy;

	@Column(nullable = false)
	private Timestamp accessedOn;

	@Column(nullable = false, length = 45)
	private String accessedType;

	@Column(nullable = false, length = 150)
	private String authId;

	@Transient
	// @Column(nullable=false, length=75) //SQL Error: 1054, SQLState: 42S22 Unknown column 'tbendataac0_.consentId' in
	// 'field list'
	private String consentId;

	@Column(length = 4)
	private String processed = "N";

	@Column(nullable = false)
	private Integer userAgentId;

	@Transient
	// @Column(nullable=false, length=20)// SQL Error: 1054, SQLState: 42S22 Unknown column
	// 'tbendataac0_.userAgentIpAddr' in 'field list'
	private String userAgentIpAddr;

	@Column(nullable = false)
	private Integer userAgentPSMapId;

	@Column(nullable = false)
	private Integer userAgentRoleId;

	@Column(nullable = false)
	private Integer userAgentServiceId;

	// commented on 28-09-2018
	// bi-directional many-to-one association to MBeneficiarymapping
//	@ManyToOne(/*fetch = FetchType.EAGER*/) /* (fetch = FetchType.LAZY) */
//	@JoinColumn(name = "BenMapId", nullable = false)
//	private MBeneficiarymapping MBeneficiarymapping;
	@Column(name = "BenMapId", insertable = true, updatable = true)
	private BigInteger benMapId;

}