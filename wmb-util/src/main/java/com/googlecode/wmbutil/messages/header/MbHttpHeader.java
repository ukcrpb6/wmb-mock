package com.googlecode.wmbutil.messages.header;

import com.ibm.broker.plugin.MbException;

/**
 * @author Bob Browning <bob.browning@pressassociation.com>
 */
public interface MbHttpHeader extends MbHeader {

    /**
     * Returns the {@code "Accept"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getAccept() throws MbException;

    /**
     * Sets the {@code "Accept"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setAccept(String accept) throws MbException;

    /**
     * Returns the {@code "Accept-Encoding"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getAcceptEncoding() throws MbException;

    /**
     * Sets the {@code "Accept-Encoding"} header or {@code null} for none.
     * <p/>
     * <p>
     * By default, this is {@code "gzip"}.
     * </p>
     *
     * @since 1.5
     */
    void setAcceptEncoding(String acceptEncoding) throws MbException;

    /**
     * Returns the {@code "Authorization"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getAuthorization() throws MbException;

    /**
     * Sets the {@code "Authorization"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setAuthorization(String authorization) throws MbException;

    /**
     * Returns the {@code "Cache-Control"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getCacheControl() throws MbException;

    /**
     * Sets the {@code "Cache-Control"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setCacheControl(String cacheControl) throws MbException;

    /**
     * Returns the {@code "Content-Encoding"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getContentEncoding() throws MbException;

    /**
     * Sets the {@code "Content-Encoding"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setContentEncoding(String contentEncoding) throws MbException;

    /**
     * Returns the {@code "Content-Length"} header or {@code null} for none.
     * <p/>
     * <p>
     * Upgrade warning: in prior version content length was represented as a String, but now it is
     * represented as a Long.
     * </p>
     *
     * @since 1.5
     */
    Long getContentLength() throws MbException;

    /**
     * Sets the {@code "Content-Length"} header or {@code null} for none.
     * <p/>
     * <p>
     * Upgrade warning: in prior version content length was represented as a String, but now it is
     * represented as a Long.
     * </p>
     *
     * @since 1.5
     */
    void setContentLength(Long contentLength) throws MbException;

    /**
     * Returns the {@code "Content-MD5"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getContentMD5() throws MbException;

    /**
     * Sets the {@code "Content-MD5"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setContentMD5(String contentMD5) throws MbException;

    /**
     * Returns the {@code "Content-Range"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getContentRange() throws MbException;

    /**
     * Sets the {@code "Content-Range"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setContentRange(String contentRange) throws MbException;

    /**
     * Returns the {@code "Content-Type"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getContentType() throws MbException;

    /**
     * Sets the {@code "Content-Type"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setContentType(String contentType) throws MbException;

    /**
     * Returns the {@code "Cookie"} header or {@code null} for none.
     * <a href='http://tools.ietf.org/html/rfc6265'>See Cookie Specification.</a>
     *
     * @since 1.6
     */
    String getCookie() throws MbException;

    /**
     * Sets the {@code "Cookie"} header or {@code null} for none.
     *
     * @since 1.6
     */
    void setCookie(String cookie) throws MbException;

    /**
     * Returns the {@code "Date"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getDate() throws MbException;

    /**
     * Sets the {@code "Date"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setDate(String date) throws MbException;

    /**
     * Returns the {@code "ETag"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getETag() throws MbException;

    /**
     * Sets the {@code "ETag"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setETag(String etag) throws MbException;

    /**
     * Returns the {@code "Expires"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getExpires() throws MbException;

    /**
     * Sets the {@code "Expires"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setExpires(String expires) throws MbException;

    /**
     * Returns the {@code "If-Modified-Since"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getIfModifiedSince() throws MbException;

    /**
     * Sets the {@code "If-Modified-Since"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setIfModifiedSince(String ifModifiedSince) throws MbException;

    /**
     * Returns the {@code "If-Match"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getIfMatch() throws MbException;

    /**
     * Sets the {@code "If-Match"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setIfMatch(String ifMatch) throws MbException;

    /**
     * Returns the {@code "If-None-Match"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getIfNoneMatch() throws MbException;

    /**
     * Sets the {@code "If-None-Match"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setIfNoneMatch(String ifNoneMatch) throws MbException;

    /**
     * Returns the {@code "If-Unmodified-Since"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getIfUnmodifiedSince() throws MbException;

    /**
     * Sets the {@code "If-Unmodified-Since"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setIfUnmodifiedSince(String ifUnmodifiedSince) throws MbException;

    /**
     * Returns the {@code "Last-Modified"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getLastModified() throws MbException;

    /**
     * Sets the {@code "Last-Modified"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setLastModified(String lastModified) throws MbException;

    /**
     * Returns the {@code "Location"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getLocation() throws MbException;

    /**
     * Sets the {@code "Location"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setLocation(String location) throws MbException;

    /**
     * Returns the {@code "MIME-Version"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getMimeVersion() throws MbException;

    /**
     * Sets the {@code "MIME-Version"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setMimeVersion(String mimeVersion) throws MbException;

    /**
     * Returns the {@code "Range"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getRange() throws MbException;

    /**
     * Sets the {@code "Range"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setRange(String range) throws MbException;

    /**
     * Returns the {@code "Retry-After"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getRetryAfter() throws MbException;

    /**
     * Sets the {@code "Retry-After"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setRetryAfter(String retryAfter) throws MbException;

    /**
     * Returns the {@code "User-Agent"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getUserAgent() throws MbException;

    /**
     * Sets the {@code "User-Agent"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setUserAgent(String userAgent) throws MbException;

    /**
     * Returns the {@code "WWW-Authenticate"} header or {@code null} for none.
     *
     * @since 1.5
     */
    String getAuthenticate() throws MbException;

    /**
     * Sets the {@code "WWW-Authenticate"} header or {@code null} for none.
     *
     * @since 1.5
     */
    void setAuthenticate(String authenticate) throws MbException;

    /**
     * Sets the {@code authorization} header as specified in <a
     * href="http://tools.ietf.org/html/rfc2617#section-2">Basic Authentication Scheme</a>.
     *
     * @since 1.2
     */
    void setBasicAuthentication(String username, String password) throws MbException;

}
