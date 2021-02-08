package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.disnodeteam.dogecv.detectors.skystone.SkystoneDetector;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvWebcam;


import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Locale;

@Autonomous(name = "LeftAutonomousREDTAPE - without opModeIsActive", group="Autonomous")
//@Disabled
public class LeftAutonomousWebcam_no_opModeIsActive extends LinearOpMode {

	private OpenCvCamera webcam;
	private SkystoneDetector skyStoneDetector;

	static final int MOTOR_TICK_COUNTS_HDMOTOR = 2240;
	static final int MOTOR_TICK_COUNTS_COREHEXMOTOR = 288;

	static final double circumference = 3.1415 * 2.95276; //2.95276 is the diameter of the mecanum wheels.
	static final double rotations4inches = 4/circumference; //this is the number of rotations needed to drive 4 inches
	static final double rotations1foot = 12/circumference; //this is the number of rotations needed to drive 1 foot
	
	static final int forwardencoder4inches = (int)(rotations4inches*MOTOR_TICK_COUNTS_HDMOTOR);
	static final int backwardencoder4inches = (int)(rotations4inches*MOTOR_TICK_COUNTS_HDMOTOR) * -1;
	static final int turntableunfolded_position = -MOTOR_TICK_COUNTS_COREHEXMOTOR/4; //this is the position that we need to run the motor to unfold the turntable
	static final int turntablefolded_position = MOTOR_TICK_COUNTS_COREHEXMOTOR/4; //this is the position that we need to run the motor to fold up the turntable
	static final int backwardencoder1foot = (int)(rotations1foot*MOTOR_TICK_COUNTS_HDMOTOR) * -1; //2240 is the motor tick counts for 1 rotation, and we're multiplying it by -1 to make it go backward
	static final int forwardencoder1foot = (int)(rotations1foot*MOTOR_TICK_COUNTS_HDMOTOR); //2240 is the motor tick counts for 1 rotation

	//integrate values for number of rotations needed
	static final int strafeencoder1foot = 3 * MOTOR_TICK_COUNTS_HDMOTOR;
	static final int strafeencoder4inches = 1 * MOTOR_TICK_COUNTS_HDMOTOR;
	static final int spin90rotations = 2 * MOTOR_TICK_COUNTS_HDMOTOR;//number of rotations to turn 90 degrees multiplied by 2240
	
	// integrate values for servo positions into this code
	static final double left_slide_up_position = 1;

	static final double right_slide_up_position = 0;
	
	static final double left_slide_down_position = 0.5;

	static final double right_slide_down_position = 0.62;
	
	static final double clawclosed_position = 0.6;
	
	static final double clawopen_position = 0.35;
	
	static final double claweject_position = 0.9;
	
	static final double backclawclosed_position = 0.5;
	
	static final double backclawopen_position = 0.7;
	
	static final double backclaweject_position = 0.9;

	private DcMotor front_left = null;
	private DcMotor front_right = null;
	private DcMotor back_left = null;
	private DcMotor back_right = null;
	private DcMotor cow = null;
	private DcMotor turntable = null;
	private DcMotor pull_down = null;

	private Servo left_slide;
	private Servo right_slide;
	private Servo backclaw;
	private Servo claw;

	private ColorSensor sensor_color;


	//start simple drive task methods

	void left_slide_up() {
		left_slide.setPosition(left_slide_up_position);
		right_slide.setPosition(right_slide_up_position);
		telemetry.addData("left_slide_up, running: ", "true");
		telemetry.update();
	}

	void left_slide_down() {
		left_slide.setPosition(left_slide_down_position);
		right_slide.setPosition(right_slide_down_position);
		telemetry.addData("left_slide_down, running: ", "true");
		telemetry.update();
	}

	void liftstop() {
		cow.setPower(0);
		pull_down.setPower(0);
		telemetry.addData("liftstop, running: ", "true");
		telemetry.update();
	}

	void liftdown() {
		cow.setPower(1);
		pull_down.setPower(-1);
		telemetry.addData("liftdown, running: ", "true");
		telemetry.update();
	}

	void liftup() {
		cow.setPower(-1);
		pull_down.setPower(1);
		telemetry.addData("liftup, running: ", "true");
		telemetry.update();
	}

