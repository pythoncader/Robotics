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

@Autonomous(name="bluepositionright", group="Cade_sensor")
//@Disabled
public class CadeRemoteDrive extends LinearOpMode {

	// Declare OpMode members.
	private ElapsedTime runtime = new ElapsedTime();
	private DcMotor front_right_drive = null;
	private DcMotor front_left_drive = null;
	private DcMotor back_right_drive = null;
	private DcMotor back_left_drive = null;
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
		back_left_drive = hardwareMap.get(DcMotor.class, "back_left_drive");
		back_right_drive = hardwareMap.get(DcMotor.class, "back_right_drive");
		cow = hardwareMap.get(DcMotor.class, "cow");
		turntable = hardwareMap.get(DcMotor.class, "turntable");
		sensor2mDistance1 = hardwareMap.get(DistanceSensor.class, "sensor_distance1");
		sensor_color = hardwareMap.get(ColorSensor.class, "sensor_color");
		claw_front = hardwareMap.get(Servo.class, "claw_front");
		claw_back = hardwareMap.get(Servo.class, "claw_back");
		base_hand = hardwareMap.get(Servo.class, "base_hand");
		pull_down = hardwareMap.get(DcMotor.class, "pull_down");

		//set the direction of the motors
		front_left_drive.setDirection(DcMotor.Direction.FORWARD);
		back_left_drive.setDirection(DcMotor.Direction.FORWARD);
		front_right_drive.setDirection(DcMotor.Direction.REVERSE);
		back_right_drive.setDirection(DcMotor.Direction.REVERSE);
		cow.setDirection(DcMotor.Direction.FORWARD);
		turn_table.setDirection(DcMotor.Direction.FORWARD);
		pull_down.setDirection(DcMotor.Direction.FORWARD);

		//add the data to the ftc app
		telemetry.addData("Status", "Initialized");
		telemetry.update();

