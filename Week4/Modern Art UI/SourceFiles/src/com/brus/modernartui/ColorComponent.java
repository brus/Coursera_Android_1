package com.brus.modernartui;

import android.graphics.Color;

public class ColorComponent {

	private String[] colors;
	private boolean directOrder;

	public ColorComponent(String[] colors, boolean directOrder) {
		this.colors = colors;
		this.directOrder = directOrder;
	}

	public int getColor(int sliderValue) {
		return Color.parseColor(directOrder ? colors[sliderValue] : colors[colors.length - 1 - sliderValue]);
	}
}
