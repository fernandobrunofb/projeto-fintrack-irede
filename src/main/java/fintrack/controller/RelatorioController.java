package fintrack.controller;

import fintrack.service.FinTracker;
import fintrack.utils.Formatador;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
        labelSaldo.setText(Formatador.formatarValor(finTracker.calcularSaldo()));
    }
}