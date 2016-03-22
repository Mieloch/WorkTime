package topworker.view.naviagtion.download;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by echomil on 21.03.16.
 */

@SpringView(name = Download.VIEW_NAME)
public class Download extends VerticalLayout implements View {

    public static final String VIEW_NAME = "download";
    private final String CLIENT_PATH = "/var/lib/openshift/56f076250c1e664af2000037/app-root/data/TopWorkerClient.zip";
    private VerticalLayout content;
    private Label downloadLabel;

    public Download() {
        init();
        addComponent(downloadLabel);
        addComponent(content);
        setExpandRatio(downloadLabel, 1.5f);
        setExpandRatio(downloadLabel, 9f);
        setComponentAlignment(downloadLabel, Alignment.MIDDLE_CENTER);
        setComponentAlignment(content, Alignment.TOP_CENTER);
    }

    private void init() {
        content = new VerticalLayout();
        TextArea instruction = new TextArea();
        instruction.setEnabled(false);
        instruction.addStyleName("information");
        instruction.setValue("1. Wypakuj archiwum.\n2. W pliku config.properties ustaw swój login oraz hasło.\n3. Dodaj TopWorkerClient.jar do programów uruchamianych przy starcie systemu.");
        instruction.setSizeFull();
        Button button = new Button("Pobierz");
        StreamResource resource = createResource();
        FileDownloader fileDownloader = new FileDownloader(resource);
        fileDownloader.extend(button);
        content.addComponent(instruction);
        content.addComponent(button);
        content.setWidth(40f, Unit.PERCENTAGE);
        content.setHeight(100f, Unit.PERCENTAGE);
        content.setExpandRatio(instruction, 1.5f);
        content.setExpandRatio(button, 9f);
        content.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        content.setComponentAlignment(instruction, Alignment.MIDDLE_CENTER);
        downloadLabel = new Label("Instalacja");
        downloadLabel.addStyleName("download-label");
    }

    private StreamResource createResource() {
        return new StreamResource(new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                try {
                    return new FileInputStream(CLIENT_PATH);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, "TopWorkerClient.zip");


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
