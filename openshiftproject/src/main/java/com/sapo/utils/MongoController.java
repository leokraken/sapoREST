package com.sapo.utils;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.sapo.datatypes.DataProducto;


public class MongoController {

	private static String MongoURL = "mongodb://tsi2:tsi2@ds043962.mongolab.com:43962/krakenmongo";
	private static String dbstr = "krakenmongo";
	private static String collection = "test";
	/*
	public class ThreadSetTag implements Runnable {
		
		private HashSet<String> tag;
		private Long id;
		
		public ThreadSetTag(HashSet<String> tag, Long id){
			this.tag= tag;
			this.id= id;
		} 
		
	    public void run(){
			MongoClient mongoClient = new MongoClient(new MongoClientURI(MongoURL));
			MongoDatabase db = mongoClient.getDatabase(dbstr);
			
			UpdateOptions options = new UpdateOptions();
			options.upsert(true);
			Document updatedoc = new Document("$set",new Document("tags",tag));
			db.getCollection(collection).updateOne(new Document("rdbms_id",id),updatedoc,options);
			
			
			
			
			
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
			db.getCollection(collection).replaceOne(new Document("rdbms_id",id), e);
			
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
					.append("tags", new ArrayList<>())
					.append("images",new ArrayList<>());
			db.getCollection(collection).insertOne(doc);
			mongoClient.close();
			
	    }
	}	*/
	
	
	/*
	public class ThreadSetImages implements Runnable {
		
		private HashSet<String> images;
		private Long id;
		
		public ThreadSetImages(HashSet<String> images, Long id){
			this.images= images;
			this.id= id;
		} 
		
	    public void run(){
			MongoClient mongoClient = new MongoClient(new MongoClientURI(MongoURL));
			MongoDatabase db = mongoClient.getDatabase(dbstr);
			
			
			UpdateOptions options = new UpdateOptions();
			options.upsert(true);
			Document updatedoc = new Document("$set",new Document("images",images));
			db.getCollection(collection).updateOne(new Document("rdbms_id",id),updatedoc,options);
			
			mongoClient.close();
			
	    }
	}	
	
	public void setTags(HashSet<String> tag, Long id){
		Thread t = new Thread(new ThreadSetTag(tag, id));
		t.start();
	}
	
	public void setImages(HashSet<String> images, Long id){
		Thread t = new Thread(new ThreadSetImages(images, id));
		t.start();
	}
	*/
	
	public class ThreadUpsertProducto implements Runnable {
		
		private DataProducto dp;
		
		public ThreadUpsertProducto(DataProducto dp){
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
			o.append("tags", dp.getTags());
			o.append("imagenes", dp.getImagenes());
			UpdateOptions options = new UpdateOptions();
			options.upsert(true);
			Document updatedoc = new Document("$set",new Document("rdbms_producto",o));
			db.getCollection(collection).updateOne(new Document("rdbms_id",dp.getID()),updatedoc,options);
			mongoClient.close();
	    }
	}
	
	public void upsertProduct(DataProducto dataproducto){
		Thread t = new Thread(new ThreadUpsertProducto(dataproducto));
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


