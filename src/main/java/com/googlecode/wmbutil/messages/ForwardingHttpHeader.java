package com.googlecode.wmbutil.messages;

import com.ibm.broker.plugin.MbException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public class ForwardingHttpHeader implements HttpHeader {

    private HttpHeader delegate;

    public ForwardingHttpHeader(HttpHeader delegate) {
        this.delegate = delegate;
    }

    @Override
    public String get(String name) throws MbException {return delegate.get(name);}

    @Override
    public void set(String name, String value) throws MbException {delegate.set(name, value);}

    @Override
    public String getAccept() throws MbException {return delegate.getAccept();}

    @Override
    public void setAccept(String accept) throws MbException {delegate.setAccept(accept);}

    @Override
    public String getAcceptEncoding() throws MbException {return delegate.getAcceptEncoding();}

    @Override
    public void setAcceptEncoding(String acceptEncoding) throws MbException {
        delegate.setAcceptEncoding(acceptEncoding);
    }

    @Override
    public String getAuthorization() throws MbException {return delegate.getAuthorization();}

    @Override
    public void setAuthorization(String authorization) throws MbException {delegate.setAuthorization(authorization);}

    @Override
    public String getCacheControl() throws MbException {return delegate.getCacheControl();}

    @Override
    public void setCacheControl(String cacheControl) throws MbException {delegate.setCacheControl(cacheControl);}

    @Override
    public String getContentEncoding() throws MbException {return delegate.getContentEncoding();}

    @Override
    public void setContentEncoding(String contentEncoding) throws MbException {
        delegate.setContentEncoding(contentEncoding);
    }

    @Override
    public Long getContentLength() throws MbException {return delegate.getContentLength();}

    @Override
    public void setContentLength(Long contentLength) throws MbException {delegate.setContentLength(contentLength);}

    @Override
    public String getContentMD5() throws MbException {return delegate.getContentMD5();}

    @Override
    public void setContentMD5(String contentMD5) throws MbException {delegate.setContentMD5(contentMD5);}

    @Override
    public String getContentRange() throws MbException {return delegate.getContentRange();}

    @Override
    public void setContentRange(String contentRange) throws MbException {delegate.setContentRange(contentRange);}

    @Override
    public String getContentType() throws MbException {return delegate.getContentType();}

    @Override
    public void setContentType(String contentType) throws MbException {delegate.setContentType(contentType);}

    @Override
    public String getCookie() throws MbException {return delegate.getCookie();}

    @Override
    public void setCookie(String cookie) throws MbException {delegate.setCookie(cookie);}

    @Override
    public String getDate() throws MbException {return delegate.getDate();}

    @Override
    public void setDate(String date) throws MbException {delegate.setDate(date);}

    @Override
    public String getETag() throws MbException {return delegate.getETag();}

    @Override
    public void setETag(String etag) throws MbException {delegate.setETag(etag);}

    @Override
    public String getExpires() throws MbException {return delegate.getExpires();}

    @Override
    public void setExpires(String expires) throws MbException {delegate.setExpires(expires);}

    @Override
    public String getIfModifiedSince() throws MbException {return delegate.getIfModifiedSince();}

    @Override
    public void setIfModifiedSince(String ifModifiedSince) throws MbException {
        delegate.setIfModifiedSince(ifModifiedSince);
    }

    @Override
    public String getIfMatch() throws MbException {return delegate.getIfMatch();}

    @Override
    public void setIfMatch(String ifMatch) throws MbException {delegate.setIfMatch(ifMatch);}

    @Override
    public String getIfNoneMatch() throws MbException {return delegate.getIfNoneMatch();}

    @Override
    public void setIfNoneMatch(String ifNoneMatch) throws MbException {delegate.setIfNoneMatch(ifNoneMatch);}

    @Override
    public String getIfUnmodifiedSince() throws MbException {return delegate.getIfUnmodifiedSince();}

    @Override
    public void setIfUnmodifiedSince(String ifUnmodifiedSince) throws MbException {
        delegate.setIfUnmodifiedSince(ifUnmodifiedSince);
    }

    @Override
    public String getLastModified() throws MbException {return delegate.getLastModified();}

    @Override
    public void setLastModified(String lastModified) throws MbException {delegate.setLastModified(lastModified);}

    @Override
    public String getLocation() throws MbException {return delegate.getLocation();}

    @Override
    public void setLocation(String location) throws MbException {delegate.setLocation(location);}

    @Override
    public String getMimeVersion() throws MbException {return delegate.getMimeVersion();}

    @Override
    public void setMimeVersion(String mimeVersion) throws MbException {delegate.setMimeVersion(mimeVersion);}

    @Override
    public String getRange() throws MbException {return delegate.getRange();}

    @Override
    public void setRange(String range) throws MbException {delegate.setRange(range);}

    @Override
    public String getRetryAfter() throws MbException {return delegate.getRetryAfter();}

    @Override
    public void setRetryAfter(String retryAfter) throws MbException {delegate.setRetryAfter(retryAfter);}

    @Override
    public String getUserAgent() throws MbException {return delegate.getUserAgent();}

    @Override
    public void setUserAgent(String userAgent) throws MbException {delegate.setUserAgent(userAgent);}

    @Override
    public String getAuthenticate() throws MbException {return delegate.getAuthenticate();}

    @Override
    public void setAuthenticate(String authenticate) throws MbException {delegate.setAuthenticate(authenticate);}

    @Override
    public void setBasicAuthentication(String username, String password) throws MbException {
        delegate.setBasicAuthentication(username, password);
    }

    @Override
    public MbHeaderType getType() {return delegate.getType();}
}
