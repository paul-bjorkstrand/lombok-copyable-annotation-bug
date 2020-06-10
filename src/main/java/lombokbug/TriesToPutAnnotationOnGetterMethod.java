package lombokbug;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class TriesToPutAnnotationOnGetterMethod {
  // This annotation should only be on fields and parameters
  // lombok tries to put this on the getter method
  @Getter //
  @MyAnnotation2
  String test;
}
