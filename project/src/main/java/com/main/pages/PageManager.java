package com.main.pages;

import java.util.Hashtable;

import com.main.App;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

// Manages switching pages(scenes) such as from Login Page to Account Creation Page, and so on.
public class PageManager {

    private static Hashtable<Parent, Scene> scenes = new Hashtable<Parent, Scene>();

    public static void SetPage(String pageName, String title, boolean resizable){
        Parent root = null;

        try {
            root = App.pages.get(pageName); // Retrieving Page
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: CreateAccountPage.fxml Could not be loaded! Dumping Error:");
            System.err.println(e);
            return;
        }

        if(root == null){
            System.err.println("CRITICAL ERROR: root == null, Page not found! \nThe pageName " + pageName + " did not return a root!");
            return;
        }

        Scene scene = null;

        // My janky fix for switching to scenes that have already been made. It works :^) - Jonas

        try{
           scene = new Scene(root);
           scenes.put(root, scene);
        }catch(IllegalArgumentException e){
            scene = scenes.get(root);
        }
        
        Stage primaryStage = App.pStage; // Retrieving Primary Stage
        
        
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.setResizable(resizable);

        primaryStage.show();
    }

    public static void SetPage(String pageName, String title){
        SetPage(pageName, title, false);
    }

}
