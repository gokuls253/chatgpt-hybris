/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.chatgpt.dto;

import java.util.LinkedHashMap;
import java.util.List;
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
{ "messages", "temperature", "max_tokens", "model" })
public class ChatgptGenerateProductDescriptionRequest
{

	@JsonProperty("messages")
	@Valid
	private List<Message> messages;

	@JsonProperty("temperature")
	private Double temperature;

	@JsonProperty("max_tokens")
	private Integer maxTokens;

	@JsonProperty("model")
	private String model;

	@JsonIgnore
	@Valid
	private final Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public ChatgptGenerateProductDescriptionRequest()
	{
	}

	/**
	 *
	 * @param temperature
	 * @param maxTokens
	 * @param messages
	 * @param model
	 */
	public ChatgptGenerateProductDescriptionRequest(final List<Message> messages, final Double temperature,
			final Integer maxTokens, final String model)
	{
		super();
		this.messages = messages;
		this.temperature = temperature;
		this.maxTokens = maxTokens;
		this.model = model;
	}

	@JsonProperty("messages")
	public List<Message> getMessages()
	{
		return messages;
	}

	@JsonProperty("messages")
	public void setMessages(final List<Message> messages)
	{
		this.messages = messages;
	}

	@JsonProperty("temperature")
	public Double getTemperature()
	{
		return temperature;
	}

	@JsonProperty("temperature")
	public void setTemperature(final Double temperature)
	{
		this.temperature = temperature;
	}

	@JsonProperty("max_tokens")
	public Integer getMaxTokens()
	{
		return maxTokens;
	}

	@JsonProperty("max_tokens")
	public void setMaxTokens(final Integer maxTokens)
	{
		this.maxTokens = maxTokens;
	}

	@JsonProperty("model")
	public String getModel()
	{
		return model;
	}

	@JsonProperty("model")
	public void setModel(final String model)
	{
		this.model = model;
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
