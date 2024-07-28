package com.codeforall.online.damngame.animals;

import org.academiadecodigo.simplegraphics.pictures.Picture;

public abstract class Animal {

    private Picture picture;

    public Animal(Picture picture) {
        this.picture = picture;
        picture.draw();
    }

    public int getUpperBorder(){
        return this.picture.getY();
    }

    public int getRightBorder(){
        return this.picture.getMaxX();
    }

    public int getLowerBorder(){
        return this.picture.getMaxY();
    }

    public int getLeftBorder(){
        return this.picture.getX();
    }

    public void moveRight(){
        this.picture.translate(20,0);
    }

    public void moveLeft(){
        this.picture.translate(-20,0);
    }

    public void moveUp(){
        this.picture.translate(0,-10);
    }

    public void moveDown(){
        this.picture.translate(0,10);
    }

    public void remove(){
        this.picture.delete();
    }
}
