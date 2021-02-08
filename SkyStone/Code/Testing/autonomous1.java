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

@Autonomous(name="Cade's remote drive", group="Cade_sensor")
//@Disabled
public class CadeRemoteDrive extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor front_right_drive = null;
    private DcMotor front_left_drive = null;
    private DcMotor back_right_drive = null;
    private DcMotor back_left_drive = null;
    private DcMotor cow = null;
    private DcMotor turn_table = null;
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
        turn_table = hardwareMap.get(DcMotor.class, "turn_table");
        sensor2mDistance1 = hardwareMap.get(DistanceSensor.class, "sensor_distance1");
        sensor_color = hardwareMap.get();

        //set the direction of the motors
        front_left_drive.setDirection(DcMotor.Direction.FORWARD);
        back_left_drive.setDirection(DcMotor.Direction.FORWARD);
        front_right_drive.setDirection(DcMotor.Direction.REVERSE);
        back_right_drive.setDirection(DcMotor.Direction.REVERSE);
        cow.setDirection(DcMotor.Direction.FORWARD);
        turn_table.setDirection(DcMotor.Direction.FORWARD);

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

              int passred = 0;
              int passblue = 0;
              front_left_drive.setPower(0);
              front_right_drive.setPower(0);
              back_left_drive.setPower(0);
              back_right_drive.setPower(0);
              if(sensor2mDistance1.getDistance(DistanceUnit.INCH) > 10) {
                while(sensor2mDistance1.getDistance(DistanceUnit.INCH) > 10) {
                    front_left_drive.setPower(0.5);
                    front_right_drive.setPower(0.5);
                    back_left_drive.setPower(0.5);
                    back_right_drive.setPower(0.5);
                    if(sensor_color.blue()/100 - sensor_color.green()/100 >=3) {
                        telemetry.addData("Blue is seen: ", "true");
                        telemetry.update();
                        front_left_drive.setPower(0);
                        front_right_drive.setPower(0);
                        back_left_drive.setPower(0);
                        back_right_drive.setPower(0);
                        if(passblue == 0) {
                            passblue++;
                        }else if(passblue > 0) {
                            break;
                        }
                    } else if(sensor_color.red()/100 - sensor_color.green()/100 >=3) {
                        telemetry.addData("Red is seen: ", "true");
                        telemetry.update();
                        front_left_drive.setPower(0);
                        front_right_drive.setPower(0);
                        back_left_drive.setPower(0);
                        back_right_drive.setPower(0);
                        if(passred == 0){
                            passred++;                            
                        }else if(passred > 0) {
                            break;
                        }
                    }
                }    
              }else if(sensor2mDistance1.getDistance(DistanceUnit.INCH) < 11) { //turn around if it sees something close to it
                front_left_drive.setPower(1);
                front_right_drive.setPower(0);
                back_left_drive.setPower(1);
                back_right_drive.setPower(0);
                sleep(300);
              }
                /* //motor directions from the remote drive
                if(gamepad1.right_stick_x < 0 && gamepad1.right_stick_y > -0.5) { //go sideways (left) when the right stick is pushed left
                    while(gamepad1.right_stick_x < 0 && gamepad1.right_stick_y > -0.5) {
                        front_left_drive.setPower(-gamepad1.right_stick_x); //front left drive forward
                        front_right_drive.setPower(gamepad1.right_stick_x); //front right drive backward
                        back_left_drive.setPower(gamepad1.right_stick_x); //back left drive backward
                        back_right_drive.setPower(-gamepad1.right_stick_x); //back right drive forward
                    }
                    front_left_drive.setPower(0);
                    front_right_drive.setPower(0);
                    back_left_drive.setPower(0);
                    back_right_drive.setPower(0);
                }

                if(gamepad1.right_stick_x > 0 && gamepad1.right_stick_y < 0.5) { //go sideways (right) when the right stick is pushed right
                    while(gamepad1.right_stick_x > 0 && gamepad1.right_stick_y < 0.5) {
                        front_left_drive.setPower(-gamepad1.right_stick_x); //front left drive backward
                        front_right_drive.setPower(gamepad1.right_stick_x); //front right drive forward
                        back_left_drive.setPower(gamepad1.right_stick_x); //back left drive forward
                        back_right_drive.setPower(-gamepad1.right_stick_x); //back right drive backward
                    }
                    front_left_drive.setPower(0);
                    front_right_drive.setPower(0);
                    back_left_drive.setPower(0);
                    back_right_drive.setPower(0);
                }

                if(gamepad1.right_stick_x > 0.49 && gamepad1.right_stick_y > 0.49) { //go diagonal forward right when the joystick's x is more than half and the joystick's y is more than half
                    while(gamepad1.right_stick_x > 0.49 && gamepad1.right_stick_y > 0.49) {
                        front_left_drive.setPower(0);
                        front_right_drive.setPower(gamepad1.right_stick_y);
                        back_left_drive.setPower(gamepad1.right_stick_y);
                        back_right_drive.setPower(0);
                    }
                    front_left_drive.setPower(0);
                    front_right_drive.setPower(0);
                    back_left_drive.setPower(0);
                    back_right_drive.setPower(0);
                } //completed

                if(gamepad1.right_stick_x < -0.49 && gamepad1.right_stick_y > 0.49) { //go diagonal forward left when the joystick's x is less than half and the joystick's y is more than half
                    while(gamepad1.right_stick_x < -0.49 && gamepad1.right_stick_y > 0.49) {
                        front_left_drive.setPower(gamepad1.right_stick_y);
                        front_right_drive.setPower(0);
                        back_left_drive.setPower(0);
                        back_right_drive.setPower(gamepad1.right_stick_y);
                    }
                    front_left_drive.setPower(0);
                    front_right_drive.setPower(0);
                    back_left_drive.setPower(0);
                    back_right_drive.setPower(0);
                } //completed

                if(gamepad1.right_stick_x > 0.49 && gamepad1.right_stick_y < -0.49) { //go diagonal backward right when the joystick's x is more than half and the joystick's y is less than half
                    while(gamepad1.right_stick_x > 0.49 && gamepad1.right_stick_y < -0.49) {
                        front_left_drive.setPower(gamepad1.right_stick_y);
                        front_right_drive.setPower(0);
                        back_left_drive.setPower(0);
                        back_right_drive.setPower(gamepad1.right_stick_y);
                    }
                    front_left_drive.setPower(0);
                    front_right_drive.setPower(0);
                    back_left_drive.setPower(0);
                    back_right_drive.setPower(0);
                } //completed

                if(gamepad1.right_stick_x < -0.49 && gamepad1.right_stick_y < -0.49) { //go diagonal backward left when the joystick's x is less than half and the joystick's y is less than half
                    while(gamepad1.right_stick_x < -0.49 && gamepad1.right_stick_y < -0.49) {
                        front_left_drive.setPower(0);
                        front_right_drive.setPower(gamepad1.right_stick_y);
                        back_left_drive.setPower(gamepad1.right_stick_y);
                        back_right_drive.setPower(0);
                    }
                    front_left_drive.setPower(0);
                    front_right_drive.setPower(0);
                    back_left_drive.setPower(0);
                    back_right_drive.setPower(0);
                } //completed

                if(gamepad1.right_stick_y < 0 && gamepad1.right_stick_x > -0.5) { //go backward when right stick is pushed down
                     while(gamepad1.right_stick_y < 0 && gamepad1.right_stick_x > -0.5) {
                        front_left_drive.setPower(-gamepad1.right_stick_y); //front left drive backward
                        front_right_drive.setPower(-gamepad1.right_stick_y); //front right drive backward
                        back_left_drive.setPower(-gamepad1.right_stick_y); //back left drive backward
                        back_right_drive.setPower(-gamepad1.right_stick_y); //back right drive backward
                     }
                     front_left_drive.setPower(0);
                     front_right_drive.setPower(0);
                     back_left_drive.setPower(0);
                     back_right_drive.setPower(0);
                }

                if(gamepad1.right_stick_y > 0 && gamepad1.right_stick_x < 0.5) { //go forward when right stick is pushed up
                    while(gamepad1.right_stick_y > 0 && gamepad1.right_stick_x < 0.5) {
                        front_left_drive.setPower(gamepad1.right_stick_y); //front left drive forward
                        front_right_drive.setPower(gamepad1.right_stick_y); //front right drive forward
                        back_left_drive.setPower(gamepad1.right_stick_y); //back left drive forward
                        back_right_drive.setPower(gamepad1.right_stick_y); //back right drive forward
                    }
                    front_left_drive.setPower(0);
                    front_right_drive.setPower(0);
                    back_left_drive.setPower(0);
                    back_right_drive.setPower(0);
                }

                if(gamepad1.left_stick_x < 0) { //spin left when left stick is pushed left
                    while(gamepad1.left_stick_x > 0) {
                        front_left_drive.setPower(gamepad1.left_stick_x); //front left drive backward
                        front_right_drive.setPower(-gamepad1.left_stick_x); //front right drive forward
                        back_left_drive.setPower(gamepad1.left_stick_x); //back left drive backward
                        back_right_drive.setPower(-gamepad1.left_stick_x); //back right drive forward
                    }
                    front_left_drive.setPower(0);
                    front_right_drive.setPower(0);
                    back_left_drive.setPower(0);
                    back_right_drive.setPower(0);
                }

                if(gamepad1.left_stick_x > 0) { //spin right when left stick is pushed right
                    while(gamepad1.left_stick_x > 0) {
                        front_left_drive.setPower(gamepad1.left_stick_x); //front left drive forward
                        front_right_drive.setPower(-gamepad1.left_stick_x); //front right drive backward
                        back_left_drive.setPower(gamepad1.left_stick_x); //back left drive forward
                        back_right_drive.setPower(-gamepad1.left_stick_x); //back right drive backward
                    }
                    front_left_drive.setPower(0);
                    front_right_drive.setPower(0);
                    back_left_drive.setPower(0);
                    back_right_drive.setPower(0);
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
                }
                */
                }
            }
            }