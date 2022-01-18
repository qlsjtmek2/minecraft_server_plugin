// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

public class HttpResponseStatus implements Comparable<HttpResponseStatus>
{
    public static final HttpResponseStatus CONTINUE;
    public static final HttpResponseStatus SWITCHING_PROTOCOLS;
    public static final HttpResponseStatus PROCESSING;
    public static final HttpResponseStatus OK;
    public static final HttpResponseStatus CREATED;
    public static final HttpResponseStatus ACCEPTED;
    public static final HttpResponseStatus NON_AUTHORITATIVE_INFORMATION;
    public static final HttpResponseStatus NO_CONTENT;
    public static final HttpResponseStatus RESET_CONTENT;
    public static final HttpResponseStatus PARTIAL_CONTENT;
    public static final HttpResponseStatus MULTI_STATUS;
    public static final HttpResponseStatus MULTIPLE_CHOICES;
    public static final HttpResponseStatus MOVED_PERMANENTLY;
    public static final HttpResponseStatus FOUND;
    public static final HttpResponseStatus SEE_OTHER;
    public static final HttpResponseStatus NOT_MODIFIED;
    public static final HttpResponseStatus USE_PROXY;
    public static final HttpResponseStatus TEMPORARY_REDIRECT;
    public static final HttpResponseStatus BAD_REQUEST;
    public static final HttpResponseStatus UNAUTHORIZED;
    public static final HttpResponseStatus PAYMENT_REQUIRED;
    public static final HttpResponseStatus FORBIDDEN;
    public static final HttpResponseStatus NOT_FOUND;
    public static final HttpResponseStatus METHOD_NOT_ALLOWED;
    public static final HttpResponseStatus NOT_ACCEPTABLE;
    public static final HttpResponseStatus PROXY_AUTHENTICATION_REQUIRED;
    public static final HttpResponseStatus REQUEST_TIMEOUT;
    public static final HttpResponseStatus CONFLICT;
    public static final HttpResponseStatus GONE;
    public static final HttpResponseStatus LENGTH_REQUIRED;
    public static final HttpResponseStatus PRECONDITION_FAILED;
    public static final HttpResponseStatus REQUEST_ENTITY_TOO_LARGE;
    public static final HttpResponseStatus REQUEST_URI_TOO_LONG;
    public static final HttpResponseStatus UNSUPPORTED_MEDIA_TYPE;
    public static final HttpResponseStatus REQUESTED_RANGE_NOT_SATISFIABLE;
    public static final HttpResponseStatus EXPECTATION_FAILED;
    public static final HttpResponseStatus UNPROCESSABLE_ENTITY;
    public static final HttpResponseStatus LOCKED;
    public static final HttpResponseStatus FAILED_DEPENDENCY;
    public static final HttpResponseStatus UNORDERED_COLLECTION;
    public static final HttpResponseStatus UPGRADE_REQUIRED;
    public static final HttpResponseStatus INTERNAL_SERVER_ERROR;
    public static final HttpResponseStatus NOT_IMPLEMENTED;
    public static final HttpResponseStatus BAD_GATEWAY;
    public static final HttpResponseStatus SERVICE_UNAVAILABLE;
    public static final HttpResponseStatus GATEWAY_TIMEOUT;
    public static final HttpResponseStatus HTTP_VERSION_NOT_SUPPORTED;
    public static final HttpResponseStatus VARIANT_ALSO_NEGOTIATES;
    public static final HttpResponseStatus INSUFFICIENT_STORAGE;
    public static final HttpResponseStatus NOT_EXTENDED;
    private final int code;
    private final String reasonPhrase;
    
