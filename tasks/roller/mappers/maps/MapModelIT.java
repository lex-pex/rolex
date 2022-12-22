package tasks.roller.mappers.maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rolex.object.Batch;
import tasks.roller.models.ITRoleModel;
import tasks.roller.models.RoleModel;
import rolex.object.Filter;
import rolex.object.Profile;
import rolex.object.Application;

public class MapModelIT extends MapModel {
	// IT 
	public RoleModel itModel(Batch batch) {
		ITRoleModel model = new ITRoleModel();
		model  = (ITRoleModel) super.model(model, batch);
		Map<String, String> profile = getProfile(batch);
		model.setApplicationName(profile.get("app"));
		model.setFilterExpression(profile.get("expression"));
		return model;
	}

	// Parse Profile on Application and Filter instances for ITModel 
	private static Map<String, String> getProfile(Batch batch) {
		Map<String, String> map = null;
		map = new HashMap<>();
		List<Profile> profiles = batch.getProfiles();
		if (profiles != null)
			for (Profile profile : profiles) {   // TODO : MULTIPLE PROFILES 
				Application app =  profile.getApplication();
				if(app != null)
					map.put("app", profile.getApplication().getName());
				for (Filter filter : profile.getConstraints()) {   // TODO : MULTIPLE FILTERS 
					map.put("expression", filter.getExpression());
				}
			}
		return map;
	}
}


