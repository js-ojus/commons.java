package com.ojuslabs.math.stat;

/**
 * Provides statistical distributions.
 */
public final class Distribution
{
    /**
     * Not intended for instantiation.
     */
    private Distribution() {
        // Intentionally left blank.
    }

    /** Chi-square distribution table for the first ten degrees of freedom. */
    public static double[][] ChiSquareTable = {
            { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
            { 0.004, 0.02, 0.06, 0.15, 0.46, 1.07, 1.64, 2.71, 3.84, 6.64, 10.83 },
            { 0.10, 0.21, 0.45, 0.71, 1.39, 2.41, 3.22, 4.60, 5.99, 9.21, 13.82 },
            { 0.35, 0.58, 1.01, 1.42, 2.37, 3.66, 4.64, 6.25, 7.82, 11.34, 16.27 },
            { 0.71, 1.06, 1.65, 2.20, 3.36, 4.88, 5.99, 7.78, 9.49, 13.28, 18.47 },
            { 1.14, 1.61, 2.34, 3.00, 4.35, 6.06, 7.29, 9.24, 11.07, 15.09, 20.52 },
            { 1.63, 2.20, 3.07, 3.83, 5.35, 7.23, 8.56, 10.64, 12.59, 16.81, 22.46 },
            { 2.17, 2.83, 3.82, 4.67, 6.35, 8.38, 9.80, 12.02, 14.07, 18.48, 24.32 },
            { 2.73, 3.49, 4.59, 5.53, 7.34, 9.52, 11.03, 13.36, 15.51, 20.09, 26.12 },
            { 3.32, 4.17, 5.38, 6.39, 8.34, 10.66, 12.24, 14.68, 16.92, 21.67, 27.88 },
            { 3.94, 4.86, 6.18, 7.27, 9.34, 11.78, 13.44, 15.99, 18.31, 23.21, 29.59 }, };

    /** P-value distribution from 5% confidence to 99.9% confidence. */
    public static double[]   PvalueTable    = { 0.95, 0.90, 0.80, 0.70, 0.50, 0.30, 0.20,
            0.10, 0.05, 0.01, 0.001        };

    /**
     * Computes chi-square value and p-value for the given 2x2 contingency
     * table. It also answers a reliability indicator: {@code true} if all
     * expected values are &gt;= 5.0; {@code false} if at least one of the
     * expected values is &lt; 5.0.
     * 
     * @param c11
     *            Frequency value for cell (1,1).
     * @param c12
     *            Frequency value for cell (1,2).
     * @param c21
     *            Frequency value for cell (2,1).
     * @param c22
     *            Frequency value for cell (2,2).
     * @return An instance of {@link ChiSquarePvalue} with the results.
     */
    public static ChiSquarePvalue chiSquare2x2(double c11, double c12, double c21,
            double c22) {
        double rowTotal1 = c11 + c12;
        double rowTotal2 = c21 + c22;
        double colTotal1 = c11 + c21;
        double colTotal2 = c12 + c22;
        double total = rowTotal1 + rowTotal2;

        double c11Exp = rowTotal1 * colTotal1 / total;
        double c12Exp = rowTotal1 * colTotal2 / total;
        double c21Exp = rowTotal2 * colTotal1 / total;
        double c22Exp = rowTotal2 * colTotal2 / total;

        double chiSq = ((c11 - c11Exp) * (c11 - c11Exp) / c11Exp)
                + ((c12 - c12Exp) * (c12 - c12Exp) / c12Exp)
                + ((c21 - c21Exp) * (c21 - c21Exp) / c21Exp)
                + ((c22 - c22Exp) * (c22 - c22Exp) / c22Exp);

        // System.err.printf("++ Chi-square : %7.3f\n", chiSq);

        // Since this is a 2x2 table, effective degrees of freedom is 1.
        double[] chiSqRow = ChiSquareTable[1];
        double pValue = 1.0;

        for (int i = 0; i < chiSqRow.length; i++) {
            if (chiSq > chiSqRow[i]) {
                continue;
            }

            if (0 == i) {
                // The computed chi-square value is smaller than even the first
                // value in the row. In this case, assign the first value from
                // the p-value row.
                pValue = PvalueTable[0];
            } else {
                // Assign the value preceding that of the current index.
                pValue = PvalueTable[i - 1];
            }
            break;
        }

        // No value in the distribution larger than computed chi-square value
        // ==> highest confidence.
        if (1.0 == pValue) {
            pValue = 0.001;
        }

        if (c11Exp < 5.0 || c12Exp < 5.0 || c21Exp < 5.0 || c22Exp < 5.0) {
            return new ChiSquarePvalue(chiSq, pValue, false);
        }

        return new ChiSquarePvalue(chiSq, pValue, true);
    }
}
