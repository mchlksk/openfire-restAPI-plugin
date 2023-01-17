/*
 * Copyright (c) 2022.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jivesoftware.openfire.plugin.rest.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import org.xmpp.packet.JID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "result")
@XmlType(propOrder = { "nodeId", "resultType", "message"})
public class NodeOperationResultEntity {

    public enum NodeOperationResultType {
        Success, Failure
    }

    String nodeId;
    NodeOperationResultType resultType;
    String message;

    @XmlElement(name = "nodeId")
    @Schema(description = "The id of the node that was to be created", example = "pubsub/shakespeare/lit/moorish_meanderings")
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String id) {
        this.nodeId = id;
    }

    @XmlElement(name = "resultType")
    @Schema(description = "The result of creating the node", example = "Failure")
    public NodeOperationResultType getResultType() {
        return resultType;
    }

    public void setResultType(NodeOperationResultType resultType) {
        this.resultType = resultType;
    }

    @XmlElement(name = "message")
    @Schema(description = "A message describing the result", example = "Node already existed and therefore not created again")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
