package blade.migrate.liferay70;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;

@Component(
		property = {
			"file.extension=java",
			"method.type=invocation",
			"method.name=updateTemplate",
			"method.expression=DDMTemplateLocalServiceUtil",
			"problem.title=DDMTemplateLocalService add new parm userId",
			"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#added-userid-parameter-to-update-operations-of-ddmstructurelocalservice-and-ddmtemplatelocalservice",
			"problem.summary=Added userId Parameter to Update Operations of DDMStructureLocalService and DDMTemplateLocalService",
			"problem.type=java",
			"problem.tickets=LPS-50939",
		},
		service = FileMigrator.class
)
public class DDMTemplateUpdateTemplateDecl extends JavaMethodMigrator {

}
