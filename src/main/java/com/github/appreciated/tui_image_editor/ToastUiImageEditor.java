package com.github.appreciated.tui_image_editor;

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

/**
 * A Vaadin Component that wraps some base functionality of the Toast UI Image Editor.
 */

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
     * Receives asynchronously the currently shown Image in the png format.
     * @param consumer A consumer instance to receive the current image as png in form of a byte array.
     */
    public void getImage(Consumer<byte[]> consumer) {
        getElement().callJsFunction("retrieveImageData");
        this.consumer = consumer;
    }

    /**
     * Receives the currently shown as png in form of a base64 String and passes it decoded to the consumer.
     */
    @ClientCallable
    private void onImageDataUpdate(String data) {
        // data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...
        String[] splitString = data.split(",");
        // String dataDescription = splitString[0];
        String actualData = splitString[1];
        consumer.accept(Base64.getDecoder().decode(actualData.getBytes()));
    }

    /**
     * Sets the first menu to be selected and started.
     * <p></p>
     * This is being done by setting the ImageEditor.options.initMenu property of the Toast UI Image Editor (@see <a href="http://nhn.github.io/tui.image-editor/latest/ImageEditor">http://nhn.github.io/tui.image-editor/latest/ImageEditor</a>).
     */
    public void setInitMenu(String initMenu) {
        getElement().setProperty("initMenu", initMenu);
    }

    /**
     * Sets the menu bar position (allowed values are 'top', 'bottom', 'left', 'right').
     * <p></p>
     * This is being done by setting the ImageEditor.options.menuBarPosition property of the Toast UI Image Editor (@see <a href="http://nhn.github.io/tui.image-editor/latest/ImageEditor">http://nhn.github.io/tui.image-editor/latest/ImageEditor</a>).
     */
    public void setMenuBarPosition(String menuBarPosition) {
        getElement().setProperty("menuBarPosition", menuBarPosition);
    }

    /**
     * Sets the currently shown image of the Toast UI Image Editor. Has currently only been tested with png. Other formats may work also.
     * @param toArray a non encoded byte array of the image
     */
    public void setImage(byte[] toArray) {
        StreamResource resource = new StreamResource(
                "temporary-image-resource.png",
                () -> new ByteArrayInputStream(toArray)
        );
        getElement().setAttribute("image-url", resource);
        getElement().callJsFunction("setEditorImage");
    }

    /**
     * Sets the theme of the Toast UI Image Editor. The themes are predefined in JavaScript.
     * <p></p>
     * This is being done by setting the ImageEditor.options.theme property of the Toast UI Image Editor (@see <a href="http://nhn.github.io/tui.image-editor/latest/ImageEditor">http://nhn.github.io/tui.image-editor/latest/ImageEditor</a>).
     */
    public void setTheme(ToastUiImageEditorTheme theme) {
        getElement().setProperty("theme", theme.getTheme());
    }
}
