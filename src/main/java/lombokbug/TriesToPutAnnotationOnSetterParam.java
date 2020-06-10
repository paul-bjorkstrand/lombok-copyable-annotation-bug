package lombokbug;

import lombok.Setter;

public class TriesToPutAnnotationOnSetterParam {
  // This annotation should only be on fields and methods
  // lombok tries to put this on the setter param
  @Setter @MyAnnotation1 String test;
}