	void stopdrivemotors() {
		front_left.setPower(0);
		front_right.setPower(0);
		back_left.setPower(0);
		back_right.setPower(0);
		telemetry.addData("stopdrivemotors, running: ", "true");
		telemetry.update();
	}

	void claweject() {
		claw.setPosition(claweject_position);
		backclaw.setPosition(backclaweject_position);
		telemetry.addData("claweject, running: ", "true");
		telemetry.update();
	}

	void clawclosed() {
		claw.setPosition(clawclosed_position);
		sleep(300);
		backclaw.setPosition(backclawclosed_position);
		telemetry.addData("clawclosed, running: ", "true");
		telemetry.update();
	}

	void clawopen() {
		claw.setPosition(clawopen_position);
		backclaw.setPosition(backclawopen_position);
		telemetry.addData("clawopen, running: ", "true");
		telemetry.update();
	}

	void turntableunfolded() { //this code will not run until called
		turntable.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		turntable.setTargetPosition(turntableunfolded_position);
		turntable.setPower(0.5);
		turntable.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		while(turntable.isBusy()) {
			telemetry.addData("turntableunfolded, running: ", "true");
			telemetry.update();
		}
		turntable.setPower(0);
	}

	void turntablefolded() { //this code will not run until called
		turntable.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		turntable.setTargetPosition(turntablefolded_position);
		turntable.setPower(0.5);
		turntable.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		while(turntable.isBusy()) {
			telemetry.addData("turntablefolded, running: ", "true");
			telemetry.update();
		}
		turntable.setPower(0);
	}

	void spin90left() { //this code will not run until called
		front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		front_left.setTargetPosition(-spin90rotations);
		front_right.setTargetPosition(spin90rotations);
		back_left.setTargetPosition(-spin90rotations);
		back_right.setTargetPosition(spin90rotations);
		
		front_left.setPower(1);
		front_right.setPower(1);
		back_left.setPower(1);
		back_right.setPower(1);

		front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		while(front_left.isBusy()) {
			telemetry.addData("spin90left, running: ", "true");
			telemetry.update();
		}

		front_left.setPower(0);
		front_right.setPower(0);
		back_left.setPower(0);
		back_right.setPower(0);
	}
	void spin90right() { //this code will not run until called
		front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		front_left.setTargetPosition(spin90rotations);
		front_right.setTargetPosition(-spin90rotations);
		back_left.setTargetPosition(spin90rotations);
		back_right.setTargetPosition(-spin90rotations);
		
		front_left.setPower(1);
		front_right.setPower(1);
		back_left.setPower(1);
		back_right.setPower(1);

		front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		while(front_left.isBusy()) {
			telemetry.addData("spin90right, running: ", "true");
			telemetry.update();
		}

		front_left.setPower(0);
		front_right.setPower(0);
		back_left.setPower(0);
		back_right.setPower(0);
	}

	void forward4inches() { //this code will not run until called
		front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		front_left.setTargetPosition(forwardencoder4inches);
		front_right.setTargetPosition(forwardencoder4inches);
		back_left.setTargetPosition(forwardencoder4inches);
		back_right.setTargetPosition(forwardencoder4inches);
		
		front_left.setPower(1);
		front_right.setPower(1);
		back_left.setPower(1);
		back_right.setPower(1);

		front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		while(front_left.isBusy()) {
			telemetry.addData("forward4inches, running: ", "true");
			telemetry.update();
		}

		front_left.setPower(0);
		front_right.setPower(0);
		back_left.setPower(0);
		back_right.setPower(0);
	}

	void backward4inches() { //this code will not run until called
		front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		front_left.setTargetPosition(backwardencoder4inches);
		front_right.setTargetPosition(backwardencoder4inches);
		back_left.setTargetPosition(backwardencoder4inches);
		back_right.setTargetPosition(backwardencoder4inches);
		
		front_left.setPower(1);
		front_right.setPower(1);
		back_left.setPower(1);
		back_right.setPower(1);

		front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		while(front_left.isBusy()) {
			telemetry.addData("backward4inches, running: ", "true");
			telemetry.update();
		}

		front_left.setPower(0);
		front_right.setPower(0);
		back_left.setPower(0);
		back_right.setPower(0);
	}

