package com.utils.swing.xtabbedpane;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DefaultTabRenderer extends AbstractTabRenderer implements PropertyChangeListener {

    private Component prototypeComponent;

    public DefaultTabRenderer() {
        super();
        prototypeComponent = generateRendererComponent(getPrototypeText(), getPrototypeIcon(), getHorizontalTextAlignment());
        addPropertyChangeListener(this);
    }

    private Component generateRendererComponent(String text, Icon icon, int horizontalTabTextAlignment) {
        JPanel rendererComponent = new JPanel(new GridBagLayout());
        rendererComponent.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 4, 2, 4);
        c.fill = GridBagConstraints.HORIZONTAL;
        rendererComponent.add(new JLabel(icon), c);

        c.gridx = 1;
        c.weightx = 1;
        rendererComponent.add(new JLabel(text, horizontalTabTextAlignment), c);

        return rendererComponent;
    }

    @Override
    public Component getTabRendererComponent(JTabbedPane tabbedPane, String text, Icon icon, int tabIndex) {
        Component rendererComponent = generateRendererComponent(text, icon, getHorizontalTextAlignment());
        int prototypeWidth = prototypeComponent.getPreferredSize().width;
        int prototypeHeight = prototypeComponent.getPreferredSize().height;
        rendererComponent.setPreferredSize(new Dimension(prototypeWidth, prototypeHeight));
        return rendererComponent;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if ("prototypeText".equals(propertyName) || "prototypeIcon".equals(propertyName)) {
            this.prototypeComponent = generateRendererComponent(getPrototypeText(), getPrototypeIcon(), getHorizontalTextAlignment());
        }
    }
}
