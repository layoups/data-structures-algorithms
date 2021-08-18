import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;



/**
 * Your implementation of various different graph algorithms.
 *
 * @author Karim Layoun
 * @userid klayoun3
 * @GTID 903210227
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List},
     * {@code java.util.Queue}, and any classes that implement the
     * aforementioned interfaces, as long as it is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
                                            Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("The given graph is null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The given root is not in the"
                    + " graph");
        }
        Queue<Vertex<T>> q = new LinkedList<>();
        List<Vertex<T>> ret = new ArrayList<>();
        q.add(start);
        return breadthFirstSearch(graph, ret, q);

    }

    /**
     * helper method for breadth first search
     *
     * @param graph the graph to search
     * @param ret the List of vertices in the graph
     * @param q the priority queue used to perform the search
     * @param <T> the class of the data in the node
     * @return the list of vertices according to the search
     */
    private static <T> List<Vertex<T>> breadthFirstSearch(Graph<T> graph,
                                                          List<Vertex<T>> ret,
                                                          Queue<Vertex<T>> q) {
        Set<Vertex<T>> set = new HashSet<>();
        while (!(q.isEmpty())) {
            Vertex<T> temp = q.remove();
            if (set.add(temp)) {
                ret.add(temp);
                for (Edge<T> edge: graph.getAdjList().get(temp)) {
                    if (!(set.contains(edge.getV()))) {
                        q.add(edge.getV());
                    }
                }
            }
        }
        return ret;
    }





    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When deciding which neighbors to visit next from a vertex, visit the
     * vertices in the order presented in that entry of the adjacency list.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
                                            Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("The given graph is null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The given root is not in the"
                    + " graph");
        }
        List<Vertex<T>> ret = new ArrayList<>();
        Set<Vertex<T>> set = new HashSet<>();
        return depthFirstSearch(start, graph, ret, set);
    }

    /**
     * helper method for depth first search
     *
     * @param start the node to start the search from
     * @param graph the graph to search
     * @param ret the List of vertices in the graph
     * @param set the set used to perform the search
     * @param <T> the class of the data in the node
     * @return the list of vertices according to the search
     */
    private static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
                                                          Graph<T> graph,
                                                          List<Vertex<T>> ret,
                                                        Set<Vertex<T>> set) {
        if (set.add(start)) {
            ret.add(start);
            for (Edge<T> inc : graph.getAdjList().get(start)) {
                if (!(set.contains(inc.getV()))) {
                    depthFirstSearch(inc.getV(), graph, ret, set);
                }
            }
        }
        return ret;
    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as it's efficient.
     *
     * You should implement the version of Dijkstra's where you terminate the
     * algorithm once either all vertices have been visited or the PQ becomes
     * empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null, or if start
     *  doesn't exist in the graph.
     * @param <T> the generic typing of the data
     * @param start index representing which vertex to start at (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every other node
     *         in the graph
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("The given graph is null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The given root is not in the"
                    + " graph");
        }
        Map<Vertex<T>, Integer> ret = new HashMap<>();
        for (Vertex<T> v: graph.getVertices()) {
            ret.put(v, Integer.MAX_VALUE);
        }
        ret.replace(start, 0);
        PriorityQueue<Edge<T>> q = new PriorityQueue<>();
        q.add(new Edge<>(start, start, 0));
        Set<Vertex<T>> set = new HashSet<>();
        while (!(q.isEmpty())) {
            Edge<T> edge = q.remove();
            Vertex<T> end = edge.getV();
            if (set.add(end)) {
                for (Edge<T> v : graph.getAdjList().get(end)) {
                    q.add(v);
                    if (ret.get(v.getV()) > (ret.get(end) + v.getWeight())) {
                        ret.put(v.getV(), ret.get(end) + v.getWeight());
                    }
                }
            }
        }
        return ret;
    }



    /**
     * Runs Prim's algorithm on the given graph and return the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * opposite edge to the set as well. This is for testing purposes.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops into the MST.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface, as long as it's efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     */
    public static <T> Set<Edge<T>> prims2(Vertex<T> start, Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("The given graph is null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The given root is not in the"
                    + " graph");

        }

        Set<Edge<T>> es = new HashSet<>();
        Set<Vertex<T>> vs = new HashSet<>();
        PriorityQueue<Edge<T>> q = new PriorityQueue<>();
        vs.add(start);
        while (vs.size() < graph.getVertices().size()) {
            for (Vertex<T> v: vs) {
                for (Edge<T> e: graph.getAdjList().get(v)) {
                    if (!(vs.contains(e.getV()))) {
                        q.add(e);
                    }
                }
            }
            if (q.isEmpty()) {
                return null;
            }
            Edge<T> add = q.remove();
            es.add(add);
            es.add(new Edge<>(add.getV(), add.getU(), add.getWeight()));
            vs.add(add.getV());
            q.clear();
        }
        return es;
    }

    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("The given graph is null");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The given root is not in the"
                    + " graph");

        }

        Set<Edge<T>> es = new HashSet<>();
        Set<Vertex<T>> vs = new HashSet<>();
        PriorityQueue<Edge<T>> q = new PriorityQueue<>();
        vs.add(start);
        for (Edge<T> e: graph.getAdjList().get(start)) {
            if (!(vs.contains(e.getV()))) {
                q.add(e);
            }
        }
        while (vs.size() < graph.getVertices().size()) {
            if (q.isEmpty()) {
                return null;
            }
            Edge<T> add = q.remove();
            if (vs.add(add.getV())) {
                es.add(add);
                es.add(new Edge<>(add.getV(), add.getU(), add.getWeight()));
                for (Edge<T> e: graph.getAdjList().get(add.getV())) {
                    if (!(vs.contains(e.getV()))) {
                        q.add(e);
                    }
                }
            }
        }
        return es;
    }

}