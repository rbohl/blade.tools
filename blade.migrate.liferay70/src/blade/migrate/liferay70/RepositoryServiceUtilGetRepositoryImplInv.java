package blade.migrate.liferay70;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;

@Component(
		property = {
			"file.extension=java",
			"method.type=invocation",
			"method.name=getRepositoryImpl",
			"method.expression=RepositoryLocalServiceUtil",
			"problem.title=RepositoryLocalServiceUtil changes",
			"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#removed-methods-getgrouplocalrepositoryimpl-and-getlocalrepositoryimpl-from-repositorylocalservice-and-repositoryservice",
			"problem.summary=Removed Methods getGroupLocalRepositoryImpl and getLocalRepositoryImpl from RepositoryLocalService and RepositoryService",
			"problem.type=java",
			"problem.tickets=LPS-55566",
		},
		service = FileMigrator.class
)
public class RepositoryServiceUtilGetRepositoryImplInv extends JavaMethodMigrator {

}
