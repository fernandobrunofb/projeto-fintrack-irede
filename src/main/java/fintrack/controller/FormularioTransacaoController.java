package fintrack.controller;

import fintrack.exceptions.EntradaInvalidaException;
import fintrack.exceptions.PersistenciaException;
import fintrack.model.TipoTransacao;
import fintrack.model.Transacao;
import fintrack.model.TransacaoAvulsa;
import fintrack.model.TransacaoMensal;
import fintrack.service.FinTracker;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FormularioTransacaoController {

    @FXML
    private TextField campoDescricao;
    @FXML
    private TextField campoValor;
    @FXML
    private DatePicker campoData;
    @FXML
    private RadioButton radioEntrada;
    @FXML
    private RadioButton radioSaida;
    @FXML
    private CheckBox checkMensal;
    @FXML
    private Spinner<Integer> spinnerDia;

    private FinTracker finTracker;
    private Transacao transacaoEmEdicao;

    @FXML
    private void initialize() {
        campoData.setValue(LocalDate.now());
        spinnerDia.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 31, 1));
        spinnerDia.disableProperty().bind(checkMensal.selectedProperty().not());
    }

    public void setFinTracker(FinTracker finTracker) {
        this.finTracker = finTracker;
    }

    public void editar(Transacao transacao) {
        this.transacaoEmEdicao = transacao;

        campoDescricao.setText(transacao.getDescricao());
        campoValor.setText(transacao.getValor().toPlainString().replace(".", ","));
        campoData.setValue(transacao.getData());

        if (transacao.getTipo() == TipoTransacao.ENTRADA) {
            radioEntrada.setSelected(true);
        } else {
            radioSaida.setSelected(true);
        }

        if (transacao instanceof TransacaoMensal mensal) {
            checkMensal.setSelected(true);
            spinnerDia.getValueFactory().setValue(mensal.getDiaRecorrencia());
        }
    }

    @FXML
    private void salvar() {
        try {
            String descricao = campoDescricao.getText();
            BigDecimal valor = lerValor();
            LocalDate data = campoData.getValue();
            TipoTransacao tipo = lerTipo();

            Transacao transacao;
            if (checkMensal.isSelected()) {
                transacao = new TransacaoMensal(descricao, valor, tipo, data, spinnerDia.getValue());
            } else {
                transacao = new TransacaoAvulsa(descricao, valor, tipo, data);
            }

            if (transacaoEmEdicao == null) {
                finTracker.cadastrar(transacao);
            } else {
                transacao.setId(transacaoEmEdicao.getId());
                finTracker.atualizar(transacao);
            }

            fecharJanela();
        } catch (EntradaInvalidaException e) {
            mostrarErro("Dados inválidos", e.getMessage());
        } catch (PersistenciaException e) {
            mostrarErro("Erro ao salvar", "Não foi possível salvar a transação. Tente novamente.");
        }
    }

    @FXML
    private void cancelar() {
        fecharJanela();
    }

    private BigDecimal lerValor() throws EntradaInvalidaException {
        String texto = campoValor.getText().trim().replace(",", ".");
        if (texto.isEmpty()) {
            throw new EntradaInvalidaException("Informe o valor!");
        }
        try {
            return new BigDecimal(texto);
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Valor inválido! Use apenas números, por exemplo: 1200,50");
        }
    }

    private TipoTransacao lerTipo() {
        if (radioEntrada.isSelected()) {
            return TipoTransacao.ENTRADA;
        }
        if (radioSaida.isSelected()) {
            return TipoTransacao.SAIDA;
        }
        return null;
    }

    private void mostrarErro(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.initOwner(campoDescricao.getScene().getWindow());
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    private void fecharJanela() {
        Stage janela = (Stage) campoDescricao.getScene().getWindow();
        janela.close();
    }
}