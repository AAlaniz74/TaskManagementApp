package com.sdproject.app.view;

import java.awt.*;

public class GBConstraints extends GridBagConstraints {

    private static final long serialVersionUID = 1L;

    public GBConstraints(int gridX, int gridY)
    {
        super();
        this.gridx = gridX;
        this.gridy = gridY;
        this.insets = new Insets(10, 0, 10, 20);
    }

    public enum Constraints
    {
        PAGE_START(GridBagConstraints.PAGE_START),
        PAGE_END(GridBagConstraints.PAGE_END),
        LINE_END(GridBagConstraints.LINE_END),
        LINE_START(GridBagConstraints.LINE_START),
        FIRST_LINE_END(GridBagConstraints.FIRST_LINE_END),
        FIRST_LINE_START(GridBagConstraints.FIRST_LINE_START),
        SOUTHEAST(GridBagConstraints.LAST_LINE_END),
        LAST_LINE_START(GridBagConstraints.LAST_LINE_START),
        CENTER(GridBagConstraints.CENTER),
        NONE(GridBagConstraints.NONE),
        HORIZONTAL(GridBagConstraints.HORIZONTAL),
        VERTICAL(GridBagConstraints.VERTICAL),
        BOTH(GridBagConstraints.BOTH);

        private int constraint;

        private Constraints(int value)
        {
            constraint = value;
        }

        public int getConstraint()
        {
            return constraint;
        }
    }

    public GBConstraints weight(double x, double y)
    {
        this.weightx = x;
        this.weighty = y;
        return this;
    }

    public GBConstraints fill(int value)
    {
        this.fill = value;
        return this;
    }

    public GBConstraints anchor(int value)
    {
        this.anchor = value;
        return this;
    }

    public GBConstraints insets(int top, int left, int bottom, int right)
    {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

}
