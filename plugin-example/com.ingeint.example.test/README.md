# Plugin Example - Test

This fragment allows you to test your plugin with Junit 5.

## How it works

This fragment will be invoke when the main plugin is creating.

### Utils

- Annotations Assertion: Allows you to test the annotation over a class, see [Annotations.java](src/com/ingeint/example/test/assertion/Annotations.java).
- Random Test Util: It is a collection of random utils, see [RandomTestUtil.java](src/com/ingeint/example/test/util/RandomTestUtil.java).
- Random Test Util: Allows you to get a private field form reflection, see [ReflectionTestUtil.java](src/com/ingeint/example/test/util/ReflectionTestUtil.java).

### Tech stack

- [Junit 5](https://junit.org/junit5/)
- [Assertj](https://joel-costigliola.github.io/assertj/)
- [Mockito](https://site.mockito.org/)
- [Javafaker](https://github.com/DiUS/java-faker)

### Resources

If you want to add any file resource use the `resources` folder.