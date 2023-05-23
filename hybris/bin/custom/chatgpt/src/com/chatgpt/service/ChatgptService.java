/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.chatgpt.service;

public interface ChatgptService
{
	String getHybrisLogoUrl(String logoCode);

	void createLogo(String logoCode);
}
