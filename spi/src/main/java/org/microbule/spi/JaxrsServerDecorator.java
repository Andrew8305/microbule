package org.microbule.spi;

public interface JaxrsServerDecorator {
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    void decorate(JaxrsServer server, JaxrsServerProperties properties);
}
