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

//import org.xmpp.packet.JID;
import org.xmpp.packet.*; // IQ


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
}
