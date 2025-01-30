package org.firstinspires.ftc.teamcode.Rupert;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.vision.VisionPortal;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import java.util.Objects;

public class Rupert {
    // Motors
    private DcMotor intakeSlides;
    private DcMotor outtakeLeftSlides;
    private DcMotor outtakeRightSlides;

    // Servos
    private Servo intakeClaw, intakeDiffLeft, intakeDiffRight, intakeRotate;
    private Servo outtakeClaw, outtakeRotate, outtakev4b;

    // Sensors
    private TouchSensor subSensorLeft, subSensorRight;
    private OpticalDistanceSensor frontLeftCorrector, frontRightCorrector, backLeftCorrector, backRightCorrector;
    private ColorSensor outtakeSensor;
    private ColorSensor intakeSensor;
    private String color;

    // Driving
    private MecanumDrive drive;
    private Pose2d pose;
    //Limelight things
    private Limelight3A limelight;
    private LLResult result;
    private LLStatus status;
    private LLResultTypes resultTypes;

    private VisionPortal visionPortal;

    // Numbers
    private double intakeClawPos;
    private double intakeDiffPos;
    private double intakeRotationPos;
    private double outtakeClawPos;
    private double outtakeRotatePos;

    public Rupert(HardwareMap hwMap) {
        // Initialize Pose2d
        this.pose = new Pose2d(0, 0, 0);

        // Initialize Limelight
        this.limelight = hwMap.get(Limelight3A.class, "limelight");

        // Initialize Vision Portal
        CameraName camera = hwMap.get(CameraName.class, "Webcam 1");
        visionPortal = new VisionPortal.Builder()
                .setCamera(camera)
                .build();

        // Initialize Mecanum Drive
        this.drive = new MecanumDrive(hwMap, pose);
    }
    public void setLimelightPipeline(int pipeline) {
        limelight.pipelineSwitch(pipeline);
    }

    public void setintakeSlidesPower(double power) {
        intakeSlides.setPower(power);
    }

    public void setOuttakeRightSlidesPos(int position) {
        outtakeRightSlides.setTargetPosition(position);
    }

    public void setOuttakeSlidesLeftPos(int position) {
        outtakeLeftSlides.setTargetPosition(position);
    }

    public void setIntakeClawPos(double position) {
        intakeClaw.setPosition(position);
    }

    public void setIntakeDiffLeftPos(double position) {
        intakeDiffLeft.setPosition(position);
    }

    public void setIntakeDiffRightPos(double position) {
        intakeDiffRight.setPosition(position);
    }

    public void setIntakeRotatePos(double position) {
        intakeRotate.setPosition(position);
    }

    public void setOuttakeClawPos(double position) {
        outtakeClaw.setPosition(position);
    }

    public void setOuttakeRotatePos(double position) {
        outtakeRotate.setPosition(position);
    }

    public void setOuttakev4bPos(double position) {
        outtakev4b.setPosition(position);
    }

    public void StartingPosition() {
        setIntakeClawPos(.3);
        setIntakeDiffRightPos(1);
        setIntakeDiffLeftPos(0);
        setIntakeRotatePos(1);
        setOuttakeClawPos(.4);
        setOuttakeRotatePos(0);
        setOuttakev4bPos(0);
    }

    public void GrabbingSamples90() {
        setIntakeRotatePos(.3);
        setIntakeClawPos(0);
        setIntakeDiffLeftPos(.8);
        setIntakeDiffRightPos(.2);
    }

    public void GrabbingSamples180() {
        setIntakeRotatePos(.3);
        setIntakeClawPos(0);
        delay(.4);
        setIntakeDiffLeftPos(.4);
        setIntakeDiffRightPos(.6);
    }

    public void GrabbingSamples() {
        setIntakeRotatePos(.1);
        delay(.4);
        setIntakeClawPos(.4);
    }

    public void ScoringSamples() {
        setOuttakeClawPos(.1);
        delay(.2);
        setIntakeClawPos(0);
        delay(.3);
        setOuttakeSlidesLeftPos(2000);
        setOuttakeRightSlidesPos(2000);
        delay(.5);
        setOuttakeRotatePos(1);
        setOuttakev4bPos(1);
    }

    public void DroppingSamples() {
        setOuttakeClawPos(0);
        delay(.3);
        setOuttakeRotatePos(0);
        setOuttakev4bPos(0);
        delay(.2);
        setOuttakeSlidesLeftPos(0);
        setOuttakeRightSlidesPos(0);
    }

    public void HangingUp() {
        setIntakeClawPos(.3);
        setIntakeDiffRightPos(1);
        setIntakeDiffLeftPos(0);
        setIntakeRotatePos(1);
        setOuttakeClawPos(0);
        setOuttakeRotatePos(0);
        setOuttakev4bPos(0);
        setOuttakeSlidesLeftPos(1500);
        setOuttakeRightSlidesPos(1500);
    }

    public void HangingDown() {
        setOuttakeSlidesLeftPos(0);
        setOuttakeRightSlidesPos(0);
        setintakeSlidesPower(-.1);
    }
    public void setLimelight(int pipeline) {
        limelight.pipelineSwitch(pipeline);
    }
    public double getLLresultsX()
    {
        return result.getTx();
    }
    public double getLLresultsY()
    {
        return result.getTy();
    }
    public double getLLresultsArea()
    {
        return result.getTa();
    }
    public void delay(double seconds) {
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while (timer.seconds() < seconds) {
        }
    }
}