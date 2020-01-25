import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
 * A frame for the GUI of the program to draw and store shapes
 *
 * Note that this program is written as the hint and is not a working program. It should be
 * completed and then should be tested. You can write your own program from the scratch if
 * it is easier for you.
 */

public class AppGUI extends JFrame {

	private static final int FRAME_WIDTH = 950;
	private static final int FRAME_HEIGHT = 450;

	private JLabel enterColourLabel;
	private JTextField enterColourField;
	private JButton clearButton;
	private JButton removeDataButton;
	private JButton getDataButton;
	private JButton ellipseButton;
	private JButton rectangleButton;
	private JButton setColourButton;
	private TitledBorder dbTitle;
	private TitledBorder resultsTitle;
	private TitledBorder initializationTitle;
	private TitledBorder commandsTitle;
	private TitledBorder enterDbTitle;
	private DefaultListModel<String> dataStored;
	private JList<String> dataList;
	private Border blackBorder;
	private ShapeDB simpleDb;
	private int indexToRemoveData;
	private boolean ellipseSelected;
	private boolean rectangleSelected;
	private String colourName;
	public DrawPanel toDrawPanel;

	/*
	 * Constructor for SimpleAssignment
	 */

	public AppGUI(ShapeDB simpleDb) {
		// Initialize all the member/instance variables

		dataStored = new DefaultListModel<String>();
		blackBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

		this.simpleDb = simpleDb;
		colourName = "";
		rectangleSelected = false;
		ellipseSelected = false;

		createTextField();
		createButtons();
		createList();
		createPanels();

		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle("Simple Assignment");
		this.setResizable(false);
	}

	/*
	 * Create the necessary text field with its corresponding label
	 */

	private void createTextField() {

		final int FIELD_WIDTH = 10;
		enterColourLabel = new JLabel("Enter colour: ");
		enterColourField = new JTextField(FIELD_WIDTH);
		enterColourField.setText("");
		enterColourField.addActionListener(new ColorListener());

	}

	/*
	 * Create all the necessary buttons
	 */

	private void createButtons() {

		getDataButton = new JButton("Restore Data");
		getDataButton.addActionListener(new GetDataListener());
		getDataButton.setEnabled(false);

		removeDataButton = new JButton("Remove Data");
		removeDataButton.addActionListener(new RemoveDataListener());
		removeDataButton.setEnabled(false);

		ellipseButton = new JButton("Ellipse");
		ellipseButton.addActionListener(new EllipseListener());

		rectangleButton = new JButton("Rectangle");
		rectangleButton.addActionListener(new RectangleListener());

		setColourButton = new JButton("Set Colour");
		setColourButton.addActionListener(new ColorListener());

		clearButton = new JButton("Clear Drawing");
		clearButton.addActionListener(new ClearListener());
	}

	/*
	 * Listener for when the colour name is input into the text field
	 */

