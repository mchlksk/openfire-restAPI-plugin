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
 * The Class PublishItemEntity.
 */
@XmlRootElement(name = "entry")
@XmlType(propOrder = { "subject", "summary", "body", "author" })
public class PublishItemEntity {

    /** The subject. */
    private String subject;

    /** The summary. */
    private String summary;

    /** The body. */
    private String body;

    /** The JID of the author. */
    private String author;

    /**
     * Instantiates a new node entity.
     */
    public PublishItemEntity() {
    }

    /**
     * Instantiates a new node entity.
     *
     * @param subject
     *            the subject
     * @param summary
     *            the summary
     * @param body
     *            the body
     * @param author
     *            the author JID
     */
    public PublishItemEntity(String subject, String summary, String body, String author) {
        this.subject = subject;
        this.summary = summary;
        this.body = body;
        this.author = author;
    }
    
    /**
     * Gets the subject.
     *
     * @return the subject
     */
    @XmlElement(name = "subject")
    @Schema(description = "Subject of the publish item", example = "Soliloquy")
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject.
     *
     * @param subject
     *            the new subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets the summary.
     *
     * @return the summary
     */
    @XmlElement(name = "summary")
    @Schema(description = "Summary of the publish item", example = "To be, or not to be")
    public String getSummary() {
        return summary;
    }

    /**
     * Sets the summary.
     *
     * @param summary
     *            the new summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Gets publish item body.
     *
     * @return the body
     */
    @XmlElement(name = "body")
    @Schema(description = "Body of the publish item", example = "To be, or not to be: that is the question")
    public String getBody() {
        return body;
    }

    /**
     * Sets the body.
     *
     * @param body
     *            the new body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets the JID of the publish item author.
     *
     * @return the author JID
     */
    @XmlElement(name = "author")
    @Schema(description = "Author of the publish item", example = "hamlet@denmark.lit")
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the JID of the publish item author.
     *
     * @param author
     *            the new author JID
     */
    public void setAuthor(String author) {
        this.author = author;
    }
}
