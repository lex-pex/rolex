package tasks.roller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.EnableWebMvc;

import tasks.roller.util.*;
import tasks.roller.util.CsvReader;
import tasks.roller.util.CsvReaderImpl;
import tasks.roller.mappers.mapper.BrBatchMapper;
import tasks.roller.mappers.mapper.BatchMapper;
import tasks.roller.mappers.mapper.ItBatchMapper;
import tasks.roller.mappers.mapper.OrgBatchMapper;
import tasks.roller.models.RoleType;
import tasks.roller.validators.RollerValidator;
import tasks.roller.validators.RollerValidatorImpl;
import rolex.api.RolexContext;
import rolex.object.TaskResult;

@Configuration 
@ComponentScan("tasks.roller") 
@PropertySource("classpath:roller.properties") 
@EnableWebMvc 
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
	public CsvConverter csvBuilder( CsvReader csvReader, RollerValidator rollerValidator ) {
		return new CsvConverterImpl( csvReader, rollerValidator );
	}

	@Bean
	public Roller roler( CsvConverter csvConverter ) {

		Map<RoleType, BatchMapper> mappers = new HashMap<>();

		mappers.put( RoleType.Organizational, new OrgBatchMapper( context ) );
		mappers.put( RoleType.It, new ItBatchMapper( context ) );
		mappers.put( RoleType.Birthrights, new BrBatchMapper( context ) );

		return new Roller( csvConverter, mappers, context, taskResult );

	}
}







