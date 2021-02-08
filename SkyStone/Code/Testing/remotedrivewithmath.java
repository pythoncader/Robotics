package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Mecanum Drive w/ math", group="remote drive")
//@Disabled
public class CadeRemoteDrive extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor front_right = null;
    private DcMotor front_left = null;
    private DcMotor back_right = null;
    private DcMotor back_left = null;

    double vertical;
    double horizontal;
    double turn;

    @Override
    public void runOpMode() {
        //initialize the hardware
        front_left = hardwareMap.get(DcMotor.class, "front_left");
        front_right = hardwareMap.get(DcMotor.class, "front_right");
        back_left = hardwareMap.get(DcMotor.class, "back_left");
        back_right = hardwareMap.get(DcMotor.class, "back_right");

        //set the direction of the motors
        front_left.setDirection(DcMotor.Direction.FORWARD);
        back_left.setDirection(DcMotor.Direction.FORWARD);
        front_right.setDirection(DcMotor.Direction.FORWARD);
        back_right.setDirection(DcMotor.Direction.FORWARD);

        //add the data to the ftc app
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        while(opModeIsActive()) {

            vertical = -gamepad1.right_stick_y;
            horizontal = gamepad1.right_stick_x;
            turn = gamepad1.left_stick_x;

            //add data to the ftc app
            telemetry.addData("vertical: ", vertical);
            telemetry.addData("horizontal: ", horizontal);
            telemetry.addData("turn: ",  turn);
            telemetry.update();

            front_left.setPower(turn + vertical + horizontal);
            front_right.setPower(-turn + vertical - horizontal);
            back_right.setPower(-turn + vertical + horizontal);
            back_left.setPower(turn + vertical - horizontal);
        }
    }
}