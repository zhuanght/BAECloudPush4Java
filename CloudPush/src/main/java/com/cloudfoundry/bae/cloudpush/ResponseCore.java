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

import java.util.Map;

/**
 *
 * <p>响应类</p>
 * 
 * @author zhuanght(zhuang.hai.tao@163.com)
 * @version 0.0.1
 */
public class ResponseCore {
    /**
	 * Constructs a new instance of this class.
	 *
	 * @param Map<String, String> header (Required) Associative map of HTTP headers ).
	 * @param String body (Required) JSON-formatted response from AWS.
	 * @param int status (Optional) HTTP response status code from the request.
	 */
    public ResponseCore(Map<String, String> header, String body, int status) {
        this.header = header;
        this.body = body;
        this.status = status;
    }
    
    /**
	 * Stores the HTTP header information.
	 */
    public final Map<String, String> header;
    /**
	 * Stores the SimpleXML response.
	 */
    public final String body;
    /**
	 * Stores the HTTP response code.
	 */
    public int status;
    
    /**
	 * Did we receive the status code we expected?
	 *
	 * @param integer|array codes (Optional) The status code(s) to expect. Pass an int for a single acceptable value, or an array of integers for multiple acceptable values.
	 * @return boolean Whether we received the expected status code or not.
	 */
    public boolean isOK(int... codes) {
       if (codes == null || codes.length == 0) {
           codes = new int[]{200, 201, 204, 206};
       }
        boolean isOK = false;
        for (int code : codes) {
            if (status == code) {
                isOK = true;
            }
        }
      
        return isOK;
    }
    
    
}
