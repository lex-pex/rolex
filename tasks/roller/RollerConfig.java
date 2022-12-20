package tasks.roller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tasks.roller.util.*;
import tasks.roller.util.CsvReader;
import tasks.roller.util.CsvReaderImpl;
import tasks.roller.mappers.mapper.BrBatchMapper;
import tasks.roller.mappers.mapper.BatchMapper;
import tasks.roller.mappers.mapper.ItBatchMapper;
import tasks.roller.mappers.mapper.OrgBatchMapper;
import tasks.roller.models.RoleTypeEnum;
import tasks.roller.validators.RollerValidator;
import tasks.roller.validators.RollerValidatorImpl;
import rolex.api.RolexContext;
import rolex.object.TaskResult;

@Configuration
public class RollerConfig {

	public static RolexContext context;
	public static TaskResult taskResult;
	public static String csvPath;

	@Bean
	public CsvReader reader() {
		return new CsvReaderImpl( csvPath, taskResult );
	}

	@Bean
	public RollerValidator rolerValidator() {
		return new RollerValidatorImpl( context, taskResult );
	}

	@Bean
	public CsvConverter csvBuilder(CsvReader csvReader, RollerValidator rollerValidator) {
		return new CsvConverterImpl(csvReader, rollerValidator);
	}

	@Bean
	public Roller roler(CsvConverter csvConverter) {

		Map<RoleTypeEnum, BatchMapper> map = new HashMap<>();

		map.put(RoleTypeEnum.Organizational, new OrgBatchMapper(context));
		map.put(RoleTypeEnum.It, new ItBatchMapper(context));
		map.put(RoleTypeEnum.Birthrights, new BrBatchMapper(context));

		return new Roller(csvConverter, map, context, taskResult );

	}
}







