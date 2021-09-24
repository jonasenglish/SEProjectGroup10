package com.main.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

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

    public static void SetInstance(DatabaseManager newInstance){
        instance = newInstance;
    }

}