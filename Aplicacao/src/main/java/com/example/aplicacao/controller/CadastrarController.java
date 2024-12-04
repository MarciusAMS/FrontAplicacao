package com.example.aplicacao.controller;

import com.example.aplicacao.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CadastrarController {
        @FXML
        private TextField nomeField;

        @FXML
        private TextField precoField;

        @FXML
        private TextField descricaoField;

        @FXML
        private TextField categoriaField;

        private static final String CADASTRO_API_URL = "http://localhost:8080/api/produtos";

        @FXML
        public void onActionCadastrar() {
            String nome = nomeField.getText();
            String preco = precoField.getText();
            String descricao = descricaoField.getText();
            String categoria = categoriaField.getText();

            try {
                String response = cadastrarProduto(nome, preco, descricao, categoria);
                exibirMensagem("Sucesso", "Produto cadastrado com sucesso!", response, Alert.AlertType.INFORMATION);
                Main.changeScreen("inicio");
            } catch (Exception e) {
                exibirMensagem("Erro", "Falha ao cadastrar produto", e.getMessage(), Alert.AlertType.ERROR);
            }
        }

        private String cadastrarProduto(String nome, String preco, String descricao, String categoria) throws Exception {
            URL url = new URL(CADASTRO_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = String.format(
                    "{\"nome\":\"%s\", \"preco\":%s, \"descricao\":\"%s\", \"categoria\":\"%s\"}",
                    nome, preco, descricao, categoria
            );

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                return "Produto cadastrado com sucesso!";
            } else {
                throw new RuntimeException("Erro ao cadastrar produto. Código HTTP: " + statusCode);
            }
        }

        private void exibirMensagem(String titulo, String cabecalho, String conteudo, Alert.AlertType tipo) {
            Alert alert = new Alert(tipo);
            alert.setTitle(titulo);
            alert.setHeaderText(cabecalho);
            alert.setContentText(conteudo);
            alert.showAndWait();
        }
    }
