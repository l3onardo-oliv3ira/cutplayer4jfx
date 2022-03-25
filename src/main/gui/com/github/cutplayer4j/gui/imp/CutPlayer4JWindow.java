package com.github.cutplayer4j.gui.imp;

import static com.github.cutplayer4j.imp.CutPlayer4J.application;
import static com.github.cutplayer4j.imp.CutPlayer4J.fileChooser;
import static com.github.cutplayer4j.view.action.ResourceAction.resource;
import static com.github.utils4j.imp.Containers.isEmpty;
import static com.github.utils4j.imp.Strings.trim;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import com.github.cutplayer4j.event.SnapshotImageEvent;
import com.github.cutplayer4j.gui.ICutPlayer4JWindow;
import com.github.cutplayer4j.gui.IMediaPlayerViewer;
import com.github.cutplayer4j.view.action.mediaplayer.MediaPlayerActions;
import com.github.utils4j.gui.imp.MediaTransferHandler;
import com.github.utils4j.gui.imp.StandardAction;
import com.github.utils4j.imp.Args;
import com.google.common.eventbus.Subscribe;

import net.miginfocom.swing.MigLayout;

public class CutPlayer4JWindow extends ShutdownAwareFrame implements ICutPlayer4JWindow {

  private static final String ACTION_EXIT_FULLSCREEN = "exit-fullscreen";
  
  private final Action mediaOpenAction;
  
  private final Action mediaQuitAction;

  private final StandardAction videoFullscreenAction;
  
  private final StandardAction videoAlwaysOnTopAction;

  private final StandardAction viewStatusBarAction;

  private final Action helpAboutAction;

  private final JMenuBar menuBar;

  private final JMenu mediaMenu;
  
  private final JMenu mediaRecentMenu;

  private final JMenu playbackMenu;
  
  private final JMenu playbackSpeedMenu;

  private final JMenu audioMenu;

  private final JMenu videoMenu;

  private final JMenu viewMenu;

  private final JMenu helpMenu;

  private final PositionPane positionPane;

  private final ControlsPane controlsPane;

  private final StatusBar statusBar;

  private final JPanel bottomPane;
  
  private final CutManagerPanel cutManagerPane;
  
  private final IMediaPlayerViewer playerViewer;
  
