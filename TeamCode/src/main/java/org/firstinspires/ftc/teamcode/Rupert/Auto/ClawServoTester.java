package org.firstinspires.ftc.teamcode.Rupert.Auto;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.Rupert.MecanumDrive;
import org.firstinspires.ftc.teamcode.Rupert.Rupert;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;
@TeleOp(name="clawServoTester", group= "TeleOp")
public class ClawServoTester extends LinearOpMode {
    Rupert rup;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.b) {
                rup.setOuttakeClawPos(0);
            }
            if (gamepad1.x) {
                rup.setOuttakeClawPos(.4);
            }
        }


    }
}
