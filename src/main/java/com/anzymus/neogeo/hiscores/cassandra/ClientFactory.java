package com.anzymus.neogeo.hiscores.cassandra;


public class ClientFactory {

	private String host;
	private int port;

	public ClientFactory(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public CassandraClient create(String keyspace) {
		return new CassandraClient(host, port, keyspace);
	}

}