  public CutPlayer4JWindow() {
    super("CutPlayer4J", Images.CUTPLAYER.asImage().orElse(null));
    
    playerViewer = application().playerViewer();
    
    mediaOpenAction = new StandardAction(resource("menu.media.item.openFile")) {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = fileChooser();
        if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(CutPlayer4JWindow.this)) {
          File file = fileChooser.getSelectedFile();
          open(file);
        }
      }
    };
    
    mediaQuitAction = new StandardAction(resource("menu.media.item.quit")) {
      @Override
      public void actionPerformed(ActionEvent e) {
        CutPlayer4JWindow.this.close();
        System.exit(0);
      }
    };

    videoFullscreenAction = new StandardAction(resource("menu.video.item.fullscreen")) {
      @Override
      public void actionPerformed(ActionEvent e) {
        //mediaPlayer.toggleFullScreen(); WE HAVE TO GO BACK HERE!
      }
    };

    videoAlwaysOnTopAction = new StandardAction(resource("menu.video.item.alwaysOnTop")) {
      @Override
      public void actionPerformed(ActionEvent e) {
        boolean onTop;
        Object source = e.getSource();
        if (source instanceof JCheckBoxMenuItem) {
          JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem)source;
          onTop = menuItem.isSelected();
        }
        else {
          throw new IllegalStateException("Don't know about source " + source);
        }
        setAlwaysOnTop(onTop);
      }
    };

    viewStatusBarAction = new StandardAction(resource("menu.view.item.statusBar")) {
      @Override
      public void actionPerformed(ActionEvent e) {
        boolean visible;
        Object source = e.getSource();
        if (source instanceof JCheckBoxMenuItem) {
          JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem)source;
          visible = menuItem.isSelected();
        }
        else {
          throw new IllegalStateException("Don't know about source " + source);
        }
        statusBar.setVisible(visible);
        bottomPane.invalidate();
        bottomPane.revalidate();
        bottomPane.getParent().invalidate();
        bottomPane.getParent().revalidate();
        CutPlayer4JWindow.this.invalidate();
        CutPlayer4JWindow.this.revalidate();
      }
    };

    helpAboutAction = new StandardAction(resource("menu.help.item.about")) {
      @Override
      public void actionPerformed(ActionEvent e) {
        AboutDialog dialog = new AboutDialog(CutPlayer4JWindow.this);
        dialog.setLocationRelativeTo(CutPlayer4JWindow.this);
        dialog.setVisible(true);
      }
    };
    
    menuBar = new JMenuBar();

    mediaMenu = new JMenu(resource("menu.media").name());
    mediaMenu.setMnemonic(resource("menu.media").mnemonic());
    mediaMenu.add(new JMenuItem(mediaOpenAction));
    mediaRecentMenu = new RecentMediaMenu(resource("menu.media.item.recent")).menu();
    mediaRecentMenu.setIcon(resource("menu.media.item.recent").menuIcon());
    
    mediaMenu.add(mediaRecentMenu);
    mediaMenu.add(new JSeparator());
    mediaMenu.add(new JMenuItem(mediaQuitAction));
    menuBar.add(mediaMenu);

    playbackMenu = new JMenu(resource("menu.playback").name());
    playbackMenu.setMnemonic(resource("menu.playback").mnemonic());

    playbackMenu.add(new JSeparator());

    MediaPlayerActions mpa = application().mediaPlayerActions();
    playbackSpeedMenu = new JMenu(resource("menu.playback.item.speed").name());
    playbackSpeedMenu.setMnemonic(resource("menu.playback.item.speed").mnemonic());
    playbackSpeedMenu.setIcon(resource("menu.playback.item.speed").menuIcon());
    for (Action action : mpa.playbackSpeedActions()) {
      playbackSpeedMenu.add(new JCheckBoxMenuItem(action));
      application().subscribe(action);
    }
    playbackMenu.add(playbackSpeedMenu);
    playbackMenu.add(new JSeparator());
    for (Action action : mpa.playbackSkipActions()) {
      playbackMenu.add(new JMenuItem(action));
    }
    playbackMenu.add(new JSeparator());
    for (Action action : mpa.playbackControlActions()) {
      application().subscribe(action);
      playbackMenu.add(new JMenuItem(action));
    }
    menuBar.add(playbackMenu);

    audioMenu = new JMenu(resource("menu.audio").name());
    audioMenu.setMnemonic(resource("menu.audio").mnemonic());

    for (Action action : mpa.audioControlActions()) {
      audioMenu.add(new JMenuItem(action));
    }
    menuBar.add(audioMenu);

    videoMenu = new JMenu(resource("menu.video").name());
    videoMenu.setMnemonic(resource("menu.video").mnemonic());

    videoMenu.add(new JCheckBoxMenuItem(videoFullscreenAction));
    videoMenu.add(new JCheckBoxMenuItem(videoAlwaysOnTopAction));
    videoMenu.add(new JSeparator());
    videoMenu.add(new JMenuItem(mpa.videoSnapshotAction()));
    videoMenu.add(new JSeparator());
    menuBar.add(videoMenu);

    viewMenu = new JMenu(resource("menu.view").name());
    viewMenu.setMnemonic(resource("menu.view").mnemonic());
    viewMenu.add(new JCheckBoxMenuItem(viewStatusBarAction));
    menuBar.add(viewMenu);

    helpMenu = new JMenu(resource("menu.help").name());
    helpMenu.setMnemonic(resource("menu.help").mnemonic());
    helpMenu.add(new JMenuItem(helpAboutAction));
    menuBar.add(helpMenu);

    setJMenuBar(menuBar);

    JPanel contentPane = new JPanel();
    contentPane.setLayout(new BorderLayout());
    
    JPanel playerPane = new JPanel(new BorderLayout());
    playerPane.add(playerViewer.asPanel(), BorderLayout.CENTER);

    JPanel bottomControlsPane = new JPanel();
    bottomControlsPane.setLayout(new MigLayout("fill, insets 0 n n n", "[grow]", "[]0[]"));

    positionPane = new PositionPane();
    bottomControlsPane.add(positionPane, "grow, wrap");
    controlsPane = new ControlsPane(mpa);
    bottomControlsPane.add(controlsPane, "grow");
    playerPane.add(bottomControlsPane, BorderLayout.SOUTH);
    
    contentPane.add(playerPane, BorderLayout.CENTER);
    
    contentPane.setTransferHandler(new MediaTransferHandler() {
      @Override
      protected void onMediaDropped(String[] uris) {
        if (!isEmpty(uris)) {
          playerViewer.mediaPlayer().play(new File(trim(uris[0])).toURI().toString());
        }
      }
    });
    
    bottomPane = new JPanel();
    bottomPane.setLayout(new BorderLayout());

    statusBar = new StatusBar();
    bottomPane.add(statusBar, BorderLayout.SOUTH);
    
    JPanel leftPane = new JPanel();
    leftPane.setLayout(new BorderLayout());
    
    leftPane.add(new SaveAllSlicesPanel(), BorderLayout.NORTH);
    
    JPanel barFix = new JPanel();
    barFix.setLayout(new BorderLayout());
    cutManagerPane = new CutManagerPanel(
      () -> {
        contentPane.remove(leftPane);
        contentPane.updateUI();
      }, 
      () -> {
        contentPane.add(leftPane, BorderLayout.WEST);
        contentPane.updateUI();
      }
    );
    barFix.add(cutManagerPane, BorderLayout.CENTER);
    barFix.add(new JPanel(), BorderLayout.EAST); //fix scroll bar 
    
    JScrollPane scrollPane = new JScrollPane(barFix);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    leftPane.add(scrollPane, BorderLayout.CENTER);
    
    contentPane.add(bottomPane, BorderLayout.SOUTH);

    setContentPane(contentPane);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    applyPreferences();
    setMinimumSize(new Dimension(370, 240));
    getActionMap().put(ACTION_EXIT_FULLSCREEN, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // WE HAVE TO GO BACK HERE!
        videoFullscreenAction.select(false);
      }
    });
  }
  
  @Override
  public void open(File file) {
    Args.requireNonNull(file, "file is null");
    if (playerViewer.mediaPlayer().play(file.toURI().toString())) {
      application().addRecentMedia(file.getAbsolutePath());
    }
  }
  
  @Subscribe
  public void onSnapshotImage(SnapshotImageEvent event) {
    new SnapshotFrame(event.image());
  }
  
  private ActionMap getActionMap() {
    JComponent c = (JComponent) getContentPane();
    return c.getActionMap();
  }
  
  @Override
  public void display() {
    super.showToFront();
  }
  
  private void applyPreferences() {
    Preferences prefs = Preferences.userNodeForPackage(CutPlayer4JWindow.class);
    setBounds(100, 100, 800, 600);
    boolean alwaysOnTop = prefs.getBoolean("alwaysOnTop", false);
    setAlwaysOnTop(alwaysOnTop);
    videoAlwaysOnTopAction.select(alwaysOnTop);
    boolean statusBarVisible = prefs.getBoolean("statusBar", false);
    statusBar.setVisible(statusBarVisible);
    viewStatusBarAction.select(statusBarVisible);
    fileChooser().setCurrentDirectory(new File(prefs.get("chooserDirectory", ".")));
    String recentMedia = prefs.get("recentMedia", "");
    if (recentMedia.length() > 0) {
      List<String> mrls = Arrays.asList(prefs.get("recentMedia", "").split("\\|"));
      Collections.reverse(mrls);
      for (String mrl : mrls) {
        application().addRecentMedia(mrl);
      }
    }
  }
  
  
  @Override
  protected void onShutdown() {
    if (wasShown()) {
      Preferences prefs = Preferences.userNodeForPackage(CutPlayer4JWindow.class);
//      prefs.putInt("frameX", getX());
//      prefs.putInt("frameY", getY());
//      prefs.putInt("frameWidth", getWidth());
//      prefs.putInt("frameHeight", getHeight());
      prefs.putBoolean("alwaysOnTop", isAlwaysOnTop());
      prefs.putBoolean("statusBar", statusBar.isVisible());
      prefs.put("chooserDirectory", fileChooser().getCurrentDirectory().toString());
      String recentMedia;
      List<String> mrls = application().recentMedia();
      if (!mrls.isEmpty()) {
        StringBuilder sb = new StringBuilder();
        for (String mrl : mrls) {
          if (sb.length() > 0) {
            sb.append('|');
          }
          sb.append(mrl);
        }
        recentMedia = sb.toString();
      }
      else {
        recentMedia = "";
      }
      prefs.put("recentMedia", recentMedia);
    }
    application().closeApplication();
  }
}
