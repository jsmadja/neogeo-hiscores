package com.neogeohiscores.common.clients;

import junit.framework.Assert;
import org.junit.Test;

public class NeoGeoFansClientIT {

    @Test
    public void should_post() throws AuthenticationFailed {
        NeoGeoFansClient client = new NeoGeoFansClient();
        boolean authenticate = client.authenticate("anzymus", "password");
        Assert.assertTrue(authenticate);
        client.post("test");
    }

}
