package pt.iceman.carcpu.screen;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import pt.iceman.cardata.utils.CustomEntry;

/**
 * Created by iceman on 22/07/2016.
 */
public class ScreenLoader {
    public static void load(AnchorPane anchor, Screen scr)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                anchor.getChildren().clear();
                ParallelTransition pTransition = new ParallelTransition();
                for (int i = 0; i < scr.getNodes().size(); i++)
                {
                    for (CustomEntry<Node, AbsolutePositioning> entry : scr.getNodes())
                    {
                        Node node = entry.getKey();

                        AbsolutePositioning pos = entry.getValue();
                        if (pos.getOrder() == i)
                        {
                            double leftAnchor = pos.getPosX();
                            double rightAnchor = pos.getWindowWidth() - (pos.getPosX() + pos.getWidth());
                            double topAnchor = pos.getPosY();
                            double bottomAnchor = pos.getWindowHeight() - (pos.getPosY() + pos.getHeight());

                            AnchorPane.setLeftAnchor(node, leftAnchor);
                            AnchorPane.setRightAnchor(node, rightAnchor);
                            AnchorPane.setTopAnchor(node, topAnchor);
                            AnchorPane.setBottomAnchor(node, bottomAnchor);

                            anchor.getChildren().add(node);

                            if (pos.getAnimation() != null)
                            {
                                pTransition.getChildren().add(pos.getAnimation());
                            } else
                            {
                                FadeTransition ft = new FadeTransition(Duration.millis(3000), anchor);
                                ft.setFromValue(0.0);
                                ft.setToValue(2.0);
                                pTransition.getChildren().add(ft);
                            }
                        }
                    }
                }
                pTransition.play();
            }
        });
    }
}
