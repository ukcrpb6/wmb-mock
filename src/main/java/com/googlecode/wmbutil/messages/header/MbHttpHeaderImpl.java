package com.googlecode.wmbutil.messages.header;

import com.ibm.broker.javacompute.Base64;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;

import java.io.UnsupportedEncodingException;

import static com.google.common.base.Preconditions.*;
import static com.google.common.net.HttpHeaders.*;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class MbHttpHeaderImpl extends AbstractMbHeader implements MbHttpHeader {

    MbHttpHeaderImpl(MbElement elm, MbHeaderType type) throws MbException {
        super(elm, type);
    }

    @Override
    public final String getAccept() throws MbException {
        return getValue(ACCEPT);
    }

    @Override
    public void setAccept(String accept) throws MbException {
        setValue(ACCEPT, accept);
    }

    @Override
    public final String getAcceptEncoding() throws MbException {
        return getValue(ACCEPT_ENCODING);
    }

    @Override
    public void setAcceptEncoding(String acceptEncoding) throws MbException {
        setValue(ACCEPT_ENCODING, acceptEncoding);
    }

    @Override
    public final String getAuthorization() throws MbException {
        return getValue(AUTHORIZATION);
    }

    @Override
    public void setAuthorization(String authorization) throws MbException {
        setValue(AUTHORIZATION, authorization);
    }

    @Override
    public final String getCacheControl() throws MbException {
        return getValue(CACHE_CONTROL);
    }

    @Override
    public void setCacheControl(String cacheControl) throws MbException {
        setValue(CACHE_CONTROL, cacheControl);
    }

    @Override
    public final String getContentEncoding() throws MbException {
        return getValue(CONTENT_ENCODING);
    }

    @Override
    public void setContentEncoding(String contentEncoding) throws MbException {
        setValue(CONTENT_ENCODING, contentEncoding);
    }

    @Override
    public final Long getContentLength() throws MbException {
        return getValue(CONTENT_LENGTH);
    }

    @Override
    public void setContentLength(Long contentLength) throws MbException {
        setValue(CONTENT_LENGTH, contentLength);
    }

    @Override
    public final String getContentMD5() throws MbException {
        return getValue(CONTENT_MD5);
    }

    @Override
    public void setContentMD5(String contentMD5) throws MbException {
        setValue(CONTENT_MD5, contentMD5);
    }

    @Override
    public final String getContentRange() throws MbException {
        return getValue(CONTENT_RANGE);
    }

    @Override
    public void setContentRange(String contentRange) throws MbException {
        setValue(CONTENT_RANGE, contentRange);
    }

    @Override
    public final String getContentType() throws MbException {
        return getValue(CONTENT_TYPE);
    }

    @Override
    public void setContentType(String contentType) throws MbException {
        setValue(CONTENT_TYPE, contentType);
    }

    @Override
    public final String getCookie() throws MbException {
        return getValue(COOKIE);
    }

    @Override
    public void setCookie(String cookie) throws MbException {
        setValue(COOKIE, cookie);
    }

    @Override
    public final String getDate() throws MbException {
        return getValue(DATE);
    }

    @Override
    public void setDate(String date) throws MbException {
        setValue(DATE, date);
    }

    @Override
    public final String getETag() throws MbException {
        return getValue(ETAG);
    }

    @Override
    public void setETag(String etag) throws MbException {
        setValue(ETAG, etag);
    }

    @Override
    public final String getExpires() throws MbException {
        return getValue(EXPIRES);
    }

    @Override
    public void setExpires(String expires) throws MbException {
        setValue(EXPIRES, expires);
    }

    @Override
    public final String getIfModifiedSince() throws MbException {
        return getValue(IF_MODIFIED_SINCE);
    }

    @Override
    public void setIfModifiedSince(String ifModifiedSince) throws MbException {
        setValue(IF_MODIFIED_SINCE, ifModifiedSince);
    }

    @Override
    public final String getIfMatch() throws MbException {
        return getValue(IF_MATCH);
    }

    @Override
    public void setIfMatch(String ifMatch) throws MbException {
        setValue(IF_MATCH, ifMatch);
    }

    @Override
    public final String getIfNoneMatch() throws MbException {
        return getValue(IF_NONE_MATCH);
    }

    @Override
    public void setIfNoneMatch(String ifNoneMatch) throws MbException {
        setValue(IF_NONE_MATCH, ifNoneMatch);
    }

    @Override
    public final String getIfUnmodifiedSince() throws MbException {
        return getValue(IF_UNMODIFIED_SINCE);
    }

    @Override
    public void setIfUnmodifiedSince(String ifUnmodifiedSince) throws MbException {
        setValue(IF_UNMODIFIED_SINCE, ifUnmodifiedSince);
    }

    @Override
    public final String getLastModified() throws MbException {
        return getValue(LAST_MODIFIED);
    }

    @Override
    public void setLastModified(String lastModified) throws MbException {
        setValue(LAST_MODIFIED, lastModified);
    }

    @Override
    public final String getLocation() throws MbException {
        return getValue(LOCATION);
    }

    @Override
    public void setLocation(String location) throws MbException {
        setValue(LOCATION, location);
    }

    @Override
    public final String getMimeVersion() throws MbException {
        return getValue("MIME-Version");
    }

    @Override
    public void setMimeVersion(String mimeVersion) throws MbException {
        setValue("MIME-Version", mimeVersion);
    }

    @Override
    public final String getRange() throws MbException {
        return getValue(RANGE);
    }

    @Override
    public void setRange(String range) throws MbException {
        setValue(RANGE, range);
    }

    @Override
    public final String getRetryAfter() throws MbException {
        return getValue(RETRY_AFTER);
    }

    @Override
    public void setRetryAfter(String retryAfter) throws MbException {
        setValue(RETRY_AFTER, retryAfter);
    }

    @Override
    public final String getUserAgent() throws MbException {
        return getValue(USER_AGENT);
    }

    @Override
    public void setUserAgent(String userAgent) throws MbException {
        setValue(USER_AGENT, userAgent);
    }

    @Override
    public final String getAuthenticate() throws MbException {
        return getValue(WWW_AUTHENTICATE);
    }

    @Override
    public void setAuthenticate(String authenticate) throws MbException {
        setValue(WWW_AUTHENTICATE, authenticate);
    }

    @Override
    public void setBasicAuthentication(String username, String password) throws MbException {
        String userPass = checkNotNull(username) + ":" + checkNotNull(password);
        try {
            setValue(AUTHORIZATION, "Basic " + Base64.encode(userPass.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            // Unlikely UTF-8 is in every JVM (java itself uses UTF-16 as default)
            throw new RuntimeException("UTF-8 is an unsupported encoding");
        }
    }

}

