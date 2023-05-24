/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.chatgpt.dto;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "message", "finish_reason", "index" })
public class Choice
{

	@JsonProperty("message")
	@Valid
	private Message message;
	@JsonProperty("finish_reason")
	private String finishReason;
	@JsonProperty("index")
	private Integer index;
	@JsonIgnore
	@Valid
	private final Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Choice()
	{
	}

	/**
	 *
	 * @param finishReason
	 * @param index
	 * @param message
	 */
	public Choice(final Message message, final String finishReason, final Integer index)
	{
		super();
		this.message = message;
		this.finishReason = finishReason;
		this.index = index;
	}

	@JsonProperty("message")
	public Message getMessage()
	{
		return message;
	}

	@JsonProperty("message")
	public void setMessage(final Message message)
	{
		this.message = message;
	}

	@JsonProperty("finish_reason")
	public String getFinishReason()
	{
		return finishReason;
	}

	@JsonProperty("finish_reason")
	public void setFinishReason(final String finishReason)
	{
		this.finishReason = finishReason;
	}

	@JsonProperty("index")
	public Integer getIndex()
	{
		return index;
	}

	@JsonProperty("index")
	public void setIndex(final Integer index)
	{
		this.index = index;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties()
	{
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(final String name, final Object value)
	{
		this.additionalProperties.put(name, value);
	}

}