	class ColorListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String colorField = enterColourField.getText();
			if (!colorField.equals("") || colorField != null) {
				colourName = colorField;
			}
		}
	}

	/*
	 * Listener for the button to get data from the simple database
	 *
	 * Part B: Add appropriate exception handling
	 */
	class GetDataListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			simpleDb.restoreShapes();
			MyList shapesRestored = simpleDb.getShapes();
			toDrawPanel.restoreShapes(shapesRestored);
			toDrawPanel.repaint();
			dataStored.clear();

			for (int i = 0; i < shapesRestored.size(); i++) {
				Shape s = (Shape) (shapesRestored.get(i));
				dataStored.addElement(s.getShapeName() + " " + s.getColourName() + " " + s.getX() + "," + s.getY());
			}

			getDataButton.setEnabled(false);
		}
	}

	/*
	 * Listener to remove data from the simple database and remove it from the
	 * drawing canvas
	 *
	 * Part B: Add appropriate exception handling
	 */

	class RemoveDataListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			simpleDb.removeShape(indexToRemoveData);
			dataStored.remove(indexToRemoveData);
			toDrawPanel.removeShape(indexToRemoveData);
			toDrawPanel.repaint();
		}
	}

	/*
	 * Listener to check if ellipse button is clicked on
	 */

	class EllipseListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			ellipseSelected = true;
			rectangleSelected = false;
		}
	}

	/*
	 * Listener to check if rectangle button is clicked on
	 */

	class RectangleListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			rectangleSelected = true;
			ellipseSelected = false;
		}
	}

	/*
	 * Listener to clear the drawing canvas and allow the user to get data from
	 * simple database afterwards
	 */

	class ClearListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			simpleDb.storeShapes();
			toDrawPanel.clearShapes();
			toDrawPanel.repaint();
			dataStored.clear();
			getDataButton.setEnabled(true);
		}
	}

	/*
	 * Create the list to show the data in the simple database
	 */

	private void createList() {
		dataList = new JList<String>(dataStored);
		dataList.setVisibleRowCount(20);
		dataList.setPrototypeCellValue(String.format("%80s", ""));
		dataList.setLayoutOrientation(JList.VERTICAL);
		dataList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		dataList.addListSelectionListener(new RemoveFromListListener());
		dataList.setBorder(blackBorder);

	}

	/*
	 * Listener to check which shape is to be removed from the simple database and
	 * drawing canvas
	 */

	class RemoveFromListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			if (event.getValueIsAdjusting() == false) {

				if (dataList.getSelectedIndex() == -1) {
					// Part A: No selection, disable remove data button
					// Part B: No selection, remove data should remain enabled
					removeDataButton.setEnabled(false);

				} else {
					// Part A: Selection, enable the remove data button.
					// Part B: Selection, remove data should have been enabled already
					indexToRemoveData = dataList.getSelectedIndex();
					removeDataButton.setEnabled(true);
				}
			}
		}
	}

	/*
	 * Creates all the panels in the GUI and add to the frame
	 */

	private void createPanels() {

		Border empty = BorderFactory.createEmptyBorder();

		JPanel mainPanel = new JPanel();
		JPanel centerPanel = new JPanel(new BorderLayout(20, 50));
		JPanel resultsPanel = new JPanel();
		resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
		JPanel inputDbPanel = new JPanel();
		JPanel commandsPanel = new JPanel(new GridLayout(2, 2));
		JPanel initializationsPanel = new JPanel(new GridLayout(2, 2));
		toDrawPanel = new DrawPanel();
		toDrawPanel.setBackground(new java.awt.Color(255, 255, 255));
		toDrawPanel.setBorder(blackBorder);

		JScrollPane dataPane = new JScrollPane(dataList);

		dbTitle = BorderFactory.createTitledBorder(empty, "List of Shapes");
		dbTitle.setTitleJustification(TitledBorder.CENTER);
		enterDbTitle = BorderFactory.createTitledBorder(empty, "Colour Input");
		enterDbTitle.setTitleJustification(TitledBorder.CENTER);
		initializationTitle = BorderFactory.createTitledBorder(empty, "Choose Shape");
		initializationTitle.setTitleJustification(TitledBorder.CENTER);
		commandsTitle = BorderFactory.createTitledBorder(empty, "Commands");
		commandsTitle.setTitleJustification(TitledBorder.CENTER);
		resultsTitle = BorderFactory.createTitledBorder(empty, "Draw Here");
		resultsTitle.setTitleJustification(TitledBorder.CENTER);

		dataPane.setBorder(dbTitle);
		resultsPanel.setBorder(resultsTitle);
		inputDbPanel.setBorder(enterDbTitle);
		initializationsPanel.setBorder(initializationTitle);
		commandsPanel.setBorder(commandsTitle);

		// Add components to the correct panels
		inputDbPanel.add(enterColourLabel);
		inputDbPanel.add(enterColourField);

		initializationsPanel.add(ellipseButton);
		initializationsPanel.add(rectangleButton);

		commandsPanel.add(setColourButton);
		commandsPanel.add(getDataButton);
		commandsPanel.add(removeDataButton);
		commandsPanel.add(clearButton);

		resultsPanel.add(toDrawPanel);

		centerPanel.add(inputDbPanel, BorderLayout.CENTER);
		centerPanel.add(initializationsPanel, BorderLayout.NORTH);
		centerPanel.add(commandsPanel, BorderLayout.SOUTH);

		mainPanel.add(dataPane);
		mainPanel.add(centerPanel);
		mainPanel.add(resultsPanel);

		add(mainPanel);
	}

	/*
	 * An implementation of JPanel to keep track of the shapes drawn on the canvas
	 */

	class DrawPanel extends JPanel {
		/*
		 * Part A: ArrayList for shapesDrawn Part B: LinkedList for shapesDrawn
		 */

		private MyList shapesDrawn;

		/*
		 * Constructor for the drawing canvas
		 */

		DrawPanel() {
			setPreferredSize(new Dimension(275, 350));
			// Add MouseListener here
			addMouseListener(new MyMouseListener());
			// Create ArrayList of shapes drawn for Part A, LinkedList for Part B
			shapesDrawn = new MyList();
		}

		/*
		 * Create MouseListener for when ellipse or rectangle button is selected
		 */

		class MyMouseListener implements MouseListener {
			public void mousePressed(MouseEvent event) {
				if (ellipseSelected && (colourName.equalsIgnoreCase("blue") || colourName.equalsIgnoreCase("orange"))) {

					EllipseShape ellipseShape = new EllipseShape(event.getX(), event.getY(), colourName);
					shapesDrawn.add(ellipseShape);
					simpleDb.addShape(ellipseShape);
					Shape shape = simpleDb.getLatestShape();
					dataStored.addElement(shape.getShapeName() + " " + shape.getColourName() + " " + shape.getX() + ","
							+ shape.getY());
				} else if (rectangleSelected
						&& (colourName.equalsIgnoreCase("blue") || colourName.equalsIgnoreCase("orange"))) {

					RectangleShape rectangleShape = new RectangleShape(event.getX(), event.getY(), colourName);
					shapesDrawn.add(rectangleShape);
					simpleDb.addShape(rectangleShape);
					Shape shape = simpleDb.getLatestShape();
					dataStored.addElement(shape.getShapeName() + " " + shape.getColourName() + " " + shape.getX() + ","
							+ shape.getY());
				}

				repaint();
			}

			public void mouseReleased(MouseEvent event) {
			}

			public void mouseClicked(MouseEvent event) {
			}

			public void mouseEntered(MouseEvent event) {
			}

			public void mouseExited(MouseEvent event) {
			}
		}

		/*
		 * Restore the shapes obtained from the simple database to the drawing canvas
		 *
		 * Part A: Restore an ArrayList Part B: Restore a LinkedList
		 */

		public void restoreShapes(MyList savedShapes) {

			shapesDrawn = new MyList(savedShapes);
		}

		/*
		 * Clear the drawing canvas of shapes
		 *
		 * Part A: Clear from ArrayList Part B: Clear from LinkedList
		 */

		public void clearShapes() {
			shapesDrawn = new MyList();
		}

		/*
		 * Remove a shape from the drawing canvas
		 *
		 * Part A: Remove from ArrayList with given index Part B: Remove from LinkedList
		 * appropriately with given index
		 */
		public void removeShape(int index) {
			shapesDrawn.remove(index); // NullPointerError
		}

		// Draw the shapes in the simple database on the drawing canvas
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			if (shapesDrawn.size() > 0) {
				for (int j = 0; j < shapesDrawn.size(); j++) {
					Shape s2 = (Shape) (shapesDrawn.get(j));
					s2.draw(g2);
				}
			}

		}

	}

}
