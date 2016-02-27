package org.usfirst.frc.team340.robot.commands.auto;

import org.usfirst.frc.team340.robot.commands.ArmToMax;
import org.usfirst.frc.team340.robot.commands.DriveSpin;
import org.usfirst.frc.team340.robot.commands.DriveTime;
import org.usfirst.frc.team340.robot.commands.Shoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CG_SpyBot extends CommandGroup {
    
    public  CG_SpyBot() {
    	addSequential(new ArmToMax(), 4);
    	addSequential(new Shoot(), 4);
    	addSequential(new DriveSpin(0.5), 1);
    	
    	// uncomment these when driveSpin has been tested
    	
//    	addSequential(new DriveTime(2, 0.8));
//    	addSequential(new DriveSpin(-0.5), 2);
//    	addSequential(new DriveTime(1, 0.8));
    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
