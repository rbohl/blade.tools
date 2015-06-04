package blade.migrate.liferay70;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.osgi.service.component.annotations.Component;

import blade.migrate.api.Problem;
import blade.migrate.api.ProjectMigrator;

@Component
public class AssetRendererIndexerAPIs implements ProjectMigrator {
	public static final String VERSION_1_6 = "1.6";

	@Override
	public List<Problem> analyze(File projectDir) {
		return Collections.singletonList(new Problem());
	}

	AstVisitor visitString( String source ) {
        ASTParser parser = ASTParser.newParser(AST.JLS3);

        @SuppressWarnings( "unchecked" )
        Map<String,String> options = JavaCore.getOptions();
        JavaCore.setComplianceOptions(JavaCore.VERSION_1_6, options);
        parser.setCompilerOptions(options);

        parser.setResolveBindings(false);
        parser.setStatementsRecovery(false);
        parser.setBindingsRecovery(false);
        parser.setSource(source.toCharArray());
        parser.setIgnoreMethodBodies(false);

        CompilationUnit ast = (CompilationUnit) parser.createAST(null);

        // AstVisitor extends org.eclipse.jdt.core.dom.ASTVisitor
        AstVisitor visitor = new AstVisitor();
        ast.accept( visitor );

        return visitor;
    }

	class AstVisitor extends ASTVisitor {
		@Override
		public boolean visit(MethodDeclaration node) {
			System.out.println(node);
			return super.visit(node);
		}
	}
}
