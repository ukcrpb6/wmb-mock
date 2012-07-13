package com.googlecode.wmbutil.messages;

import com.google.common.base.Optional;
import com.googlecode.wmbutil.NiceMbException;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;

import java.util.NoSuchElementException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public abstract class AbstractHeaderFactory<T extends Header> {

    /**
     * Get the header type for the current factory.
     *
     * @return MbHeaderType constant
     */
    protected abstract MbHeaderType getHeaderType();

    /**
     * Get the header from the message object.
     *
     * @param message Message to get the header from
     * @return
     * @throws MbException
     */
    protected T getHeader(MbMessage message) throws MbException {
        return getHeader(getHeaderElement(message));
    }

    protected abstract T getImmutableHeader(MbElement element) throws MbException;

    protected abstract T getHeader(MbElement element) throws MbException;

    protected T createHeader(MbMessage message) throws MbException {
        return getHeader(message.getRootElement().createElementAsLastChild(getHeaderType().getParserName()));
    }

    public MbElement getHeaderElement(MbMessage msg) throws MbException {
        return msg.getRootElement().getFirstElementByPath(getHeaderType().getRootPath());
    }

    public T get(MbMessage msg) throws MbException {
        if (headerExistsIn(msg)) {
            return getHeader(msg);
        }
        throw new NoSuchElementException(getHeaderType().getRootPath() + " does not exist in message " + msg);
    }

    public Optional<T> tryGet(MbMessage msg) throws MbException {
        return headerExistsIn(msg) ? Optional.of(getHeader(msg)) : Optional.<T>absent();
    }

    public T create(MbMessage msg) throws MbException {
        if (headerExistsIn(msg)) {
            throw new NiceMbException("Header " + getHeaderType() + " already exists");
        }
        return createHeader(msg);
    }

    public T getOrCreate(final MbMessage msg) throws MbException {
        Optional<T> wrapped = tryGet(msg);
        return (wrapped.isPresent()) ? wrapped.get() : create(msg);
    }

    public Optional<T> tryRemove(MbMessage msg) throws MbException {
        MbElement elm = getHeaderElement(msg);
        if (elm != null) {
            elm.detach();
            return Optional.of(getImmutableHeader(elm));
        }
        return Optional.absent();
    }

    /**
     * Remove the header from the message.
     *
     * @param msg Message to remove header from
     * @return Detached header element
     * @throws MbException Element does ont exist
     */
    public T remove(MbMessage msg) throws MbException {
        Optional<T> element = tryRemove(msg);
        if(element.isPresent()) {
            return element.get();
        }
        throw new NiceMbException(getHeaderType() + " does not exist");
    }

    /**
     * Validate that header exists in the provided message.
     *
     * @param msg Message to get header from
     * @return True if the header exists, false otherwise
     * @throws MbException
     */
    public boolean headerExistsIn(MbMessage msg) throws MbException {
        return getHeaderElement(msg) != null;
    }

}
