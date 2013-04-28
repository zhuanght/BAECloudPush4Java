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

/**
 *
 * <p>连接选项，包括连接超时时间和读取超时时间，单位是毫秒
 * 默认值是连接超时5000毫秒，读取超时30000毫秒</p>
 * 
 * @author zhuanght(zhuang.hai.tao@163.com)
 * @version 0.0.1
 */
public class ConnectionOption {
    public ConnectionOption(int optTimeout, int connectionTimeout) {
        this.optTimeout = optTimeout;
        this.connectionTimeout = connectionTimeout;
    }
    
    static {
        DEFAULT = new ConnectionOption(30000, 5000);
    }
    
    public final int optTimeout;
    public final int connectionTimeout;
    
    public static final ConnectionOption DEFAULT;
}
