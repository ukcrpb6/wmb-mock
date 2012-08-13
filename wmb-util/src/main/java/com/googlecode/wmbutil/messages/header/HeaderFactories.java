package com.googlecode.wmbutil.messages.header;

import com.google.common.base.Optional;
import com.googlecode.wmbutil.CCSID;
import com.googlecode.wmbutil.messages.MqFormat;
import com.googlecode.wmbutil.messages.MqMessageType;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class HeaderFactories {

    private static AbstractHeaderFactory<MbHttpHeader> getHttpHeaderFactory(final MbHeaderType type) {
        return new AbstractHeaderFactory<MbHttpHeader>() {
            @Override
            protected MbHeaderType getHeaderType() {
                return type;
            }

            @Override
            protected MbHttpHeader getHeader(MbElement element) throws MbException {
                return new MbHttpHeaderImpl(element, getHeaderType());
            }
        };
    }

    public static final AbstractHeaderFactory<MbHttpHeader> HTTP_INPUT_HEADER_FACTORY =
            getHttpHeaderFactory(MbHeaderType.HTTP_INPUT);

    public static final AbstractHeaderFactory<MbHttpHeader> HTTP_REQUEST_HEADER_FACTORY =
            getHttpHeaderFactory(MbHeaderType.HTTP_REQUEST);

    public static final AbstractHeaderFactory<MbHttpHeader> HTTP_RESPONSE_HEADER_FACTORY =
            getHttpHeaderFactory(MbHeaderType.HTTP_RESPONSE);

    public static final AbstractHeaderFactory<MbHttpHeader> HTTP_REPLY_HEADER_FACTORY =
            getHttpHeaderFactory(MbHeaderType.HTTP_REPLY);

    public static final AbstractHeaderFactory<MbMQMDHeader> MQMD_HEADER_FACTORY =
            new AbstractHeaderFactory<MbMQMDHeader>() {
                @Override
                protected MbHeaderType getHeaderType() {
                    return MbHeaderType.MQMD;
                }

                @Override
                protected MbMQMDHeaderImpl getHeader(MbElement element) throws MbException {
                    return new MbMQMDHeaderImpl(element);
                }

                @Override
                protected MbMQMDHeader createHeader(MbMessage message) throws MbException {
                    final MbMQMDHeader mqmd = super.createHeader(message);
                    mqmd.setVersion(2);
                    mqmd.setReport(0);
                    mqmd.setFormat(MqFormat.MQFMT_STRING);
                    mqmd.setEncoding(546);
                    mqmd.setCodedCharSetId(CCSID.ASCII.getId());
                    mqmd.setMsgType(MqMessageType.MQMT_DATAGRAM);
                    mqmd.setExpiry(-1);
                    mqmd.setFeedback(0);
                    mqmd.setPriority(0);
                    mqmd.setPersistence(1);
                    mqmd.setBackoutCount(0);
                    mqmd.setOffset(0);
                    mqmd.setMsgSeqNumber(1);
                    mqmd.setMsgFlags(0);
                    return mqmd;
                }
            };

    public static final AbstractHeaderFactory<MbPropertiesHeader> PROPERTIES_HEADER_FACTORY =
            new AbstractHeaderFactory<MbPropertiesHeader>() {
                @Override
                protected MbHeaderType getHeaderType() {
                    return MbHeaderType.PROPERTIES;
                }

                @Override
                protected MbPropertiesHeader getHeader(MbElement element) throws MbException {
                    return new MbPropertiesHeaderImpl(element);
                }
            };

    public static final AbstractHeaderFactory<MbMQRFH2Header> MQRFH2_HEADER_FACTORY =
            new AbstractHeaderFactory<MbMQRFH2Header>() {
                @Override
                protected MbHeaderType getHeaderType() {
                    return MbHeaderType.MQRFH2;
                }

                @Override
                protected MbMQRFH2Header getHeader(MbElement element) throws MbException {
                    return new MbMQRFH2HeaderImpl(element);
                }

                @Override
                protected MbMQRFH2Header createHeader(MbMessage message) throws MbException {
                    final MbElement createdElement;

                    Optional<MbMQMDHeader> mqmd = MQMD_HEADER_FACTORY.tryGet(message);
                    if (mqmd.isPresent()) {
                        mqmd.get().setFormat(MqFormat.MQFMT_RF_HEADER_2);
                        createdElement = mqmd.get().getMbElement().createElementAfter(getHeaderType().getParserName());
                    } else {
                        Optional<MbPropertiesHeader> properties = PROPERTIES_HEADER_FACTORY.tryGet(message);
                        if (properties.isPresent()) {
                            createdElement = properties.get().getMbElement().createElementAfter(getHeaderType().getParserName());
                        } else {
                            return super.create(message);
                        }
                    }

                    return getHeader(createdElement);
                }
            };

}