	void right4inches() { //this code will not run until called
		front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		front_left.setTargetPosition(-strafeencoder4inches);
		front_right.setTargetPosition(strafeencoder4inches);
		back_left.setTargetPosition(strafeencoder4inches);
		back_right.setTargetPosition(-strafeencoder4inches);
		
		front_left.setPower(1);
		front_right.setPower(1);
		back_left.setPower(1);
		back_right.setPower(1);

		front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		while(front_left.isBusy() && opModeIsActive()) {
			telemetry.addData("right4inches, running: ", "true");
			telemetry.update();
		}

		front_left.setPower(0);
		front_right.setPower(0);
		back_left.setPower(0);
		back_right.setPower(0);
	}

	void left4inches() { //this code will not run until called
		front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		front_left.setTargetPosition(strafeencoder4inches);
		front_right.setTargetPosition(-strafeencoder4inches);
		back_left.setTargetPosition(-strafeencoder4inches);
		back_right.setTargetPosition(strafeencoder4inches);
		
		front_left.setPower(1);
		front_right.setPower(1);
		back_left.setPower(1);
		back_right.setPower(1);

		front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		while(front_left.isBusy()) {
			telemetry.addData("left4inches, running: ", "true");
			telemetry.update();
		}

		front_left.setPower(0);
		front_right.setPower(0);
		back_left.setPower(0);
		back_right.setPower(0);
	}

	void forward1foot() { //this code will not run until called
		front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		front_left.setTargetPosition(forwardencoder1foot);
		front_right.setTargetPosition(forwardencoder1foot);
		back_left.setTargetPosition(forwardencoder1foot);
		back_right.setTargetPosition(forwardencoder1foot);
		
		front_left.setPower(1);
		front_right.setPower(1);
		back_left.setPower(1);
		back_right.setPower(1);

		front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		while(front_left.isBusy()) {
			telemetry.addData("forward1foot, running: ", "true");
			telemetry.update();
		}

		front_left.setPower(0);
		front_right.setPower(0);
		back_left.setPower(0);
		back_right.setPower(0);
	} //end forward1foot method

	void backward1foot() { //this code will not run until called
		front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		front_left.setTargetPosition(backwardencoder1foot);
		front_right.setTargetPosition(backwardencoder1foot);
		back_left.setTargetPosition(backwardencoder1foot);
		back_right.setTargetPosition(backwardencoder1foot);

		front_left.setPower(1);
		front_right.setPower(1);
		back_left.setPower(1);
		back_right.setPower(1);

		front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		while(front_left.isBusy()) {
			telemetry.addData("backward1foot, running: ", "true");
			telemetry.update();
		}

		front_left.setPower(0);
		front_right.setPower(0);
		back_left.setPower(0);
		back_right.setPower(0);
	} //end backward1foot method

	void right1foot() { //this code will not run until called
		//front left drive backward
		//front right drive forward
		//back left drive forward
		//back right drive backward
		front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		front_left.setTargetPosition(-strafeencoder1foot);
		front_right.setTargetPosition(strafeencoder1foot);
		back_left.setTargetPosition(strafeencoder1foot);
		back_right.setTargetPosition(-strafeencoder1foot);

		front_left.setPower(1);
		front_right.setPower(1);
		back_left.setPower(1);
		back_right.setPower(1);

		front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		while(front_left.isBusy()) {
			telemetry.addData("right1foot, running: ", "true");
			telemetry.update();
		}

		front_left.setPower(0);
		front_right.setPower(0);
		back_left.setPower(0);
		back_right.setPower(0);

	} //end right1foot method

	void left1foot() { //this code will not run until called
		//front left drive forward
		//front right drive backward
		//back left drive backward
		//back right drive forward
		front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		front_left.setTargetPosition(strafeencoder1foot);
		front_right.setTargetPosition(-strafeencoder1foot);
		back_left.setTargetPosition(-strafeencoder1foot);
		back_right.setTargetPosition(strafeencoder1foot);

		front_left.setPower(1);
		front_right.setPower(1);
		back_left.setPower(1);
		back_right.setPower(1);

		front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		while(front_left.isBusy() && opModeIsActive()) {
			telemetry.addData("left1foot, running: ", "true");
			telemetry.update();
		}

		front_left.setPower(0);
		front_right.setPower(0);
		back_left.setPower(0);
		back_right.setPower(0);

	} //end left1foot method

