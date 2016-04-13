package edu.bsu.kmcminn.CS336;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UI extends Application{

	Stage window;
	Scene searchScene, displayScene;
	
	public static void main(String[] args) {
		launch(args);
		}
	
	private Button searchButton = new Button ("Search");
	private Button goBackButton = new Button ("Go Back");
	private TextField searchField = new TextField ();
	private Text databaseDisplayText = new Text();
	
	@Override
	public void start(Stage primaryStage){
	window = primaryStage;
	configure(window);
	}
	
	public void configure(Stage window){
		window.setTitle("Database Query");
		makeScenes();
		window.setScene(searchScene);
		window.show();
	}
	
	public void makeScenes(){
		configureButtons();
		makeSearchScene();
		makeDisplayScene();
	}
	
	public void configureButtons(){
		searchButton.setAlignment(Pos.CENTER);
		goBackButton.setAlignment(Pos.CENTER);
		searchButton.setOnAction((event) -> {
			try {
				searchDatabase();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			window.setScene(displayScene);
		}
	);
		goBackButton.setOnAction( e -> window.setScene(searchScene));
		
	}
	
	public void makeSearchScene(){
		Label searchLabel = new Label ("Enter search query here:");
		VBox layout1 = new VBox(20);
		layout1.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(searchLabel, searchField, searchButton );
		searchScene = new Scene(layout1, 600, 600);
	}
	
	public void makeDisplayScene(){
		Label displayLabel = new Label ("Results from database:");
		ScrollPane scrollPane1 = new ScrollPane();
		scrollPane1.setContent(databaseDisplayText);
		scrollPane1.autosize();
		VBox layout2 = new VBox(20);
		layout2.getChildren().addAll(displayLabel, scrollPane1, goBackButton);
		displayScene = new Scene (layout2, 600, 600);		
	}
	
	private void searchDatabase() throws SQLException, IOException{
		JdbcCheckup3 dataBase = new JdbcCheckup3();
		String results = dataBase.searchDB(searchField.getText());
		databaseDisplayText.setText(results);
	}

	
}
