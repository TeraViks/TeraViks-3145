/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;

import frc.robot.RobotMap;

public class Boomerang extends Subsystem {
  // Create the Drive Motor and Steer Motor Objects
  private final WPI_TalonSRX liftMotor = new WPI_TalonSRX(RobotMap.BOOMERANG_LIFT_TalonSRX_CAN_ID);
  private final WPI_TalonSRX intakeMotor = new WPI_TalonSRX(RobotMap.INTAKE__TalonSRX_CAN_ID);
  private final WPI_TalonSRX shootMotor = new WPI_TalonSRX(RobotMap.SHOOT_TalonSRX_CAN_ID);
  private final WPI_TalonSRX rotateMotor = new WPI_TalonSRX(RobotMap.BOOMERANG_ROTATE_TalonSRX_CAN_ID);
  private final WPI_TalonSRX hatchMotor = new WPI_TalonSRX(RobotMap.HATCH_GRABBER_TalonSRX_CAN_ID);
  private final WPI_TalonSRX liftBoosterMotor = new WPI_TalonSRX(RobotMap.BOOMERANG_LIFT_BOOSTER_TalonSRX_CAN_ID);

  private int TIMEOUT = RobotMap.TalonSRX_TIMEOUT;
  
  public Boomerang(){
  }
  
  public void init(){
    System.out.println("**Initializing Boomerang Motors");
    initLiftMotor();
    initIntakeMotor();
    initShootMotor();
    initRotateMotor();
    initHatchMotor();
    initLiftBoosterMotor();
  }

  public void setLiftLevel(double position){
    liftMotor.set(ControlMode.MotionMagic, position);
  }

  public void setLiftVelocity(double velocity){
    liftMotor.set(ControlMode.Velocity, velocity);
  }

  public int getLiftPosition(){
    return liftMotor.getSelectedSensorPosition();
  }

  public void startBallIntake(){
    intakeMotor.set(ControlMode.PercentOutput, .75);
  }

  public void reverseBallIntake(){
    intakeMotor.set(ControlMode.PercentOutput, -1.);
  }

  public void startBallEject() {
    shootMotor.set(ControlMode.PercentOutput, 1.);
  }

  public void stopBallMotors(){
    intakeMotor.set(ControlMode.PercentOutput, 0.);
    shootMotor.set(ControlMode.PercentOutput, 0.);
  }

  public void deployBoomerang() {
    rotateMotor.set(ControlMode.MotionMagic, RobotMap.BOOMERANG_DEPLOYED_POSITION/3.);
    delay(500);
    rotateMotor.set(ControlMode.MotionMagic, RobotMap.BOOMERANG_DEPLOYED_POSITION/1.8);
    delay(500);
    rotateMotor.set(ControlMode.MotionMagic, RobotMap.BOOMERANG_DEPLOYED_POSITION/1.1);
    delay(500);
    rotateMotor.set(ControlMode.PercentOutput, 0.);
  }

  public double getRotateMotorPosition(){
    return rotateMotor.getSelectedSensorPosition();
  }
  
  public void extendHatchMotors() {
    hatchMotor.set(ControlMode.PercentOutput, 1.);
  }

  public void retractHatchMotors() {
    hatchMotor.set(ControlMode.PercentOutput, -1.);   
  }

  public void holdExtendedHatchMotors() {
    hatchMotor.set(ControlMode.PercentOutput, .05);   
  }

  public void holdRetractedHatchMotors() {
    hatchMotor.set(ControlMode.PercentOutput, -.3);   
  }

  public void stopHatchMotors() {
    hatchMotor.set(ControlMode.PercentOutput, 0.);
  }
  
  private void initLiftMotor(){
    liftMotor.configFactoryDefault();
    
    liftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    liftMotor.selectProfileSlot(0, 0); //slot #, PID #
    
    liftMotor.setInverted(false);
    liftMotor.setNeutralMode(NeutralMode.Brake);
    
    liftMotor.setSensorPhase(false);
    liftMotor.setSelectedSensorPosition(0);
    liftMotor.configClearPositionOnQuadIdx(false, TIMEOUT);
    
    liftMotor.configMotionAcceleration(8000, TIMEOUT);  //400 Optical Encoder accel and velocity targets, max speed
    liftMotor.configMotionCruiseVelocity(12000, TIMEOUT);

    liftMotor.configPeakOutputForward(1., TIMEOUT);
    liftMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    liftMotor.configNominalOutputForward(0, TIMEOUT);
    liftMotor.configNominalOutputReverse(0, TIMEOUT);
    
    liftMotor.configAllowableClosedloopError(0, 8, TIMEOUT); //400 Optical Encoder error

    liftMotor.config_kP(0, .5, TIMEOUT);  //400 Optical Encoder gain
    liftMotor.config_kI(0, 0, TIMEOUT);
    liftMotor.config_kD(0, 1, TIMEOUT);
    liftMotor.config_kF(0, 0, TIMEOUT);
    
    System.out.println("  --Boomerang Lift Motor Initialized");
  }
  
