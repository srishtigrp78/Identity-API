package com.iemr.common.identity.data.rmnch;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class NCD_TB_HRP_data {

	@Expose
	private String confirmed_ncd;
	@Expose
	private String confirmed_hrp;
	@Expose
	private String confirmed_tb;
	@Expose
	private String confirmed_ncd_diseases;
	@Expose
	private String diagnosis_status;
	@Expose
	private List<Map<String, String>> confirmed_ncd_diseases_Snomed;
	private String p_diagnosis;
	private String p_diagnosis_snomed;
	private String visit_code;
	private Long benRegID;
	private String visit_category;

}
