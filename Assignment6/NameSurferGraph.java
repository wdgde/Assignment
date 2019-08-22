
/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GLine;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {

	// 颜色数组
	private Color[] color = new Color[] { Color.BLACK, Color.RED, Color.BLUE, Color.ORANGE };
	// 存放每个NameSurferEntry对象的数组表
	private ArrayList<NameSurferEntry> Entry = new ArrayList<NameSurferEntry>();

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		// You fill in the rest //
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	// 删除单个名字对应的折线
	public void clear(String name) {
		// You fill this in //
		name = name.toLowerCase();
		for (int i = 0; i < Entry.size(); i++) {
			if (Entry.get(i) == null) {
				continue;
			}
			if (name.equals(Entry.get(i).getName().toLowerCase())) {
				Entry.set(i, null);
			}
		}
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note that
	 * this method does not actually draw the graph, but simply stores the entry;
	 * the graph is drawn by calling update.
	 */
	// 将一个名字及其对应的排名添加进数组表
	public void addEntry(NameSurferEntry entry) {
		// You fill this in //
		Entry.add(entry);
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of entries.
	 * Your application must call update after calling either clear or addEntry;
	 * update is also called whenever the size of the canvas changes.
	 */
	// 更新显示的画面
	public void update() {
		// You fill this in //
		removeAll();
		// 绘制网格
		drawGrid();
		// 绘制每个名字对应的折线
		for (int i = 0; i < Entry.size(); i++) {
			if (Entry.get(i) != null) {
				showEntry(Entry.get(i), color[i % 4]);
			}
		}
	}

	public void drawGrid() {
		// 绘制竖线
		for (int i = 0; i < NDECADES; i++) {
			GLine line = new GLine((getWidth() / NDECADES) * i, 0, (getWidth() / NDECADES) * i, getHeight());
			add(line);
			GLabel label = new GLabel(START_DECADE + DECADES * i + "");
			label.setLocation((getWidth() / NDECADES) * i + DECADES_BETWEEN_LINE,
					getHeight() - GRAPH_MARGIN_SIZE + label.getAscent());
			add(label);
		}
		// 绘制上方的横线
		GLine upline = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		add(upline);
		// 绘制下方的横线
		GLine downline = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		add(downline);
	}

	// 在画面中显示折线
	public void showEntry(NameSurferEntry entry, Color color) {
		// 定义一个变量表示每隔10年
		int decade = START_DECADE;
		// 折线开始的x,y坐标
		int startX = 0;
		double startY = 0;
		// 折线结束的x,y坐标
		int endX = 0;
		double endY = 0;
		for (int i = 0; i < NDECADES - 1; i++) {
			startX = (getWidth() / NDECADES) * i;
			startY = (double) (getHeight() - GRAPH_MARGIN_SIZE * 2) / MAX_RANK * entry.getRank(decade)
					+ GRAPH_MARGIN_SIZE;
			endX = (getWidth() / NDECADES) * (i + 1);
			endY = (double) (getHeight() - GRAPH_MARGIN_SIZE * 2) / MAX_RANK * entry.getRank(decade + DECADES)
					+ GRAPH_MARGIN_SIZE;
			GLabel label = new GLabel(entry.getName() + " " + entry.getRank(decade));
			if (entry.getRank(decade) == 0) {
				startY = getHeight() - GRAPH_MARGIN_SIZE;
				label.setLabel(entry.getName() + " *");
			}
			if (entry.getRank(decade + DECADES) == 0) {
				endY = getHeight() - GRAPH_MARGIN_SIZE;
			}
			GLine line = new GLine(startX, startY, endX, endY);
			line.setColor(color);
			add(line);
			label.setColor(color);
			label.setLocation(startX + DECADES_BETWEEN_LINE, startY);
			add(label);
			decade += DECADES;
		}
		// 最后一年的名字和排名
		GLabel lastlabel = new GLabel(entry.getName() + " " + entry.getRank(decade));
		if (entry.getRank(decade) == 0) {
			lastlabel.setLabel(entry.getName() + " *");
		}
		lastlabel.setColor(color);
		lastlabel.setLocation(endX, endY);
		add(lastlabel);
	}

	// 清除所有的折线
	public void clearAll() {
		// You fill this in //
		Entry.clear();
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}
}
