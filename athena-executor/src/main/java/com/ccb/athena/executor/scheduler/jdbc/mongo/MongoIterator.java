package com.ccb.athena.executor.scheduler.jdbc.mongo;

import java.util.Iterator;

import org.bson.Document;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.MongoCursor;

public class MongoIterator implements Iterator<JSONObject> {
	
	private MongoCursor<Document> cursor;

	public MongoIterator(MongoCursor<Document> cursor) {
		super();
		this.cursor = cursor;
	}

	public boolean hasNext() {
		return cursor.hasNext();
	}

	public JSONObject next() {
		Document next = cursor.next();
		return JSONObject.parseObject(next.toJson());
	}

}
