package com.example.aplicacao.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.aplicacao.model.Produto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.List;

public class ConsultarController {

    @FXML
    private TableView<Produto> tableProdutos;

    @FXML
    private TableColumn<Produto, Long> colId;

    @FXML
    private TableColumn<Produto, String> colNome;

    @FXML
    private TableColumn<Produto, String> colDescricao;

    @FXML
    private TableColumn<Produto, String> colCategoria;

    @FXML
    private TableColumn<Produto, Double> colPreco;

    @FXML
    private TextField idField; // Campo para entrada de ID

    private static final String API_URL = "http://localhost:8080/api/produtos";

    @FXML
    public void initialize() {
        // Configurar colunas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        // Carregar todos os dados na inicialização
        carregarDados();
    }

    @FXML
    private void carregarDados() {

            String id = idField.getText(); // Obter o valor do campo ID
            HttpResponse<String> response;
    }

    private HttpResponse<String> enviarRequisicaoGET(String url) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private List<Produto> processarResposta(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Processa resposta com base em se é uma lista ou um único objeto
        if (json.startsWith("[")) { // JSON de lista
            return mapper.readValue(json, new TypeReference<List<Produto>>() {});
        } else { // JSON de objeto único
            Produto produto = mapper.readValue(json, Produto.class);
            return List.of(produto);
        }
    }

    private void atualizarTabela(List<Produto> produtos) {
        tableProdutos.getItems().clear();
        tableProdutos.getItems().setAll(produtos);
        exibirMensagemInformativa("Dados atualizados com sucesso!");
    }

    private void lidarComErro(int statusCode) {
        if (statusCode == HttpURLConnection.HTTP_NOT_FOUND) {
            exibirMensagemErro("Nenhum dado encontrado (404).");
        } else {
            exibirMensagemErro("Erro ao carregar dados. Código HTTP: " + statusCode);
        }
    }

    private void exibirMensagemInformativa(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void exibirMensagemErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
