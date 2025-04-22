package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.net.URL;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class FXMLUtil <T> {
    /**
     * Loads an FXML file and returns the root Parent node.
     *
     * @param fxmlPath Path to the FXML file (relative to resources, e.g., "/com/example/fxml/sample.fxml")
     * @return The loaded Parent node
     * @throws IOException If the FXML file cannot be loaded
     */
    public static Parent loadFXML(String fxmlPath) throws IOException {
        return loadFXML(fxmlPath, null);
    }

    /**
     * Loads an FXML file with a specified controller and returns the root Parent node.
     *
     * @param fxmlPath  Path to the FXML file (relative to resources)
     * @param controller The controller instance to set (or null if not needed)
     * @return The loaded Parent node
     * @throws IOException If the FXML file cannot be loaded
     */
    public static Parent loadFXML(String fxmlPath, Object controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getResource(fxmlPath));
        if (controller != null) {
            loader.setController(controller);
        }
        return loader.load();
    }

    /**
     * Loads an FXML file and returns a typed controller.
     *
     * @param fxmlPath Path to the FXML file (relative to resources)
     * @param <T>      The type of the controller
     * @return The controller instance
     * @throws IOException If the FXML file cannot be loaded
     */
    public static <T> T loadFXMLWithController(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getResource(fxmlPath));
        loader.load();
        return loader.getController();
    }

    /**
     * Loads an FXML file with a specified controller and returns a typed controller.
     *
     * @param fxmlPath  Path to the FXML file (relative to resources)
     * @param controller The controller instance to set
     * @param <T>       The type of the controller
     * @return The controller instance
     * @throws IOException If the FXML file cannot be loaded
     */
    public static <T> T loadFXMLWithController(String fxmlPath, Object controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getResource(fxmlPath));
        loader.setController(controller);
        loader.load();
        return loader.getController();
    }

    /**
     * Loads an FXML file and returns both the Parent and the controller.
     *
     * @param fxmlPath Path to the FXML file (relative to resources)
     * @param <T>      The type of the controller
     * @return A Result object containing the Parent and controller
     * @throws IOException If the FXML file cannot be loaded
     */
    public static <T> Result<T> loadFXMLWithResult(String fxmlPath) throws IOException {
        return loadFXMLWithResult(fxmlPath, null);
    }

    /**
     * Loads an FXML file with a specified controller and returns both the Parent and the controller.
     *
     * @param fxmlPath  Path to the FXML file (relative to resources)
     * @param controller The controller instance to set (or null if not needed)
     * @param <T>       The type of the controller
     * @return A Result object containing the Parent and controller
     * @throws IOException If the FXML file cannot be loaded
     */
    public static <T> Result<T> loadFXMLWithResult(String fxmlPath, Object controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getResource(fxmlPath));
        Parent parent = loader.load();
        return new Result<>(parent, loader.getController());
    }

    /**
     * Helper method to get the URL of a resource.
     *
     * @param resourcePath Path to the resource (relative to resources)
     * @return The URL of the resource
     * @throws IllegalArgumentException If the resource is not found
     */
    private static URL getResource(String resourcePath) {
        URL resource = FXMLUtil.class.getResource(resourcePath);
        if (resource == null) {
            throw new IllegalArgumentException("Resource not found: " + resourcePath);
        }
        return resource;
    }

    /**
     * Record to hold the Parent and controller result.
     *
     * @param <T> The type of the controller
     */
    public record Result<T>(Parent parent, T controller) {
    }
}
