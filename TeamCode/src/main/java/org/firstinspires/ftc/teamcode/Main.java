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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 *
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Teleop Tank", group="Pushbot")

public class Main extends OpMode{

    /* Declare OpMode members. */
    Robot robot = new Robot(); // use the class created to define a Pushbot's hardware

    public Main() {
    }
    // could also use HardwarePushbotMatrix class.
                 // sets rate to move servo

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        boolean intake;
        double eject;
        double left;
        double right;
        boolean foundationServoPickUp;
        boolean foundationServoPutDown;
        boolean winchUp;
        boolean winchDown;
        boolean clawGrab;
        boolean clawRelease;
        boolean slideForward;
        boolean slideBackward;

        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        left = -gamepad1.left_stick_y;
        right = -gamepad1.right_stick_y;
        intake = gamepad2.right_bumper;
        eject = gamepad2.right_trigger;
        foundationServoPickUp = gamepad2.x;
        foundationServoPutDown = gamepad2.y;
        winchUp = gamepad2.dpad_up;
        winchDown = gamepad2.dpad_down;
        clawGrab = gamepad2.a;
        clawRelease = gamepad2.b;
        slideForward = gamepad2.dpad_left;
        slideBackward = gamepad2.dpad_right;

        if (slideForward) {
            robot.slide.setPower(100);
        } else if ((!slideBackward) && (!slideForward)) {
            robot.slide.setPower(0);
        }
        if (slideBackward) {
            robot.slide.setPower(-100);
        } else if ((!slideBackward) && (!slideForward)) {
            robot.slide.setPower(0);
        }
        if (winchUp) {
            robot.winch.setPower(1);
        } else if ((!winchDown) && (!winchUp)) {
            robot.winch.setPower(0);
        }
        if (winchDown) {
            robot.winch.setPower(-1);
        } else if ((!winchDown) && (!winchUp)) {
            robot.winch.setPower(0);
        }
        if (clawGrab) {
            robot.claw.setPosition(1);
        }
        if (clawRelease) {
            robot.claw.setPosition(0.3);
        }
        if (left < 0.5 && left > -0.5) {
            robot.leftDrive.setPower(left / 2);
        } else {
            robot.leftDrive.setPower(left);
        }
        if (right < 0.5 && right > -0.5) {
            robot.rightDrive.setPower(right / 2);
        } else {
            robot.rightDrive.setPower(right);
        }
        if (intake) {
            robot.leftIntake.setPower(1);
            robot.rightIntake.setPower(1);
        } else if (eject > (Math.PI / 30)) {
            robot.rightIntake.setPower(-1);
            robot.leftIntake.setPower(-1);
        }
        else {
            robot.rightIntake.setPower(0);
            robot.leftIntake.setPower(0);
        }

        if (foundationServoPickUp) {
            robot.foundationLeft.setPosition(0.7);
            robot.foundationRight.setPosition(0.7);
        }
        else if (foundationServoPutDown) {
            robot.foundationLeft.setPosition(-0.1);
            robot.foundationRight.setPosition(-0.1);
        }





    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
