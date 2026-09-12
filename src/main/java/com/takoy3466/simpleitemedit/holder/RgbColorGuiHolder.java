package com.takoy3466.simpleitemedit.holder;

import com.takoy3466.simpleitemedit.editor.EditorRegistry;
import com.takoy3466.simpleitemedit.session.EditingSession;

public class RgbColorGuiHolder extends AbstractGuiHolder {
    private final EditingSession session;
    private final EditorRegistry editors;

    private int red, green, blue;

    public RgbColorGuiHolder(EditingSession session, EditorRegistry editors, int red, int green, int blue) {
        this.session = session;
        this.editors = editors;

        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public EditingSession session() {
        return session;
    }

    public EditorRegistry editors() {
        return editors;
    }

    public int red() {
        return red;
    }

    public int green() {
        return green;
    }

    public int blue() {
        return blue;
    }

    public void red(int red) {
        this.red = clamp(red);
    }

    public void green(int green) {
        this.green = clamp(green);
    }

    public void blue(int blue) {
        this.blue = clamp(blue);
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}