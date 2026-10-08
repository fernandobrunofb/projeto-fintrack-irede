package fintrack.controller;

import fintrack.service.FinTracker;
import fintrack.utils.Formatador;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.math.BigDecimal;

public class RelatorioController {

    @FXML
    private Label labelEntradas;
    @FXML
    private Label labelSaidas;
    @FXML
    private Label labelSaldo;

    public void setFinTracker(FinTracker finTracker) {
        labelEntradas.setText(Formatador.formatarValor(finTracker.calcularTotalEntradas()));
        labelSaidas.setText(Formatador.formatarValor(finTracker.calcularTotalSaidas()));

        BigDecimal saldo = finTracker.calcularSaldo();
        labelSaldo.setText(Formatador.formatarValor(saldo));
        if (saldo.signum() < 0) {
            labelSaldo.getStyleClass().add("negativo");
        }
    }
}