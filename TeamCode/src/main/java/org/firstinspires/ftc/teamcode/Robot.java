package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Robot {
    /* Public OpMode members. */
    public DcMotor  leftDrive   = null;
    public DcMotor  rightDrive  = null;
    //this identifies the servo motors and allows the phone to identify
    public DcMotor leftIntake = null;
    public DcMotor rightIntake = null;
    public Servo foundationLeft = null;
    public Servo foundationRight = null;
    public CRServo slide = null;
    public DcMotor winch = null;
    public Servo claw = null;
    public static final double COUNTS_PER_INCH = 42.780848752;


    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    private final double intakeSpeed = 1;
    private final double ejectSpeed = -1;
    private final double grabSpeedRelease = 1;
    private final double grabSpeedGrab = -1;
    private final double platformGrabSpeed = 1;
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
        leftIntake = hwMap.get(DcMotor.class, "left_intake");
        rightIntake = hwMap.get(DcMotor.class, "right_intake");
        //Define and initialize platform grabbing servos
        foundationLeft = hwMap.get(Servo.class, "foundation_Left");
        foundationRight = hwMap.get(Servo.class, "foundation_Right");
        slide = hwMap.get(CRServo.class, "slide");
        winch = hwMap.get(DcMotor.class, "winch");
        claw = hwMap.get(Servo.class, "claw");


        leftDrive.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightDrive.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
        //this sets the input of the motors
        leftIntake.setDirection(DcMotor.Direction.FORWARD);
        rightIntake.setDirection(DcMotor.Direction.REVERSE);
        //set platform grab motor directions
        foundationLeft.setDirection(Servo.Direction.FORWARD);
        slide.setDirection(CRServo.Direction.FORWARD);
        winch.setDirection(DcMotor.Direction.FORWARD);
        claw.setDirection(Servo.Direction.FORWARD);


        // Set all motors to zero power
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        leftIntake.setPower(0);
        rightIntake.setPower(0);
        winch.setPower(0);



        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        winch.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);




        // Define and initialize ALL installed servos.

    }

    public void intakeBlock() {
        leftIntake.setPower(intakeSpeed);
        rightIntake.setPower(intakeSpeed);
    }
    public void ejectBlock() {
        leftIntake.setPower(ejectSpeed);
        rightIntake.setPower(ejectSpeed);
    }
    public void stopIntake() {
        leftIntake.setPower(0);
        rightIntake.setPower(0);
    }
    public void drive(double leftSpeed, double rightSpeed) {
        leftDrive.setPower(leftSpeed);
        rightDrive.setPower(rightSpeed);
    }


    }







