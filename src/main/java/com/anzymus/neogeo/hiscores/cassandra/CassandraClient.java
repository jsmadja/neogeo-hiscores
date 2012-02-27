package com.anzymus.neogeo.hiscores.cassandra;

import static org.apache.cassandra.thrift.ConsistencyLevel.ONE;

import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.IndexClause;
import org.apache.cassandra.thrift.IndexExpression;
import org.apache.cassandra.thrift.IndexOperator;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.thrift.KeySlice;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.apache.cassandra.thrift.TBinaryProtocol;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class CassandraClient {

	private Cassandra.Client client;
	private TTransport transport;

	public CassandraClient(String host, int port, String keyspace) {
		try {
			transport = new TFramedTransport(new TSocket(host, port));
			transport.open();
		} catch (TTransportException e) {
			throw new IllegalStateException("Cannot initialize client factory");
		}

		TProtocol protocol = new TBinaryProtocol(transport);
		client = new Cassandra.Client(protocol);
		try {
			client.set_keyspace(keyspace);
		} catch (InvalidRequestException e) {
			throw new IllegalStateException("Cannot create client for keyspace " + keyspace, e);
		} catch (TException e) {
			throw new IllegalStateException("Cannot create client for keyspace " + keyspace, e);
		}
	}

	public Cassandra.Client getClient() {
		return client;
	}

	public void insert(String typeName, String keyValue, String columnName, String value) throws NotInsertedException {
		ColumnParent type = new ColumnParent(typeName);
		ByteBuffer key = ByteBuffer.wrap(keyValue.getBytes());
		Column column = new Column();
		column.setName(columnName.getBytes());
		column.setValue(value.getBytes());
		column.setTimestamp(System.currentTimeMillis());
		try {
			client.insert(key, type, column, ONE);
		} catch (Exception e) {
			String message = MessageFormat.format(
					"Cannot insert typeName:{0}, keyValue:{1}, columnName:{2}, value:{3}", typeName, keyValue,
					columnName, value);
			throw new NotInsertedException(message, e);
		}
	}

	public String get(String typeName, String keyValue, String columnName) throws InvalidRequestException,
			UnavailableException, TimedOutException, TException {
		try {
			ColumnPath columnPath = new ColumnPath(typeName);
			columnPath.setColumn(columnName.getBytes());
			ByteBuffer key = ByteBuffer.wrap(keyValue.getBytes());
			ColumnOrSuperColumn column = client.get(key, columnPath, ONE);
			byte[] value = column.getColumn().getValue();
			return new String(value);
		} catch (NotFoundException e) {
			return "";
		}
	}

	public void close() {
		try {
			transport.flush();
		} catch (TTransportException e) {
			throw new IllegalStateException("Cannot flush factory");
		}
		transport.close();
	}

	public Cassandra.Client getTrueClient() {
		return client;
	}

	public Map<String, String> getValuesAsMap(String type, String key) throws InvalidRequestException,
			UnavailableException, TimedOutException, TException {
		ByteBuffer wrap = ByteBuffer.wrap(new byte[0]);
		SliceRange sliceRange = new SliceRange(wrap, wrap, false, 100);
		SlicePredicate predicate = new SlicePredicate();
		predicate.setSlice_range(sliceRange);
		ColumnParent parent = new ColumnParent(type);
		KeyRange keyRange = new KeyRange(100);
		keyRange.setStart_key(new byte[0]);
		keyRange.setEnd_key(new byte[0]);
		List<KeySlice> keySlices = client.get_range_slices(parent, predicate, keyRange, ConsistencyLevel.ONE);
		return asMap(key, keySlices);
	}

	public Map<String, String> getValuesAsMap2(String type, String key) throws InvalidRequestException,
			UnavailableException, TimedOutException, TException {
		ByteBuffer wrap = ByteBuffer.wrap(new byte[0]);
		SliceRange sliceRange = new SliceRange(wrap, wrap, false, 100);

		SlicePredicate predicate = new SlicePredicate();
		predicate.setSlice_range(sliceRange);

		ColumnParent parent = new ColumnParent(type);

		KeyRange keyRange = new KeyRange(100);
		keyRange.setStart_key(new byte[0]);
		keyRange.setEnd_key(new byte[0]);

		IndexExpression expression = new IndexExpression();
		expression.setColumn_name(new byte[0]);
		expression.setOp(IndexOperator.LTE);
		expression.setValue(new byte[0]);

		IndexClause indexClause = new IndexClause();
		indexClause.setStart_key(key.getBytes());
		indexClause.addToExpressions(expression);

		List<KeySlice> keySlices = client.get_indexed_slices(parent, indexClause, predicate, ConsistencyLevel.ONE);
		return asMap(keySlices);
	}

	private Map<String, String> asMap(List<KeySlice> keySlices) {
		Map<String, String> map = new HashMap<String, String>();
		for (KeySlice keySlice : keySlices) {
			List<ColumnOrSuperColumn> columns = keySlice.getColumns();
			for (ColumnOrSuperColumn columnOrSuperColumn : columns) {
				Column column = columnOrSuperColumn.getColumn();
				String name = new String(column.getName());
				String value = new String(column.getValue());
				map.put(name, value);
			}
		}
		return map;
	}

	private Map<String, String> asMap(String key, List<KeySlice> keySlices) {
		Map<String, String> map = new HashMap<String, String>();
		for (KeySlice keySlice : keySlices) {
			String sliceKey = new String(keySlice.getKey());
			if (key.equals(sliceKey)) {
				List<ColumnOrSuperColumn> columns = keySlice.getColumns();
				for (ColumnOrSuperColumn columnOrSuperColumn : columns) {
					Column column = columnOrSuperColumn.getColumn();
					String name = new String(column.getName());
					String value = new String(column.getValue());
					map.put(name, value);
				}
				return map;
			}
		}
		return map;
	}
}