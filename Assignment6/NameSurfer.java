
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import acm.program.Program;

public class NameSurfer extends Program implements NameSurferConstants {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	JTextField textField = new JTextField(TEXTFIELD_LENGTH);
	private NameSurferGraph graph;

	public void init() {
		// You fill this in, along with any helper methods
		// 绘制网格
		graph = new NameSurferGraph();
		add(graph);
		// 添加文本
		JLabel label = new JLabel("Name");
		add(label, SOUTH);
		// 添加输入框
		add(textField, SOUTH);
		// 添加画图按钮
		JButton GraphButton = new JButton("Graph");
		add(GraphButton, SOUTH);
		// 添加删除按钮
		JButton ClearButton = new JButton("Clear");
		add(ClearButton, SOUTH);
		// 添加删除全部按钮
		JButton ClearAllButton = new JButton("ClearAll");
		add(ClearAllButton, SOUTH);
		// 添加监听
		addActionListeners();
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		// You fill this in //
		// 获取监听事件
		String cmd = e.getActionCommand();
		if (cmd.equals("Graph")) {
			NameSurferDataBase nameSurferDataBase;
			try {
				// 读文件
				nameSurferDataBase = new NameSurferDataBase(NAMES_DATA_FILE);
				// 找对应的名字以及排名
				NameSurferEntry enty = nameSurferDataBase.findEntry(textField.getText().toString());
				if (enty != null) {
					// 添加该名字名字及排名到数组表中
					graph.addEntry(enty);
					// 更新显示的画面
					graph.update();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			textField.setText("");
		} else if (cmd.equals("Clear")) {
			// 删除某一个指定的名字的折线
			graph.clear(textField.getText().toString());
			graph.update();
			textField.setText("");
		} else if (cmd.equals("ClearAll")) {
			// 删除所有名字的折线
			graph.clearAll();
			graph.update();
		}
	}
}
