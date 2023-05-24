/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.chatgpt.util;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 */
public final class ChatgptPromptHelper
{

	private static final Logger LOG = LoggerFactory.getLogger(ChatgptPromptHelper.class);

	//Product Description Prompts
	private static final String GENERATE_PROD_DESC = "Generate a product description for an e-commerce store.";
	private static final String NEW_LINE = "\n";
	private static final String PRODUCT_NAME = "Product Name: ";
	private static final String PRODUCT_CATEGORIES = "Product Categories: ";
	private static final String DESCRIPTION = "Description: ";

	private ChatgptPromptHelper()
	{
		//empty to avoid instantiating this helper class
	}

	//Prompt to generate the Product description by passing, the Name & Categories
	public static String generateProductDescriptionPrompt(ProductModel product)
	{
		String productName = product.getName();
		Collection<CategoryModel> superCategories = product.getSupercategories();
		StringBuilder categories = new StringBuilder();
		for (CategoryModel category : superCategories)
		{
			categories.append(category.getName()).append(", ");
		}
		// Remove the trailing comma
		categories.deleteCharAt(categories.length() - 1);

		StringBuilder prompt = new StringBuilder();
		prompt.append(GENERATE_PROD_DESC).append(NEW_LINE).append(PRODUCT_NAME).append(productName).append(NEW_LINE)
				.append(PRODUCT_CATEGORIES).append(categories.toString()).append(NEW_LINE).append(DESCRIPTION);

		return prompt.toString();
	}

}
