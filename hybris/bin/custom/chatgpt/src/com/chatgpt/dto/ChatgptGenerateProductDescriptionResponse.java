/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.chatgpt.dto;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "id", "object", "created", "model", "usage", "choices" })
public class ChatgptGenerateProductDescriptionResponse
{

	@JsonProperty("id")
	private String id;
	@JsonProperty("object")
	private String object;
	@JsonProperty("created")
	private Integer created;
	@JsonProperty("model")
	private String model;
	@JsonProperty("usage")
	@Valid
	private Usage usage;
	@JsonProperty("choices")
	@Valid
	private List<Choice> choices;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public ChatgptGenerateProductDescriptionResponse()
	{
	}

	/**
	 *
	 * @param created
	 * @param usage
	 * @param model
	 * @param id
	 * @param choices
	 * @param object
	 */
	public ChatgptGenerateProductDescriptionResponse(final String id, final String object, final Integer created,
			final String model, final Usage usage, final List<Choice> choices)
	{
		super();
		this.id = id;
		this.object = object;
		this.created = created;
		this.model = model;
		this.usage = usage;
		this.choices = choices;
	}

	@JsonProperty("id")
	public String getId()
	{
		return id;
	}

	@JsonProperty("id")
	public void setId(final String id)
	{
		this.id = id;
	}

	@JsonProperty("object")
	public String getObject()
	{
		return object;
	}

	@JsonProperty("object")
	public void setObject(final String object)
	{
		this.object = object;
	}

	@JsonProperty("created")
	public Integer getCreated()
	{
		return created;
	}

	@JsonProperty("created")
	public void setCreated(final Integer created)
	{
		this.created = created;
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

	@JsonProperty("usage")
	public Usage getUsage()
	{
		return usage;
	}

	@JsonProperty("usage")
	public void setUsage(final Usage usage)
	{
		this.usage = usage;
	}

	@JsonProperty("choices")
	public List<Choice> getChoices()
	{
		return choices;
	}

	@JsonProperty("choices")
	public void setChoices(final List<Choice> choices)
	{
		this.choices = choices;
	}

}