		void colorsensordrivebackred() {
			// hsvValues is an array that will hold the hue, saturation, and value information.
			float hsvValues[] = {0F, 0F, 0F};

			// values is a reference to the hsvValues array.
			final float values[] = hsvValues;

			// sometimes it helps to multiply the raw RGB values with a scale factor
			// to amplify/attentuate the measured values.
			final double SCALE_FACTOR = 255;

			// convert the RGB values to HSV values.
			// multiply by the SCALE_FACTOR.
			// then cast it back to int (SCALE_FACTOR is a double)
			
			Color.RGBToHSV((int) (sensor_color.red() * SCALE_FACTOR), (int) (sensor_color.green() * SCALE_FACTOR), (int) (sensor_color.blue() * SCALE_FACTOR), hsvValues);
			// send the info back to driver station using telemetry function.
			telemetry.addData("Alpha", sensor_color.alpha());
			telemetry.addData("Red  ", sensor_color.red());
			telemetry.addData("Green", sensor_color.green());
			telemetry.addData("Blue ", sensor_color.blue());
			telemetry.addData("Hue", hsvValues[0]);
			telemetry.addData("Saturation", hsvValues[1]);
			telemetry.addData("Value", hsvValues[2]);
			telemetry.addData("colorsensordrivebackred, running: ", "true");
			telemetry.update();

			//for red require a value from 345-360 or 0 to 15
			while(hsvValues[0] < 344 && hsvValues[0] > 15) { //run while the color sensor is not seeing red

				Color.RGBToHSV((int) (sensor_color.red() * SCALE_FACTOR), (int) (sensor_color.green() * SCALE_FACTOR), (int) (sensor_color.blue() * SCALE_FACTOR), hsvValues);

				front_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
				front_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
				back_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
				back_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

				front_left.setPower(0.5);
				front_right.setPower(0.5);
				back_left.setPower(0.5);
				back_right.setPower(0.5);

				if(hsvValues[0] > 344 || hsvValues[0] < 16 && hsvValues[1] > 0.49) { //if the color sensor sees hue values of red and the saturation is above 0.49, stop
					telemetry.addData("Red Line Seen: ", "true");
					telemetry.update();
					break;
				}
			}

			//or use the rgb values:
			/* 
			
			    //turn on motors
			    telemetry.addData("red: ", sensor_color.red()/100);
			    telemetry.addData("green: ", sensor_color.green()/100);
			    telemetry.addData("blue: ", sensor_color.blue()/100);
			    telemetry.update();
			    if(sensor_color.red()/100 - sensor_color.green()/100 >=3){
			        telemetry.addData("Blue is seen: ", "true");
			        //turn off motors
			        break;
			    }
			}
			*/

			front_left.setPower(0);
			front_right.setPower(0);
			back_left.setPower(0);
			back_right.setPower(0);
		} //end colorsensordrivebackred method

