/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.cloudfoundry.bae.cloudpush;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * 本文件百度云服务Java版本SDK的公共网络交互功能
 *
 * @author zhuanght(zhuang.hai.tao@163.com)
 * @version 0.0.1
 */
public class RequestCore {

    private static final Log logger = LogFactory.getLog(RequestCore.class);
    /**
     * The URL being requested.
     */
    private String requestUrl;
    /**
     * The headers being sent in the request.
     */
    private Map<String, String> requestHeaders;
    /**
     * The headers being sent in the request.
     */
    private String requestBody;
    /**
     * The headers returned by the request.
     */
    private Map<String, String> responseHeaders;
    /**
     * The body returned by the request.
     */
    private String responseBody;
    /**
     * The HTTP status code returned by the request.
     */
    private int responseCode;
    /**
     * The method by which the request is being made.
     */
    private HttpMethod method;
    /**
     * Custom CURLOPT settings.
     */
    private ConnectionOption connectionOption = null;
    /**
     * Default useragent string to use.
     */
    private String useragent = "RequestCore/1.4.2";
    /**
     * GET HTTP Method
     */
    public final static String HTTP_GET = "GET";
    /**
     * POST HTTP Method
     */
    public final static String HTTP_POST = "POST";
    /**
     * PUT HTTP Method
     */
    public final static String HTTP_PUT = "PUT";
    /**
     * DELETE HTTP Method
     */
    public final static String HTTP_DELETE = "DELETE";
    /**
     * HEAD HTTP Method
     */
    public final static String HTTP_HEAD = "HEAD";

    /**
     * Constructs a new instance of this class.
     *
     * @param string $url (Optional) The URL to request or service endpoint to query.
     */
    public RequestCore(String url) {
        this.requestUrl = url;
        this.method = HttpMethod.HTTP_GET;
        this.requestHeaders = new HashMap<String, String>();
        this.requestBody = "";
    }

    /**
     * Adds a custom HTTP header to the request.
     *
     * @param string key (Required) The custom HTTP header to set.
     * @param mixed value (Required) The value to assign to the custom HTTP header.
     * @return this A reference to the current instance.
     */
    public RequestCore addHeader(String headerKey, String headerValue) {
        this.requestHeaders.put(headerKey, headerValue);
        return this;
    }

    /**
     * Removes an HTTP header from the request.
     *
     * @param string key (Required) The custom HTTP header to set.
     * @return this A reference to the current instance.
     */
    public RequestCore removeHeader(String key) {
        if (requestHeaders.containsKey(key)) {
            requestHeaders.remove(key);
        }

        return this;
    }

    /**
     * Set the method type for the request.
     *
     * @param string method (Required) One of the following constants: HTTP_GET, HTTP_POST, HTTP_PUT, HTTP_HEAD,
     * HTTP_DELETE.
     * @return this A reference to the current instance.
     */
    public RequestCore setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    /**
     * Sets a custom useragent string for the class.
     *
     * @param string ua (Required) The useragent string to use.
     * @return this A reference to the current instance.
     */
    public RequestCore setUseragent(String ua) {
        this.useragent = ua;
        return this;
    }

    /**
     * Set the body to send in the request.
     *
     * @param string body (Required) The textual content to send along in the body of the request.
     * @return this A reference to the current instance.
     */
    public RequestCore setBody(String body) {
        this.requestBody = body;
        return this;
    }

    /**
     * Set additional CURLOPT settings. These will merge with the default settings, and override if there is a
     * duplicate.
     *
     * @param ConnectionOption connOption(Optional) A set connection options.
     * @return this A reference to the current instance.
     */
    public RequestCore setConnectionOption(ConnectionOption connOption) {
        this.connectionOption = connOption;
        return this;
    }

    /**
     * Set the URL to make the request to.
     *
     * @param string url (Required) The URL to make the request to.
     * @return $this A reference to the current instance.
     */
    public RequestCore setRequestUrl(String url) {
        this.requestUrl = url;
        return this;
    }

    /**
     * Sends the request, calling necessary utility functions to update built-in properties.
     *
     * @return string The resulting unparsed data from the request.
     */
    public void sendRequest() {
        executeRequest();
    }

    private void executeRequest() {
        HttpClient client = new HttpClient();
        HttpConnectionManagerParams hcManagerParams = client.getHttpConnectionManager().getParams();
        hcManagerParams.setConnectionTimeout(connectionOption.connectionTimeout);
        hcManagerParams.setSoTimeout(connectionOption.optTimeout);
        PostMethod post = new PostMethod(requestUrl);
        
        if (requestHeaders != null && !requestHeaders.isEmpty()) {
            Set<String> headerKeySet = requestHeaders.keySet();
            for (String headerKey : headerKeySet) {
                String headerValue = requestHeaders.get(headerKey);
                post.addRequestHeader(headerKey, headerValue);
            }
        }        
        
        post.setRequestEntity(new StringRequestEntity(requestBody));
        try { 
            responseCode = client.executeMethod(post);
            responseBody = post.getResponseBodyAsString();
            
            logger.info(responseBody);
            
            Header[] rspHeaders = post.getResponseHeaders();
            responseHeaders = new HashMap<String, String>();
            for (Header rspHeader : rspHeaders) {
                responseHeaders.put(rspHeader.getName(), rspHeader.getValue());
            }
        } catch (IOException ex) {
            logger.error(ex);
        }
    }


    /*%******************************************************************************************%*/
    // RESPONSE METHODS
    /**
     * Get the HTTP response headers from the request.
     *
     * @param string header (Optional) A specific header value to return. Defaults to all headers.
     * @return Map<String, String> All or selected header values.
     */
    public Map<String, String> getResponseHeader() {
        return Collections.unmodifiableMap(this.responseHeaders);
    }

    public Map<String, String> getResponseHeader(String header) {
        return Collections.singletonMap(header, this.responseHeaders.get(header));
    }

    /**
     * Get the HTTP response body from the request.
     *
     * @return string The response body.
     */
    public String getResponseBody() {
        return this.responseBody;
    }

    /**
     * Get the HTTP response code from the request.
     *
     * @return string The HTTP response code.
     */
    public int getResponseCode() {
        return this.responseCode;
    }
}

/**
 * HTTP method enum
 *
 * @author zhuanght
 */
enum HttpMethod {

    HTTP_GET("GET"), HTTP_POST("POST"), HTTP_PUT("PUT"), HTTP_HEAD("HEAD"), HTTP_DELETE("DELETE");

    private HttpMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return method;
    }
    private String method;
}
