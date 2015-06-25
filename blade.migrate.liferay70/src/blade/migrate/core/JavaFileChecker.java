package blade.migrate.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

/**
 * check inside the java file to see if there is  imports , methods and so on.
 * @author 
 *
 */
public class JavaFileChecker {

	/**
	 * initialize the Checker 
	 * @param file
	 */
	public JavaFileChecker(File file) {
		this.file = file;
		this.fileHelper = new FileHelper();

		try {
			this.ast = createJavaClassVisitor();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public SearchResult findImport(final String importName) {
		final List<SearchResult> methodResults = new ArrayList<>();

		ast.accept(new ASTVisitor() {

			@Override
			public boolean visit(ImportDeclaration node) {
				if (importName.equals(node.getName().toString())) {
					int startLine = ast.getLineNumber(node.getName()
						.getStartPosition());
					int startOffset = node.getName().getStartPosition();
					int endLine = ast.getLineNumber(node.getName()
						.getStartPosition() + node.getName().getLength());
					int endOffset = node.getName().getStartPosition() +
						node.getName().getLength();

					methodResults.add(new SearchResult(file, startOffset,
						endOffset, startLine, endLine));
				}

				return false;
			};
		});

		if (0 != methodResults.size()) {
			return methodResults.get(0);
		}

		return null;
	}

	public List<SearchResult> findMethodDeclartion(
		final String name, final String[] params) {

		final List<SearchResult> methodResults = new ArrayList<>();

		ast.accept(new ASTVisitor() {

			@Override
			public boolean visit(MethodDeclaration node) {
				boolean sameParmSize = true;
				String methodName = node.getName().toString();
				List<?> parmsList = node.parameters();

				if (name.equals(methodName) &&
					params.length == parmsList.size()) {

					for (int i = 0; i < params.length; i++) {
						if (!(params[i].trim().equals(parmsList.get(i)
								.toString()
								.substring(0, params[i].trim().length())))) {
							sameParmSize = false;
							break;
						}
					}
				} else {
					sameParmSize = false;
				}

				if (sameParmSize) {
					final int startLine = ast.getLineNumber(node.getName()
						.getStartPosition());
					final int startOffset = node.getName().getStartPosition();
					node.accept(new ASTVisitor() {

						@Override
						public boolean visit(Block node) {

							// SimpleName parent can not be MarkerAnnotation and
							// SimpleType
							// SingleVariableDeclaration node contains the
							// parms's type

							int endLine = ast.getLineNumber(node
								.getStartPosition());
							int endOffset = node.getStartPosition();
							methodResults
									.add(new SearchResult(file, startOffset,
										endOffset, startLine, endLine));

							return false;
						};
					});
				}

				return false;
			}
		});

		if (0 != methodResults.size()) {
			return methodResults;
		}else{
			return null;
		}

	}

	/**
	 * find the direct static call from some Type(class or interface) 
	 * @param expressionValue    the type name
	 * @param methodName     the method name
	 * @return
	 */
	public List<SearchResult> findMethodInvocation(
		final String expressionValue, final String methodName) {

		//SearchResult retval = null;
		final List<SearchResult> methodResults = new ArrayList<>();

		ast.accept(new ASTVisitor() {

			@Override
			public boolean visit(MethodInvocation node) {
				String methodNameValue = node.getName().toString();
				Expression expression = node.getExpression();
				
				System.out.println(expression+"."+methodNameValue);
				
				if ( methodName.equals(methodNameValue) && expression != null
						&& expression.toString().equals(expressionValue)) {
					final int startOffset = expression.getStartPosition();
					final int startLine = ast.getLineNumber(startOffset);
					final int endOffset = node.getStartPosition() + node.getLength();
					final int endLine = ast.getLineNumber(endOffset);

					methodResults.add(new SearchResult(file, startOffset,
						endOffset, startLine, endLine));
				}

				return true;
			}
		});

		if (0 != methodResults.size()) {
			return methodResults;
		}else{
			return null;
		}
		
	}

	@SuppressWarnings("unchecked")
	private CompilationUnit createJavaClassVisitor()
		throws FileNotFoundException, IOException {

		ASTParser parser = ASTParser.newParser(AST.JLS4);

		Map<String, String> options = JavaCore.getOptions();

		JavaCore.setComplianceOptions(JavaCore.VERSION_1_6, options);

		parser.setCompilerOptions(options);

		parser.setResolveBindings(true);
		parser.setStatementsRecovery(true);
		parser.setBindingsRecovery(true);
		parser.setSource(fileHelper.readFile(file).toCharArray());
		parser.setIgnoreMethodBodies(false);

		return (CompilationUnit)parser.createAST(null);
	}

	private final CompilationUnit ast;
	private final File file;
	private final FileHelper fileHelper;

}