/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.chatgpt.setup;

import static com.chatgpt.constants.ChatgptConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.chatgpt.constants.ChatgptConstants;
import com.chatgpt.service.ChatgptService;


@SystemSetup(extension = ChatgptConstants.EXTENSIONNAME)
public class ChatgptSystemSetup
{
	private final ChatgptService chatgptService;

	public ChatgptSystemSetup(final ChatgptService chatgptService)
	{
		this.chatgptService = chatgptService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		chatgptService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return ChatgptSystemSetup.class.getResourceAsStream("/chatgpt/sap-hybris-platform.png");
	}
}
