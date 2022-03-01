package com.github.cutplayer4j.imp;

import java.io.File;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 *
 * @author dean
 */
public class Merda extends Application {

  public static void main(String[] args) {
    launch(args);
  }
  //--add-modules=javafx.controls
  @Override
  public void start(Stage primaryStage) {
    final File f = new File("D:\\temp\\video.mp4");
    
    final Media m = new Media(f.toURI().toString());
    final MediaPlayer mp = new MediaPlayer(m);
    
    
    final MediaView mv = new MediaView(mp);
    
    final DoubleProperty width = mv.fitWidthProperty();
    final DoubleProperty height = mv.fitHeightProperty();
    
//    width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
//    height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
    
    mv.setPreserveRatio(true);
    
    StackPane root = new StackPane();
    root.getChildren().add(mv);
    
    final Scene scene = new Scene(root, 960, 540);
    scene.setFill(javafx.scene.paint.Color.BLACK);
    
    primaryStage.setScene(scene);
    primaryStage.setTitle("Full Screen Video Player");
    primaryStage.setFullScreen(true);
    primaryStage.show();
    
    mp.play();
  }
}


/*
  public class Test {

     private static void initAndShowGUI() {
         // This method is invoked on Swing thread
         JFrame frame = new JFrame("FX");
         final JFXPanel fxPanel = new JFXPanel();
         frame.add(fxPanel);
         frame.setVisible(true);

         Platform.runLater(new Runnable() {
             @Override
             public void run() {
                 initFX(fxPanel);
             }
         });
     }

     private static void initFX(JFXPanel fxPanel) {
         // This method is invoked on JavaFX thread
         Scene scene = createScene();
         fxPanel.setScene(scene);
     }

     public static void main(String[] args) {
         SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
                 initAndShowGUI();
             }
         });
     }
 }
 
 
 
 int width = mediaPlayer.getMedia().getWidth();
 int height = mediaPlayer.getMedia().getHeight();
 WritableImage wim = new WritableImage(width, height);
 MediaView mv = new MediaView();
 mv.setFitWidth(width);
 mv.setFitHeight(height);
 mv.setMediaPlayer(mediaPlayer);
 mv.snapshot(null, wim);
 try {
   ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", new File("/test.png"));
 } catch (Exception s) {
   System.out.println(s);
 }
 * */
