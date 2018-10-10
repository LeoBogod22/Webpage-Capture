
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserType;
import com.teamdev.jxbrowser.chromium.Callback;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import com.teamdev.jxbrowser.chromium.javafx.internal.LightWeightWidget;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;

/**
 * The sample demonstrates how to get screen shot of the web page
 * and save it as PNG image file.
 */
public class WebCrawler extends Application {
    @Override
    public void start(final Stage primaryStage) throws Exception {
        // #1 Create Browser instance
        final Browser browser = new Browser(BrowserType.LIGHTWEIGHT);
        final BrowserView view = new BrowserView(browser);

        // #2 Set the required view size
        browser.setSize(1280, 1024);

        // Wait until Chromium resizes view
        Thread.sleep(500);

        // #3 Load web page and wait until web page is loaded completely
        Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
            @Override
            public void invoke(Browser browser) {
                browser.loadURL("https://teamdev.com");
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Wait until Chromium renders web page content
                    Thread.sleep(500);

                    // #4 Get javafx.scene.image.Image of the loaded web page
                    LightWeightWidget lightWeightWidget = (LightWeightWidget) view.getChildren().get(0);
                    Image image = lightWeightWidget.getImage();

                    // Save the image into a PNG file
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", new File("google.com.png"));
                } catch (Exception ignored) {}
                finally {
                    // Dispose Browser instance
                    browser.dispose();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}