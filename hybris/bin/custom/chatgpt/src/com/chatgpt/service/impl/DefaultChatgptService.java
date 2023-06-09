/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.chatgpt.service.impl;

import de.hybris.platform.catalog.model.CatalogUnawareMediaModel;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.cronjob.CronJobService;
import de.hybris.platform.servicelayer.exceptions.SystemException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpMethod;

import com.chatgpt.client.ChatGPTClient;
import com.chatgpt.constants.ChatgptConstants;
import com.chatgpt.dto.ChatgptGenerateProductDescriptionRequest;
import com.chatgpt.dto.ChatgptGenerateProductDescriptionResponse;
import com.chatgpt.dto.Message;
import com.chatgpt.model.ChatgptProductDescriptonCronjobModel;
import com.chatgpt.service.ChatgptService;
import com.chatgpt.util.ChatgptPromptHelper;
import com.google.gson.Gson;



public class DefaultChatgptService implements ChatgptService
{
	private static final Logger LOG = LoggerFactory.getLogger(DefaultChatgptService.class);

	private MediaService mediaService;
	private ModelService modelService;
	private FlexibleSearchService flexibleSearchService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "chatGptClient")
	private ChatGPTClient chatGptClient;

	@Resource(name = "commonI18NService")
	private CommonI18NService commonI18NService;

	@Resource(name = "cronJobService")
	private CronJobService cronJobService;

	@Override
	public String getHybrisLogoUrl(final String logoCode)
	{
		final MediaModel media = mediaService.getMedia(logoCode);

		// Keep in mind that with Slf4j you don't need to check if debug is enabled, it is done under the hood.
		LOG.debug("Found media [code: {}]", media.getCode());

		return media.getURL();
	}

	@Override
	public void createLogo(final String logoCode)
	{
		final Optional<CatalogUnawareMediaModel> existingLogo = findExistingLogo(logoCode);

		final CatalogUnawareMediaModel media = existingLogo.isPresent() ? existingLogo.get()
				: modelService.create(CatalogUnawareMediaModel.class);
		media.setCode(logoCode);
		media.setRealFileName("sap-hybris-platform.png");
		modelService.save(media);

		mediaService.setStreamForMedia(media, getImageStream());
	}

	private final static String FIND_LOGO_QUERY = "SELECT {" + CatalogUnawareMediaModel.PK + "} FROM {"
			+ CatalogUnawareMediaModel._TYPECODE + "} WHERE {" + CatalogUnawareMediaModel.CODE + "}=?code";

	private Optional<CatalogUnawareMediaModel> findExistingLogo(final String logoCode)
	{
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(FIND_LOGO_QUERY);
		fQuery.addQueryParameter("code", logoCode);

		try
		{
			return Optional.of(flexibleSearchService.searchUnique(fQuery));
		}
		catch (final SystemException e)
		{
			return Optional.empty();
		}
	}

	private InputStream getImageStream()
	{
		return DefaultChatgptService.class.getResourceAsStream("/chatgpt/sap-hybris-platform.png");
	}

	@Override
	public boolean generateProductDescription(final List<ProductModel> products)
	{
		try
		{
			LOG.debug("Generating Product Description using ChatGPT model | START ");
			final List<ProductModel> items = new ArrayList<>();
			boolean result = true;
			for (final ProductModel product : products)
			{
				final String productDescriptionRequestJson = generateProductDescriptionRequest(product);
				final String requestUrl = configurationService.getConfiguration().getString(ChatgptConstants.PRODUCT_DESCRIPTION_API);
				final String productDescriptionResponseJson = chatGptClient.doRequest(String.valueOf(HttpMethod.POST), requestUrl,
						productDescriptionRequestJson, null);
				final ChatgptGenerateProductDescriptionResponse productDescriptionResponse = new Gson()
						.fromJson(productDescriptionResponseJson, ChatgptGenerateProductDescriptionResponse.class);
				if (productDescriptionResponse != null && productDescriptionResponse.getChoices() != null
						&& productDescriptionResponse.getChoices().size() > 0)
				{
					LOG.debug("Product description successfully generated using ChatGPT model : Product Code : {}", product.getCode());
					final Message message = productDescriptionResponse.getChoices().get(0).getMessage();
					if (message != null)
					{
						product.setDescription(message.getContent());
					}
					items.add(product);
				}
				else
				{
					LOG.error("Product description failed to generate using ChatGPT model : Product Code : {}", product.getCode());
					result = false;
				}
			}
			if (items.size() > 0)
			{
				modelService.saveAll(items);
			}
			items.forEach(item -> {
				modelService.refresh(item);
			});
			LOG.debug("Generating Product Description using ChatGPT model | END ");
			return result;
		}
		catch (Exception ex)
		{
			LOG.error("Product description failed to generate using ChatGPT model : ", ex.getMessage());
			return false;
		}
	}

	/**
	 *
	 */
	private String generateProductDescriptionRequest(final ProductModel product)
	{
		LOG.debug("Populating Product Description ChatGPT API request | START ");
		final ChatgptGenerateProductDescriptionRequest request = new ChatgptGenerateProductDescriptionRequest();
		final Message message = new Message();
		message.setRole(ChatgptConstants.CHATGPT_ROLE);
		message.setContent(ChatgptPromptHelper.generateProductDescriptionPrompt(product));
		request.setMessages(Arrays.asList(message));
		request.setMax_tokens(configurationService.getConfiguration().getInt(ChatgptConstants.PRODUCT_DESCRIPTION_MAX_TOKENS));
		request.setTemperature(configurationService.getConfiguration().getDouble(ChatgptConstants.PRODUCT_DESCRIPTION_TEMPERATURE));
		request.setModel(configurationService.getConfiguration().getString(ChatgptConstants.PRODUCT_DESCRIPTION_MODEL));
		LOG.debug("Populating Product Description ChatGPT API request | END ");
		return new Gson().toJson(request);
	}

	@Override
	public String performProductDescriptionJob(List<ItemModel> items)
	{
		try
		{
			LOG.debug("performProductDescriptionJob | START ");
			List<ProductModel> products = new ArrayList<>();
			items.forEach(item -> {
				if (item instanceof ProductModel)
				{
					products.add((ProductModel) item);
				}
			});
			ChatgptProductDescriptonCronjobModel descriptionJob = modelService.create(ChatgptProductDescriptonCronjobModel.class);
			descriptionJob.setProductsList(products);
			descriptionJob.setSessionUser(userService.getCurrentUser());
			descriptionJob.setSessionCurrency(commonI18NService.getCurrentCurrency());
			descriptionJob.setSessionLanguage(commonI18NService.getCurrentLanguage());
			modelService.save(descriptionJob);
			cronJobService.performCronJob(descriptionJob);
			modelService.refresh(descriptionJob);
			LOG.debug("performProductDescriptionJob | END ");
			return descriptionJob.getCode();
		}
		catch (Exception ex)
		{
			LOG.error("Error during triggering product description cronjob : {}", ex.getMessage());
			return null;
		}
	}

	@Required
	public void setMediaService(final MediaService mediaService)
	{
		this.mediaService = mediaService;
	}

	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	@Required
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}


}
