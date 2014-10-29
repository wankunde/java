package com.wankun.math3;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

/**
 * 
 * <p>
 * 向量的加减乘除运算、向量外积、向量内积、向量范数
 * </p>
 * 
 * @author wankun
 * @date 2014年9月19日
 * @version 1.0
 */
public class VectorTest {

	public static void main(String[] args) {
		vector();
	}

	private static void vector() {
		// TODO Auto-generated method stub
		double[] vec1 = { 1d, 2d, 3d };
		double[] vec2 = { 4d, 5d, 6d };
		ArrayRealVector v1 = new ArrayRealVector(vec1);
		ArrayRealVector v2 = new ArrayRealVector(vec2);

		// output directly
		System.out.println("v1 is " + v1);
		// dimension : size of vector
		System.out.println("size is " + v1.getDimension());
		// vector add
		System.out.println("v1 + v2 = " + v1.add(v2));
		// System.out.println("v1 + v2 = " + v1.add(vec2));
		// vector substract
		System.out.println("v1 - v2 = " + v1.subtract(v2));
		// vector element by element multiply
		System.out.println("v1 * v2 = " + v1.ebeMultiply(v2));
		// vector element by element divide
		System.out.println("v1 / v2 = " + v1.ebeDivide(v2));
		// get index at 1
		System.out.println("v1[1] = " + v1.getEntry(1));
		// vector append
		RealVector t_vec = v1.append(v2);
		System.out.println("v1 append v2 is " + t_vec);
		// vector distance
		System.out.println("distance between v1 and v2 is " + v1.getDistance(v2));
		System.out.println("L1 distance between v1 and v2 is " + v1.getL1Distance(v2));
		// vector norm
		System.out.println("norm of v1 is " + v1.getNorm());
		// vector dot product
		System.out.println("dot product of v1 and v2 is " + v1.dotProduct(v2));
		// vector outer product
		System.out.println("outer product of v1 and v2 is " + v1.outerProduct(v2));
		// vector orthogonal projection
		System.out.println("hogonal projection of v1 and v2 is " + v1.projection(v2));
		// vector map operations
		// System.out.println("Map the Math.abs(double) function to v1 is "
		// + v1.mapAbs());
		// v1.mapInvToSelf();
		System.out.println("Map the 1/x function to v1 itself is " + v1);
		// vector get sub vector
		System.out.println("sub vector of v1 is " + v1.getSubVector(0, 2));
	}

}