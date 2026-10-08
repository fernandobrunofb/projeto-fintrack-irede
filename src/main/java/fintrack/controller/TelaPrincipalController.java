package fintrack.controller;

import fintrack.exceptions.PersistenciaException;
import fintrack.model.Transacao;
import fintrack.service.FinTracker;
import fintrack.utils.Formatador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class TelaPrincipalController {

    @FXML
    private TableView<Transacao> tabelaTransacoes;
    @FXML
    private TableColumn<Transacao, String> colunaData;
    @FXML
    private TableColumn<Transacao, String> colunaDescricao;
    @FXML
    private TableColumn<Transacao, String> colunaValor;
    @FXML
    private TableColumn<Transacao, String> colunaTipo;
    @FXML
    private Button botaoEditar;
    @FXML
    private Button botaoRemover;
    @FXML
    private BorderPane raiz;
    @FXML
    private BorderPane painelTransacoes;

    private FinTracker finTracker;

    @FXML
    private void initialize() {
        colunaData.setCellValueFactory(c ->
                new SimpleStringProperty(Formatador.formatarData(c.getValue().getData())));
        colunaDescricao.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getDescricao()));
        colunaValor.setCellValueFactory(c ->
                new SimpleStringProperty(Formatador.formatarValor(c.getValue().getValor())));
        colunaTipo.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getTipo().getRotulo()));

        var semSelecao = tabelaTransacoes.getSelectionModel().selectedItemProperty().isNull();
        botaoEditar.disableProperty().bind(semSelecao);
        botaoRemover.disableProperty().bind(semSelecao);
    }

    public void setFinTracker(FinTracker finTracker) {
        this.finTracker = finTracker;
        atualizarTabela();
    }

    @FXML
    private void mostrarTransacoes() {
        raiz.setCenter(painelTransacoes);
        atualizarTabela();
    }

    @FXML
    private void mostrarRelatorio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fintrack/view/Relatorio.fxml"));
            Parent telaRelatorio = loader.load();

            RelatorioController controller = loader.getController();
            controller.setFinTracker(finTracker);

            raiz.setCenter(telaRelatorio);
        } catch (IOException e) {
            mostrarErro("Não foi possível abrir o relatório.");
        }
    }

    @FXML
    private void abrirNovaTransacao() {
        abrirFormulario("Nova transação", null);
    }

    @FXML
    private void editarTransacao() {
        Transacao selecionada = tabelaTransacoes.getSelectionModel().getSelectedItem();
        abrirFormulario("Editar transação", selecionada);
    }

    @FXML
    private void removerTransacao() {
        Transacao selecionada = tabelaTransacoes.getSelectionModel().getSelectedItem();

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.initOwner(tabelaTransacoes.getScene().getWindow());
        confirmacao.setTitle("Remover transação");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Deseja remover \"" + selecionada.getDescricao() + "\"?");

        Optional<ButtonType> resposta = confirmacao.showAndWait();
        if (resposta.isPresent() && resposta.get() == ButtonType.OK) {
            try {
                finTracker.remover(selecionada.getId());
                atualizarTabela();
            } catch (PersistenciaException e) {
                mostrarErro("Não foi possível remover a transação.");
            }
        }
    }

    private void abrirFormulario(String titulo, Transacao transacao) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fintrack/view/FormularioTransacao.fxml"));
            Parent raiz = loader.load();

            FormularioTransacaoController controller = loader.getController();
            controller.setFinTracker(finTracker);
            if (transacao != null) {
                controller.editar(transacao);
            }

            Stage janela = new Stage();
            janela.setTitle(titulo);
            janela.initModality(Modality.APPLICATION_MODAL);
            janela.initOwner(tabelaTransacoes.getScene().getWindow());
            janela.setScene(new Scene(raiz));
            janela.showAndWait();

            atualizarTabela();
        } catch (IOException e) {
            mostrarErro("Não foi possível abrir o formulário.");
        }
    }

    private void atualizarTabela() {
        tabelaTransacoes.setItems(FXCollections.observableArrayList(finTracker.listar()));
    }

    private void mostrarErro(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR, mensagem);
        alerta.initOwner(tabelaTransacoes.getScene().getWindow());
        alerta.showAndWait();
    }
}