	void colorsensordrivebackblue() {
		// hsvValues is an array that will hold the hue, saturation, and value information.
		float hsvValues[] = {0F, 0F, 0F};

		// values is a reference to the hsvValues array.
		final float values[] = hsvValues;

		// sometimes it helps to multiply the raw RGB values with a scale factor
		// to amplify/attentuate the measured values.
		final double SCALE_FACTOR = 255;

		// convert the RGB values to HSV values.
		// multiply by the SCALE_FACTOR.
		// then cast it back to int (SCALE_FACTOR is a double)

		Color.RGBToHSV((int) (sensor_color.red() * SCALE_FACTOR), (int) (sensor_color.green() * SCALE_FACTOR), (int) (sensor_color.blue() * SCALE_FACTOR), hsvValues);
		// send the info back to driver station using telemetry function.
		telemetry.addData("Alpha", sensor_color.alpha());
		telemetry.addData("Red  ", sensor_color.red());
		telemetry.addData("Green", sensor_color.green());
		telemetry.addData("Blue ", sensor_color.blue());
		telemetry.addData("Hue", hsvValues[0]);
		telemetry.addData("Saturation", hsvValues[1]);
		telemetry.addData("Value", hsvValues[2]);
		telemetry.addData("colorsensordrivebackblue, running: ", "true");
		telemetry.update();

		//for red require a value from 345-360 or 0 to 15
		while(hsvValues[0] < 344 && hsvValues[0] > 15 && opModeIsActive()) { //run while the color sensor is not seeing red

			Color.RGBToHSV((int) (sensor_color.red() * SCALE_FACTOR), (int) (sensor_color.green() * SCALE_FACTOR), (int) (sensor_color.blue() * SCALE_FACTOR), hsvValues);

			front_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
			front_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
			back_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
			back_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

			front_left.setPower(-0.5);
			front_right.setPower(-0.5);
			back_left.setPower(-0.5);
			back_right.setPower(-0.5);

			if(hsvValues[0] > 225 && hsvValues[0] < 255 && hsvValues[1] > 0.49) { //if the color sensor sees hue values of blue and the saturation is above 0.49, stop
				telemetry.addData("Blue Line Seen: ", "true");
				telemetry.update();
				break;
			}
		}

					//or use the rgb values:
		/* 
		
		    //turn on motors
		    telemetry.addData("red: ", sensor_color.red()/100);
		    telemetry.addData("green: ", sensor_color.green()/100);
		    telemetry.addData("blue: ", sensor_color.blue()/100);
		    telemetry.update();
		    if(sensor_color.red()/100 - sensor_color.green()/100 >=3){
		        telemetry.addData("Blue is seen: ", "true");
		        //turn off motors
		        break;
		    }
		}
		*/

		front_left.setPower(0);
		front_right.setPower(0);
		back_left.setPower(0);
		back_right.setPower(0);

	} //end colorsensordrivebackblue method



	//start autonomous program methods

	void leftblockautonomous() { //this code will not run until called
		telemetry.addData("Skystone Seen: ", "left");
		telemetry.update();
		if(opModeIsActive()) {
			turntableunfolded();
		}
		if(opModeIsActive()) {
			clawopen();
		}
		if(opModeIsActive()) {
			forward1foot();
		}
		if(opModeIsActive()) {
			left4inches();
		}
		if(opModeIsActive()) {
			forward4inches();
		}
		if(opModeIsActive()) {
			clawclosed();
		}
		if(opModeIsActive()) {
			backward4inches();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			liftup();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			liftstop();
		}
		if(opModeIsActive()) {
			forward4inches();
		}
		if(opModeIsActive()) {
			clawopen();
		}
		if(opModeIsActive()) {
			backward4inches();
		}
		if(opModeIsActive()) {
			liftdown();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			liftstop();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			left4inches();
		}
		if(opModeIsActive()) {
			forward4inches();
		}
		if(opModeIsActive()) {
			clawclosed();
		}
		if(opModeIsActive()) {
			backward4inches();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			liftup();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			liftstop();
		}
		if(opModeIsActive()) {
			clawopen();
		}
		if(opModeIsActive()) {
			backward4inches();
		}
		if(opModeIsActive()) {
			spin90right();
		}
		if(opModeIsActive()) {
			spin90right();
		}
		if(opModeIsActive()) {
			backward4inches();
		}
		if(opModeIsActive()) {
			left_slide_down();
		}
		if(opModeIsActive()) {
			spin90right();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			spin90right();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			left_slide_up();
		}
		if(opModeIsActive()) {
			colorsensordrivebackred();
		}
		if(opModeIsActive()) {
			right1foot();
		}
	} //end leftblockautonomous method


