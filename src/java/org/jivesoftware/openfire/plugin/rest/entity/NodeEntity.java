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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class NodeEntity.
 */
@XmlRootElement(name = "node")
@XmlType(propOrder = { "id", "name", "parent", "leaf" })
public class NodeEntity {

    /** The id. */
    private String id;

    /** The name. */
    private String name;

    /** The id of the parent node. */
    private String parent;

    /** The node type. If true, the node is a leaf node, otherwise it's a collection node. */
    private Boolean leaf;

    /**
     * Instantiates a new node entity.
     */
    public NodeEntity() {
    }

    /**
     * Instantiates a new node entity.
     *
     * @param id
     *            the id
     * @param name
     *            the name
     * @param type
     *            the node type
     */
    public NodeEntity(String id, String name, String parent, Boolean leaf) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.leaf = leaf;
    }
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    @XmlElement(name = "id")
    @Schema(description = "Id of the node", example = "pubsub/shakespeare/lit/moorish_meanderings")
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    @XmlElement(name = "name")
    @Schema(description = "Name of the node", example = "moorish_meanderings")
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the parent node id.
     *
     * @return the parent
     */
    @XmlElement(name = "parent")
    @Schema(description = "Id of the parent node", example = "pubsub/shakespeare/lit")
    public String getParent() {
        return parent;
    }

    /**
     * Sets the parent node id.
     *
     * @param parent
     *            the parent node id
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    /**
     * Gets whether this is a leaf node
     *
     * @return whether it's a leaf node
     */
    @XmlElement(name = "leaf")
    @Schema(description = "Whether the node is a leaf node (has published items) or a collection node (is parent for other nodes)", example = "false")
    public Boolean getLeaf() {
        return leaf;
    }

    /**
     * Sets whether this is a leaf node
     *
     * @param shared whether this is a leaf node
     */
    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }
}
