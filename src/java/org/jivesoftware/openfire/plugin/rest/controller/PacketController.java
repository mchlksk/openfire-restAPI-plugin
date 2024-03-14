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

package org.jivesoftware.openfire.plugin.rest.controller;

import java.util.*;
import javax.ws.rs.core.Response;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.dom4j.Element;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.util.UserDataElement;
import org.dom4j.QName;

import org.apache.commons.lang3.StringEscapeUtils;

import org.xmpp.packet.PacketError.Condition;

import org.xmpp.forms.DataForm;
import org.xmpp.forms.FormField;

import org.jivesoftware.openfire.XMPPServer;

import org.jivesoftware.openfire.plugin.rest.exceptions.ExceptionType;
import org.jivesoftware.openfire.plugin.rest.exceptions.ServiceException;

import org.xmpp.packet.*; // IQ, Message, Presence


/**
 * The Class PacketController.
 */
public class PacketController {
    /** The Constant INSTANCE. */
    public static final PacketController INSTANCE = new PacketController();
    
    /**
     * Gets the single instance of PacketController.
     *
     * @return single instance of PacketController
     */
    public static PacketController getInstance() {
        return INSTANCE;
    }
    

    /**
     * Test01.
     *
     */
    public String test01() {
        return "oukey doukey";
    }

    /**
     * routeIq.
     *
     * @param packetContent
     *            the IQ packet content in XML format
     * @return none
     * 
     * @throws ServiceException
     *             the service exception
     */
    public void routeIq(String packetContent, Boolean autoid) throws ServiceException {
        Document doc;
        try {
            doc = DocumentHelper.parseText(packetContent);
        } catch (Exception e) {
            throw new ServiceException("Could not deserialize given data as XML.", "PacketController", "BAD_REQUEST" , Response.Status.BAD_REQUEST, null);
        }
    
        IQ iq;
        try {
            iq = new IQ(doc.getRootElement());
        } catch (Exception e) {
            throw new ServiceException("Could not construct IQ object from the given data.", "PacketController", "BAD_REQUEST" , Response.Status.BAD_REQUEST, null);
        }
        
        if(autoid != null && autoid)
        {
            iq.setID(new IQ().getID());
        }
        
        XMPPServer server = XMPPServer.getInstance();
        if(server == null)
            throw new ServiceException("Could not get instance of XMPP server.", "PacketController", "INTERNAL_SERVER_ERROR" , Response.Status.INTERNAL_SERVER_ERROR, null);
        
        try {
            server.getPacketRouter().route(iq);
        } catch (Exception e) {
            throw new ServiceException("XMPP server packet router failed to route the IQ packet.", "PacketController", "INTERNAL_SERVER_ERROR" , Response.Status.INTERNAL_SERVER_ERROR, null);
        }
    }


    /**
     * routeMessage.
     *
     * @param packetContent
     *            the message packet content in XML format
     * @return none
     * 
     * @throws ServiceException
     *             the service exception
     */
    public void routeMessage(String packetContent, Boolean autoid) throws ServiceException {
        Document doc;
        try {
            doc = DocumentHelper.parseText(packetContent);
        } catch (Exception e) {
            throw new ServiceException("Could not deserialize given data as XML.", "PacketController", "BAD_REQUEST" , Response.Status.BAD_REQUEST, null);
        }
    
        Message message;
        try {
            message = new Message(doc.getRootElement());
        } catch (Exception e) {
            throw new ServiceException("Could not construct Message object from the given data.", "PacketController", "BAD_REQUEST" , Response.Status.BAD_REQUEST, null);
        }

        if(autoid != null && autoid)
        {
            // take advantage of ID generating ctor of IQ
            message.setID(new IQ().getID());
        }
        
        XMPPServer server = XMPPServer.getInstance();
        if(server == null)
            throw new ServiceException("Could not get instance of XMPP server.", "PacketController", "INTERNAL_SERVER_ERROR" , Response.Status.INTERNAL_SERVER_ERROR, null);
        
        try {
            server.getPacketRouter().route(message);
        } catch (Exception e) {
            throw new ServiceException("XMPP server packet router failed to route the message packet.", "PacketController", "INTERNAL_SERVER_ERROR" , Response.Status.INTERNAL_SERVER_ERROR, null);
        }
    }



    /**
     * routePresence.
     *
     * @param packetContent
     *            the presence packet content in XML format
     * @return none
     * 
     * @throws ServiceException
     *             the service exception
     */
    public void routePresence(String packetContent, Boolean autoid) throws ServiceException {
        Document doc;
        try {
            doc = DocumentHelper.parseText(packetContent);
        } catch (Exception e) {
            throw new ServiceException("Could not deserialize given data as XML.", "PacketController", "BAD_REQUEST" , Response.Status.BAD_REQUEST, null);
        }
    
        Presence presence;
        try {
            presence = new Presence(doc.getRootElement());
        } catch (Exception e) {
            throw new ServiceException("Could not construct Presence object from the given data.", "PacketController", "BAD_REQUEST" , Response.Status.BAD_REQUEST, null);
        }

        if(autoid != null && autoid)
        {
            // take advantage of ID generating ctor of IQ
            presence.setID(new IQ().getID());
        }
        
        XMPPServer server = XMPPServer.getInstance();
        if(server == null)
            throw new ServiceException("Could not get instance of XMPP server.", "PacketController", "INTERNAL_SERVER_ERROR" , Response.Status.INTERNAL_SERVER_ERROR, null);
        
        try {
            server.getPacketRouter().route(presence);
        } catch (Exception e) {
            throw new ServiceException("XMPP server packet router failed to route the presence packet.", "PacketController", "INTERNAL_SERVER_ERROR" , Response.Status.INTERNAL_SERVER_ERROR, null);
        }
    }

}
