package tasks.roller.mappers.maps;

import java.util.List;

import rolex.object.Batch;
import tasks.roller.models.RoleTypeEnum;
import tasks.roller.models.RoleModel;

public class MapModel {
	// Common fields 
	public RoleModel model(RoleModel model, Batch batch) {
		model.setType(RoleTypeEnum.valueOf(batch.getType()));
		model.setRoleName(batch.getName());
		model.setDisplayName(batch.getDisplayName());
		model.setDescription(batch.getDescription("en_US"));
		List<Batch> inherited = batch.getInheritance();
		//TODO : MULTIPLE CHOICE INHERITANCE 
		if(inherited != null) 
			for(Batch b : batch.getInheritance())
				model.setInheritedRoleName(b.getName());
		//TODO : CHECK ON OWNER IDENTITY 
		model.setOwner(batch.getOwner().getName());
		return model;
	}

}
