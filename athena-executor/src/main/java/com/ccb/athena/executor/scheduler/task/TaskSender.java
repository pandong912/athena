package com.ccb.athena.executor.scheduler.task;

import com.alibaba.fastjson.JSONObject;
import com.ccb.athena.executor.scheduler.ductsume.RedisProductor;
import com.ccb.athena.executor.scheduler.jdbc.mongo.MongoConnector;
import com.ccb.athena.executor.scheduler.jdbc.mongo.MongoIterable;
import com.ccb.athena.executor.scheduler.jdbc.mongo.MongoOperator;
import com.ccb.athena.executor.scheduler.jdbc.redis.RedisConnector;

public class TaskSender {
	
	public static final String SORT = "time";
	
	public static final JSONObject END_VALUE = new JSONObject();
	
	public static final String[] FIELDS = {"uid", "serviceId", "request", "response", "respCode"};
	
	private MongoConnector mongoConnector;
	
	private RedisConnector redisConnector;
	
	public TaskSender(MongoConnector mongoConnector, RedisConnector redisConnector) {
		super();
		this.mongoConnector = mongoConnector;
		this.redisConnector = redisConnector;
	}

	public void process(String id) throws Exception {
		MongoOperator operator = mongoConnector.getOperator("task");
		JSONObject task = operator.findOneBy(id);
		String redisKey = createNodes(task);
		MongoIterable autoCases = findCases(task);
		product(redisKey, autoCases);
	}

	private String createNodes(JSONObject task) throws Exception {
		TaskKeeper keeper = new TaskKeeper("127.0.0.1:2181", "/ltr/task");
		String taskName = task.getString("name");
		String pushKey = keeper.register("save", taskName, null);
		String sendNodeName = keeper.register("send", taskName, pushKey);
		keeper.close();
		return sendNodeName;
	}

	private MongoIterable findCases(JSONObject task) {
		// mongo:uid serviceId request respect respCode
		// consumer1: uid serviceId request response  [respect respCode] 
		// : uid response respect respCode
		MongoOperator operator = mongoConnector.getOperator(task.getString("collection"));
		MongoIterable autoCases = operator
				.findMany(task.getString("filter"))
				.projection(FIELDS)
				.sort("{orders:1}");
		
		String sort = task.getString("sort");
//		if (!AssertWorker.clamp(sort).isBlank()) {
			autoCases = autoCases.sort(sort);
//		}
		Integer limit = task.getInteger("limit");
		if (null != limit) {
			autoCases = autoCases.limit(limit);
		}
		return autoCases;
	}

	private void product(String redisKey, MongoIterable autoCases) {
		RedisProductor productor = new RedisProductor(redisKey, redisConnector);
		for (JSONObject autoCase : autoCases) {
			productor.store(autoCase);
		}
		productor.store(END_VALUE);
	}
	
	public static void main(String[] args) throws Exception {
		MongoConnector mongoConnector = new MongoConnector();
		mongoConnector.connect("127.0.0.1", 27017, "ltr");
		RedisConnector redisConnector = new RedisConnector();
		redisConnector.connect("127.0.0.1", 6379, null);
		
		TaskSender sender = new TaskSender(mongoConnector, redisConnector);
		sender.process("000001");
		
		mongoConnector.disconnect();
		redisConnector.disconnect();
	}
}