    public static HttpResponseStatus valueOf(final int code) {
        switch (code) {
            case 100: {
                return HttpResponseStatus.CONTINUE;
            }
            case 101: {
                return HttpResponseStatus.SWITCHING_PROTOCOLS;
            }
            case 102: {
                return HttpResponseStatus.PROCESSING;
            }
            case 200: {
                return HttpResponseStatus.OK;
            }
            case 201: {
                return HttpResponseStatus.CREATED;
            }
            case 202: {
                return HttpResponseStatus.ACCEPTED;
            }
            case 203: {
                return HttpResponseStatus.NON_AUTHORITATIVE_INFORMATION;
            }
            case 204: {
                return HttpResponseStatus.NO_CONTENT;
            }
            case 205: {
                return HttpResponseStatus.RESET_CONTENT;
            }
            case 206: {
                return HttpResponseStatus.PARTIAL_CONTENT;
            }
            case 207: {
                return HttpResponseStatus.MULTI_STATUS;
            }
            case 300: {
                return HttpResponseStatus.MULTIPLE_CHOICES;
            }
            case 301: {
                return HttpResponseStatus.MOVED_PERMANENTLY;
            }
            case 302: {
                return HttpResponseStatus.FOUND;
            }
            case 303: {
                return HttpResponseStatus.SEE_OTHER;
            }
            case 304: {
                return HttpResponseStatus.NOT_MODIFIED;
            }
            case 305: {
                return HttpResponseStatus.USE_PROXY;
            }
            case 307: {
                return HttpResponseStatus.TEMPORARY_REDIRECT;
            }
            case 400: {
                return HttpResponseStatus.BAD_REQUEST;
            }
            case 401: {
                return HttpResponseStatus.UNAUTHORIZED;
            }
            case 402: {
                return HttpResponseStatus.PAYMENT_REQUIRED;
            }
            case 403: {
                return HttpResponseStatus.FORBIDDEN;
            }
            case 404: {
                return HttpResponseStatus.NOT_FOUND;
            }
            case 405: {
                return HttpResponseStatus.METHOD_NOT_ALLOWED;
            }
            case 406: {
                return HttpResponseStatus.NOT_ACCEPTABLE;
            }
            case 407: {
                return HttpResponseStatus.PROXY_AUTHENTICATION_REQUIRED;
            }
            case 408: {
                return HttpResponseStatus.REQUEST_TIMEOUT;
            }
            case 409: {
                return HttpResponseStatus.CONFLICT;
            }
            case 410: {
                return HttpResponseStatus.GONE;
            }
            case 411: {
                return HttpResponseStatus.LENGTH_REQUIRED;
            }
            case 412: {
                return HttpResponseStatus.PRECONDITION_FAILED;
            }
            case 413: {
                return HttpResponseStatus.REQUEST_ENTITY_TOO_LARGE;
            }
            case 414: {
                return HttpResponseStatus.REQUEST_URI_TOO_LONG;
            }
            case 415: {
                return HttpResponseStatus.UNSUPPORTED_MEDIA_TYPE;
            }
            case 416: {
                return HttpResponseStatus.REQUESTED_RANGE_NOT_SATISFIABLE;
            }
            case 417: {
                return HttpResponseStatus.EXPECTATION_FAILED;
            }
            case 422: {
                return HttpResponseStatus.UNPROCESSABLE_ENTITY;
            }
            case 423: {
                return HttpResponseStatus.LOCKED;
            }
            case 424: {
                return HttpResponseStatus.FAILED_DEPENDENCY;
            }
            case 425: {
                return HttpResponseStatus.UNORDERED_COLLECTION;
            }
            case 426: {
                return HttpResponseStatus.UPGRADE_REQUIRED;
            }
            case 500: {
                return HttpResponseStatus.INTERNAL_SERVER_ERROR;
            }
            case 501: {
                return HttpResponseStatus.NOT_IMPLEMENTED;
            }
            case 502: {
                return HttpResponseStatus.BAD_GATEWAY;
            }
            case 503: {
                return HttpResponseStatus.SERVICE_UNAVAILABLE;
            }
            case 504: {
                return HttpResponseStatus.GATEWAY_TIMEOUT;
            }
            case 505: {
                return HttpResponseStatus.HTTP_VERSION_NOT_SUPPORTED;
            }
            case 506: {
                return HttpResponseStatus.VARIANT_ALSO_NEGOTIATES;
            }
            case 507: {
                return HttpResponseStatus.INSUFFICIENT_STORAGE;
            }
            case 510: {
                return HttpResponseStatus.NOT_EXTENDED;
            }
            default: {
                String reasonPhrase;
                if (code < 100) {
                    reasonPhrase = "Unknown Status";
                }
                else if (code < 200) {
                    reasonPhrase = "Informational";
                }
                else if (code < 300) {
                    reasonPhrase = "Successful";
                }
                else if (code < 400) {
                    reasonPhrase = "Redirection";
                }
                else if (code < 500) {
                    reasonPhrase = "Client Error";
                }
                else if (code < 600) {
                    reasonPhrase = "Server Error";
                }
                else {
                    reasonPhrase = "Unknown Status";
                }
                return new HttpResponseStatus(code, reasonPhrase + " (" + code + ')');
            }
        }
    }
    
    public HttpResponseStatus(final int code, final String reasonPhrase) {
        if (code < 0) {
            throw new IllegalArgumentException("code: " + code + " (expected: 0+)");
        }
        if (reasonPhrase == null) {
            throw new NullPointerException("reasonPhrase");
        }
        int i = 0;
        while (i < reasonPhrase.length()) {
            final char c = reasonPhrase.charAt(i);
            switch (c) {
                case '\n':
                case '\r': {
                    throw new IllegalArgumentException("reasonPhrase contains one of the following prohibited characters: \\r\\n: " + reasonPhrase);
                }
                default: {
                    ++i;
                    continue;
                }
            }
        }
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }
    
    public int code() {
        return this.code;
    }
    
    public String reasonPhrase() {
        return this.reasonPhrase;
    }
    
    @Override
    public int hashCode() {
        return this.code();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof HttpResponseStatus && this.code() == ((HttpResponseStatus)o).code();
    }
    
