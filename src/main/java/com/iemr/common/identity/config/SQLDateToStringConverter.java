package com.iemr.common.identity.config;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class SQLDateToStringConverter implements Converter<java.sql.Timestamp, String>{

	@Override
	public String convert(Timestamp arg0) {
		Date dt = new Date(arg0.getTime());
		return dt.toString();
	}

}
