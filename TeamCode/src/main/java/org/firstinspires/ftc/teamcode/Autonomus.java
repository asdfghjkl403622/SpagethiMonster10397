package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Autonomus Tank", group = "Pushbot")
public class Autonomus extends LinearOpMode {

    static final double COUNTS_PER_INCH = 85.561697504;

    Robot robot = new Robot();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        driveInches(0.25, 4 * Math.PI, 200);
        turnDegrees(0.25, -90, 200);
        driveInches(0.25, 12, 400);



    }

    public void driveInches(double speed, double Inches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Determine new target position, and pass to motor controller
        newLeftTarget = robot.leftDrive.getCurrentPosition() + (int) (Inches * COUNTS_PER_INCH);
        newRightTarget = robot.rightDrive.getCurrentPosition() + (int) (Inches * COUNTS_PER_INCH);
        robot.leftDrive.setTargetPosition(newLeftTarget);
        robot.rightDrive.setTargetPosition(newRightTarget);

        // Turn On RUN_TO_POSITION
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        runtime.reset();
        robot.leftDrive.setPower(Math.abs(speed));
        robot.rightDrive.setPower(Math.abs(speed));


        while (opModeIsActive() &&
                (runtime.seconds() < timeoutS) &&
                (robot.leftDrive.isBusy() && robot.rightDrive.isBusy())) {

            // Display it for the driver.
            telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
            telemetry.addData("Path2", "Running at %7d :%7d",
                    robot.leftDrive.getCurrentPosition(),
                    robot.rightDrive.getCurrentPosition());
            telemetry.update();
        }

        // Stop all motion;
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        // Turn off RUN_TO_POSITION
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }

    public void turnDegrees(double speed, double degrees, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Determine new target position, and pass to motor controller
        newLeftTarget = robot.leftDrive.getCurrentPosition() + (int) (((degrees / 360) * Math.PI * 17.5) * COUNTS_PER_INCH);
        newRightTarget = robot.rightDrive.getCurrentPosition() + (int) (((degrees / 360) * Math.PI * 17.5) * COUNTS_PER_INCH * -1);
        robot.leftDrive.setTargetPosition(newLeftTarget);
        robot.rightDrive.setTargetPosition(newRightTarget);

        // Turn On RUN_TO_POSITION
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        runtime.reset();
        robot.leftDrive.setPower(Math.abs(speed));
        robot.rightDrive.setPower(Math.abs(speed));


        while (opModeIsActive() &&
                (runtime.seconds() < timeoutS) &&
                (robot.leftDrive.isBusy() && robot.rightDrive.isBusy())) {

            // Display it for the driver.
            telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
            telemetry.addData("Path2", "Running at %7d :%7d",
                    robot.leftDrive.getCurrentPosition(),
                    robot.rightDrive.getCurrentPosition());
            telemetry.update();
        }

        // Stop all motion;
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        // Turn off RUN_TO_POSITION
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

}
