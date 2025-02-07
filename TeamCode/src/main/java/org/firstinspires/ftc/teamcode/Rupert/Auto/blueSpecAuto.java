package org.firstinspires.ftc.teamcode.Rupert.Auto;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import org.firstinspires.ftc.teamcode.Rupert.MecanumDrive;
import org.firstinspires.ftc.teamcode.Rupert.Rupert;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;

import java.util.Vector;

@Autonomous(name="Blue Spec Auto", group = "Autonomous")

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
            waitForStart();
            Action specAuto = drive.actionBuilder(new Pose2d(0, 0, 0))
                    .afterTime(0, (t)->
                    {rup.ScoringSpecimens();
                        return false;})
                    .strafeToLinearHeading(new Vector2d(-30, 0), Math.toRadians(0))
                    .afterTime(.5,(t)->
                    {rup.DroppingSpecimens();
                        return false;})
                    .afterTime(1.3, (t)->
                    {
                        rup.StartingPosition();
                        return false;
                    })
                    .splineToConstantHeading(new Vector2d(-15, 12), Math.toRadians(0))
                    .splineToConstantHeading(new Vector2d(-15, 29), Math.toRadians(0))
                    .splineToConstantHeading(new Vector2d(-45, 29), Math.toRadians(0))
                    .afterTime(0, (t)->
                    {
                        rup.delay(.2);
                        return false;
                    })
                    .splineToConstantHeading(new Vector2d(-45, 41), Math.toRadians(0))
                    .afterTime(0, (t)->
                    {
                        rup.delay(.5);
                        return false;
                    })

                    .splineToConstantHeading(new Vector2d(-5, 41), Math.toRadians(0))
                    .splineToConstantHeading(new Vector2d(-45, 40), Math.toRadians(0))
                    .afterTime(0, (t)->
                    {
                        rup.delay(.2);
                        return false;
                    })
                    .splineToConstantHeading(new Vector2d(-45, 45), Math.toRadians(0))
                    .afterTime(0, (t)->
                    {
                        rup.delay(.5);
                        return false;
                    })
                    .splineToConstantHeading(new Vector2d(-5, 45), Math.toRadians(0))
                    .build();
            while(opModeIsActive()){
                Actions.runBlocking(specAuto);
                rup.delay(4);
                break;



            }
        }

    }
}
