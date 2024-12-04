package com.example.aplicacao;

import com.example.aplicacao.controller.ConsultarController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stage;
    private static Scene loginScene;
    private static Scene cadastroScene;
    private static Scene inicioScene;
    private static Scene cadastrarScene;
    private static Scene alterarScene;
    private static Scene excluirScene;
    private static Scene consultarScene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        Parent loginLoader = FXMLLoader.load(getClass().getResource("/com/example/aplicacao/login-view.fxml"));
        loginScene = new Scene(loginLoader, 270, 400);

        Parent cadastroLoader = FXMLLoader.load(getClass().getResource("/com/example/aplicacao/cadastro-view.fxml"));
        cadastroScene = new Scene(cadastroLoader, 350, 520);

        Parent inicioLoader = FXMLLoader.load(getClass().getResource("/com/example/aplicacao/inicio-view.fxml"));
        inicioScene = new Scene(inicioLoader, 350, 520);

        Parent cadastrarLoader = FXMLLoader.load(getClass().getResource("/com/example/aplicacao/cadastrar-view.fxml"));
        cadastrarScene = new Scene(cadastrarLoader, 350, 520);

        Parent alterarLoader = FXMLLoader.load(getClass().getResource("/com/example/aplicacao/alterar-view.fxml"));
        alterarScene = new Scene(alterarLoader, 350, 520);

        Parent excluirLoader = FXMLLoader.load(getClass().getResource("/com/example/aplicacao/excluir-view.fxml"));
        excluirScene = new Scene(excluirLoader, 350, 520);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aplicacao/consultar-view.fxml"));
        Parent root = loader.load();
        ConsultarController controller = loader.getController();
        Scene consultarScene = new Scene(root, 350, 520);


        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }


    public static void changeScreen(String screen) {
        if (stage != null) {
            switch (screen) {
                case "login":
                    stage.setTitle("Login");
                    stage.setScene(loginScene);
                    break;
                case "cadastro":
                    stage.setTitle("Cadastro");
                    stage.setScene(cadastroScene);
                    break;
                case "inicio":
                    stage.setTitle("Inicio");
                    stage.setScene(inicioScene);
                    break;
                case "cadastrar":
                    stage.setTitle("Cadastrar");
                    stage.setScene(cadastrarScene);
                    break;
                case "alterar":
                    stage.setTitle("Alterar");
                    stage.setScene(alterarScene);
                    break;
                case "excluir":
                    stage.setTitle("Excluir");
                    stage.setScene(excluirScene);
                    break;
                case "consultar":
                    stage.setTitle("Consultar");
                    stage.setScene(consultarScene);
                    break;
                default:
                    throw new IllegalArgumentException("Tela não encontrada: " + screen);
            }
        } else {
            throw new IllegalStateException("Stage ainda não foi inicializado!");
        }
    }
}