import java.util.ArrayList;
import java.util.List;

public class ComputerNode implements Comparable
{
	public static final char WHITE = 'w';
	public static final char GRAY = 'g';
	public static final char BLACK = 'b';

	private int id, timestamp;
	private ArrayList<ComputerNode> outNeighbors;
	private char color; // For DFS
	private ComputerNode pred; // for DFS

	public ComputerNode(int id, int timestamp)
	{
		this.id = id;
		this.timestamp = timestamp;
		outNeighbors = new ArrayList<>();
	}

	/**
	 * Returns the ID of this {@code ComputerNode}.
	 *
	 * @return The ID of this {@code ComputerNode}
	 */
	public int getID()
	{
		return id;
	}

	/**
	 * Returns the timestamp of this {@code ComputerNode}.
	 *
	 * @return The timestamp of this {@code ComputerNode}
	 */
	public int getTimestamp()
	{
		return timestamp;
	}

	/**
	 * Returns a {@code List} of the nodes that can be reached from this node.
	 *
	 * @return A {@code List} containing the neighbors of this node
	 */
	public List<ComputerNode> getOutNeighbors()
	{
		return outNeighbors;
	}

	/**
	 * Adds a {@code ComputerNode} to the {@code List} of this
	 * {@code ComputerNode}'s out-neighbors.
	 *
	 * @param neighbor The {@code ComputerNode} to be added to this
	 * 				   {@code ComputerNode}'s {@code List} of out-neighbors
	 */
	public void addOutNeighbor(ComputerNode neighbor)
	{
		outNeighbors.add(neighbor);
	}

	/**
	 * Returns the color of this {@code ComputerNode}. This color is used in
	 * running a depth-first search on the graph. The color is either:
	 * WHITE (Undiscovered),
	 * GRAY (Visited), or
	 * BLACK (Finished).
	 *
	 * @return The color of this {@code ComputerNode}
	 */
	public char getColor()
	{
		return color;
	}

	/**
	 * Sets the color of this {@code ComputerNode}.
	 *
	 * @param color The color to which to set the color of this
	 * 				{@code ComputerNode}
	 */
	public void setColor(char color)
	{
		this.color = color;
	}

	/**
	 * Sets the predecessor of this {@code ComputerNode}. This is calculated
	 * while running a depth-first search on the graph.
	 *
	 * @param pred The {@code ComputerNode} which is the predecessor of this
	 *             {@code ComputerNode}
	 */
	public void setPred(ComputerNode pred)
	{
		this.pred = pred;
	}

	/**
	 * Compares this {@code ComputerNode} to another {@code Object}.
	 *
	 * @param o The object to which to compare this {@code ComputerNode}
	 * @return -1, 0, or 1 if {@code o.timestamp} is less than, equal to,
	 * 		   or greater than {@code this.timestamp}, respectively
	 */
	@Override
	public int compareTo(Object o)
	{
		if (!(o instanceof ComputerNode)) throw new IllegalArgumentException();

		if (((ComputerNode) o).timestamp < this.timestamp) return 1;
		else if (((ComputerNode) o).timestamp > this.timestamp) return -1;
		return 0;
	}

	/**
	 * Checks this {@code ComputerNode} against another {@code ComputerNode}
	 * for equality, which is determined by checking if the ID and timestamp of
	 * the two {@code ComputerNode}s are equal.
	 *
	 * @param node The {@code ComputerNode} against which to check this
	 *             {@code ComputerNode}
	 * @return True if the two {@code ComputerNode}s are equal, false otherwise
	 */
	public boolean equals(ComputerNode node)
	{
		return this.getID() == node.getID() && this.getTimestamp() == node.getTimestamp();
	}
}