/**
 *
 */
package com.chatgpt.backoffice.widgets.actions.productdescription;

import de.hybris.platform.core.model.product.ProductModel;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.chatgpt.backoffice.constants.ChatgptbackofficeConstants;
import com.chatgpt.service.ChatgptService;
import com.hybris.backoffice.widgets.notificationarea.event.NotificationEvent.Level;
import com.hybris.cockpitng.actions.ActionContext;
import com.hybris.cockpitng.actions.ActionResult;
import com.hybris.cockpitng.actions.CockpitAction;
import com.hybris.cockpitng.util.notifications.NotificationService;
import com.hybris.cockpitng.util.notifications.event.NotificationEventTypes;


/**
 * @author gokul
 *
 */
public class GenerateProductDescriptionAction implements CockpitAction<ProductModel, ProductModel>
{

	private static final Logger LOG = Logger.getLogger(GenerateProductDescriptionAction.class);

	@Resource(name = "chatgptService")
	private ChatgptService chatgptService;

	@Resource
	private NotificationService notificationService;

	@Override
	public ActionResult<ProductModel> perform(final ActionContext<ProductModel> productActionContext)
	{
		final ProductModel product = productActionContext.getData();
		try
		{
			final boolean result = chatgptService.generateProductDescription(Arrays.asList(product));
			if (result)
			{
				notificationService.notifyUser(ChatgptbackofficeConstants.GENERATE_PRODUCT_DESCRIPTION_SUCCESS,
						NotificationEventTypes.EVENT_TYPE_GENERAL, Level.SUCCESS);
				return new ActionResult<>(ActionResult.SUCCESS);
			}
			else
			{
				notificationService.notifyUser(ChatgptbackofficeConstants.GENERATE_PRODUCT_DESCRIPTION_ERROR,
						NotificationEventTypes.EVENT_TYPE_GENERAL, Level.FAILURE);
				return new ActionResult<>(ActionResult.ERROR);
			}
		}
		catch (final Exception exception)
		{
			notificationService.notifyUser(ChatgptbackofficeConstants.GENERATE_PRODUCT_DESCRIPTION_ERROR,
					NotificationEventTypes.EVENT_TYPE_GENERAL, Level.FAILURE);
			return new ActionResult<>(ActionResult.ERROR);
		}
	}

	public boolean canPerform(final ActionContext<ProductModel> productActionContext)
	{
		return true;
	}

}
