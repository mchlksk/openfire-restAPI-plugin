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

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The Class NodeEntities.
 */
@XmlRootElement(name = "results")
public class NodeOperationResultEntities {
    
    /** The results. */
    List<NodeOperationResultEntity> results;

    /**
     * Instantiates a new node operation results.
     */
    public NodeOperationResultEntities() {
    }

    /**
     * Instantiates a new node operation results.
     *
     * @param results the results
     */
    public NodeOperationResultEntities(List<NodeOperationResultEntity> results) {
        this.results = results;
    }

    /**
     * Gets the results.
     *
     * @return the results
     */
    @XmlElement(name = "result")
    @JsonProperty(value = "result")
    public List<NodeOperationResultEntity> getResults() {
        return results;
    }

    /**
     * Sets the results.
     *
     * @param results the new results
     */
    public void setResults(List<NodeOperationResultEntity> results) {
        this.results = results;
    }

}
