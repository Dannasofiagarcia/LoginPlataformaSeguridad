package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	private LoginController loginController;

	public Main() {
		loginController = new LoginController();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader fxmll = new FXMLLoader(getClass().getResource("Main.fxml"));
		fxmll.setController(loginController);
		Parent root = fxmll.load();

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login plataforma");
		primaryStage.show();

	}

}
