package com.ccb.athena.executor.scheduler.jdbc.mongo;

import org.bson.BsonDocument;
import org.bson.Document;

import com.alibaba.fastjson.JSONObject;
import com.ccb.athena.executor.scheduler.jdbc.DbOperator;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class MongoOperator implements DbOperator<JSONObject> {
	
	private MongoCollection<Document> collection;

	public MongoOperator(MongoCollection<Document> collection) {
		super();
		this.collection = collection;
	}

	public JSONObject findOneBy(String id) {
		Document first = collection.find(Filters.eq("id", id)).first();
		return JSONObject.parseObject(first.toJson());
	}

	public MongoIterable findMany(String filter) {
		FindIterable<Document> docs = collection.find(BsonDocument.parse(filter));
		return new MongoIterable(docs);
	}

	public void save(Object object) {
		String json = JSONObject.toJSONString(object);
		Document document = Document.parse(json);
		collection.insertOne(document);
	}

	public MongoCollection<Document> getCollection() {
		return collection;
	}

}
