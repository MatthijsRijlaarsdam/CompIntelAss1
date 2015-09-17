/**
 * Created by Niek on 9/15/2015.
 */
public class Edge {

    private Neuron from;
    private Neuron to;
    private double weight;

    public Edge(Neuron from, Neuron to) {
        this.from = from;
        this.to = to;
        this.weight = Math.random();
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

    public double getWeight() {
        return weight;
    }
    public Neuron opposite(Neuron neuron) {
        if (neuron.equals(from)) {
            return to;
        } else if(neuron.equals(to)) {
            return from;
        } else {
            return null;
        }
    }

}
