package org.microbule.decorator.errormap;

import javax.ws.rs.ext.Provider;

import org.microbule.errormap.api.ErrorMapperService;

@Provider
public class RootExceptionMapper extends ErrorMapperExceptionMapper<Exception> {
//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public RootExceptionMapper(ErrorMapperService errorMapperService) {
        super(errorMapperService);
    }
}
