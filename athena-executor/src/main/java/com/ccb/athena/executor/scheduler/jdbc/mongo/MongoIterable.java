package com.ccb.athena.executor.scheduler.jdbc.mongo;

import org.apache.commons.lang3.StringUtils;
import org.bson.BsonBoolean;
import org.bson.BsonDocument;
import org.bson.Document;

import com.alibaba.fastjson.JSONObject;
import com.ccb.athena.executor.scheduler.jdbc.DbIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

public class MongoIterable implements DbIterable<JSONObject> {
	
	private FindIterable<Document> docs;

	public MongoIterable(FindIterable<Document> docs) {
		super();
		this.docs = docs;
	}

	public MongoIterable sort(String sort) {
		if (StringUtils.isNotBlank(sort)) {
			this.docs = docs.sort(BsonDocument.parse(sort));
		}
		return this;
	}

	public MongoIterable limit(int num) {
		if (num > 0) {
			this.docs = docs.limit(num);
		}
		return this;
	}
	
	public MongoIterable projection(String[] fields) {
		BsonDocument bson = new BsonDocument();
		for (String field : fields) {
			bson.put(field, new BsonBoolean(true));
		}
		this.docs = docs.projection(bson);
		return this;
	}

	public MongoIterator iterator() {
		MongoCursor<Document> iterator = docs.iterator();
		return new MongoIterator(iterator);
	}

	public FindIterable<Document> getDocs() {
		return docs;
	}

}