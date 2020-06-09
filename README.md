# lombok-copyable-annotation-bug

This is a minimal example of the problem.

The tl;dr version:

> If an annotation does not specify at least these three targets: field, parameter, and method, lombok will cause a compiler error when building.

### What is happening

Lombok is not inspecting the annotations `@Target` meta-annotation for `copyableAnnotations`, and is attempting to add all annotations listed in that configuration to all generated code locations.

For example: in [MyAnnotation1.java], because the annotation does not have `ElementType.PARAMETER` in its `@Target` meta-annotation, then Lombok fails because it is trying to put that annotation on the constructor parameter generated by `@RequiredArgsConstructor`.

[MyAnnotation1.java]: src/main/java/lombokbug/MyAnnotation1.java

Similarly, this is broken for when an annotation does not have `ElementType.METHOD`, as in [MyAnnotation2.java].

[MyAnnotation2.java]: src/main/java/lombokbug/MyAnnotation2.java

### How it should work

Lombok should be looking into the `@Target` meta-annotation and use those targets to determine which generated code gets which annotation.

Ideally, Lombok would be able to as many `ElementType` values as possible, depending on the annotation. At a minimum, it should handle the following (based on the current `copyableAnnotation` semantics):

- `FIELD` - necessary, since the annotated source code elements are mostly fields.
- `METHOD` - if present, apply to the getter methods.
- `PARAMETER` - if present, apply to constructor & setter method parameters (possibly including `@Builder` methods).

### Example

In my example code, I would expect the code to build and produce the following delombok'd code:

```java
package lombokbug;

import lombok.Generated;

public class CopyableAnnotationsBug {
    @MyAnnotation1
    @MyAnnotation2
    @MyAnnotation3
    String testValue;

    @Generated
    public TestData(@MyAnnotation2 @MyAnnotation3 String testValue) {
        this.testValue = testValue;
    }

    @MyAnnotation1
    @MyAnnotation3
    @Generated
    public String getTestValue() {
        return this.testValue;
    }

    @Generated
    public void setTestValue(@MyAnnotation2 @MyAnnotation3 String testValue) {
        this.testValue = testValue;
    }
}
```

Here, `@MyAnnotation1` is applied only to the parameters of the constructor and setter, while `@MyAnnotation2` is applied only to the getter method.