    @Override
    public int compareTo(final HttpResponseStatus o) {
        return this.code() - o.code();
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder(this.reasonPhrase.length() + 5);
        buf.append(this.code);
        buf.append(' ');
        buf.append(this.reasonPhrase);
        return buf.toString();
    }
    
    static {
        CONTINUE = new HttpResponseStatus(100, "Continue");
        SWITCHING_PROTOCOLS = new HttpResponseStatus(101, "Switching Protocols");
        PROCESSING = new HttpResponseStatus(102, "Processing");
        OK = new HttpResponseStatus(200, "OK");
        CREATED = new HttpResponseStatus(201, "Created");
        ACCEPTED = new HttpResponseStatus(202, "Accepted");
        NON_AUTHORITATIVE_INFORMATION = new HttpResponseStatus(203, "Non-Authoritative Information");
        NO_CONTENT = new HttpResponseStatus(204, "No Content");
        RESET_CONTENT = new HttpResponseStatus(205, "Reset Content");
        PARTIAL_CONTENT = new HttpResponseStatus(206, "Partial Content");
        MULTI_STATUS = new HttpResponseStatus(207, "Multi-Status");
        MULTIPLE_CHOICES = new HttpResponseStatus(300, "Multiple Choices");
        MOVED_PERMANENTLY = new HttpResponseStatus(301, "Moved Permanently");
        FOUND = new HttpResponseStatus(302, "Found");
        SEE_OTHER = new HttpResponseStatus(303, "See Other");
        NOT_MODIFIED = new HttpResponseStatus(304, "Not Modified");
        USE_PROXY = new HttpResponseStatus(305, "Use Proxy");
        TEMPORARY_REDIRECT = new HttpResponseStatus(307, "Temporary Redirect");
        BAD_REQUEST = new HttpResponseStatus(400, "Bad Request");
        UNAUTHORIZED = new HttpResponseStatus(401, "Unauthorized");
        PAYMENT_REQUIRED = new HttpResponseStatus(402, "Payment Required");
        FORBIDDEN = new HttpResponseStatus(403, "Forbidden");
        NOT_FOUND = new HttpResponseStatus(404, "Not Found");
        METHOD_NOT_ALLOWED = new HttpResponseStatus(405, "Method Not Allowed");
        NOT_ACCEPTABLE = new HttpResponseStatus(406, "Not Acceptable");
        PROXY_AUTHENTICATION_REQUIRED = new HttpResponseStatus(407, "Proxy Authentication Required");
        REQUEST_TIMEOUT = new HttpResponseStatus(408, "Request Timeout");
        CONFLICT = new HttpResponseStatus(409, "Conflict");
        GONE = new HttpResponseStatus(410, "Gone");
        LENGTH_REQUIRED = new HttpResponseStatus(411, "Length Required");
        PRECONDITION_FAILED = new HttpResponseStatus(412, "Precondition Failed");
        REQUEST_ENTITY_TOO_LARGE = new HttpResponseStatus(413, "Request Entity Too Large");
        REQUEST_URI_TOO_LONG = new HttpResponseStatus(414, "Request-URI Too Long");
        UNSUPPORTED_MEDIA_TYPE = new HttpResponseStatus(415, "Unsupported Media Type");
        REQUESTED_RANGE_NOT_SATISFIABLE = new HttpResponseStatus(416, "Requested Range Not Satisfiable");
        EXPECTATION_FAILED = new HttpResponseStatus(417, "Expectation Failed");
        UNPROCESSABLE_ENTITY = new HttpResponseStatus(422, "Unprocessable Entity");
        LOCKED = new HttpResponseStatus(423, "Locked");
        FAILED_DEPENDENCY = new HttpResponseStatus(424, "Failed Dependency");
        UNORDERED_COLLECTION = new HttpResponseStatus(425, "Unordered Collection");
        UPGRADE_REQUIRED = new HttpResponseStatus(426, "Upgrade Required");
        INTERNAL_SERVER_ERROR = new HttpResponseStatus(500, "Internal Server Error");
        NOT_IMPLEMENTED = new HttpResponseStatus(501, "Not Implemented");
        BAD_GATEWAY = new HttpResponseStatus(502, "Bad Gateway");
        SERVICE_UNAVAILABLE = new HttpResponseStatus(503, "Service Unavailable");
        GATEWAY_TIMEOUT = new HttpResponseStatus(504, "Gateway Timeout");
        HTTP_VERSION_NOT_SUPPORTED = new HttpResponseStatus(505, "HTTP Version Not Supported");
        VARIANT_ALSO_NEGOTIATES = new HttpResponseStatus(506, "Variant Also Negotiates");
        INSUFFICIENT_STORAGE = new HttpResponseStatus(507, "Insufficient Storage");
        NOT_EXTENDED = new HttpResponseStatus(510, "Not Extended");
    }
}
