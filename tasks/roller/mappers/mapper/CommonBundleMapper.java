package tasks.roller.mappers.mapper;


import java.util.ArrayList;
import java.util.List;

import rolex.object.Batch;
import tasks.roller.models.RoleTypeEnum;
import tasks.roller.models.RoleModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import rolex.api.RolexContext;
import rolex.object.Identity;
import rolex.tools.GeneralException;

@Getter
@RequiredArgsConstructor
public class CommonBundleMapper {

	private final RolexContext context;

	public RoleModel bundleToModel(Batch batch, RoleModel model) {
		model.setType(RoleTypeEnum.valueOf(batch.getType()));
		model.setRoleName(batch.getName());
		model.setOwner(batch.getOwner().getName());
		model.setDisplayName(batch.getDisplayName());
		model.setDescription(batch.getDescription("en_US"));
		List<Batch> inherited = batch.getInheritance();
		if(inherited != null) 		// MULTIPLE CHOICE INHERITANCE ( here the last one ) 
			for(Batch b : batch.getInheritance())
				model.setInheritedRoleName(b.getName());
		return model;
	}

	//
	// * * * * * * * * * > Model > To > Batch > * * * * * * * * *
	//

	public Batch modelToBundle(RoleModel model, Batch batch) throws GeneralException {
		batch.setType(model.getType().toString());
		batch.setName(model.getName());
		setOwner(model, batch);
		setDisplayName(model, batch);
		setDescription(model, batch);
		setInheritance(model, batch);
		return batch;
	}

	// Handle Owner 
	public void setOwner(RoleModel model, Batch batch) throws GeneralException {
		Identity owner = (Identity) context.getObjectByName(Identity.class, model.getOwner());
		batch.setOwner(owner);
	}

	// The (Batch/CSV)-Model already has a description case
	public void setDisplayName(RoleModel model, Batch batch) {
		if(model.getDisplayName() == null)
			batch.setDisplayName(model.getName());
		else 
			batch.setDisplayName(model.getDisplayName());
	}

	// Handle description if exists case 
	private void setDescription(RoleModel model, Batch batch) {
		if(model.getDescription() != null && !model.getDescription().equals(""))
			batch.addDescription("en_US", model.getDescription());
	}

	// Handle Inherited Role 
	public void setInheritance(RoleModel model, Batch batch) throws GeneralException {
		if (model.getInheritedRoleName() != null) {
			Batch inherited = (Batch) context.getObjectByName(Batch.class, model.getInheritedRoleName());
			batch.setInheritance( asList( inherited ) );
		}
	}

	/**
	 * Wrapper to support multiple inheritance
	 */
	private List<Batch> asList(Batch batch) {
		List<Batch> list = new ArrayList<>();
		list.add(batch);
		return list;
	}

	public CommonBundleMapper(RolexContext context) {
		this.context = context;
	}
}


