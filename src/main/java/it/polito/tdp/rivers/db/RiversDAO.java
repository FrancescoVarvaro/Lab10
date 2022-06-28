package it.polito.tdp.rivers.db;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public River getRiver(River fiume) { // passo il fiume dell'utente e mi ritorna un oggetto sempre di tipo fiume con le info che mi servono
		
			final String sql = "select id, nome, count(*) as numMisure, avg(flow) as avg" // 1 e ultima misurazione --> li prendo poi dalla lista -> lista.get(0 e size-1)
					+ "from river r, flow f "
					+ "where r.id = f.river and f.name(oppure id) = ? (fiume.getName/Id) "
					+ "group by id" // anche se non c'è bisogno dato che abbiamo preso 1 solo fiume
					+ "order by ASC day"; // anche se non c'è bisogno dato che abbiamo preso 1 solo fiume
	
			River rivers = null;
	
			try {
				Connection conn = DBConnect.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, fiume.getName());
				ResultSet res = st.executeQuery();
	
				if (res.next()) {
					rivers = new River(res.getInt("id"), res.getString("nome"));
					rivers.setFlowAvg(res.getDouble("avg"));
					//il numero di misurazioni basta fare fiume.getFLows().size();
				}
	
				conn.close();
				return rivers;
			} catch (SQLException e) {
				//e.printStackTrace();
				throw new RuntimeException("SQL Error");
			}
		}
	
	public List<Flow> getFlows(River river) {
			
			final String sql = "SELECT day, flow FROM flow WHERE river = ? ORDER BY ASC day";
	
			List<Flow> flows = new LinkedList<Flow>();
	
			try {
				Connection conn = DBConnect.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, river.getId());
				ResultSet res = st.executeQuery();
	
				while (res.next()) {
					flows.add(new Flow(res.getDate("day").toLocalDate(),res.getDouble("flow"),river/*ma anche null*/));
				}
				river.setFlows(flows);
				conn.close();
				
			} catch (SQLException e) {
				//e.printStackTrace();
				throw new RuntimeException("SQL Error");
			}
	
			return flows;
		}
}
