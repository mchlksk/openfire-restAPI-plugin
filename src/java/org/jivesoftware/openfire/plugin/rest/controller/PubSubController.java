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

import org.dom4j.Element;
import org.dom4j.util.UserDataElement;
import org.dom4j.QName;

import org.apache.commons.lang3.StringEscapeUtils;

import org.xmpp.packet.PacketError.Condition;

import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.pubsub.PubSubInfo;
import org.jivesoftware.openfire.pubsub.PubSubService;
import org.jivesoftware.openfire.pubsub.PubSubModule;
import org.jivesoftware.openfire.pubsub.PubSubEngine;
import org.jivesoftware.openfire.pubsub.Node;
import org.jivesoftware.openfire.pubsub.LeafNode;
import org.jivesoftware.openfire.pubsub.CollectionNode;
import org.jivesoftware.openfire.pubsub.DefaultNodeConfiguration;

import org.jivesoftware.openfire.plugin.rest.entity.NodeEntity;
import org.jivesoftware.openfire.plugin.rest.entity.NodeOperationResultEntity;

import org.jivesoftware.openfire.plugin.rest.exceptions.ExceptionType;
import org.jivesoftware.openfire.plugin.rest.exceptions.ServiceException;

import org.xmpp.packet.JID;

/**
 * The Class PubSubController.
 */
public class PubSubController {
    /** The Constant INSTANCE. */
    public static final PubSubController INSTANCE = new PubSubController();
    
    /**
     * Gets the single instance of PubSubController.
     *
     * @return single instance of PubSubController
     */
    public static PubSubController getInstance() {
        return INSTANCE;
    }

    /**
     * Gets the nodes.
     *
     * @return the nodes
     * @throws ServiceException
     *             the service exception
     */
    public List<NodeEntity> getNodes(String targetParentId) throws ServiceException {
        Collection<Node> nodes = XMPPServer.getInstance().getPubSubModule().getNodes();
        List<NodeEntity> nodeEntities = new ArrayList<>();
        for(Node node : nodes)
        {
            final CollectionNode parent = node.getParent();

            final String parentId = (parent == null || parent.isRootCollectionNode())
                ? ""
                : parent.getNodeID();

            if(!parentId.equals(targetParentId))
                continue;
            
            nodeEntities.add(new NodeEntity(node.getNodeID(), node.getName(), parentId, !node.isCollectionNode()));
        }
        return nodeEntities;
    }
    
    /**
     * Create a new node.
     *
     * @return NodeOperationResultEntity
     *             the node creation result
     * @throws ServiceException
     *             the service exception
     */
    public NodeOperationResultEntity addNode(NodeEntity nodeEntity, String creatorJid) throws ServiceException {

        NodeOperationResultEntity result = new NodeOperationResultEntity();
        result.setNodeId(nodeEntity.getId());

        XMPPServer xmppServer = XMPPServer.getInstance();
        PubSubModule pubSubModule = xmppServer.getPubSubModule();

        Element configureElement = new UserDataElement("configure");
        Element formElement = configureElement.addElement(QName.get("x", "jabber:x:data"));
        
        if(!nodeEntity.getParent().isEmpty())
        {
            Element collectionElement = formElement.addElement("field");
            collectionElement.addAttribute("var", "pubsub#collection");
            Element collectionValue = collectionElement.addElement("value");
            collectionValue.setText(nodeEntity.getParent());
        }
        
        Element typeElement = formElement.addElement("field");
        typeElement.addAttribute("var", "pubsub#node_type");
        Element typeValue = typeElement.addElement("value");
        if(nodeEntity.getLeaf())
            typeValue.setText("leaf");
        else
            typeValue.setText("collection");
        
        PubSubEngine.CreateNodeResponse engineResponse = PubSubEngine.createNodeHelper(pubSubModule, new JID(creatorJid), configureElement, nodeEntity.getId(), null);
        
        if(engineResponse.creationStatus != null
            || engineResponse.pubsubError != null)
        {
            result.setResultType(NodeOperationResultEntity.NodeOperationResultType.Failure);
            String message = new String("Node creation failed");
            
            if(engineResponse.creationStatus != null)
                message = message + ", PubSubEngine response code: "+engineResponse.creationStatus.toString();
            if(engineResponse.pubsubError != null)
                message = message + ", PubSubEngine error: "+StringEscapeUtils.escapeHtml4(engineResponse.pubsubError.asXML());    
                
            result.setMessage(message);                                               
            return result;
        }
        
        if(engineResponse.newNode == null)
        {
            result.setResultType(NodeOperationResultEntity.NodeOperationResultType.Failure);
            result.setMessage("Node creation failed, PubSubengine returned null Node, without further details about the error");                                               
            return result;
        }
        
        result.setResultType(NodeOperationResultEntity.NodeOperationResultType.Success);
        result.setMessage("New node has been created as requested");                                               
        return result;
    }

    /**
     * Delete an existing node.
     *
     * @return NodeOperationResultEntity
     *             the node deletion result
     * @throws ServiceException
     *             the service exception
     */
    public NodeOperationResultEntity deleteNode(String nodeId, String operatorJid, boolean purge) throws ServiceException {

        NodeOperationResultEntity result = new NodeOperationResultEntity();
        result.setNodeId(nodeId);
        
        if(nodeId.isEmpty())
        {
            result.setResultType(NodeOperationResultEntity.NodeOperationResultType.Failure);
            result.setMessage("Node deletion failed, thye provided node ID is epmty");                                               
            return result;
        }
        
        Node node = XMPPServer.getInstance().getPubSubModule().getNode(nodeId);
        
        if(node == null)
        {
            result.setResultType(NodeOperationResultEntity.NodeOperationResultType.Failure);
            result.setMessage("Node deletion failed, the node with the given ID was not found on this server");                                               
            return result;
        }
        
        if(node.isRootCollectionNode())
        {
            result.setResultType(NodeOperationResultEntity.NodeOperationResultType.Failure);
            result.setMessage("Node deletion failed, the node that needs to be deleted is a root collection node");                                               
            return result;
        }
        
        if(node.isCollectionNode())
        {
            result.setResultType(NodeOperationResultEntity.NodeOperationResultType.Failure);
            result.setMessage("Node deletion failed, the node that needs to be deleted is a collection node, deleting such node is not supported");                                               
            return result;
        }
        
        if(!node.isAdmin(new JID(operatorJid)))
        {
            result.setResultType(NodeOperationResultEntity.NodeOperationResultType.Failure);
            result.setMessage("Node deletion failed, the given operator does not have an admin role on the given node");                                               
            return result;
        }
        
        if(purge)
        {
            ((LeafNode) node).purge();
        }
        node.delete();
        
        result.setResultType(NodeOperationResultEntity.NodeOperationResultType.Success);
        String message = new String("The given node has been deleted as requested");
        if(purge)
        {
            message = message + " (published items purged)";
        }
        result.setMessage(message);                                               
        return result;
    }
}
