package org.microbule.decorator.errormap;

import org.microbule.spi.JaxrsServer;
import org.microbule.spi.JaxrsServerDecorator;
import org.microbule.spi.JaxrsServerProperties;

public class ErrorMapperDecorator implements JaxrsServerDecorator {
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private final ErrorMapperService errorMapperService;

//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public ErrorMapperDecorator(ErrorMapperService errorMapperService) {
        this.errorMapperService = errorMapperService;
    }

//----------------------------------------------------------------------------------------------------------------------
// JaxrsServerDecorator Implementation
//----------------------------------------------------------------------------------------------------------------------


    @Override
    public void decorate(JaxrsServer server, JaxrsServerProperties properties) {
        server.addProvider(new WebApplicationExceptionMapper(errorMapperService));
        server.addProvider(new RootExceptionMapper(errorMapperService));
    }
}