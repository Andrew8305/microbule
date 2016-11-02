package org.microbule.errormap.impl;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.RedirectionException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class WebApplicationExceptions {
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private static final Map<Integer, Class<? extends WebApplicationException>> EXCEPTIONS_MAP = new HashMap<>();

//----------------------------------------------------------------------------------------------------------------------
// Static Methods
//----------------------------------------------------------------------------------------------------------------------

    static {
        EXCEPTIONS_MAP.put(400, BadRequestException.class);
        EXCEPTIONS_MAP.put(401, NotAuthorizedException.class);
        EXCEPTIONS_MAP.put(403, ForbiddenException.class);
        EXCEPTIONS_MAP.put(404, NotFoundException.class);
        EXCEPTIONS_MAP.put(405, NotAllowedException.class);
        EXCEPTIONS_MAP.put(406, NotAcceptableException.class);
        EXCEPTIONS_MAP.put(415, NotSupportedException.class);
        EXCEPTIONS_MAP.put(500, InternalServerErrorException.class);
        EXCEPTIONS_MAP.put(503, ServiceUnavailableException.class);
    }

    public static Class<? extends WebApplicationException> getWebApplicationExceptionClass(Response response) {
        int status = response.getStatus();
        Class<? extends WebApplicationException> cls = EXCEPTIONS_MAP.get(status);
        if (cls == null) {
            int family = status / 100;
            if (family == 3) {
                cls = RedirectionException.class;
            } else if (family == 4) {
                cls = ClientErrorException.class;
            } else if (family == 5) {
                cls = ServerErrorException.class;
            }
        }
        return cls == null ? WebApplicationException.class : cls;
    }
}
