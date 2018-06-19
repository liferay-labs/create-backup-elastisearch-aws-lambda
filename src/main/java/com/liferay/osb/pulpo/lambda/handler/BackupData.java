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

package com.liferay.osb.pulpo.lambda.handler;

/**
 * @author Cristina González
 */
public class BackupData {

	public String getBucket() {
		return _bucket;
	}

	public String getHost() {
		return _host;
	}

	public String getRole() {
		return _role;
	}

	public void setBucket(String bucket) {
		_bucket = bucket;
	}

	public void setHost(String host) {
		_host = host;
	}

	public void setRole(String role) {
		_role = role;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("BackupData{");

		sb.append("_bucket='");
		sb.append(_bucket);
		sb.append('\'');
		sb.append(", _host='");
		sb.append(_host);
		sb.append('\'');
		sb.append(", _role='");
		sb.append(_role);
		sb.append('\'');
		sb.append('}');

		return sb.toString();
	}

	private String _bucket;
	private String _host;
	private String _role;

}