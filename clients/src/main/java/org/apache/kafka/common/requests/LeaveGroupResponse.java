/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.apache.kafka.common.requests;

import org.apache.kafka.common.protocol.ApiKeys;
import org.apache.kafka.common.protocol.Errors;
import org.apache.kafka.common.protocol.types.Struct;

import java.nio.ByteBuffer;

public class LeaveGroupResponse extends AbstractResponse {

    private static final String ERROR_CODE_KEY_NAME = "error_code";

    /**
     * Possible error code:
     *
     * GROUP_LOAD_IN_PROGRESS (14)
     * CONSUMER_COORDINATOR_NOT_AVAILABLE (15)
     * NOT_COORDINATOR_FOR_CONSUMER (16)
     * UNKNOWN_CONSUMER_ID (25)
     * GROUP_AUTHORIZATION_FAILED (30)
     */
    private final Errors error;

    public LeaveGroupResponse(Errors error) {
        this.error = error;
    }

    public LeaveGroupResponse(Struct struct) {
        error = Errors.forCode(struct.getShort(ERROR_CODE_KEY_NAME));
    }

    public Errors error() {
        return error;
    }

    @Override
    public Struct toStruct(short version) {
        Struct struct = new Struct(ApiKeys.LEAVE_GROUP.responseSchema(version));
        struct.set(ERROR_CODE_KEY_NAME, error.code());
        return struct;
    }

    public static LeaveGroupResponse parse(ByteBuffer buffer, short versionId) {
        return new LeaveGroupResponse(ApiKeys.LEAVE_GROUP.parseResponse(versionId, buffer));
    }

}
