package blade.migrate.api;

public interface MigrationConstants {

	String MARKER_TYPE = "com.liferay.ide.project.core.MigrationProblemMarker";

	String MARKER_ATTRIBUTE_URL = "migrationProblem.url";
	String MARKER_ATTRIBUTE_SUMMARY = "migrationProblem.summary";
	String MARKER_ATTRIBUTE_TYPE = "migrationProblem.type";
	String MARKER_ATTRIBUTE_TICKET = "migrationProblem.ticket";
	String MARKER_ATTRIBUTE_RESOLVED = "migrationProblem.resolved";
	String MARKER_ATTRIBUTE_TIMESTAMP = "migrationProblem.timestamp";
	String MARKER_ATTRIBUTE_AUTOCORRECTCONTEXT = "migrationProblem.autoCorrectContext";

	String HELPER_PROJECT_NAME = "__migration_helper__";

}