  private void initLiftBoosterMotor(){
    liftBoosterMotor.configFactoryDefault();

    liftBoosterMotor.setInverted(true);
    liftBoosterMotor.setNeutralMode(NeutralMode.Brake);

    liftBoosterMotor.configPeakOutputForward(1., TIMEOUT);
    liftBoosterMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    liftBoosterMotor.configNominalOutputForward(0, TIMEOUT);
    liftBoosterMotor.configNominalOutputReverse(0, TIMEOUT);
    
    liftBoosterMotor.set(ControlMode.Follower, (double)RobotMap.BOOMERANG_LIFT_TalonSRX_CAN_ID);

    System.out.println("  --Boomerang Lift Booster Motor Initialized");
  }
  
  private void initIntakeMotor(){
    intakeMotor.configFactoryDefault();
    
    intakeMotor.setInverted(false);
    intakeMotor.setNeutralMode(NeutralMode.Brake);
    
    intakeMotor.configPeakOutputForward(1., TIMEOUT);
    intakeMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    intakeMotor.configNominalOutputForward(0, TIMEOUT);
    intakeMotor.configNominalOutputReverse(0, TIMEOUT);
    
    System.out.println("  --Boomerang Intake Motor Initialized");
  }
  
  private void initShootMotor(){
    shootMotor.configFactoryDefault();
 
    shootMotor.setInverted(true);
    shootMotor.setNeutralMode(NeutralMode.Brake);
    
    shootMotor.configPeakOutputForward(1., TIMEOUT);
    shootMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    shootMotor.configNominalOutputForward(0, TIMEOUT);
    shootMotor.configNominalOutputReverse(0, TIMEOUT);
    
    System.out.println("  --Boomerang Shoot Motor Initialized");
  }
  
  private void initRotateMotor(){
    rotateMotor.configFactoryDefault();
    
    rotateMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT);
    rotateMotor.selectProfileSlot(0, 0); //slot #, PID #
    
    rotateMotor.setInverted(false);
    rotateMotor.setNeutralMode(NeutralMode.Brake);
    
    rotateMotor.setSensorPhase(false);
    rotateMotor.setSelectedSensorPosition(0);
    rotateMotor.configClearPositionOnQuadIdx(false, TIMEOUT);
    
    rotateMotor.configMotionAcceleration(6000, TIMEOUT);  //400 Mag Encoder accel and cruise targets, adding 3.25:1 GB so triple speed from before
    rotateMotor.configMotionCruiseVelocity(6000, TIMEOUT);

    rotateMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);

    rotateMotor.configPeakOutputForward(1., TIMEOUT);
    rotateMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    rotateMotor.configNominalOutputForward(0, TIMEOUT);
    rotateMotor.configNominalOutputReverse(0, TIMEOUT);
    
    rotateMotor.configAllowableClosedloopError(0, 8, TIMEOUT); //400 Optical Encoder error
    
    rotateMotor.config_kP(0, 1., TIMEOUT);
    rotateMotor.config_kI(0, 0, TIMEOUT);
    rotateMotor.config_kD(0, 1, TIMEOUT);
    rotateMotor.config_kF(0, 0, TIMEOUT);
    
    System.out.println("  --Boomerang Rotate Motor Initialized");
  }
  
  private void initHatchMotor(){
    hatchMotor.configFactoryDefault();
    
    hatchMotor.setInverted(false);
    hatchMotor.setNeutralMode(NeutralMode.Brake);
    
    hatchMotor.configPeakOutputForward(1., TIMEOUT);
    hatchMotor.configPeakOutputReverse(-1., TIMEOUT);
    
    hatchMotor.configNominalOutputForward(0, TIMEOUT);
    hatchMotor.configNominalOutputReverse(0, TIMEOUT);
    
    System.out.println("  --Boomerang Hatch Motor Initialized");
  }

  private void delay(int msec){
    try{
        Thread.sleep(msec);
    }
    catch (Exception e){
        System.out.println("Error in Waitloop");
    }
}
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
