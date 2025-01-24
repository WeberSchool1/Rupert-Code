package org.firstinspires.ftc.teamcode.Rupert;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Objects;

public class Rupert {
    // Motors
    private DcMotor intakeSlides;
    private DcMotor outtakeSlides;
    private DcMotor specimenSlides;

    // Servos
    private Servo specimenRotate;
    private Servo intakeClaw, intakeDiffLeft, intakeDiffRight, intakeRotate;
    private Servo outtakeClaw, outtakeRotate, outtakev4b;

    // Sensors
    private TouchSensor subSensorLeft, subSensorRight;
    private OpticalDistanceSensor frontLeftCorrector, frontRightCorrector, backLeftCorrector, backRightCorrector;
    private ColorSensor outtakeSensor;
    private String color;

    // Variables
    private HuskyLens huskyLens;
    HuskyLens.Block[] samples;
    private MecanumDrive drive;
    private Pose2d pose;
    //numbers
    private double intakeClawPos;
    private double intakeDiffPos;
    private double intakeRotationPos;
    private double outtakeClawPos;
    private double outtakeRotatePos;
    private double specimenRotatePos;

    public Rupert(HardwareMap hwMap) {
        // Initialize Pose2d
        this.pose = new Pose2d(0, 0, 0);

        // Initialize Mecanum Drive
        this.drive = new MecanumDrive(hwMap, pose);
        huskyLens = hwMap.get(HuskyLens.class, "huskyLens");
        huskyLens.selectAlgorithm(HuskyLens.Algorithm.OBJECT_TRACKING);

        // Motors
        intakeSlides = hwMap.dcMotor.get("intakeSlides");
        intakeSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
     

        outtakeSlides = hwMap.dcMotor.get("outtakeSlides");
        outtakeSlides.setTargetPosition(0);
        outtakeSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeSlides.setPower(1);

        specimenSlides = hwMap.dcMotor.get("specimenArm");
        specimenSlides.setTargetPosition(0);
        specimenSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        specimenSlides.setDirection(DcMotorSimple.Direction.REVERSE);
        specimenSlides.setPower(1);

        // Servos
        //Specimen
        specimenRotate = hwMap.servo.get("specimenRotate");
        specimenRotate.setPosition(specimenRotatePos);
        //intake
        intakeClaw = hwMap.servo.get("intakeGrabber");
        intakeClaw.setPosition(intakeClawPos);

        intakeDiffLeft = hwMap.servo.get("intakeLeftDiff");
        intakeDiffLeft.setPosition(intakeDiffPos);

        intakeDiffRight = hwMap.servo.get("intakeRightDiff");
        intakeDiffRight.setPosition(intakeDiffPos);

        intakeRotate = hwMap.servo.get("intakeV4b");
        intakeRotate.setPosition(intakeRotationPos);
        //outtake
        outtakeClaw = hwMap.servo.get("outakeClaw");
        outtakeClaw.setPosition(outtakeClawPos);

        outtakeRotate = hwMap.servo.get("outakeRotate");
        outtakeRotate.setPosition(outtakeRotatePos);

        outtakev4b= hwMap.servo.get("outakeV4b");
        outtakev4b.setPosition(outtakeRotatePos);

        // Touch Sensors
        subSensorLeft = hwMap.touchSensor.get("subSensorLeft");
        subSensorRight = hwMap.touchSensor.get("subSensorRight");

        // Color Sensor
        outtakeSensor = hwMap.colorSensor.get("outtakeSensor");
    }

    // Setter Methods

    //Specimen
    public void setSpecimenSlidesPos(int position) {
        specimenSlides.setTargetPosition(position);

    }
    public void setintakeSlidesPower(double power) {
        intakeSlides.setPower(power);
    }
    public void setSpecimenRotatePos(double position) {
        specimenRotate.setPosition(position);

    }
    //intake
    //intake Claw
    public void setIntakeClawPos(double position)
    {
        intakeClaw.setPosition(position);
    }
    //intakeDiff
    public void setIntakeDiffLeftPos(double position){
        intakeDiffLeft.setPosition(position);
    }
    public void setIntakeDiffRightPos(double position){
        intakeDiffRight.setPosition(position);
    }
    //intakeRotate
    public void setIntakeRotatePos(double position){
        intakeRotate.setPosition(position);
    }
    //outake
    //OuttakeSlides
    public void setOuttakeSlidesPos(int position)
    {
        outtakeSlides.setTargetPosition(position);
    }
    //outakeClaw
    public void setOuttakeClawPos(double position)
    {
        outtakeClaw.setPosition(position);
    }
    //outakeRotate
    public void setOuttakeRotatePos(double position){ outtakeRotate.setPosition(position);}
    public void setOuttakev4bPos(double position){ outtakev4b.setPosition(position);}
    public void SampleDetect(int Color)
    {
    }
    // Color Sensor Logic
    public String getColor() {
        int red = outtakeSensor.red();
        int green = outtakeSensor.green();
        int blue = outtakeSensor.blue();

        if (red > green && red > blue) {
            return "red";
        } else if (blue > red && blue > green) {
            return "blue";
        } else if (red + green > blue) {
            return "yellow";
        } else {
            return "null";
        }
    }
    public void Color(String Color)
    {
      color=Color;
    }
    //Intake Classes
    public void StartingPosition()
    {
        setSpecimenSlidesPos(0);
        setSpecimenRotatePos(0);
        setIntakeClawPos(.4);
        setIntakeDiffRightPos(1);
        setIntakeDiffLeftPos(0);
        setIntakeRotatePos(1);
        setOuttakeClawPos(0);
        setOuttakeRotatePos(0);
        setOuttakev4bPos(0);
    }
    public void GrabbingSamples90(){
        setIntakeRotatePos(.3);
        setIntakeClawPos(0);
        setIntakeDiffLeftPos(.8);
        setIntakeDiffRightPos(0);
    }
    public void GrabbingSamples180()
    {
        setIntakeRotatePos(.3);
        setIntakeClawPos(0);
        delay(.4);
        setIntakeDiffRightPos(.3);
        setIntakeDiffLeftPos(1);
    }
    public void GrabbingSamples()
    {
        setIntakeRotatePos(.1);
        delay(.4);
        setIntakeClawPos(.4);
    }
    public void ScoringSamples()
    {
        setOuttakeClawPos(.4);
        delay(.1);
        setIntakeClawPos(0);
        delay(.3);
        setOuttakeSlidesPos(1800);
        delay(.5);
        setOuttakeRotatePos(1);
        setOuttakev4bPos(1);
    }
    public void DroppingSamples()
    {
        setOuttakeClawPos(0);
        delay(.3);
        setOuttakeRotatePos(0);
        setOuttakev4bPos(0);
        delay(.5);
       setOuttakeSlidesPos(0);
    }
    public void HangingUp()
        {
            setSpecimenRotatePos(0);
            setIntakeClawPos(.4);
            setIntakeDiffRightPos(1);
            setIntakeDiffLeftPos(0);
            setIntakeRotatePos(1);
            setOuttakeClawPos(0);
            setOuttakeRotatePos(0);
            setOuttakev4bPos(0);
            setOuttakeSlidesPos(1500);
            setSpecimenSlidesPos(100);
        }
        public void HangingDown()
        {
            setOuttakeSlidesPos(0);
            setSpecimenSlidesPos(0);
        }

    // Delay Utility
    public void delay(double seconds) {
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while (timer.seconds() < seconds) {
            // Empty loop for delay
        }
    }
}
//Rupert is a god beans
