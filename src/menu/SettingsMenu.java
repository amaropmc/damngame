package com.codeforall.online.damngame.menu;

import com.codeforall.online.damngame.grid.Grid;
import com.codeforall.online.damngame.menu.buttons.BackButton;
import com.codeforall.online.damngame.menu.mouse.SettingsPointer;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class SettingsMenu extends Menu{

    private BackButton backButton;
    private Picture settingsInfo;
    private SettingsPointer pointer;

    public SettingsMenu(Grid grid) {
        super(grid);

        backButton = new BackButton();
        backButton.drawButton(grid, this.background);

        settingsInfo = new Picture(Grid.PADDING * 8 + grid.columnToX(this.background.getX()), Grid.PADDING * 10, "resources/resources/menu/settingsInfo.png");
        settingsInfo.draw();

        this.pointer = new SettingsPointer(this);
    }

    public BackButton getBackButton() {
        return this.backButton;
    }

    public void delete() {
        this.backButton.delete();
        this.settingsInfo.delete();
        this.pointer = null;
    }

    public void returnToMainMenu() {
        delete();

        new MainMenu(this.grid);
    }
}


