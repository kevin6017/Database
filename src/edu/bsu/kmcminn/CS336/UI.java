package edu.bsu.kmcminn.CS336;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
	//private TextField searchField = new TextField ();
	private TextField numberField1 = new TextField();
	private TextField numberField2 = new TextField();
	private Text databaseDisplayText = new Text();
	
	ComboBox choiceBox = new ComboBox();
	
	
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
				e.printStackTrace();
			}
			window.setScene(displayScene);
		}
	);
		goBackButton.setOnAction( e -> window.setScene(searchScene));
		
	}
	
	public void makeSearchScene(){
		choiceBox.getItems().addAll("Select pilots with hours between two bounds", 
				"Select Planes with Hobb's Times bettwen two bounds",
				"Select Pilot with the most hours");
		Label searchLabel = new Label ("Enter search query here:");
		
		HBox numberFields = new HBox(20);
		numberFields.getChildren().addAll(numberField1, numberField2);
		numberFields.setAlignment(Pos.CENTER);
		
		HBox boundLabels = new HBox(175);
		Label lowerBoundLabel = new Label ("From:");
		Label upperBoundLabel = new Label ("To:");
		boundLabels.getChildren().addAll(lowerBoundLabel, upperBoundLabel);
		boundLabels.setAlignment(Pos.CENTER);
		
		VBox layout1 = new VBox(20);
		layout1.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(searchLabel, choiceBox,boundLabels, numberFields, searchButton );
		searchScene = new Scene(layout1, 600, 600);
	}
	
	public void makeDisplayScene(){
		Label displayLabel = new Label ("Results from database:");
		ScrollPane scrollPane1 = new ScrollPane();
		scrollPane1.setContent(databaseDisplayText);
		scrollPane1.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		VBox layout2 = new VBox(20);
		layout2.getChildren().addAll(displayLabel, scrollPane1, goBackButton);
		displayScene = new Scene (layout2, 600, 600);		
	}
	
	private void searchDatabase() throws SQLException, IOException{
		DatabaseInterface dataBase = new DatabaseInterface();
		String searchString = createSearchString();
		String results = dataBase.searchDB(searchString);
		databaseDisplayText.setText(results);
	}
	
	private String createSearchString(){
		String searchString = null;
		if (choiceBox.getValue() == "Select pilots with hours between two bounds"){
			searchString = "Select * from PILOT, FLOWN_BY WHERE PILOT.Pilot_ID = FLOWN_BY.Pilot_ID AND HOURS BETWEEN " + 
					numberField1.getText() + " AND " + numberField2.getText();
		}
		if (choiceBox.getValue() == "Select Planes with Hobb's Times bettwen two bounds"){
			searchString = "Select * from PLANE WHERE HOBBS_TIME BETWEEN " +numberField1.getText() + " AND "+ numberField2.getText();
		}
		if (choiceBox.getValue() == "Select Pilot with the most hours"){
			searchString = "Select * " +numberField1.getText() + " AND "+ numberField2.getText();
		}
		return searchString;
	}

	
}
