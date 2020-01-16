package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;


import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;



import java.util.ArrayList;
import java.util.List;


@Autonomous(name = "Autonomus Blue", group = "Pushbot")
public class Autonomus2 extends LinearOpMode {

    static final double COUNTS_PER_INCH = 85.561697504;
    ElapsedTime timer = new ElapsedTime();
    Robot robot = new Robot();
    private ElapsedTime runtime = new ElapsedTime();
    VuforiaLocalizer vuforia;
    OpenGLMatrix lastLocation = null;
    public static final String TAG = "Vuforia Navigation Sample";

    @Override
    public void runOpMode() throws InterruptedException {
        boolean lastResetState = false;
        boolean curResetState  = false;
        robot.init(hardwareMap);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        robot.intakeBlock();
        driveInches(0.5, -24.25, 200);
        robot.stopIntake();
        driveInches(0.75, 18, 200);
        turnDegrees(0.5, 45, 200);
        driveInches(0.75, 18, 600);
        robot.intakeBlock();
        sleep(3000);
        robot.stopIntake();
        driveInches(0.75, -6, 200);








        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AUdp9w7/////AAABmQrdl1rA/USMqEBXQO9JJz4xFKE6xJYmArNkGCIkzwRTnaULoN5KJZN8IRcQmi5Dmp4dA8xPcNjK1JLtL14tJXpgKrP0OGylaVeCU9oyuLD2jZy8D7lIc5wbmHwHDz0rmqomDn0QJbWKlQNNuT9WoAjgkQyXwHMT/MgvOnf44bqVsSrwyycBedZvMOtnyEATBEdLniSqS2PbhVbAEjAeoXBVRhL0kVEFF1PRNIECCmH1X7jRppoybSpOXhs6KFSIHuILAlh1ahyTG6fB/a8quO77T6NlQK7NeVlDxtzahAk7dkITN57neABwoimKBGm85uWEpk+w237AEzGLr8C74uOOU5+2lTDp84LJx4btB+S/";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        VuforiaTrackables stonesAndChips = this.vuforia.loadTrackablesFromAsset("StonesAndChips");
        VuforiaTrackable redTarget = stonesAndChips.get(0);
        redTarget.setName("RedTarget");  // Stones

        VuforiaTrackable blueTarget  = stonesAndChips.get(1);
        blueTarget.setName("BlueTarget");  // Chips

        //* For convenience, gather together all the trackable objects in one easily-iterable collection
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(stonesAndChips);
        float mmPerInch        = 25.4f;
        float mmBotWidth       = (float) (17.5 * mmPerInch);            // ... or whatever is right for your robot
        float mmFTCFieldWidth  = (12*12 - 2) * mmPerInch;   // the FTC field is ~11'10" center-to-center of the glass panels
        OpenGLMatrix redTargetLocationOnField = OpenGLMatrix
                 /*Then we translate the target off to the RED WALL. Our translation here
                is a negative translation in X.*/
                .translation(-mmFTCFieldWidth/2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                         /*First, in the fixed (field) coordinate system, we rotate 90deg in X, then 90 in Z */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 90, 0));
        redTarget.setLocation(redTargetLocationOnField);
        RobotLog.ii(TAG, "Red Target=%s", format(redTargetLocationOnField));


         /** To place the Stones Target on the Blue Audience wall:
         * - First we rotate it 90 around the field's X axis to flip it upright
         * - Finally, we translate it along the Y axis towards the blue audience wall.*/

        OpenGLMatrix blueTargetLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the Blue Audience wall.
                Our translation here is a positive translation in Y.*/
                .translation(0, mmFTCFieldWidth/2, 0)
                .multiplied(Orientation.getRotationMatrix(
                         /*First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 0, 0));
        blueTarget.setLocation(blueTargetLocationOnField);
        RobotLog.ii(TAG, "Blue Target=%s", format(blueTargetLocationOnField));
        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(mmBotWidth/2,0,0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.YZY,
                        AngleUnit.DEGREES, -90, 0, 0));
        RobotLog.ii(TAG, "phone=%s", format(phoneLocationOnRobot));
        ((VuforiaTrackableDefaultListener)redTarget.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)blueTarget.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        stonesAndChips.activate();
        while (opModeIsActive()) {

            for (VuforiaTrackable trackable : allTrackables) {

         /** getUpdatedRobotLocation() will return null if no new information is available since
         * the last time that call was made, or if the trackable is not currently visible.
         * getRobotLocation() will return null if the trackable is not currently visible.*/

                telemetry.addData(trackable.getName(), ((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible() ? "Visible" : "Not Visible");    //

                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
            }
            /**
         * Provide feedback as to where the robot was last located (if we know).*/

            if (lastLocation != null) {
                //  RobotLog.vv(TAG, "robot=%s", format(lastLocation));
                telemetry.addData("Pos", format(lastLocation));
            } else {
                telemetry.addData("Pos", "Unknown");
            }
            telemetry.update();
        }
    }


    public void driveInches(double speed, double Inches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;
        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
        robot.leftDrive.setPower(-1);
        robot.rightDrive.setPower(-1);
        sleep(10);
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
    String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
    }

}
