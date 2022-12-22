package tasks.roller.mappers.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rolex.object.Batch;
import tasks.roller.models.BirthrightsRoleModel;
import lombok.Getter;
import rolex.api.RolexContext;
import tasks.roller.models.RoleModel;
import rolex.object.IdentitySelector;
import rolex.object.Patcher;
import rolex.tools.GeneralException;

@Getter
public class BrBatchMapper implements BatchMapper {

	private final RolexContext context;
	private final CommonBundleMapper commonFieldsMapper;

	public BrBatchMapper(RolexContext context) {
		this.context = context;
		commonFieldsMapper = new CommonBundleMapper(context);
	}

	@Override
	public BirthrightsRoleModel bundleToModel(Batch batch) {
		BirthrightsRoleModel model = new BirthrightsRoleModel();
		commonFieldsMapper.bundleToModel(batch, model);
		Map<String, Object> ea = batch.getExtendedAttributes();
		if(ea != null)
			model.setMarket((String) ea.get(BirthrightsRoleModel.marketAlias));
		else
			model.setMarket((String) batch.getAttribute(BirthrightsRoleModel.marketAlias));
		List<Batch> batchRequirements = batch.getRequirements();
		List<String> modelRequirements = new ArrayList<>();
		for (Batch requiredBatch : batchRequirements)
			modelRequirements.add(requiredBatch.getName());
		model.setRequirements(modelRequirements);
		model.setAssignmentRuleName(getRuleName(batch));
		return model;
	}

	@Override
	public Batch modelToBundle(RoleModel model, Batch batch) throws GeneralException {
		Batch itBatch = commonFieldsMapper.modelToBundle(model, batch);
		BirthrightsRoleModel BirthrightsRoleModel = (BirthrightsRoleModel) model;
		setMarket(BirthrightsRoleModel, batch);
		setRequirements(BirthrightsRoleModel, batch);
		setRule(BirthrightsRoleModel, batch);
		return itBatch;
	}

	//
	// * * * * * * * * * > Batch > to > Model > * * * * * * * * *
	//

	// Patcher-Name from IdentitySelector Wrapper for BirthrightsRoleModel
	private static String getRuleName(Batch batch) {
		IdentitySelector is = batch.getSelector();
		if(is == null) 
			return null;
		Patcher patcher = is.getPatcher();
		if(patcher == null)
			return null;
		return patcher.getName();
	}

	//
	// * * * * * * * * * > Model > to > Batch > * * * * * * * * *
	//

	// Market is set to be inner ISS Extended Attribute 
	private void setMarket(BirthrightsRoleModel model, Batch batch) {
		Map<String, Object> ea = batch.getExtendedAttributes();
		if(ea == null) 
			batch.setAttribute(BirthrightsRoleModel.marketAlias, model.getMarket());
		else
			ea.put(BirthrightsRoleModel.marketAlias, model.getMarket());
	}

	private void setRule(BirthrightsRoleModel model, Batch batch) throws GeneralException {
		IdentitySelector is = batch.getSelector();
		if(is == null)
			is = new IdentitySelector();
		Patcher patcher = null;
		if(model.getAssignmentRuleName() != null && !model.getAssignmentRuleName().equals(""))
			patcher = (Patcher) context.getObjectByName(Patcher.class, model.getAssignmentRuleName());
		is.setPatcher(patcher);
		batch.setSelector(is);
	}

	private void setRequirements(BirthrightsRoleModel model, Batch batch) throws GeneralException {
		List<Batch> updated = batch.getRequirements();
		updated = new ArrayList<>();
		List<String> requirements = model.getRequirements();
		if(requirements != null) {
			for(String name : requirements) {
				Batch b = (Batch) context.getObjectByName(Batch.class, name);
				updated.add(b);
			}
		}
		batch.setRequirements(updated);
	}

}









