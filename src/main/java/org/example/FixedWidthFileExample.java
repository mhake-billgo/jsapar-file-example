package org.example;

import org.jsapar.TextComposer;
import org.jsapar.model.BigDecimalCell;
import org.jsapar.model.DateCell;
import org.jsapar.model.Line;
import org.jsapar.model.StringCell;
import org.jsapar.schema.Schema;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * This is an example of using the JSaPar (Java Schema based Parser) to create a fixed width file.
 * See overview doc here: https://org-tigris-jsapar.github.io/jsapar/index
 *  And schema specifics here: https://org-tigris-jsapar.github.io/jsapar/basics_schema
 */
public class FixedWidthFileExample {

  public static void main(String[] args) throws IOException {
    Schema schema = getSchema("src/main/java/org/example/fixed-width-schema.xml");
    try (StringWriter writer = new StringWriter()) {
      TextComposer composer = new TextComposer(schema, writer);
      composer.composeLine(createHeaderLine("BGO123", new Date()));
      composer.composeLine(createPaymentLine("JoeSmith", "Mikes Bikes", new BigDecimal("23.44")));
      composer.composeLine(createPaymentLine("Fred", "ACME Plumbing", new BigDecimal("345.66")));
      composer.composeLine(createPaymentLine("Steve", "Overland Mountain Bike Association", new BigDecimal("50.00")));

      System.out.println("\n------ Result --------");
      System.out.println(writer.toString());
    }
  }

  static Schema getSchema(String path) throws IOException {
    try (Reader outSchemaReader = new FileReader(path)) {
      return Schema.ofXml(outSchemaReader);
    }
  }

  static Line createHeaderLine(String entityId, Date date) {
    Line line = new Line("Header")
            .addCell(new StringCell("EntityId", entityId))
            .addCell(new DateCell("Date",date));
    return line;
  }

  static Line createPaymentLine(String payFrom, String payTo, BigDecimal amount) {
    Line line = new Line("Payment")
            .addCell(new StringCell("PayFrom", payFrom))
            .addCell(new StringCell("PayTo", payTo))
            .addCell(new BigDecimalCell("Amount", amount));
    return line;
  }

}
