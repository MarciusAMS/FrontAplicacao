package com.example.aplicacao.controller;

import com.example.aplicacao.Main;
import javafx.fxml.FXML;

public class InicioController {

    @FXML
    public void onActionCadastrar() {
        Main.changeScreen("cadastrar");
    }

    @FXML
    public void onActionAlterar() {
        Main.changeScreen("alterar");
    }

    @FXML
    public void onActionExcluir() {
        Main.changeScreen("excluir");
    }

    @FXML
    public void onActionConsultar() {
        Main.changeScreen("consultar");
    }

}
