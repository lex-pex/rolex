package tasks.roller;

import java.util.Map;

import rolex.object.Batch;
import tasks.roller.util.CsvConverter;
import tasks.roller.mappers.mapper.BatchMapper;
import tasks.roller.models.RoleModel;
import tasks.roller.models.OrgRoleModel;
import rolex.api.RolexContext;
import rolex.object.Filter;
import rolex.object.QueryOptions;
import rolex.object.TaskResult;
import rolex.tools.GeneralException;
import lombok.RequiredArgsConstructor;
import tasks.roller.models.RoleTypeEnum;

@RequiredArgsConstructor
public class Roller {

	private final CsvConverter csvConverter;
	private final Map<RoleTypeEnum, BatchMapper> bundleMapperMap;
	private final RolexContext context;
	private final TaskResult taskResult;
	private Map<String, RoleModel> models;

	public void buildRoles() {

		// All Role Models from csv 
		models = csvConverter.getModels();

		// process 'Organizational' containers
		models.entrySet().stream()
				.filter(entry -> entry.getValue().getType().equals( RoleTypeEnum.Organizational ))
					.forEach(entry -> processOrganizationalRole( entry.getValue() ));

		// process 'It' tasks.roler.models
		models.entrySet().stream()
			.filter(entry -> entry.getValue().getType().equals( RoleTypeEnum.It ))
				.forEach(entry -> processRoleModel( entry.getValue() ));

		// process 'Birthrights' tasks.roler.models
		models.entrySet().stream()
			.filter(entry -> entry.getValue().getType().equals( RoleTypeEnum.Birthrights ))
				.forEach(entry -> processRoleModel( entry.getValue() ));
	}

	private void processOrganizationalRole(RoleModel model) {
		if (model.getInheritedRoleName() != null) {
			Filter filter = Filter.eq("name", model.getInheritedRoleName());
			QueryOptions qOptions = new QueryOptions(filter);
			int count = 0;
			try {
				count = context.countObjects(Batch.class, qOptions);
			} catch (GeneralException e) {
				e.printStackTrace();
			}
			if (count == 0) {
				OrgRoleModel orgModel  = (OrgRoleModel) models.get(model.getInheritedRoleName());
				processRoleModel(orgModel);
			}
		}
		processRoleModel(model);
	}

	// Convert to Model, Compare tasks.roler.models, Create bundle in case of lack
	private void processRoleModel(RoleModel model) {
		BatchMapper batchMapper =  bundleMapperMap.get( model.getType() );
		try {
			Batch batch = (Batch) context.getObjectByName( Batch.class, model.getName() );
			if (batch == null) {
				batch = batchMapper.modelToBundle(model, new Batch());
				context.saveObject(batch);
				context.commitTransaction();
			} else {
				RoleModel bundleModel = batchMapper.bundleToModel(batch);
				if (!bundleModel.equals(model)) {
					batch = batchMapper.modelToBundle(model, batch);
					context.saveObject(batch);
					context.commitTransaction();
				}
			}
		} catch (GeneralException e) {
			taskResult.addMessage("Error comparing tasks.roler.models: " + e.getMessage());
			taskResult.setCompletionStatus(TaskResult.CompletionStatus.Error);
			e.printStackTrace();
		}
	}

	public Roller(CsvConverter csvConverter, Map<RoleTypeEnum, BatchMapper> bundleMapperMap, RolexContext context, TaskResult taskResult) {
		this.csvConverter = csvConverter;
		this.bundleMapperMap = bundleMapperMap;
		this.context = context;
		this.taskResult = taskResult;
	}
}


