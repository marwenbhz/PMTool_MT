package com.pmtool.ejb;

import java.io.IOException;
import java.util.List;

import javax.ejb.Local;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.JsonParseException;
import com.pmtool.model.*;

@Local
public interface IBanqueLocal {
	
		
	public void createBanque (String tenant_id, Banque b) throws JsonParseException, IOException;
	public Banque getBanqueById (String tenant_id, int id);
	public List<Banque> getAllBanques(String tenant_id); 
	public void updateBanque(String tenant_id, int id, Banque b) throws ClientProtocolException, IOException;
	public void deleteBanque (String tenant_id, int id) throws ClientProtocolException, IOException;
	public List<Project> getProjectsByBank (int id, String tenant_id); 
	
}
