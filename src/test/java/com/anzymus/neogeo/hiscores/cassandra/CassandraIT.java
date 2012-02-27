package com.anzymus.neogeo.hiscores.cassandra;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CassandraIT {

	CassandraClient tutorials;
	CassandraClient neogeo;

	@Before
	public void init() {
		ClientFactory factory = new ClientFactory("127.0.0.1", 9160);
		tutorials = factory.create("tutorials");
		neogeo = factory.create("neogeo");
	}

	@After
	public void tearDown() {
		tutorials.close();
		neogeo.close();
	}

	@Test
	public void should_store_in_cassandra() throws Exception {
		tutorials.insert("User", "100", "prenom", "Bob");
		tutorials.insert("User", "100", "nom", "Wilson");

		tutorials.insert("User", "101", "prenom", "Geese");
		tutorials.insert("User", "101", "nom", "Howard");
		tutorials.insert("User", "101", "age", "50");

		assertEquals("Bob", tutorials.get("User", "100", "prenom"));
		assertEquals("Geese", tutorials.get("User", "101", "prenom"));

		assertEquals("Wilson", tutorials.get("User", "100", "nom"));
		assertEquals("Howard", tutorials.get("User", "101", "nom"));

		assertEquals("", tutorials.get("User", "100", "age"));
		assertEquals("50", tutorials.get("User", "101", "age"));

		neogeo.insert("Game", "1", "title", "Fatal Fury");
		assertEquals("Fatal Fury", neogeo.get("Game", "1", "title"));
	}

	@Test
	public void should_store_game_scores() throws Exception {
		neogeo.insert("Game", "FatalFury-MVS", "1st", "Bob");
		neogeo.insert("Game", "FatalFury-MVS", "2nd", "Mike");
		neogeo.insert("Game", "FatalFury-MVS", "3rd", "Anz");

		Map<String, String> map = neogeo.getValuesAsMap("Game", "FatalFury-MVS");
		assertEquals("Mike", map.get("2nd"));
		assertEquals("Bob", map.get("1st"));
		assertEquals("Anz", map.get("3rd"));

		map = neogeo.getValuesAsMap2("Game", "FatalFury-MVS");
		assertEquals("Mike", map.get("2nd"));
		assertEquals("Bob", map.get("1st"));
		assertEquals("Anz", map.get("3rd"));
	}

}