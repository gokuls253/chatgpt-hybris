/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.chatgpt.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ai.chatgpt.constants.AifeaturesConstants;


/**
 *
 */
public class ChatGPTClient
{
	private static final Logger LOG = Logger.getLogger(ChatGPTClient.class);

	private String apiKey;

	private int connectionTimeOut;

	private int readTimeOut;

	private static final String BLANK = "";

	/**
	 * Do request.
	 *
	 * @param method
	 *           the method
	 * @param requestUrl
	 *           the requestUrl
	 * @param requestJSON
	 *           the request JSON
	 * @return the string
	 */
	public String doRequest(final String method, final String requestUrl, final String requestJSON,
			final Map<String, Object> params)
	{
		// Create authentication
		final String apiKey = getApiKey();

		HttpsURLConnection connection = null;

		try
		{
			//Pass the request parameters
			StringBuilder postData = null;
			if (params != null)
			{
				postData = new StringBuilder();
				for (final Map.Entry<String, Object> item : params.entrySet())
				{
					if (postData.length() == 0)
					{
						postData.append('?');
					}
					if (postData.length() != 1)
					{
						postData.append('&');
					}
					postData.append(URLEncoder.encode(item.getKey(), "UTF-8"));
					postData.append('=');
					postData.append(URLEncoder.encode(String.valueOf(item.getValue()), "UTF-8"));
				}
			}
			String url = StringUtils.EMPTY;
			if (postData != null)
			{
				url = String.join(BLANK, postData.toString());
			}
			// Establish connectivity
			final URL urlObject = new URL(url);
			connection = (HttpsURLConnection) urlObject.openConnection();

			// Set timeouts
			connection.setConnectTimeout(getConnectionTimeOut());
			connection.setReadTimeout(getReadTimeOut());

			// Disable caching
			connection.setUseCaches(false);

			// Set request method
			connection.setRequestMethod(method);

			// Add general request properties
			connection.setRequestProperty(HttpHeaders.AUTHORIZATION, AifeaturesConstants.BEARER + apiKey);
			connection.setRequestProperty(HttpHeaders.CACHE_CONTROL, AifeaturesConstants.NO_CACHE);
			connection.setRequestProperty(HttpHeaders.CONTENT_TYPE, AifeaturesConstants.JSON_UTF_8);

			// Enable writing to resource and disable any user interactions
			connection.setDoOutput(true);
			connection.setAllowUserInteraction(false);

			// Log request message
			if (LOG.isDebugEnabled())
			{
				LOG.debug("Request : " + requestJSON);
			}

			// Write to the resource
			if (requestJSON != null)
			{
				final PrintStream printStream = new PrintStream(connection.getOutputStream());
				printStream.print(requestJSON);
				printStream.close();
			}


			// Retrieve the response code and message
			final int responseCode = connection.getResponseCode();
			final String message = connection.getResponseMessage();

			// Log response code and message
			if (!Arrays.asList(Integer.valueOf(HttpURLConnection.HTTP_OK), Integer.valueOf(HttpURLConnection.HTTP_CREATED))
					.contains(Integer.valueOf(responseCode)))
			{
				LOG.error("The ChatGPT server returned with the response code " + responseCode + " and with message " + message);
			}
			else
			{
				LOG.info("The ChatGPT server returned with the response code " + responseCode + " and with message " + message);
			}

			// Read response
			final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
					responseCode < HttpURLConnection.HTTP_BAD_REQUEST ? connection.getInputStream() : connection.getErrorStream()));
			String inputLine;
			final StringBuilder response = new StringBuilder();

			while ((inputLine = bufferedReader.readLine()) != null)
			{
				response.append(inputLine);
			}

			bufferedReader.close();

			// Log response message
			if (LOG.isDebugEnabled())
			{
				LOG.debug("ChatGPT Server Response : " + response.toString());
			}

			return response.toString();
		}
		catch (final Exception e)
		{
			LOG.error("Exception occured in ChatGPT API call", e);
			return StringUtils.EMPTY;
		}
		finally
		{
			if (connection != null)
			{
				connection.disconnect();
			}
		}

	}

	public String getApiKey()
	{
		return apiKey;
	}

	public void setApiKey(final String authToken)
	{
		this.apiKey = authToken;
	}


	public int getConnectionTimeOut()
	{
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(final int connectionTimeOut)
	{
		this.connectionTimeOut = connectionTimeOut;
	}

	public int getReadTimeOut()
	{
		return readTimeOut;
	}

	public void setReadTimeOut(final int readTimeOut)
	{
		this.readTimeOut = readTimeOut;
	}


}
