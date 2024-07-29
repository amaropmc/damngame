package com.codeforall.online.damngame;

import com.codeforall.online.damngame.Player;
import com.codeforall.online.damngame.grid.Position;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;

/* KeyHandler.java */
public class KeyHandler implements KeyboardHandler {

    private Player player;
    private Keyboard keyboard;

    public KeyHandler(Player player) {
        this.player = player;

        this.keyboard = new Keyboard(this);
        createKeyboardEvents();
    }

    public void createKeyboardEvents() {

        KeyboardEvent keyboardEventLeft = new KeyboardEvent();
        keyboardEventLeft.setKey(KeyboardEvent.KEY_LEFT);
        keyboardEventLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventLeft);

        KeyboardEvent keyboardEventRight = new KeyboardEvent();
        keyboardEventRight.setKey(KeyboardEvent.KEY_RIGHT);
        keyboardEventRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventRight);

    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        // this is going to happen when keys F or R are pressed
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_LEFT:
                player.moveLeft();
                break;
            case KeyboardEvent.KEY_RIGHT:
                player.moveRight();
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        // specify what will happen when the keys are released
    }
}