/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.RobotMap;
import frc.robot.commands.Drive;
import frc.robot.swerve.SwerveModule;
import frc.robot.swerve.SwerveDrive;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  // Create the Drive Motor and Steer Motor Objects //  
  private final WPI_TalonSRX frontRightDriveMotor = new WPI_TalonSRX(RobotMap.FRONT_RIGHT_DRIVE_TalonSRX_CAN_ID);
  // private final WPI_TalonSRX frontLeftDriveMotor = new WPI_TalonSRX(RobotMap.FRONT_LEFT_DRIVE_TalonSRX_CAN_ID);
  // private final WPI_TalonSRX rearLeftDriveMotor = new WPI_TalonSRX(RobotMap.REAR_LEFT_DRIVE_TalonSRX_CAN_ID);
  // private final WPI_TalonSRX rearRightDriveMotor = new WPI_TalonSRX(RobotMap.REAR_RIGHT_DRIVE_TalonSRX_CAN_ID);

  private final WPI_TalonSRX frontRightSteerMotor = new WPI_TalonSRX(RobotMap.FRONT_RIGHT_STEER_TalonSRX_CAN_ID);
  // private final WPI_TalonSRX frontLeftSteerMotor = new WPI_TalonSRX(RobotMap.FRONT_LEFT_STEER_TalonSRX_CAN_ID);
  // private final WPI_TalonSRX rearLeftSteerMotor = new WPI_TalonSRX(RobotMap.REAR_LEFT_STEER_TalonSRX_CAN_ID);
  // private final WPI_TalonSRX rearRightSteerMotor = new WPI_TalonSRX(RobotMap.REAR_RIGHT_STEER_TalonSRX_CAN_ID);
  
  private SwerveModule frontRightWheel;
  private SwerveModule frontLeftWheel;
  private SwerveModule rearLeftWheel;
  private SwerveModule rearRightWheel;

  private SwerveDrive m_SwerveDrive;

  public Drivetrain(){
    //Create the Swerve Drive Modules for each wheel//
    frontRightWheel = new SwerveModule("FrontRightWheel", frontRightDriveMotor, frontRightSteerMotor);
    // frontLeftWheel = new SwerveModule("FrontLeftWheel", frontLeftDriveMotor, frontLeftSteerMotor);
    // rearLeftWheel = new SwerveModule("RearLeftWheel", rearLeftDriveMotor, rearLeftSteerMotor);
    // rearRightWheel = new SwerveModule("RearRightWheel", rearRightDriveMotor, rearRightSteerMotor);

    //Now Build the complete Swerve Drive Object with all four Wheel Modules//
    // m_SwerveDrive = new SwerveDrive(frontRightWheel, frontLeftWheel, rearLeftWheel, rearRightWheel);
    m_SwerveDrive = new SwerveDrive(frontRightWheel, frontRightWheel, frontRightWheel, frontRightWheel);
  }

  public void init(){

  }
  private void initDriveMotor(WPI_TalonSRX driveMotor){

  }

  private void initSteerMotor(WPI_TalonSRX steerMotor){

  }

  public void move(double fwd, double str, double rcw){
    m_SwerveDrive.setMotors(fwd, str, rcw);
    System.out.println(frontRightSteerMotor.getSelectedSensorPosition());
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new Drive());
  }
}