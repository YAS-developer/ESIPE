package fr.uge.poo.uberclientformatters.question1;

@FunctionalInterface
public interface UberClientFormater {
  UberClientFormater toHTML =
          uDto -> String.format("<h2>%s %s  (%1.2f*)</h2>", uDto.firstName(), uDto.lastName(), uDto.avgGrades());

  UberClientFormater toHTMLSimple =
          uDto -> String.format("<h2>%s %s </h2>", uDto.firstName(), uDto.lastName());

  UberClientFormater toHtmlWithEmails = uDto -> String.format("<h2>%s %s (%1.2f*) : %s </h2>", uDto.firstName(), uDto.lastName(), uDto.avgGrades(), uDto.emails());


  String format(UberClient.DTO uDto);
}
