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
import com.hybris.backoffice.widgets.notificationarea.NotificationService;
import com.hybris.backoffice.widgets.notificationarea.event.NotificationEvent;
import com.hybris.cockpitng.actions.ActionContext;
import com.hybris.cockpitng.actions.ActionResult;
import com.hybris.cockpitng.actions.CockpitAction;


/**
 * @author gokul
 *
 */
public class GenerateProductDescriptionAction implements CockpitAction<ProductModel, ProductModel>
{

	private static final Logger LOG = Logger.getLogger(GenerateProductDescriptionAction.class);

	@Resource(name = "chatgptService")
	private ChatgptService chatgptService;

	@Resource(name = "notificationService")
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
				notificationService.notifyUser(notificationService.getWidgetNotificationSource(productActionContext),
						ChatgptbackofficeConstants.GENERATE_PRODUCT_DESCRIPTION_EVENT, NotificationEvent.Level.SUCCESS);
				return new ActionResult<ProductModel>(ActionResult.SUCCESS, product);
			}
			else
			{
				notificationService.notifyUser(notificationService.getWidgetNotificationSource(productActionContext),
						ChatgptbackofficeConstants.GENERATE_PRODUCT_DESCRIPTION_EVENT, NotificationEvent.Level.FAILURE);
				return new ActionResult<>(ActionResult.ERROR);
			}
		}
		catch (final Exception exception)
		{
			notificationService.notifyUser(notificationService.getWidgetNotificationSource(productActionContext),
					ChatgptbackofficeConstants.GENERATE_PRODUCT_DESCRIPTION_EVENT, NotificationEvent.Level.FAILURE);
			return new ActionResult<>(ActionResult.ERROR);
		}
	}

	public boolean canPerform(final ActionContext<ProductModel> productActionContext)
	{
		return true;
	}

}
