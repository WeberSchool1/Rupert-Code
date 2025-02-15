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
@Autonomous(name= "Spec Auto", group= "Autonomous")
public class SpecAuto extends LinearOpMode{
    MecanumDrive drive;
    Pose2d Pose2d;
    Rupert rup;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        rup=new Rupert((hardwareMap));
        if(TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)){
        rup.AutoStartingPos();

            Action Score1 = drive.actionBuilder(new Pose2d(0, 0, 0))
                    .afterTime(0, (t)->
                    {rup.AutoScoringSpecimens();
                        return false;})
                    .strafeToLinearHeading(new Vector2d(30, 0), Math.toRadians(0))
                    .afterTime(0,(t)->
                    {rup.DroppingSpecimens();
                        return false;})
                    .afterTime(.2, (t)->
                    {
                        rup.StartingPosition();
                        return false;
                    })
                    .splineToConstantHeading(new Vector2d(0, 0), Math.toRadians(0))
                    .build();

            waitForStart();
            while(opModeIsActive())
            {
                Actions.runBlocking(Score1);
                drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0,1), 0));
                rup.delay(2);
                break;
            }
        }
    }
}
