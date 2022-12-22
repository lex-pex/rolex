package tasks.roller.mappers.maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rolex.object.Batch;
import tasks.roller.models.BirthrightsRoleModel;
import tasks.roller.models.RoleModel;
import rolex.object.IdentitySelector;
import rolex.object.Patcher;

public class MapModelBR extends MapModel {
	// BR 
	public RoleModel brModel(Batch batch) {
		// Common fields 
		BirthrightsRoleModel model = new BirthrightsRoleModel();
		model = (BirthrightsRoleModel) super.model( model, batch);
		Map<String, Object> ea = batch.getExtendedAttributes();
		if(ea != null)
			model.setMarket((String) ea.get(BirthrightsRoleModel.marketAlias));
		else
			model.setMarket((String) batch.getAttribute(BirthrightsRoleModel.marketAlias));
		// Requirements 
		List<Batch> batchRequirements = batch.getRequirements();
		List<String> modelRequirements = new ArrayList<>();
		for (Batch requiredBatch : batchRequirements)
			modelRequirements.add(requiredBatch.getName());
		model.setRequirements(modelRequirements);
		// Patcher
		model.setAssignmentRuleName(getRuleName(batch));
		return model;
	}

	// Patcher-Name from IdentitySelector Wrapper for BirthrightsRoleModel
	private static String getRuleName(Batch batch) {
		IdentitySelector is = batch.getSelector();
		if(is == null) 
			return null;
		Patcher patcher = is.getPatcher();
		if( patcher == null )
			return null;
		return patcher.getName();
	}
}




