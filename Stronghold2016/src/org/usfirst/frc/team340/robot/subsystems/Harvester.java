package org.usfirst.frc.team340.robot.subsystems;

import org.usfirst.frc.team340.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * this is a dumb name because this mechanism will also shoot
 * HarvestShooter?
 * HaversterAndShooter?
 * 
 * todo: robotmap this and write commands
 * todo: modify as design changes (updated as of 1/25/16)
 */
public class Harvester extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	// Roller farthest from the robot, it is the shooter
	
	public static final double SHOOTER_SHOOT_V_BUS = -1.0;
	public static final double SHOOTER_HARVEST_V_BUS = 0.35;
	public static final double SHOOTER_DISCHARGE_BALL_V_BUS = -0.6;
	public static final double HARVESTER_RELEASE_BALL_V_BUS = .25;
	public static final double HARVESTER_DISCHARGE_BALL_V_BUS = 0.25;
	public static final int HARVESTER_CONTROL_STALL_CURRENT = 42;
	public static final double HARVESTER_HARVEST_V_BUS = -0.2;
	
	private CANTalon shooterWheelA;
	private CANTalon shooterWheelB;
	// Roller closest to the robot
	private CANTalon harvesterBallControl;
	
	// not sure what type of motor this is gonna be
	private CANTalon tiltLeft;
	private CANTalon tiltRight;
	
	//limit switches
	private DigitalInput limitLeft;
	private DigitalInput limitRight;
	
	private DigitalInput ballSensorLeft;
	private DigitalInput ballSensorRight;
	
	public class ZeroablePotentiometer extends AnalogPotentiometer {
		
		private double offset = 0.0;
		private int invert = 1;
		public ZeroablePotentiometer(int channel) {
			super(channel);
		}
		
		public ZeroablePotentiometer(int channel, double scale) {
			super(channel, scale);
		}
		
		public ZeroablePotentiometer(int channel, double scale, double offset) {	
			super(channel, scale);
			this.offset = offset;
		}
		
		public double get() {
			return (super.get() - offset) * invert;
		}
		
		private boolean hasReset = false;
		
		public void reset() {
			offset = super.get();
			hasReset = true;
		}
		public boolean isReset() {
			return hasReset;
		}
		
		public void setInverted(boolean invert){
			if(invert){
				this.invert = -1;
			}else{
				this.invert = 1;
			}
		}
		
		public boolean isInverted(){
			return this.invert == -1;
		}
	}   
	
	//potentiometers
	private ZeroablePotentiometer leftPot;
	private ZeroablePotentiometer rightPot;
	
	public Harvester() {		
		shooterWheelA = new CANTalon(RobotMap.HarvesterShooterWheelA);//Construct shooter A as CANTalon, this should have encoder into it
		shooterWheelB = new CANTalon(RobotMap.HarvesterShooterWheelB);//Construct shooter B as CANTalon
		shooterWheelB.changeControlMode(CANTalon.TalonControlMode.Follower);//turn shooter motor B to a slave
		shooterWheelB.set(shooterWheelA.get());//slave shooter motor B to shooter motor A
		
		harvesterBallControl = new CANTalon(RobotMap.HarvesterBallControl);
		
		//TODO: sync left/right motors
		tiltRight = new CANTalon(RobotMap.HarvesterAimingMotorRight);
		tiltLeft = new CANTalon(RobotMap.HarvesterAimingMotorLeft);
		
		//tiltLeft.setVoltageRampRate(5); // this might be a good way to solve our ramp rate issue ie smooth out the jerkieness
		//tiltRight.setVoltageRampRate(5); // this might be a good way to solve our ramp rate issue ie smooth out the jerkieness
		
		limitLeft = new DigitalInput(RobotMap.HarvesterLeftBump);
		limitRight = new DigitalInput(RobotMap.HarvesterRightBump);
		
		leftPot = new ZeroablePotentiometer(RobotMap.LeftAimPot, 250);
		rightPot = new ZeroablePotentiometer(RobotMap.RightAimPot, 250);
		rightPot.setInverted(true);
		
		ballSensorLeft = new DigitalInput(RobotMap.BallSensorLeftPort);
		ballSensorRight = new DigitalInput(RobotMap.BallSensorRightPort);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /**
     * Drives the roller farthest from the robot
     * @param value speed
     */
    public void setShooter(double value) {
    	shooterWheelA.set(value);
    }
   
    /**
     * all methods begin by disabling the wheels. changes controlling mode to voltage %,
     * absolute voltage (voltage compensation), and encoder speed, respectively. latter also reenables
     */
    public void setPercentVolt() {
    	shooterWheelA.disable();
    	shooterWheelA.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    }
    
    /**
     * Set the shooter to absolute voltage mode. In this mode all set values 
     * should be in volts from 12V to -12V. This will compensate for voltage 
     * variation from the battery over the course of the match. Closed loop 
     * control will be disabled before mode shifting
     */
    public void setAbsVolt() {
    	shooterWheelA.disable();
    	shooterWheelA.changeControlMode(CANTalon.TalonControlMode.Voltage);
    }
    
    /**
     * Set the shooter to closed loop encoder control. Does not enable the closed loop.
     */
    public void setEncSpd() {
    	shooterWheelA.disable();
    	shooterWheelA.changeControlMode(CANTalon.TalonControlMode.Speed);
    }
    
    public void enableClosedLoop(){
    	shooterWheelA.enableControl();
    }
    
    public void disableClosedLoop(){
    	shooterWheelA.disable();
    }
    
    /**
     * Drives the roller closest to the robot
     * at a specific speed.
     * @param speed
     */
    public void setBallControl(double speed) {
    	harvesterBallControl.set(speed);
    }
    
//    /**
//     * Drives the motor that actuates the harvester/shooter
//     * @param speed
//     */
//    public void setTiltSpeed(double speed) {
//    	tiltLeft.set(-speed);
//    	tiltRight.set(speed);
//    }
    
    /**
     * Access right limit switch state
     * @return boolean limit switch state
     */
    public boolean getRightLimit() {
    	return !limitRight.get();
    }
    
    /**
     * Access left limit switch state
     * @return boolean limit switch state
     */
    public boolean getLeftLimit() {
    	return !limitLeft.get();
    }
    
    public double getLeftAimPot() {
    	return leftPot.get();
    }
    
    public double getRightAimPot() {
    	return rightPot.get();
    }
    
    public void resetLeftPot() {
    	leftPot.reset();
    }
    public void resetRightPot() {
    	rightPot.reset();
    }
    
    public void resetBothPots() {
    	resetLeftPot();
    	resetRightPot();
    }
    
    public boolean hasReset() {
    	return leftPot.isReset() && rightPot.isReset();
    }
    
    public void setLeftTilt(double speed) {
    	tiltLeft.set(-speed);
    }
    
    public void setRightTilt(double speed) {
    	tiltRight.set(speed);
    }
    
    public void setTilt(double speed) {
    	this.setRightTilt(speed);
    	this.setLeftTilt(speed);
    }
    
    public double getControlCurrent() {
    	return harvesterBallControl.getOutputCurrent();
    }
    
    public boolean hasBall() {
    	return !ballSensorLeft.get() && !ballSensorRight.get();
    }
}
