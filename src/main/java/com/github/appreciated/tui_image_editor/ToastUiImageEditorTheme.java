package com.github.appreciated.tui_image_editor;

public enum ToastUiImageEditorTheme {
    /**
     * Defined in resources/META-INF/resources/frontend/com/github/appreciated/tui-image-editor/tui-image-editor-black-theme.js
     */
    blackTheme("blackTheme"),
    /**
     * Defined in resources/META-INF/resources/frontend/com/github/appreciated/tui-image-editor/tui-image-editor-white-theme.js
     */
    whiteTheme("whiteTheme");

    private String theme;

    ToastUiImageEditorTheme(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }
}
