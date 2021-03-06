package org.usfirst.frc.team3145.robot;

import com.kauailabs.navx.frc.AHRS;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RobotTest extends Command {
	static TalonSRX _encoderTalon = new TalonSRX(1);
	static TalonSRX _talon2 = new TalonSRX(2);
	static TalonSRX _talon3 = new TalonSRX(3);
	public int maxValue = 20000; // max value refers to the complete revolution of the talon srx
	public int setEncoder = 0;
	public AHRS ahrs;

	public RobotTest() {
		_encoderTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// reset CURRENT position to 0
		_encoderTalon.setSelectedSensorPosition(0, 0, 0);
		setEncoder = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		try {
			  // TODO: FIGURE OUT SERIAL PORT
	          /* Communicate w/navX-MXP via the MXP SPI Bus.                                     */
	          /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
	          /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
	          ahrs = new AHRS(SPI.Port.kMXP); 
	      } catch (RuntimeException ex ) {
	          DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
	      }
	  }

		Joystick testJoystick = new Joystick(0);
		double joystickInfo = testJoystick.getRawAxis(1) * -1;
		double encoderInfo = _encoderTalon.getSelectedSensorPosition(0);
		
		if (joystickInfo < -0.1) {
			setEncoder +=50 * joystickInfo; // TODO: Make speed proportional to joystick intensity by multiplying by joystick value. 
		} 
		
		if (joystickInfo > 0.1){
			setEncoder +=50 * joystickInfo;
	}

//		_encoderTalon.set(ControlMode.MotionMagic, 0);
		_encoderTalon.set(ControlMode.MotionMagic, setEncoder);
		System.out.println("Encoder pos: " + encoderInfo);
		System.out.println("Joystick pos: " + joystickInfo);

		// if (_encoderTalon.getSelectedSensorPosition(0) > maxValue) {
		// // set pos to 0 manually
		// _encoderTalon.setSelectedSensorPosition(0, 0, 0);
		// }
	}

	// Make this return true when this Command no longer needs to run execute()
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
