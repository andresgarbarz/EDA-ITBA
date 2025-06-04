package itba.andy.TP6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

abstract public class AdjacencyListGraph<V, E> implements GraphService<V, E> {

	private boolean isSimple;
	protected boolean isDirected;
	private boolean acceptSelfLoop;
	private boolean isWeighted;
	protected String type;

	// HashMap no respeta el orden de insercion. En el testing considerar eso
	private Map<V, Collection<InternalEdge>> adjacencyList = new HashMap<>();

	// respeta el orden de llegada y facilita el testing
	// private Map<V,Collection<InternalEdge>> adjacencyList= new LinkedHashMap<>();

	protected Map<V, Collection<AdjacencyListGraph<V, E>.InternalEdge>> getAdjacencyList() {
		return adjacencyList;
	}

	protected AdjacencyListGraph(boolean isSimple, boolean isDirected, boolean acceptSelfLoop, boolean isWeighted) {
		this.isSimple = isSimple;
		this.isDirected = isDirected;
		this.acceptSelfLoop = acceptSelfLoop;
		this.isWeighted = isWeighted;

		this.type = String.format("%s %sWeighted %sGraph with %sSelfLoop",
				isSimple ? "Simple" : "Multi", isWeighted ? "" : "Non-",
				isDirected ? "Di" : "", acceptSelfLoop ? "" : "No ");
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void addVertex(V aVertex) {

		if (aVertex == null)
			throw new IllegalArgumentException(Messages.getString("addVertexParamCannotBeNull"));

		// no edges yet
		getAdjacencyList().putIfAbsent(aVertex,
				new ArrayList<InternalEdge>());
	}

	@Override
	public int numberOfVertices() {
		return getVertices().size();
	}

	@Override
	public Collection<V> getVertices() {
		return getAdjacencyList().keySet();
	}

	@Override
	public int numberOfEdges() {
		int count = 0;
		if (isDirected) {
			// Para grafos dirigidos, contamos todos los edges
			for (Collection<InternalEdge> edges : getAdjacencyList().values()) {
				count += edges.size();
			}
		} else {
			// Para grafos no dirigidos, contamos cada edge solo una vez
			HashSet<V> visited = new HashSet<>();
			for (V vertex : getAdjacencyList().keySet()) {
				if (!visited.contains(vertex)) {
					count += getAdjacencyList().get(vertex).size();
					visited.add(vertex);
				}
			}
		}
		return count;
	}

	@Override
	public void addEdge(V aVertex, V otherVertex, E theEdge) {

		// validation!!!!
		if (aVertex == null || otherVertex == null || theEdge == null)
			throw new IllegalArgumentException(Messages.getString("addEdgeParamCannotBeNull"));

		// es con peso? debe tener implementado el metodo double getWeight()
		if (isWeighted) {
			// reflection
			Class<? extends Object> c = theEdge.getClass();
			try {
				c.getDeclaredMethod("getWeight");
			} catch (NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(
						type + " is weighted but the method double getWeighed() is not declared in theEdge");
			}
		}

		if (!acceptSelfLoop && aVertex.equals(otherVertex)) {
			throw new RuntimeException(String.format("%s does not accept self loops between %s and %s",
					type, aVertex, otherVertex));
		}

		// if any of the vertex is not presented, the node is created automatically
		addVertex(aVertex);
		addVertex(otherVertex);

	}

	@Override
	public boolean removeVertex(V aVertex) {
		if (aVertex == null)
			throw new IllegalArgumentException(Messages.getString("removeVertexParamCannotBeNull"));
		for (V vertex : getAdjacencyList().keySet()) {
			Collection<InternalEdge> edges = getAdjacencyList().get(vertex);
			edges.removeIf(edge -> edge.target.equals(aVertex));
		}
		getAdjacencyList().remove(aVertex);
		return true;
	}

	@Override
	public boolean removeEdge(V aVertex, V otherVertex) {
		int count = 0;
		if (aVertex == null || otherVertex == null)
			throw new IllegalArgumentException(Messages.getString("removeEdgeParamCannotBeNull"));
		for (V vertex : getAdjacencyList().keySet()) {
			if (vertex.equals(aVertex)) {
				Collection<InternalEdge> edges = getAdjacencyList().get(vertex);
				count += edges.removeIf(edge -> edge.target.equals(otherVertex)) ? 1 : 0;
			}
			if (vertex.equals(otherVertex)) {
				Collection<InternalEdge> edges = getAdjacencyList().get(vertex);
				count += edges.removeIf(edge -> edge.target.equals(aVertex)) ? 1 : 0;
			}
		}
		if (isDirected)
			count++; // in a directed graph, we remove the edge only once
		return count == 2;
	}

	@Override
	public boolean removeEdge(V aVertex, V otherVertex, E theEdge) {
		int count = 0;
		if (aVertex == null || otherVertex == null || theEdge == null)
			throw new IllegalArgumentException(Messages.getString("removeEdgeParamCannotBeNull"));
		for (V vertex : getAdjacencyList().keySet()) {
			if (vertex.equals(aVertex)) {
				Collection<InternalEdge> edges = getAdjacencyList().get(vertex);
				count += edges.removeIf(edge -> edge.target.equals(otherVertex) && edge.edge.equals(theEdge)) ? 1 : 0;
			}
			if (vertex.equals(otherVertex)) {
				Collection<InternalEdge> edges = getAdjacencyList().get(vertex);
				count += edges.removeIf(edge -> edge.target.equals(aVertex) && edge.edge.equals(theEdge)) ? 1 : 0;
			}
		}
		return count == 2;
	}

	@Override
	public void dump() {
		for (V vertex : getAdjacencyList().keySet()) {
			System.out.println(vertex + " -> " + getAdjacencyList().get(vertex));
		}
	}

	@Override
	public int degree(V aVertex) {
		if (isDirected) {
			return inDegree(aVertex) + outDegree(aVertex);
		}
		return inDegree(aVertex);
	}

	@Override
	public int inDegree(V aVertex) {
		int count = 0;
		for (V vertex : getAdjacencyList().keySet()) {
			Collection<InternalEdge> edges = getAdjacencyList().get(vertex);
			for (InternalEdge edge : edges) {
				if (edge.target.equals(aVertex)) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public int outDegree(V aVertex) {
		return getAdjacencyList().get(aVertex).size();
	}

	@Override
	public Iterable<V> getBFS(V start) {
		if (start == null)
			throw new IllegalArgumentException(Messages.getString("getBFSParamCannotBeNull"));
		if (!getVertices().contains(start))
			throw new IllegalArgumentException(Messages.getString("getBFSParamMustBeInGraph"));
		return () -> new BFSIterator(start);
	}

	@Override
	public Iterable<V> getDFS(V start) {
		if (start == null)
			throw new IllegalArgumentException(Messages.getString("getDFSParamCannotBeNull"));
		if (!getVertices().contains(start))
			throw new IllegalArgumentException(Messages.getString("getDFSParamMustBeInGraph"));
		return () -> new DFSIterator(start);
	}

	private abstract class GraphIterator implements Iterator<V> {
		protected HashSet<V> visited = new HashSet<>();

		protected GraphIterator(V start) {
			init();
			if (start != null) {
				addToStructure(start);
				visited.add(start);
			}
		}

		abstract void init();

		@Override
		public boolean hasNext() {
			return !isEmpty();
		}

		@Override
		public V next() {
			V current = removeFromStructure();
			for (InternalEdge edge : getAdjacencyList().get(current)) {
				V neighbor = edge.target;
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					addToStructure(neighbor);
				}
			}
			return current;
		}

		protected abstract void addToStructure(V vertex);

		protected abstract V removeFromStructure();

		protected abstract boolean isEmpty();
	}

	private class DFSIterator extends GraphIterator {
		private Stack<V> stack;

		private DFSIterator(V start) {
			super(start);
		}

		@Override
		void init() {
			this.stack = new Stack<>();
		}

		@Override
		protected void addToStructure(V vertex) {
			stack.push(vertex);
		}

		@Override
		protected V removeFromStructure() {
			return stack.pop();
		}

		@Override
		protected boolean isEmpty() {
			return stack.isEmpty();
		}
	}

	private class BFSIterator extends GraphIterator {
		Queue<V> queue;

		private BFSIterator(V start) {
			super(start);
		}

		@Override
		void init() {
			this.queue = new LinkedList<>();
		}

		@Override
		protected void addToStructure(V vertex) {
			queue.add(vertex);
		}

		@Override
		protected V removeFromStructure() {
			return queue.remove();
		}

		@Override
		protected boolean isEmpty() {
			return queue.isEmpty();
		}
	}

	// Parcial 2024 Q1 ej 2
	public int getNRegular() {
		if (!isSimple || acceptSelfLoop)
			throw new RuntimeException("Graph not valid");

		int degree = degree(getAdjacencyList().keySet().iterator().next());
		for (V vertex : getAdjacencyList().keySet()) {
			if (degree(vertex) != degree)
				return -1;
		}
		return degree;
	}

	class InternalEdge {
		E edge;
		V target;

		InternalEdge(E propEdge, V target) {
			this.target = target;
			this.edge = propEdge;
		}

		@Override
		public boolean equals(Object obj) {
			@SuppressWarnings("unchecked")
			InternalEdge aux = (InternalEdge) obj;

			return ((edge == null && aux.edge == null) || (edge != null && edge.equals(aux.edge)))
					&& target.equals(aux.target);
		}

		@Override
		public int hashCode() {
			return target.hashCode();
		}

		@Override
		public String toString() {
			return String.format("-[%s]-(%s)", edge, target);
		}
	}

}
