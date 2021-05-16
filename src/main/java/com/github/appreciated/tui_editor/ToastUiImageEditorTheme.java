package com.github.appreciated.tui_editor;

public enum ToastUiImageEditorTheme {
    blackTheme("blackTheme"), whiteTheme("whiteTheme");

    private String theme;

    ToastUiImageEditorTheme(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }
}
