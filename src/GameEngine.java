package com.codeforall.online.damngame;

import com.codeforall.online.damngame.animals.AnimalFactory;
import com.codeforall.online.damngame.animals.ducks.Duck;
import com.codeforall.online.damngame.animals.ducks.DuckReward;
import com.codeforall.online.damngame.animals.sharks.Shark;
import com.codeforall.online.damngame.controlers.KeyHandler;
import com.codeforall.online.damngame.controlers.DuckCatcher;
import com.codeforall.online.damngame.grid.Grid;
import com.codeforall.online.damngame.menu.Menu;
import com.codeforall.online.damngame.menu.mouse.MenuPointer;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameEngine {
    private Grid grid;
    private Player player;
    private List<Duck> ducks = new ArrayList<>();
    private List<Shark> sharks = new ArrayList<>();
    private List<DuckReward> rewards = new ArrayList<>();
    private boolean canGameStart = false;

    public GameEngine() {
        this.grid = new Grid(100, 50);
    }

    public void init() throws InterruptedException {
        Menu menu =  new Menu(this.grid);
        MenuPointer menuPointer = new MenuPointer(menu);

        while(!canGameStart){
            if(menu.getGameStart()){
                this.canGameStart = true;
                menu = null;
                start();
            }else if(menu.getQuitGame()){
                System.exit(0);
            }
        }
    }

    public void start() throws InterruptedException {
        this.grid.init();
        this.player = new Player(this.grid);
        new KeyHandler(this.player);

        this.ducks.add(AnimalFactory.getNewDuck(grid));
        this.sharks.add(AnimalFactory.getNewShark(grid));

        int duckMovementCounter = 0;
        int sharkMovementCounter = 0;

        while(this.player.getLives() > 0) {

            Thread.sleep(100);

            duckMovementCounter = ducksMove(duckMovementCounter);

            sharkMovementCounter = sharksMove(sharkMovementCounter);

            rewardsMove();
        }

        gameOver();
    }

    public void gameOver(){
        Picture gameOver = new Picture(grid.columnToX(grid.getCols()) / 2, grid.rowToY(grid.getRows()) / 3, "resources/gameover.png");
        gameOver.draw();
        gameOver.translate(-100,0);
        gameOver.grow(100,100);
    }

    public int ducksMove(int duckMovementCounter) {
        if (ducks.isEmpty() || duckMovementCounter % 50 == 0) {
            ducks.add(AnimalFactory.getNewDuck(grid));
        }

        Iterator<Duck> duckIterator = ducks.iterator();
        while (duckIterator.hasNext()) {
            Duck duck = duckIterator.next();

            DuckCatcher duckCatcher = new DuckCatcher(duck);

            duck.moveRight();
            duckMovementCounter ++;

            if(duck.hasReward() && duck.getDuckReward() != null){
                rewards.add(duck.getDuckReward());
                rewardsMove();
            }

            if (duck.getRightBorder() >= grid.columnToX(grid.getCols())) {
                duck.remove();
            }

            if (duck.getToRemove()) {
                duckIterator.remove();
            }
        }
        return duckMovementCounter;
    }

    public void rewardsMove() {

        Iterator<DuckReward> rewardIterator = rewards.iterator();
        while(rewardIterator.hasNext()){
            DuckReward reward = rewardIterator.next();

            reward.getRewardPic().draw();
            reward.moveDown();

            if(reward.getRewardPic().getMaxY() > player.getPicture().getMaxY()){
                reward.getRewardPic().delete();
                rewardIterator.remove();
            }

            CollisionDetector collectReward = new CollisionDetector(reward.getRewardPic(), this.player.getPicture());

            if (collectReward.hasCollided()) {
                if(player.getLives() < 3){
                    player.increaseLives();
                }

                reward.getRewardPic().delete();
                rewardIterator.remove();
            }
        }
    }

    public int sharksMove(int sharkMovementCounter) {
        if (sharks.isEmpty() || sharkMovementCounter % 20 == 0) {
            sharks.add(AnimalFactory.getNewShark(grid));
        }

        Iterator<Shark> sharkIterator = sharks.iterator();
        while (sharkIterator.hasNext()) {
            Shark shark = sharkIterator.next();

            shark.moveUp();
            sharkMovementCounter++;

            CollisionDetector collision = new CollisionDetector(this.player.getPicture(), shark.getPicture());

            if (collision.hasCollided()) {
                this.player.decrementLives();
                System.out.println(this.player.getLives());

                shark.remove();
                sharkIterator.remove();

            } else if (shark.getUpperBorder() <= grid.rowToY(grid.getRows()) / 1.53) {
                shark.remove();
                sharkIterator.remove();
            }
        }
        return sharkMovementCounter;
    }
}
