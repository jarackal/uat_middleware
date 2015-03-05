package com.investingchannel.uat.webservice.datastore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Pattern;

import com.investingchannel.uat.webservice.util.SiteTargetValuesImpl;
import com.investingchannel.uat.webservice.util.TargetCollection;
import com.investingchannel.uat.webservice.util.TargetCollectionImpl;

public class DatabaseStoreImpl extends BaseDataStoreImpl implements DatabaseStore {

	
	public DatabaseStoreImpl() {
	}	
	
	public void getAutoTargets() {
			
		String sql = 	"SELECT auto_targets.sitename, auto_targets.url_key, targets.targetkey "+
						"FROM auto_targets "+
							"JOIN targets "+
							"ON auto_targets.target_id = targets.id "+
						"ORDER BY priority ASC";
		
		Connection connection = getDatabaseConnection();
		if (connection == null)
			return;
		
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			// created a hashtable of htTarget
			Hashtable<String, ArrayList<TargetCollection>> htTarget = new Hashtable<String, ArrayList<TargetCollection>>();
			ArrayList<TargetCollection> targetCollections = null;
			while(resultSet.next()) {

				String siteName = resultSet.getString("sitename");

				if(siteName == null)
					continue;
				
				
				String target = resultSet.getString("targetkey");
				String url_key = resultSet.getString("url_key");
				Pattern urlPattern = Pattern.compile(url_key);

				if(htTarget.get(siteName) == null)
				{
					targetCollections = new ArrayList<TargetCollection>();
					htTarget.put(siteName, targetCollections);
				}
				else 
					targetCollections = htTarget.get(siteName);
				
				TargetCollection tc = new TargetCollectionImpl();
				tc.setSitename(siteName);
				tc.setTarget(target);
				tc.setUrlkey(urlPattern);
				targetCollections.add(tc);
			}

			SiteTargetValuesImpl activeSiteTargetValues = SiteTargetValuesImpl.getInstance();
			activeSiteTargetValues.setSiteTargetValue(htTarget);
			
			resultSet.close();
			preparedStatement.close();
			connection.close();
			System.out.println("Data Added");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}