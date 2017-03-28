package org.usfirst.frc.team6571.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

//a223
//

public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	Timer timer = new Timer();
	
	Victor FLmotor = new Victor(0); // Front left drive motor
	Victor BLmotor = new Victor(1); // Back left drive motor
	Victor FRmotor = new Victor(2); // Front right drive motor
	Victor BRmotor = new Victor(3); // Back right drive motor
	
	Victor motor = new Victor(4);
	
	Joystick joy0 = new Joystick(0);
	
	RobotDrive maindrive = new RobotDrive(FRmotor, BRmotor, FLmotor, BLmotor);
	
	DigitalInput limitup = new DigitalInput(0);
	DigitalInput limitdown = new DigitalInput(1);
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		SmartDashboard.putBoolean("limitup", limitup.get());
		SmartDashboard.putBoolean("limitdown", limitdown.get());
		SmartDashboard.putNumber("motor", motor.get());
		
		timer.reset();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
	    	while(isAutonomous() && isEnabled() && timer.get() <6.0) {
	    		maindrive.arcadeDrive(-.7, 0);
	    		
	    		SmartDashboard.putNumber("Timer in auton", timer.get());
	    	}
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
    	while (isOperatorControl() && isEnabled()) {   
    	
    		maindrive.arcadeDrive(joy0.getRawAxis(1), -joy0.getRawAxis(0));
    		
    		SmartDashboard.putBoolean("limitup", limitup.get());
    		SmartDashboard.putBoolean("limitdown", limitdown.get());
    		SmartDashboard.putNumber("motor", motor.get());
    		SmartDashboard.putNumber("bl", BLmotor.get())
    		;
    		SmartDashboard.putString("jjdfbnd", "jdbhosgn");
    		
    		if(joy0.getRawButton(1) && limitup.get() == true) {
    			motor.set(.5);
    		} else if (joy0.getRawButton(2) && limitdown.get() == true) {
    			motor.set(-.5);
    		} else {
    			motor.set(0);
    		}
    	
    	}
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}