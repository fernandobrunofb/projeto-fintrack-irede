package fintrack.app;

import fintrack.controller.TelaPrincipalController;
import fintrack.dao.Conexao;
import fintrack.dao.TransacaoDAO;
import fintrack.service.FinTracker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

public class FinApp extends Application {

    private Connection conexao;

    @Override
    public void start(Stage stage) throws Exception {
        conexao = Conexao.conectar();
        FinTracker finTracker = new FinTracker(new TransacaoDAO(conexao));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fintrack/view/TelaPrincipal.fxml"));
        Parent raiz = loader.load();

        TelaPrincipalController controller = loader.getController();
        controller.setFinTracker(finTracker);

        stage.setTitle("FinTrack");
        stage.setScene(new Scene(raiz, 800, 500));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        if (conexao != null) {
            conexao.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}