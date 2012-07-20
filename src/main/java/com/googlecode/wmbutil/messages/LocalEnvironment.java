/*
 * Copyright 2007 (C) Callista Enterprise.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *	http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.googlecode.wmbutil.messages;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.googlecode.wmbutil.NiceMbException;
import com.googlecode.wmbutil.messages.configuration.EmailAttachment;
import com.googlecode.wmbutil.messages.configuration.EmailDestination;
import com.googlecode.wmbutil.messages.configuration.TimeoutRequest;
import com.googlecode.wmbutil.util.TypeSafetyHelper;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Wrapper class for the local environment. Makes it easier to work with some
 * common attributes like destinations lists and routing labels.
 *
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class LocalEnvironment extends MbElementWrapper {

    /**
     * Wrap an existing local environment message, note that this is not the
     * same {@link MbMessage} as the one containing your data
     *
     * @param msg The message to wrap
     * @return The wrapped message
     * @throws MbException
     */
    public static Optional<LocalEnvironment> wrap(MbMessage msg) throws MbException {
        return Optional.fromNullable(checkNotNull(msg).getRootElement()).transform(
                new Function<MbElement, LocalEnvironment>() {
                    @Override
                    public LocalEnvironment apply(MbElement input) {
                        return new LocalEnvironment(input);
                    }
                });
    }

    /**
     * Constructs a new LocalEnvironment wrapper
     *
     * @param elm The element to be wrapped
     * @throws MbException
     */
    private LocalEnvironment(MbElement elm) {
        super(elm);
    }

    /**
     * Get all router list labels.
     *
     * @return The array of all router list labels
     * @throws MbException
     */
    public List<String> getRouterListLabelNames() throws MbException {
        Iterable<MbElement> labelElms = TypeSafetyHelper.typeSafeIterable(
                (List<?>) getMbElement().evaluateXPath("Destination/RouterList/DestinationData/labelName"), MbElement.class);
        return ImmutableList.copyOf(Iterables.transform(labelElms, new Function<MbElement, String>() {
            @Override
            public String apply(MbElement input) {
                try {
                    return input.getValueAsString();
                } catch (MbException e) {
                    throw Throwables.propagate(e);
                }
            }
        }));
    }

    /**
     * Add a new router list label.
     *
     * @param labelName The label name to use
     * @throws MbException
     */
    public void addRouterListLabelName(String labelName) throws MbException {
        getMbElement().evaluateXPath(
                "?Destination/?RouterList/?$DestinationData/?labelName[set-value('" + labelName + "')]");
    }

    /**
     * Add a TimeoutRequest to the LocalEnvironment. For more information
     * regarding the parameters see the Websphere Message Broker documentation.
     *
     * @param request A TimeoutRequest object.
     * @throws MbException Throws exception if identifier is missing.
     */
    public void addTimeoutRequest(TimeoutRequest request) throws MbException {
        final MbElement el = getMbElement();

        // <Action>
        el.evaluateXPath("?TimeoutRequest/?Action[set-value('" + request.getAction() + "')]");

        // <Identifier>
        if (request.getIdentifier() != null || request.getIdentifier().length() > 0) {
            el.evaluateXPath("?TimeoutRequest/?Identifier[set-value('" + request.getIdentifier() + "')]");
        } else {
            throw new NiceMbException("Missing identifier attribute on TimeoutRequest");
        }

        // <StartDate> + <StartTime>
        if (request.getStartDateTime() != null) {
            el.evaluateXPath("?TimeoutRequest/?StartDate[set-value('" + request.getStartDate() + "')]");
            el.evaluateXPath("?TimeoutRequest/?StartTime[set-value('" + request.getStartTime() + "')]");
        }

        // <Interval>
        if (request.getInterval() != null) {
            el.evaluateXPath("?TimeoutRequest/?Interval[set-value('" + request.getInterval() + "')]");
        }

        // <Count>
        if (request.getCount() != null) {
            el.evaluateXPath("?TimeoutRequest/?Count[set-value('" + request.getCount() + "')]");
        }

        // <IgnoreMissed>
        el.evaluateXPath("?TimeoutRequest/?IgnoreMissed[set-value('" + request.getIgnoreMissed() + "')]");

        // <AllowOverwrite>
        el.evaluateXPath("?TimeoutRequest/?AllowOverwrite[set-value('" + request.getAllowOverwrite() + "')]");
    }

    /**
     * Sets an Email destination header
     *
     * @param dest The EmailDestination to add
     * @throws MbException
     */
    public void setEmailDestination(EmailDestination dest) throws MbException {
        final MbElement el = getMbElement();
        if (dest.getSmtpServer() != null) {
            el.evaluateXPath("?Destination/?Email/?SMTPServer[set-value('" + dest.getSmtpServer() + "')]");
        }
        if (dest.getSecurityIdentity() != null) {
            el.evaluateXPath("?Destination/?Email/?SecurityIdentity[set-value('" + dest.getSecurityIdentity() + "')]");
        }
        if (dest.getBodyContentType() != null) {
            el.evaluateXPath("?Destination/?Email/?BodyContentType[set-value('" + dest.getBodyContentType() + "')]");
        }
        if (dest.getMultiPartContentType() != null) {
            el.evaluateXPath(
                    "?Destination/?Email/?MultiPartContentType[set-value('" + dest.getMultiPartContentType() + "')]");
        }
    }

    /**
     * Adds an email attachment to the local environment
     * TODO: Handle XPath/ESQL expressions in Content
     *
     * @param emailAttachment The EmailAttachment to add
     * @throws MbException
     */
    public void addEmailAttachment(EmailAttachment emailAttachment) throws MbException {

        getMbElement().evaluateXPath("?Destination/?Email/?$Attachment");
        MbElement attachmentElm = getMbElement().getFirstElementByPath("Destination/Email").getLastChild();

        if (emailAttachment.getAttachmentContentAsBlob() != null) {
            attachmentElm.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "Content",
                    emailAttachment.getAttachmentContentAsBlob());

        } else if (emailAttachment.getAttachmentContentAsText() != null) {
            attachmentElm.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "Content",
                    emailAttachment.getAttachmentContentAsText());
        }

        if (emailAttachment.getAttachmentContentType() != null) {
            attachmentElm.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "ContentType",
                    emailAttachment.getAttachmentContentType());
        }

        if (emailAttachment.getAttachmentContentName() != null) {
            attachmentElm.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "ContentName",
                    emailAttachment.getAttachmentContentName());
        }

        if (emailAttachment.getAttachmentContentEncoding() != null) {
            attachmentElm.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "ContentEncoding",
                    emailAttachment.getAttachmentContentEncoding());
        }

    }
}
