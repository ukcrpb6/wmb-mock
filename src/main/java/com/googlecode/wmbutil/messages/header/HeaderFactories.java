package com.googlecode.wmbutil.messages.header;

import com.google.common.base.Optional;
import com.googlecode.wmbutil.WmqFormat;
import com.googlecode.wmbutil.messages.MqMessageFormat;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class HeaderFactories {

    private static AbstractHeaderFactory<MutableMbHttpHeader> getHttpHeaderFactory(final MbHeaderType type) {
        return new AbstractHeaderFactory<MutableMbHttpHeader>() {
            @Override
            protected MbHeaderType getHeaderType() {
                return type;
            }

            @Override
            protected MutableMbHttpHeader getHeader(MbElement element) throws MbException {
                return new MutableMbHttpHeader(element, getHeaderType());
            }

            @Override
            protected ImmutableMbHttpHeader getImmutableHeader(MbElement element) throws MbException {
                return new ImmutableMbHttpHeader(element, getHeaderType());
            }
        };
    }

    public static final AbstractHeaderFactory<MutableMbHttpHeader> HTTP_INPUT_HEADER_FACTORY =
            getHttpHeaderFactory(MbHeaderType.HTTP_INPUT);

    public static final AbstractHeaderFactory<MutableMbHttpHeader> HTTP_REQUEST_HEADER_FACTORY =
            getHttpHeaderFactory(MbHeaderType.HTTP_REQUEST);

    public static final AbstractHeaderFactory<MutableMbHttpHeader> HTTP_RESPONSE_HEADER_FACTORY =
            getHttpHeaderFactory(MbHeaderType.HTTP_RESPONSE);

    public static final AbstractHeaderFactory<MutableMbHttpHeader> HTTP_REPLY_HEADER_FACTORY =
            getHttpHeaderFactory(MbHeaderType.HTTP_REPLY);

    public static final AbstractHeaderFactory<MutableMbMQMDHeader> MQMD_HEADER_FACTORY =
            new AbstractHeaderFactory<MutableMbMQMDHeader>() {
                @Override
                protected MbHeaderType getHeaderType() {
                    return MbHeaderType.MQMD;
                }

                @Override
                protected MutableMbMQMDHeader getHeader(MbElement element) throws MbException {
                    return new MutableMbMQMDHeader(element);
                }

                @Override
                protected ImmutableMbMQMDHeader getImmutableHeader(MbElement element) throws MbException {
                    return new ImmutableMbMQMDHeader(element);
                }

                @Override
                protected MutableMbMQMDHeader createHeader(MbMessage message) throws MbException {
                    final MutableMbMQMDHeader mqmd = super.createHeader(message);
                    mqmd.setVersion(2);
                    mqmd.setReport(0);
                    mqmd.setFormat(MqMessageFormat.MQSTR);
                    mqmd.setEncoding(546);
                    mqmd.setCodedCharSetId(437);
                    mqmd.setMsgType(8);
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

    public static final AbstractHeaderFactory<MutableMbPropertiesHeader> PROPERTIES_HEADER_FACTORY =
            new AbstractHeaderFactory<MutableMbPropertiesHeader>() {
                @Override
                protected MbHeaderType getHeaderType() {
                    return MbHeaderType.PROPERTIES;
                }

                @Override
                protected MutableMbPropertiesHeader getHeader(MbElement element) throws MbException {
                    return new MutableMbPropertiesHeader(element);
                }

                @Override
                protected ImmutableMbPropertiesHeader getImmutableHeader(MbElement element) throws MbException {
                    return new ImmutableMbPropertiesHeader(element);
                }
            };

    public static final AbstractHeaderFactory<MutableMbMQRFH2Header> MQRFH2_HEADER_FACTORY =
            new AbstractHeaderFactory<MutableMbMQRFH2Header>() {
                @Override
                protected MbHeaderType getHeaderType() {
                    return MbHeaderType.MQRFH2;
                }

                @Override
                protected MutableMbMQRFH2Header getHeader(MbElement element) throws MbException {
                    return new MutableMbMQRFH2Header(element);
                }

                @Override
                protected ImmutableMbMQRFH2Header getImmutableHeader(MbElement element) throws MbException {
                    return new ImmutableMbMQRFH2Header(element);
                }

                @Override
                protected MutableMbMQRFH2Header createHeader(MbMessage message) throws MbException {
                    final MbElement createdElement;

                    Optional<MutableMbMQMDHeader> mqmd = MQMD_HEADER_FACTORY.tryGet(message);
                    if (mqmd.isPresent()) {
                        mqmd.get().setFormat(WmqFormat.MQ_RFH2);
                        createdElement = mqmd.get().getMbElement().createElementAfter(getHeaderType().getParserName());
                    } else {
                        Optional<MutableMbPropertiesHeader> properties = PROPERTIES_HEADER_FACTORY.tryGet(message);
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
