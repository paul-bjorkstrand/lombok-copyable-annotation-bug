package lombokbug;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TriesToPutAnnotationOnConstructorParam {
  // This annotation should only be on fields and methods
  // lombok tries to put this on the constructor param
  @MyAnnotation1 String test;
}
