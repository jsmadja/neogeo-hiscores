package com.anzymus.neogeo.hiscores.clients;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class NeoGeoFansClientIT {

	@Test
	public void should_post_on_ngf() throws AuthenticationFailed {
		NeoGeoFansClient client = new NeoGeoFansClient();
		client.authenticate("anzymus", "xedy4bsa");
		client.post("message de test");
	}

}
