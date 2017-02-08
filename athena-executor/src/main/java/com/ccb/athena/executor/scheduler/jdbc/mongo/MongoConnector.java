package com.ccb.athena.executor.scheduler.jdbc.mongo;

import org.bson.Document;

import com.ccb.athena.executor.scheduler.jdbc.DbConnector;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoConnector implements DbConnector<MongoOperator> {
	
	private MongoClient client;
	
	private MongoDatabase database;
	
	public void connect(String host, int port, String database) {
		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
		builder.socketTimeout(900000);
		this.client = new MongoClient(new ServerAddress(host, port), builder.build());
		this.database = client.getDatabase(database);
	}

	public MongoOperator getOperator(String collectionName) {
		MongoCollection<Document> collection = database.getCollection(collectionName);
		return new MongoOperator(collection);
	}

	public void disconnect() {
		client.close();
	}

}
