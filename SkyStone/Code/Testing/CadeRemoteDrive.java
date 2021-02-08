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
public class CadeElseTest extends LinearOpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor front_right = null;
    private DcMotor front_left = null;
    private DcMotor back_right = null;
    private DcMotor back_left = null;
    private DcMotor cow = null;
    private DcMotor turntable = null;
    private Servo claw;
    private Servo backclaw;
    private Servo basehand;

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
        claw = hardwareMap.get(Servo.class, "claw");
        backclaw = hardwareMap.get(Servo.class, "backclaw");
        basehand = hardwareMap.get(Servo.class, "basehand");

        //set the direction of the motors
        front_left.setDirection(DcMotor.Direction.REVERSE);
        back_left.setDirection(DcMotor.Direction.REVERSE);
        front_right.setDirection(DcMotor.Direction.FORWARD);
        back_right.setDirection(DcMotor.Direction.FORWARD);
        cow.setDirection(DcMotor.Direction.FORWARD);
        pull_down.setDirection(DcMotor.Direction.FORWARD);
        turntable.setDirection(DcMotor.Direction.FORWARD);

        //add the data to the ftc app
        telemetry.addData("Status: ", "Initialized");
        telemetry.update();

        waitForStart();
        while(opModeIsActive()) {

            if(gamepad1.right_stick_x > 0 && gamepad1.right_stick_y > -0.5 && gamepad1.right_stick_y < 0.5 && opModeIsActive()) { //go sideways (left) when the right stick is pushed left
                front_left.setPower(gamepad1.right_stick_x); //front left drive forward
                front_right.setPower(-gamepad1.right_stick_x); //front right drive backward
                back_left.setPower(-gamepad1.right_stick_x); //back left drive backward
                back_right.setPower(gamepad1.right_stick_x); //back right drive forward
            }else if (opModeIsActive()){
                front_left.setPower(0);
                front_right.setPower(0);
                back_left.setPower(0);
                back_right.setPower(0);
            }else {
                break;
            }

            if(gamepad1.right_stick_x < 0 && gamepad1.right_stick_y < 0.5 && gamepad1.right_stick_y > -0.5 && opModeIsActive()) { //go sideways (right) when the right stick is pushed right
                front_left.setPower(gamepad1.right_stick_x); //front left drive backward
                front_right.setPower(-gamepad1.right_stick_x); //front right drive forward
                back_left.setPower(-gamepad1.right_stick_x); //back left drive forward
                back_right.setPower(gamepad1.right_stick_x); //back right drive backward
            }else if (opModeIsActive()){
                front_left.setPower(0);
                front_right.setPower(0);
                back_left.setPower(0);
                back_right.setPower(0);
            }else {
                break;
            }

            if(gamepad1.right_stick_x < -0.49 && gamepad1.right_stick_y < -0.49 && opModeIsActive()) { //go diagonal forward right when the joystick's x is more than half and the joystick's y is more than half
                front_left.setPower(0.3);
                front_right.setPower(-gamepad1.right_stick_y); //forward
                back_left.setPower(-gamepad1.right_stick_y); //forward
                back_right.setPower(0.3);
            }else if (opModeIsActive()){
                front_left.setPower(0);
                front_right.setPower(0);
                back_left.setPower(0);
                back_right.setPower(0);
            }else {
                break;
            }

            if(gamepad1.right_stick_x > 0.49 && gamepad1.right_stick_y < -0.49 && opModeIsActive()) { //go diagonal forward left when the joystick's x is less than half and the joystick's y is more than half
                front_left.setPower(-gamepad1.right_stick_y); //forward
                front_right.setPower(0.3);
                back_left.setPower(0.3);
                back_right.setPower(-gamepad1.right_stick_y); //forward
            }else if (opModeIsActive()){
                front_left.setPower(0);
                front_right.setPower(0);
                back_left.setPower(0);
                back_right.setPower(0);
            }else {
                break;
            }

            if(gamepad1.right_stick_x < -0.49 && gamepad1.right_stick_y > 0.49 && opModeIsActive()) { //go diagonal backward right when the joystick's x is more than half and the joystick's y is less than half
                front_left.setPower(-gamepad1.right_stick_y); //backward
                front_right.setPower(-0.3);
                back_left.setPower(-0.3);
                back_right.setPower(-gamepad1.right_stick_y); //backward
            }else if (opModeIsActive()){
                front_left.setPower(0);
                front_right.setPower(0);
                back_left.setPower(0);
                back_right.setPower(0);
            }else {
                break;
            }

            if(gamepad1.right_stick_x > 0.49 && gamepad1.right_stick_y > 0.49 && opModeIsActive()) { //go diagonal backward left when the joystick's x is less than half and the joystick's y is less than half
                front_left.setPower(-0.3);
                front_right.setPower(-gamepad1.right_stick_y); //backward
                back_left.setPower(-gamepad1.right_stick_y); //backward
                back_right.setPower(-0.3);
            }else if (opModeIsActive()){
                front_left.setPower(0);
                front_right.setPower(0);
                back_left.setPower(0);
                back_right.setPower(0);
            }else {
                break;
            }

            if(gamepad1.right_stick_y > 0 && gamepad1.right_stick_x > -0.5 && gamepad1.right_stick_x < 0.5 && opModeIsActive()) { //go backward when right stick is pushed down
                front_left.setPower(-gamepad1.right_stick_y); //front left drive backward
                front_right.setPower(-gamepad1.right_stick_y); //front right drive backward
                back_left.setPower(-gamepad1.right_stick_y); //back left drive backward
                back_right.setPower(-gamepad1.right_stick_y); //back right drive backward
            }else if (opModeIsActive()){
                front_left.setPower(0);
                front_right.setPower(0);
                back_left.setPower(0);
                back_right.setPower(0);
            }else {
                break;
            }

            if(gamepad1.right_stick_y < 0 && gamepad1.right_stick_x < 0.5 && gamepad1.right_stick_x > -0.5 && opModeIsActive()) { //go forward when right stick is pushed up
                front_left.setPower(-gamepad1.right_stick_y); //front left drive forward
                front_right.setPower(-gamepad1.right_stick_y); //front right drive forward
                back_left.setPower(-gamepad1.right_stick_y); //back left drive forward
                back_right.setPower(-gamepad1.right_stick_y); //back right drive forward
            }else if (opModeIsActive()){
                front_left.setPower(0);
                front_right.setPower(0);
                back_left.setPower(0);
                back_right.setPower(0);
            }else {
                break;
            }

            if(gamepad1.left_stick_x > 0 && opModeIsActive()) { //spin left when left stick is pushed left
                front_left.setPower(-gamepad1.left_stick_x); //front left drive backward
                front_right.setPower(gamepad1.left_stick_x); //front right drive forward
                back_left.setPower(-gamepad1.left_stick_x); //back left drive backward
                back_right.setPower(gamepad1.left_stick_x); //back right drive forward
            }else if (opModeIsActive()){
                front_left.setPower(0);
                front_right.setPower(0);
                back_left.setPower(0);
                back_right.setPower(0);
            }else {
                break;
            }

            if(gamepad1.left_stick_x < 0 && opModeIsActive()) { //spin right when left stick is pushed right
                front_left.setPower(-gamepad1.left_stick_x); //front left drive forward
                front_right.setPower(gamepad1.left_stick_x); //front right drive backward
                back_left.setPower(-gamepad1.left_stick_x); //back left drive forward
                back_right.setPower(gamepad1.left_stick_x); //back right drive backward
            }else if (opModeIsActive()){
                front_left.setPower(0);
                front_right.setPower(0);
                back_left.setPower(0);
                back_right.setPower(0);
            }else {
                break;
            }
            

            
            if(gamepad1.dpad_up && opModeIsActive()) {
                cow.setPower(1);
                pull_down.setPower(-1);
            }else if(opModeIsActive()) {
                cow.setPower(0);
                pull_down.setPower(0);
            }else {
                break;
            }

            if(gamepad1.dpad_down && opModeIsActive()) {
                cow.setPower(-1);
                pull_down.setPower(1);
            }else if(opModeIsActive()) {
                cow.setPower(0);
                pull_down.setPower(0);
            }else {
                break;
            }
            

            if(gamepad1.dpad_right && opModeIsActive()) {
                turntable.setPower(1);
            }else if(opModeIsActive()) {
                turntable.setPower(0);
            }else {
                break;
            }

            if(gamepad1.dpad_left && opModeIsActive()) {
                turntable.setPower(-1);
            }else if(opModeIsActive()) {
                turntable.setPower(0);
            }else {
                break;
            }
            
            
            if(gamepad1.left_bumper && opModeIsActive()) {
                backclaw.setPosition(0.6);//open the claw
                claw.setPosition(0.3);
            }else {
                break;
            }


            if(gamepad1.right_bumper && opModeIsActive()) {
                backclaw.setPosition(0.15);
                claw.setPosition(0.45);
            }else {
                break;
            }


            if(gamepad1.b && opModeIsActive()) {
                basehand.setPosition(0); //close the hand
            }else {
                break;
            }


            if(gamepad1.y && opModeIsActive()) {
                basehand.setPosition(1); //open the hand
            }else {
                break;
            }


        } //end while loop  
    } //end public void
} //end class