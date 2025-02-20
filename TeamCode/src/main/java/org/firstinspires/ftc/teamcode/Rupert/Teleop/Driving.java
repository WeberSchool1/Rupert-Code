package org.firstinspires.ftc.teamcode.Rupert.Teleop;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.Rupert.MecanumDrive;
import org.firstinspires.ftc.teamcode.Rupert.Rupert;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;


@TeleOp(name = "Driving", group = "TeleOP")

public class Driving extends LinearOpMode {
    MecanumDrive drive;
    Pose2d pose2d;
    Rupert rup;

    @Override
    public void runOpMode() throws InterruptedException {
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
            rup = new Rupert(hardwareMap);
            rup.setIntakeRotatePos(1);
            rup.StartingPosition();
            rup.setRgb();
            int colorSearch = 0;
            waitForStart();
            while (opModeIsActive()) {
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(
                                gamepad1.right_stick_y,
                                -gamepad1.right_stick_x),
                        -gamepad1.left_stick_x));
                //Manual Mode
                if (gamepad1.a) {
                    rup.StartingPosition();
                }
                if (gamepad1.b) {
                    rup.GrabbingSamples90();
                }
                if (gamepad1.x) {
                    rup.GrabbingSamples180();
                }
                if (gamepad1.y) {
                    rup.GrabbingSamples();
                }
                if(gamepad1.left_stick_button)
                {
                    rup.ScoringSamples();
                }
                if(gamepad1.right_stick_button)
                {
                    rup.DroppingSamples();

                }
                if (gamepad1.dpad_up) {
                    rup.prepForObservationDrop();
                }
                if (gamepad1.dpad_right) {
                    colorSearch = 1;
                }
                if (gamepad1.dpad_left) {
                    colorSearch = 2;
                }
                if (gamepad1.dpad_down) {
                    colorSearch = 3;
                }

                if (gamepad1.left_bumper) {
                    rup.setintakeSlidesPower(-1);
                } else if (gamepad1.right_bumper) {
                    rup.setintakeSlidesPower(1);
                } else {
                    rup.setintakeSlidesPower(0);
                }
                if (gamepad2.dpad_right) {
                    rup.observationZone();
                }
                if (gamepad2.dpad_up) {
                    rup.ScoringSpecimens();
                }
                    if(gamepad2.b)
                    {
                        rup.ScoringSamples();
                    }

                    if (gamepad2.y) {
                        rup.DroppingSamples();
                    }
                    if (gamepad2.dpad_down) {
                        rup.DroppingSpecimens();
                    }

                    if (gamepad2.right_bumper&&gamepad2.left_bumper) {
                        rup.HangingUp();
                    }


                        if (gamepad2.left_stick_button && gamepad2.right_stick_button) {
                            rup.HangingDown();
                            while (opModeIsActive()) {
                                rup.setintakeSlidesPower(-1);
                                if (gamepad2.options) {
                                    break;
                                }
                            }
                        }
                    rup.setRgb();
                    telemetry.addData("Intake Sample", rup.intakeSensorColor());
                    telemetry.addData("Outtake Sample", rup.outtakeSensorColor());
                    telemetry.addData("Green", rup.intakeSensorGreen());
                    telemetry.addData("Red", rup.intakeSensorRed());
                    telemetry.addData("Blue", rup.intakeSensorBlue());
                    telemetry.update();


            }


        }


    }
}







