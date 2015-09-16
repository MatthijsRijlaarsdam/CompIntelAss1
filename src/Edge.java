/**
 * Created by Niek on 9/15/2015.
 */
public class Edge {

    private Neuron from;
    private Neuron to;
    public Edge(Neuron from, Neuron to) {
        this.from = from;
        this.to = to;
    }

    public Neuron getFrom() {
        return from;
    }

    public Neuron getTo() {
        return to;
    }

    @Override
    public boolean equals(Object obj) {
        Edge e = (Edge)obj;
        return e.from == from && e.to == to;
    }

}
