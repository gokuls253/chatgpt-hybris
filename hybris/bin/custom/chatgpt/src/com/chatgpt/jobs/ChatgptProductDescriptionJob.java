package com.chatgpt.jobs;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chatgpt.model.ChatgptProductDescriptonCronjobModel;
import com.chatgpt.service.ChatgptService;

/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company. All rights reserved.
 */


/**
 *
 */
public class ChatgptProductDescriptionJob extends AbstractJobPerformable<ChatgptProductDescriptonCronjobModel>
{
	private static final Logger LOG = LoggerFactory.getLogger(ChatgptProductDescriptionJob.class);

	@Resource(name = "chatgptService")
	private ChatgptService chatgptService;

	@Override
	public PerformResult perform(final ChatgptProductDescriptonCronjobModel job)
	{
		LOG.info("ChatgptProductDescriptionJob | START");
		try
		{
			List<ProductModel> productList = null;
			//Product List should be populated either through query for manual runs or list of products from backoffice list view action
			if (StringUtils.isNotEmpty(job.getSearchQuery()))
			{
				final String query = job.getSearchQuery();
				final FlexibleSearchQuery fsQuery = new FlexibleSearchQuery(query);
				final SearchResult<ProductModel> result = flexibleSearchService.search(fsQuery);
				productList = result.getResult();
			}
			else
			{
				productList = job.getProductsList();
			}
			final boolean result = chatgptService.generateProductDescription(productList);
			LOG.info("ChatgptProductDescriptionJob | END");
			if (result)
			{
				return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
			}
			else
			{
				return new PerformResult(CronJobResult.FAILURE, CronJobStatus.ABORTED);
			}
		}
		catch (final Exception ex)
		{
			LOG.error("Error while generating chatgpt product description for list of products" + job.getCode() + " " + ex);
			return new PerformResult(CronJobResult.FAILURE, CronJobStatus.ABORTED);
		}
	}

}
