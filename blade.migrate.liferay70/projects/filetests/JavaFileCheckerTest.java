package blade.migrate.liferay70;

public class Test {
	boolean value = false;

	public static void main(String[] args) {
		Foo foo = new Foo();

		System.out.println(
			foo.
				bar(value));

		System.out.println(
			String.
				valueOf(1));

		foo.bar("1");
	}

	Foo foo = new Foo();

	public void anotherMethod() {
		foo.bar("2");
	}

}
