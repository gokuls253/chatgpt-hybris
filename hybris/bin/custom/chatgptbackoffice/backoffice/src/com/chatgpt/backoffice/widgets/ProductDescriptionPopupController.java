/**
 *
 */
package com.chatgpt.backoffice.widgets;

import de.hybris.platform.core.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;

import com.chatgpt.service.ChatgptService;
import com.hybris.backoffice.widgets.notificationarea.NotificationService;
import com.hybris.backoffice.widgets.notificationarea.event.NotificationEvent;
import com.hybris.cockpitng.annotations.SocketEvent;
import com.hybris.cockpitng.annotations.ViewEvent;
import com.hybris.cockpitng.labels.LabelService;
import com.hybris.cockpitng.util.DefaultWidgetController;


/**
 * @author gosathyanarayanan
 *
 */
public class ProductDescriptionPopupController extends DefaultWidgetController
{
	public static final String SOCKET_STARTED_DESC_CRON_JOB = "startedProductDescriptionCronJob";
	protected static final String SOCKET_IN_INPUT_OBJECTS = "inputProducts";
	protected static final String SOCKET_OUTPUT_CANCEL = "cancel";
	protected static final String LABEL_TITLE_SINGLE = "title.single";
	protected static final String LABEL_TITLE_WINDOW = "title.window";
	protected static final String LABEL_TITLE_MANY = "title.many";
	protected static final String CANCEL_BUTTON_ID = "cancel";
	protected static final String GENERATE_BUTTON_ID = "generate";
	protected static final String MODEL_DESC_ITEMS = "modelDescItems";

	@WireVariable
	protected transient LabelService labelService;
	@WireVariable
	protected transient ChatgptService chatgptService;
	@Wire
	private Label title;
	@Wire
	private Button generate;

	@WireVariable
	private transient NotificationService notificationService;

	@Override
	public void initialize(final Component comp)
	{
		super.initialize(comp);
		setWidgetTitle(getLabel(LABEL_TITLE_WINDOW));
		final List<ItemModel> syncItems = getDescItems();
		if (CollectionUtils.isNotEmpty(syncItems))
		{
			adjustTitle(syncItems);
		}
	}

	@SocketEvent(socketId = SOCKET_IN_INPUT_OBJECTS)
	public void showSyncJobsForInputObjects(final List<ItemModel> items)
	{
		setValue(MODEL_DESC_ITEMS, items);
		adjustTitle(items);
	}

	protected List<ItemModel> getDescItems()
	{
		List<ItemModel> items = getValue(MODEL_DESC_ITEMS, List.class);
		if (items == null)
		{
			items = new ArrayList<>();
			setValue(MODEL_DESC_ITEMS, items);
		}
		return items;
	}

	protected void adjustTitle(final List<ItemModel> itemsToSync)
	{
		String titleText = null;
		String toolTip = null;
		if (CollectionUtils.isNotEmpty(itemsToSync))
		{
			if (itemsToSync.size() == 1)
			{
				titleText = getLabel(LABEL_TITLE_SINGLE, new Object[]
				{ getLabelService().getObjectLabel(itemsToSync.get(0)) });
				toolTip = titleText;
			}
			else
			{
				titleText = getLabel(LABEL_TITLE_MANY, new Object[]
				{ Integer.valueOf(itemsToSync.size()) });
			}
		}
		title.setValue(titleText);
		title.setTooltiptext(toolTip);
	}

	@ViewEvent(eventName = Events.ON_CLICK, componentID = CANCEL_BUTTON_ID)
	public void closeSyncPopup()
	{
		sendOutput(SOCKET_OUTPUT_CANCEL, null);
	}

	@ViewEvent(eventName = Events.ON_CLICK, componentID = GENERATE_BUTTON_ID)
	public void onGenerateButtonClick()
	{
		final String jobCode = chatgptService.performProductDescriptionJob(getDescItems());
		if (jobCode != null)
		{
			sendOutput(SOCKET_STARTED_DESC_CRON_JOB, jobCode);
			getNotificationService().notifyUser("productdescriptionpopup", "jobStarted", NotificationEvent.Level.SUCCESS);
		}
		else
		{
			getNotificationService().notifyUser("productdescriptionpopup", "jobNotStarted", NotificationEvent.Level.FAILURE);
		}
	}

	protected LabelService getLabelService()
	{
		return labelService;
	}

	protected NotificationService getNotificationService()
	{
		return notificationService;
	}


}
