package com.iemr.common.identity.utils.km;

public interface KMService
{
	String getDocumentRoot();
	
	String createDocument(String documentPath, String filepath);
}
