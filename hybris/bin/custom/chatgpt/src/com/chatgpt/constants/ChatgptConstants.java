/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.chatgpt.constants;

/**
 * Global class for all Chatgpt constants. You can add global constants for your extension into this class.
 */
public final class ChatgptConstants extends GeneratedChatgptConstants
{
	public static final String EXTENSIONNAME = "chatgpt";

	private ChatgptConstants()
	{
		//empty to avoid instantiating this constant class
	}

	// implement here constants used by this extension

	public static final String PLATFORM_LOGO_CODE = "chatgptPlatformLogo";
	public static final String NO_CACHE = "no-cache";
	public static final String JSON_UTF_8 = "application/json; charset=utf-8";
	public static final String BEARER = "Bearer ";
	public static final String CHATGPT_ROLE = "user";


	public static final String PRODUCT_DESCRIPTION_API = "chatgpt.product.description.generator.api";
	public static final String PRODUCT_DESCRIPTION_MODEL = "chatgpt.product.description.model.name";
	public static final String PRODUCT_DESCRIPTION_TEMPERATURE = "chatgpt.product.description.temperature";
	public static final String PRODUCT_DESCRIPTION_MAX_TOKENS = "chatgpt.product.description.max.tokens";

	public static final String LANGUAGE_TRANSLATION_API = "chatgpt.language.translation.api";
	public static final String LANGUAGE_TRANSLATION_MODEL = "chatgpt.language.translation.model.name";
	public static final String LANGUAGE_TRANSLATION_TEMPERATURE = "chatgpt.language.translation.temperature";
	public static final String LANGUAGE_TRANSLATION_MAX_TOKENS = "chatgpt.language.translation.max.tokens";
}
