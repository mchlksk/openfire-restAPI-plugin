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
        MessageEntity msg = new MessageEntity();
        msg.setBody(testResult);
        return msg;
        //return Response.status(Status.OK).build();
    }


    @POST
    @Path("/iq")
    @Operation( summary = "Route an IQ packet",
        description = "Route an IQ packet.",
        responses = {
            @ApiResponse(responseCode = "200", description = "The IQ packet has been added to processing queue."),
            @ApiResponse(responseCode = "400", description = "Invalid IQ packet."),
            @ApiResponse(responseCode = "500", description = "The IQ packet failed to process due to internal server error.")
        })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response routeIq(
        @RequestBody(description = "An IQ packet.", required = true) String packetContent)
        throws ServiceException
    {
        packetController.routeIq(packetContent);
        return Response.status(Status.OK).build();
    }
}
