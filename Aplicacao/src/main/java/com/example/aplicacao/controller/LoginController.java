package com.example.aplicacao.controller;

import com.example.aplicacao.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginController {

        // Campos na interface gráfica
        @FXML
        private TextField emailField;

        @FXML
        private TextField passwordField;

        private static final String LOGIN_API_URL = "http://localhost:8080/api/funcionarios/login?email=%s&senha=%s";

    @FXML
    protected void onLoginButtonClick() {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            String response = realizarLogin(email, password);
            exibirMensagem("Sucesso", "Login realizado com sucesso!", response, Alert.AlertType.INFORMATION);

            Main.changeScreen("inicio");
//            // Trocar para a tela de início
//            Stage stage = (Stage) emailField.getScene().getWindow();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aplicacao/inicio-view.fxml"));
//            Scene scene = new Scene(loader.load());
//            stage.setScene(scene);
//            stage.show();

        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao realizar login", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private String realizarLogin(String email, String password) throws Exception {
        // Construir a URL com os parâmetros de login
        String urlString = String.format(LOGIN_API_URL, email, password);
        URL url = new URL(urlString);

        // Configurar a conexão HTTP
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST"); // Altere para POST se necessário
        connection.setRequestProperty("Accept", "application/json");

        // Checar o status da resposta
        int statusCode = connection.getResponseCode();
        if (statusCode == HttpURLConnection.HTTP_OK) {
            // Ler a resposta do servidor
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } else {
            throw new RuntimeException("Erro ao realizar login. Código HTTP: " + statusCode);
        }
    }

        private void exibirMensagem(String titulo, String cabecalho, String conteudo, Alert.AlertType tipo) {
            Alert alert = new Alert(tipo);
            alert.setTitle(titulo);
            alert.setHeaderText(cabecalho);
            alert.setContentText(conteudo);
            alert.showAndWait();
        }

    @FXML
    public void onActionCadastro() {
        Main.changeScreen("cadastro");
    }
}

