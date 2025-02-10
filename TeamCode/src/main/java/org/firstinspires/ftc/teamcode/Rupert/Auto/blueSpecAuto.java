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
import org.firstinspires.ftc.teamcode.Rupert.MecanumDrive;
import org.firstinspires.ftc.teamcode.Rupert.Rupert;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;

import java.util.Vector;

@Autonomous(name="Spec Auto", group = "Autonomous")

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

            Action specAuto = drive.actionBuilder(new Pose2d(0, 0, 0))
                    .afterTime(0, (t)->
                    {rup.AutoScoringSpecimens();
                        return false;})
                    .strafeToLinearHeading(new Vector2d(-25, 0), Math.toRadians(0))
                    .afterTime(0,(t)->
                    {rup.DroppingSpecimens();
                        return false;})

                    .splineToConstantHeading(new Vector2d(-15, 29), Math.toRadians(0))
                    .afterTime(.3, (t)->
                    {
                        rup.StartingPosition();
                        return false;
                    })
                    .splineToConstantHeading(new Vector2d(-40, 29), Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(-40, 40), Math.toRadians(0))
                    .splineToConstantHeading(new Vector2d(-10, 40), Math.toRadians(0))
                    .splineToConstantHeading(new Vector2d(-40, 40), Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(-40, 53), Math.toRadians(0))
                    .splineToConstantHeading(new Vector2d(-10, 53), Math.toRadians(0))
                    .splineToConstantHeading(new Vector2d(-40, 53), Math.toRadians(0))
                    .strafeToLinearHeading(new Vector2d(-40, 60), Math.toRadians(0))
                    .afterTime(0, (t)->
                    {
                        rup.GrabbingSamples90();
                        rup.prepForObservationDrop();
                        return false;
                    })
                    .splineToConstantHeading(new Vector2d(-8, 60), Math.toRadians(0))
                    .afterTime(.1, (t)->
                    {
                        rup.GrabbingSamples();
                        return false;
                    })
                    .afterTime(.5,(t)->
                            {
                                rup.ScoringSpecimens();
                                return false;
                            }
                    )
                    .strafeToLinearHeading(new Vector2d(-8, 55), Math.toRadians(0))
                    .splineToConstantHeading(new Vector2d(-30, 0), Math.toRadians(0))
                    .build();
            waitForStart();
            while(opModeIsActive()){
                rup.setintakeSlidesPower(-.5);
                Actions.runBlocking(specAuto);
                rup.delay(4);
                break;



            }
        }

    }
}
