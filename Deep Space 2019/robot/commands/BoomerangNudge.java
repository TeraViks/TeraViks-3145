/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class BoomerangNudge extends Command {
  private double upDown;
  private double position;

  public BoomerangNudge() {
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // getting fwd values from raw input from the joystick's y axis
    upDown = -Robot.m_oi.getOperatorY();

    // Dead-band the joystick inputs to remove noise/errors when centered
    if ((upDown > -RobotMap.Y_AXIS_THREASHOLD) && (upDown < RobotMap.Y_AXIS_THREASHOLD)) upDown = 0.;

    position = (double) Robot.m_boomerang.getLiftPosition();
    if (upDown > 0) position += 5000.; 
    if (upDown < 0) position -= 5000.;

    Robot.m_boomerang.setLiftLevel(position);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
