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

@Autonomous(name="Sample Auto", group = "Autonomous")

public class SampleAuto extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive;
        Pose2d pose2d;
        Rupert rup;
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
            rup = new Rupert((hardwareMap));
            rup.AutoStartingPos();
            waitForStart();
            Action SampAuto = drive.actionBuilder(new Pose2d(0,0, 0))
                    .afterTime(0, (t)->
                    {
                        rup.AutoScoringSamples();
                        return false;
                    })
                    //Scoring first sample
                    .strafeToLinearHeading(new Vector2d(-24, 26), Math.toRadians(45))
                    .afterTime(0, (t)->
                    {
                        rup.DroppingSamples();
                        rup.GrabbingSamples90();
                        return false;
                    })
                    //Grabbing Second Sample
                    .splineToConstantHeading(new Vector2d(-2.1, 22), Math.toRadians(95))
                   .afterTime(0, (t)->
                    {
                        rup.GrabbingSamples();
                        return false;
                    })

                    .afterTime(1,(t)->
                    {
                        rup.ScoringSamples();
                        return false;
                    })
                    //Scoring Second Sample
                    .splineToConstantHeading(new Vector2d(-22, 16), Math.toRadians(45))
                    .afterTime(0, (t)->{
                rup.DroppingSamples();
                rup.GrabbingSamples90();
                return false;
            })
                    .build();

            while(opModeIsActive())
            {
                Actions.runBlocking(SampAuto);
                rup.delay(3);
                break;
            }
        }}}