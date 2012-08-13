package com.googlecode.wmbutil.messages.header;

import com.googlecode.wmbutil.messages.MbElementWrapper;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

/**
 * Abstract base class for the Header helpers.
 *
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public abstract class AbstractMbHeader extends MbElementWrapper implements MbHeader {

    private final MbHeaderType type;

    /**
     * Constructor defining the header with the specific element.
     *
     * @param elm      The message element
     * @throws MbException
     */
    public AbstractMbHeader(MbElement elm, MbHeaderType type) throws MbException {
        super(elm);
        this.type = type;
    }

    @Override
    public MbHeaderType getType() {
        return type;
    }

}
