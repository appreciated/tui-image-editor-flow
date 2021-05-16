package com.github.appreciated;

import com.github.appreciated.tui_editor.ToastUiImageEditor;
import com.github.appreciated.tui_editor.ToastUiImageEditorTheme;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.Route;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Route("")
public class View extends FlexLayout {
    private final ToastUiImageEditor toastUiImageEditor;
    private final Button load;
    private final Button store;

    public View() {
        setFlexDirection(FlexDirection.COLUMN);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        toastUiImageEditor = new ToastUiImageEditor();
        toastUiImageEditor.setHeight("1000px");
        toastUiImageEditor.setWidth("1100px");
        toastUiImageEditor.setInitMenu("filter");
        toastUiImageEditor.setTheme(ToastUiImageEditorTheme.whiteTheme);
        toastUiImageEditor.setMenuBarPosition("bottom");
        add(toastUiImageEditor);
        load = new Button("Load", buttonClickEvent -> {
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource("test-image.png").toURI()));
                toastUiImageEditor.setImage(bytes);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
        add(load);
        store = new Button("Store", buttonClickEvent ->
                toastUiImageEditor.getImage(bytes -> {
                    try {
                        FileOutputStream stream = new FileOutputStream(System.getProperty("user.dir") + "/"+"test.png");
                        stream.write(bytes);
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
        );
        add(store);
    }

}
