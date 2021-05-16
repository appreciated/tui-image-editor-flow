package com.github.appreciated.tui_editor;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.function.Consumer;

@Tag("tui-image-editor-wrapper")
@NpmPackage(value = "tui-image-editor", version = "^3.14.3")
@StyleSheet("https://uicdn.toast.com/tui-image-editor/v3.14.3/tui-image-editor.css")
@StyleSheet("https://uicdn.toast.com/tui-color-picker/v2.2.6/tui-color-picker.css")
@JsModule("./com/github/appreciated/tui-image-editor/tui-image-editor-wrapper.js")
public class ToastUiImageEditor extends Component implements HasSize {


    private Consumer<byte[]> consumer;

    public ToastUiImageEditor() {
    }

    /**
     * The Image shown by the ToastUI Editor can be read from the Vaadin side using a Method.
     */
    public void getImage(Consumer<byte[]> consumer) {
        getElement().callJsFunction("retrieveImageData");
        this.consumer = consumer;
    }

    @ClientCallable
    public void onImageDataUpdate(String data) {
        // data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...
        String[] splitString = data.split(",");
        String dataDescription = splitString[0];
        String actualData = splitString[1];
        consumer.accept(Base64.getDecoder().decode(actualData.getBytes()));
    }

    /**
     * The property initMenu of the ToastUI Editor can be set from the Vaadin side (f.e. 'filter' ...).
     */
    public void setInitMenu(String initMenu) {
        getElement().setProperty("initMenu", initMenu);
    }

    /**
     * The property menuBarPosition of the ToastUI Editor can be set from the Vaadin side (f.e. 'top' ...).
     */
    public void setMenuBarPosition(String menuBarPosition) {
        getElement().setProperty("menuBarPosition", menuBarPosition);
    }

    public void setImage(byte[] toArray) {
        StreamResource resource = new StreamResource("temporary-image-resource.png",
                () -> new ByteArrayInputStream(toArray));
        getElement().setAttribute("image-url", resource);
        getElement().callJsFunction("setEditorImage");
    }

    public void setTheme(ToastUiImageEditorTheme theme) {
        getElement().setProperty("theme", theme.getTheme());
    }
}
