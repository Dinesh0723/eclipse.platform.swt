package org.eclipse.swt.graphics;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved
 */

import java.io.Serializable;
import org.eclipse.swt.*;

/**
 * Instances of this class represent rectangular areas in an
 * (x, y) coordinate system. The top left corner of the rectangle
 * is specified by its x and y values, and the extent of the
 * rectangle is specified by its width and height.
 * <p>
 * The coordinate space for rectangles and points is considered
 * to have increasing values downward and to the right from its
 * origin making this the normal, computer graphics oriented notion
 * of (x, y) coordinates rather than the strict mathematical one.
 * </p>
 * <p>
 * Application code does <em>not</em> need to explicitly release the
 * resources managed by each instance when those instances are no longer
 * required, and thus no <code>dispose()</code> method is provided.
 * </p>
 *
 * @see Point
 */

public final class Rectangle implements Serializable {
	
	/**
	 * the x coordinate of the rectangle
	 */
	public int x;
	
	/**
	 * the y coordinate of the rectangle
	 */
	public int y;
	
	/**
	 * the width of the rectangle
	 */
	public int width;
	
	/**
	 * the height of the rectangle
	 */
	public int height;

/**
 * Construct a new instance of this class given the 
 * x, y, width and height values.
 *
 * @param x the x coordinate of the origin of the rectangle
 * @param y the y coordinate of the origin of the rectangle
 * @param width the width of the rectangle
 * @param height the height of the rectangle
 */
public Rectangle (int x, int y, int width, int height) {
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
}

/**
 * Destructively replaces the x, y, width and height values
 * in the receiver with ones which represent the union of the
 * rectangles specified by the receiver and the given rectangle.
 * <p>
 * The union of two rectangles is the smallest single rectangle
 * that completely covers both of the areas covered by the two
 * given rectangles.
 * </p>
 *
 * @param rect the rectangle to merge with the receiver
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the argument is null</li>
 * </ul>
 */
public void add (Rectangle rect) {
	if (rect == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	int left = x < rect.x ? x : rect.x;
	int top = y < rect.y ? y : rect.y;
	int lhs = x + width;
	int rhs = rect.x + rect.width;
	int right = lhs > rhs ? lhs : rhs;
	lhs = y + height;
	rhs = rect.y + rect.height;
	int bottom = lhs > rhs ? lhs : rhs;
	x = left;  y = top;  width = right - left;  height = bottom - top;
}

/**
 * Returns <code>true</code> if the point specified by the
 * arguments is inside the area specified by the receiver,
 * and <code>false</code> otherwise.
 *
 * @param x the x coordinate of the point to test for containment
 * @param y the y coordinate of the point to test for containment
 * @return <code>true</code> if the rectangle contains the point and <code>false</code> otherwise
 */
public boolean contains (int x, int y) {
	return (x >= this.x) && (y >= this.y) && ((x - this.x) < width) && ((y - this.y) < height);
}

/**
 * Returns <code>true</code> if the given point is inside the
 * area specified by the receiver, and <code>false</code>
 * otherwise.
 *
 * @param pt the point to test for containment
 * @return <code>true</code> if the rectangle contains the point and <code>false</code> otherwise
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the argument is null</li>
 * </ul>
 */
public boolean contains (Point pt) {
	if (pt == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	return contains(pt.x, pt.y);
}

/**
 * Compares the argument to the receiver, and returns true
 * if they represent the <em>same</em> object using a class
 * specific comparison.
 *
 * @param object the object to compare with this object
 * @return <code>true</code> if the object is the same as this object and <code>false</code> otherwise
 *
 * @see #hashCode
 */
public boolean equals (Object object) {
	if (object == this) return true;
	if (!(object instanceof Rectangle)) return false;
	Rectangle r = (Rectangle)object;
	return (r.x == this.x) && (r.y == this.y) && (r.width == this.width) && (r.height == this.height);
}

/**
 * Returns an integer hash code for the receiver. Any two 
 * objects which return <code>true</code> when passed to 
 * <code>equals</code> must return the same value for this
 * method.
 *
 * @return the receiver's hash
 *
 * @see #equals
 */
public int hashCode () {
	return x ^ y ^ width ^ height;
}

/**
 * Returns a new rectangle which represents the intersection
 * of the receiver and the given rectangle. 
 * <p>
 * The intersection of two rectangles is the rectangle that
 * covers the area which is contained within both rectangles.
 * </p>
 *
 * @param rect the rectangle to intersect with the receiver
 * @return the intersection of the receiver and the argument
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the argument is null</li>
 * </ul>
 */
public Rectangle intersection (Rectangle rect) {
	if (rect == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	if (this == rect) return new Rectangle (x, y, width, height);
	int left = x > rect.x ? x : rect.x;
	int top = y > rect.y ? y : rect.y;
	int lhs = x + width;
	int rhs = rect.x + rect.width;
	int right = lhs < rhs ? lhs : rhs;
	lhs = y + height;
	rhs = rect.y + rect.height;
	int bottom = lhs < rhs ? lhs : rhs;
	return new Rectangle (left, top, right - left, bottom - top);
}

/**
 * Returns <code>true</code> if the given rectangle intersects
 * with the receiver and <code>false</code> otherwise.
 * <p>
 * Two rectangles intersect if the area of the rectangle
 * representing their intersection is not empty.
 * </p>
 *
 * @param rect the rectangle to test for intersection
 * @return <code>true</code> if the rectangle intersects with the receiver, and <code>false</code> otherwise
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the argument is null</li>
 * </ul>
 *
 * @see #intersection
 * @see #isEmpty
 */
public boolean intersects (Rectangle rect) {
	if (rect == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	return (rect == this) || (rect.x < x + width) && (rect.y < y + height) &&
		(rect.x + rect.width > x) && (rect.y + rect.height > y);
}
		
/**
 * Returns <code>true</code> if the receiver does not cover any
 * area in the (x, y) coordinate plane, and <code>false</code> if
 * the receiver does cover some area in the plane.
 * <p>
 * A rectangle is considered to <em>cover area</em> in the 
 * (x, y) coordinate plane if both its width and height are 
 * non-zero.
 * </p>
 *
 * @return <code>true</code> if the receiver is empty, and <code>false</code> otherwise
 */
public boolean isEmpty () {
	return (width <= 0) || (height <= 0);
}

/**
 * Returns a string containing a concise, human-readable
 * description of the receiver.
 *
 * @return a string representation of the rectangle
 */
public String toString () {
	return "Rectangle {" + x + ", " + y + ", " + width + ", " + height + "}";
}

/**
 * Returns a new rectangle which represents the union of
 * the receiver and the given rectangle.
 * <p>
 * The union of two rectangles is the smallest single rectangle
 * that completely covers both of the areas covered by the two
 * given rectangles.
 * </p>
 *
 * @param rect the rectangle to perform union with
 * @return the union of the receiver and the argument
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the argument is null</li>
 * </ul>
 *
 * @see #add
 */
public Rectangle union (Rectangle rect) {
	if (rect == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	int left = x < rect.x ? x : rect.x;
	int top = y < rect.y ? y : rect.y;
	int lhs = x + width;
	int rhs = rect.x + rect.width;
	int right = lhs > rhs ? lhs : rhs;
	lhs = y + height;
	rhs = rect.y + rect.height;
	int bottom = lhs > rhs ? lhs : rhs;
	return new Rectangle (left, top, right - left, bottom - top);
}

}
