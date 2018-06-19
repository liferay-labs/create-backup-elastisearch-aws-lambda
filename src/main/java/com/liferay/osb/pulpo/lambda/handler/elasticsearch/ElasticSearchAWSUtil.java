/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.osb.pulpo.lambda.handler.elasticsearch;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpMethodName;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.liferay.osb.pulpo.lambda.handler.BackupData;
import com.liferay.osb.pulpo.lambda.handler.http.SimpleHttpErrorResponseHandler;
import com.liferay.osb.pulpo.lambda.handler.http.SimpleHttpResponseHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.net.URI;

import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Cristina González
 */
public class ElasticSearchAWSUtil {

	public static String backupIndices(
		BackupData backupData, LambdaLogger lambdaLogger) {

		String content =
			"{\"type\": \"s3\",\"settings\": { \"bucket\": \"" +
				backupData.getBucket() +
					"\",\"endpoint\": \"s3.amazonaws.com\",\"role_arn\": \"" +
						backupData.getRole() + "\"}}";

		Request<Void> request = _getRequest(
			backupData.getHost(), "/_snapshot/" + backupData.getBucket(),
			Collections.emptyMap(), HttpMethodName.POST, content);

		new AmazonHttpClient(
			new ClientConfiguration()
		).requestExecutionBuilder(
		).executionContext(
			new ExecutionContext(true)
		).request(
			request
		).errorResponseHandler(
			new SimpleHttpErrorResponseHandler()
		).execute(
			new SimpleHttpResponseHandler()
		);

		String backupName =
			backupData.getBucket() + "/" + _format.format(new Date());

		lambdaLogger.log("Creating Backup; " + backupName);

		Request<Void> backupRequest = _getRequest(
			backupData.getHost(), "/_snapshot/" + backupName,
			Collections.emptyMap(), HttpMethodName.PUT,
			"{\"ignore_unavailable\": true, \"include_global_state\": false}");

		new AmazonHttpClient(
			new ClientConfiguration()
		).requestExecutionBuilder(
		).executionContext(
			new ExecutionContext(true)
		).request(
			backupRequest
		).errorResponseHandler(
			new SimpleHttpErrorResponseHandler()
		).execute(
			new SimpleHttpResponseHandler()
		);

		return backupName;
	}

	private static Request<Void> _getRequest(
		String host, String path, Map<String, List<String>> params,
		HttpMethodName httpMethodName, String content) {

		Request<Void> request = new DefaultRequest<>("es");

		request.setHttpMethod(httpMethodName);

		request.setEndpoint(URI.create(host));

		request.setResourcePath(path);

		if (!params.isEmpty()) {
			request.setParameters(params);
		}

		if (content != null) {
			InputStream contentInputStream = new ByteArrayInputStream(
				content.getBytes());

			request.setContent(contentInputStream);
		}

		AWS4Signer aws4Signer = new AWS4Signer();

		aws4Signer.setServiceName(request.getServiceName());
		aws4Signer.setRegionName(_REGION);
		aws4Signer.sign(
			request, new DefaultAWSCredentialsProviderChain().getCredentials());

		return request;
	}

	private static final String _REGION = System.getenv(
		SDKGlobalConfiguration.AWS_REGION_ENV_VAR);

	private static final SimpleDateFormat _format = new SimpleDateFormat(
		"yyyyMMddHHmmss");

}