	void middleblockautonomous() { //this code will not run until called
		telemetry.addData("Skystone Seen: ", "middle");
		telemetry.update();
		if(opModeIsActive()) {
			turntableunfolded();
		}
		if(opModeIsActive()) {
			clawopen();
		}
		if(opModeIsActive()) {
			forward1foot();
		}
		if(opModeIsActive()) {
			forward4inches();
		}
		if(opModeIsActive()) {
			clawclosed();
		}
		if(opModeIsActive()) {
			backward4inches();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			liftup();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			liftstop();
		}
		if(opModeIsActive()) {
			right4inches();
		}
		if(opModeIsActive()) {
			forward4inches();
		}
		if(opModeIsActive()) {
			clawopen();
		}
		if(opModeIsActive()) {
			backward4inches();
		}
		if(opModeIsActive()) {
			liftdown();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			liftstop();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			left4inches();
		}
		if(opModeIsActive()) {
			forward4inches();
		}
		if(opModeIsActive()) {
			clawclosed();
		}
		if(opModeIsActive()) {
			backward4inches();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			liftup();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			liftstop();
		}
		if(opModeIsActive()) {
			right4inches();
		}
		if(opModeIsActive()) {
			clawopen();
		}
		if(opModeIsActive()) {
			left4inches();
		}
		if(opModeIsActive()) {
			backward4inches();
		}
		if(opModeIsActive()) {
			spin90right();
		}
		if(opModeIsActive()) {
			spin90right();
		}
		if(opModeIsActive()) {
			backward4inches();
		}
		if(opModeIsActive()) {
			left_slide_down();
		}
		if(opModeIsActive()) {
			spin90right();
		}
		if(opModeIsActive()) {
			spin90right();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			left_slide_up();
		}
		if(opModeIsActive()) {
			colorsensordrivebackred();
		}
		if(opModeIsActive()) {
			right1foot();
		}
	} //end middleblockautonomous method

	void rightblockautonomous() { //this code will not run until called
		telemetry.addData("Skystone Seen: ", "right");
		telemetry.update();
		if(opModeIsActive()) {
			turntableunfolded();
		}
		if(opModeIsActive()) {
			clawopen();
		}
		if(opModeIsActive()) {
			forward1foot();
		}
		if(opModeIsActive()) {
			right4inches();
		}
		if(opModeIsActive()) {
			forward4inches();
		}
		if(opModeIsActive()) {
			clawclosed();
		}
		if(opModeIsActive()) {
			backward4inches();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			liftup();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			liftstop();
		}
		if(opModeIsActive()) {
			forward4inches();
		}
		if(opModeIsActive()) {
			clawopen();
		}
		if(opModeIsActive()) {
			backward4inches();
		}
		if(opModeIsActive()) {
			liftdown();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			liftstop();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			forward4inches();
		}
		if(opModeIsActive()) {
			clawclosed();
		}
		if(opModeIsActive()) {
			backward4inches();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			liftup();
		}
		if(opModeIsActive()) {
			right1foot();
		}
		if(opModeIsActive()) {
			liftstop();
		}
		if(opModeIsActive()) {
			clawopen();
		}
		if(opModeIsActive()) {
			left4inches();
		}
		if(opModeIsActive()) {
			backward4inches();
		}
		if(opModeIsActive()) {
			spin90right();
		}
		if(opModeIsActive()) {
			spin90right();
		}
		if(opModeIsActive()) {
			backward4inches();
		}
		if(opModeIsActive()) {
			left_slide_down();
		}
		if(opModeIsActive()) {
			spin90right();
		}
		if(opModeIsActive()) {
			spin90right();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			left1foot();
		}
		if(opModeIsActive()) {
			left_slide_up();
		}
		if(opModeIsActive()) {
			colorsensordrivebackred();
		}
		if(opModeIsActive()) {
			right1foot();
		}
	} //end rightblockautonomous method


