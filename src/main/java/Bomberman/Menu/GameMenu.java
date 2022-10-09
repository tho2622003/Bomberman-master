package Bomberman.Menu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import static Bomberman.Constants.Constant.*;
import static Bomberman.Sounds.SoundEffect.setSoundSwitch;
import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.centerTextBind;

public class GameMenu extends FXGLMenu {
    public GameMenu() {
        super(MenuType.GAME_MENU);
        Shape shape = new Rectangle(SCENE_WIDTH, SCENE_HEIGHT, Color.GREY);
        shape.setOpacity(0.5);

        ImageView background = new ImageView();

        if (getRandomNumberPause(1, 3) == 1) {
            background.setImage(new Image("assets/textures/esc_background.png"));
        }
        else if (getRandomNumberPause(1, 3) == 2) {
            background.setImage(new Image("assets/textures/esc_background2.png"));
        }
        else background.setImage(new Image("assets/textures/esc_background3.png"));

        background.setX(160);
        background.setY(90);
        background.setEffect(new DropShadow(5, 3.5, 3.5, Color.WHITE));
        background.setEffect(new Lighting());

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.rgb(185, 19, 21));
        dropShadow.setHeight(7);
        dropShadow.setWidth(7);
        dropShadow.setOffsetX(6);
        dropShadow.setOffsetY(8);
        dropShadow.setSpread(8);

        var title = getUIFactoryService().newText(getSettings().getTitle(), Color.rgb(248, 185, 54), 90);
        title.setEffect(dropShadow);
        centerTextBind(title, getAppWidth() / 2.0, 310);

        var version = getUIFactoryService().newText(getSettings().getVersion(), Color.WHITE, 20);
        version.setEffect(new DropShadow(3, 3, 3, Color.RED));
        centerTextBind(version, 800, 280);

        var menuBox = new VBox(
                new MenuButton("Resume", 52, () -> fireResume()),
                new MenuButton("Mute/unmute", 22, () -> setSoundSwitch()),
                new MenuButton("Return to Menu", 22, () -> fireExitToMainMenu()),
                new MenuButton("Exit", 22, () -> fireExit())
        );

        menuBox.setAlignment(Pos.CENTER_LEFT);
        menuBox.setTranslateX(getAppWidth() / 2.0 - 110);
        menuBox.setTranslateY(getAppHeight() / 2.0 - 20);
        menuBox.setSpacing(0);

        getContentRoot().getChildren().addAll(shape, background, title, version, menuBox);
    }

    public int getRandomNumberPause(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
