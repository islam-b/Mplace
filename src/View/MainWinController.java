package View;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import Model.CoordGeo;
import Model.Point;
import Util.Conversion;
import Util.Fermat;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainWinController {

	@FXML
	private TextField longi_field;
	@FXML
	private TextField latt_field;
	@FXML
	private JFXButton add_well;
	@FXML
	private JFXButton manifold_place;
	@FXML
	private VBox wells_container;
	
	ArrayList<Point> wells= new ArrayList<Point>();
	ArrayList<CoordGeo> wells_geo= new ArrayList<CoordGeo>();
	ArrayList<CoordGeo> best_pos = new ArrayList<CoordGeo>();
	
	public void new_well() {
		double latt = Double.valueOf(latt_field.getText());
		double longi = Double.valueOf(longi_field.getText());
		wells_geo.add(new CoordGeo(latt,longi));
		wells.add(Conversion.gps2xy(latt,longi));
		latt_field.setText("");
		longi_field.setText("");
		show_well();
	}
	
	public void show_well() {
		HBox well_contain= new HBox();
		well_contain.setAccessibleHelp(String.valueOf(wells.size()));
		DecimalFormat df = new DecimalFormat ( ) ; 
		df.setMaximumFractionDigits ( 6 ) ; //arrondi à 2 chiffres apres la virgules 
		df.setMinimumFractionDigits ( 2 ) ; 
		Label title=new Label();
		title.setText("	  Well "+wells.size()+"  ("+df.format(wells_geo.get(wells_geo.size()-1).latt)+" , "+df.format(wells_geo.get(wells_geo.size()-1).longi)+")");
		well_contain.getChildren().add(title);
		
		wells_container.getChildren().add(well_contain);
		
	}
	@FXML 
	private AnchorPane container;
	public void show_manifold() {
		
//		wells.add(Conversion.gps2xy(24.026397,0.120849 ));
//		wells.add(Conversion.gps2xy(23.523700,3.490693));
//		wells.add(Conversion.gps2xy(26.391870, 3.658447));
//		wells.add(Conversion.gps2xy(26.411551, -0.1208500));
		
		
		
		Fermat fermat=new Fermat();
		best_pos=fermat.getFermatPoint(wells);
		Point echelle=Conversion.xy2pixel(wells);
		ArrayList<CoordGeo> lst=fermat.getFermatPoint(wells);
		Point manifold=Conversion.gps2xy(lst.get(0).latt,lst.get(0).longi);
		int i=0;
		for (i=0;i<wells.size();i++) {
			Line line=new Line(wells.get(i).x+16,wells.get(i).y+16,manifold.x+32,manifold.y+32);
			line.setFill(Paint.valueOf("#79a4d2"));
			line.setStrokeWidth(3);
			ImageView well=new ImageView();
			well.setImage(new Image(this.getClass().getResourceAsStream("Resources/well.png")));
			well.setFitWidth(32);
			well.setPreserveRatio(true);
			well.setLayoutX(wells.get(i).x);
			well.setLayoutY(wells.get(i).y);
			container.getChildren().add(line);
			container.getChildren().add(well);
			
		}
		ImageView manif=new ImageView();
		manif.setImage(new Image(this.getClass().getResourceAsStream("Resources/manifold.png")));
		manif.setFitWidth(64);
		manif.setPreserveRatio(true);
		manif.setLayoutX(manifold.x);
		manif.setLayoutY(manifold.y);
		container.getChildren().add(manif);
		showbests();
		
	}
	
	@FXML
	VBox bests_container;
	public void showbests() {
		int i=0;
		DecimalFormat df = new DecimalFormat ( ) ;
		df.setMaximumFractionDigits ( 6 ) ; //arrondi à 2 chiffres apres la virgules 
		df.setMinimumFractionDigits ( 2 ) ; 
		Label lab0=new Label();
		lab0.setText("      "+"("+ df.format(best_pos.get(i).latt)+" , "+df.format(best_pos.get(i).longi)+")");
		lab0.setFont(Font.font("System", FontWeight.BOLD, 14));
		bests_container.getChildren().add(lab0);
	
		
		
		for (i=1;i<best_pos.size();i++) {
			Label lab=new Label();
			lab.setText("	 "+i+". ("+ df.format(best_pos.get(i).latt)+" , "+df.format(best_pos.get(i).longi)+")");
			bests_container.getChildren().add(lab);
		}
	}
}
