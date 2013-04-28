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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author zhuanght(zhuang.hai.tao@163.com)
 * @version 0.0.1
 */
public class CloudPushTest {
    
    @Before
    public void setUp() {
        apiKey = "MFRUN68t8O33NZdDlUqHxi5Z";
        secretKey = "UVUNcKzQvPwiI8mQVxxj3MsdGwTirzOf";
        userId = "765522986755783217";
        channelID = "3947047891437553579";
    }
    
    @After
    public void finish() {
        // nothing
    }
    
    @Test
    public void queryBindList()  {
        Map<String, String> optional = new HashMap<String, String>();
        optional.put(Channel.CHANNEL_ID, channelID);
        
        Channel channel = new Channel();
        
        channel.initialize(apiKey, secretKey, null);
        JSONObject result = channel.queryBindList(userId, optional);
        if (channel.errcode ==0) {
            Assert.assertNotNull(result);
        }
        else {
            System.out.println("error_code: " + channel.errcode + "; error_msg: " + channel.errmsg);
        }
        
    }
    
    @Test
    public void pushMessage() {
        Map<String, String> optional = new HashMap<String, String>();
        optional.put(Channel.USER_ID, userId);
        optional.put(Channel.MESSAGE_TYPE, String.valueOf(1));
        
        Channel channel = new Channel();
        channel.initialize(apiKey, secretKey, null);
//       
        int pushType = 3; 
        String messages = "{ 'title': 'hello " + new Date() + "' , 'description': 'hello'}";
        String messageKey = "msg_key";
        
        JSONObject result = channel.pushMessage(pushType, messages, messageKey, optional);
        if (channel.errcode ==0) {
            Assert.assertNotNull(result);
            System.out.println(result);
        }
        else {
            System.out.println("error_code: " + channel.errcode + "; error_msg: " + channel.errmsg);
        }
    }
    
    private String apiKey;
    private String secretKey;
    private String userId;
    private String channelID;
}
