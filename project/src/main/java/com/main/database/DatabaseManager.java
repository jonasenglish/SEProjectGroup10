package com.main.database;

import static com.mongodb.client.model.Filters.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class DatabaseManager {

    public static DatabaseManager instance = null;

    private MongoClientURI uri = new MongoClientURI("mongodb+srv://Group10:FmOfxH67VfqhBs2E@projectcluster.nlgn5.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");

    private MongoClient mongoClient = null;

    public void Connect(){
        try {
            mongoClient = new MongoClient(uri);
            System.out.println("Connected to MongoDB.");
        } catch (Exception e) {
            System.err.println("ERROR! Failed to connect to database! Perhaps Username or Password was incorrect?");
        }
    }

    public MongoClient GetMongoClient(){
        return mongoClient;
    }

    public MongoDatabase GetDatabase(){
        return mongoClient.getDatabase("ProjectDatabase");
    }

    public MongoCollection<Document> GetAccountCollection(){
        return GetDatabase().getCollection("Accounts");
    }

    public static void SetInstance(DatabaseManager newInstance){
        instance = newInstance;
    }

    public Document FindDocumentByUsername(String username, MongoCollection<Document> accountCollection){
        Document document = accountCollection.find(eq("username", username)).first();
        return document;
    }

    public Document FindDocumentByEmail(String email, MongoCollection<Document> accountCollection){
        Document document = accountCollection.find(eq("email", email)).first();
        return document;
    }

}