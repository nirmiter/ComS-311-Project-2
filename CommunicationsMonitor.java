import java.util.*;

public class CommunicationsMonitor
{
	private LinkedHashMap<ComputerNode, List<ComputerNode>> adjacencyList;
	private LinkedHashMap<Integer, List<ComputerNode>> nodeTimestamps;
	private ArrayList<Communication> communications;
	private boolean created;

	public CommunicationsMonitor()
	{
		adjacencyList = new LinkedHashMap<>();
		nodeTimestamps = new LinkedHashMap<>();
		communications = new ArrayList<>();
		created = false;
	}

	/**
	 * Adds a {@code Communication} to the list of {@code Communication}s to be
	 * used in this graph. If the graph has already been created, calling this
	 * method will have noe effect.
	 *
	 * @param c1 The ID of the first computer in the communication
	 * @param c2 The ID of the second computer in the communication
	 * @param timestamp The time at which the two computers communicated
	 */
	public void addCommunication(int c1, int c2, int timestamp)
	{
		if (created) return;
		communications.add(new Communication(c1, c2, timestamp));
	}

	/**
	 * Creates a graph from the list of {@code Communication}s. Updates both
	 * the adjacency list representation of the graph and the list of trace
	 * data, that is, the list for each computer containing the nodes that
	 * refer to that computer.
	 */
	public void createGraph() // Needs to be O(n + m log(m))
	{
		ComputerNode node1, node2;
		ArrayList<ComputerNode> timestamps;
		ComputerNode prevLast;

		/* Sort the Communications by timestamp */
		communications.sort(Comparator.naturalOrder()); // O(m log(m))

		/* For each Communication, add the correct ComputerNodes to the correct lists */
		for (Communication c : communications) // O(m)
		{
			/* Create the nodes */
			node1 = new ComputerNode(c.c1, c.timestamp);
			node2 = new ComputerNode(c.c2, c.timestamp);

			/* If the nodes are the same as a preexisting node, replace the reference with the existing one */
			if (!nodeTimestamps.containsKey(c.c1)) // O(log(n))
			{
				nodeTimestamps.put(c.c1, new ArrayList<>());
				nodeTimestamps.get(c.c1).add(node1);
			}
			else
			{
				timestamps = (ArrayList<ComputerNode>) nodeTimestamps.get(c.c1);
				prevLast = timestamps.get(timestamps.size() - 1);

				if (prevLast.getTimestamp() != node1.getTimestamp())
				{
					timestamps.add(node1);
					adjacencyList.get(prevLast).add(node1);
				}
				else node1 = prevLast;
			}

			if (!nodeTimestamps.containsKey(c.c2)) // O(log(n))
			{
				nodeTimestamps.put(c.c2, new ArrayList<>());
				nodeTimestamps.get(c.c2).add(node2);
			}
			else
			{
				timestamps = (ArrayList<ComputerNode>) nodeTimestamps.get(c.c2);
				prevLast = timestamps.get(timestamps.size() - 1);

				if (prevLast.getTimestamp() != node2.getTimestamp())
				{
					timestamps.add(node2);
					adjacencyList.get(prevLast).add(node2);
				}
				else node2 = prevLast;
			}

			/* If the node hasn't been added to the adjacency list, add it */
			if (!adjacencyList.containsKey(node1)) adjacencyList.put(node1, new ArrayList<>());
			if (!adjacencyList.containsKey(node2)) adjacencyList.put(node2, new ArrayList<>());

			/* Add edges between the two nodes in the Communication */
			adjacencyList.get(node1).add(node2);
			adjacencyList.get(node2).add(node1);

			/* Add the nodes to other's out-neighbors list */
			node1.addOutNeighbor(node2);
			node2.addOutNeighbor(node1);
		}

		created = true;
	}

	/**
	 * Determines if there exists a path from {@code c1} to {@code c2} from
	 * time {@code x} to time {@code y}. Returns this path if it exists and
	 * {@code null} otherwise.
	 *
	 * @param c1 The ID of the starting {@code ComputerNode}
	 * @param c2 The ID of the ending {@code ComputerNode}
	 * @param x The time at which computer {@code c1} was first infected
	 * @param y The time at which computer {@code c2}
	 * @return A path between the nodes with IDs {@code c1} and {@code c2}
	 * 		   if such a path exists such that the virus could get from
	 * 		   {@code c1} to {@code c2} between the times {@code x} and
	 * 		   {@code y}, {@code null} otherwise
	 */
	public List<ComputerNode> queryInfection(int c1, int c2, int x, int y)
	{
		// TODO Implement
		return null;
	}

