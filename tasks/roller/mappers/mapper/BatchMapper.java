package tasks.roller.mappers.mapper;

import rolex.object.Batch;
import tasks.roller.models.RoleModel;
import rolex.tools.GeneralException;

public interface BatchMapper {
	public RoleModel bundleToModel(Batch batch);
	public Batch modelToBundle(RoleModel model, Batch batch) throws GeneralException;
}
