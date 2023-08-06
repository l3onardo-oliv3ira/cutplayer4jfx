
package com.github.cutplayer4j.gui.imp;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class BorderedStandardLabel extends StandardLabel {

  private static final Border STANDARD_BORDER = BorderFactory.createCompoundBorder(
    BorderFactory.createBevelBorder(BevelBorder.LOWERED),
    BorderFactory.createEmptyBorder(1, 2, 1, 2)
  );

  public BorderedStandardLabel() {
    setBorder(STANDARD_BORDER);
  }

  public BorderedStandardLabel(String template) {
    super(template);
  }
}
