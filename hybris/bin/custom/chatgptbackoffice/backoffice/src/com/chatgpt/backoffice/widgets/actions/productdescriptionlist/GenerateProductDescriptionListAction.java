/**
 *
 */
package com.chatgpt.backoffice.widgets.actions.productdescriptionlist;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.chatgpt.backoffice.constants.ChatgptbackofficeConstants;
import com.google.common.collect.Lists;
import com.hybris.backoffice.widgets.notificationarea.NotificationService;
import com.hybris.backoffice.widgets.notificationarea.event.NotificationEvent;
import com.hybris.cockpitng.actions.ActionContext;
import com.hybris.cockpitng.actions.ActionResult;
import com.hybris.cockpitng.actions.CockpitAction;
import com.hybris.cockpitng.engine.impl.AbstractComponentWidgetAdapterAware;


/**
 * @author gokul
 *
 */
public class GenerateProductDescriptionListAction extends AbstractComponentWidgetAdapterAware
		implements CockpitAction<Object, List>
{

	private static final Logger LOG = Logger.getLogger(GenerateProductDescriptionListAction.class);

	private static final String SOCKET_OUT_SELECTED_OBJECTS = "currentProducts";
	private static final String ITEM_TYPE_CODE = "Item";

	@Resource(name = "notificationService")
	private NotificationService notificationService;

	@Override
	public boolean canPerform(final ActionContext<Object> ctx)
	{
		if (ctx.getData() != null)
		{
			final List<Object> data = getData(ctx);
			if (data.size() > 0)
			{
				return true;
			}
			else
			{
				return false;
			}

		}
		return false;
	}

	@Override
	public ActionResult<List> perform(final ActionContext<Object> context)
	{
		ActionResult<List> result = new ActionResult<>(ActionResult.ERROR);
		if (context.getData() != null)
		{
			final List<Object> data = getData(context);
			//Limit number of products to 50
			if (CollectionUtils.isNotEmpty(data) && data.size() <= 50)
			{
				sendOutput(SOCKET_OUT_SELECTED_OBJECTS, data);
				result = new ActionResult<>(ActionResult.SUCCESS);
			}
			else
			{
				notificationService.notifyUser(notificationService.getWidgetNotificationSource(context),
						ChatgptbackofficeConstants.GENERATE_PRODUCT_DESCRIPTION_LIST_VALIDATION, NotificationEvent.Level.FAILURE);
			}
		}
		return result;
	}

	protected List<Object> getData(final ActionContext<Object> context)
	{
		if (context.getData() instanceof Collection)
		{
			final Collection<Object> data = (Collection) context.getData();
			return data.stream().filter(o -> !Objects.isNull(o)).collect(Collectors.toList());
		}
		else if (context.getData() != null)
		{
			return Lists.newArrayList(context.getData());
		}
		return Collections.emptyList();
	}

}
