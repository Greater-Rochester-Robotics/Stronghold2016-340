package org.usfirst.frc.team340.robot.commands.overrides;

import edu.wpi.first.wpilibj.command.Command;

import java.util.logging.Logger;

import org.usfirst.frc.team340.robot.Robot;
/**
 *
 */
public class MO_ClutchOn extends Command {
	
	Logger logger = Robot.getLogger(MO_ClutchOn.class);
	
	/**
	 * Set requirements for clutch operation
	 * Requires driver subsystem
	 * Engages the clutch that drives the climbing arm mechanism
	 */
    public MO_ClutchOn() {
    	
    	requires(Robot.drive);
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("[Initializing: MO_ClutchOn]");
    }

    // Called repeatedly when this Command is scheduled to run
    /**
     * Engages the clutch
     */
    protected void execute() {
    	Robot.drive.engagePTO();
    }

    // Make this return true when this Command no longer needs to run execute()
    /**
     * Sets command to completed
     * @return boolean true
     */
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
