/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.chatgpt.service;

import de.hybris.platform.core.model.product.ProductModel;

import java.util.List;

public interface ChatgptService
{
	String getHybrisLogoUrl(String logoCode);

	void createLogo(String logoCode);
	
	void generateProductDescription(List<ProductModel> products);
}
