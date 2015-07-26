package net.horzelam.playground.cassandra;
import static java.lang.System.out;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class HiCassandraJava {
	public static void main(String[] args) {
		out.printf("Hello spark...");

		Cluster cluster = Cluster.builder().withPort(9042)
				.addContactPoint("10.0.2.15").build();

		// ------------------------- Cluster INFO
		Metadata metadata = cluster.getMetadata();
		out.printf("Connected to cluster:" + metadata.getClusterName());
		for (final Host host : metadata.getAllHosts()) {
			out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
					host.getDatacenter(), host.getAddress(), host.getRack());
		}
		out.printf("Cluster closed:%s \n", cluster.isClosed());

		// -------------------------- WRITE/READ operations:
		Session session = cluster.connect("videodb");

		session.execute("CREATE KEYSPACE IF NOT EXISTS test WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1 }");
		session.execute("CREATE TABLE IF NOT EXISTS test.key_value (key INT PRIMARY KEY, value VARCHAR)");
		session.execute("TRUNCATE test.key_value");
		session.execute("INSERT INTO test.key_value(key, value) VALUES (1, 'first row')");
		session.execute("INSERT INTO test.key_value(key, value) VALUES (2, 'second row')");
		session.execute("INSERT INTO test.key_value(key, value) VALUES (3, 'third row')");

		ResultSet result = session.execute("SELECT * FROM test.key_value;");
		for (Row row : result.all()) {
			out.printf("ROW: %s \n", row);
		}

		cluster.close();
		out.printf("FINISHED");

	}
}
