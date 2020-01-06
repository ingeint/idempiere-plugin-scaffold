# iDempiere Plugin Template - Test

This fragment allows you to test your plugin with Junit 5.

## How it works

### Utils

- Annotations Assertion: Allows you to test the annotation over a class, see [Annotations.java](src/com.ingeint.template/test/assertion/Annotations.java).
- Random Test Util: It is a collection of random utils, see [RandomTestUtil.java](src/com.ingeint.template/test/util/RandomTestUtil.java).
- Random Test Util: Allows you to get a private field form reflection, see [ReflectionTestUtil.java](src/com.ingeint.template/test/util/ReflectionTestUtil.java).

### Tech stack

- [Junit 5](https://junit.org/junit5/)
- [Assertj](https://joel-costigliola.github.io/assertj/)
- [Mockito](https://site.mockito.org/)
- [Javafaker](https://github.com/DiUS/java-faker)