	/**
	 * Returns the trace data of the graph, that is, a mapping for each
	 * {@code ComputerNode} that contains each time the {@code ComputerNode}
	 * appears in the graph.
	 *
	 * @return A {@code HashMap} containing each computer ID and the
	 * 		  {@code List} of nodes with that ID that appear in the graph
	 */
	public HashMap<Integer, List<ComputerNode>> getComputerMapping()
	{
		return nodeTimestamps;
	}

	/**
	 * Returns a {@code List} of {@code ComputerNode}s that refer to the
	 * computer with ID {@code c}.
	 *
	 * @param c The ID of the {@code ComputerNode} whose list is to be returned
	 * @return The {@code List} of {@code ComputerNodes} that refer to the
	 * 		   computer with ID {@code c}
	 */
	public List<ComputerNode> getComputerMapping(int c)
	{
		return nodeTimestamps.get(c);
	}

	/**
	 *
	 *
	 * @return
	 */
	public List<Communication> getCommunications()
	{
		return communications;
	}

	/**
	 * Prints the graph's adjacency list representation.
	 */
	public void printGraph()
	{
		System.out.println("Node Timestamps:");
		nodeTimestamps.forEach((Integer id, List<ComputerNode> edges) ->
		{
			System.out.print("(" + id + ")" + " ->\t");

			for (ComputerNode edge : edges)
			{
				System.out.print("(" + edge.getID() + ", " + edge.getTimestamp() + ")\t");
			}

			System.out.println("");
		});

		System.out.println("Adjacency List:");
		adjacencyList.forEach((ComputerNode node, List<ComputerNode> edges) ->
		{
			System.out.print("(" + node.getID() + ", " + node.getTimestamp() + ")" + " ->\t");

			for (ComputerNode edge : edges)
			{
				System.out.print("(" + edge.getID() + ", " + edge.getTimestamp() + ")\t");
			}

			System.out.println("");
		});

		/*System.out.println("\nOut-Neighbors:");

		adjacencyList.forEach((ComputerNode node, List<ComputerNode> edges) ->
		{
			System.out.print("(" + node.getID() + ", " + node.getTimestamp() + ")" + " ->\t");

			for (ComputerNode neighbor : node.getOutNeighbors())
			{
				System.out.print("(" + neighbor.getID() + ", " + neighbor.getTimestamp() + ")\t");
			}

			System.out.println("");
		});*/
	}

	private void depthFirstSearch()
	{
		adjacencyList.forEach((ComputerNode v, List<ComputerNode> neighbors) ->
		{
			v.setColor(ComputerNode.WHITE);
			v.setPred(null);
		});

		adjacencyList.forEach((ComputerNode v, List<ComputerNode> neighbors) ->
		{
			if (v.getColor() == ComputerNode.WHITE) depthFirstSearchVisit(v);
		});
	}

	private void depthFirstSearchVisit(ComputerNode u)
	{
		u.setColor(ComputerNode.GRAY);

		for (ComputerNode v : u.getOutNeighbors())
		{
			if (v.getColor() == ComputerNode.WHITE)
			{
				v.setPred(u);
				depthFirstSearchVisit(v);
			}
		}

		u.setColor(ComputerNode.BLACK);
	}

	public class Communication implements Comparable
	{
		int c1, c2;
		int timestamp;

		public Communication(int c1, int c2, int timestamp)
		{
			this.c1 = c1;
			this.c2 = c2;
			this.timestamp = timestamp;
		}

		/**
		 * Returns the timestamp of this {@code Communication}.
		 *
		 * @return The timestamp of this {@code Communication}
		 */
		public int getTimestamp()
		{
			return timestamp;
		}

		/**
		 * Compares this {@code Communication} to another {@code Object}.
		 *
		 * @param o The object to which to compare this {@code Communication}
		 * @return -1, 0, or 1 if {@code o.timestamp} is less than, equal to,
		 * 		   or greater than {@code this.timestamp}, respectively
		 */
		@Override
		public int compareTo(Object o)
		{
			if (!(o instanceof Communication)) throw new IllegalArgumentException();

			if (((Communication) o).timestamp < this.timestamp) return 1;
			else if (((Communication) o).timestamp > this.timestamp) return -1;
			return 0;
		}
	}
}