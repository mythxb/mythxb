package com.common.utils.mongodb;

import com.common.utils.DateUtils;
import com.common.utils.mongodb.utils.JSONUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MongoDBClient {

	// private MongoDBJDBC mongoDBJDBC = new MongoDBJDBC("127.0.0.1",
	// "Myth","user","user@user");//本地
	// private MongoDBJDBC mongoDBJDBC = new MongoDBJDBC("127.0.0.1", "Myth");
	static MongoDatabase mongoDatabase = null;
	static{
		try {
			MongoDBJDBC mongoDBJDBC = new MongoDBJDBC("118.123.247.70", "SafeAlert", "anao", "anaoMongodbPWD2017",27017);// 煤矿
			mongoDatabase = mongoDBJDBC.connectMongoDBByname();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 创建集合
	 * 
	 * @param collectName
	 */
	public void createCollection(String collectName) throws Exception {
		try {
//			MongoDatabase mongoDatabase = mongoDBJDBC.connectMongoDB();
			mongoDatabase.createCollection(collectName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取集合
	 * 
	 * @param collectName
	 * @return
	 */
	public MongoCollection<Document> getCollection(String collectName) throws Exception {
		MongoCollection<Document> collection = null;
		// MongoDatabase mongoDatabase = mongoDBJDBC.connectMongoDB();
//		MongoDatabase mongoDatabase = mongoDBJDBC.connectMongoDBByname();
		collection = mongoDatabase.getCollection(collectName);
		/*
		 * MongoIterable<String> colls = mongoDatabase.listCollectionNames();
		 * for (String str : colls) {
		 * 
		 * }
		 */
		return collection;
	}

	public void insertDocument(String collectName, String str) throws Exception {
//		MongoDatabase mongoDatabase = mongoDBJDBC.connectMongoDB();
		MongoCollection<Document> collection = mongoDatabase.getCollection(collectName);
		System.out.println("集合 test 选择成功");
		// 插入文档
		/**
		 * 1. 创建文档 org.bson.Document 参数为key-value的格式 2. 创建文档集合List<Document> 3.
		 * 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用
		 * mongoCollection.insertOne(Document)
		 */
		Document document = new Document();
		Map<String, String> map = JSONUtils.jsonStrtoMap(str);
		if (null != map && map.size() > 0) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				document.append(entry.getKey(), entry.getValue());
			}
		}
		List<Document> documents = new ArrayList<Document>();
		documents.add(document);
		collection.insertMany(documents);
		System.out.println("文档插入成功");
	}

	/**
	 * 更新文档
	 * 
	 * @param collectName
	 * @param
	 * @throws Exception
	 */
	public void updateDocument(String collectName, String beforeKey, String beforeValue, String afterKey,
			String afterValue) throws Exception {
//		MongoDatabase mongoDatabase = mongoDBJDBC.connectMongoDB();
		MongoCollection<Document> collection = mongoDatabase.getCollection(collectName);
		collection.updateMany(Filters.eq(beforeKey, beforeValue),
				new Document("$set", new Document(afterKey, afterValue)));
		// 检索查看结果
		FindIterable<Document> findIterable = collection.find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while (mongoCursor.hasNext()) {
			System.out.println(mongoCursor.next());
		}
	}

	/**
	 * 删除文档
	 * 
	 * @param collectName
	 * @param key
	 * @param value
	 * @param type
	 *            1：删除符合条件的第一个文档 : 2：删除所有符合条件的文档
	 * @throws Exception
	 */
	public void delDocument(String collectName, String key, String value, Integer type) throws Exception {
//		MongoDatabase mongoDatabase = mongoDBJDBC.connectMongoDB();
		MongoCollection<Document> collection = mongoDatabase.getCollection(collectName);
		if (1 == type) {
			// 删除符合条件的第一个文档
			collection.deleteOne(Filters.eq(key, value));
		} else if (2 == type) {
			// 删除所有符合条件的文档
			collection.deleteMany(Filters.eq(key, value));
		}
		// 检索查看结果
		FindIterable<Document> findIterable = collection.find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while (mongoCursor.hasNext()) {
			System.out.println(mongoCursor.next());
		}
	}

	/**
	 * 查询mongodb 数据 
	 * @param query 条件
	 * @param collName 集合名
	 * @param limit 查询条数  小于0 或 null 无限制
	 * @return
	 */
	public static List<Document> getByCollNmae(BasicDBObject query, String collName,Integer limit) {
		List<Document> result = new ArrayList<Document>();
		try {
			MongoDBClient mongoDBClient = new MongoDBClient();
			MongoCollection<Document> collection = mongoDBClient.getCollection("DevRtData");
			FindIterable<Document> findIterable = collection.find(query);
			if(null != limit && limit > 0){
				findIterable.limit(limit);
			}
			for (Document document : findIterable) {
				result.add(document);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	public static void main(String[] args) {
		Long start = System.currentTimeMillis();
		BasicDBObject query =  new BasicDBObject();
		query.append("AdministrativeCode", "0202");
		query.append("sensorID", "0202007000005");
		query.append("generationDate", new BasicDBObject("$gte", DateUtils.parseCalendar2Date(DateUtils.getDateTimeStr("2017-06-28 00:00:00", "yyyy-MM-dd HH:mm:ss")))
				.append("$lte", DateUtils.parseCalendar2Date(DateUtils.getDateTimeStr("2017-06-28 12:00:00", "yyyy-MM-dd HH:mm:ss"))));
		System.out.println("query = "+query);
		List<Document> findAllB = getByCollNmae(query, "DevRtData",0);
		System.out.println(findAllB.size());
		/*for(Document doc : findAllB){
			System.out.println(doc.get("coalMineID"));
		}*/
		System.out.println(findAllB.size());
		try {
			/*
			 * MongoDBClient mongoDBClient = new MongoDBClient();
			 * MongoCollection<Document> collection =
			 * mongoDBClient.getCollection("DevRtData"); FindIterable<Document>
			 * findIterable = collection.find(Filters.eq("currValue",
			 * "0.2199999988079071")); // FindIterable<Document> findIterable =
			 * collection.find(); MongoCursor<Document> cursor1 =
			 * collection.find(Filters.eq("currValue", "weixin")).iterator();
			 * MongoCursor<Document> mongoCursor = findIterable.iterator(); Long
			 * size = 1l; while(mongoCursor.hasNext()){ size++;
			 * System.out.println(mongoCursor.next()); System.out.println(size);
			 * }
			 * 
			 * // mongoDBClient.insertDocument("myth",
			 * "{\"title\":\"dongtest\",\"age\":\"35\"}"); //
			 * mongoDBClient.updateDocument("myth", "title", "dongtestss",
			 * "age", "40"); // mongoDBClient.delDocument("myth", "title",
			 * "myths",2);
			 */ } catch (Exception e) {
			e.printStackTrace();
		}

		Long end = System.currentTimeMillis();
		Long reslt = end - start;
		System.err.println("请求秒数 = " + DateUtils.timeSub(reslt));
	}

}
