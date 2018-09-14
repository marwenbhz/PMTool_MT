package com.pmtool.ejb;

import java.io.IOException;
import java.util.List;

import javax.ejb.Local;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.JsonParseException;
import com.pmtool.model.*;

@Local
public interface ITeamLocal {
	
		
	public void createTeam (String tenant_id, Team b) throws JsonParseException, IOException;
	public Team getTeamById (String tenant_id, int id);
	public List<Team> getAllTeams(String tenant_id); 
	public void updateTeam(String tenant_id, int id, Team b) throws ClientProtocolException, IOException;
	public void deleteTeam (String tenant_id, int id) throws ClientProtocolException, IOException;
	public List<User> getUsersByTeam (int id, String tenant_id);
}
