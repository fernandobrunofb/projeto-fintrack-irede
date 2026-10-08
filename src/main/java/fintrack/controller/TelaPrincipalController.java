package fintrack.controller;

import fintrack.model.Transacao;
import fintrack.service.FinTracker;
import fintrack.utils.Formatador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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
                new SimpleStringProperty(c.getValue().getTipo().toString()));
    }

    @FXML
    private void abrirNovaTransacao() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fintrack/view/NovaTransacao.fxml"));
            Parent raiz = loader.load();

            NovaTransacaoController controller = loader.getController();
            controller.setFinTracker(finTracker);

            Stage janela = new Stage();
            janela.setTitle("Nova transação");
            janela.initModality(Modality.APPLICATION_MODAL);
            janela.initOwner(tabelaTransacoes.getScene().getWindow());
            janela.setScene(new Scene(raiz));
            janela.showAndWait();

            atualizarTabela();
        } catch (IOException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR, "Não foi possível abrir o formulário.");
            alerta.showAndWait();
        }
    }

    public void setFinTracker(FinTracker finTracker) {
        this.finTracker = finTracker;
        atualizarTabela();
    }

    private void atualizarTabela() {
        tabelaTransacoes.setItems(FXCollections.observableArrayList(finTracker.listar()));
    }
}