package util.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

/**
 * Provides helper methods for controllers.
 */
public class ControllerHelper {
    /**
     * Load and show FXML resources
     * @param fxmlLoader fxml loader
     * @param resourceName resource name
     * @param stage stage
     * @throws IOException when FXML resource is not available
     */
    public static void loadAndShowFXML(FXMLLoader fxmlLoader, String resourceName, Stage stage) throws IOException {
        Logger.trace("Loading FXML resource {}", resourceName);
        fxmlLoader.setLocation(fxmlLoader.getClass().getResource(resourceName));
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
