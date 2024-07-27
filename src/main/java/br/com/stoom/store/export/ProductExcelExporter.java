package br.com.stoom.store.export;

import br.com.stoom.store.model.Product;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

public class ProductExcelExporter extends AbstractExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ProductExcelExporter() {
        workbook = new XSSFWorkbook();
    }

    public void exportProducts(List<Product> listProducts, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/octet-stream", ".xlsx");

        writeHeaderLine();
        writeDataLines(listProducts);

        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeHeaderLine() {
        // Cria um nome único para a aba
        String sheetName = "Products_" + System.currentTimeMillis(); // Nome único com timestamp

        // Cria a aba com o nome único
        sheet = workbook.createSheet(sheetName);

        XSSFRow row = sheet.createRow(0);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        createCell(row, 0, "Produto Id", cellStyle);
        createCell(row, 1, "Nome Produto", cellStyle);
        createCell(row, 2, "SKU", cellStyle);
        createCell(row, 3, "Categoria", cellStyle);
        createCell(row, 4, "Marca", cellStyle);
        createCell(row, 5, "Preço", cellStyle);
        createCell(row, 6, "Descrição", cellStyle);
        createCell(row, 7, "Sttus", cellStyle);
    }



    private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle style) {
        XSSFCell cell = row.createCell(columnIndex);
        sheet.autoSizeColumn(columnIndex);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value ? "Sim" : "Não");
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);
    }


    private void writeDataLines(List<Product> listProducts) {
        int rowIndex = 1;

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        cellStyle.setFont(font);

        for (Product product : listProducts) {
            XSSFRow row = sheet.createRow(rowIndex++);
            int columnIndex = 0;

            createCell(row, columnIndex++, product.getId(), cellStyle);
            createCell(row, columnIndex++, product.getName(), cellStyle);
            createCell(row, columnIndex++, product.getSku(), cellStyle);
            createCell(row, columnIndex++, product.getCategory().getName(), cellStyle);
            createCell(row, columnIndex++, product.getBrand().getName(), cellStyle);
            createCell(row, columnIndex++, product.getPrice(), cellStyle);
            createCell(row, columnIndex++, product.getDescription(), cellStyle);
            createCell(row, columnIndex++, product.isActive(), cellStyle);
        }
    }
}
