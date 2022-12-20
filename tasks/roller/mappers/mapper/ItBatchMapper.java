package tasks.roller.mappers.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rolex.object.Batch;
import tasks.roller.models.ITRoleModel;
import tasks.roller.models.RoleModel;
import lombok.Getter;
import rolex.api.RolexContext;
import rolex.object.Application;
import rolex.object.Filter;
import rolex.object.Profile;
import rolex.tools.GeneralException;

@Getter
public class ItBatchMapper implements BatchMapper {

	private final RolexContext context;
	private final CommonBundleMapper commonFieldsMapper;

	public ItBatchMapper(RolexContext context) {
		this.context = context;
		commonFieldsMapper = new CommonBundleMapper( context );
	}

	@Override
	public ITRoleModel bundleToModel(Batch batch) {
		ITRoleModel model = new ITRoleModel();
		commonFieldsMapper.bundleToModel(batch, model);
		Map<String, String> profile = getProfileFromBundle(batch);
		model.setApplicationName(profile.get("application"));
		model.setFilterExpression(profile.get("expression"));
		return model;
	}

	@Override
	public Batch modelToBundle(RoleModel model, Batch batch) throws GeneralException {
		Batch itBatch = commonFieldsMapper.modelToBundle(model, batch);
		ITRoleModel itModel = (ITRoleModel) model;
		processProfile(itModel, batch);
		return itBatch;
	}

	// Batch's Profile
	private void processProfile(ITRoleModel model, Batch batch) throws GeneralException {
		List<Profile> profiles = batch.getProfiles();
		if(profiles == null)
			batch.assignProfiles( createNewProfiles( model, batch) );
		else
			parseProfiles( profiles, model );
	}

	// Create new profiles 
	private List<Profile> createNewProfiles(ITRoleModel model, Batch batch) throws GeneralException {
		List<Profile> profiles = new ArrayList<>();
		Profile profile = new Profile();
		profile.setApplication(getApplication(model.getApplicationName()));
		List<Filter> filters = new ArrayList<>();
		Filter filter = Filter.compile( model.getFilterExpression() );
		filters.add(filter);
		profile.setConstraints( filters );
		profiles.add(profile);
		return profiles;
	}

	// Parse existing Profiles 
	private void parseProfiles(List<Profile> profiles, ITRoleModel model) throws GeneralException {
		for(Profile profile : profiles) { 
			Application app = profile.getApplication();
			if (app != null)
				if( ! app.getName().equals(model.getApplicationName()))
					profile.setApplication(getApplication(model.getApplicationName()));
			List<Filter> filters = profile.getConstraints();
			boolean noSuchExpression = true;
			for(Filter filter : filters) {
				String bundleExpression = filter.getExpression();
				String modelExpression = model.getFilterExpression();
				if(bundleExpression != null && modelExpression != null) {
					if(modelExpression.equals(bundleExpression)) {
						noSuchExpression = false;
					}
				}
			}
			if( noSuchExpression ) {
				addFilter(profile, model.getFilterExpression());
			}
		}
	}

	// Filter duty 
	private void addFilter(Profile profile, String expression) {
		if(expression != null) {
			Filter filter = Filter.compile(expression);
			if (filter != null) {
				List<Filter> filters = profile.getConstraints();
				if(filters == null) {
					filters = new ArrayList<>();
				} else if (!filters.contains(filter)) {
					filters.add(filter);
				}
				profile.setConstraints(filters);
			}
		}
	}

	// Application withdrawal
	private Application getApplication(String name) throws GeneralException {
		return (Application) context.getObjectByName(Application.class, name);
	}

	// Parse Profile( Application and Filter) into a Map 
	private static Map<String, String> getProfileFromBundle(Batch batch) {
		Map<String, String> map = new HashMap<>();
		List<Profile> profiles = batch.getProfiles();
		if (profiles != null)
			for (Profile profile : profiles) { 
				Application app =  profile.getApplication();
				if(app != null)
					map.put("application", profile.getApplication().getName());
				for (Filter filter : profile.getConstraints()) {
					map.put("expression", filter.getExpression());
				}
			}
		return map;
	}
}
