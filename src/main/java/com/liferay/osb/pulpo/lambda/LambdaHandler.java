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

package com.liferay.osb.pulpo.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.liferay.osb.pulpo.lambda.handler.BackupData;
import com.liferay.osb.pulpo.lambda.handler.elasticsearch.ElasticSearchAWSUtil;

/**
 * @author Cristina González
 */
public class LambdaHandler implements RequestHandler<BackupData, String> {

	@Override
	public String handleRequest(BackupData backupData, Context context) {
		LambdaLogger logger = context.getLogger();

		logger.log(
			"You are going to backup all indices for " + backupData + "\n");

		return ElasticSearchAWSUtil.backupIndices(backupData);
	}

	//"us-east-1-pulpo-elasticsearch-dev-backup"

}