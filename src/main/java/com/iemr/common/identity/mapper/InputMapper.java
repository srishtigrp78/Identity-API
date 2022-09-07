package com.iemr.common.identity.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.LongSerializationPolicy;
import com.iemr.common.identity.exception.IEMRException;

public class InputMapper
{
	private static final Logger logger = LoggerFactory.getLogger(InputMapper.class);

	private static GsonBuilder builder;
	private static InputMapper instance = null;

	private InputMapper()
	{
		builder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
				// .excludeFieldsWithoutExposeAnnotation()
				.serializeNulls().setLongSerializationPolicy(LongSerializationPolicy.STRING);
	}

	public static InputMapper getInstance()
	{
		if (instance == null)
		{
			instance = new InputMapper();
		}

		return instance;
	}

	public Gson gson()
	{
		return builder.create();
	}

	public <T> T fromJson(String json, Class<T> classOfT) throws IEMRException
	{
		return builder.create().fromJson(json, classOfT);
	}

	public boolean validate(String json) throws IEMRException
	{
		// JsonElement - could be a JsonObject, a JsonArray, a JsonPrimitive or a JsonNull
		JsonElement element = new JsonParser().parse(json);

		if (element instanceof JsonObject)
		{
			logger.info("Of Type JsonObject - true!");
			return true;
		} else if (element instanceof JsonArray)
		{
			logger.info("Of Type JsonArray - true!");
			return true;
		} else if (element instanceof JsonPrimitive)
		{
			logger.info("Of Type JsonPrimitive - true!");
			return true;
		} else if (element instanceof JsonNull)
		{
			logger.info("Of Type JsonNull - true!");
			return true;
		}

		return false;
	}
}
