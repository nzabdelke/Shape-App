
/*
 * Class to represent a very simplified database
 *
* Note that this program is written as the hint and is not a working program. It should be
* completed and then should be tested. You can write your own program from the scratch if
* it is easier for you.
*/

import java.util.NoSuchElementException;

public class ShapeDB {

	/*
	 * ArrayList for Part A. for Part B, they should be LinkedList
	 */

	private MyList shapes;
	private MyList storedShapes;

	/*
	 * Constructor to initialize the two ArrayList's for Part A. For Part B, they
	 * should be LinkedList
	 */

	public ShapeDB() {
		shapes = new MyList();
		storedShapes = new MyList();
	}

	/*
	 * Get the list of shapes from the simple database Part A: You should get an
	 * ArrayList Part B: You should get a LinkedList
	 */

	public MyList getShapes() {
		return shapes;
	}

	/*
	 * Add a shape to the simple database Part A: Add to an ArrayList Part B: Add to
	 * a LinkedList
	 */

	public void addShape(Shape newShape) {
		shapes.add(newShape);
	}

	/*
	 * Get the latest shape added to the simple database Part A: Get latest shape
	 * from ArrayList Part B: Get latest shape from LinkedList
	 */

	public Shape getLatestShape() {

		// if (shapes.size() < 0) {throw new NoSuchElementException();}

		return (Shape) (shapes.get(shapes.size() - 1));
	}

	/*
	 * Get a shape from an index Part A: Get from an index in ArrayList Part B: Get
	 * from a LinkedList appropriately with the given index
	 */

	public Shape getShape(int index) {
		return (Shape) (shapes.get(index));
	}

	/*
	 * Store the shapes in the simple database Part A: Store in ArrayList Part B:
	 * Store in LinkedList
	 */

	public void storeShapes() {
		storedShapes = new MyList(shapes);
	}

	/*
	 * Get the stored shapes from the simple database Part A: restore from ArrayList
	 * Part B: restore from LinkedList
	 */

	public void restoreShapes() {
		MyList tempShapes = new MyList(shapes);
		tempShapes = new MyList();
		shapes = new MyList(storedShapes);
	}

	/*
	 * Remove a shape from an index Part A: Remove from an index in ArrayList Part
	 * B: Remove from the LinkedList appropriately with the given index
	 */

	public void removeShape(int index) {
		shapes.remove(index);
	}

}
