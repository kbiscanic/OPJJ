package hr.fer.zemris.java.hw07.layoutmans;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

import javax.swing.SizeRequirements;

/**
 * Custom Layout Manager that places the components along the vertical axis.
 * There are 3 possible ways of placing components, and these are determined by
 * {@link StackedLayoutDirection} enumeration.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class StackedLayout implements LayoutManager2 {

	private StackedLayoutDirection direction;
	private SizeRequirements xTotal;
	private SizeRequirements yTotal;
	private SizeRequirements[] xChildren;
	private SizeRequirements[] yChildren;

	public StackedLayout(StackedLayoutDirection direction) {
		this.direction = direction;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		invalidateLayout(comp.getParent());
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		invalidateLayout(comp.getParent());
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension preferredLayoutDimension = new Dimension(0, 0);

		determineSizes(parent);

		Insets insets = parent.getInsets();

		preferredLayoutDimension.height = yTotal.preferred + insets.top
				+ insets.bottom;
		preferredLayoutDimension.width = xTotal.preferred + insets.left
				+ insets.right;

		return preferredLayoutDimension;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Dimension minimumLayoutDimension = new Dimension(0, 0);

		determineSizes(parent);

		Insets insets = parent.getInsets();

		minimumLayoutDimension.height = yTotal.minimum + insets.top
				+ insets.bottom;
		minimumLayoutDimension.width = xTotal.minimum + insets.left
				+ insets.right;

		return minimumLayoutDimension;
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();

		Dimension allocated = parent.getSize();
		allocated.height -= insets.top + insets.bottom;
		allocated.width -= insets.left + insets.right;

		determineSizes(parent);

		int compCount = parent.getComponentCount();
		int[] xOffsets = new int[compCount];
		int[] xSpans = new int[compCount];
		int[] yOffsets = new int[compCount];
		int[] ySpans = new int[compCount];

		SizeRequirements.calculateAlignedPositions(allocated.width, xTotal,
				xChildren, xOffsets, xSpans);
		if (direction == StackedLayoutDirection.FILL) {
			SizeRequirements.calculateTiledPositions(allocated.height, yTotal,
					yChildren, yOffsets, ySpans);
		} else {
			SizeRequirements.calculateTiledPositions(yTotal.preferred, yTotal,
					yChildren, yOffsets, ySpans);
		}

		for (int i = 0; i < compCount; i++) {
			Component comp = parent.getComponent(i);
			if (direction == StackedLayoutDirection.FROM_BOTTOM) {
				comp.setBounds(insets.left + xOffsets[i], allocated.height
						+ insets.top - yTotal.preferred + yOffsets[i],
						xSpans[i], ySpans[i]);
			} else {
				comp.setBounds(insets.left + xOffsets[i], insets.top
						+ yOffsets[i], xSpans[i], ySpans[i]);
			}

		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		invalidateLayout(comp.getParent());
	}

	@Override
	public Dimension maximumLayoutSize(Container parent) {
		Dimension maximumLayoutDimension = new Dimension(0, 0);

		determineSizes(parent);

		Insets insets = parent.getInsets();

		maximumLayoutDimension.height = yTotal.maximum + insets.top
				+ insets.bottom;
		maximumLayoutDimension.width = xTotal.maximum + insets.left
				+ insets.right;

		return maximumLayoutDimension;
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		determineSizes(target);
		return xTotal.alignment;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		determineSizes(target);
		return yTotal.alignment;
	}

	@Override
	public void invalidateLayout(Container target) {
		xChildren = null;
		yChildren = null;
		xTotal = null;
		yTotal = null;
	}

	/**
	 * Private method used to determine {@link SizeRequirements} for this
	 * container.
	 * 
	 * @param parent
	 */
	private void determineSizes(Container parent) {
		if (xChildren != null && yChildren != null) {
			return;
		}
		int compCount = parent.getComponentCount();
		xChildren = new SizeRequirements[compCount];
		yChildren = new SizeRequirements[compCount];
		for (int i = 0; i < compCount; i++) {
			Component comp = parent.getComponent(i);
			if (!comp.isVisible()) {
				xChildren[i] = new SizeRequirements();
				yChildren[i] = new SizeRequirements();
				continue;
			}

			Dimension min = comp.getMinimumSize();
			Dimension pref = comp.getPreferredSize();
			Dimension max = comp.getMaximumSize();

			xChildren[i] = new SizeRequirements(min.width, pref.width,
					max.width, 0);
			yChildren[i] = new SizeRequirements(min.height, pref.height,
					max.height, 0.5f);

		}

		xTotal = SizeRequirements.getAlignedSizeRequirements(xChildren);
		yTotal = SizeRequirements.getTiledSizeRequirements(yChildren);

	}

	/**
	 * Public enumeration that determines way of placing components in this
	 * Layout Manager. In case of <code>FROM_TOP</code>, components are placed
	 * from top of container. In case of <code>FROM_BOTTOM</code>, components
	 * are placed in such a way that the last component is placed at the bottom
	 * of container. In case of <code>FILL</code>, components are stretched so
	 * that they fill entire container.
	 * 
	 * @author Kristijan Biscanic
	 * 
	 */
	public enum StackedLayoutDirection {
		FROM_TOP, FROM_BOTTOM, FILL;
	}

}
