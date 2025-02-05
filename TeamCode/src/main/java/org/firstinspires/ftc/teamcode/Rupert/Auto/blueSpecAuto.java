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
            while(opModeIsActive()){
                rup.ScoringSpecimens();
                Action scoring1stSpec = drive.actionBuilder(new Pose2d(0, 0, 0))
                        .strafeToLinearHeading(new Vector2d(-30, 0), Math.toRadians(0))
                        .build();
                Actions.runBlocking(scoring1stSpec);
                rup.DroppingSpecimens();
                rup.StartingPosition();
                Action grabNextSample1 = drive.actionBuilder(new Pose2d(-30, 0, 180))
                        .strafeToLinearHeading(new Vector2d(-10, 0), Math.toRadians(180))
                        .strafeToLinearHeading(new Vector2d(-9, 43.5), Math.toRadians(180))
                        .build();
                Actions.runBlocking(grabNextSample1);
                rup.GrabbingSamples90();
                rup.GrabbingSamples();
                rup.delay(.8);

                rup.delay(3);


            }
        }

    }
}
