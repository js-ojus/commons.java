package com.ojuslabs.math.stat;

/**
 * Used to answer the computed chi-square and p-value, together with a flag that indicates
 * high reliability ({@code true}) or low ({@code false}).
 */
public final class ChiSquarePvalue
{
    public final double  chiSquare;
    public final double  pValue;
    public final boolean reliable;

    public ChiSquarePvalue(double chiSq, double pv, boolean rel) {
        chiSquare = chiSq;
        pValue = pv;
        reliable = rel;
    }
}
