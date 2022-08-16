package simulator.model;

public abstract class Event implements Comparable<Event> {

	protected int time;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else
			this.time = time;
	}

	int getTime() {
		return time;
	}

	@Override
	public int compareTo(Event o) {
		if(this.time == o.getTime()) return 0;
		else if(this.time < o.getTime()) return -1;
		else return 1;
	}

	abstract void execute(RoadMap map);
}
