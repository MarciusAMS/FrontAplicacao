package com.example.aplicacao.controller;

import com.example.aplicacao.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AlterarController {
    @FXML
    private TextField idField;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField precoField;

    @FXML
    private TextField descricaoField;

    @FXML
    private TextField categoriaField;

    private static final String ALTERAR_API_URL = "http://localhost:8080/api/produtos";

    @FXML
    public void onActionAlterar() {
        String id = idField.getText();
        String nome = nomeField.getText();
        String preco = precoField.getText();
        String descricao = descricaoField.getText();
        String categoria = categoriaField.getText();

        if (id.isEmpty() || nome.isEmpty() || preco.isEmpty() || descricao.isEmpty() || categoria.isEmpty()) {
            exibirMensagem("Erro", "Preencha todos os campos antes de alterar o produto.", "", Alert.AlertType.ERROR);
            return;
        }

        try {
            String response = alterarProduto(Integer.parseInt(id), nome, preco, descricao, categoria);
            exibirMensagem("Sucesso", "Produto alterado com sucesso!", response, Alert.AlertType.INFORMATION);
            Main.changeScreen("login");
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha ao alterar produto", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private String alterarProduto(int produtoId, String nome, String preco, String descricao, String categoria) throws Exception {
        URL url = new URL(ALTERAR_API_URL + "/" + produtoId); // Adiciona o ID do produto na URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
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
            return "Produto alterado com sucesso!";
        } else {
            throw new RuntimeException("Erro ao alterar produto. Código HTTP: " + statusCode);
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
