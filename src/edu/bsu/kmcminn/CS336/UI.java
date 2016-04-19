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

public class UI extends Application {

	Stage window;
	Scene searchScene, displayScene;

	public static void main(String[] args) {
		launch(args);
	}

	private Button searchButton = new Button("Search");
	private Button goBackButton = new Button("Go Back");
	private TextField numberField1 = new TextField();
	private TextField numberField2 = new TextField();
	private Text databaseDisplayText = new Text();

	ComboBox<String> choiceBox = new ComboBox<String>();

	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		configure(window);
	}

	public void configure(Stage window) {
		window.setTitle("Database Query");
		makeScenes();
		window.setScene(searchScene);
		window.show();
	}

	public void makeScenes() {
		configureButtons();
		makeSearchScene();
		makeDisplayScene();
	}

	public void configureButtons() {
		searchButton.setAlignment(Pos.CENTER);
		goBackButton.setAlignment(Pos.CENTER);
		searchButton.setOnAction((event) -> {
			try {
				searchDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}
			window.setScene(displayScene);
		});
		goBackButton.setOnAction(e -> window.setScene(searchScene));

	}

	public void makeSearchScene() {
		choiceBox.getItems().addAll(
				"Select pilots with hours between two bounds",
				"Select Planes with Hobb's Times bettwen two bounds",
				"Select Pilot with the most hours",
				"Select Pilot with the least hours",
				"Select current makes and models of aircraft in the hangar",
				"Select all mechanics of a department number",
				"Select all pilots born before a certain year",
				"Show the mechanics and what planes they are assigned to",
				"Display the average Hobb's time and amount of planes for each company",
				"Select the plane with the highest Hobb's time");
		Label searchLabel = new Label("Enter search query here: \n\n If the search asks for only one value, use the To: text box.");

		HBox numberFields = new HBox(20);
		numberFields.getChildren().addAll(numberField1, numberField2);
		numberFields.setAlignment(Pos.CENTER);

		HBox boundLabels = new HBox(175);
		Label lowerBoundLabel = new Label("From:");
		Label upperBoundLabel = new Label("To:");
		boundLabels.getChildren().addAll(lowerBoundLabel, upperBoundLabel);
		boundLabels.setAlignment(Pos.CENTER);

		VBox layout1 = new VBox(20);
		layout1.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(searchLabel, choiceBox, boundLabels,
				numberFields, searchButton);
		searchScene = new Scene(layout1, 600, 600);
	}

	public void makeDisplayScene() {
		Label displayLabel = new Label("Results from database:");
		ScrollPane scrollPane1 = new ScrollPane();
		scrollPane1.setContent(databaseDisplayText);
		scrollPane1.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		VBox layout2 = new VBox(20);
		layout2.getChildren().addAll(displayLabel, scrollPane1, goBackButton);
		displayScene = new Scene(layout2, 600, 600);
	}

	private void searchDatabase() throws SQLException, IOException {
		DatabaseInterface dataBase = new DatabaseInterface();
		String searchString = createSearchString();
		String results = dataBase.searchDB(searchString);
		databaseDisplayText.setText(results);
	}

	private String createSearchString() {
		String searchString = null;
		if (choiceBox.getValue() == "Select pilots with hours between two bounds") {
			searchString = "Select * from PILOT, FLOWN_BY WHERE PILOT.Pilot_ID = FLOWN_BY.Pilot_ID AND HOURS BETWEEN "
					+ numberField1.getText() + " AND " + numberField2.getText();
		}
		if (choiceBox.getValue() == "Select Planes with Hobb's Times bettwen two bounds"){
			searchString = "Select * from PLANE WHERE HOBBS_TIME BETWEEN " +numberField1.getText() + " AND "+ numberField2.getText();
		}
		if (choiceBox.getValue() == "Select Pilot with the most hours"){
			searchString = "SELECT * FROM PILOT, FLOWN_BY WHERE PILOT.PILOT_ID = FLOWN_BY.PILOT_ID AND HOURS IN	(SELECT MAX(HOURS)FROM FLOWN_BY)";
		}
		if (choiceBox.getValue() == "Select Pilot with the least hours"){
			searchString = "SELECT * FROM PILOT, FLOWN_BY WHERE PILOT.PILOT_ID = FLOWN_BY.PILOT_ID AND HOURS IN	(SELECT MIN(HOURS)FROM FLOWN_BY)";
		}
		if (choiceBox.getValue() == "Select current makes and models of aircraft in the hangar"){
			searchString = "SELECT TAIL_NO, P_MAKE, P_MODEL FROM PLANE";
		}
		if (choiceBox.getValue() == "Select all mechanics of a department number"){
			searchString = "SELECT F_NAME, L_NAME, SSN FROM MECHANIC WHERE DEP_NUMBER = "+numberField2.getText() + " ORDER BY L_NAME ASC";
		}
		if (choiceBox.getValue() == "Select all pilots born before a certain year"){
			searchString = "SELECT * FROM PILOT WHERE to_number(to_char(DOB, 'yyyy')) < " + numberField2.getText();
		}
		if (choiceBox.getValue() == "Show the mechanics and what planes they are assigned to"){
			searchString = "SELECT M.F_NAME, M.L_NAME, P.TAIL_NO, P.P_MODEL FROM MECHANIC M, PLANE P, WORKS_ON W WHERE M.SSN = W.M_SSN AND P.TAIL_NO = W.TAIL_NO";
		}
		if (choiceBox.getValue() == "Display the average Hobb's time and amount of planes for each company"){
			searchString = "SELECT C_NAME, AVG(HOBBS_TIME), COUNT(*) FROM PLANE GROUP BY C_NAME";
		}
		if (choiceBox.getValue() == "Select the plane with the highest Hobb's time"){
			searchString = "SELECT * FROM PLANE WHERE HOBBS_TIME IN (SELECT MAX(HOBBS_TIME) FROM PLANE)";
		}
		return searchString;
	}
}
