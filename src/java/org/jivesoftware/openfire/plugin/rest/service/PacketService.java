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

import org.jivesoftware.openfire.plugin.rest.controller.PacketController;
import org.jivesoftware.openfire.plugin.rest.entity.MessageEntity;

import java.util.*;

import org.jivesoftware.openfire.plugin.rest.exceptions.ServiceException;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


@Path("restapi/v1/packet")
@Tag(name="packet", description = "Managing Openfire Packets.")
public class PacketService {

    private PacketController packetController;

    @PostConstruct
    public void init() {
        packetController = PacketController.getInstance();
    }

    @GET
    @Path("/test01")
    @Operation( summary = "Test endpoint 01",
                description = "Test endpoint 01",
                responses = {
                    //@ApiResponse(responseCode = "200", description = "test01 request has been processed")
                    @ApiResponse(responseCode = "200", description = "test01 request has been processed.", content = @Content(schema = @Schema(implementation = MessageEntity.class)))
                })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    //public Response test01()
    public MessageEntity test01()
        throws ServiceException
    {
        String testResult = packetController.test01();
        //return Response.status(Status.OK).build();
        MessageEntity msg = new MessageEntity();
        msg.setBody(testResult);
        return msg;
    }


    /*
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
    @Operation( summary = "Create node(s)",
        description = "Create new PubSub node(s).",
        responses = {
            @ApiResponse(responseCode = "200", description = "Request has been processed. Results are reported in the response.", content = @Content(schema = @Schema(implementation = NodeOperationResultEntities.class)))
        })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public NodeOperationResultEntities addNodes(
        @Parameter(description = "The JID of the creator.", example = "hamlet@denmark.lit", required = true) @QueryParam("jid") String jid,
        @RequestBody(description = "The list of nodes that needs to be created.", required = true) NodeEntities nodeEntities)
        throws ServiceException
    {
        List<NodeOperationResultEntity> results = new ArrayList<NodeOperationResultEntity>();
        for(NodeEntity node : nodeEntities.getNodes())
        {
            results.add(pubSubController.addNode(node, jid));
        }
        return new NodeOperationResultEntities(results);
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

    @GET
    @Path("/test01")
    @Operation( summary = "Test endpoint 01",
                description = "Test endpoint 01",
                responses = {
                    //@ApiResponse(responseCode = "200", description = "test01 request has been processed")
                    @ApiResponse(responseCode = "200", description = "test01 request has been processed.", content = @Content(schema = @Schema(implementation = MessageEntity.class)))
                })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    //public Response test01()
    public MessageEntity test01()
        throws ServiceException
    {
        String testResult = pubSubController.test01();
        //return Response.status(Status.OK).build();
        MessageEntity msg = new MessageEntity();
        msg.setBody(testResult);
        return msg;
    }
    */

}
