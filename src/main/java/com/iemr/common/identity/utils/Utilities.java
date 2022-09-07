package com.iemr.common.identity.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemr.common.identity.controller.IdentityController;

public class Utilities {
	//private static final Logger logger = LoggerFactory.getLogger(Utilities.class);
	
	/**
	 * Gets the json as string.
	 *
	 * @param obj the obj
	 * @return the json as string
	 */
	private Logger logger = LoggerFactory.getLogger(IdentityController.class);
	public String getJsonAsString(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(mapper.writeValueAsString(obj));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
		
		return sb.toString();
	}

}