	//start actual program
	@Override
	public void runOpMode() {

		int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
		webcam = new OpenCvWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

		front_left = hardwareMap.get(DcMotor.class, "front_left");
		front_right = hardwareMap.get(DcMotor.class, "front_right");
		back_left = hardwareMap.get(DcMotor.class, "back_left");
		back_right = hardwareMap.get(DcMotor.class, "back_right");
		cow = hardwareMap.get(DcMotor.class, "cow");
		pull_down = hardwareMap.get(DcMotor.class, "pull_down");
		turntable = hardwareMap.get(DcMotor.class, "turntable");
		claw = hardwareMap.get(Servo.class, "claw");
		backclaw = hardwareMap.get(Servo.class, "backclaw");
		left_slide = hardwareMap.get(Servo.class, "left_slide");
		right_slide = hardwareMap.get(Servo.class, "right_slide");

		sensor_color = hardwareMap.get(ColorSensor.class, "color");

		front_left.setDirection(DcMotor.Direction.REVERSE);
		back_left.setDirection(DcMotor.Direction.REVERSE);
		front_right.setDirection(DcMotor.Direction.FORWARD);
		back_right.setDirection(DcMotor.Direction.FORWARD);
		cow.setDirection(DcMotor.Direction.FORWARD);
		pull_down.setDirection(DcMotor.Direction.FORWARD);
		turntable.setDirection(DcMotor.Direction.FORWARD);

		turntable.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		cow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		pull_down.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		front_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		front_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		back_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		back_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		cow.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		turntable.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		pull_down.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

		telemetry.setAutoClear(true);

		/*
		 * Open the connection to the camera device
		 */
		webcam.openCameraDevice();

		/*
		 * Specify the image processing pipeline we wish to invoke upon receipt
		 * of a frame from the camera. Note that switching pipelines on-the-fly
		 * (while a streaming session is in flight) *IS* supported.
		 */
		skyStoneDetector = new SkystoneDetector();
		webcam.setPipeline(skyStoneDetector);

		/*
		 * Tell the camera to start streaming images to us! Note that you must make sure
		 * the resolution you specify is supported by the camera. If it is not, an exception
		 * will be thrown.
		 *
		 * Also, we specify the rotation that the camera is used in. This is so that the image
		 * from the camera sensor can be rotated such that it is always displayed with the image upright.
		 * For a front facing camera, rotation is defined assuming the user is looking at the screen.
		 * For a rear facing camera or a webcam, rotation is defined assuming the camera is facing
		 * away from the user.
		 */
		webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

		/*
		 * Send some stats to the telemetry
		 */
		telemetry.addData("Stone Position X", skyStoneDetector.getScreenPosition().x);
		telemetry.addData("Stone Position Y", skyStoneDetector.getScreenPosition().y);
		telemetry.addData("Frame Count", webcam.getFrameCount());
		telemetry.addData("FPS", String.format(Locale.US, "%.2f", webcam.getFps()));
		telemetry.addData("Total frame time ms", webcam.getTotalFrameTimeMs());
		telemetry.addData("Pipeline time ms", webcam.getPipelineTimeMs());
		telemetry.addData("Overhead time ms", webcam.getOverheadTimeMs());
		telemetry.addData("Theoretical max FPS", webcam.getCurrentPipelineMaxFps());
		telemetry.update();

		/*
		 * NOTE: stopping the stream from the camera early (before the end of the OpMode
		 * when it will be automatically stopped for you) *IS* supported. The "if" statement
		 * below will stop streaming from the camera when the "A" button on gamepad 1 is pressed.
		 */
		if(skyStoneDetector.getScreenPosition().x < 60 && opModeIsActive()) {
			if(skyStoneDetector.getScreenPosition().x < 60 && opModeIsActive()) {
				telemetry.addData("Skystone Position: ", "left");
				telemetry.update();
			}
			if(skyStoneDetector.getScreenPosition().x < 60 && opModeIsActive()) {
				telemetry.addData("Skystone Position: ", "left");
				telemetry.update();
			}
			if(skyStoneDetector.getScreenPosition().x < 60 && opModeIsActive()) {
				telemetry.addData("Skystone Position: ", "left");
				telemetry.update();
			}
			if(skyStoneDetector.getScreenPosition().x < 60 && opModeIsActive()) {
				telemetry.addData("Skystone Position: ", "left");
				telemetry.update();
			}
			if(skyStoneDetector.getScreenPosition().x < 60 && opModeIsActive()) {
				telemetry.addData("Skystone Position: ", "left");
				telemetry.addData("Skystone Left:", "chosen");
				telemetry.update();
				//drive forward and then left to grab the third block on the left, then continue autonomous
				webcam.closeCameraDevice();
				//Wait for the user to press start on the Driver Station
				waitForStart();
				leftblockautonomous(); //call method to grab the right skystones
			}
		}else if(skyStoneDetector.getScreenPosition().x > 59 && skyStoneDetector.getScreenPosition().x < 150 && opModeIsActive()) {
			if(skyStoneDetector.getScreenPosition().x > 59 && skyStoneDetector.getScreenPosition().x < 150 && opModeIsActive()) {
				telemetry.addData("Skystone Position: ", "center");
				telemetry.update();
			}
			if(skyStoneDetector.getScreenPosition().x > 59 && skyStoneDetector.getScreenPosition().x < 150 && opModeIsActive()) {
				telemetry.addData("Skystone Position: ", "center");
				telemetry.update();
			}
			if(skyStoneDetector.getScreenPosition().x > 59 && skyStoneDetector.getScreenPosition().x < 150 && opModeIsActive()) {
				telemetry.addData("Skystone Position: ", "center");
				telemetry.update();
			}
			if(skyStoneDetector.getScreenPosition().x > 59 && skyStoneDetector.getScreenPosition().x < 150 && opModeIsActive()) {
				telemetry.addData("Skystone Position: ", "center");
				telemetry.update();
			}
			if(skyStoneDetector.getScreenPosition().x > 59 && skyStoneDetector.getScreenPosition().x < 150 && opModeIsActive()) {
				telemetry.addData("Skystone Position: ", "center");
				telemetry.addData("Skystone Center:", "chosen");
				telemetry.update();
				//drive forward and then left to grab the center block, then continue autonomous
				webcam.closeCameraDevice();
				//Wait for the user to press start on the Driver Station
				waitForStart();
				middleblockautonomous(); //call method to grab the right skystones
			}
		}else if(skyStoneDetector.getScreenPosition().x > 149 && opModeIsActive()) {
			if(skyStoneDetector.getScreenPosition().x > 149 && opModeIsActive()) {
				telemetry.addData("Skystone Position: ", "right");
				telemetry.update();
			}
			if(skyStoneDetector.getScreenPosition().x > 149 && opModeIsActive()) {
				telemetry.addData("Skystone Position: ", "right");
				telemetry.update();
			}
			if(skyStoneDetector.getScreenPosition().x > 149 && opModeIsActive()) {
				telemetry.addData("Skystone Position: ", "right");
				telemetry.update();
			}
			if(skyStoneDetector.getScreenPosition().x > 149 && opModeIsActive()) {
				telemetry.addData("Skystone Position: ", "right");
				telemetry.update();
			}
			if(skyStoneDetector.getScreenPosition().x > 149 && opModeIsActive()) {
				telemetry.addData("Skystone Position: ", "right");
				telemetry.addData("Skystone Right:", "chosen");
				telemetry.update();
				//drive forward and then left to grab the first block on the right, then continue autonomous
				webcam.closeCameraDevice();
				//Wait for the user to press start on the Driver Station
				waitForStart();
				rightblockautonomous(); //call method to grab the right skystones
			}
		}

		stopdrivemotors();
		liftstop();
	} //end public void run OpMode

