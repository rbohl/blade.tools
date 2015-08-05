package blade.migrate.provider.templates;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class BladeTemplate {

	private String _name;
	private InputStream _resource;

	public BladeTemplate(String name, InputStream resource) {
		_name = name;
		_resource = resource;
	}

	public Reader getReader() {
		return new BufferedReader(new InputStreamReader(_resource));
	}

}
