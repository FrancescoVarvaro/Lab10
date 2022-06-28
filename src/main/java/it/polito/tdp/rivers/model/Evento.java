package it.polito.tdp.rivers.model;

import java.time.LocalDate;


public class Evento implements Comparable <Evento>{
	public enum EventType{
//		FLUSSO_ENTRATA, //forse l'abbiamo già come dato di partenza (sarebbe la colonna flow)
		FLUSSO_USCITA
	}
	private EventType type;
	private /*LocalDate*/ int tempo;
	private Flow flow;
	
	public Evento(EventType type, int tempo, Flow flow) {
		super();
		this.type = type;
		this.tempo = tempo;
		this.flow = flow;
	}
	
	public void setFlow(Flow flow) {
		this.flow = flow;
	}
	
	public Flow getFlow() {
		return flow;
	}
	
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tempo;
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
		Evento other = (Evento) obj;
		if (tempo != other.tempo)
			return false;
		return true;
	}

	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.tempo-o.tempo;
	}
	
	
}
