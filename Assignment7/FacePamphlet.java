
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import acm.graphics.GImage;
import acm.program.Program;
import acm.util.ErrorException;

public class FacePamphlet extends Program implements FacePamphletConstants {
	// 定义四个长度为TEXT_FIELD_SIZE的输入框
	JTextField nameTextField = new JTextField(TEXT_FIELD_SIZE);
	JTextField statusTextField = new JTextField(TEXT_FIELD_SIZE);
	JTextField pictureTextField = new JTextField(TEXT_FIELD_SIZE);
	JTextField friendTextField = new JTextField(TEXT_FIELD_SIZE);
	// 定义几个类的对象
	public static FacePamphletDatabase facePamphletDatabase = new FacePamphletDatabase();
	private FacePamphletProfile Profile;
	private FacePamphletCanvas canvas;

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
		// You fill this in
		canvas = new FacePamphletCanvas();
		add(canvas);
		// 添加文本
		JLabel label = new JLabel("Name");
		add(label, NORTH);
		// 添加输入框
		add(nameTextField, NORTH);
		// 添加add按钮
		JButton addButton = new JButton("Add");
		add(addButton, NORTH);
		// 添加delete按钮
		JButton deleteButton = new JButton("Delete");
		add(deleteButton, NORTH);
		// 添加lookup按钮
		JButton lookupButton = new JButton("Lookup");
		add(lookupButton, NORTH);
		// 添加按钮
		add(statusTextField, WEST);
		JButton statusButton = new JButton("Change Status");
		add(statusButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(pictureTextField, WEST);
		JButton pictureButton = new JButton("Change Picture");
		add(pictureButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(friendTextField, WEST);
		JButton friendButton = new JButton("Add Friend");
		add(friendButton, WEST);
		// 为输入框添加监听
		statusTextField.addActionListener(this);
		pictureTextField.addActionListener(this);
		friendTextField.addActionListener(this);
		// 添加监听
		addActionListeners();
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	// 监听事件
	public void actionPerformed(ActionEvent e) {
		// You fill this in as well as add any additional methods
		// 当点击按钮 或者按下enter键时相应
		if (e.getSource() == statusTextField || e.getActionCommand().equals("Change Status")) {
			changeStatus();
		} else if (e.getSource() == pictureTextField || e.getActionCommand().equals("Change Picture")) {
			changePicture();
		} else if (e.getSource() == friendTextField || e.getActionCommand().equals("Add Friend")) {
			addFriend();
		} else if (e.getActionCommand().equals("Add")) {
			addName();
		} else if (e.getActionCommand().equals("Delete")) {
			deleteName();
		} else if (e.getActionCommand().equals("Lookup")) {
			lookUpName();
		}
	}

	// 添加一个名字
	private void addName() {
		String name = nameTextField.getText().toString();
		// 判断输入的合法性
		if (!name.matches("^[A-Za-z]+$")) {
			canvas.showMessage("The Input Must Be Letter!!");
			return;
		}
		// 查看该社交网络中是否已经存在这个名字
		if (facePamphletDatabase.containsProfile(name)) {
			canvas.showMessage("The Name " + name + " Exited!!");
		} else {
			Profile = new FacePamphletProfile(name);
			facePamphletDatabase.addProfile(Profile);
			canvas.showMessage("Add The Name " + name + " Succees!!");
		}
		// nameTextField.setText("");
		// 重绘画面
		canvas.displayProfile(Profile);
	}

	// 删除一个名字
	private void deleteName() {
		String name = nameTextField.getText().toString();
		if (!name.matches("^[A-Za-z]+$")) {
			canvas.showMessage("The Input Must Be Letter!!");
			return;
		}
		if (facePamphletDatabase.containsProfile(name)) {
			facePamphletDatabase.deleteProfile(name);
			Profile = new FacePamphletProfile(name);
			if (Profile.removeFriend(name)) {
				canvas.showMessage("The Name " + Profile.getName() + " Delete Succees!!");
			}
		} else {
			canvas.showMessage("The Name " + name + " Is Not Exit To Delete!!");
		}
		// nameTextField.setText("");
		canvas.displayProfile(Profile);
	}

	// 查找一个名字
	private void lookUpName() {
		String name = nameTextField.getText().toString();
		if (!name.matches("^[A-Za-z]+$")) {
			canvas.showMessage("The Input Must Be Letter!!");
			return;
		}
		if (facePamphletDatabase.containsProfile(name)) {
			Profile = facePamphletDatabase.getProfile(name);
			canvas.showMessage("The Name " + Profile.getName() + " LookUp Succees!!");
		} else {
			canvas.showMessage("The Name " + name + " Is Not Exit To LookUp!!");
		}
		// nameTextField.setText("");
		canvas.displayProfile(Profile);
	}

	// 改变状态
	private void changeStatus() {
		String name = nameTextField.getText().toString();
		String status = statusTextField.getText().toString();
		if (!name.matches("^[A-Za-z]+$") || !status.matches("^[A-Za-z]+$")) {
			canvas.showMessage("The Input Must Be Letter!!");
			canvas.displayProfile(Profile);
			return;
		}
		if (facePamphletDatabase.containsProfile(name)) {
			Profile = facePamphletDatabase.getProfile(name);
			Profile.setStatus(statusTextField.getText().toString());
			canvas.showMessage("The Name " + name + " Change Status Succees!!");
		} else {
			canvas.showMessage("The Name " + name + " Is Not Exit To Change Status!!");
		}
		// statusTextField.setText("");
		canvas.displayProfile(Profile);
	}

	// 改变图片
	private void changePicture() {
		String name = nameTextField.getText().toString();
		String picturename = pictureTextField.getText().toString();
		if (!name.matches("^[A-Za-z]+$")) {
			canvas.showMessage("The Input Must Be Letter!!");
			return;
		}
		GImage image = null;
		// 判断图片是否存在
		try {
			if (!picturename.equals("")) {
				image = new GImage(picturename);
			} else {
				canvas.showMessage("The Picture " + picturename + " Is Not In The File!!");
			}
		} catch (ErrorException ex) {
			canvas.showMessage("The Picture " + picturename + " Is Not In The File!!");
		}
		if (image != null) {
			if (facePamphletDatabase.containsProfile(name)) {
				Profile = facePamphletDatabase.getProfile(name);
				Profile.setImage(picturename);
				canvas.showMessage("The Name " + name + " Change Picture Succees!!");
			} else {
				canvas.showMessage("The Name " + name + " Is Not Exit To Change Picture!!");
			}
		}
		// pictureTextField.setText("");
		canvas.displayProfile(Profile);
	}

	// 添加朋友
	private void addFriend() {
		String name = nameTextField.getText().toString();
		String friendname = friendTextField.getText().toString();
		if (!name.matches("^[A-Za-z]+$") || !friendname.matches("^[A-Za-z]+$")) {
			canvas.showMessage("The Input Must Be Letter!!");
			canvas.displayProfile(Profile);
			return;
		}
		if (facePamphletDatabase.containsProfile(name)) {
			if (facePamphletDatabase.containsProfile(friendname)) {
				addEachFriend(name, friendname);
			} else {
				canvas.showMessage("The Friend " + friendname + " Is Not Exit To Add Friend!!");
			}
		} else {
			canvas.showMessage("The Name " + name + " Is Not Exit To Add Friend!!");
		}
		// friendTextField.setText("");
		canvas.displayProfile(Profile);
	}

	// 互相添加朋友
	private void addEachFriend(String name, String friendname) {
		Profile = facePamphletDatabase.getProfile(friendname);
		if (Profile.addFriend(name)) {
			canvas.showMessage(friendname + " Add Each Friend Succee!!");
		} else {
			canvas.showMessage("The Friend " + name + " Is Exit!!");
		}
		Profile = facePamphletDatabase.getProfile(name);
		if (Profile.addFriend(friendname)) {
			canvas.showMessage("The Name " + name + " Add Friend " + friendname + " Succees!!");

		} else {
			canvas.showMessage("The Friend " + friendname + " Is Exit To Add Friend!!");
		}
	}

}
