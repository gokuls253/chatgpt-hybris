package com.chatgpt.backoffice.jalo;

import com.chatgpt.backoffice.constants.ChatgptbackofficeConstants;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import org.apache.log4j.Logger;

public class ChatgptbackofficeManager extends GeneratedChatgptbackofficeManager
{
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger( ChatgptbackofficeManager.class.getName() );
	
	public static final ChatgptbackofficeManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (ChatgptbackofficeManager) em.getExtension(ChatgptbackofficeConstants.EXTENSIONNAME);
	}
	
}
