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
public class Cade2 extends LinearOpMode {
    static void init() {
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
        turntable.setDirection(DcMotor.Direction.FORWARD);

        //add the data to the ftc app
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }
    static void driveleft() {
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
    static void driveright() {
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
    static void drivediagonalforwardright() {
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
    }
    static void drivediagonalforwardleft() {
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
    }
    static void drivediagonalbackwardright() {
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
    }
    static void drivediagonalbackwardleft() {
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
    }
    static void drivereverse() {
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
    static void driveforward() {
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
    static void spinleft() {
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
    static void spinright() {
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
    static void cowup() {
        while(gamepad1.dpad_up) {
            cow.setPower(1);
        }
        cow.setPower(0);
    }
    static void cowdown() {
        while(gamepad1.dpad_down) {
            cow.setPower(-1);
        }
        cow.setPower(0);
    }
    static void turntableright() {
        while(gamepad1.dpad_right) {
            turntable.setPower(1);
        }
        turntable.setPower(0)
    }
    static void turntableleft() {
        while(gamepad1.dpad_left) {
            turntable.setPower(-1);
        }
        turntable.setPower(0)
    }
    static void openclaw() {
        claw_back.setPosition(0);
        claw_front.setPosition(1);
    }
    static void closeclaw() {
        claw_back.setPosition(0.2);
        claw_front.setPosition(0.3);
    }
    static void movebase_hand() {
        if(b == 0) {
            base_hand.setPosition(0); //close the hand
        }else if(b == 1) {
            base_hand.setPosition(1); //open the hand
            b = 0;
        }
    }

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor front_right = null;
    private DcMotor front_left = null;
    private DcMotor back_right = null;
    private DcMotor back_left = null;
    private DcMotor cow = null;
    private DcMotor turntable = null;
    private Servo claw_front;
    private Servo claw_back;
    private Servo base_hand;
    int b = 0;
    @Override

    public void runOpMode() {
        init();
        waitForStart();
        while(opModeIsActive()) {
            //add data to the ftc app
            telemetry.addData("gamepad y = ", gamepad1.right_stick_y);
            telemetry.addData("gamepad x = ",  gamepad1.right_stick_x);
            telemetry.update();



                if(gamepad1.right_stick_x < 0 && gamepad1.right_stick_y < 0.5 && gamepad1.right_stick_y > -0.5) { //go sideways (right) when the right stick is pushed right
                    driveright();
                }

                if(gamepad1.right_stick_x < -0.49 && gamepad1.right_stick_y < -0.49) { //go diagonal forward right when the joystick's x is more than half and the joystick's y is more than half
                    drivediagonalforwardright();
                }

                if(gamepad1.right_stick_x > 0.49 && gamepad1.right_stick_y < -0.49) { //go diagonal forward left when the joystick's x is less than half and the joystick's y is more than half
                    drivediagonalforwardleft();
                }

                if(gamepad1.right_stick_x < -0.49 && gamepad1.right_stick_y > 0.49) { //go diagonal backward right when the joystick's x is more than half and the joystick's y is less than half
                    drivediagonalbackwardright();
                }

                if(gamepad1.right_stick_x > 0.49 && gamepad1.right_stick_y > 0.49) { //go diagonal backward left when the joystick's x is less than half and the joystick's y is less than half
                    drivediagonalbackwardleft();
                }

                if(gamepad1.right_stick_y > 0 && gamepad1.right_stick_x > -0.5 && gamepad1.right_stick_x < 0.5) { //go backward when right stick is pushed down
                    drivereverse();
                }

                if(gamepad1.right_stick_y < 0 && gamepad1.right_stick_x < 0.5 && gamepad1.right_stick_x > -0.5) { //go forward when right stick is pushed up
                    driveforward();
                }

                if(gamepad1.left_stick_x > 0) { //spin left when left stick is pushed left
                    spinleft();
                }

                if(gamepad1.left_stick_x < 0) { //spin right when left stick is pushed right
                    spinright();
                }

                if(gamepad1.dpad_up) {
                    cowup();
                }

                if(gamepad1.dpad_down) {
                    cowdown();
                }

                if(gamepad1.dpad_right) {
                    turntableright();
                }

                if(gamepad1.dpad_left) {
                    turntableleft();
                }

                if(gamepad1.left_bumper) { //open the claw
                    openclaw();
                }

                if(gamepad1.right_bumper) { //close the claw
                    closeclaw();
                }
                if(gamepad1.b) {
                    movebase_hand();
                }
                }
            }
            }