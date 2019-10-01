package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Robot {
    /* Public OpMode members. */
    public DcMotor  leftDrive   = null;
    public DcMotor  rightDrive  = null;
    //this identifies the servo motors and allows the phone to identify
    public DcMotor leftSucc = null;
    public DcMotor rightSucc = null;
    public DcMotor Grab = null;
    public static final double COUNTS_PER_INCH = 42.780848752;


    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    private final double intakeSpeed = 1;
    private final double ejectSpeed = -1;
    private final double grabSpeedRelease = 1;
    private final double grabSpeedGrab = -1;
    /* Constructor */
    public Robot(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftDrive  = hwMap.get(DcMotor.class, "left_drive");
        rightDrive = hwMap.get(DcMotor.class, "right_drive");
        //this renames the motors in the hardware map
        leftSucc = hwMap.get(DcMotor.class, "left_succ");
        rightSucc = hwMap.get(DcMotor.class, "right_succ");
        //Define and initialize grab motor
        Grab = hwMap.get(DcMotor.class, "Grab");

        leftDrive.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightDrive.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        //this sets the input of the motors
        leftSucc.setDirection(DcMotor.Direction.FORWARD);
        rightSucc.setDirection(DcMotor.Direction.REVERSE);
        //Sets motor directions for the grab motors
        Grab.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to zero power
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        leftSucc.setPower(0);
        rightSucc.setPower(0);
        Grab.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftSucc.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightSucc.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Define and initialize ALL installed servos.

    }

    public void intakeBlock() {
        leftSucc.setPower(intakeSpeed);
        rightSucc.setPower(intakeSpeed);
    }
    public void ejectBlock() {
        leftSucc.setPower(ejectSpeed);
        rightSucc.setPower(ejectSpeed);
    }
    public void drive(double leftSpeed, double rightSpeed) {
        leftDrive.setPower(leftSpeed);
        rightDrive.setPower(rightSpeed);
    }
    public void driveInches(double distance) {
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDrive.setPower(COUNTS_PER_INCH * distance);
        rightDrive.setPower(COUNTS_PER_INCH * distance);
    }
    public void grabControl(boolean grab, boolean release) {
        if (grab) {
            Grab.setPower(grabSpeedGrab);
        }
        if (release) {
            Grab.setPower(grabSpeedRelease);
        }
    }





}
