package oseam.panels;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

import oseam.Messages;

import java.awt.Cursor;
import java.awt.event.ActionListener;

public class PanelPort extends JPanel {

	private ButtonGroup regionButtons = null;
	private JRadioButton regionAButton = null;
	private JRadioButton regionBButton = null;
	private ButtonGroup shapeButtons = null;
	private JRadioButton pillarButton = null;
	private JRadioButton sparButton = null;
	private JRadioButton canButton = null;
	private JRadioButton floatButton = null;
	private JRadioButton beaconButton = null;
	private JRadioButton towerButton = null;
	private JRadioButton perchButton = null;

	public PanelPort() {
		super();
		initialize();
	}

	private void initialize() {
		this.setLayout(null);
		this.add(getRegionAButton(), null);
		this.add(getRegionBButton(), null);
		this.add(getPillarButton(), null);
		this.add(getSparButton(), null);
		this.add(getCanButton(), null);
		this.add(getFloatButton(), null);
		this.add(getBeaconButton(), null);
		this.add(getTowerButton(), null);
		this.add(getPerchButton(), null);

		regionButtons = new ButtonGroup();
		regionButtons.add(regionAButton);
		regionButtons.add(regionBButton);
		ActionListener alRegion = new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (regionAButton.isSelected()) {
					regionAButton.setEnabled(false);
				} else {
					regionAButton.setEnabled(true);
				}
				if (regionBButton.isSelected()) {
					regionBButton.setEnabled(false);
				} else {
					regionBButton.setEnabled(true);
				}
			}
		};
		regionAButton.addActionListener(alRegion);
		regionBButton.addActionListener(alRegion);

		shapeButtons = new ButtonGroup();
		shapeButtons.add(pillarButton);
		shapeButtons.add(sparButton);
		shapeButtons.add(canButton);
		shapeButtons.add(floatButton);
		shapeButtons.add(beaconButton);
		shapeButtons.add(towerButton);
		shapeButtons.add(perchButton);
		ActionListener alShape = new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				pillarButton.setEnabled(!pillarButton.isSelected());
				sparButton.setEnabled(!sparButton.isSelected());
				canButton.setEnabled(!canButton.isSelected());
				floatButton.setEnabled(!floatButton.isSelected());
				beaconButton.setEnabled(!beaconButton.isSelected());
				towerButton.setEnabled(!towerButton.isSelected());
				perchButton.setEnabled(!perchButton.isSelected());
			}
		};
		pillarButton.addActionListener(alShape);
		sparButton.addActionListener(alShape);
		canButton.addActionListener(alShape);
		floatButton.addActionListener(alShape);
		beaconButton.addActionListener(alShape);
		towerButton.addActionListener(alShape);
		perchButton.addActionListener(alShape);
	}

	private JRadioButton getRegionAButton() {
		if (regionAButton == null) {
			regionAButton = new JRadioButton(new ImageIcon(getClass()
					.getResource("/images/RegionAButton.png")));
			regionAButton.setBounds(new Rectangle(0, 0, 35, 30));
			regionAButton.setToolTipText(Messages.getString("RegionATip"));
		}
		return regionAButton;
	}

	private JRadioButton getRegionBButton() {
		if (regionBButton == null) {
			regionBButton = new JRadioButton(new ImageIcon(getClass()
					.getResource("/images/RegionBButton.png")));
			regionBButton.setBounds(new Rectangle(0, 30, 35, 30));
			regionBButton.setToolTipText(Messages.getString("RegionBTip"));
		}
		return regionBButton;
	}

	private JRadioButton getPillarButton() {
		if (pillarButton == null) {
			pillarButton = new JRadioButton(new ImageIcon(getClass()
					.getResource("/images/PillarButton.png")));
			pillarButton.setBounds(new Rectangle(0, 64, 35, 32));
			pillarButton.setToolTipText(Messages.getString("PillarTip"));
		}
		return pillarButton;
	}

	private JRadioButton getSparButton() {
		if (sparButton == null) {
			sparButton = new JRadioButton(new ImageIcon(getClass().getResource(
					"/images/SparButton.png")));
			sparButton.setBounds(new Rectangle(0, 96, 35, 32));
			sparButton.setToolTipText(Messages.getString("SparTip"));
		}
		return sparButton;
	}

	private JRadioButton getCanButton() {
		if (canButton == null) {
			canButton = new JRadioButton(new ImageIcon(getClass().getResource(
					"/images/CanButton.png")));
			canButton.setBounds(new Rectangle(0, 128, 35, 32));
			canButton.setToolTipText(Messages.getString("CanTip"));
		}
		return canButton;
	}

	private JRadioButton getFloatButton() {
		if (floatButton == null) {
			floatButton = new JRadioButton(new ImageIcon(getClass()
					.getResource("/images/FloatButton.png")));
			floatButton.setBounds(new Rectangle(35, 0, 35, 32));
			floatButton.setToolTipText(Messages.getString("FloatTip"));
		}
		return floatButton;
	}

	private JRadioButton getBeaconButton() {
		if (beaconButton == null) {
			beaconButton = new JRadioButton(new ImageIcon(getClass()
					.getResource("/images/BeaconButton.png")));
			beaconButton.setBounds(new Rectangle(35, 32, 35, 32));
			beaconButton.setToolTipText(Messages.getString("BeaconTip"));
		}
		return beaconButton;
	}

	private JRadioButton getTowerButton() {
		if (towerButton == null) {
			towerButton = new JRadioButton(new ImageIcon(getClass()
					.getResource("/images/TowerButton.png")));
			towerButton.setBounds(new Rectangle(35, 64, 35, 32));
			towerButton.setToolTipText(Messages.getString("TowerTip"));
		}
		return towerButton;
	}

	private JRadioButton getPerchButton() {
		if (perchButton == null) {
			perchButton = new JRadioButton(new ImageIcon(getClass()
					.getResource("/images/PerchPButton.png")));
			perchButton.setBounds(new Rectangle(35, 96, 35, 32));
			perchButton.setToolTipText(Messages.getString("PerchTip"));
		}
		return perchButton;
	}

}