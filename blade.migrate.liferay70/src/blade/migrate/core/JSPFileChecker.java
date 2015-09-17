package blade.migrate.core;

import blade.migrate.api.MigrationConstants;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jst.jsp.core.internal.java.JSPTranslation;
import org.eclipse.jst.jsp.core.internal.java.JSPTranslator;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.NodeList;

public class JSPFileChecker extends JavaFileChecker {

	public JSPFileChecker(File file) {
		super(file);

		getTranslation(file);
	}

	private JSPTranslationPrime createJSPTranslation() {
		IDOMModel jspModel = null;

		try {
			final IFile jspFile = getWorkspaceHelper()
					.createIFile(MigrationConstants.MIGRATION_HELPER_PROJECT_NAME, getFile());

			jspModel = (IDOMModel) StructuredModelManager.getModelManager()
					.getModelForRead(jspFile);
			final IDOMDocument domDocument = jspModel.getDocument();
			final IDOMNode domNode = (IDOMNode) domDocument
					.getDocumentElement();

			final IProgressMonitor npm = new NullProgressMonitor();
			final JSPTranslator translator = new JSPTranslator();

			if (domNode != null) {
				translator.reset((IDOMNode) domDocument.getDocumentElement(),
						npm);
			} else {
				translator.reset((IDOMNode) domDocument.getFirstChild(), npm);
			}

			translator.translate();

			final IJavaProject javaProject = JavaCore
					.create(jspFile.getProject());

			_translation = new JSPTranslationPrime(javaProject, translator,
					jspFile);

			return _translation;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jspModel != null) {
				jspModel.releaseFromRead();
			}
		}

		return null;
	}

	@Override
	protected SearchResult createSearchResult(int startOffset, int endOffset,
			int startLine, int endLine, boolean fullMatch) {

		IDOMModel jspModel = null;

		try {
			final JSPTranslationPrime translation = getTranslation(getFile());

			final int jspStartOffset = translation.getJspOffset(startOffset);
			final int jspEndOffset = translation.getJspOffset(endOffset);

			jspModel = (IDOMModel) StructuredModelManager.getModelManager()
					.getModelForRead(translation._jspFile);
			final IDOMDocument domDocument = jspModel.getDocument();

			final IStructuredDocument structuredDocument = domDocument
					.getStructuredDocument();
			final int jspStartLine = structuredDocument
					.getLineOfOffset(jspStartOffset) + 1;
			final int jspEndLine = structuredDocument
					.getLineOfOffset(jspEndOffset) + 1;

			return super.createSearchResult(jspStartOffset, jspEndOffset,
					jspStartLine, jspEndLine, fullMatch);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jspModel != null) {
				jspModel.releaseFromRead();
			}
		}

		return super.createSearchResult(startOffset, endOffset, startLine,
				endLine, fullMatch);
	}

	@Override
	protected char[] getJavaSource() {
		JSPTranslation translation = getTranslation(getFile());

		return translation.getJavaText().toCharArray();
	}

	private JSPTranslationPrime getTranslation(File file) {
		try {
			synchronized (_map) {
				WeakReference<JSPTranslationPrime> translationRef = _map.get(file);

				if (translationRef == null || translationRef.get() == null) {
					final JSPTranslationPrime newTranslation = createJSPTranslation();

					_map.put(file, new WeakReference<JSPTranslationPrime>(newTranslation));

					_translation = newTranslation;
				}
				else {
					_translation = translationRef.get();
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}

		return _translation;
	}

	private WorkspaceHelper getWorkspaceHelper() {
		if (_workspaceHelper == null) {
			_workspaceHelper = new WorkspaceHelper();
		}

		return _workspaceHelper;
	}

	public List<SearchResult> findJSPTags(String tagName , String[] attrNames , String[] attrValue) {

		final List<SearchResult> searchResults = new ArrayList<>();

		final IFile jspFile = getTranslation(getFile())._jspFile;

		try {
			final IDOMModel jspModel = (IDOMModel) StructuredModelManager
					.getModelManager().getModelForRead(jspFile);

			final IDOMDocument domDocument = jspModel.getDocument();

			final IStructuredDocument structuredDocument = domDocument
					.getStructuredDocument();

			final NodeList nodeList = domDocument.getElementsByTagName(tagName);

			for (int i = 0; i < nodeList.getLength(); i++) {
				final IDOMNode domNode = (IDOMNode) nodeList.item(i);

				if (attrNames == null) {
					int startOffset = domNode.getStartOffset();
					int endOffset = domNode.getEndOffset();
					int jspStartLine = structuredDocument.getLineOfOffset(startOffset) + 1;
					int jspEndLine = structuredDocument.getLineOfOffset(endOffset) + 1;
					searchResults.add(super.createSearchResult(
							startOffset,endOffset, jspStartLine,jspEndLine, true));

				} else {
					for (int j = 0; j < attrNames.length; j++) {
						final IDOMNode attrNode = (IDOMNode) domNode
								.getAttributes().getNamedItem(attrNames[j]);

						if (attrNode != null) {
							if (attrValue != null) {
								if (!(attrValue[j].equals(attrNode.getNodeValue())))
									continue;
							}

							int startOffset = attrNode.getStartOffset();
							int endOffset = attrNode.getEndOffset();
							int jspStartLine = structuredDocument.getLineOfOffset(startOffset) + 1;
							int jspEndLine = structuredDocument.getLineOfOffset(endOffset) + 1;

							searchResults.add(super.createSearchResult(
									startOffset, endOffset, jspStartLine,jspEndLine, true));
						}
					}
				}
			}
		} catch (IOException | CoreException e) {
			e.printStackTrace();
		}

		return searchResults;
	}

	/**
	 * A simple subclass to hold a reference to the original jspFile
	 */
	private class JSPTranslationPrime extends JSPTranslation {

		private IFile _jspFile;

		public JSPTranslationPrime(IJavaProject javaProject,
				JSPTranslator translator, IFile jspFile) {
			super(javaProject, translator);

			_jspFile = jspFile;
		}
	}

	private static Map<File, WeakReference<JSPTranslationPrime>> _map = new WeakHashMap<>();

	private JSPTranslationPrime _translation;
	private WorkspaceHelper _workspaceHelper;
}
