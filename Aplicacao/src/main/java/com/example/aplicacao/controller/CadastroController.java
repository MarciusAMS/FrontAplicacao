package com.example.aplicacao.controller;

import com.example.aplicacao.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CadastroController {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField senhaField;

    private static final String CADASTRO_API_URL = "http://localhost:8080/api/funcionarios";

    @FXML
    public void onActionCadastrar() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();

        try {
            String response = cadastrarUsuario(nome, email, senha);
            exibirMensagem("Sucesso", "Cadastro realizado com sucesso!", response, Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao realizar cadastro", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private String cadastrarUsuario(String nome, String email, String senha) throws IOException {
        // URL da API de cadastro
        URL url = new URL(CADASTRO_API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Criando o corpo da requisição JSON
        String jsonInputString = String.format("{\"nome\":\"%s\", \"email\":\"%s\", \"senha\":\"%s\"}", nome, email, senha);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int statusCode = connection.getResponseCode();
        if (statusCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } else {
            throw new RuntimeException("Erro ao cadastrar. Código HTTP: " + statusCode);
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
    public void onActionLogin() {
        Main.changeScreen("login");
    }
}

