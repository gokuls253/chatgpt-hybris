/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.chatgpt.service;

import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.List;


public interface ChatgptService
{
	String getHybrisLogoUrl(String logoCode);

	void createLogo(String logoCode);

	boolean generateProductDescription(List<ProductModel> products);

	String performProductDescriptionJob(List<ItemModel> items);
}
