/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="redpositionright", group="Cade_sensor")
//@Disabled
public class CadeRemoteDrive extends LinearOpMode {

	// Declare OpMode members.
	private ElapsedTime runtime = new ElapsedTime();
	private DcMotor front_right_drive = null;
	private DcMotor front_left_drive = null;
	private DcMotor back_right = null;
	private DcMotor back_left = null;
	private DcMotor cow = null;
	private DcMotor turntable = null;
	private DcMotor pull_down = null;
	private Servo claw_front;
	private Servo claw_back;
	private Servo base_hand;
	private DistanceSensor sensor2mDistance1;
	ColorSensor sensor_color;
	@Override
	public void runOpMode() {
		//initialize the hardware
		Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor)sensor2mDistance1;
		front_left_drive = hardwareMap.get(DcMotor.class, "front_left_drive");
		front_right_drive = hardwareMap.get(DcMotor.class, "front_right_drive");
		back_left = hardwareMap.get(DcMotor.class, "back_left");
		back_right = hardwareMap.get(DcMotor.class, "back_right");
		cow = hardwareMap.get(DcMotor.class, "cow");
		turntable = hardwareMap.get(DcMotor.class, "turntable");
		claw_front = hardwareMap.get(Servo.class, "claw");
		claw_back = hardwareMap.get(Servo.class, "backclaw");
		base_hand = hardwareMap.get(Servo.class, "basehand");
		pull_down = hardwareMap.get(DcMotor.class, "pull_down");

		//set the direction of the motors
		front_left_drive.setDirection(DcMotor.Direction.REVERSE);
		back_left.setDirection(DcMotor.Direction.REVERSE);
		front_right_drive.setDirection(DcMotor.Direction.FORWARD);
		back_right.setDirection(DcMotor.Direction.FORWARD);
		cow.setDirection(DcMotor.Direction.FORWARD);
		turn_table.setDirection(DcMotor.Direction.FORWARD);
		pull_down.setDirection(DcMotor.Direction.FORWARD);

		//add the data to the ftc app
		telemetry.addData("Status", "Initialized");
		telemetry.update();

		waitForStart();
		while(opModeIsActive()) { //this program takes the inputs from both the color sensor and the 2m distance sensor to avoid obstacles and 
			
			if(opModeIsActive()){
				//go forward (backward, but we start backward) for 0.8 seconds
				front_left.setPower(-0.5);
				front_right.setPower(-0.5);
				back_left.setPower(-0.5);
				back_right.setPower(-0.5);
				sleep(1100);
				front_left.setPower(0);
				front_right.setPower(0);
				back_left.setPower(0);
				back_right.setPower(0);
				base_hand.setPosition(0);
				sleep(300);
			}
			if(opModeIsActive()){
				//turn the base
				front_left.setPower(-0.5);
				front_right.setPower(0.5);
				back_left.setPower(-0.5);
				back_right.setPower(0.5);
				sleep(1000);
			}
			if(opModeIsActive()){
				//move it into the building zone
				front_left.setPower(-1); //front left drive backward
				front_right.setPower(1); //front right drive forward
				back_left.setPower(1); //back left drive forward
				back_right.setPower(-1); //back right drive backward
				sleep(1000);
			}
			if(opModeIsActive()){
				front_left.setPower(-1);
				front_right.setPower(-1);
				back_left.setPower(-1);
				back_right.setPower(-1);
				sleep(500);

			}
			if(opModeIsActive()){
				//let go
				base_hand.setPosition(1);

			}
			if(opModeIsActive()){
				//push forward
				front_left.setPower(0.5);
				front_right.setPower(0.5);
				back_left.setPower(0.5);
				back_right.setPower(0.5);
				sleep(2000);
				front_left.setPower(0);
				front_right.setPower(0);
				back_left.setPower(0);
				back_right.setPower(0);
			}
				}
			}
			}