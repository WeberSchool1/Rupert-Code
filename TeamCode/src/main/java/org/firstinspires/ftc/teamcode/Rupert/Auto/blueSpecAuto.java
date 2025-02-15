package org.firstinspires.ftc.teamcode.Rupert.Auto;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.MotionDetection;
import org.firstinspires.ftc.teamcode.Rupert.MecanumDrive;
import org.firstinspires.ftc.teamcode.Rupert.Rupert;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;

import java.util.Vector;

@Autonomous(name="Backup Spec Auto", group = "Autonomous")

public class blueSpecAuto extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive;
        Pose2d pose2d;
        Rupert rup;

        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
            rup=new Rupert((hardwareMap));
            rup.AutoStartingPos();

            Action Score1 = drive.actionBuilder(new Pose2d(0, 0, 0))
                    .afterTime(0, (t)->
                    {rup.AutoScoringSpecimens();
                        return false;})
                    .strafeToLinearHeading(new Vector2d(-30, 0), Math.toRadians(0))
                    .afterTime(0,(t)->
                    {rup.DroppingSpecimens();
                        return false;})
                    .afterTime(.2, (t)->
                    {
                        rup.StartingPosition();
                        return false;
                    })
                    .splineToConstantHeading(new Vector2d(-8, 0), Math.toRadians(0))
                    .splineToConstantHeading(new Vector2d(-8, -15.4), Math.toRadians(0))
                    .build();
            Action Push1= drive.actionBuilder(new Pose2d(-43.5, -15.4, 0))
                    .splineToConstantHeading(new Vector2d(-43.5, -18), Math.toRadians(0))
                    .build();
            Action Push2= drive.actionBuilder(new Pose2d(-43.5, -18, 0))
                            .build();
            waitForStart();
            while(opModeIsActive()){
                telemetry.addData("Pos:", drive.pose.position);
                telemetry.update();
                rup.setintakeSlidesPower(-.5);
                Actions.runBlocking(Score1);
                drive.setPoseEstimate(new Pose2d(-8, -10, 0));
                drive.setDrivePowers(new PoseVelocity2d(new Vector2d(-1, 0), 0));
                rup.delay(.65);
                Actions.runBlocking(Push1);
                drive.setDrivePowers(new PoseVelocity2d(new Vector2d(1,0), 0));
                rup.delay(1.4);
                drive.setDrivePowers(new PoseVelocity2d(new Vector2d(-1, 0), 0));
                rup.delay(1);
                Actions.runBlocking(Push2);

                break;



            }
        }

    }
}
