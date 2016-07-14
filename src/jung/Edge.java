package jung;

public class Edge<E> {
	
	private Node<E> startVertex;
	private Node<E> endVertex;
	private boolean marked;
	
	public Edge(Node<E> node1, Node<E> node2){
		this.startVertex = node1;
		this.endVertex = node2;
		marked = false;
	}

	public Node<E> getStartVertex() {
		return startVertex;
	}

	public void setStartVertex(Node<E> startVertex) {
		this.startVertex = startVertex;
	}

	public Node<E> getEndVertex() {
		return endVertex;
	}

	public void setEndVertex(Node<E> endVertex) {
		this.endVertex = endVertex;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endVertex == null) ? 0 : endVertex.hashCode());
		result = prime * result + ((startVertex == null) ? 0 : startVertex.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge<?> other = (Edge) obj;
		if (endVertex == null) {
			if (other.endVertex != null)
				return false;
		} else if (!endVertex.equals(other.endVertex))
			return false;
		if (startVertex == null) {
			if (other.startVertex != null)
				return false;
		} else if (!startVertex.equals(other.startVertex))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "E[start=" + startVertex + ", end=" + endVertex + ", marked=" + marked + "]";
	}
	
	
}
