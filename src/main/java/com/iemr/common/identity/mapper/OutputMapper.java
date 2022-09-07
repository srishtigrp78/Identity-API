package com.iemr.common.identity.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

public class OutputMapper
{
	private static GsonBuilder builder;
	private static OutputMapper instance = null;

	private OutputMapper()
	{
		builder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
				// .excludeFieldsWithoutExposeAnnotation()
				.serializeNulls().setLongSerializationPolicy(LongSerializationPolicy.STRING);
	}

	public static OutputMapper getInstance()
	{
		if (instance == null)
		{
			instance = new OutputMapper();
		}

		return instance;
	}

	public Gson gson()
	{
		return builder.create();
	}
}
