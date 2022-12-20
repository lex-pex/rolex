package tasks.roller.mappers.maps;

import tasks.roller.models.OrgRoleModel;
import rolex.object.Batch;

public class MapModelOrg extends MapModel {
	// ORG 
	public OrgRoleModel orgModel(Batch batch) {
		// Common fields 
		OrgRoleModel model = new OrgRoleModel();
		model = (OrgRoleModel) super.model(model, batch);
		return model;
	}
}
