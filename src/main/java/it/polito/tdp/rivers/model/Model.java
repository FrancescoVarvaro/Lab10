package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	private RiversDAO dao;
	private List <River> fiumi = null;
	private River fiumeUtente;
	private Simulazione sim;
	
	public Model() {
		dao = new RiversDAO();
	}
	
	public List<River> getAllRiver(){
		if(fiumi == null) {
			fiumi = dao.getAllRivers();
		}
		return fiumi;
	}
	
	public void caricaFlussi(River r) {
		fiumeUtente=dao.getRiver(r);
		dao.getFlows(fiumeUtente); // creo la lista di flow (ordinata) all'interno del mio oggetto
	}
	
	public int getNmisure() {
		return fiumeUtente.getFlows().size();
	}
	public LocalDate getPrimaMisurazione() {
		return fiumeUtente.getFlows().get(0).getDay();
	}
	public LocalDate getSecondaMisurazione() {
		return fiumeUtente.getFlows().get(fiumeUtente.getFlows().size() - 1).getDay();
	}
	
	public void Simula(double k) {
		sim = new Simulazione(k, fiumeUtente);
		sim.loadQueue();
		sim.run();
	}
	
	public int getInsufficienza() {
		return sim.getGiorniInsufficienza();
	}
	public double getCmed() {
		return sim.getCmed();
	}
}
