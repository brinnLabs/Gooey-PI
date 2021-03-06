package org.bbcm.gooeypi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EasEE extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage pStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		pStage = primaryStage;
		primaryStage.setTitle("EasEE Setup Utility");
		AnchorPane myPane = FXMLLoader.load(getClass().getResource("/assets/bbcm/gooeypi/layout/easee.fxml"));
		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.show();
		primaryStage.setResizable(false);

	}

}
