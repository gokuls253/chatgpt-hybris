/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.chatgpt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "role", "content" })
public class Message
{

	@JsonProperty("role")
	private String role;

	@JsonProperty("content")
	private String content;


	/**
	 * No args constructor for use in serialization
	 *
	 */
	public Message()
	{
	}

	/**
	 *
	 * @param role
	 * @param content
	 */
	public Message(final String role, final String content)
	{
		super();
		this.role = role;
		this.content = content;
	}

	@JsonProperty("role")
	public String getRole()
	{
		return role;
	}

	@JsonProperty("role")
	public void setRole(final String role)
	{
		this.role = role;
	}

	@JsonProperty("content")
	public String getContent()
	{
		return content;
	}

	@JsonProperty("content")
	public void setContent(final String content)
	{
		this.content = content;
	}

}