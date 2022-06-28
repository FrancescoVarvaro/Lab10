package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.model.Evento.EventType;

public class Simulazione {
	
	//parametri ingresso
	private double capienzaTot;
	private River fiume;
	private double probabilita = 0.05;
	private double flusso_minimo_uscita;
//	il fiume che passo come parametro deve avere gia aggiunto la lista di flows ordinata per data
	public Simulazione(double k, River fiume) {
		this.fiume = fiume;
		capienzaTot = k*30*fiume.getFlowAvg();
		flusso_minimo_uscita = 0.8 * fiume.getFlowAvg();
		C = capienzaTot/2;
		queue = new PriorityQueue<>();
	}
	
	// valori di output
	private int nGgErogazioneInsufficiente=0;
	
	// coda degli eventi
	PriorityQueue<Evento> queue; 
	
	// stato del mondo
	private double C;
	private List<Double> occupazioneBacino = new ArrayList<>();
	
	public void loadQueue() {
		int nMisureFiume = fiume.getFlows().size(); // il numero di misure mi corrispondono anche al numero di giorni
//		fiume.getFlow() deve ritornare la lista di Flow in ordine temporale
		for(int tempo = 0; tempo < nMisureFiume; tempo++) {
			Flow flussoInEntrata = fiume.getFlows().get(tempo);
			Evento e = new Evento(EventType.FLUSSO_USCITA, tempo, flussoInEntrata);
			this.queue.add(e);
		}
		
		
	}
	public void run() {
		while(!queue.isEmpty()) {
			Evento e = queue.poll();
			processEvent(e);
		}
	}
	private void processEvent(Evento e) {
		
		double flussoOut = flusso_minimo_uscita;

		if (Math.random() <= probabilita) {
			flussoOut = 10 * flusso_minimo_uscita;
		}
		
		double flussoIn = e.getFlow().getFlow();
		
		if(flussoIn >= flussoOut) {
			C = C + (flussoIn-flussoOut);
		}else
			C = C - (flussoOut-flussoIn);
		
		if(C > capienzaTot)
			C = capienzaTot;
		
		if(C < flussoOut) {
			nGgErogazioneInsufficiente++;
			C = 0;
		}
		
		occupazioneBacino.add(C);
	}
	
	public double getCmed() {
		double qtaCapacitaMedia = 0;
		for(double d : occupazioneBacino) {
			qtaCapacitaMedia += d;
		}
		qtaCapacitaMedia = qtaCapacitaMedia/occupazioneBacino.size();
		return qtaCapacitaMedia;
	}
	
	public int getGiorniInsufficienza() {
		return nGgErogazioneInsufficiente;
	}
	
	
}
