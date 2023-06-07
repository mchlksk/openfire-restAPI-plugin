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

package org.jivesoftware.openfire.plugin.rest.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.jivesoftware.openfire.plugin.rest.controller.PubSubController;
import org.jivesoftware.openfire.plugin.rest.entity.NodeEntity;
import org.jivesoftware.openfire.plugin.rest.entity.NodeEntities;
import org.jivesoftware.openfire.plugin.rest.entity.NodeOperationResultEntity;
import org.jivesoftware.openfire.plugin.rest.entity.PublishItemEntity;

import java.util.*;

import org.jivesoftware.openfire.plugin.rest.exceptions.ServiceException;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("restapi/v1/pubsub")
@Tag(name="PubSub", description = "Managing Openfire PubSubs.")
public class PubSubService {

    private PubSubController pubSubController;

    @PostConstruct
    public void init() {
        pubSubController = PubSubController.getInstance();
    }

    @GET
    @Operation( summary = "Get child nodes",
                description = "Get a list of all PubSub nodes that are children to the given parent node. If parent is not provided (or empty), the invisible root collection node is used as parent.",
                responses = {
                    @ApiResponse(responseCode = "200", description = "PubSub nodes", content = @Content(schema = @Schema(implementation = NodeEntities.class))),
                    @ApiResponse(responseCode = "404", description = "Parent node with this id not found.")
                })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public NodeEntities getNodes(
        @Parameter(description = "The ID of the parent collection node, whom children are reqeusted.", example = "pubsub/shakespeare/lit/moorish_meanderings", required = true) @QueryParam("parent") String parent)
        throws ServiceException
    {
        final String parentSafe = (parent == null) ? "" : parent; 
        return new NodeEntities(pubSubController.getNodes(parentSafe));
    }

    @POST
    @Operation( summary = "Create node",
        description = "Create a new PubSub node.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Request has been processed. Results are reported in the response.", content = @Content(schema = @Schema(implementation = NodeOperationResultEntity.class)))
        })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public NodeOperationResultEntity addNode(
        @Parameter(description = "The JID of the creator.", example = "hamlet@denmark.lit", required = true) @QueryParam("jid") String jid,
        @RequestBody(description = "The node that needs to be created.", required = true) NodeEntity nodeEntity)
        throws ServiceException
    {
        return pubSubController.addNode(nodeEntity, jid);
    }
    
    @DELETE
    @Path("/{id}")
    @Operation( summary = "Delete PubSub node",
        description = "Removes an existing PubSub node.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Request has been processed. Results are reported in the response.", content = @Content(schema = @Schema(implementation = NodeOperationResultEntity.class)))
        })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public NodeOperationResultEntity deleteNode(
        @Parameter(description = "The JID of the operator who commands node deletion.", example = "hamlet@denmark.lit", required = true) @QueryParam("jid") String jid,
        @Parameter(description = "Whether the published items need to be purged before node deletion.", example = "true", required = false) @QueryParam("purge") boolean purge,
        @Parameter(description = "The ID of the node that needs to be deleted.", example = "pubsub/shakespeare/lit/moorish_meanderings", required = true) @PathParam("id") String id)
        throws ServiceException
    {                                          
        return pubSubController.deleteNode(id, jid, purge);
    }

    @POST
    @Path("/{id}")
    @Operation( summary = "Publish item to PubSub",
        description = "Publish an item to a PubSub.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Request has been processed. Results are reported in the response.", content = @Content(schema = @Schema(implementation = NodeOperationResultEntity.class)))
        })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public NodeOperationResultEntity publish(
        @Parameter(description = "The ID of the node where the item needs to be published.", example = "pubsub/shakespeare/lit/moorish_meanderings", required = true) @PathParam("id") String id,
        @RequestBody(description = "Publish item.", required = true) PublishItemEntity publishItem)
        throws ServiceException
    {                                          
        return pubSubController.publishItem(id, publishItem);
    }
}
