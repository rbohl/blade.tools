package blade.migrate.liferay70;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.JavaFileMigrator;
import blade.migrate.core.SearchResult;

@Component(
		property = {
			"file.extensions=java",
			"problem.title=Added Required Parameter groupId for Adding Tags, Categories, and Vocabularies",
			"problem.summary=The API for adding tags, categories, and vocabularies now requires passing the groupId parameter. Previously, it had to be included in the ServiceContext parameter passed to the method.",
			"problem.tickets=LPS-54570",
			"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#added-required-parameter-groupid-for-adding-tags-categories-and-vocabularies"
		},
		service = FileMigrator.class
	)

public class RequiredParamGroupId  extends JavaFileMigrator {

	@Override
	protected List<SearchResult> searchJavaFile(File file) {
		final JavaFileChecker javaFileChecker = new JavaFileChecker(file);
		List<SearchResult>  result  = new ArrayList<SearchResult>();

		result.addAll( javaFileChecker.findMethodInvocations(null,"AssetTagServiceUtil","addTag",new String[]{"String","String[]","ServiceContext"}));
		result.addAll(javaFileChecker.findMethodInvocations(null,"AssetTagLocalServiceUtil","addTag",new String[]{"long","String[]","ServiceContext"}));

		result.addAll(javaFileChecker.findMethodInvocations(null,"AssetCategoryServiceUtil","addCategory",new String[]{"long","Map<Locale,String>","Map<Locale,String>","long","String[]","ServiceContext"}));
		result.addAll(javaFileChecker.findMethodInvocations(null,"AssetCategoryServiceUtil","addCategory",new String[]{"String","long","ServiceContext"}));
		result.addAll(javaFileChecker.findMethodInvocations(null,"AssetCategoryLocalServiceUtil","addCategory",new String[]{"long","long","Map<Locale,String>","Map<Locale,String>","long","String[]","ServiceContext"}));
		result.addAll(javaFileChecker.findMethodInvocations(null,"AssetCategoryLocalServiceUtil","addCategory",new String[]{"long","String","long","ServiceContext"}));

		result.addAll(javaFileChecker.findMethodInvocations(null,"AssetVocabularyServiceUtil","addVocabulary",new String[]{"Map<Locale,String>","Map<Locale,String>","String","ServiceContext"}));
		result.addAll(javaFileChecker.findMethodInvocations(null,"AssetVocabularyServiceUtil","addVocabulary",new String[]{"String","Map<Locale,String>","Map<Locale,String>","String","ServiceContext"}));
		result.addAll(javaFileChecker.findMethodInvocations(null,"AssetVocabularyServiceUtil","addVocabulary",new String[]{"String","ServiceContext"}));
		result.addAll(javaFileChecker.findMethodInvocations(null,"AssetVocabularyLocalServiceUtil","addVocabulary",new String[]{"long","Map<Locale,String>","Map<Locale,String>","String","ServiceContext"}));
		result.addAll(javaFileChecker.findMethodInvocations(null,"AssetVocabularyLocalServiceUtil","addVocabulary",new String[]{"long","String","Map<Locale,String>","Map<Locale,String>","String","ServiceContext"}));
		result.addAll(javaFileChecker.findMethodInvocations(null,"AssetVocabularyLocalServiceUtil","addVocabulary",new String[]{"long","String","ServiceContext"}));

		result.addAll(javaFileChecker.findMethodInvocations(null,"JournalFolderServiceUtil","updateFolder",new String[]{"long","long","String","String","boolean","ServiceContext"}));
		result.addAll(javaFileChecker.findMethodInvocations(null,"JournalFolderLocalServiceUtil","updateFolder",new String[]{"long","long","long","String","String","boolean","ServiceContext"}));
		return  result;
	}
}