		waitForStart();
		while(opModeIsActive()) { //this program takes the inputs from both the color sensor and the 2m distance sensor to avoid obstacles and 
			//add data to the ftc app
			telemetry.addData("distance (in): ", sensor2mDistance1.getDistance(DistanceUnit.INCH));
			telemetry.addData("ID", String.format("%x", sensorTimeOfFlight.getMode1ID()));
			telemetry.addData("did time out", Boolean.toString(sensorTimeOfFlight.didTimeoutOccur()));
			telemetry.addData("gamepad y = ", gamepad1.right_stick_y);
			telemetry.addData("gamepad x = ",  gamepad1.right_stick_x);
			telemetry.update();

			//turn table motor unfolded
			turntable.setPower(0.2);
			sleep(300);
			turntable.setPower(0);

			//claw position set
			claw_front.setPosition(0.5);
			claw_back.setPosition(0);

			// lift up a little
			cow.setPower(0.3);
			pull_down.setPower(0.3);
			sleep(500);
			pull_down.setPower(0);
			cow.setPower(0);


			//go left for half a second
			front_left.setPower(0.5); //front left drive forward
			front_right.setPower(-0.5); //front right drive backward
			back_left.setPower(-0.5); //back left drive backward
			back_right.setPower(0.5); //back right drive forward
			sleep(500); //wait half a second

			//go forward for 0.8 seconds
			front_left.setPower(0.5);
			front_right.setPower(0.5);
			back_left.setPower(0.5);
			back_right.setPower(0.5);
			sleep(800);

			//close the claw on brick if distance sensor says that the brick is close
			if(sensor2mDistance1.getDistance(DistanceUnit.INCH) < 6) {
				claw_front.setPosition(0);
				claw_back.setPosition(0.2);
			}else if(sensor2mDistance1.getDistance(DistanceUnit.INCH) > 5) {
				while(sensor2mDistance1.getDistance(DistanceUnit.INCH) > 5) {
					front_left.setPower(0.5);
					front_right.setPower(0.5);
					back_left.setPower(0.5);
					back_right.setPower(0.5);
				}
				if(sensor2mDistance1.getDistance(DistanceUnit.INCH) < 6) {
					front_left.setPower(0);
					front_right.setPower(0);
					back_left.setPower(0);
					back_right.setPower(0);
					claw_front.setPosition(0);
					claw_back.setPosition(0.2);
				}
			}
			//back up and let the lift down
			cow.setPower(-0.3)
			front_left.setPower(-0.5);
			front_right.setPower(-0.5);
			back_left.setPower(-0.5);
			back_right.setPower(-0.5);
			sleep(500);

			//go right with the block (waiting until crossing the line)
			while(sensor_color.blue()/100 - sensor_color.green()/100 < 3) {
				front_left.setPower(-0.5); //front left drive backward
				front_right.setPower(0.5); //front right drive forward
				back_left.setPower(0.5); //back left drive forward
				back_right.setPower(-0.5); //back right drive backward
				if(sensor_color.blue()/100 - sensor_color.green()/100 >= 3) {
					front_left.setPower(0);
					front_right.setPower(0);
					back_left.setPower(0);
					back_right.setPower(0);
					sleep(500);
				}			
			}

			front_left.setPower(-0.5);
			front_right.setPower(0.5);
			back_left.setPower(0.5);
			back_right.setPower(-0.5);
			sleep(1500);

			front_left.setPower(0.5);
			front_right.setPower(-0.5);
			back_left.setPower(0.5);
			back_right.setPower(-0.5);
			sleep(500);

			front_left.setPower(-0.5);
			front_right.setPower(-0.5);
			back_left.setPower(-0.5);
			back_right.setPower(-0.5);
			sleep(500);

			front_left.setPower(0);
			front_right.setPower(0);
			back_left.setPower(0);
			back-right.setPower(0);

			base_hand.setPosition(0);
			sleep(300);

			//turn the base
			front_left.setPower(-0.5);
			front_right.setPower(0.5);
			back_left.setPower(-0.5);
			back_right.setPower(0.5);
			sleep(1000);

			//move it into the building zone
			front_left.setPower(-1); //front left drive backward
			front_right.setPower(1); //front right drive forward
			back_left.setPower(1); //back left drive forward
			back_right.setPower(-1); //back right drive backward
			sleep(1000);

			//let go
			base_hand.setPosition(1);

			//back up
			front_left.setPower(-0.5);
			front_right.setPower(-0.5);
			back_left.setPower(-0.5);
			back_right.setPower(-0.5);
			sleep(300);

			//turn around
			front_left.setPower(0.5);
			front_right.setPower(-0.5);
			back_left.setPower(0.5);
			back_right.setPower(-0.5);
			sleep(300);

			//move forward
			front_left.setPower(1);
			front_right.setPower(1);
			back_left.setPower(1);
			back_right.setPower(1);
			sleep(500);
			front_left.setPower(0);
			front_right.setPower(0);
			back_left.setPower(0);
			back_right.setPower(0);

			//set the block on the base
			claw_front.setPosition(1);
			claw_back.setPosition(0);

			//back up until parking on the line
			while(sensor_color.blue()/100 - sensor_color.green()/100 < 3) {
				front_left.setPower(-0.5);
				front_right.setPower(-0.5);
				back_left.setPower(-0.5);
				back_right.setPower(-0.5);
				if(sensor_color.blue()/100 - sensor_color.green()/100 >= 3) { //move a little bit forward when the color sensor sees the line
					front_left.setPower(0.5);
					front_right.setPower(0.5);
					back_left.setPower(0.5);
					back_right.setPower(0.5);
					sleep(200);
				}
			}


				/* //motor directions from the remote drive
			//add data to the ftc app
			telemetry.addData("gamepad y = ", gamepad1.right_stick_y);
			telemetry.addData("gamepad x = ",  gamepad1.right_stick_x);
			telemetry.update();

				if(gamepad1.right_stick_x > 0 && gamepad1.right_stick_y > -0.5 && gamepad1.right_stick_y < 0.5) { //go sideways (left) when the right stick is pushed left
					while(gamepad1.right_stick_x > 0 && gamepad1.right_stick_y > -0.5 && gamepad1.right_stick_y < 0.5) {
						front_left.setPower(gamepad1.right_stick_x); //front left drive forward
						front_right.setPower(-gamepad1.right_stick_x); //front right drive backward
						back_left.setPower(-gamepad1.right_stick_x); //back left drive backward
						back_right.setPower(gamepad1.right_stick_x); //back right drive forward
					}
					front_left.setPower(0);
					front_right.setPower(0);
					back_left.setPower(0);
					back_right.setPower(0);
				}

				if(gamepad1.right_stick_x < 0 && gamepad1.right_stick_y < 0.5 && gamepad1.right_stick_y > -0.5) { //go sideways (right) when the right stick is pushed right
					while(gamepad1.right_stick_x < 0 && gamepad1.right_stick_y < 0.5) {
						front_left.setPower(gamepad1.right_stick_x); //front left drive backward
						front_right.setPower(-gamepad1.right_stick_x); //front right drive forward
						back_left.setPower(-gamepad1.right_stick_x); //back left drive forward
						back_right.setPower(gamepad1.right_stick_x); //back right drive backward
					}
					front_left.setPower(0);
					front_right.setPower(0);
					back_left.setPower(0);
					back_right.setPower(0);
				}

				if(gamepad1.right_stick_x < -0.49 && gamepad1.right_stick_y < -0.49) { //go diagonal forward right when the joystick's x is more than half and the joystick's y is more than half
					while(gamepad1.right_stick_x < -0.49 && gamepad1.right_stick_y < -0.49) {
						front_left.setPower(0.3);
						front_right.setPower(-gamepad1.right_stick_y); //forward
						back_left.setPower(-gamepad1.right_stick_y); //forward
						back_right.setPower(0.3);
					}
					front_left.setPower(0);
					front_right.setPower(0);
					back_left.setPower(0);
					back_right.setPower(0);
				} //completed

				if(gamepad1.right_stick_x > 0.49 && gamepad1.right_stick_y < -0.49) { //go diagonal forward left when the joystick's x is less than half and the joystick's y is more than half
					while(gamepad1.right_stick_x > 0.49 && gamepad1.right_stick_y < -0.49) {
						front_left.setPower(-gamepad1.right_stick_y); //forward
						front_right.setPower(0.3);
						back_left.setPower(0.3);
						back_right.setPower(-gamepad1.right_stick_y); //forward
					}
					front_left.setPower(0);
					front_right.setPower(0);
					back_left.setPower(0);
					back_right.setPower(0);
				} //completed

				if(gamepad1.right_stick_x < -0.49 && gamepad1.right_stick_y > 0.49) { //go diagonal backward right when the joystick's x is more than half and the joystick's y is less than half
					while(gamepad1.right_stick_x < -0.49 && gamepad1.right_stick_y > 0.49) {
						front_left.setPower(-gamepad1.right_stick_y); //backward
						front_right.setPower(-0.3);
						back_left.setPower(-0.3);
						back_right.setPower(-gamepad1.right_stick_y); //backward
					}
					front_left.setPower(0);
					front_right.setPower(0);
					back_left.setPower(0);
					back_right.setPower(0);
				} //completed

				if(gamepad1.right_stick_x > 0.49 && gamepad1.right_stick_y > 0.49) { //go diagonal backward left when the joystick's x is less than half and the joystick's y is less than half
					while(gamepad1.right_stick_x > 0.49 && gamepad1.right_stick_y > 0.49) {
						front_left.setPower(-0.3);
						front_right.setPower(-gamepad1.right_stick_y); //backward
						back_left.setPower(-gamepad1.right_stick_y); //backward
						back_right.setPower(-0.3);
					}
					front_left.setPower(0);
					front_right.setPower(0);
					back_left.setPower(0);
					back_right.setPower(0);
				} //completed

				if(gamepad1.right_stick_y > 0 && gamepad1.right_stick_x > -0.5 && gamepad1.right_stick_x < 0.5) { //go backward when right stick is pushed down
					 while(gamepad1.right_stick_y > 0 && gamepad1.right_stick_x > -0.5 && gamepad1.right_stick_x < 0.5) {
						front_left.setPower(-gamepad1.right_stick_y); //front left drive backward
						front_right.setPower(-gamepad1.right_stick_y); //front right drive backward
						back_left.setPower(-gamepad1.right_stick_y); //back left drive backward
						back_right.setPower(-gamepad1.right_stick_y); //back right drive backward
					 }
					 front_left.setPower(0);
					 front_right.setPower(0);
					 back_left.setPower(0);
					 back_right.setPower(0);
				}

				if(gamepad1.right_stick_y < 0 && gamepad1.right_stick_x < 0.5 && gamepad1.right_stick_x > -0.5) { //go forward when right stick is pushed up
					while(gamepad1.right_stick_y < 0 && gamepad1.right_stick_x < 0.5 && gamepad1.right_stick_x > -0.5) {
						front_left.setPower(-gamepad1.right_stick_y); //front left drive forward
						front_right.setPower(-gamepad1.right_stick_y); //front right drive forward
						back_left.setPower(-gamepad1.right_stick_y); //back left drive forward
						back_right.setPower(-gamepad1.right_stick_y); //back right drive forward
					}
					front_left.setPower(0);
					front_right.setPower(0);
					back_left.setPower(0);
					back_right.setPower(0);
				}

				if(gamepad1.left_stick_x > 0) { //spin left when left stick is pushed left
					while(gamepad1.left_stick_x > 0) {
						front_left.setPower(-gamepad1.left_stick_x); //front left drive backward
						front_right.setPower(gamepad1.left_stick_x); //front right drive forward
						back_left.setPower(-gamepad1.left_stick_x); //back left drive backward
						back_right.setPower(gamepad1.left_stick_x); //back right drive forward
					}
					front_left.setPower(0);
					front_right.setPower(0);
					back_left.setPower(0);
					back_right.setPower(0);
				}

				if(gamepad1.left_stick_x < 0) { //spin right when left stick is pushed right
					while(gamepad1.left_stick_x < 0) {
						front_left.setPower(-gamepad1.left_stick_x); //front left drive forward
						front_right.setPower(gamepad1.left_stick_x); //front right drive backward
						back_left.setPower(-gamepad1.left_stick_x); //back left drive forward
						back_right.setPower(gamepad1.left_stick_x); //back right drive backward
					}
					front_left.setPower(0);
					front_right.setPower(0);
					back_left.setPower(0);
					back_right.setPower(0);
				}

				if(gamepad1.dpad_up) {
					while(gamepad1.dpad_up) {
						cow.setPower(1);
					}
					cow.setPower(0);
				}

				if(gamepad1.dpad_down) {
					while(gamepad1.dpad_down) {
						cow.setPower(-1);
					}
					cow.setPower(0);
				}

				if(gamepad1.dpad_right) {
					while(gamepad1.dpad_right) {
						turn_table.setPower(1);
					}
					turn_table.setPower(0)
				}

				if(gamepad1.dpad_left) {
					while(gamepad1.dpad_left) {
						turn_table.setPower(-1);
					}
					turn_table.setPower(0)
				}*/
				}
			}
			}