/*
* AMRIT – Accessible Medical Records via Integrated Technology 
* Integrated EHR (Electronic Health Records) Solution 
*
* Copyright (C) "Piramal Swasthya Management and Research Institute" 
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.common.identity.utils.http;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;


@Component
public class HttpUtils {
	public static final String AUTHORIZATION = "Authorization";
	private RestTemplate rest;
	private HttpHeaders headers;
	private HttpStatus status;

	public HttpUtils() {
		if (rest == null) {
			rest = new RestTemplate();
			headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
		}
	}

	private Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	public String get(String uri) {
		String body;
		HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
		ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		setStatus((HttpStatus) responseEntity.getStatusCode());
		body = responseEntity.getBody();
		return body;
	}

	public String get(String uri, Map<String, Object> header) {
		String body;
		HttpHeaders headers = new HttpHeaders();
		if (header.containsKey(HttpHeaders.AUTHORIZATION)) {
			headers.add(HttpHeaders.AUTHORIZATION, header.get(HttpHeaders.AUTHORIZATION).toString());
		}
		if (header.containsKey(HttpHeaders.CONTENT_TYPE)) {
			headers.add(HttpHeaders.CONTENT_TYPE, header.get(HttpHeaders.CONTENT_TYPE).toString());
		} else {
			headers.add("Content-Type", MediaType.APPLICATION_JSON);
		}
		HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
		ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		setStatus((HttpStatus) responseEntity.getStatusCode());
		body = responseEntity.getBody();
		return body;
	}

	public String post(String uri, String json) {
		String body;
		HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
		ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		setStatus((HttpStatus) responseEntity.getStatusCode());
		body = responseEntity.getBody();
		return body;
	}

	public String post(String uri, String data, Map<String, Object> header) {
		String body;
		HttpHeaders headers = new HttpHeaders();
		if (header.containsKey(headers.AUTHORIZATION)) {
			headers.add(headers.AUTHORIZATION, header.get(headers.AUTHORIZATION).toString());
		}
		HttpEntity<String> requestEntity;
		requestEntity = new HttpEntity<>(data, headers);
		ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		setStatus((HttpStatus) responseEntity.getStatusCode());
		body = responseEntity.getBody();
		return body;
	}

	public String uploadFile(String uri, String data, Map<String, Object> header) throws IOException {
		String body;
		HttpHeaders headers = new HttpHeaders();
		if (header.containsKey(headers.AUTHORIZATION)) {
			headers.add(headers.AUTHORIZATION, header.get(headers.AUTHORIZATION).toString());
		}
		if (header.containsKey(headers.CONTENT_TYPE)) {
			headers.add(headers.CONTENT_TYPE, header.get(headers.CONTENT_TYPE).toString());
		} else {
			headers.add("Content-Type", MediaType.APPLICATION_JSON);
		}
		ResponseEntity<String> responseEntity = null;
		if (headers.getContentType().toString().equals(MediaType.MULTIPART_FORM_DATA_TYPE.toString())) {
			HttpEntity<FormDataMultiPart> requestEntity;
			
			try(FormDataMultiPart multiPart = new FormDataMultiPart();
					FileInputStream is = new FileInputStream(data)) {
				
				FormDataBodyPart filePart = new FormDataBodyPart();
				multiPart.bodyPart(filePart);
				multiPart.field("docPath", data);
				headers.add("Content-Type", MediaType.APPLICATION_JSON);
				requestEntity = new HttpEntity<>(multiPart, headers);
				responseEntity = rest.exchange(uri, HttpMethod.POST, requestEntity, String.class);
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			}catch (IOException e) {
				logger.error(e.getMessage());
			}
		} else {
			HttpEntity<String> requestEntity;
			requestEntity = new HttpEntity<>(data, headers);
			responseEntity = rest.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		}
		setStatus((HttpStatus) responseEntity.getStatusCode());
		body = responseEntity.getBody();
		return body;
	}


	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}