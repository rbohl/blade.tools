package blade.migrate.core;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * Parses a java file and provides some  methods for finding search results
 */
public class JavaFileChecker {

	/**
	 * initialize the Checker
	 * @param file java file
	 */
	public JavaFileChecker(File file) {
		_file = file;
		_fileHelper = new FileHelper();

		try {
			synchronized (_map) {
				WeakReference<CompilationUnit> astRef = _map.get(file);

				if (astRef == null || astRef.get() == null) {
					final CompilationUnit newAst = createJavaClassVisitor();

					_map.put(file, new WeakReference<CompilationUnit>(newAst));

					_ast = newAst;
				}
				else {
					_ast = astRef.get();
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private CompilationUnit createJavaClassVisitor() {
		ASTParser parser = ASTParser.newParser(AST.JLS8);

		Map<String, String> options = JavaCore.getOptions();

		JavaCore.setComplianceOptions(JavaCore.VERSION_1_6, options);

		parser.setCompilerOptions(options);

		//setUnitName for resolve bindings
		String unitName = _file.getName();
		parser.setUnitName(unitName);

		String[] sources = { "" };
		String[] classpath = { "" };
		//setEnvironment for resolve bindings even if the args is empty
		parser.setEnvironment(classpath, sources, new String[] { "UTF-8" }, true);

		parser.setResolveBindings(true);
		parser.setStatementsRecovery(true);
		parser.setBindingsRecovery(true);
		parser.setSource(getJavaSource());
		parser.setIgnoreMethodBodies(false);

		return (CompilationUnit)parser.createAST(null);
	}

	protected File getFile() {
		return _file;
	}

	protected char[] getJavaSource() {
		try {
			return _fileHelper.readFile(_file).toCharArray();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<SearchResult> findCatchExceptions(final String[] exceptions) {

		final List<SearchResult> searchResults = new ArrayList<>();

		_ast.accept(new ASTVisitor() {

			@Override
			public boolean visit(CatchClause node){
				String exceptionTypeName = node.getException().getType().toString();
				boolean retVal = false;

				for (String exceptionType : exceptions) {
					if ( exceptionTypeName.equals(exceptionType)){
						final int startLine = _ast.getLineNumber(node.getException().getStartPosition());
						final int startOffset = node.getException().getStartPosition();

						int endLine = _ast.getLineNumber(node.getException().getStartPosition() + node.getException().getLength());
							int endOffset = node.getException().getStartPosition() + node.getException().getLength();
							searchResults
									.add(createSearchResult(startOffset,
										endOffset, startLine, endLine, true));

							retVal = true;
					}
				}

				return retVal;
			}
		});

		return searchResults;

	}

	public List<SearchResult> findImplementsInterface(final String interfaceName){
		final List<SearchResult> searchResults = new ArrayList<>();

		_ast.accept(new ASTVisitor() {

			@Override
			public boolean visit(TypeDeclaration node) {
				ITypeBinding[] superInterfaces = null;

				if (node.resolveBinding() != null) {
					superInterfaces = node.resolveBinding().getInterfaces();

					if (superInterfaces != null && superInterfaces.length > 0) {

						if (superInterfaces[0].getName()
								.equals(interfaceName)) {
							int startLine = _ast.getLineNumber(
									node.getName().getStartPosition());
							int startOffset = node.getName().getStartPosition();
							int endLine = _ast.getLineNumber(
									node.getName().getStartPosition()
											+ node.getName().getLength());
							int endOffset = node.getName().getStartPosition()
									+ node.getName().getLength();

							searchResults
									.add(createSearchResult(startOffset,
											endOffset, startLine, endLine, true));
						}
					}
				}

				return true;
			}
		});

		return searchResults;
	}

	public SearchResult findImport(final String importName) {
		final List<SearchResult> searchResults = new ArrayList<>();

		_ast.accept(new ASTVisitor() {

			@Override
			public boolean visit(ImportDeclaration node) {
				if (importName.equals(node.getName().toString())) {
					int startLine = _ast.getLineNumber(node.getName()
						.getStartPosition());
					int startOffset = node.getName().getStartPosition();
					int endLine = _ast.getLineNumber(node.getName()
						.getStartPosition() + node.getName().getLength());
					int endOffset = node.getName().getStartPosition() +
						node.getName().getLength();

					searchResults.add(createSearchResult(startOffset,
						endOffset, startLine, endLine, true));
				}

				return false;
			};
		});

		if (0 != searchResults.size()) {
			return searchResults.get(0);
		}

		return null;
	}

	public SearchResult findPackage(final String packageName) {
		final List<SearchResult> searchResults = new ArrayList<>();

		_ast.accept(new ASTVisitor() {
			@Override
			public boolean visit(PackageDeclaration node) {
				if (packageName.equals(node.getName().toString())) {
					int startLine = _ast.getLineNumber(node.getName()
						.getStartPosition());
					int startOffset = node.getName().getStartPosition();
					int endLine = _ast.getLineNumber(node.getName()
						.getStartPosition() + node.getName().getLength());
					int endOffset = node.getName().getStartPosition() +
						node.getName().getLength();

					searchResults.add(createSearchResult(startOffset,
						endOffset, startLine, endLine, true));
				}

				return false;
			};
		});

		if (0 != searchResults.size()) {
			return searchResults.get(0);
		}

		return null;
	}

	public List<SearchResult> findMethodDeclaration(
		final String name, final String[] params) {

		final List<SearchResult> searchResults = new ArrayList<>();

		_ast.accept(new ASTVisitor() {

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
					final int startLine = _ast.getLineNumber(node.getName()
						.getStartPosition());
					final int startOffset = node.getName().getStartPosition();
					node.accept(new ASTVisitor() {

						@Override
						public boolean visit(Block node) {

							// SimpleName parent can not be MarkerAnnotation and
							// SimpleType
							// SingleVariableDeclaration node contains the
							// parms's type

							int endLine = _ast.getLineNumber(node
								.getStartPosition());
							int endOffset = node.getStartPosition();
							searchResults
									.add(createSearchResult(startOffset,
										endOffset, startLine, endLine, true));

							return false;
						};
					});
				}

				return false;
			}
		});

		return searchResults;
	}
	/**
	 * find the method invocations for a particular method on a given type or expression
	 *
	 * @param typeHint the type hint to use when matching expressions
	 * @param expressionValue    the expression only value (no type hint)
	 * @param methodName     the method name
	 * @return    search results
	 */
	@SuppressWarnings("unchecked")
	public List<SearchResult> findMethodInvocations(
		final String typeHint, final String expressionValue, final String methodName,
		final String[] methodParamTypes) {
		final List<SearchResult> searchResults = new ArrayList<>();

		_ast.accept(new ASTVisitor() {

			@Override
			public boolean visit(MethodInvocation node) {
				final String methodNameValue = node.getName().toString();
				final Expression expression = node.getExpression();

				ITypeBinding type = null;

				if (expression != null) {
					type = expression.resolveTypeBinding();
				}

				if ( ((methodName.equals(methodNameValue)) || ("*".equals(methodName))) &&
						// if typeHint is not null it must match the type hint and ignore the expression
						// not strictly check the type and will check equals later
						( (typeHint != null && type != null && type.getName().endsWith(typeHint))  ||
						// with no typeHint then expressions can be used to match Static invocation
						 (typeHint == null && expression != null && expression.toString().equals(expressionValue)))) {

					boolean argumentsMatch = false;

					if (methodParamTypes != null) {
						Expression[] argExpressions = ((List<Expression>)node.arguments()).toArray(new Expression[0]);

						if (argExpressions.length == methodParamTypes.length) {
							//args number matched
							boolean possibleMatch = true;
							// assume all types will match until we find otherwise
							boolean typeMatched = true;
							boolean typeUnresolved = false;

							for(int i = 0; i < argExpressions.length; i++) {
								Expression arg = argExpressions[i];
								ITypeBinding argType = arg.resolveTypeBinding();

								if (argType != null) {
									//can resolve the type
									 if( argType.getName().equals(methodParamTypes[i])) {
										 //type matched
										continue;
									 } else {
										 //type unmatched
										 possibleMatch = false;
										 typeMatched = false;
										 break;
									 }
								} else{
									possibleMatch = false;
									//there are two cases :
									//typeUnresolved : means that  all resolved type is matched and there is unsolved type , need to set fullMatch false
									//typeUnmatched : means that some resolved type is unmatched , no need to add SearchResult

									//do not add searchResults now , just record the state and continue
									//because there maybe unmatched type later which will  break this case
									typeUnresolved = true;
								}
							}

							if( typeMatched && typeUnresolved ){
								final int startOffset = expression.getStartPosition();
								final int startLine = _ast.getLineNumber(startOffset);
								final int endOffset = node.getStartPosition() + node.getLength();
								final int endLine = _ast.getLineNumber(endOffset);
								//can't resolve the type but  args number matched  ,  note that the last param is false
								searchResults.add(createSearchResult(startOffset, endOffset, startLine, endLine, false));
							}

							if (possibleMatch) {
								argumentsMatch = true;
							}
						}
					}
					//any method args types is OK without setting methodParamTypes
					else {
						argumentsMatch = true;
					}

					if (argumentsMatch) {
						final int startOffset = expression.getStartPosition();
						final int startLine = _ast.getLineNumber(startOffset);
						final int endOffset = node.getStartPosition() + node.getLength();
						final int endLine = _ast.getLineNumber(endOffset);
						boolean isFullMatch = true;

						//endsWith but not equals
						if( typeHint != null && type != null && type.getName().endsWith(typeHint) && !type.getName().equals(typeHint) ) {
							isFullMatch = false;
						}

						searchResults.add(createSearchResult(startOffset, endOffset, startLine, endLine, isFullMatch));
					}
				}

				return true;
			}
		});

		return searchResults;
	}

	protected SearchResult createSearchResult(int startOffset, int endOffset,
			int startLine, int endLine, boolean fullMatch) {

		return new SearchResult(_file, startOffset, endOffset, startLine,
				endLine, fullMatch);
	}

	public List<SearchResult> findQualifiedName(final String exception) {
		final List<SearchResult> searchResults = new ArrayList<>();

		_ast.accept(new ASTVisitor() {

			@Override
			public boolean visit(QualifiedName node) {
				String qualifyName = node.getFullyQualifiedName();
				boolean retVal = false;

				if (qualifyName.equals(exception)) {
					final int startLine = _ast
							.getLineNumber(node.getStartPosition());
					final int startOffset = node.getStartPosition();
					int endLine = _ast.getLineNumber(
							node.getStartPosition() + node.getLength());
					int endOffset = node.getStartPosition() + node.getLength();
					searchResults.add(createSearchResult(startOffset,
							endOffset, startLine, endLine, true));

					retVal = true;
				}

				return retVal;
			}
		});

		return searchResults;
	}

	public List<SearchResult> findSuperClass(final String superClassName){
		final List<SearchResult> searchResults = new ArrayList<>();

		_ast.accept(new ASTVisitor() {

			@Override
			public boolean visit(TypeDeclaration node) {
				ITypeBinding superClass = null;

				if (node.resolveBinding() != null) {
					superClass = node.resolveBinding().getSuperclass();

					if (superClass != null) {
						if (superClass.getName().equals(superClassName)) {
							int startLine = _ast.getLineNumber(
									node.getName().getStartPosition());
							int startOffset = node.getName().getStartPosition();
							int endLine = _ast.getLineNumber(
									node.getName().getStartPosition()
											+ node.getName().getLength());
							int endOffset = node.getName().getStartPosition()
									+ node.getName().getLength();

							searchResults
									.add(createSearchResult(startOffset,
											endOffset, startLine, endLine, true));
						}
					}
				}

				return true;
			}
		});

		return searchResults;
	}

	private static Map<File, WeakReference<CompilationUnit>> _map = new WeakHashMap<>();

	private final CompilationUnit _ast;
	private final File _file;
	private final FileHelper _fileHelper;

}