package com.googlecode.wmbutil.messages;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class ImmutableHttpHeader extends MutableHttpHeader {

    public ImmutableHttpHeader(MbElement elm, MbHeaderType type) throws MbException {
        super(elm, type);
    }

    @Override
    public void set(String name, String value) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAccept(String accept) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAcceptEncoding(String acceptEncoding) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAuthorization(String authorization) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCacheControl(String cacheControl) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setContentEncoding(String contentEncoding) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setContentLength(Long contentLength) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setContentMD5(String contentMD5) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setContentRange(String contentRange) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setContentType(String contentType) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCookie(String cookie) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDate(String date) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setETag(String etag) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setExpires(String expires) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIfModifiedSince(String ifModifiedSince) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIfMatch(String ifMatch) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIfNoneMatch(String ifNoneMatch) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIfUnmodifiedSince(String ifUnmodifiedSince) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLastModified(String lastModified) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLocation(String location) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMimeVersion(String mimeVersion) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRange(String range) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRetryAfter(String retryAfter) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUserAgent(String userAgent) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAuthenticate(String authenticate) throws MbException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBasicAuthentication(String username, String password) throws MbException {
        throw new UnsupportedOperationException();
    }

}
