package Bomberman.Menu;

import Bomberman.UI.StageStartScene;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.input.view.KeyView;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import static Bomberman.Sounds.SoundEffect.setSoundSwitch;
import static Bomberman.Sounds.SoundEffect.turnOffMusic;
import static com.almasb.fxgl.dsl.FXGL.*;
import static javafx.scene.input.KeyCode.*;

public class MainMenu extends FXGLMenu {
    public MainMenu() {
        super(MenuType.MAIN_MENU);
        ImageView background = new ImageView();

        if (getRandomNumberMain(1, 3) == 1) {
            background.setImage(new Image("assets/textures/main_background.png"));
        }
        else if (getRandomNumberMain(1, 3) == 2) {
            background.setImage(new Image("assets/textures/main_background2.png"));
        }
        else background.setImage(new Image("assets/textures/main_background3.png"));

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.rgb(185, 19, 21));
        dropShadow.setHeight(8);
        dropShadow.setWidth(8);
        dropShadow.setOffsetX(8);
        dropShadow.setOffsetY(10);
        dropShadow.setSpread(10);

        var title = getUIFactoryService().newText(getSettings().getTitle(), Color.rgb(248, 185, 54), 130);
        title.setEffect(dropShadow);
        centerTextBind(title, getAppWidth() / 2.0, 300);

        var version = getUIFactoryService().newText(getSettings().getVersion(), Color.WHITE, 25);
        version.setEffect(new DropShadow(3, 3, 3, Color.RED));
        centerTextBind(version, 860, 250);

        var menuBox = new VBox(
                new MenuButton("New Game", 60, () -> newGame()),
                new MenuButton("How to Play", 30, () -> instruct()),
                new MenuButton("Mute/unmute", 30, () -> setSoundSwitch()),
                new MenuButton("Exit", 30, () -> fireExit())
        );

        menuBox.setAlignment(Pos.CENTER_LEFT);
        menuBox.setTranslateX(getAppWidth() * 0.35);
        menuBox.setTranslateY(getAppHeight() / 2.0 - 20);
        menuBox.setSpacing(0);

        getContentRoot().getChildren().addAll(background, title, version, menuBox);
    }

    private void instruct() {
        GridPane pane = new GridPane();

        pane.addRow(0, getUIFactoryService().newText(" Movement      "),
                new HBox(new KeyView(W), new KeyView(S), new KeyView(A), new KeyView(D)));
        pane.addRow(1, getUIFactoryService().newText(" Place Bomb      "),
                new KeyView(SPACE));

        getDialogService().showBox("How to Play", pane, getUIFactoryService().newButton("OK"));
    }


    public void newGame() {
        fireNewGame();
        getGameTimer().runOnceAfter(() -> {
            turnOffMusic();
            getSceneService().pushSubScene(new StageStartScene());
        }, Duration.millis(10));
    }

    public int getRandomNumberMain(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
