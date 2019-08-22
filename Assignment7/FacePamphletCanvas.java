
/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Iterator;

import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants, ComponentListener {

	private GLabel namelabel;
	private FacePamphletProfile thisProfile;
	private String thismessage;

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
		// You fill this in
		// 添加监听
		addComponentListener(this);
	}

	/**
	 * This method displays a message string near the bottom of the canvas. Every
	 * time this method is called, the previously displayed message (if any) is
	 * replaced by the new message text passed in.
	 */
	// 显示提示信息
	public void showMessage(String msg) {
		// You fill this in
		thismessage = msg;
		if (msg != null) {
			GLabel messagelabel = new GLabel(msg);
			messagelabel.setFont(MESSAGE_FONT);
			messagelabel.setLocation(getWidth() / 2 - messagelabel.getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
			add(messagelabel);
		}

	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the bottom
	 * of the screen) and then the given profile is displayed. The profile display
	 * includes the name of the user from the profile, the corresponding image (or
	 * an indication that an image does not exist), the status of the user, and a
	 * list of the user's friends in the social network.
	 */
	// 绘制整个画面
	public void displayProfile(FacePamphletProfile profile) {
		thisProfile = profile;
		// You fill this in
		removeAll();
		if (profile != null) {
			addName(profile);
			addImage(profile);
			addFriend(profile);
			addStatus(profile);
		}
		showMessage(thismessage);
	}

	// 添加名字时绘制
	private void addName(FacePamphletProfile profile) {
		namelabel = new GLabel(profile.getName());
		namelabel.setFont(PROFILE_NAME_FONT);
		namelabel.setLocation(LEFT_MARGIN, TOP_MARGIN + namelabel.getHeight());
		namelabel.setColor(Color.BLUE);
		add(namelabel);
	}

	// 改变图片时绘制
	private void addImage(FacePamphletProfile profile) {
		if (profile.getImage().equals("")) {
			GRect rect = new GRect(LEFT_MARGIN, LEFT_MARGIN + namelabel.getHeight() + IMAGE_MARGIN, IMAGE_WIDTH,
					IMAGE_HEIGHT);
			add(rect);
			GLabel imagelabel = new GLabel("No Image");
			imagelabel.setFont(PROFILE_IMAGE_FONT);
			imagelabel.setLocation(LEFT_MARGIN + IMAGE_WIDTH / 2 - imagelabel.getWidth() / 2, LEFT_MARGIN
					+ namelabel.getHeight() + IMAGE_MARGIN + IMAGE_HEIGHT / 2 + imagelabel.getDescent() / 2);
			add(imagelabel);
		} else {
			GImage image = new GImage(profile.getImage());
			image.scale((double) IMAGE_WIDTH / image.getWidth(), (double) IMAGE_HEIGHT / image.getHeight());
			add(image, LEFT_MARGIN, LEFT_MARGIN + namelabel.getHeight() + IMAGE_MARGIN);
		}
	}

	// 改变朋友时绘制
	private void addFriend(FacePamphletProfile profile) {
		GLabel friendlabel = new GLabel("Friends:");
		friendlabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		friendlabel.setLocation(getWidth() / 2, LEFT_MARGIN + namelabel.getHeight() + IMAGE_MARGIN);
		add(friendlabel);
		Iterator<String> iter = profile.getFriends();
		int i = 1;
		while (iter.hasNext()) {
			String myfriend = iter.next();
			if (FacePamphlet.facePamphletDatabase.containsProfile(myfriend)) {
				GLabel myfriendlabel = new GLabel(myfriend);
				myfriendlabel.setFont(PROFILE_FRIEND_FONT);
				myfriendlabel.setLocation(getWidth() / 2,
						LEFT_MARGIN + namelabel.getHeight() + IMAGE_MARGIN + myfriendlabel.getHeight() * i);
				add(myfriendlabel);
			}
			i++;
		}
	}

	// 改变状态是绘制
	private void addStatus(FacePamphletProfile profile) {
		if (profile.getStatus().equals("")) {
			GLabel nostatuslabel = new GLabel("No current status");
			nostatuslabel.setFont(PROFILE_STATUS_FONT);
			nostatuslabel.setLocation(LEFT_MARGIN, TOP_MARGIN + namelabel.getHeight() + IMAGE_MARGIN + IMAGE_HEIGHT
					+ STATUS_MARGIN + nostatuslabel.getHeight());
			add(nostatuslabel);
		} else {
			GLabel statuslabel = new GLabel(profile.getName() + " is " + profile.getStatus());
			statuslabel.setFont(PROFILE_STATUS_FONT);
			statuslabel.setLocation(LEFT_MARGIN, TOP_MARGIN + namelabel.getHeight() + IMAGE_MARGIN + IMAGE_HEIGHT
					+ STATUS_MARGIN + statuslabel.getHeight());
			add(statuslabel);
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		displayProfile(thisProfile);
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

}
