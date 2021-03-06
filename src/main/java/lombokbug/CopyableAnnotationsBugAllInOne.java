package lombokbug;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class CopyableAnnotationsBugAllInOne {
  @MyAnnotation1 // does not work, annotation does not list ElementType.PARAMETER
  @MyAnnotation2 // does not work, annotation does not list ElementType.METHOD
  @MyAnnotation3 // works, annotation lists ElementTypes FIELD, PARAMETER, and METHOD
  @Getter
  @Setter
  String test;
}
