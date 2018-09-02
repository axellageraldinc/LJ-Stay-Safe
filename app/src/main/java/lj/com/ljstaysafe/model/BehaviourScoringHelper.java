package lj.com.ljstaysafe.model;

public class BehaviourScoringHelper {

    // Distracted
    public static double INITIAL_DISTRACTED_SCORE = 10.0;
    public static double CURRENT_DISTRACTED_SCORE = 10.0;
    public static double DISTRACTED__MINUS_SCORE_PER_100MS_CONSTANT = 0.1;
    public static double DISTRACTED_X_FLOOR_CONSTRAINT = -0.9;
    public static double DISTRACTED_X_CEILING_CONSTRAINT = -0.2;
    public static double DISTRACTED_Y_FLOOR_CONSTRAINT = 0.6;
    public static double DISTRACTED_Y_CEILING_CONSTRAINT = 9.1;
    public static double DISTRACTED_Z_FLOOR_CONSTRAINT = 3.9;
    public static double DISTRACTED_Z_CEILING_CONSTRAINT = 9.82;

    // Overall
    public static double INITIAL_OVERALL_SCORE = 10.0;
    public static double CURRENT_OVERALL_SCORE = 10.0;
}
