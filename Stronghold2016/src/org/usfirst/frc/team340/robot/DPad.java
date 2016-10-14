package org.usfirst.frc.team340.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class DPad extends Button {
	private Joystick joystick;
	private int direction;
	
	/**
	 * Class for dealing with the D-Pad
	 * aka PoV hat
	 * @param joy the joystick
	 * @param direction angle the hat
	 * must be equal to
	 */
	public DPad(Joystick joy, int direction) {
		this.joystick = joy;
		this.direction = direction;
	}
	
	public DPad(Joystick joy, Direction dir) {
		this.joystick = joy;
		this.direction = dir.direction;
	}
	/**
	 * Gets whether or not the hat's angle
	 * is equal to the given angle
	 * @return true if the PoV hat is
	 * equal to the given angle, false
	 * otherwise
	 */
	public boolean get() {
		return joystick.getPOV() == direction;		
	}
	
	public enum Direction {
		dPadRight(90),
		dPadUpRight(45),
		dPadUp(0),
		dPadUpLeft(315),
		dPadLeft(270),
		dPadDownLeft(225),
		dPadDown(180),
		dPadDownRight(135);
		
		public final int direction;
		
		private Direction(int direction) {
			this.direction = direction;
		}
	}
}