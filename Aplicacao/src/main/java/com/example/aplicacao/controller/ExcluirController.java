package com.example.aplicacao.controller;

import com.example.aplicacao.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExcluirController {

    @FXML
    private TextField idField;

    private static final String EXCLUIR_API_URL = "http://localhost:8080/api/produtos";

    @FXML
    public void onActionExcluir() {
        String id = idField.getText();

        if (id == null || id.isEmpty()) {
            exibirMensagem("Erro", "ID inválido", "Por favor, insira um ID válido.", Alert.AlertType.ERROR);
            return;
        }

        try {
            excluirProduto(id);
            exibirMensagem("Sucesso", "Produto Excluído", "O produto foi excluído com sucesso!", Alert.AlertType.INFORMATION);
            Main.changeScreen("login");
        } catch (Exception e) {
            exibirMensagem("Erro", "Falha na Exclusão", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void excluirProduto(String id) throws Exception {
        URL url = new URL(EXCLUIR_API_URL + "/" + id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        int statusCode = connection.getResponseCode();

        if (statusCode != HttpURLConnection.HTTP_NO_CONTENT) {
            throw new RuntimeException("Erro ao excluir produto. Código HTTP: " + statusCode);
        }
    }

    private void exibirMensagem(String titulo, String cabecalho, String conteudo, Alert.AlertType tipoAlerta) {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }
}
