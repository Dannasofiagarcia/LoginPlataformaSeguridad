package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import model.UserPassword;

public class LoginController {

	@FXML
	private Pane administradorPane;

	@FXML
	private Button cambiarContraBtn;

	@FXML
	private Button consultarLoginBtn;

	@FXML
	private Button consultarUsuarioBtn;

	@FXML
	private Button iniciarSesionBtn;

	@FXML
	private Pane inicioPane;

	@FXML
	private Label nombreUsuarioLabel;

	@FXML
	private Button registrarseBtn;

	@FXML
	private Pane usuarioPane;

	@FXML
	private Pane consultaPane;

	@FXML
	private Button contraBlancoBtn;

	@FXML
	private Button eliminarUsuarioBtn;

	@FXML
	private TextField nombreUsuarioTF;

	private static String path = ".//src//bd//bd.txt";

	private static File bd;

	String usuarioLoggeado;
	String contraLoggeado;

	public LoginController() {
		bd = new File(path);
	}

	@FXML
	void cambiarContra(ActionEvent event) {

		String econdedPassword = "";

		TextInputDialog dialog = new TextInputDialog("Nueva contraseña");
		dialog.setTitle("Cambio contraseña");
		dialog.setHeaderText("Look, a New Password Dialog");
		dialog.setContentText("Escriba la contrasaeña nueva");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			try {
				econdedPassword = UserPassword.hashPassword(result.get().toString());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				File temporal = new File(bd.getAbsolutePath() + ".tmp");

				BufferedReader br = new BufferedReader(new FileReader(path));
				PrintWriter pw = new PrintWriter(new FileWriter(temporal));

				String line = null;

				while ((line = br.readLine()) != null) {
					if (!line.split(",")[0].equals(usuarioLoggeado)) {
						pw.println(line);
						pw.flush();
					} else {
						String informacionUsuario = line.split(",")[0] + "," + econdedPassword + ",";
						if (line.split("").length > 2) {
							informacionUsuario += line.split(",")[2];
						}
						pw.println(informacionUsuario);
						pw.flush();
					}
				}
				pw.close();
				br.close();

				if (!bd.delete() || !temporal.renameTo(bd)) {
					Alert advertencia = new Alert(AlertType.ERROR);
					advertencia.setTitle("No fue posible hacer los cambios");
					advertencia.initStyle(StageStyle.DECORATED);
					advertencia.showAndWait();
				}

				Alert advertencia = new Alert(AlertType.CONFIRMATION);
				advertencia.setTitle("Contraseña cambiada exitosamente");
				advertencia.initStyle(StageStyle.DECORATED);
				advertencia.showAndWait();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@FXML
	void contraBlanco(ActionEvent event) {
		String usuario = nombreUsuarioTF.getText();
		try {

			File temporal = new File(bd.getAbsolutePath() + ".tmp");

			BufferedReader br = new BufferedReader(new FileReader(path));
			PrintWriter pw = new PrintWriter(new FileWriter(temporal));

			String line = null;

			while ((line = br.readLine()) != null) {
				if (!line.split(",")[0].equals(usuario)) {
					pw.println(line);
					pw.flush();
				} else {
					String informacionUsuario = line.split(",")[0] + ", ,";
					if (line.split("").length > 2) {
						informacionUsuario += line.split(",")[2];
					}
					pw.println(informacionUsuario);
					pw.flush();
				}
			}
			pw.close();
			br.close();

			if (!bd.delete() || !temporal.renameTo(bd)) {
				Alert advertencia = new Alert(AlertType.ERROR);
				advertencia.setTitle("No fue posible hacer los cambios");
				advertencia.initStyle(StageStyle.DECORATED);
				advertencia.showAndWait();
			}

			Alert advertencia = new Alert(AlertType.CONFIRMATION);
			advertencia.setTitle("Contraseña cambiada exitosamente");
			advertencia.initStyle(StageStyle.DECORATED);
			advertencia.showAndWait();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void eliminarUsuario(ActionEvent event) {

		String usuario = nombreUsuarioTF.getText();
		try {

			File temporal = new File(bd.getAbsolutePath() + ".tmp");

			BufferedReader br = new BufferedReader(new FileReader(path));
			PrintWriter pw = new PrintWriter(new FileWriter(temporal));

			String line = null;

			while ((line = br.readLine()) != null) {
				if (!line.split(",")[0].equals(usuario)) {
					pw.println(line);
					pw.flush();
				}
			}
			pw.close();
			br.close();

			if (!bd.delete() || !temporal.renameTo(bd)) {
				Alert advertencia = new Alert(AlertType.ERROR);
				advertencia.setTitle("No fue posible hacer los cambios");
				advertencia.initStyle(StageStyle.DECORATED);
				advertencia.showAndWait();
			}

			Alert advertencia = new Alert(AlertType.CONFIRMATION);
			advertencia.setTitle("Usuario eliminado correctamente");
			advertencia.initStyle(StageStyle.DECORATED);
			advertencia.showAndWait();

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void consultarLogin(ActionEvent event) {
		try {

			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = null;
			String usuarios = "";

			while ((line = br.readLine()) != null) {
				if (line.split(",")[0].equals(usuarioLoggeado)) {
					String datos[] = line.split(",");
					Alert advertencia = new Alert(AlertType.INFORMATION);
					advertencia.setTitle("Información de la última conexión");
					advertencia.setContentText(datos[2]);
					advertencia.initStyle(StageStyle.DECORATED);
					advertencia.showAndWait();
				}
			}
			br.close();

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void consultarUsuario(ActionEvent event) {

		String usuario = nombreUsuarioTF.getText();
		try {

			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = null;
			String usuarios = "";

			while ((line = br.readLine()) != null) {
				String datos[] = line.split(",");
				usuarios += line.split(",")[0] + "\n";
			}
			br.close();
			System.out.println(usuarios);
			usuariosDialog(usuarios);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void usuariosDialog(String usuarios) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Usuarios existentes");
		alert.setHeaderText("Look, an Information Dialog");
		alert.setContentText("Expandir para ver los nombres de usuario existentes");

		Label label = new Label("Usuarios:");

		TextArea textArea = new TextArea(usuarios);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}

	@FXML
	void iniciarSesion(ActionEvent event) {

		Dialog<Pair<String, String>> dialog = new Dialog<>();

		dialog.setTitle("Register Dialog");
		dialog.setHeaderText("Ingrese los datos para el registro");

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> username.requestFocus());

		// Convert the result to a username-password-pair when the login button is
		// clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(username.getText(), password.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(usernamePassword -> {
			System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());

			String contra = usernamePassword.getValue();
			String usuario = usernamePassword.getKey();

			if (usuario.equals("Administrador") && contra.equals("Administrador")) {
				administradorPane.setVisible(true);
				inicioPane.setVisible(false);
				nombreUsuarioLabel.setText(usuario);

			} else {
				try {
					if (UserPassword.validatePassword(contra, usuario)) {

						usuarioLoggeado = usuario;
						contraLoggeado = contra;

						inicioPane.setVisible(false);
						nombreUsuarioLabel.setText(usuario);
						usuarioPane.setVisible(true);

						ultimaConexion(usuario);

					} else {
						Alert advertencia = new Alert(AlertType.ERROR);
						advertencia.setTitle("Datos incorrectos, verifique por favor.");
						advertencia.initStyle(StageStyle.DECORATED);
						advertencia.showAndWait();
					}
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	void ultimaConexion(String usuario) {

		try {
			File temporal = new File(bd.getAbsolutePath() + ".tmp");

			BufferedReader br = new BufferedReader(new FileReader(path));
			PrintWriter pw = new PrintWriter(new FileWriter(temporal));

			String line = null;

			while ((line = br.readLine()) != null) {

				if (line.split(",")[0].equals(usuario)) {

					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
					String fecha = dtf.format(LocalDateTime.now());
					String[] mensajeDividido = line.split(",");
					String mensajeCompleto = mensajeDividido[0] + "," + mensajeDividido[1] + "," + fecha;
					pw.println(mensajeCompleto);
					pw.flush();
				} else {
					pw.println(line);
					pw.flush();
				}
			}
			pw.close();
			br.close();

			if (!bd.delete() || !temporal.renameTo(bd)) {
				Alert advertencia = new Alert(AlertType.ERROR);
				advertencia.setTitle("No fue posible agregar la última conexión");
				advertencia.initStyle(StageStyle.DECORATED);
				advertencia.showAndWait();
			}

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void registrarse(ActionEvent event) {

		Dialog<Pair<String, String>> dialog = new Dialog<>();

		dialog.setTitle("Register Dialog");
		dialog.setHeaderText("Ingrese los datos para el registro");

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Registrarse", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> username.requestFocus());

		// Convert the result to a username-password-pair when the login button is
		// clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(username.getText(), password.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(usernamePassword -> {
			System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());

			try {
				String contra = usernamePassword.getValue();
				String usuario = usernamePassword.getKey();

				if (!usuario.equals("Administrador")) {

					String encodedPassword = UserPassword.hashPassword(contra);
					String textForBD = usuario + "," + encodedPassword + ",\n";
					FileOutputStream f = new FileOutputStream(path, true);

					byte[] byteArr = textForBD.getBytes();
					f.write(byteArr);
					f.close();

					Alert advertencia = new Alert(AlertType.CONFIRMATION);
					advertencia.setTitle("Registrado correctamente");
					advertencia.initStyle(StageStyle.DECORATED);
					advertencia.showAndWait();

				} else {
					Alert advertencia = new Alert(AlertType.ERROR);
					advertencia.setTitle("Solo existe un administrador");
					advertencia.initStyle(StageStyle.DECORATED);
					advertencia.showAndWait();
				}

			} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
				Alert advertencia = new Alert(AlertType.ERROR);
				advertencia.setTitle("No pudo ser registrado");
				advertencia.initStyle(StageStyle.DECORATED);
				advertencia.setContentText(e.getMessage());
				e.printStackTrace();
				advertencia.showAndWait();
			}
		});

	}

	void registrarseDialog() {

	}

}
