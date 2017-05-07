package org.microbule.config.env;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.microbule.config.api.Config;

public class EnvironmentVariablesConfigProviderTest extends Assert {
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    @Test
    public void testGetServerConfig() {
        Map<String, String> env = new HashMap<>();
        env.put("one_two_foo", "bar");
        EnvironmentVariablesConfigProvider provider = new EnvironmentVariablesConfigProvider(() -> env);

        final Config config = provider.getConfig("one", "two");
        assertEquals("bar", config.value("foo").get());
    }
}