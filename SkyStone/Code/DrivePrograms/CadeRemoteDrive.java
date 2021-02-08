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
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
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

@TeleOp(name="Cade's remote drive", group="drive")
//@Disabled
public class CadeRemoteDrive extends LinearOpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor front_right = null;
    private DcMotor front_left = null;
    private DcMotor back_right = null;
    private DcMotor back_left = null;
    private DcMotor cow = null;
    private DcMotor turn_table = null;
    private Servo claw_front;
    private Servo claw_back;
    private Servo base_hand;
    int b = 0;
    int a = 0;

    @Override

    public void runOpMode() {
        //initialize the hardware
        front_left = hardwareMap.get(DcMotor.class, "front_left");
        front_right = hardwareMap.get(DcMotor.class, "front_right");
        back_left = hardwareMap.get(DcMotor.class, "back_left");
        back_right = hardwareMap.get(DcMotor.class, "back_right");
        cow = hardwareMap.get(DcMotor.class, "cow");
        turntable = hardwareMap.get(DcMotor.class, "turntable");
        pull_down = hardwareMap.get(DcMotor.class, "pull_down");
        claw_front = hardwareMap.get(Servo.class, "claw_front");
        claw_back = hardwareMap.get(Servo.class, "claw_back");
        base_hand = hardwareMap.get(Servo.class, "base_hand");

        //set the direction of the motors
        front_left.setDirection(DcMotor.Direction.REVERSE);
        back_left.setDirection(DcMotor.Direction.REVERSE);
        front_right.setDirection(DcMotor.Direction.FORWARD);
        back_right.setDirection(DcMotor.Direction.FORWARD);
        cow.setDirection(DcMotor.Direction.FORWARD);
        turn_table.setDirection(DcMotor.Direction.FORWARD);

        //add the data to the ftc app
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        Thread drive = new Thread() {
            public void run() {
                while(opModeIsActive()) {
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
                } //end while loop
            } //end public void run
        }; //end thread drive

        Thread cow = new Thread() {
            public void run() {
                while(opModeIsActive()) {
                    if(gamepad1.dpad_up) {
                        while(gamepad1.dpad_up) {
                            cow.setPower(1);
                            pull_down.setPower(-1);
                        }
                        cow.setPower(0);
                        pull_down.setPower(0);
                    }

                    if(gamepad1.dpad_down) {
                        while(gamepad1.dpad_down) {
                            cow.setPower(-1);
                            pull_down.setPower(1);
                        }
                        cow.setPower(0);
                        pull_down.setPower(0);
                    }
                } //end while loop
            } //end public void run
        }; //end thread "other"

        Thread turntable = new Thread() {
            public void run() {
                while(opModeIsActive) {
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
                }
            }
        }; //end thread turntable
        
        Thread claw = new Thread() {
            public void run() {
                while(opModeIsActive) {
                    if(gamepad1.left_trigger) {
                        if(a == 0) { //close the claw
                            claw_back.setPosition(0.2);
                            claw_front.setPosition(0.3);
                            a = 1;
                        }else if(a == 1) { //open the claw
                            claw_back.setPosition(0);
                            claw_front.setPosition(1);
                            a = 0;
                        }
                    }
                }
            }
        }; //end thread claw

        Thread base_hand = new Thread() {
            public void run() {
                while(opModeIsActive) {
                    if(gamepad1.b) {
                        if(b == 0) {
                            base_hand.setPosition(0); //close the hand
                            b = 1;
                        }else if(b == 1) {
                            base_hand.setPosition(1); //open the hand
                            b = 0;
                        }
                    }
                } //end while loop
            } //end public void run
        }; //end thread base_hand
        drive.start();
        cow.start();
        turntable.start();
        claw.start();
        base_hand.start();

        drive.join();
        cow.join();
        turntable.join();
        claw.join();
        base_hand.join();
        
    } //end public void runopmode
} //end class