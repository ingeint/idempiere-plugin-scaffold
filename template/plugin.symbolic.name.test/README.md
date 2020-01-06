# ${plugin.name} - Test

This fragment allows you to test your plugin with Junit 5.

## How it works

### Utils

- Annotations Assertion: Allows you to test the annotation over a class, see [Annotations.java](src/${plugin.root.as.path}/test/assertion/Annotations.java).
- Random Test Util: It is a collection of random utils, see [RandomTestUtil.java](src/${plugin.root.as.path}/test/util/RandomTestUtil.java).
- Random Test Util: Allows you to get a private field form reflection, see [ReflectionTestUtil.java](src/${plugin.root.as.path}/test/util/ReflectionTestUtil.java).

### Tech stack

- [Junit 5](https://junit.org/junit5/)
- [Assertj](https://joel-costigliola.github.io/assertj/)
- [Mockito](https://site.mockito.org/)
- [Javafaker](https://github.com/DiUS/java-faker)