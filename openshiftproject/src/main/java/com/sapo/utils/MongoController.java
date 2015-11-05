package com.sapo.utils;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.sapo.datatypes.DataProducto;


public class MongoController {

	private static String MongoURL = "mongodb://tsi2:tsi2@ds043962.mongolab.com:43962/krakenmongo";
	private static String dbstr = "krakenmongo";
	private static String collection = "test";
	
	public class ThreadRemoveTag implements Runnable {
		
		private List<String> tag;
		private Long id;
		
		public ThreadRemoveTag(List<String> tag, Long id){
			this.tag= tag;
			this.id= id;
		} 
		
	    public void run(){
	    	MongoClient mongoClient = new MongoClient(new MongoClientURI(MongoURL));
			MongoDatabase db = mongoClient.getDatabase(dbstr);
			FindIterable<Document> iterable= db.getCollection(collection).find(eq("rdbms_id",id));
			iterable.forEach(new Block<Document>() {
			    @Override
			    public void apply(final Document document) {
			        System.out.println(document);
			    }
			});
			
			Document e = iterable.first();
			//append de los tags
			@SuppressWarnings("unchecked")
			HashSet<String> tags = new HashSet<>((List<String>) e.get("tags"));//(List<String>) e.get("tags");
			tags.removeAll(tag);
			e.replace("tags", tags);
			db.getCollection("test").replaceOne(new Document("rdbms_id",id), e);
			mongoClient.close();
	    }
	}
	
	public class ThreadAddTag implements Runnable {
		
		private List<String> tag;
		private Long id;
		
		public ThreadAddTag(List<String> tag, Long id){
			this.tag= tag;
			this.id= id;
		} 
		
	    public void run(){
			MongoClient mongoClient = new MongoClient(new MongoClientURI(MongoURL));
			MongoDatabase db = mongoClient.getDatabase(dbstr);
			FindIterable<Document> iterable= db.getCollection(collection).find(eq("rdbms_id",id));
			iterable.forEach(new Block<Document>() {
			    @Override
			    public void apply(final Document document) {
			        System.out.println(document);
			    }
			});
			
			Document e = iterable.first();
			//append de los tags
			@SuppressWarnings("unchecked")
			HashSet<String> tags = new HashSet<>((List<String>) e.get("tags"));//(List<String>) e.get("tags");
			tags.addAll(tag);
			e.replace("tags", tags);
			db.getCollection("test").replaceOne(new Document("rdbms_id",id), e);
			mongoClient.close();
			
	    }
	}	
	
	
	public class ThreadInsertProducto implements Runnable {
		
		DataProducto dp =null;
		
		public ThreadInsertProducto(DataProducto p){
			dp= p;
		} 
		
	    public void run(){
	    	
			MongoClient mongoClient = new MongoClient(new MongoClientURI(MongoURL));
			MongoDatabase db = mongoClient.getDatabase(dbstr);
			BasicDBObject o = new BasicDBObject();
			o.append("id", dp.getID());
			o.append("categoriaid", dp.getCategoria());
			o.append("nombre", dp.getNombre());
			o.append("descripcion", dp.getDescripcion());
			o.append("generico", dp.getIsgenerico());
			Document doc = new Document("rdbms_id",dp.getID())
					.append("rdbms_producto",o)
					.append("tags", new ArrayList<>());			
			db.getCollection(collection).insertOne(doc);
			mongoClient.close();
			
	    }
	}	
	
	public class ThreadUpdateProducto implements Runnable {
		
		private DataProducto dp;
		
		public ThreadUpdateProducto(DataProducto dp){
			this.dp=dp;
		} 
		
	    public void run(){
	    	MongoClient mongoClient = new MongoClient(new MongoClientURI(MongoURL));
			MongoDatabase db = mongoClient.getDatabase(dbstr);
			
			BasicDBObject o = new BasicDBObject();
			o.append("id", dp.getID());
			o.append("categoriaid", dp.getCategoria());
			o.append("nombre", dp.getNombre());
			o.append("descripcion", dp.getDescripcion());
			o.append("generico", dp.getIsgenerico());
			
			db.getCollection(collection).updateOne(new Document("rdbms_id",dp.getID()),new Document("$set",new Document("rdbms_producto",o)));
			
			mongoClient.close();
	    }
	}
	
	
	public void removeTags(List<String> tag, Long id){
		Thread t = new Thread(new ThreadRemoveTag(tag, id));
		t.start();
	}
	
	public void addTags(List<String> tag, Long id){
		Thread t = new Thread(new ThreadAddTag(tag, id));
		t.start();
	}
	
	public void addProduct(DataProducto dataproducto){
		Thread t = new Thread(new ThreadInsertProducto(dataproducto));
		t.start();
	}
	
	public void updateProduct(DataProducto dataproducto){
		Thread t = new Thread(new ThreadUpdateProducto(dataproducto));
		t.start();
	}
	
	public Document getProducto(Long id){
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MongoURL));
		MongoDatabase db = mongoClient.getDatabase(dbstr);
		FindIterable<Document> iterable= db.getCollection(collection).find(eq("rdbms_id",id));
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		        System.out.println(document);
		    }
		});
		
		Document e = iterable.first();
		mongoClient.close();
		return e;
	}
	
}


