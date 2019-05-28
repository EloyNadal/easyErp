package com.easyErp.project.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.easyErp.project.model.AppManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

public class EscritorXLS {
	
	public static <T> boolean crearArchivoExcel(TableView<T> tabla) {
		
		LinkedList<String> header = new LinkedList<String>(); 
		for (TableColumn<T, ?> cabeceras : tabla.getColumns()) {
			header.add(cabeceras.getText());
		}
		
		String[][] rows = new String[tabla.getItems().size()][tabla.getVisibleLeafColumns().size()];
		
		for (int i = 0; i < tabla.getItems().size(); i++) {
			for (int j = 0; j < tabla.getVisibleLeafColumns().size(); j++) {
				if (tabla.getVisibleLeafColumns().get(j).getCellData(i) != null){
					rows[i][j] = tabla.getVisibleLeafColumns().get(j).getCellData(i).toString();
				}
				else {
					rows[i][j] = "";
				}
			}
		}
		
		File file = openFileChooser("Nombre del archivo", false);
		if(file == null) return false;
		crearResultado(rows, header, file);
		return true;
	}

	private static void crearResultado(String[][] data, LinkedList<String> cabecera ,File file) {

		XSSFWorkbook libro = new XSSFWorkbook();
		
		XSSFSheet hoja1 = libro.createSheet("data");
		CellStyle style = libro.createCellStyle();
		
		Font font = libro.createFont();
		font.setBold(true);
		style.setFont(font);
		
		CellStyle totales = libro.createCellStyle();
		totales.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		totales.setFillPattern(FillPatternType.THICK_BACKWARD_DIAG);

		int fila = 0;

		XSSFRow rowCabecera = hoja1.createRow(0);

		for (int i = 0; i < cabecera.size(); i++) {
			XSSFCell cellCabecera = rowCabecera.createCell(i);
			cellCabecera.setCellStyle(style);
			cellCabecera.setCellValue(cabecera.get(i));
		}

		fila++;
		
		for (int i = 0; i < data.length; i++) {
			
			XSSFRow row = hoja1.createRow(fila);
			
			for (int k = 0; k < data[i].length; k++) {
				XSSFCell cell = row.createCell(k);
				cell.setCellValue(data[i][k]);
			}
			
			fila++;			
		}
		for (int i = 0; i < cabecera.size(); i++) {
			hoja1.autoSizeColumn(i);
		}

		FileOutputStream fileOS = null;

		try {
			fileOS = new FileOutputStream(file);
			if (file.exists()) {
				file.delete();
			}

			libro.write(fileOS);
			fileOS.flush();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileOS.close();
				libro.close();
				showAlert();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	private static File openFileChooser(String title, boolean open) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XLS", "*.xlsx"));
		File file;
		if (open)
			file = fileChooser.showOpenDialog(AppManager.getInstance().getStage());
		else
			file = fileChooser.showSaveDialog(AppManager.getInstance().getStage());
		return file;
	}
	
	private static void showAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Exportar tabla");
        alert.setHeaderText(null);
        alert.setContentText("Tabla exportada correctamente");
        alert.showAndWait();
  }
	
//	En el boton
//	ImageView i = new ImageView(new Image(getClass().getResourceAsStream("/image/excelIcon.png")));
//	i.setFitHeight(25);
//	i.setFitWidth(25);
//	btnExc.setGraphic(i);
	

}
