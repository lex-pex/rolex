package tasks.roller.mappers.mapper;


import rolex.object.Batch;
import tasks.roller.models.RoleModel;
import tasks.roller.models.OrgRoleModel;
import rolex.api.RolexContext;
import rolex.tools.GeneralException;


public class OrgBatchMapper implements BatchMapper {

	private final RolexContext context;
	private final CommonBundleMapper commonFieldsMapper;

	public OrgBatchMapper(RolexContext context ) {
		this.context = context;
		commonFieldsMapper = new CommonBundleMapper( context );
	}

	@Override
	public OrgRoleModel bundleToModel( Batch batch) {
		OrgRoleModel model = new OrgRoleModel();
		commonFieldsMapper.bundleToModel(batch, model );
		return model;
	}

	@Override
	public Batch modelToBundle(RoleModel model, Batch batch) throws GeneralException {
		Batch orgBatch = commonFieldsMapper.modelToBundle( model, batch);
		return orgBatch;
	}
}