	/*
	if(gamepad1.a)
	{
		 * IMPORTANT NOTE: calling stopStreaming() will indeed stop the stream of images
		 * from the camera (and, by extension, stop calling your vision pipeline). HOWEVER,
		 * if the reason you wish to stop the stream early is to switch use of the camera
		 * over to, say, Vuforia or TFOD, you will also need to call closeCameraDevice()
		 * (commented out below), because according to the Android Camera API documentation:
		 *         "Your application should only have one Camera object active at a time for
		 *          a particular hardware camera."
		 *
		 * NB: calling closeCameraDevice() will internally call stopStreaming() if applicable,
		 * but it doesn't hurt to call it anyway, if for no other reason than clarity.
		 *
		 * NB2: if you are stopping the camera stream to simply save some processing power
		 * (or battery power) for a short while when you do not need your vision pipeline,
		 * it is recommended to NOT call closeCameraDevice() as you will then need to re-open
		 * it the next time you wish to activate your vision pipeline, which can take a bit of
		 * time. Of course, this comment is irrelevant in light of the use case described in
		 * the above "important note".
		webcam.stopStreaming();
		//webcam.closeCameraDevice();
	}
	 * The viewport (if one was specified in the constructor) can also be dynamically "paused"
	 * and "resumed". The primary use case of this is to reduce CPU, memory, and power load
	 * when you need your vision pipeline running, but do not require a live preview on the
	 * robot controller screen. For instance, this could be useful if you wish to see the live
	 * camera preview as you are initializing your robot, but you no longer require the live
	 * preview after you have finished your initialization process; pausing the viewport does
	 * not stop running your pipeline.
	 *
	 * The "if" statements below will pause the viewport if the "X" button on gamepad1 is pressed,
	 * and resume the viewport if the "Y" button on gamepad1 is pressed.
	else if(gamepad1.x) {
		webcam.pauseViewport();
	}
	else if(gamepad1.y) {
		webcam.resumeViewport();
	}*/

} //end class