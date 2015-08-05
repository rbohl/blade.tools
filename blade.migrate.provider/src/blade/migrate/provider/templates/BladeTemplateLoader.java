package blade.migrate.provider.templates;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import freemarker.cache.TemplateLoader;

public class BladeTemplateLoader implements TemplateLoader {

	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
		if (templateSource instanceof BladeTemplate) {
			BladeTemplate bladeTemplate = (BladeTemplate) templateSource;
			bladeTemplate.getReader().close();
		}
	}

	@Override
	public Object findTemplateSource(String name) throws IOException {
		BladeTemplate retval = null;

		InputStream resource = this.getClass().getResourceAsStream(name);

		if( resource != null ) {
			// found template

			retval = new BladeTemplate(name, resource);
		}


		return retval;
	}

	@Override
	public long getLastModified(Object arg0) {
		return -1;
	}

	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		Reader retval = null;

		if (templateSource instanceof BladeTemplate) {
			BladeTemplate bladeTemplate = (BladeTemplate) templateSource;

			retval = bladeTemplate.getReader();
		}

		return retval;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
