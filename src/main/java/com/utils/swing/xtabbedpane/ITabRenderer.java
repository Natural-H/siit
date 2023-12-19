package com.utils.swing.xtabbedpane;

import javax.swing.*;
import java.awt.*;

public interface ITabRenderer {
    Component getTabRendererComponent(JTabbedPane tabbedPane, String text, Icon icon, int tabIndex);
}
