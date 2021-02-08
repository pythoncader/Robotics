package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Stoponblue", group="sensor")
//@Disabled
public class Stoponblue extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor cade_test = null;
    ColorSensor sensor_color;
    @Override
    public void runOpMode() {
        sensor_color = hardwareMap.get(ColorSensor.class, "sensor_color");
        cade_test = hardwareMap.get(DcMotor.class, "cade_test");
        cade_test.setDirection(DcMotor.Direction.FORWARD);
        telemetry.addData("Status", "Initialized");
        waitForStart();
        while(opModeIsActive()) {
            cade_test.setPower(0.5);
            telemetry.addData("red: ", sensor_color.red()/100);
            telemetry.addData("green: ", sensor_color.green()/100);
            telemetry.addData("blue: ", sensor_color.blue()/100);
            telemetry.update();
            if(sensor_color.blue()/100 - sensor_color.green()/100 >=3){
                telemetry.addData("Blue is seen: ", "true");
                cade_test.setPower(0);
                sleep(1000);
            }
        }
    }
}
