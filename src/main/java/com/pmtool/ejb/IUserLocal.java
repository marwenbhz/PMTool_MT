package com.pmtool.ejb;

import java.io.IOException;
import java.util.List;

import javax.ejb.Local;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.JsonParseException;
import com.pmtool.model.*;

@Local
public interface IUserLocal {
	
		
	public void createUser (String tenant_id, UserPost b) throws JsonParseException, IOException;
	public User getUserById (String tenant_id, int id);
	public List<User> getAllUsers(String tenant_id); 
	public void updateUser(String tenant_id, int id, UserPost b) throws ClientProtocolException, IOException;
	public void deleteUser (String tenant_id, int id) throws ClientProtocolException, IOException;
	
}
