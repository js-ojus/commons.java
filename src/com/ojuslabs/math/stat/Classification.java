package com.ojuslabs.math.stat;

/**
 * Provides statistical classification measures.
 */
public final class Classification
{
    /**
     * Not intended for instantiation.
     */
    private Classification() {
        // Intentionally left blank.
    }

    /**
     * Answers the computed sensitivity of the association between two
     * variables. The counts of true positives and false negatives are utilised
     * in this computation.
     * 
     * @param tp
     *            Number of true positives.
     * @param fn
     *            Number of false negatives.
     * @return Sensitivity
     */
    public static double sensitivity(int tp, int fn) {
        return ((double) tp) / (tp + fn);
    }

    /**
     * Answers the computed specificity of the association between two
     * variables. The counts of true negatives and false positives are utilised
     * in this computation.
     * 
     * @param tn
     *            Number of true negatives.
     * @param fp
     *            Number of false positives.
     * @return Specificity
     */
    public static double specificity(int tn, int fp) {
        return ((double) tn) / (fp + tn);
    }

    /**
     * Answers the computed Matthews Correlation Coefficient(MCC) for the
     * contingency table (or confusion matrix) given by the four input values:
     * true positives, false positives, false negatives and true negatives.
     * 
     * @param tp
     *            Number of true positives.
     * @param fp
     *            Number of false positives.
     * @param fn
     *            Number of false negatives.
     * @param tn
     *            Number of true negatives.
     * @return MCC computed for the given contingency table.
     */
    public static double mcc(int tp, int fp, int fn, int tn) {
        int numerator = (tp * tn) - (fp * fn);
        double denominator = Math.sqrt((tp + fp) * (tp + fn) * (tn + fp)
                * (tn + fn));
        return numerator / denominator;
    }
}
