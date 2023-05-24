/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.chatgpt.util;

import de.hybris.platform.core.model.product.ProductModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 */
public final class ChatgptPromptHelper
{

	private static final Logger LOG = LoggerFactory.getLogger(ChatgptPromptHelper.class);

	private ChatgptPromptHelper()
	{
		//empty to avoid instantiating this helper class
	}

	public static String generateProductDescriptionPrompt(ProductModel product)
	{

		return null;
	}

}
