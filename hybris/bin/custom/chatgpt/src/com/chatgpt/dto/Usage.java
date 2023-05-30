/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.chatgpt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "prompt_tokens", "completion_tokens", "total_tokens" })
public class Usage
{

	@JsonProperty("prompt_tokens")
	private Integer promptTokens;

	@JsonProperty("completion_tokens")
	private Integer completionTokens;

	@JsonProperty("total_tokens")
	private Integer totalTokens;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Usage()
	{

	}

	/**
	 *
	 * @param promptTokens
	 * @param completionTokens
	 * @param totalTokens
	 */
	public Usage(Integer promptTokens, Integer completionTokens, Integer totalTokens)
	{
		super();
		this.promptTokens = promptTokens;
		this.completionTokens = completionTokens;
		this.totalTokens = totalTokens;
	}

	/**
	 * @return the promptTokens
	 */
	@JsonProperty("prompt_tokens")
	public Integer getPromptTokens()
	{
		return promptTokens;
	}

	/**
	 * @param promptTokens
	 *           the promptTokens to set
	 */
	@JsonProperty("prompt_tokens")
	public void setPromptTokens(Integer promptTokens)
	{
		this.promptTokens = promptTokens;
	}

	/**
	 * @return the completionTokens
	 */
	@JsonProperty("completion_tokens")
	public Integer getCompletionTokens()
	{
		return completionTokens;
	}

	/**
	 * @param completionTokens
	 *           the completionTokens to set
	 */
	@JsonProperty("completion_tokens")
	public void setCompletionTokens(Integer completionTokens)
	{
		this.completionTokens = completionTokens;
	}

	/**
	 * @return the totalTokens
	 */
	@JsonProperty("total_tokens")
	public Integer getTotalTokens()
	{
		return totalTokens;
	}

	/**
	 * @param totalTokens
	 *           the totalTokens to set
	 */
	@JsonProperty("total_tokens")
	public void setTotalTokens(Integer totalTokens)
	{
		this.totalTokens = totalTokens;
	}




}
