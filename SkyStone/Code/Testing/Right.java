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
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


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

@Autonomous(name="BluePositionRight", group="Autonomous")
//@Disabled
public class BluePositionRight extends LinearOpMode { /*This code makes the robot drive forward until a block is seen. It grabs it and brings it
to the other side of the arena. It drops it. Then the robot drives until it sees a blue line.*/

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor front_right = null;
    private DcMotor front_left = null;
    private DcMotor back_right = null;
    private DcMotor back_left = null;
    private DcMotor cow = null;
    private DcMotor turntable = null;
    private DcMotor pull_down = null;
    private Servo claw;
    private Servo backclaw;
    private Servo basehand;
    private DistanceSensor distance;
    ColorSensor color;
    @Override
    public void runOpMode() {
        //initialize the hardware
        front_left = hardwareMap.get(DcMotor.class, "front_left");
        front_right = hardwareMap.get(DcMotor.class, "front_right");
        back_left = hardwareMap.get(DcMotor.class, "back_left");
        back_right = hardwareMap.get(DcMotor.class, "back_right");
        cow = hardwareMap.get(DcMotor.class, "cow");
        turntable = hardwareMap.get(DcMotor.class, "turntable");
        distance = hardwareMap.get(DistanceSensor.class, "distance");
        color = hardwareMap.get(ColorSensor.class, "color");
        claw = hardwareMap.get(Servo.class, "claw");
        backclaw = hardwareMap.get(Servo.class, "backclaw");
        basehand = hardwareMap.get(Servo.class, "basehand");
        pull_down = hardwareMap.get(DcMotor.class, "pull_down");

        //set the direction of the motors
        front_left.setDirection(DcMotor.Direction.REVERSE);
        back_left.setDirection(DcMotor.Direction.REVERSE);
        front_right.setDirection(DcMotor.Direction.FORWARD);
        back_right.setDirection(DcMotor.Direction.FORWARD);
        cow.setDirection(DcMotor.Direction.FORWARD);
        turntable.setDirection(DcMotor.Direction.FORWARD);
        pull_down.setDirection(DcMotor.Direction.FORWARD);

        //add the data to the ftc app
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        while(opModeIsActive()) {
            if(opModeIsActive()) {
                //turn table motor unfolded
                turntable.setPower(-0.4);
                sleep(350);
                turntable.setPower(0);
            }else if(!opModeIsActive()) {
                break;
            }
            if(opModeIsActive()) {
                //claw position set (open)
                claw.setPosition(0.6);
                backclaw.setPosition(0.3);
                sleep(300);
            }else if(!opModeIsActive()) {
                break;
            }
            if(opModeIsActive()) {
                //go forward for 0.8 seconds
                front_left.setPower(0.5);
                front_right.setPower(0.5);
                back_left.setPower(0.5);
                back_right.setPower(0.5);
                sleep(1050);
                front_left.setPower(0);
                front_right.setPower(0);
                back_left.setPower(0);
                back_right.setPower(0);
                sleep(200);
            }else if(!opModeIsActive()) {
                break;
            }
            if(opModeIsActive()) {
                //close the claw on the brick
                claw.setPosition(0.15);
                backclaw.setPosition(0.45);
                sleep(400);
            }else if(!opModeIsActive()) {
                break;
            }
            if(opModeIsActive()) {
                //back up for half a second
                front_left.setPower(-0.5);
                front_right.setPower(-0.5);
                back_left.setPower(-0.5);
                back_right.setPower(-0.5);
                sleep(500);
            }else if(!opModeIsActive()) {
                break;
            }
            //go left with the block
            if(opModeIsActive()) {
                //continue left for a second
                front_left.setPower(-0.5);
                front_right.setPower(0.5);
                back_left.setPower(0.5);
                back_right.setPower(-0.5);
                sleep(4500);
            }else if(!opModeIsActive()) {
                break;
            }

            if(opModeIsActive()) {
                //open the claw
                claw.setPosition(0.6);
                backclaw.setPosition(0.3);
                telemetry.addData("Claw is open: ", "true");
                telemetry.update();
            }else if(!opModeIsActive()){
                break;
            }
            if(opModeIsActive()) {
                front_left.setPower(0.5); //front left drive backward
                front_right.setPower(-0.5); //front right drive forward
                back_left.setPower(-0.5); //back left drive forward
                back_right.setPower(0.5); //back right drive backward
                sleep(1900);
            }else if(!opModeIsActive()) {
                break;
            }
                if(opModeIsActive()) {
                    front_left.setPower(-0.5);
                    front_right.setPower(-0.5);
                    back_left.setPower(-0.5);
                    back_right.setPower(-0.5);
                    sleep(500);
                    front_left.setPower(0);
                    front_right.setPower(0);
                    back_left.setPower(0);
                    back_right.setPower(0);
                }else if(!opModeIsActive()) {
                break;
            }
                break;
        }
    }
}
