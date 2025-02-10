package org.firstinspires.ftc.teamcode.Rupert;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import com.qualcomm.robotcore.util.ElapsedTime;

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
    private Servo rgbLeft, rgbRight;

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
        this.limelight = hwMap.get(Limelight3A.class, "Limelight");

        // Initialize Mecanum Drive
        this.drive = new MecanumDrive(hwMap, pose);
        // Motors
        intakeSlides = hwMap.dcMotor.get("intakeSlides");
        intakeSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        outtakeLeftSlides = hwMap.dcMotor.get("outtakeLeftSlides");
        outtakeLeftSlides.setTargetPosition(0);
        outtakeLeftSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeLeftSlides.setPower(1);

        outtakeRightSlides = hwMap.dcMotor.get("outtakeRightSlides");
        outtakeRightSlides.setTargetPosition(0);
        outtakeRightSlides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        outtakeRightSlides.setDirection(DcMotorSimple.Direction.REVERSE);
        outtakeRightSlides.setPower(1);

        // Servos
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
        outtakeClaw = hwMap.servo.get("outtakeClaw");
        outtakeClaw.setPosition(outtakeClawPos);

        outtakeRotate = hwMap.servo.get("outtakeRotate");
        outtakeRotate.setPosition(outtakeRotatePos);

        outtakev4b= hwMap.servo.get("outtakeV4b");
        outtakev4b.setDirection(Servo.Direction.REVERSE);
        outtakev4b.setPosition(outtakeRotatePos);

        // Touch Sensors
        subSensorLeft = hwMap.touchSensor.get("subSensorLeft");
        subSensorRight = hwMap.touchSensor.get("subSensorRight");

        // Color Sensors
        intakeSensor = hwMap.colorSensor.get("intakeSensor");
        outtakeSensor = hwMap.colorSensor.get("outtakeSensor");
        //RGB Indicators
        rgbLeft = hwMap.servo.get("rgbLeft");
        rgbRight = hwMap.servo.get("rgbRight");

    }
    public int intakeSensorRed()
    {
        return intakeSensor.red();
    }
    public int intakeSensorBlue()
    {
        return intakeSensor.blue();
    }
    public int intakeSensorGreen()
    {
        return intakeSensor.green();
    }
    public void setRgb()
    {
        if (Objects.equals(intakeSensorColor(), "red"))
        {
            rgbLeft.setPosition(.279);
            rgbRight.setPosition(.279);

        }else if(Objects.equals(intakeSensorColor(), "blue"))
        {
            rgbLeft.setPosition(.611);
            rgbRight.setPosition(.611);
        }else if(Objects.equals(intakeSensorColor(), "yellow"))
        {
            rgbLeft.setPosition(.388);
            rgbRight.setPosition(.388);
        }else
        {
            rgbLeft.setPosition(.93);
            rgbRight.setPosition(.93);
        }
    }
    public String intakeSensorColor()
    {

        if (intakeSensor.red() >1000&&intakeSensor.green()<1400&& intakeSensor.blue()<600) {
            return "red";
        }
        else if (intakeSensor.red() > 1200 && intakeSensor.green() > 1500&& intakeSensor.blue()<1500) {
            return "yellow";
        }
        else if (intakeSensor.blue() > 800&& intakeSensor.green()<1500&& intakeSensor.red()<800) {
            return "blue";
        }
        else {
            return "none";
        }
    }
    public String outtakeSensorColor()
    {
        if (outtakeSensor.red() >1000&&outtakeSensor.green()<1200&& outtakeSensor.blue()<600) {
            return "red";
        }
        else if (outtakeSensor.red() > 400 && outtakeSensor.green() > 400&& outtakeSensor.blue()<1500) {
            return "yellow";
        }
        else if (outtakeSensor.blue() > 1000&& outtakeSensor.green()<1200&& outtakeSensor.red()<800) {
            return "blue";
        }
        else {
            return "none";
        }
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
    //Teleop Methods Start
    public Action StartingPosition() {
        setIntakeClawPos(.4);
        setIntakeDiffRightPos(1);
        setIntakeDiffLeftPos(0);
        setIntakeRotatePos(1);
        setOuttakeClawPos(.4);
        setOuttakeRotatePos(.16);
        setOuttakev4bPos(1);
        setOuttakeRightSlidesPos(0);
        setOuttakeSlidesLeftPos(0);
        return null;
    }

    public Action GrabbingSamples90() {
        setIntakeRotatePos(.3);
        setIntakeClawPos(0);
        setIntakeDiffLeftPos(.8);
        setIntakeDiffRightPos(.2);
        return null;
    }

    public Action GrabbingSamples180() {
        setIntakeRotatePos(.3);
        setIntakeClawPos(0);
        delay(.4);
        setIntakeDiffLeftPos(1);
        setIntakeDiffRightPos(.5);
        return null;
    }

    public Action GrabbingSamples() {
        setIntakeRotatePos(.1);
        delay(.2);
        setIntakeClawPos(.4);
        delay(.1);
        StartingPosition();
        return null;
    }
    public Action observationZone()
        {
           setIntakeRotatePos(.1);
           delay(.4);
           setIntakeClawPos(0);
           delay(.4);
           StartingPosition();
           return null;
        }

    public Action ScoringSamples() {
        setOuttakeClawPos(0);
        delay(.2);
        setIntakeClawPos(0);
        delay(.3);
        setOuttakeSlidesLeftPos(2800);
        setOuttakeRightSlidesPos(2800);
        delay(.5);
        setOuttakeRotatePos(.6);
        setOuttakev4bPos(.2);
        return null;
    }

    public Action ScoringSpecimens() {
        setOuttakeClawPos(0);
        delay(.4);
        setIntakeClawPos(0);
        delay(.3);
        setOuttakeSlidesLeftPos(525);
        setOuttakeRightSlidesPos(525);
        delay(.5);
        setOuttakeRotatePos(.86);
        setOuttakev4bPos(.28);
        return null;

    }
    public Action DroppingSpecimens()
    {
        setOuttakev4bPos(.1);
        setOuttakeRotatePos(1);
        delay(.3);
        setOuttakeClawPos(.4);
        setOuttakeSlidesLeftPos(500);
        return null;
    }
    public Action DroppingSamples() {
        setOuttakeClawPos(.4);
        delay(.3);
        setOuttakeClawPos(.4);
        setOuttakeRotatePos(.16);
        setOuttakev4bPos(.9);
        delay(.2);
        setOuttakeSlidesLeftPos(0);
        setOuttakeRightSlidesPos(0);
        return null;
    }

    public Action HangingUp() {
        StartingPosition();
        setOuttakeSlidesLeftPos(1500);
        setOuttakeRightSlidesPos(1500);
        return null;
    }

    public Action HangingDown() {
        setOuttakeSlidesLeftPos(0);
        setOuttakeRightSlidesPos(0);
        return null;
    }
    //TeleopMethodsEnd

    public Action AutoStartingPos()
    {
        setIntakeClawPos(0);
        setIntakeDiffRightPos(1);
        setIntakeDiffLeftPos(0);
        setIntakeRotatePos(1);
        setOuttakeClawPos(0);
        setOuttakeRotatePos(.16);
        setOuttakev4bPos(.7);
        setOuttakeRightSlidesPos(0);
        setOuttakeSlidesLeftPos(0);
        return null;
    }
    public Action AutoObservationDrop()
    {
        setOuttakeRotatePos(.16);
        delay(.2);
        setOuttakeClawPos(.2);
        delay(.2);
        setIntakeClawPos(0);
        setOuttakeRotatePos(1);
        setOuttakev4bPos(0);
        delay(.2);
        setOuttakeClawPos(.4);
        return null;
    }
    public Action AutoScoringSpecimens()
    {
        setOuttakeSlidesLeftPos(525);
        setOuttakeRightSlidesPos(525);
        setOuttakeRotatePos(.84);
        setOuttakev4bPos(.28);
        return null;
    }
    public Action AutoScoringSamples()
    {
        setOuttakeSlidesLeftPos(2800);
        setOuttakeRightSlidesPos(2800);
        delay(2);
        setOuttakeRotatePos(.6);
        setOuttakev4bPos(.2);
        return null;
    }
    public Action prepForObservationDrop()
    {
        setOuttakeRotatePos(.3);
        return null;
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
    public Action delay(double seconds) {
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while (timer.seconds() < seconds) {
        }
        return null;
    }
}